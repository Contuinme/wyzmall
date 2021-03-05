package cn.wyz.wyzmall.product.service.impl;

import cn.wyz.wyzmall.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.wyz.common.utils.PageUtils;
import cn.wyz.common.utils.Query;

import cn.wyz.wyzmall.product.dao.CategoryDao;
import cn.wyz.wyzmall.product.entity.CategoryEntity;
import cn.wyz.wyzmall.product.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        //获得所有分类数据
        List<CategoryEntity> entities = baseMapper.selectList(null);
        //组装成树形结构返回
        List<CategoryEntity> collect = entities.stream().filter(categoryEntity -> categoryEntity.getParentCid().equals(0L))
                .map(categoryEntity -> {
                    categoryEntity.setChildren(getChildren(categoryEntity, entities));
                    return categoryEntity;
                })
                .sorted((menu1, menu2) -> {
                    return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
                })
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public void removeMenuByIds(List<Long> catIds) {
        //TODO 检查是否引用
        baseMapper.deleteBatchIds(catIds);
    }

    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> path = new ArrayList<>();
        findParentPath(catelogId, path);
        return  path.toArray(Long[]::new);
    }

    /*
        及联更新
     */
    @Transactional
    @Override
    public void updateCascade(CategoryEntity category) {
        updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());
    }

    private void findParentPath(Long catelogId,List<Long> path) {
        CategoryEntity byId = this.getById(catelogId);
        if(byId.getParentCid() != 0) {
            findParentPath(byId.getParentCid(), path);
        }
        path.add(catelogId);
    }

    private List<CategoryEntity> getChildren(CategoryEntity root, List<CategoryEntity> entities) {
        List<CategoryEntity> collect = entities.stream().filter(categoryEntity -> categoryEntity.getParentCid().equals(root.getCatId()))
                .map(categoryEntity -> {
                    categoryEntity.setChildren(getChildren(categoryEntity, entities));
                    return categoryEntity;
                })
                .sorted((menu1, menu2) -> {
                    return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
                })
                .collect(Collectors.toList());
        return collect;
    }

}