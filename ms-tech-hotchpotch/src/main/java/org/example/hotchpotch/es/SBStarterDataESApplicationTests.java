package org.example.hotchpotch.es;

import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.example.es.dao.StudentESRepository;
import org.example.es.pojo.Student;
import org.example.es.pojo.record.Record;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;           // ES 7.6.2
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;
// import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;      // ES 7.17.9

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class SBStarterDataESApplicationTests {

    /* ES 7.17.9
    @Resource
    private ElasticsearchTemplate template;
     */

    // ES 7.6.2
    @Resource
    private ElasticsearchRestTemplate template;     // 内部具有 RestHighLevelClient 类型的成员变量

    @Resource
    private StudentESRepository studentESRepository;

    /* deprecated in 7.17
    @Resource
    private RestHighLevelClient client;
     */

    /**
     * 创建索引
     *
     */
    @Test
    void createIndex() {
        // 从 spring data es 4.0开始所有索引操作都在这个接口
        IndexOperations indexOperations = template.indexOps(Record.class);
        // 是否存在，存在则删除
        if (indexOperations.exists()) {
            indexOperations.delete();
            System.out.println("删除旧索引");
        }

        // 创建索引
        boolean flag = indexOperations.create();

        // createMapping 据实体类获取映射关系
        // putMapping    把映射关系添加到索引中
        Document mapping = indexOperations.createMapping(Record.class);
        indexOperations.putMapping(mapping);

        System.out.println("是否创建成功：" + flag);
    }

    /**
     * 删除索引
     *
     */
    @Test
    void deleteIndex() {
        IndexOperations indexOperations = template.indexOps(Student.class);

        boolean delete = indexOperations.delete();
        System.out.println("是否删除成功：" + delete);
    }

    /**
     * 新增单个文档
     *
     */
    @Test
    void insertDoc() {
        Student student = Student.builder()
                .id(11)
                .name("张三")
                .desc("华为手机")
                .data("230")
                .age(23)
                .build();

        Student insert = template.save(student);
        System.out.println("新增文档成功：" + insert);
    }

    /**
     * 批量新增文档
     *
     */
    @Test
    void batchInsertDoc() {
        List<Student> list = new ArrayList<>();
//        list.add(new Student(2,"李四","苹果手机","1",22));
//        list.add(new Student(3,"王五","oppo手机","2",24));
//        list.add(new Student(4,"赵六","vivo手机","3",25));
//        list.add(new Student(5,"田七","小米手机","4",26));
        list.add(new Student(1,"张三","111","1",22));
        list.add(new Student(11,"张三2号","222","11",22));
        list.add(new Student(111,"老山","张三小子","111",22));
        Iterable<Student> result = template.save(list);
//        studentESRepository.saveAll(list);
        System.out.println("批量新增：" + result);
    }

    /**
     * 根据主键删除文档
     *
     */
    @Test
    void deleteDocById() {
        int id = 9;
        String deleteDoc = template.delete(Integer.toString(id), Student.class);
//        studentESRepository.deleteById(id);
        System.out.println("删除id为 " + id + " 的文档：" + deleteDoc);
    }

    /**
     * 根据条件删除
     *
     */
    @Test
    void deleteDocByQuery() {
        // 基于查询字符串的 es 查询
//        QueryStringQueryBuilder queryBuilder = QueryBuilders.queryStringQuery("张三");
//        Query query = new NativeSearchQuery(queryBuilder);

        // 构建查询：基于键值对
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.termQuery("name", "张三"))
                .build();

        // 创建索引协调器
        IndexCoordinates index = IndexCoordinates.of("student_index");

        // 执行删除操作
        template.delete(query, Student.class, index);   // return void
//        System.out.println("Delete Query：" + query);
        System.out.println("Delete 张三 Doc");
    }

    /**
     * 全量修改
     *
     */
    @Test
    void updateTotal() {
        Student newStu = Student.builder()
                .id(66)
                .age(6)
                .data("666666")
                .desc("iphone and xiaomi")
                .name("老六")
                .build();

        Student save = template.save(newStu);
        System.out.println("update total: " + save);
    }

    /**
     * 部分修改
     *
     */
    @Test
    void updatePart() {
        // ctx.source 固定写法
        String script = "ctx._source.age = 29;ctx._source.desc = 'oppo and iphone'";
        int id = 3;
        UpdateQuery query = UpdateQuery.builder(Integer.toString(id)).withScript(script).build();
        IndexCoordinates index = IndexCoordinates.of("student_index");

        UpdateResponse update = template.update(query, index);

        System.out.println(update.getResult());
    }

    /**
     * 根据主键查询
     *
     */
    @Test
    void searchById() {
        Student stu = template.get("3", Student.class);
        System.out.println(stu);
    }

    /**
     * 模糊查询
     *
     */
    @Test
    void likeSearch() {
//        QueryStringQueryBuilder queryBuilder = QueryBuilders.queryStringQuery("张三");
//        FuzzyQueryBuilder queryBuilder = QueryBuilders.fuzzyQuery("name", "张三");
//
//        Query query = new NativeSearchQuery(queryBuilder);

        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.fuzzyQuery("name", "张三"))
                .build();

        SearchHits<Student> search = template.search(query, Student.class);
        System.out.println("search: " + search);
        List<SearchHit<Student>> searchHits = search.getSearchHits();
        System.out.println("searchHits: " + searchHits);
        List<Student> list = new ArrayList<>();
        searchHits.forEach(sh->{
            list.add(sh.getContent());
        });
        System.out.println("list: " + list);
    }

    /**
     * match_all 查询所有文档
     *
     */
    @Test
    void matchAllSearch() {
//        MatchAllQueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
//        Query query = new NativeSearchQuery(queryBuilder);

        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchAllQuery())
                .build();

        SearchHits<Student> search = template.search(query, Student.class);
        System.out.println("search: " + search);
        List<SearchHit<Student>> searchHits = search.getSearchHits();
        System.out.println("searchHits: " + searchHits);
        List<Student> list = new ArrayList<>();
        searchHits.forEach(sh->{
            list.add(sh.getContent());
        });
        System.out.println("list: " + list);
    }

    /**
     * match 查询
     *
     */
    @Test
    void matchSearch() {
//        Query query = new NativeSearchQuery(QueryBuilders.matchQuery("name", "张三"));

        /* 多条件查询 错误写法
        Query query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("name", "张三"))      // 失效，被下一行覆盖
                .withQuery(QueryBuilders.matchQuery("age", 22))          // 有效
                .withPageable(PageRequest.of(0, 10))
                .build();
         */

        /* BoolQueryBuilder 方式   -   实现多条件查询
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("name", "张三"))
                .must(QueryBuilders.matchQuery("age", 22));

        Query query = new NativeSearchQuery(queryBuilder);
         */

        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(
                        QueryBuilders.boolQuery()
                                .must(QueryBuilders.matchQuery("name", "张三"))
                                .must(QueryBuilders.matchQuery("age", 22))
                )
                .build();

        // match 会将查询条件 先分词 再查询；命中一个就返回
        NativeSearchQuery query1 = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("desc", "小米手机真的很棒！"))
                .build();

        SearchHits<Student> search = template.search(query1, Student.class);
        System.out.println("search: " + search);
        List<SearchHit<Student>> searchHits = search.getSearchHits();
        System.out.println("searchHits: " + searchHits);
        List<Student> list = new ArrayList<>();
        searchHits.forEach(sh->{
            list.add(sh.getContent());
        });
        System.out.println("list: " + list);
    }

    /**
     * match_phrase 查询文档
     *
     * 短语搜索是对条件不分词，但是文档中属性根据配置实体类时指定的分词类型进行分词。
     * 如果属性使用ik分词器，从分词后的索引数据中进行匹配。
     */
    @Test
    void matchPhraseSearch() {
        Query query = new NativeSearchQuery(QueryBuilders.matchPhraseQuery("desc", "and iphone"));

        SearchHits<Student> search = template.search(query, Student.class);
        System.out.println("search: " + search);
        List<SearchHit<Student>> searchHits = search.getSearchHits();
        System.out.println("searchHits: " + searchHits);
        List<Student> list = new ArrayList<>();
        searchHits.forEach(sh->{
            list.add(sh.getContent());
        });
        System.out.println("list: " + list);
    }

    /**
     * range 查询
     *
     */
    @Test
    void rangeSearch() {
        // 方式一
//        Query query = new NativeSearchQuery(QueryBuilders.rangeQuery("age").gte(24).lte(29));

        // 方式二
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.rangeQuery("age").gte(24).lte(29))
                .build();

        SearchHits<Student> search = template.search(query, Student.class);
        System.out.println("search: " + search);
        List<SearchHit<Student>> searchHits = search.getSearchHits();
        System.out.println("searchHits: " + searchHits);
        List<Student> list = new ArrayList<>();
        searchHits.forEach(sh->{
            list.add(sh.getContent());
        });
        System.out.println("list: " + list);
    }

    /**
     * 多条件查询
     *
     */
    @Test
    void multiQuerySearch() {

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        List<QueryBuilder> listQuery = new ArrayList<>();
        listQuery.add(QueryBuilders.matchQuery("name", "张三"));
        listQuery.add(QueryBuilders.matchQuery("age", 22));
        boolQueryBuilder.must().addAll(listQuery);      // must: 逻辑 "与"
//        boolQueryBuilder.should().addAll(listQuery);    // should: 逻辑 "或"

        BoolQueryBuilder boolQueryBuilder2 = QueryBuilders.boolQuery();
        List<QueryBuilder> listQuery2 = new ArrayList<>();
        listQuery2.add(QueryBuilders.matchQuery("name","李四"));
        listQuery2.add(QueryBuilders.matchQuery("age",23));
        boolQueryBuilder2.must().addAll(listQuery2);// 逻辑 与

        BoolQueryBuilder boolQueryBuilder3 = QueryBuilders.boolQuery();
        List<QueryBuilder> listQuery3 = new ArrayList<>();
        listQuery3.add(boolQueryBuilder);
        listQuery3.add(boolQueryBuilder2);
        boolQueryBuilder3.should().addAll(listQuery3);

        Query query = new NativeSearchQuery(boolQueryBuilder3);
        SearchHits<Student> search = template.search(query, Student.class);
        System.out.println("search: " + search);
        List<SearchHit<Student>> searchHits = search.getSearchHits();
        System.out.println("searchHits: " + searchHits);
        List<Student> list = new ArrayList<>();
        searchHits.forEach(sh->{
            list.add(sh.getContent());
        });
        System.out.println("list: " + list);
    }

    /**
     * 分页与排序
     *
     */
    @Test
    void pageSearch() {
        /* 方式一: Query
        Query query = new NativeSearchQuery(QueryBuilders.matchAllQuery());
        // 排序
        query.addSort(Sort.by(Sort.Direction.DESC, "age"));
        // 分页
        query.setPageable(PageRequest.of(1, 2));
         */

        // 方式二: NativeSearchQueryBuilder
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchAllQuery())
                .withSort(SortBuilders.fieldSort("age").order(SortOrder.DESC))
                .withPageable(PageRequest.of(1, 2))
                .build();

        SearchHits<Student> search = template.search(query, Student.class);
        System.out.println("search: " + search);
        List<SearchHit<Student>> searchHits = search.getSearchHits();
        System.out.println("searchHits: " + searchHits);
        List<Student> list = new ArrayList<>();
        searchHits.forEach(sh->{
            list.add(sh.getContent());
        });
        System.out.println("list: " + list);

        /* repository
        PageRequest pageRequest = PageRequest.of(1, 2);
        Page<Student> all = studentESRepository.findAll(pageRequest);
        all.forEach(System.out::println);
         */
    }

    /**
     * 病历的高级检索
     *
     */
    @Test
    void advancedSearchRecord() {
        // *************************************** 高级检索 输入 ***************************************
        int pageNum = 2;        // 分页：页码
        int pageSize = 5;       // 分页：页大小

        // 排序：默认对 入院时间 进行降序排序 - 最近入院的病历在前面

        List<String> inDiagList = new ArrayList<>();        // 入院诊断
        inDiagList.add("脑梗塞");
        inDiagList.add("颈动脉");
        List<String> outDiagList = new ArrayList<>();       // 出院诊断
        outDiagList.add("脑梗塞");
        List<String> outDrugList = new ArrayList<>();       // 出院带药
        outDrugList.add("阿司匹林片");

        String fromYear = "2017";                           // 时间范围：开始年份    --- 根据入院时间的年份
        String toYear = "2019";                             // 时间范围：结束年份
        List<String> hospitals = new ArrayList<>();     // 选择医院(三家)
        int smoke = 0;                                  // 个人史:吸烟史 - 0: 全部, 1: 有, 2: 无
        int drink = 0;                                  // 个人史:饮酒史 - 0: 全部, 1: 有, 2: 无
        int isTouchRadioactive = 0;                     // 个人史:毒物或放射性物质接触 - 0: 全部, 1: 有, 2: 无
        int hypertension = 0;                           // 现病史:高血压 - 0: 全部, 1: 有, 2: 无
        int hyperlipidemia = 0;                         // 现病史:高血脂 - 0: 全部, 1: 有, 2: 无
        int hyperglycemia = 0;                          // 现病史:高血糖 - 0: 全部, 1: 有, 2: 无
        int diabetes = 0;                               // 现病史:糖尿病 - 0: 全部, 1: 有, 2: 无
        String NIHSS = "";                              // 全部/筛选
        int inNi = -1;                                  // 入院时的 NIHSS
        int outNi = -1;                                 // 出院时的 NIHSS
        int ageRange = 0;                               // 0: 全部, 1: <18, 2: 18~40, 3: 41~65, 4: >65
        int isFirstDiag = 0;                            // 首次患病 - 0: 全部, 1: 有, 2: 无
        // ******************************************* end *******************************************

        // 构建 复合条件查询 构造器
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        List<QueryBuilder> queryList = new ArrayList<>();

        // 构造 入院诊断 关键词搜索，全部匹配则返回 - must
        for (String inDiag : inDiagList) {
            queryList.add(QueryBuilders.matchQuery("入院情况.入院诊断", inDiag));
        }

        // 构造 出院诊断 关键词搜索，全部匹配则返回 - must
        for (String outDiag : outDiagList) {
            queryList.add(QueryBuilders.matchQuery("出院情况.出院诊断", outDiag));
        }

        // 构造 出院诊断 关键词搜索，全部匹配则返回 - must
        for (String outDrug : outDrugList) {
            queryList.add(QueryBuilders.matchQuery("出院情况.出院带药", outDrug));
        }

        // 构造 入院时间 的年份区间搜索 - rangeQuery
        if (!fromYear.isEmpty() && !toYear.isEmpty()) {
            queryList.add(QueryBuilders.rangeQuery("入院情况.入院时间")
                    .gte(fromYear + "-01-01").lte(toYear + "-12-31"));      // 手动加上 -月份-日期 以实现区间搜索
        }


        // 合并所有查询
        queryBuilder.must().addAll(queryList);

        // 创建查询
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withSort(SortBuilders.fieldSort("入院情况.入院时间").order(SortOrder.DESC))    // "."用来表示 mapping 层级关系
                .withPageable(PageRequest.of(pageNum, pageSize))
                .build();

        // 执行并输出
        SearchHits<Record> searchHits = template.search(query, Record.class);
        List<SearchHit<Record>> searchHitList = searchHits.getSearchHits();
        List<Record> recordList = new ArrayList<>();
        searchHitList.forEach(sh -> {
            recordList.add(sh.getContent());
        });
        System.out.println("Records: " + recordList);
    }
}
