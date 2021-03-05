package cn.wyz.wyzmall.product;

import cn.wyz.wyzmall.product.entity.BrandEntity;
import cn.wyz.wyzmall.product.service.BrandService;
import cn.wyz.wyzmall.product.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class WyzmallProductApplicationTests {

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;

    @Test
    public void testFindPath() {
        Long[] catelogPath = categoryService.findCatelogPath(225L);
        System.out.println(Arrays.asList(catelogPath));
    }

    @Test
    void contextLoads() {
        BrandEntity res = brandService.getOne(new QueryWrapper<BrandEntity>().eq("brand_id", "1"));
        System.out.println(res);
    }

}
