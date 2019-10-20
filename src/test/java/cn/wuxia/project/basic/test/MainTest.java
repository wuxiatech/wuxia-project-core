package cn.wuxia.project.basic.test;

import cn.wuxia.project.common.api.ApiRequestBean;
import cn.wuxia.project.common.api.ApiRequestHandler;
import cn.wuxia.project.common.api.ApiResponseBean;
import cn.wuxia.project.common.bean.SimpleFieldProperty;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainTest {



    public static void main(String[] args) {
        String result = "";

        String str = "测试文字<img src=\"/upload/2016-05/2016052314264421.jpg\" alt=\"\" title=\"\" />测试文字" +
                "<img title=\"\" src=\"/upload/2016-05/2016052314262994.png\" alt=\"\" />测试文字" +
                "<img src='/upload/2016-05/2016052314262994.jpg' alt=\"\" title=\"\" />测试文字";
        //在内容中匹配与正则表达式匹配的字符
        Pattern p = Pattern.compile("(i?)<img.*? src=?(.*?\\.(jpg|gif|bmp|bnp|png)).*? />");
        Matcher m = p.matcher(str);

        while (m.find()) {
            String k = m.group();
            //循环匹配到的字符
            System.out.println(k);
            int srcIndex = k.indexOf("src=");
            String strSrc = cn.wuxia.common.util.StringUtil.substring(k, srcIndex + 4);
            System.out.println(strSrc);

        }


        List<String> abc = Lists.newArrayList("abc", "efg", "xyz");
        for (String k : abc) {
            if (k.equals("efg")) {
                abc.remove(k);
            }
        }
        System.out.println(abc);
    }

    @Test
    public void test() throws Exception {
        ApiResponseBean responseBean = ApiRequestBean.encode("abc");
        System.out.println(responseBean.getString());

        ApiResponseBean responseBean2 = ApiRequestBean.encode(SimpleFieldProperty.value("abc").name("xyz"));
        System.out.println(responseBean2.getObject().getClass());


        ApiResponseBean responseBean3 = ApiRequestBean.okJson(SimpleFieldProperty.value("abc").name("xyz"));
        System.out.println(responseBean3.getString());

        SimpleFieldProperty property =  ApiRequestHandler.toBean(responseBean3, SimpleFieldProperty.class);
        System.out.println(property);

        Map map =  ApiRequestHandler.toMap(responseBean3);
        System.out.println(map);

    }
}
