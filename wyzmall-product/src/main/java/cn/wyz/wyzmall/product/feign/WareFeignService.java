package cn.wyz.wyzmall.product.feign;

import cn.wyz.common.utils.R;
import cn.wyz.common.vo.SkuHasStockVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("wyzmall-ware")
public interface WareFeignService {

    @PostMapping("/ware/wmswaresku/hasstack")
    R getSkusHasStock(@RequestBody List<Long> skuIds);
}
