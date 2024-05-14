package org.example.algorithm.FaceTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * input: cpdomains = ["900 google.mail.com", "50 yahoo.com", "1 intel.mail.com", "5 wiki.org"]
 * output: ["901 mail.com","50 yahoo.com","900 google.mail.com","5 wiki.org","5 org","1 intel.mail.com","951 com"]
 * desc: 解释：按照前文描述，会访问 "google.mail.com" 900 次，"yahoo.com" 50 次，"intel.mail.com" 1 次，"wiki.org" 5 次。
 *          而对于父域名，会访问 "mail.com" 900 + 1 = 901 次，"com" 900 + 50 + 1 = 951 次，和 "org" 5 次。
 *
 */
public class HuaFaceTest {

    public List<String> func(String[] cpdomains) {

        Map<String, Integer> inputMap = new HashMap<>();
        Map<String, Integer> childMap = new HashMap<>();

        // 构建输入的父域，并构建子域初始化访问量为0
        for (int i = 0; i < cpdomains.length; i++) {
            String domain = cpdomains[i];

            String[] cp = domain.split(" ");
            String ip = cp[1];
            inputMap.put(ip, Integer.parseInt(cp[0]));

            String[] childs = ip.split(".");
            for (int j = 1; j < childs.length; j++) {
                String child = childs[j];
                for (int k = j + 1; k < childs.length; k++) {
                    child += "." + childs[k];
                }
                childMap.put(child, 0);
            }
        }

        // 根据父域名，获取子域名的访问和
        for (Map.Entry<String, Integer> entry : childMap.entrySet()) {
            String childDomain = entry.getKey();
            for (Map.Entry<String, Integer> inputEntry : inputMap.entrySet()) {
                String inputDomain = inputEntry.getKey();
                // 包含则添加
                if (inputDomain.contains(childDomain)) {
                    entry.setValue(entry.getValue() + inputEntry.getValue());
                }
            }
        }

        // 输出
        List<String> res = new ArrayList<>();
        // 添加 子域名
        for (Map.Entry<String, Integer> entry : childMap.entrySet()) {
            String item = entry.getValue() + " " + entry.getKey();
            res.add(item);
        }
        // 添加 父域名
        for (Map.Entry<String, Integer> entry : inputMap.entrySet()) {
            String item = entry.getValue() + " " + entry.getKey();
            res.add(item);
        }

        return res;
    }
}
