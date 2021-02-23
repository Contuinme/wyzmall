package cn.wyz.wyzmall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.wyz.common.utils.PageUtils;
import cn.wyz.wyzmall.ware.entity.UndoLogEntity;

import java.util.Map;

/**
 * 
 *
 * @author wyz
 * @email 806543499@qq.com
 * @date 2021-02-23 08:58:01
 */
public interface UndoLogService extends IService<UndoLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

