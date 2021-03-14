package cn.wyz.wyzmall.coupon.service.impl;

import cn.wyz.common.to.MemberPrice;
import cn.wyz.common.to.SkuReductionTo;
import cn.wyz.wyzmall.coupon.entity.SmsMemberPriceEntity;
import cn.wyz.wyzmall.coupon.entity.SmsSkuLadderEntity;
import cn.wyz.wyzmall.coupon.service.SmsMemberPriceService;
import cn.wyz.wyzmall.coupon.service.SmsSkuLadderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.wyz.common.utils.PageUtils;
import cn.wyz.common.utils.Query;

import cn.wyz.wyzmall.coupon.dao.SmsSkuFullReductionDao;
import cn.wyz.wyzmall.coupon.entity.SmsSkuFullReductionEntity;
import cn.wyz.wyzmall.coupon.service.SmsSkuFullReductionService;
import org.springframework.transaction.annotation.Transactional;


@Service("smsSkuFullReductionService")
public class SmsSkuFullReductionServiceImpl extends ServiceImpl<SmsSkuFullReductionDao, SmsSkuFullReductionEntity> implements SmsSkuFullReductionService {

    @Autowired
    private SmsSkuLadderService skuLadderService;

    @Autowired
    private SmsMemberPriceService smsMemberPriceService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SmsSkuFullReductionEntity> page = this.page(
                new Query<SmsSkuFullReductionEntity>().getPage(params),
                new QueryWrapper<SmsSkuFullReductionEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void saveSkuReduction(SkuReductionTo skuReductionTo) {
        SmsSkuLadderEntity smsSkuLadderEntity = new SmsSkuLadderEntity();
        BeanUtils.copyProperties(skuReductionTo, smsSkuLadderEntity);
        if(smsSkuLadderEntity.getFullCount() > 0) {
            skuLadderService.save(smsSkuLadderEntity);
        }

        SmsSkuFullReductionEntity smsSkuFullReductionEntity = new SmsSkuFullReductionEntity();
        BeanUtils.copyProperties(skuReductionTo, smsSkuFullReductionEntity);
        if(smsSkuFullReductionEntity.getFullPrice().compareTo(BigDecimal.ZERO) > 0) {
            save(smsSkuFullReductionEntity);
        }

        List<MemberPrice> memberPrice = skuReductionTo.getMemberPrice();
        List<SmsMemberPriceEntity> collect = memberPrice.stream().map(item -> {
            SmsMemberPriceEntity smsMemberPriceEntity = new SmsMemberPriceEntity();
            smsMemberPriceEntity.setSkuId(skuReductionTo.getSkuId());
            smsMemberPriceEntity.setMemberLevelId(item.getId());
            smsMemberPriceEntity.setMemberLevelName(item.getName());
            smsMemberPriceEntity.setMemberPrice(item.getPrice());
            smsMemberPriceEntity.setAddOther(1);
            return smsMemberPriceEntity;
        }).filter(item -> item.getMemberPrice().compareTo(BigDecimal.ZERO) > 0)
          .collect(Collectors.toList());

        smsMemberPriceService.saveBatch(collect);
    }

}