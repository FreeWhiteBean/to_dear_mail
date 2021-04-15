package com.green.todearmail.utils;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RwJsonFileUtils{

    public static void main(String[] args) throws IOException {

//        File file = new File("monthJson.json");
//        if (!file.exists()) {
//            System.err.println("Can't Find " + file);
//        }
        String filePath = "settings/monthJson.json";
//        ClassPathResource classPathResource = new ClassPathResource("monthJson.json");
//        String filePath = classPathResource.getPath();
//        if (!classPathResource.getFile().exists()) {
//            System.err.println("Can't Find " + classPathResource.getFilename());
//        }

        //将Json文件读取为字符串
        String json = FileUtils.readJsonData(filePath);

        //修改Json的value
        DocumentContext context = JsonPath.parse(json);
        Map<String,String> map = FastJsonConvertUtils.convertJSONToObject(json,Map.class);

        JsonPath path = JsonPath.compile("$.come");
        context.set(path, "2020-04-10");
        path = JsonPath.compile("$.go");
        context.set(path, "2020-04-17");

        String changedJson = context.jsonString();

        //将json字符串格式化 并写入制定文件
        FileUtils.writeFile(filePath, FomateJsonUtils.formatJson(changedJson));

        HashMap read = JsonPath.read(changedJson, "$");

        System.out.println(read.toString());

    }
}
