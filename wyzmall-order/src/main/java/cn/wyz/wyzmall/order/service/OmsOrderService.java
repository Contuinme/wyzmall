package cn.wyz.wyzmall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.wyz.common.utils.PageUtils;
import cn.wyz.wyzmall.order.entity.OmsOrderEntity;

import java.util.Map;

/**
 * 订单
 *
 * @author wyz
 * @email 806543499@qq.com
 * @date 2021-02-23 08:51:15
 */
public interface OmsOrderService extends IService<OmsOrderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

