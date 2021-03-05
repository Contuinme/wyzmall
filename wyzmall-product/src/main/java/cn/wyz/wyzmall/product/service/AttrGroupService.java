package cn.wyz.wyzmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.wyz.common.utils.PageUtils;
import cn.wyz.wyzmall.product.entity.AttrGroupEntity;

import java.util.Map;

/**
 * 属性分组
 *
 * @author wyz
 * @email 806543499@qq.com
 * @date 2021-02-23 00:12:53
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPage(Map<String, Object> params, Long catelogId);

}

