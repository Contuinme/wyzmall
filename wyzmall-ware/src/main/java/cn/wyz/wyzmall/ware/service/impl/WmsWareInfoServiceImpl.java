package cn.wyz.wyzmall.ware.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.wyz.common.utils.PageUtils;
import cn.wyz.common.utils.Query;

import cn.wyz.wyzmall.ware.dao.WmsWareInfoDao;
import cn.wyz.wyzmall.ware.entity.WmsWareInfoEntity;
import cn.wyz.wyzmall.ware.service.WmsWareInfoService;
import org.springframework.util.StringUtils;


@Service("wmsWareInfoService")
public class WmsWareInfoServiceImpl extends ServiceImpl<WmsWareInfoDao, WmsWareInfoEntity> implements WmsWareInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<WmsWareInfoEntity> wmsWareInfoEntityQueryWrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if(StringUtils.hasLength(key)) {
            wmsWareInfoEntityQueryWrapper.eq("id", key)
                    .or().like("name", key)
                    .or().like("address", key)
                    .or().like("areacode", key);
        }
        IPage<WmsWareInfoEntity> page = this.page(
                new Query<WmsWareInfoEntity>().getPage(params),
                wmsWareInfoEntityQueryWrapper
        );

        return new PageUtils(page);
    }

}