package cn.wyz.wyzmall.member.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.wyz.common.utils.PageUtils;
import cn.wyz.common.utils.Query;

import cn.wyz.wyzmall.member.dao.UmsMemberDao;
import cn.wyz.wyzmall.member.entity.UmsMemberEntity;
import cn.wyz.wyzmall.member.service.UmsMemberService;


@Service("umsMemberService")
public class UmsMemberServiceImpl extends ServiceImpl<UmsMemberDao, UmsMemberEntity> implements UmsMemberService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UmsMemberEntity> page = this.page(
                new Query<UmsMemberEntity>().getPage(params),
                new QueryWrapper<UmsMemberEntity>()
        );

        return new PageUtils(page);
    }

}