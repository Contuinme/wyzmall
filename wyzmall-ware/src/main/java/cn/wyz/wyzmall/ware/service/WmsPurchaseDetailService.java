package cn.wyz.wyzmall.ware.service;

import cn.wyz.wyzmall.ware.entity.WmsPurchaseEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.wyz.common.utils.PageUtils;
import cn.wyz.wyzmall.ware.entity.WmsPurchaseDetailEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author wyz
 * @email 806543499@qq.com
 * @date 2021-02-23 08:58:02
 */
public interface WmsPurchaseDetailService extends IService<WmsPurchaseDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<WmsPurchaseDetailEntity> listDetailWithPurchaseId(WmsPurchaseEntity item);
}

