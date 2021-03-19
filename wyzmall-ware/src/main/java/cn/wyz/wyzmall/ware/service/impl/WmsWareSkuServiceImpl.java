package cn.wyz.wyzmall.ware.service.impl;

import cn.wyz.common.utils.R;
import cn.wyz.wyzmall.ware.feign.ProductFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.wyz.common.utils.PageUtils;
import cn.wyz.common.utils.Query;

import cn.wyz.wyzmall.ware.dao.WmsWareSkuDao;
import cn.wyz.wyzmall.ware.entity.WmsWareSkuEntity;
import cn.wyz.wyzmall.ware.service.WmsWareSkuService;
import org.springframework.util.StringUtils;


@Service("wmsWareSkuService")
public class WmsWareSkuServiceImpl extends ServiceImpl<WmsWareSkuDao, WmsWareSkuEntity> implements WmsWareSkuService {

    @Autowired
    private ProductFeignService productFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<WmsWareSkuEntity> wmsWareSkuEntityQueryWrapper = new QueryWrapper<>();

        String skuId = (String) params.get("skuId");
        if(StringUtils.hasLength(skuId)) {
            wmsWareSkuEntityQueryWrapper.eq("sku_id", skuId);
        }

        String wareId = (String) params.get("wareId");
        if(StringUtils.hasLength(wareId)) {
            wmsWareSkuEntityQueryWrapper.eq("ware_id", wareId);
        }
        IPage<WmsWareSkuEntity> page = this.page(
                new Query<WmsWareSkuEntity>().getPage(params),
                wmsWareSkuEntityQueryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public void addStock(Long skuId, Long wareId, Integer skuNum) {
        WmsWareSkuEntity wmsWareSkuEntity = baseMapper.selectOne(new QueryWrapper<WmsWareSkuEntity>().eq("sku_id", skuId).eq("ware_id", wareId));
        if(wmsWareSkuEntity == null) {
            WmsWareSkuEntity skuEntity = new WmsWareSkuEntity();
            skuEntity.setSkuId(skuId);
            skuEntity.setWareId(wareId);
            skuEntity.setStock(skuNum);
            skuEntity.setStockLocked(0);
            try {
                R r = productFeignService.info(skuId);
                Map<String, Object> data = (Map<String, Object>) r.get("skuinfo");
                if(r.getCode().equals(0)) {
                    skuEntity.setSkuName((String) data.get("skuName"));
                }
            } catch (Exception ignored) {

            }

            baseMapper.insert(skuEntity);
        } else {
            baseMapper.addStock(skuId, wareId, skuNum);
        }
    }

}