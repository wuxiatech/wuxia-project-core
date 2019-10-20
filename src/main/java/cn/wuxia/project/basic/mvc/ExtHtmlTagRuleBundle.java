/*
* Created on :Nov 22, 2014
* Author     :songlin
* Change History
* Version       Date         Author           Reason
* <Ver.No>     <date>        <who modify>       <reason>
* Copyright 2014-2020 wuxia.tech All right reserved.
*/
package cn.wuxia.project.basic.mvc;

import org.sitemesh.SiteMeshContext;
import org.sitemesh.content.ContentProperty;
import org.sitemesh.content.tagrules.TagRuleBundle;
import org.sitemesh.content.tagrules.html.ExportTagToContentRule;
import org.sitemesh.tagprocessor.State;

public class ExtHtmlTagRuleBundle implements TagRuleBundle {

    @Override
    public void install(State defaultState, ContentProperty contentProperty, SiteMeshContext siteMeshContext) {
        defaultState.addRule("mycss", new ExportTagToContentRule(siteMeshContext, contentProperty.getChild("mycss"), false));
        defaultState.addRule("myheader", new ExportTagToContentRule(siteMeshContext, contentProperty.getChild("myheader"), false));
        defaultState.addRule("myjs", new ExportTagToContentRule(siteMeshContext, contentProperty.getChild("myjs"), false));
        defaultState.addRule("myfooter", new ExportTagToContentRule(siteMeshContext, contentProperty.getChild("myfooter"), false));
    }

    @Override
    public void cleanUp(State defaultState, ContentProperty contentProperty, SiteMeshContext siteMeshContext) {

    }

}
