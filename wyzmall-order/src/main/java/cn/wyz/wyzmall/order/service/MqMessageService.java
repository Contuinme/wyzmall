package cn.wyz.wyzmall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.wyz.common.utils.PageUtils;
import cn.wyz.wyzmall.order.entity.MqMessageEntity;

import java.util.Map;

/**
 * 
 *
 * @author wyz
 * @email 806543499@qq.com
 * @date 2021-02-23 08:51:14
 */
public interface MqMessageService extends IService<MqMessageEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

