package cn.wuxia.project.basic.core.conf.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Where;

import cn.wuxia.project.common.model.ModifyInfoEntity;

/**
 * The persistent class for the sys_generate_static_page database table.
 */
@Entity
@Table(name = "p_generate_static_page")
@Where(clause = ModifyInfoEntity.ISOBSOLETE_DATE_IS_NULL)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GenerateStaticPage extends ModifyInfoEntity {
    private static final long serialVersionUID = 1L;

    private String charset;

    private String parameter;

    private String fileName;

    private String sourceUrl;

    private String toDistLocation;

    public GenerateStaticPage() {
        super();
    }

    public GenerateStaticPage(String sourceUrl, String toDistLocation, String fileName) {
        super();
        this.sourceUrl = sourceUrl;
        this.toDistLocation = toDistLocation;
        this.fileName = fileName;
    }

    public String getCharset() {
        return this.charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    @Column(name = "FILE_NAME")
    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getParameter() {
        return this.parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    @Column(name = "SOURCE_URL")
    public String getSourceUrl() {
        return this.sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    @Column(name = "TO_DIST_LOCATION")
    public String getToDistLocation() {
        return this.toDistLocation;
    }

    public void setToDistLocation(String toDistLocation) {
        this.toDistLocation = toDistLocation;
    }

}
