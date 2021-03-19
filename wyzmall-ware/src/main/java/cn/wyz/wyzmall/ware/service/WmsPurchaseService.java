package cn.wyz.wyzmall.ware.service;

import cn.wyz.wyzmall.ware.vo.MergeVo;
import cn.wyz.wyzmall.ware.vo.PurchaseDoneVo;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.wyz.common.utils.PageUtils;
import cn.wyz.wyzmall.ware.entity.WmsPurchaseEntity;

import java.util.List;
import java.util.Map;

/**
 * 采购信息
 *
 * @author wyz
 * @email 806543499@qq.com
 * @date 2021-02-23 08:58:02
 */
public interface WmsPurchaseService extends IService<WmsPurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageUnreceivePurchase(Map<String, Object> params);

    void mergePurchase(MergeVo mergeVo);

    void received(List<Long> ids);

    void done(PurchaseDoneVo vo);
}

