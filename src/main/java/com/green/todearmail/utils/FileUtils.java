package com.green.todearmail.utils;

import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class FileUtils {
    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     * @param filePath
     * @return
     */
    public static String readJsonData(String filePath){
        // 读取文件数据
        //System.out.println("读取文件数据util");
        ClassPathResource classPathResource = new ClassPathResource(filePath);
        StringBuffer strbuffer = new StringBuffer();
        File myFile = null;
        try {
            myFile = classPathResource.getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!myFile.exists()) {
            System.err.println("Can't Find " + filePath);
        }
        try {
            InputStream is = classPathResource.getInputStream();

//            FileInputStream fis = new FileInputStream(pactFile);
            InputStreamReader inputStreamReader = new InputStreamReader(is, "UTF-8");
            BufferedReader in  = new BufferedReader(inputStreamReader);

            String str;
            while ((str = in.readLine()) != null) {
                strbuffer.append(str);  //new String(str,"UTF-8")
            }
            in.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
        //System.out.println("读取文件结束util");
        return strbuffer.toString();

    }

    /**
     * 把json格式的字符串写到文件
     * @param filePath
     * @param sets
     * @return
     */
    public static boolean writeFile(String filePath, String sets) {
        FileWriter fw;
        ClassPathResource classPathResource = new ClassPathResource(filePath);
        try {
            fw = new FileWriter(classPathResource.getDescription());
            PrintWriter out = new PrintWriter(fw);
            out.write(sets);
            out.println();
            fw.close();
            out.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

}