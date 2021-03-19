package cn.wyz.wyzmall.ware.service.impl;

import cn.wyz.wyzmall.ware.entity.WmsPurchaseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.wyz.common.utils.PageUtils;
import cn.wyz.common.utils.Query;

import cn.wyz.wyzmall.ware.dao.WmsPurchaseDetailDao;
import cn.wyz.wyzmall.ware.entity.WmsPurchaseDetailEntity;
import cn.wyz.wyzmall.ware.service.WmsPurchaseDetailService;
import org.springframework.util.StringUtils;


@Service("wmsPurchaseDetailService")
public class WmsPurchaseDetailServiceImpl extends ServiceImpl<WmsPurchaseDetailDao, WmsPurchaseDetailEntity> implements WmsPurchaseDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<WmsPurchaseDetailEntity> wmsPurchaseDetailEntityQueryWrapper = new QueryWrapper<>();

        String key = (String) params.get("key");
        if(StringUtils.hasLength(key)) {
            wmsPurchaseDetailEntityQueryWrapper.and(queryWrapper -> queryWrapper.eq("purchase_id", key).or().eq("sku_id", key));
        }

        String status = (String) params.get("status");
        if(StringUtils.hasLength(status)) {
            wmsPurchaseDetailEntityQueryWrapper.eq("status", status);
        }

        String wareId = (String) params.get("wareId");
        if(StringUtils.hasLength(wareId)) {
            wmsPurchaseDetailEntityQueryWrapper.eq("ware_id", wareId);
        }

        IPage<WmsPurchaseDetailEntity> page = this.page(
                new Query<WmsPurchaseDetailEntity>().getPage(params),
                wmsPurchaseDetailEntityQueryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public List<WmsPurchaseDetailEntity> listDetailWithPurchaseId(WmsPurchaseEntity item) {

        return list(new QueryWrapper<WmsPurchaseDetailEntity>().eq("purchase_id", item));
    }

}