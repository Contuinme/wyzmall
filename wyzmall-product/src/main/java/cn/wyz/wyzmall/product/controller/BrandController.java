package cn.wyz.wyzmall.product.controller;

import java.util.Arrays;
import java.util.Map;

import cn.wyz.common.valid.AddGroup;
import cn.wyz.common.valid.UpdateGroup;
import cn.wyz.common.valid.UpdateStatusGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.wyz.wyzmall.product.entity.BrandEntity;
import cn.wyz.wyzmall.product.service.BrandService;
import cn.wyz.common.utils.PageUtils;
import cn.wyz.common.utils.R;

/**
 * 品牌
 *
 * @author wyz
 * @email 806543499@qq.com
 * @date 2021-02-23 00:12:53
 */
@RestController
@RequestMapping("product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = brandService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{brandId}")
    public R info(@PathVariable("brandId") Long brandId){
		BrandEntity brand = brandService.getById(brandId);

        return R.ok().put("brand", brand);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@Validated({AddGroup.class}) @RequestBody BrandEntity brand/*, BindingResult bindingResult*/){
//        if(bindingResult.hasErrors()) {
//            Map<String, String> map = new HashMap<>();
//            bindingResult.getFieldErrors().forEach(item -> {
//                map.put(item.getField(), item.getDefaultMessage());
//            });
//            return R.error(400, "数据不合法").put("data", map);
//        } else {
//
//        }
        brandService.save(brand);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@Validated({UpdateGroup.class}) @RequestBody BrandEntity brand){
		brandService.updateDetail(brand);

        return R.ok();
    }

    /**
     * 修改显示状态信息
     */
    @RequestMapping("/update/status")
    public R updateStatus(@Validated({UpdateStatusGroup.class}) @RequestBody BrandEntity brand){
        brandService.updateById(brand);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] brandIds){
		brandService.removeByIds(Arrays.asList(brandIds));

        return R.ok();
    }

}
