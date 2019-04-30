package cn.wuxia.project.basic.core.conf.support;

import cn.wuxia.common.spring.SpringContextHolder;
import cn.wuxia.common.util.ArrayUtil;
import cn.wuxia.common.util.ListUtil;
import cn.wuxia.common.util.StringUtil;
import cn.wuxia.project.basic.core.conf.bean.MenuBean;
import cn.wuxia.project.basic.core.conf.bean.TagsinputBean;
import cn.wuxia.project.basic.core.conf.entity.CustomTag;
import cn.wuxia.project.basic.core.conf.entity.CustomTagCategory;
import cn.wuxia.project.basic.core.conf.entity.SystemDictionary;
import cn.wuxia.project.basic.core.conf.service.*;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import java.util.List;

public class DTools {
    protected final static Logger logger = LoggerFactory.getLogger(DTools.class);
    //public static DicCustomConfigService dicCustomConfigService = SpringContextHolder.getBean(DicCustomConfigService.class);

    private static SystemDictionaryService dictionaryService = SpringContextHolder.getBean(SystemDictionaryService.class);

    private static CustomTagService tagService = SpringContextHolder.getBean(CustomTagService.class);

    private static CustomTagCategoryService tagCategoryService = SpringContextHolder.getBean(CustomTagCategoryService.class);

    private static OrderNoGenerateService noGenerateService = SpringContextHolder.getBean(OrderNoGenerateService.class);

    private static CustomMenuGroupService menuGroupService = SpringContextHolder.getBean(CustomMenuGroupService.class);

    private static CustomMenuService menuService = SpringContextHolder.getBean(CustomMenuService.class);

    /**
     * 根据字典code获取value值
     *
     * @param code
     * @return
     * @author songlin
     */
    //    @Cacheable(key = CacheConstants.CACHED_KEY_DEFAULT + "+#code", value = CacheConstants.CACHED_VALUE_1_HOUR)
    public static String dic(String code) {
        Assert.isTrue(StringUtil.isNotBlank(code), "code不能为空");
        DicBean dicBean = dictionaryService.findByCode(code);
        if (dicBean == null) {
            return null;
        }
        String value = dicBean.getValue();
        String[] keys = StringUtil.getTemplateKey(value);
        if (ArrayUtil.isNotEmpty(keys)) {
            for (String k : keys) {
                String newVal = dic(k);
                /**
                 * 这里如果查询不到值则保留原值
                 */
                if (StringUtil.isNotBlank(newVal)) {
                    value = StringUtil.replaceKeysSimple(value, k, newVal);
                }
            }
        }

        logger.info("请求字典：{}={}", code, value);
        return value;
    }

    /**
     * 根据字典code获取value值
     *
     * @param code
     * @param parentCode
     * @return
     * @author songlin
     */
    //    @Cacheable(key = CacheConstants.CACHED_KEY_DEFAULT + "+#code+#parentCode", value = CacheConstants.CACHED_VALUE_1_HOUR)
    public static String dic(String code, String parentCode) {
        if (parentCode == null) {
            return dic(code);
        }

        DicBean dicBean = dictionaryService.findByCodeAndParentCode(code, parentCode);
        if (dicBean == null) {
            return "";
        }
        String value = dicBean.getValue();
        String[] keys = StringUtil.getTemplateKey(value);
        if (ArrayUtil.isNotEmpty(keys)) {
            for (String k : keys) {
                String newVal = dic(k);
                /**
                 * 这里如果查询不到值则保留原值
                 */
                if (StringUtil.isNotBlank(newVal)) {
                    value = StringUtil.replaceKeysSimple(value, k, newVal);
                }
            }
        }

        logger.info("请求字典：{}={}", code, value);
        return value;
    }

    /**
     * 根据id获取对象
     *
     * @param id
     * @return
     * @author songlin
     */
    //    @Cacheable(key = CacheConstants.CACHED_KEY_DEFAULT + "+#id", value = CacheConstants.CACHED_VALUE_1_HOUR)
    public static DicBean dicById(String id) {
        SystemDictionary dic = dictionaryService.findById(id);
        if (dic == null)
            return null;
        DicBean dicBean = new DicBean();
        BeanUtils.copyProperties(dic, dicBean);
        return dicBean;
    }

    /**
     * 根据父code获取所有子对象
     *
     * @param code
     * @return
     * @author songlin
     */
    //    @Cacheable(key = CacheConstants.CACHED_KEY_DEFAULT + "+#code", value = CacheConstants.CACHED_VALUE_1_HOUR)
    public static List<DicBean> dicByParentCode(String code) {
        return dictionaryService.findByParentCode(code);
    }

    /**
     * 根据tagid获取标签名称
     *
     * @param id
     * @return
     * @author songlin
     */
    //    @Cacheable(key = CacheConstants.CACHED_KEY_DEFAULT + "+#id", value = CacheConstants.CACHED_VALUE_1_HOUR)
    public static TagBean tagById(String id) {
        CustomTag customTag = tagService.findById(id);
        if (customTag == null)
            return null;
        return new TagBean(customTag);
    }

    /**
     * 根据categoryid获取标签组名称
     *
     * @param categoryid
     * @return
     * @author songlin
     */
    //    @Cacheable(key = CacheConstants.CACHED_KEY_DEFAULT + "+#categoryid", value = CacheConstants.CACHED_VALUE_1_HOUR)
    public static List<TagBean> tagsByCid(String categoryid) {
        List<CustomTag> tags = tagService.findByCategory(categoryid);
        List<TagBean> tagBeans = Lists.newArrayList();
        if (ListUtil.isNotEmpty(tags)) {
            for (CustomTag customTag : tags) {
                tagBeans.add(new TagBean(customTag));
            }
        }
        return tagBeans;
    }

    /**
     * 根据categoryCode获取标签组名称
     *
     * @param categoryCode
     * @return
     * @author songlin
     */
    //    @Cacheable(key = CacheConstants.CACHED_KEY_DEFAULT + "+#categoryid", value = CacheConstants.CACHED_VALUE_1_HOUR)
    public static List<TagBean> tagsByCcode(String categoryCode) {
        CustomTagCategory customTagCategory = tagCategoryService.findByCode(categoryCode);
        if (customTagCategory != null) {
            return tagsByCid(customTagCategory.getId());
        }
        return null;
    }

    /**
     * tagsinput 插件专用
     *
     * @param categoryCode
     * @return
     * @author songlin
     */
    //    @Cacheable(key = CacheConstants.CACHED_KEY_DEFAULT + "+#categoryid", value = CacheConstants.CACHED_VALUE_1_HOUR)
    public static List<TagsinputBean> tagsForTagsinput(String categoryCode) {
        CustomTagCategory customTagCategory = tagCategoryService.findByCode(categoryCode);
        List<CustomTag> tags = tagService.findByCategory(customTagCategory.getId());
        List<TagsinputBean> tagBeans = Lists.newArrayList();
        if (ListUtil.isNotEmpty(tags)) {
            for (CustomTag customTag : tags) {
                tagBeans.add(new TagsinputBean(customTag));
            }
        }
        return tagBeans;
    }

    /**
     * 顺序码
     *
     * @param code
     * @return
     */
    public static long nextOrderNo(String code) {
        return noGenerateService.next(code);
    }

    /**
     * 根据groupcode查找菜单
     *
     * @param groupcode
     * @param platform
     * @return
     */
    public static List<MenuBean> menuByGroup(String groupcode, String platform) {
        List<MenuBean> list = menuGroupService.findByCode(groupcode);
        if (ListUtil.isNotEmpty(list)) {
            for (MenuBean menuBean : list) {
                resolvingUrl(menuBean, platform);
            }
            return list;
        }
        return null;
    }

    private static void resolvingUrl(MenuBean menuBean, String platform) {
        String value = menuBean.getUrl();
        String[] keys = StringUtil.getTemplateKey(value);
        if (ArrayUtil.isNotEmpty(keys)) {
            for (String k : keys) {
                String newVal = dic(k, platform);
                /**
                 * 这里如果查询不到值则保留原值
                 */
                if (StringUtil.isNotBlank(newVal)) {
                    value = StringUtil.replaceKeysSimple(value, k, newVal);
                }
            }
        }
        menuBean.setUrl(value);
        if (ListUtil.isNotEmpty(menuBean.getSubMenu())) {
            for (MenuBean menuBean2 : menuBean.getSubMenu()) {
                resolvingUrl(menuBean2, platform);
            }
        }
    }
}
