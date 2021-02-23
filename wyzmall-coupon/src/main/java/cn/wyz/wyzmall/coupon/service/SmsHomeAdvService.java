package cn.wyz.wyzmall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.wyz.common.utils.PageUtils;
import cn.wyz.wyzmall.coupon.entity.SmsHomeAdvEntity;

import java.util.Map;

/**
 * 首页轮播广告
 *
 * @author wyz
 * @email 806543499@qq.com
 * @date 2021-02-23 08:16:58
 */
public interface SmsHomeAdvService extends IService<SmsHomeAdvEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

