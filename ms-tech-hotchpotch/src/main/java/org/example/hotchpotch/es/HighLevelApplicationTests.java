package org.example.hotchpotch.es;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.client.indices.*;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.AvgAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.example.es.annotations.ESField;
import org.example.es.enums.ESFieldEnum;
import org.example.es.pojo.HClientStudent;
import org.example.es.pojo.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

@SpringBootTest
public class HighLevelApplicationTests {

    /*
    @BeforeEach     // 在测试类中每个操作运行前运行的方法
    void setUp() {
        HttpHost host = HttpHost.create("http://localhost:9200");
        RestClientBuilder builder = RestClient.builder(host);
        client = new RestHighLevelClient(builder);
    }

    @AfterEach      // 在测试类中每个操作运行后运行的方法
    void tearDown() throws IOException {
        client.close();
    }

    private RestHighLevelClient client;
     */

    @Resource
    private RestHighLevelClient client;

    /**
     * 创建索引
     * CreateIndexRequest
     *
     */
    @Test
    void testCreateIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("student_index2");

        // 分片参数
        request.settings(Settings.builder()
                // 分片数
                .put("index.number_of_shards", 1)
                // 副本数
                .put("index.number_of_replicas", 1)
        );
        /* 构建 mapping 并 create
        String json = "";       // 索引的 mapping
        request.source(json, XContentType.JSON);
        client.indices().create(request, RequestOptions.DEFAULT);
         */

        // 创建索引操作客户端
        IndicesClient indices = client.indices();
        // 创建响应结果
        CreateIndexResponse response = indices.create(request, RequestOptions.DEFAULT);
        // 获取响应值
        boolean acknowledged = response.isAcknowledged();
        System.out.println("acknowledged = " + acknowledged);       // true
    }

    /**
     * 查询索引库
     * GetIndexRequest
     *
     */
    @Test
    void testGetIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest("student_index2");

        GetIndexResponse response = client.indices().get(request, RequestOptions.DEFAULT);

        System.out.println("getIndexResponse = " + response);           // org.elasticsearch.client.indices.GetIndexResponse@2a259f6f
        String[] indices = response.getIndices();
        if (Arrays.asList(indices).contains("student_index2"))
            System.out.println("Index 'student_index2' exists.");       // 输出
        else
            System.out.println("Index 'student_index2' does not exist.");

    }

    /**
     * 删除索引
     * DeleteIndexRequest
     *
     */
    @Test
    void testDeleteIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("student_index2");
        AcknowledgedResponse delete = client.indices().delete(request, RequestOptions.DEFAULT);
        boolean acknowledged = delete.isAcknowledged();
        System.out.println("acknowledged = " + acknowledged);       // true
    }

    /**
     * 创建映射 - 代码显示构建
     * PutMappingRequest
     *
     */
    @Test
    void testPutMapping() throws IOException {
        PutMappingRequest request = new PutMappingRequest("student_index2");
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                .startObject("properties")
                .startObject("address")
                .field("type", "text")
                .field("analyzer", "ik_max_word")
                .endObject()
                .startObject("username")
                .field("type", "keyword")
                .endObject()
                .startObject("phone")
                .field("type", "text")
                .field("analyzer", "ik_max_word")
                .endObject()
                .endObject()
                .endObject();

        PutMappingRequest source = request.source(builder);

        AcknowledgedResponse response = client.indices().putMapping(source, RequestOptions.DEFAULT);
        boolean acknowledged = response.isAcknowledged();
        System.out.println("acknowledged = " + acknowledged);
    }

    // 实体类映射关系 方法实现
    private XContentBuilder generateBuilder(Class clazz) {
        XContentBuilder builder = null;
        try {
            builder = XContentFactory.jsonBuilder();
            builder.startObject();
            builder.startObject("properties");
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field f : declaredFields) {
                if (f.isAnnotationPresent(ESField.class)) {
                    // 获取注解
                    ESField declaredAnnotation = f.getDeclaredAnnotation(ESField.class);
                    if (declaredAnnotation.type() == ESFieldEnum.OBJECT) {
                        // 获取当前类的对象-- Action
                        Class<?> type = f.getType();
                        Field[] df2 = type.getDeclaredFields();
                        builder.startObject(f.getName());
                        builder.startObject("properties");
                        // 遍历该对象中的所有属性
                        for (Field f2 : df2) {
                            if (f2.isAnnotationPresent(ESField.class)) {
                                // 获取注解
                                ESField declaredAnnotation2 = f2.getDeclaredAnnotation(ESField.class);
                                builder.startObject(f2.getName());
                                builder.field("type", declaredAnnotation2.type().getType());
                                // keyword不需要分词
                                if (declaredAnnotation2.type() == ESFieldEnum.TEXT) {
                                    builder.field("analyzer", declaredAnnotation2.analyzer().getType());
                                }
                                builder.endObject();
                            }
                        }
                        builder.endObject();

                    } else {
                        builder.startObject(f.getName());
                        builder.field("type", declaredAnnotation.type().getType());
                        // keyword不需要分词
                        if (declaredAnnotation.type() == ESFieldEnum.TEXT) {
                            builder.field("analyzer", declaredAnnotation.analyzer().getType());
                        }
                    }
                    builder.endObject();
                }
            }
            // 对应property
            builder.endObject();
            builder.endObject();
        } catch (IOException e) {
            System.out.println("【ES操作】 组装映射字段语句异常");
        }

        return builder;
    }

    /**
     * 创建映射2 - 实体类映射
     *
     */
    @Test
    void testCreateIndexAndMapping() throws IOException {
        // 初始化 创建索引请求，指定索引名称
        CreateIndexRequest request = new CreateIndexRequest("student_index3");

        // 设置 settings
        request.settings(Settings.builder()
                .put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2)
        );

        // 设置 mapping - 自定义实体类 mapping 映射方法: generateBuilder
        request.mapping(generateBuilder(HClientStudent.class));

        // 发送并响应创建索引请求
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);

        boolean acknowledged = response.isAcknowledged();
        boolean shardsAcknowledged = response.isShardsAcknowledged();

        System.out.println("acknowledged: " + acknowledged + "\nshardsAcknowledged: " + shardsAcknowledged);    // true true
    }

    /**
     * 查看映射
     * GetMappingsRequest
     *
     */
    @Test
    void testGetMapping() throws IOException {
        GetMappingsRequest request = new GetMappingsRequest();
        request.indices("student_index3");

        GetMappingsResponse response = client.indices().getMapping(request, RequestOptions.DEFAULT);
        Map<String, MappingMetaData> mappings = response.mappings();

        MappingMetaData metaData = mappings.get("student_index3");

        System.out.println("student_index3 mapping: " + metaData.getSourceAsMap().toString());
    }

    /**
     * 添加文档
     * IndexRequest
     *
     */
    @Test
    void testCreateDoc() throws IOException {
        HClientStudent student = new HClientStudent("1", "小吴", "浙江省杭州市", "110");

        IndexRequest request = new IndexRequest("student_index3").id(student.getId());

        // 将 student(类的实例对象) 转化为 JSON 字符串，通过 source 添加到 request 中
        // 方式一：fastjson 库 实现
        String jsonStr = JSON.toJSONString(student);
        /* 方式二：jackson 库
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writeValueAsString(student);
         */
        /* 方式三：Gson 库
        Gson gson = new Gson();
        String jsonStr = gson.toJson(student);
         */

        request.source(jsonStr, XContentType.JSON);

        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        RestStatus status = response.status();
        System.out.println("status: " + status);    // status: CREATED
    }

    /**
     * 批量添加文档
     * BulkRequest
     *
     */
    @Test
    void testCreateDocBulk() throws IOException {
        List<HClientStudent> list = new ArrayList<>();
        list.add(new HClientStudent("22", "小金", "浙江省宁波市", "520"));
        list.add(new HClientStudent("33", "小何", "浙江省温州市", "333"));
        list.add(new HClientStudent("44", "小李", "江西省南昌市", "119"));
        BulkRequest bulk = new BulkRequest();

        for (HClientStudent student : list) {
            IndexRequest request = new IndexRequest("student_index3").id(student.getId());
            String json = JSON.toJSONString(student);
            request.source(json, XContentType.JSON);
            bulk.add(request);
        }
        BulkResponse response = client.bulk(bulk, RequestOptions.DEFAULT);
        RestStatus status = response.status();
        System.out.println("status: " + status);    // status: OK
    }

    /**
     * 根据 id 删除文档
     * DeleteRequest
     *
     */
    @Test
    void testDeleteDocById() throws IOException {
//        DeleteRequest request = new DeleteRequest("student_index3", "1");
        DeleteRequest request = new DeleteRequest("student_index3");
        request.id("1");

        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        RestStatus status = response.status();
        System.out.println("status: " + status);    // status: OK
    }

    /**
     * 根据 id 查询文档
     * GetRequest
     *
     */
    @Test
    void testGetDocById() throws IOException {
//        GetRequest request = new GetRequest("student_index3", "22");
        GetRequest request = new GetRequest("student_index3");
        request.id("22");

        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        String sourceAsString = response.getSourceAsString();

        System.out.println("source str: " + sourceAsString);
        // source str: {"address":"浙江省宁波市","id":"22","phone":"520","username":"小金"}
    }

    /**
     * 修改文档
     * UpdateRequest .doc
     *
     */
    @Test
    void testUpdateDoc() throws IOException {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("address", "浙江省杭州市");
        jsonMap.put("phone", "520520");

        UpdateRequest request = new UpdateRequest("student_index3", "22");
        request.doc(jsonMap);

        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
        RestStatus status = response.status();
        System.out.println("status: " + status);    // status: OK
    }

    /**
     * 查询全部 match_all
     * SearchRequest
     *
     */
    @Test
    void testMatchAll() throws IOException {
        //创建搜索对象，入参可以为多个索引库参数
        SearchRequest searchRequest = new SearchRequest("student_index3");
        //创建查询构造器
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        //设置查询构造器
        searchRequest.source(searchSourceBuilder);

        // 获取结果集
        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] hits = search.getHits().getHits();
        //遍历每一条记录
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();

//            HClientStudent student = JSON.parseObject(sourceAsString, HClientStudent.class);
            System.out.println("sourceAsString = " + sourceAsString);
//            System.out.println("student = " + student);
        }
        /* output
        sourceAsString = {"address":"浙江省杭州市","id":"22","phone":"520520","username":"小金"}
        sourceAsString = {"address":"江西省南昌市","id":"44","phone":"119","username":"小李"}
        sourceAsString = {"address":"浙江省温州市","id":"33","phone":"333","username":"小何"}
         */
    }

    /**
     * 条件查询 match
     * SearchRequest
     *
     */
    @Test
    void testMatchSearch() throws IOException {
        SearchRequest request = new SearchRequest("student_index3");

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchQuery("address", "浙江"));
        request.source(builder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();

            HClientStudent student = JSON.parseObject(sourceAsString, HClientStudent.class);
            System.out.println("student: " + student);
        }
        /* output
            student: HClientStudent(id=22, username=小金, address=浙江省杭州市, phone=520520)
            student: HClientStudent(id=33, username=小何, address=浙江省温州市, phone=333)
         */
    }

    /**
     * 组合条件查询 bool match
     * BoolQueryBuilder
     *
     */
    @Test
    void testMultiMatchSearch() throws IOException {
        SearchRequest request = new SearchRequest("student_index3");

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchQuery("address", "浙江"))
                .must(QueryBuilders.matchQuery("phone", "333"));

        sourceBuilder.query(boolQueryBuilder);
        request.source(sourceBuilder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();

            HClientStudent student = JSON.parseObject(sourceAsString, HClientStudent.class);
            System.out.println("student: " + student);
        }
        /* output: (数字字符串，不分词，仅保存完整的数字字符串)
            student: HClientStudent(id=33, username=小何, address=浙江省温州市, phone=333)
         */
    }

    /**
     * 范围查询 range
     *
     */
    @Test
    void testRangeSearch() throws IOException {
        SearchRequest request = new SearchRequest("student_index3");

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.rangeQuery("phone").gt("100").lte("333"));
        request.source(builder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();

            HClientStudent student = JSON.parseObject(sourceAsString, HClientStudent.class);
            System.out.println("student: " + student);
        }
        /* output
            student: HClientStudent(id=44, username=小李, address=江西省南昌市, phone=119)
            student: HClientStudent(id=33, username=小何, address=浙江省温州市, phone=333)
         */
    }

    /**
     * 过滤查询 source
     *
     */
    @Test
    void testSourceSearch() throws IOException {
        SearchRequest request = new SearchRequest("student_index3");

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchAllQuery());
//        searchSourceBuilder.fetchField("phone");
        String[] includes = {"id", "username"};
        String[] excludes = {"address", "phone"};
        builder.fetchSource(includes, excludes);
        request.source(builder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();

//            HClientStudent student = JSON.parseObject(sourceAsString, HClientStudent.class);
            System.out.println("sourceAsString: " + sourceAsString);
        }
        /* output
            sourceAsString: {"id":"22","username":"小金"}
            sourceAsString: {"id":"44","username":"小李"}
            sourceAsString: {"id":"33","username":"小何"}
         */
    }

    /**
     * 排序 sort
     *
     */
    @Test
    void testSortSearch() throws IOException {
        SearchRequest request = new SearchRequest("student_index3");

        SearchSourceBuilder builder = new SearchSourceBuilder();
        // 查询
        builder.query(QueryBuilders.matchAllQuery());

        // 排序
        builder.sort(new FieldSortBuilder("username").order(SortOrder.ASC));

        // 过滤
//        searchSourceBuilder.fetchField("phone");
        String[] includes = {"id", "username", "address"};
        String[] excludes = {"phone"};
        builder.fetchSource(includes, excludes);

        request.source(builder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        for (SearchHit hit : hits) {

            String sourceAsString = hit.getSourceAsString();
            System.out.println("sourceAsString: " + sourceAsString);
        }
        /* output
            sourceAsString: {"address":"浙江省温州市","id":"33","username":"小何"}
            sourceAsString: {"address":"江西省南昌市","id":"44","username":"小李"}
            sourceAsString: {"address":"浙江省杭州市","id":"22","username":"小金"}
         */
    }

    /**
     * 分页查询 from size
     *
     */
    @Test
    void testPageSearch() throws IOException {
        SearchRequest request = new SearchRequest("student_index3");

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchAllQuery());

        // 添加分页
        int page = 1;   // 页码从 0 开始
        int size = 2;   // 页大小
        int start = page * size;
        // 配置分页
        builder.from(start);
        builder.size(size);

        request.source(builder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        for (SearchHit hit : hits) {

            String sourceAsString = hit.getSourceAsString();
            System.out.println("sourceAsString: " + sourceAsString);
        }
        /* output       共 3 条数据
            sourceAsString: {"address":"浙江省温州市","id":"33","phone":"333","username":"小何"}
         */
    }

    /**
     * 聚合查询
     * aggs 之 度量（metrics）
     *
     */
    @Test
    void testAggMetricsSearch() throws IOException {
        SearchRequest request = new SearchRequest("student_index3");

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchAllQuery());

        // count 统计
        builder.aggregation(AggregationBuilders.count("countUsername").field("username"));

        request.source(builder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        System.out.println("Agg: " + response.getAggregations().getAsMap());
        // Agg: {countUsername=org.elasticsearch.search.aggregations.metrics.ParsedValueCount@70ed902a}

        SearchHits hits = response.getHits();
        for (SearchHit hit : hits) {

            String sourceAsString = hit.getSourceAsString();
            System.out.println("sourceAsString: " + sourceAsString);
        }
        /* output
            sourceAsString: {"address":"浙江省杭州市","id":"22","phone":"520520","username":"小金"}
            sourceAsString: {"address":"江西省南昌市","id":"44","phone":"119","username":"小李"}
            sourceAsString: {"address":"浙江省温州市","id":"33","phone":"333","username":"小何"}
         */
    }

    /**
     * 聚合查询
     * aggs 之 桶 bucket
     *
     * 尚未测试
     *
     */
    @Test
    void testAggBucketSearch() throws IOException {
        // 1、创建查询请求，规定查询的索引
        SearchRequest request = new SearchRequest("student_index3");

        // 2、创建条件构造
        SearchSourceBuilder builder = new SearchSourceBuilder();
        // 3、构造条件
        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        builder.query(matchAllQueryBuilder);
        //聚合年龄分布
        TermsAggregationBuilder ageAgg = AggregationBuilders.terms("genderCount").field("gender");
        builder.aggregation(ageAgg);
        //聚合平均年龄
        AvgAggregationBuilder balanceAvg = AggregationBuilders.avg("balanceAvg").field("balance");
        builder.aggregation(balanceAvg);

        //4、将构造好的条件放入请求中
        request.source(builder);

        //5、开始执行发送request请求
        SearchResponse searchResponse = client.search(request, RequestOptions.DEFAULT);

        //6、开始处理返回的数据
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit hit : hits) {

            String sourceAsString = hit.getSourceAsString();
            System.out.println("sourceAsString: " + sourceAsString);
        }

        Map<String, Aggregation> asMap = searchResponse.getAggregations().getAsMap();
        System.out.println("asMap = " + asMap);
    }
}
