package cn.wyz.wyzmall.ware.service.impl;

import cn.wyz.common.constant.WareConstant;
import cn.wyz.wyzmall.ware.entity.WmsPurchaseDetailEntity;
import cn.wyz.wyzmall.ware.service.WmsPurchaseDetailService;
import cn.wyz.wyzmall.ware.service.WmsWareSkuService;
import cn.wyz.wyzmall.ware.vo.MergeVo;
import cn.wyz.wyzmall.ware.vo.PurchaseDoneVo;
import cn.wyz.wyzmall.ware.vo.PurchaseItemDoneVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.wyz.common.utils.PageUtils;
import cn.wyz.common.utils.Query;

import cn.wyz.wyzmall.ware.dao.WmsPurchaseDao;
import cn.wyz.wyzmall.ware.entity.WmsPurchaseEntity;
import cn.wyz.wyzmall.ware.service.WmsPurchaseService;
import org.springframework.transaction.annotation.Transactional;


@Service("wmsPurchaseService")
public class WmsPurchaseServiceImpl extends ServiceImpl<WmsPurchaseDao, WmsPurchaseEntity> implements WmsPurchaseService {

    @Autowired
    private WmsPurchaseDetailService wmsPurchaseDetailService;

    @Autowired
    private WmsWareSkuService wmsWareSkuService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WmsPurchaseEntity> page = this.page(
                new Query<WmsPurchaseEntity>().getPage(params),
                new QueryWrapper<WmsPurchaseEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageUnreceivePurchase(Map<String, Object> params) {
        IPage<WmsPurchaseEntity> page = this.page(
                new Query<WmsPurchaseEntity>().getPage(params),
                new QueryWrapper<WmsPurchaseEntity>().eq("status", 0).or().eq("status", 1)
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void mergePurchase(MergeVo mergeVo) {
        Long purchaseId = mergeVo.getPurchaseId();
        if(purchaseId == null) {
            WmsPurchaseEntity wmsPurchaseEntity = new WmsPurchaseEntity();

            wmsPurchaseEntity.setCreateTime(new Date());
            wmsPurchaseEntity.setUpdateTime(new Date());
            wmsPurchaseEntity.setStatus(WareConstant.PurchaseStatusEnum.CREATE.getCode());
            save(wmsPurchaseEntity);
            purchaseId = wmsPurchaseEntity.getId();
        }

        //TODO        确认采购单状态为0，1才能合并


        List<Long> items = mergeVo.getItems();
        Long finalPurchaseId = purchaseId;
        List<WmsPurchaseDetailEntity> collect = items.stream().map(id -> {
            WmsPurchaseDetailEntity wmsPurchaseDetailEntity = new WmsPurchaseDetailEntity();

            wmsPurchaseDetailEntity.setId(id);
            wmsPurchaseDetailEntity.setPurchaseId(finalPurchaseId);
            wmsPurchaseDetailEntity.setStatus(WareConstant.PurchaseDetailEnum.ASSIGNED.getCode());
            return wmsPurchaseDetailEntity;
        }).collect(Collectors.toList());

        wmsPurchaseDetailService.updateBatchById(collect);

        WmsPurchaseEntity wmsPurchaseEntity = new WmsPurchaseEntity();
        wmsPurchaseEntity.setId(purchaseId);
        wmsPurchaseEntity.setUpdateTime(new Date());
        updateById(wmsPurchaseEntity);
    }

    @Override
    public void received(List<Long> ids) {
        List<WmsPurchaseEntity> collect = ids.stream().map(this::getById)
                .filter(item -> item.getStatus().equals(WareConstant.PurchaseStatusEnum.CREATE.getCode()) || item.getStatus().equals(WareConstant.PurchaseStatusEnum.ASSIGNED.getCode()))
                .peek(item -> {
                    item.setStatus(WareConstant.PurchaseStatusEnum.RECEIVE.getCode());
                    item.setUpdateTime(new Date());
                })
                .collect(Collectors.toList());

        updateBatchById(collect);

        collect.forEach(item -> {
            List<WmsPurchaseDetailEntity> entities = wmsPurchaseDetailService.listDetailWithPurchaseId(item);
            List<WmsPurchaseDetailEntity> wmsPurchaseDetailEntities = entities.stream().map(entity -> {
                WmsPurchaseDetailEntity wmsPurchaseDetailEntity = new WmsPurchaseDetailEntity();
                wmsPurchaseDetailEntity.setId(entity.getId());
                wmsPurchaseDetailEntity.setStatus(WareConstant.PurchaseDetailEnum.BUYING.getCode());
                return entity;
            }).collect(Collectors.toList());
            wmsPurchaseDetailService.updateBatchById(wmsPurchaseDetailEntities);
        });
    }

    @Transactional
    @Override
    public void done(PurchaseDoneVo vo) {
        boolean flag = true;
        List<PurchaseItemDoneVo> items = vo.getItems();

        List<WmsPurchaseDetailEntity> updates = new ArrayList<>();
        for (PurchaseItemDoneVo item : items) {
            if(item.getStatus().equals(WareConstant.PurchaseDetailEnum.ERROR.getCode())) {
                flag = false;
            } else {
                WmsPurchaseDetailEntity entity = wmsPurchaseDetailService.getById(item.getItemId());
                wmsWareSkuService.addStock(entity.getSkuId(), entity.getWareId(), entity.getSkuNum());
            }
            WmsPurchaseDetailEntity wmsPurchaseDetailEntity = new WmsPurchaseDetailEntity();
            wmsPurchaseDetailEntity.setStatus(item.getStatus());
            wmsPurchaseDetailEntity.setId(item.getItemId());
            updates.add(wmsPurchaseDetailEntity);
        }
        wmsPurchaseDetailService.updateBatchById(updates);

        WmsPurchaseEntity wmsPurchaseEntity = new WmsPurchaseEntity();
        wmsPurchaseEntity.setId(vo.getId());
        wmsPurchaseEntity.setStatus(flag ? WareConstant.PurchaseStatusEnum.FINISH.getCode() : WareConstant.PurchaseStatusEnum.ERROR.getCode());
        wmsPurchaseEntity.setUpdateTime(new Date());
        updateById(wmsPurchaseEntity);


    }

}