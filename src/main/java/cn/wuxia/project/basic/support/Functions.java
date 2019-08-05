package cn.wuxia.project.basic.support;

import cn.wuxia.common.util.*;
import cn.wuxia.project.basic.core.conf.bean.MenuBean;
import cn.wuxia.project.basic.core.conf.support.DTools;
import cn.wuxia.project.basic.core.conf.support.DicBean;
import cn.wuxia.project.basic.core.conf.support.TagBean;

import cn.wuxia.project.common.support.CacheConstants;
import cn.wuxia.project.common.support.CacheSupport;
import cn.wuxia.project.common.support.Constants;
import cn.wuxia.project.common.third.ip.IpUtil;
import cn.wuxia.project.common.third.ip.bean.IpAdress;
import com.google.common.collect.Lists;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Functions {

    protected final static Logger logger = LoggerFactory.getLogger(Functions.class);

    /**
     * 替换从begin开始到倒数end为止的字符为*
     *
     * @param str
     * @param begin
     * @param end
     * @author songlin
     */
    public static String replaceBetween(String str, Integer begin, Integer end) {
        if (StringUtil.isBlank(str)) {
            return "";
        }
        String s = StringUtil.substring(str, 0, begin) + "*****";
        if (str.length() > end) {
            s += StringUtil.substring(str, str.length() - end, str.length());
        }
        return s;
    }

    /**
     * url 安全性传参
     *
     * @param param
     * @return
     * @author songlin
     */
    public static String encodeUrl(Object param) {
        if (StringUtil.isNotBlank(param)) {
            return Base64.encodeBase64URLSafeString(param.toString().getBytes());
        }
        return "";
    }

    /**
     * url 安全性传参
     *
     * @param param
     * @return
     * @author songlin
     */
    public static String decodeUrl(Object param) {
        if (StringUtil.isNotBlank(param)) {
            return new String(Base64.decodeBase64(param.toString()));
        }
        return "";
    }

    /**
     * 中文转码
     *
     * @param s
     * @return
     * @author songlin
     */
    public static String encode(String s) {
        String result = s;
        if (StringUtil.isNotBlank(s)) {
            try {
                s = s.replace("/", "_2F").replace("\\", "2F_");
                result = URLEncoder.encode(s, Constants.UTF_8);
            } catch (UnsupportedEncodingException e) {
                return result;
            }
        }
        return result;
    }

    public static String decode(String s) {
        String result = s;
        if (StringUtil.isNotBlank(s)) {
            try {
                s = s.replace("_2F", "/").replace("2F_", "\\");
                result = URLEncoder.encode(s, Constants.UTF_8);
            } catch (UnsupportedEncodingException e) {
                return result;
            }
        }
        return result;
    }

    /**
     * 因为jstl fn 不支持正则表达式，拓展支持正则
     *
     * @param input
     * @param regex
     * @param replacement
     * @return
     * @author songlin
     */
    public static String replace(String input, String regex, String replacement) {
        if (StringUtil.isNotBlank(input)) {
            return input.replaceAll(regex, replacement);
        }
        return input;
    }

    /**
     * java实现不区分大小写替换 replace Ignore Case
     *
     * @param input
     * @param regex
     * @param replacement
     * @return
     */
    public static String replaceic(String input, String regex, String replacement) {
        try {
            Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            Matcher mc = p.matcher(input);
            StringBuffer sb = new StringBuffer();
            while (mc.find()) {
                mc.appendReplacement(sb, "<span class='font_color01'>" + mc.group() + "</span>");
            }
            mc.appendTail(sb);
            return sb.toString();
        } catch (Exception e) {
            return input;
        }
    }

    public static boolean contants(Object collection, Object validate) {
        if (collection instanceof List) {
            List list = (List) collection;
            if (list.contains(validate)) {
                return true;
            }
        } else if (collection instanceof Map) {
            Map map = (Map) collection;
            if (map.containsKey(validate)) {
                return true;
            }
        } else if (collection instanceof Object[]) {

            if (ArrayUtil.contains((Object[]) collection, validate)) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {

        System.out.print(Functions.replaceic("asfsdfdsAfsadfsd", "a", "2"));
        System.out.println(encode("/"));
    }

    /**
     * 获取中文数字名称
     *
     * @param number
     * @return
     * @author wuwenhao
     */
    public static String getChageChinese(String number) {
        List<String> result = Lists.newArrayList();
        String output = "";
        for (int i = 0; i < number.length(); i++) {
            char subNmber = number.charAt(i);
            if (subNmber == '1')
                output += "一";
            if (subNmber == '2')
                output += "二";
            if (subNmber == '3')
                output += "三";
            if (subNmber == '4')
                output += "四";
            if (subNmber == '5')
                output += "五";
            if (subNmber == '6')
                output += "六";
            if (subNmber == '7')
                output += "七";
            if (subNmber == '8')
                output += "八";
            if (subNmber == '9')
                output += "九";
            if (subNmber == '0')
                output += "零";
        }
        result.add(output);
        return StringUtil.join(result, "、");
    }

    /**
     * Description of the method
     *
     * @param content
     * @return
     * @author Administrator
     */
    public static Map<String, Object> jsonToMap(String content) {
        return JsonUtil.fromJson(content);
    }

    /**
     * 对象转为你json字符串
     *
     * @param content
     * @return
     * @author songlin
     */
    public static String toJson(Object content) {
        return JsonUtil.toJson(content);
    }


    /**
     * 查找字典
     *
     * @param dicid
     * @return
     */
    public static DicBean getDicById(String dicid) {
        return DTools.dicById(dicid);
    }

    /**
     * 查找字典
     *
     * @param parentcode
     * @return
     */
    public static List<DicBean> getDicsByParentCode(String parentcode) {
        return DTools.dicByParentCode(parentcode);
    }

    /**
     * 查找字典
     *
     * @param diccode
     * @return
     */
    public static String getDicByCode(String diccode) {
        return DTools.dic(diccode);
    }

    /**
     * 查找字典
     *
     * @param diccode
     * @param parentcode
     * @return
     */
    public static String getDic(String diccode, String parentcode) {
        if (StringUtil.isBlank(parentcode)) {
            return DTools.dic(diccode);
        }
        return DTools.dic(diccode, parentcode);
    }

    /**
     * 查找标签
     *
     * @param tagid
     * @return
     */
    public static TagBean getTag(String tagid) {
        return DTools.tagById(tagid);
    }

    /**
     * 查找标签
     *
     * @param categorycode
     * @return
     */
    public static List<TagBean> getTags(String categorycode) {
        return DTools.tagsByCcode(categorycode);
    }

    public static String getctx(String type) {
        try {
            Method method = DConstants.class.getMethod(type + "ServerName", String.class);
            logger.info("调用方法{}", method.getName());
            return (String) method.invoke(null, DConstants.getPlatform());
        } catch (Exception e) {
            logger.warn("", e);
            return "";
        }
    }

    /**
     * 解释字符串$
     *
     * @param template
     * @param object
     * @return
     */
    public static String stringSimpleParse(String template, Object object) {
        return StringParserUtil.simpleParse(template, object);
    }

    /**
     * 解释字符串#
     * spring spel表达式
     *
     * @param template
     * @param object
     * @return
     */
    public static String stringSPELParse(String template, Object object) {
        return StringParserUtil.spelParse(template, object);
    }

    /**
     * 根据groupcode查找菜单
     *
     * @param groupcode
     * @return
     */
    public static List<MenuBean> menus(String groupcode) {
        return DTools.menuByGroup(groupcode, DConstants.getPlatform());
    }


    /**
     * 根据ip查找归属地
     *
     * @param ip
     * @return
     */
    public static String ipBelongingTo(String ip) {
        if (StringUtil.isNotBlank(ip)) {
            /**
             * 缓存的key取前3位
             */
            String ipKey = StringUtil.substringBeforeLast(ip, ".");
            String addr = (String) CacheSupport.get(CacheConstants.CACHED_VALUE_1_DAY, ipKey);
            if (StringUtil.isBlank(addr)) {
                IpAdress ipAdress = IpUtil.bytool(ip);
                if (ipAdress != null) {
                    List<String> detail = Lists.newArrayList();
                    if (StringUtil.isNotBlank(ipAdress.getCountry())) {
                        detail.add(ipAdress.getCountry());
                    }
                    if (StringUtil.isNotBlank(ipAdress.getRegion())) {
                        detail.add(ipAdress.getRegion());
                    }
                    if (StringUtil.isNotBlank(ipAdress.getCity())) {
                        detail.add(ipAdress.getCity());
                    }
                    if (StringUtil.isNotBlank(ipAdress.getIsp())) {
                        detail.add(ipAdress.getIsp());
                    }
                    addr = StringUtil.join(detail, "，");
                    CacheSupport.set(CacheConstants.CACHED_VALUE_1_DAY, ipKey, addr);
                }
                return addr;
            } else {
                return addr;
            }
        } else {
            return "";
        }
    }

}
