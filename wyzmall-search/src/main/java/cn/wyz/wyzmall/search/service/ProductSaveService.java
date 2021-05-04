package cn.wyz.wyzmall.search.service;


import cn.wyz.common.to.es.SkuEsModel;

import java.io.IOException;
import java.util.List;

public interface ProductSaveService {
    Boolean productStatusUp(List<SkuEsModel> skuEsModelList) throws IOException;
}
