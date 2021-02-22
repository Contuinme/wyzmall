package cn.wyz.wyzmall.product;

import cn.wyz.wyzmall.product.entity.BrandEntity;
import cn.wyz.wyzmall.product.service.BrandService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WyzmallProductApplicationTests {

    @Autowired
    private BrandService brandService;

    @Test
    void contextLoads() {
        BrandEntity res = brandService.getOne(new QueryWrapper<BrandEntity>().eq("brand_id", "1"));
        System.out.println(res);
    }

}
