package cn.wyz.wyzmall.product.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.wyz.common.utils.PageUtils;
import cn.wyz.common.utils.Query;

import cn.wyz.wyzmall.product.dao.AttrGroupDao;
import cn.wyz.wyzmall.product.entity.AttrGroupEntity;
import cn.wyz.wyzmall.product.service.AttrGroupService;
import org.springframework.util.StringUtils;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catelogId) {
        IPage<AttrGroupEntity> page = null;
        if(catelogId.equals(0L)) {
            return this.queryPage(params);
        } else {
            String key = (String) params.get("key");
            QueryWrapper<AttrGroupEntity> queryWrapper = new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId);
            if(StringUtils.hasLength(key)) {
                queryWrapper.and((wrapper -> wrapper.eq("attr_group_id", key).or().like("attr_group_name", key)));
            }
            page = this.page(new Query<AttrGroupEntity>().getPage(params), queryWrapper);
        }
        return new PageUtils(page);
    }

}