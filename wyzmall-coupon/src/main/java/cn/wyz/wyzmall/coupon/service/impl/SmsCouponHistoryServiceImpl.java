package cn.wyz.wyzmall.coupon.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.wyz.common.utils.PageUtils;
import cn.wyz.common.utils.Query;

import cn.wyz.wyzmall.coupon.dao.SmsCouponHistoryDao;
import cn.wyz.wyzmall.coupon.entity.SmsCouponHistoryEntity;
import cn.wyz.wyzmall.coupon.service.SmsCouponHistoryService;


@Service("smsCouponHistoryService")
public class SmsCouponHistoryServiceImpl extends ServiceImpl<SmsCouponHistoryDao, SmsCouponHistoryEntity> implements SmsCouponHistoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SmsCouponHistoryEntity> page = this.page(
                new Query<SmsCouponHistoryEntity>().getPage(params),
                new QueryWrapper<SmsCouponHistoryEntity>()
        );

        return new PageUtils(page);
    }

}