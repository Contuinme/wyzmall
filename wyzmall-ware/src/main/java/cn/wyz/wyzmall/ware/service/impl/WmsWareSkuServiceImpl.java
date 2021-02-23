package cn.wyz.wyzmall.ware.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.wyz.common.utils.PageUtils;
import cn.wyz.common.utils.Query;

import cn.wyz.wyzmall.ware.dao.WmsWareSkuDao;
import cn.wyz.wyzmall.ware.entity.WmsWareSkuEntity;
import cn.wyz.wyzmall.ware.service.WmsWareSkuService;


@Service("wmsWareSkuService")
public class WmsWareSkuServiceImpl extends ServiceImpl<WmsWareSkuDao, WmsWareSkuEntity> implements WmsWareSkuService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WmsWareSkuEntity> page = this.page(
                new Query<WmsWareSkuEntity>().getPage(params),
                new QueryWrapper<WmsWareSkuEntity>()
        );

        return new PageUtils(page);
    }

}