package cn.wyz.wyzmall.member.feign;

import cn.wyz.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("wyzmall-coupon")
public interface CouponFeignService {

    @RequestMapping("coupon/smscoupon/member/list")
    public R memberCoupons();
}
