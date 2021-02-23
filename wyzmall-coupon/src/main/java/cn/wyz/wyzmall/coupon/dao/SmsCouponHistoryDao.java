package cn.wyz.wyzmall.coupon.dao;

import cn.wyz.wyzmall.coupon.entity.SmsCouponHistoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券领取历史记录
 * 
 * @author wyz
 * @email 806543499@qq.com
 * @date 2021-02-23 08:16:58
 */
@Mapper
public interface SmsCouponHistoryDao extends BaseMapper<SmsCouponHistoryEntity> {
	
}
