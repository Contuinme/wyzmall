package cn.wyz.wyzmall.search.service.impl;

import cn.wyz.common.to.es.SkuEsModel;
import cn.wyz.wyzmall.search.config.ESConfig;
import cn.wyz.wyzmall.search.constant.EsConstant;
import cn.wyz.wyzmall.search.service.ProductSaveService;
import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductSaveServiceImpl implements ProductSaveService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public Boolean productStatusUp(List<SkuEsModel> skuEsModelList) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        skuEsModelList.forEach(skuEsModel -> {
            IndexRequest indexRequest = new IndexRequest(EsConstant.PRODUCT_INDEX);
            indexRequest.id(skuEsModel.getSkuId().toString());
            String json = JSON.toJSONString(skuEsModel);
            indexRequest.source(json, XContentType.JSON);

            bulkRequest.add(indexRequest);
        });
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, ESConfig.COMMON_OPTIONS);

        boolean failure = bulk.hasFailures();
        List<String> collect = Arrays.stream(bulk.getItems()).map(BulkItemResponse::getId).collect(Collectors.toList());
        return !failure;
    }
}
