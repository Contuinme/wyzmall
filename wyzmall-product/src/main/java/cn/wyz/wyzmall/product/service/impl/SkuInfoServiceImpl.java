package cn.wyz.wyzmall.product.service.impl;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.wyz.common.utils.PageUtils;
import cn.wyz.common.utils.Query;

import cn.wyz.wyzmall.product.dao.SkuInfoDao;
import cn.wyz.wyzmall.product.entity.SkuInfoEntity;
import cn.wyz.wyzmall.product.service.SkuInfoService;
import org.springframework.util.StringUtils;


@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                new QueryWrapper<SkuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageWithCondition(Map<String, Object> params) {
        QueryWrapper<SkuInfoEntity> skuInfoEntityQueryWrapper = new QueryWrapper<>();

        String key = (String) params.get("key");
        if(StringUtils.hasLength(key)) {
            skuInfoEntityQueryWrapper.and(wrapper -> wrapper.eq("sku_id", key).or().like("sku_name", key));
        }

        String catalogId = (String) params.get("catalogId");
        if(StringUtils.hasLength(catalogId) && !"0".equalsIgnoreCase(catalogId)) {
            skuInfoEntityQueryWrapper.eq("catalog_id", catalogId);
        }

        String brandId = (String) params.get("brandId");
        if(StringUtils.hasLength(brandId) && !"0".equalsIgnoreCase(brandId)) {
            skuInfoEntityQueryWrapper.eq("brand_id", brandId);
        }

        String min = (String) params.get("min");
        if(StringUtils.hasLength(min)) {
            skuInfoEntityQueryWrapper.ge("price", min);
        }

        String max = (String) params.get("max");
        if(StringUtils.hasLength(max)) {
            try {
                BigDecimal bigDecimal = new BigDecimal(max);

                if(bigDecimal.compareTo(BigDecimal.ZERO) == 1) {
                    skuInfoEntityQueryWrapper.le("price", max);
                }
            } catch (Exception e) {

            }
        }

        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                skuInfoEntityQueryWrapper
        );

        return new PageUtils(page);
    }

}