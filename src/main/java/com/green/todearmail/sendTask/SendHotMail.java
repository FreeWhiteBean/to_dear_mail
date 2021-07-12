package com.green.todearmail.sendTask;


import com.alibaba.fastjson.JSONObject;
import com.green.todearmail.utils.DateUtils;
import com.green.todearmail.utils.FastJsonConvertUtils;
import com.green.todearmail.utils.HttpUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Component
public class SendHotMail {
    private static String MAIL_FROM = "yjjzfight@163.com";// 指明邮件的发件人
    private static String PASSWORD_MAILFROM = "XVIKRUXPTQKWBYWI";// 指明邮件的发件人登陆密码
    private static String MAIL_HOST ="smtp.163.com";	// 邮件的服务器域名
//    private static String MAIL_FROM = "515438175@qq.com";// 指明邮件的发件人
//    private static String PASSWORD_MAILFROM = "ygxjpltbwxhpbhcf";// 指明邮件的发件人登陆密码
//    private static String MAIL_HOST ="smtp.qq.com";	// 邮件的服务器域名
    private static String MAIL_TO = "704685927@qq.com";	// 指明邮件的收件人
    private static String MAIL_TITTLE = "爱你的今天";// 邮件的标题
    private static String MAIL_TEXT ="这是一个简单的邮件";	// 邮件的文本内容
    private static String START_TIME ="2019-09-10 23:12:37";	// together
    private static String filePath = "settings/monthJson.json";

    @Scheduled(cron = "0 0 9 * * ?")
    public static void sendMail() throws Exception {

//        String json = readJsonData(filePath);

        String weatherResult = HttpUtils.get( "http://t.weather.itboy.net/api/weather/city/101110101", null,60000,60000,"utf-8");
//        String weatherResult = HttpUtils.get( "http://t.weather.itboy.net/api/weather/city/101281701", null,60000,60000,"utf-8");
        String dsResult = HttpUtils.get( "http://open.iciba.com/dsapi/", null,60000,60000,"utf-8");
        String sweetResult = HttpUtils.get( "https://chp.shadiao.app/api.php?from=lovecyt", null,60000,60000,"utf-8");

        Map<String, Object> weatherResultMap = FastJsonConvertUtils.convertJSONToObject(weatherResult, Map.class) ;
        Map<String,Object> data = FastJsonConvertUtils.convertJSONToObject((JSONObject) weatherResultMap.get("data"),Map.class) ;
        List<Map<String,Object>> forecast = (List<Map<String, Object>>) data.get("forecast");
        Map<String,Object> weatherInfo = forecast.get(0);

        Map<String, Object> dsResultMap = FastJsonConvertUtils.convertJSONToObject(dsResult, Map.class) ;
        Map map = DateUtils.timesBetween(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(START_TIME),new Date());
        String together = "亲爱的宝贝，平行世界中我们已经在一起" + map.get("Day") +"天"+ map.get("Hour")+"小时"+map.get("Min")+"分"+map.get("Sec")+"秒";
        StringBuffer sb = new StringBuffer();
        sb.append(dsResultMap.get("content")).append("<br>").append(dsResultMap.get("note")).append("<br>").append("今天是");
        sb.append(weatherInfo.get("ymd")).append("\t\t").append(weatherInfo.get("week")).append("\t\t")
                .append(weatherInfo.get("type")).append("\t\t").append(weatherInfo.get("fx")).append("\t\t")
                .append(weatherInfo.get("fl")).append("\t\t").append(weatherInfo.get("high")).append("\t\t")
                .append(weatherInfo.get("low")).append("\t\t").append(weatherInfo.get("notice")).append("，我爱你").append("<br>");
        sb.append(together).append("<br>");
        sb.append(sweetResult).append("<br>");
        sb.append("来自爱你的寻梦人");
        MAIL_TEXT = sb.toString();
        System.out.println(MAIL_TEXT);
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        MAIL_TITTLE = "爱你的" + date.substring(0,4)+"年"+date.substring(4,6)+"月"+date.substring(6,8)+"日";

        Properties prop = new Properties();
        prop.setProperty("mail.host", MAIL_HOST);
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth", "true");

        // 使用JavaMail发送邮件的5个步骤

        // 1、创建session
        Session session = Session.getInstance(prop);
        // 开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(true);
        // 2、通过session得到transport对象
        Transport ts = session.getTransport();
        // 3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
        ts.connect(MAIL_HOST,MAIL_FROM, PASSWORD_MAILFROM);
        // 4、创建邮件
        Message message = createSimpleMail(session,MAIL_FROM,MAIL_TO,MAIL_TITTLE,MAIL_TEXT);
        // 5、发送邮件
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();
    }

    /**
     * @Method: createSimpleMail
     * @Description: 创建一封只包含文本的邮件
     */
    public static MimeMessage createSimpleMail(Session session, String mailfrom, String mailTo, String mailTittle,
                                               String mailText) throws Exception {
        // 创建邮件对象
        MimeMessage message = new MimeMessage(session);
        // 指明邮件的发件人
        message.setFrom(new InternetAddress(mailfrom));
        // 指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
        // 邮件的标题
        message.setSubject(mailTittle);
        // 邮件的文本内容
        message.setContent(mailText, "text/html;charset=UTF-8");
        // 返回创建好的邮件对象
        return message;
    }

    public static void main(String[] args) throws Exception {
        sendMail();
    }

    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     * @param pactFile
     * @return
     */
    public static String readJsonData(String pactFile) throws IOException {
        // 读取文件数据
        //System.out.println("读取文件数据util");
        ClassPathResource classPathResource = new ClassPathResource(pactFile);
        StringBuffer strbuffer = new StringBuffer();
        File myFile = classPathResource.getFile();
        if (!myFile.exists()) {
            System.err.println("Can't Find " + pactFile);
        }
        try {
            FileInputStream fis = new FileInputStream(pactFile);
            InputStreamReader inputStreamReader = new InputStreamReader(fis, "UTF-8");
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
        try {
            fw = new FileWriter(filePath);
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
