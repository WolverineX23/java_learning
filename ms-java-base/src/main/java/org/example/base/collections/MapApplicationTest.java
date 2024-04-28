package org.example.base.collections;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@SpringBootTest
public class MapApplicationTest {


    // map 的遍历方式汇总
    @Test
    void testMap() {
        Map<Character, Integer> map = new HashMap<>();
        map.put('a', 2);
        map.put('b', 1);

        /* Map 没有 get(int index)；只有 get(Object o); - 靠键获取
        if (map.get(0) == map.get(1)) {
            System.out.println("0: " + map.get(0) + " and " + "1: " + map.get(1));  // 0: null and 1: null
            System.out.println("Equal");
        }
        */

        // 比较两个值是否相等
        Collection<Integer> values = map.values();
        for (Integer value : values) {
            System.out.println(value);
        }
//        if (values.get(0).equals(values.get(1))) {
//            System.out.println("Equal");
//        }

        // 遍历方式一：keySet() - 返回 key 集合 Set
        System.out.println("keySet(): ");
        for (Character key : map.keySet()) {
            System.out.println("key: " + key + ", value: " + map.get(key));
        }

        // 遍历方式二： foreach
        System.out.println("foreach: ");
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            System.out.println("key: " + entry.getKey() + ", value: " + entry.getValue());
        }

        // 遍历方式三：迭代器
        System.out.println("Iterator: ");
        Iterator<Map.Entry<Character, Integer>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Character, Integer> entry = iterator.next();
            System.out.println("key: " + entry.getKey() + ", value: " + entry.getValue());
        }

        // 遍历方式四：Lambda 表达式
        System.out.println("Lambda: ");
        map.forEach((key, value) -> System.out.println("key: " + key + ", value: " + value));

        // 遍历方式五：Stream 流
        System.out.println("Stream: ");
        map.entrySet().stream().forEach((Map.Entry<Character, Integer> entry) -> {
            System.out.println("key: " + entry.getKey() + ", value: " + entry.getValue());
        });
    }
}
