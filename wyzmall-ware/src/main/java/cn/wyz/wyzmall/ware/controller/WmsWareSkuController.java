package cn.wyz.wyzmall.ware.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import cn.wyz.common.vo.SkuHasStockVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.wyz.wyzmall.ware.entity.WmsWareSkuEntity;
import cn.wyz.wyzmall.ware.service.WmsWareSkuService;
import cn.wyz.common.utils.PageUtils;
import cn.wyz.common.utils.R;



/**
 * 商品库存
 *
 * @author wyz
 * @email 806543499@qq.com
 * @date 2021-02-23 08:58:01
 */
@RestController
@RequestMapping("ware/wmswaresku")
public class WmsWareSkuController {
    @Autowired
    private WmsWareSkuService wmsWareSkuService;

    @PostMapping("/hasstack")
    public R getSkusHasStock(@RequestBody List<Long> skuIds) {
        List<SkuHasStockVo> skuHasStockVoList = wmsWareSkuService.getSkusHasStock(skuIds);
        return R.ok().setData(skuHasStockVoList);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = wmsWareSkuService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		WmsWareSkuEntity wmsWareSku = wmsWareSkuService.getById(id);

        return R.ok().put("wmsWareSku", wmsWareSku);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody WmsWareSkuEntity wmsWareSku){
		wmsWareSkuService.save(wmsWareSku);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody WmsWareSkuEntity wmsWareSku){
		wmsWareSkuService.updateById(wmsWareSku);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		wmsWareSkuService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
