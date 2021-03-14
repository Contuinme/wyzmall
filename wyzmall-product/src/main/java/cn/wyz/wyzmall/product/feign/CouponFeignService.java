package cn.wyz.wyzmall.product.feign;

import cn.wyz.common.to.SkuReductionTo;
import cn.wyz.common.to.SpuBoundTo;
import cn.wyz.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("wyzmall-coupon")
public interface CouponFeignService {

    @PostMapping("/coupon/smsspubounds/save")
    R saveSpuBounds(@RequestBody SpuBoundTo spuBoundTo);

    @PostMapping("/coupon/smsskufullreduction/saveinfo")
    R saveSkuReduction(@RequestBody SkuReductionTo skuReductionTo);
}
