package cn.wyz.wyzmall.ware.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.wyz.wyzmall.ware.vo.MergeVo;
import cn.wyz.wyzmall.ware.vo.PurchaseDoneVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.wyz.wyzmall.ware.entity.WmsPurchaseEntity;
import cn.wyz.wyzmall.ware.service.WmsPurchaseService;
import cn.wyz.common.utils.PageUtils;
import cn.wyz.common.utils.R;



/**
 * 采购信息
 *
 * @author wyz
 * @email 806543499@qq.com
 * @date 2021-02-23 08:58:02
 */
@RestController
@RequestMapping("ware/wmspurchase")
public class WmsPurchaseController {

    @Autowired
    private WmsPurchaseService wmsPurchaseService;

    @PostMapping("/done")
    public R finish(@RequestBody PurchaseDoneVo vo) {
        wmsPurchaseService.done(vo);
        return R.ok();
    }

    @PostMapping("/received")
    public R received(@RequestBody List<Long> ids) {

        wmsPurchaseService.received(ids);
        return R.ok();
    }

    @PostMapping("/merge")
    public R merge(@RequestBody MergeVo mergeVo) {
        wmsPurchaseService.mergePurchase(mergeVo);
        return R.ok();
    }

    @RequestMapping("/unreceive/list")
    public R unreceiveList(@RequestParam Map<String, Object> params){
        PageUtils page = wmsPurchaseService.queryPageUnreceivePurchase(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = wmsPurchaseService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		WmsPurchaseEntity wmsPurchase = wmsPurchaseService.getById(id);

        return R.ok().put("wmsPurchase", wmsPurchase);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody WmsPurchaseEntity wmsPurchase){
        wmsPurchase.setCreateTime(new Date());
        wmsPurchase.setUpdateTime(new Date());
		wmsPurchaseService.save(wmsPurchase);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody WmsPurchaseEntity wmsPurchase){
		wmsPurchaseService.updateById(wmsPurchase);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		wmsPurchaseService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
