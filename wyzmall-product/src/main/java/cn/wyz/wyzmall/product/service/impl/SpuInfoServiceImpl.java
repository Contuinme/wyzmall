package cn.wyz.wyzmall.product.service.impl;

import cn.wyz.common.constant.ProductConstant;
import cn.wyz.common.to.SkuReductionTo;
import cn.wyz.common.to.SpuBoundTo;
import cn.wyz.common.to.es.SkuEsModel;
import cn.wyz.common.utils.R;
import cn.wyz.common.vo.SkuHasStockVo;
import cn.wyz.wyzmall.product.entity.*;
import cn.wyz.wyzmall.product.feign.CouponFeignService;
import cn.wyz.wyzmall.product.feign.SearchFeignService;
import cn.wyz.wyzmall.product.feign.WareFeignService;
import cn.wyz.wyzmall.product.service.*;
import cn.wyz.wyzmall.product.vo.*;
import com.alibaba.fastjson.TypeReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.wyz.common.utils.PageUtils;
import cn.wyz.common.utils.Query;

import cn.wyz.wyzmall.product.dao.SpuInfoDao;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Autowired
    private SpuInfoDescService spuInfoDescService;

    @Autowired
    private SpuImagesService spuImagesService;

    @Autowired
    private AttrService attrService;

    @Autowired
    private ProductAttrValueService productAttrValueService;

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private SkuImagesService skuImagesService;

    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;

    @Autowired
    private CouponFeignService couponFeignService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private WareFeignService wareFeignService;

    @Autowired
    private SearchFeignService searchFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void saveSpuInfo(SpuSaveVo vo) {

        //保存基本信息
        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
        BeanUtils.copyProperties(vo, spuInfoEntity);
        spuInfoEntity.setCreateTime(new Date());
        spuInfoEntity.setUpdateTime(new Date());
        saveBaseSpuInfo(spuInfoEntity);

        //保存描述图片
        List<String> decript = vo.getDecript();
        SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity();
        spuInfoDescEntity.setSpuId(spuInfoEntity.getId());
        spuInfoDescEntity.setDecript(String.join(",", decript));
        spuInfoDescService.saveSpuInfoDesc(spuInfoDescEntity);

        //保存图片集
        List<String> images = vo.getImages();
        spuImagesService.saveImages(spuInfoEntity.getId(), images);

        //保存规格参数
        List<BaseAttrs> baseAttrs = vo.getBaseAttrs();
        List<ProductAttrValueEntity> collect = baseAttrs.stream().map(attr -> {
            ProductAttrValueEntity productAttrValueEntity = new ProductAttrValueEntity();
            productAttrValueEntity.setAttrId(attr.getAttrId());
            AttrEntity byId = attrService.getById(attr.getAttrId());
            productAttrValueEntity.setAttrName(byId.getAttrName());
            productAttrValueEntity.setAttrValue(attr.getAttrValues());
            productAttrValueEntity.setQuickShow(attr.getShowDesc());
            productAttrValueEntity.setSpuId(spuInfoEntity.getId());
            return productAttrValueEntity;
        }).collect(Collectors.toList());
        productAttrValueService.saveBatch(collect);

        //保存积分
        SpuBoundTo spuBoundTo = new SpuBoundTo();
        BeanUtils.copyProperties(vo.getBounds(), spuBoundTo);
        spuBoundTo.setSpuId(spuInfoEntity.getId());
        R r = couponFeignService.saveSpuBounds(spuBoundTo);
        if(!r.getCode().equals(0)) {
            log.error("远程保存spu积分信息失败");
        }

        //保存sku
        List<Skus> skus = vo.getSkus();
        if(!CollectionUtils.isEmpty(skus)) {
            skus.forEach(sku -> {
                String defaultImg = "";
                for (Images image : sku.getImages()) {
                    if(image.getDefaultImg() == 1) {
                        defaultImg = image.getImgUrl();
                    }
                }
                SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
                BeanUtils.copyProperties(sku,skuInfoEntity);
                skuInfoEntity.setBrandId(spuInfoEntity.getBrandId());
                skuInfoEntity.setCatalogId(spuInfoEntity.getCatalogId());
                skuInfoEntity.setSaleCount(0L);
                skuInfoEntity.setSpuId(spuInfoEntity.getId());
                skuInfoEntity.setSkuDefaultImg(defaultImg);
                skuInfoService.save(skuInfoEntity);

                Long skuId = skuInfoEntity.getSkuId();

                //sku图片信息
                List<SkuImagesEntity> skuImagesEntities = sku.getImages().stream().map(image -> {
                    SkuImagesEntity skuImagesEntity = new SkuImagesEntity();

                    skuImagesEntity.setSkuId(skuId);
                    skuImagesEntity.setImgUrl(image.getImgUrl());
                    skuImagesEntity.setDefaultImg(image.getDefaultImg());
                    return skuImagesEntity;
                }).filter(skuImagesEntity -> StringUtils.hasLength(skuImagesEntity.getImgUrl()))
                  .collect(Collectors.toList());

                skuImagesService.saveBatch(skuImagesEntities);

                //保存销售属性
                List<Attr> attr = sku.getAttr();
                List<SkuSaleAttrValueEntity> skuSaleAttrValueEntities = attr.stream().map(a -> {
                    SkuSaleAttrValueEntity skuSaleAttrValueEntity = new SkuSaleAttrValueEntity();
                    BeanUtils.copyProperties(a, skuSaleAttrValueEntity);
                    skuSaleAttrValueEntity.setSkuId(skuId);
                    return skuSaleAttrValueEntity;
                }).collect(Collectors.toList());
                skuSaleAttrValueService.saveBatch(skuSaleAttrValueEntities);

                //保存优惠信息
                SkuReductionTo skuReductionTo = new SkuReductionTo();
                BeanUtils.copyProperties(sku, skuReductionTo);
                skuReductionTo.setSkuId(skuId);
                if(skuReductionTo.getFullCount() > 0 || skuReductionTo.getFullPrice().compareTo(BigDecimal.ZERO) > 0) {
                    R res = couponFeignService.saveSkuReduction(skuReductionTo);
                    if(!res.getCode().equals(0)) {
                        log.error("远程保存优惠信息信息失败");
                    }
                }
            });
        }
    }

    @Override
    public void saveBaseSpuInfo(SpuInfoEntity spuInfoEntity) {
        baseMapper.insert(spuInfoEntity);
    }

    @Override
    public PageUtils queryPageWithCondition(Map<String, Object> params) {
        QueryWrapper<SpuInfoEntity> spuInfoEntityQueryWrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if(StringUtils.hasLength(key)) {
            spuInfoEntityQueryWrapper.and(wrapper -> wrapper.and(w -> w.eq("id", key).or().like("spu_name", key)));
        }
        String status = (String) params.get("status");
        if(StringUtils.hasLength(status)) {
            spuInfoEntityQueryWrapper.eq("publish_status", status);
        }
        String brandId = (String) params.get("brandId");
        if(StringUtils.hasLength(brandId) && !"0".equalsIgnoreCase(brandId)) {
            spuInfoEntityQueryWrapper.eq("brand_id", brandId);
        }
        String catalogId = (String) params.get("catalogId");
        if(StringUtils.hasLength(catalogId) && !"0".equalsIgnoreCase(catalogId)) {
            spuInfoEntityQueryWrapper.eq("catalog_id", catalogId);
        }
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                spuInfoEntityQueryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public void spuUp(Long spuId) {
        List<SkuInfoEntity> skuInfoEntityList = skuInfoService.getSkusBySpuId(spuId);
        List<Long> skuIds = skuInfoEntityList.stream().map(SkuInfoEntity::getSkuId).collect(Collectors.toList());

        List<ProductAttrValueEntity> baseAttrs = productAttrValueService.baseAttrListForSpu(spuId);
        List<Long> baseAttrIds = baseAttrs.stream().map(ProductAttrValueEntity::getAttrId).collect(Collectors.toList());
        List<Long> searchAttrIds = attrService.selectSearchAttrIds(baseAttrIds);
        HashSet<Long> searchAttrIdsSet = new HashSet<>(searchAttrIds);

        List<SkuEsModel.Attrs> attrsList = baseAttrs.stream().filter(baseAttrId -> searchAttrIdsSet.contains(baseAttrId)).map(item -> {
            SkuEsModel.Attrs attrs = new SkuEsModel.Attrs();
            BeanUtils.copyProperties(item, attrs);
            return attrs;
        }).collect(Collectors.toList());

        Map<Long, Boolean> skuHasStockVoMap = null;
        try {
            R skusHasStockList = wareFeignService.getSkusHasStock(skuIds);
            skuHasStockVoMap = skusHasStockList.getData(new TypeReference<List<SkuHasStockVo>>(){}).stream().collect(Collectors.toMap(SkuHasStockVo::getSkuId, item -> item.getHasStock()));
        } catch (Exception e) {
            log.error("远程调用库存服务异常，错误原因: {}", e);
        }

        Map<Long, Boolean> finalSkuHasStockVoMap = skuHasStockVoMap;
        List<SkuEsModel> skuEsModelList = skuInfoEntityList.stream().map(skuInfoEntity -> {
            SkuEsModel skuEsModel = new SkuEsModel();
            BeanUtils.copyProperties(skuInfoEntity, skuEsModel);
            skuEsModel.setSkuPrice(skuInfoEntity.getPrice());
            skuEsModel.setSkuImg(skuInfoEntity.getSkuDefaultImg());
            if(finalSkuHasStockVoMap == null) {
                skuEsModel.setHasStock(true);
            } else {
                skuEsModel.setHasStock(finalSkuHasStockVoMap.get(skuInfoEntity.getSkuId()));
            }
            skuEsModel.setHotScore(0L);

            BrandEntity brandEntity = brandService.getById(skuEsModel.getBrandId());
            skuEsModel.setBrandName(brandEntity.getName());
            skuEsModel.setBrandImg(brandEntity.getLogo());

            CategoryEntity categoryEntity = categoryService.getById(skuEsModel.getCatalogId());
            skuEsModel.setCatalogName(categoryEntity.getName());

            skuEsModel.setAttrs(attrsList);

            return skuEsModel;
        }).collect(Collectors.toList());

        R r = searchFeignService.productStatusUp(skuEsModelList);
        if(r.getCode() == 0) {
            baseMapper.updateSpuStatus(spuId, ProductConstant.StatusEnum.NEW_SPU.getCode());
        } else {
            // TODO 重复调用 接口幂等性 重试机制？
        }
    }

}