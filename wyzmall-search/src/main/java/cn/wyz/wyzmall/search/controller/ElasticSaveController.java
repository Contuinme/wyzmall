package cn.wyz.wyzmall.search.controller;

import cn.wyz.common.exception.BizCodeException;
import cn.wyz.common.to.es.SkuEsModel;
import cn.wyz.common.utils.R;
import cn.wyz.wyzmall.search.service.ProductSaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/search/save")
public class ElasticSaveController {

    @Autowired
    private ProductSaveService productSaveService;

    @PostMapping("/product")
    public R productStatusUp(@RequestBody List<SkuEsModel> skuEsModelList) {
        Boolean success = false;
        try{
            success = productSaveService.productStatusUp(skuEsModelList);
        }catch (Exception e) {
            log.error("ElasticSaveController商品上架错误: {}", e);
        }

        if(!success) {
            return R.error(BizCodeException.PRODUCT_UP_EXCEPTION.getCode(),BizCodeException.PRODUCT_UP_EXCEPTION.getMsg());
        }

        return R.ok();
    }
}
