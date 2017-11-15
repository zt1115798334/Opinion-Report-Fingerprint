package com.opinion.mysql.entity;

import com.opinion.base.bean.BaseSortRequest;
import com.opinion.mysql.converter.LocalDateAttributeConverter;
import com.opinion.mysql.converter.LocalDateTimeAttributeConverter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 上报文章表 entity
 *
 * @author zhangtong
 * Created by on 2017/11/13
 */
@Entity
@Table(name = "t_report_article")
public class ReportArticle extends BaseSortRequest implements Serializable {

    private static final long serialVersionUID = 9201034849892179274L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    /**
     * 上报来源 artificial:人工上报，machine:机器上报
     */
    @Column(name = "report_source", nullable = false)
    private String reportSource;

    /**
     * 上报类型 上报类型 red：红色，orange：橙色，yellow：黄色
     */
    @Column(name = "report_level", nullable = false)
    private String reportLevel;

    /**
     * 来源地址
     */
    @Column(name = "source_url")
    private String sourceUrl;

    /**
     * 来源类型 网络：network 媒体 ： media 现场 scene 其他 other
     */
    @Column(name = "source_type", nullable = false)
    private String sourceType;

    /**
     * 标题
     */
    @Column(name = "title", nullable = false)
    private String title;

    /**
     * 发布时间
     */
    @Column(name = "publish_datetime", nullable = false)
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime publishDatetime;


    /**
     * 回复类型 点击 click，评论 comment  预估值 estimate
     */
    @Column(name = "reply_type", nullable = false)
    private String replyType;

    /**
     * 回复数
     */
    @Column(name = "reply_number", nullable = false)
    private Integer replyNumber;

    /**
     * 上报原因
     */
    @Column(name = "report_cause", nullable = false)
    private String reportCause;

    /**
     * 采纳时间
     */
    @Column(name = "adopt_date")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime adoptDate;

    /**
     * 采纳人
     */
    @Column(name = "adopt_user")
    private String adoptUser;

    /**
     * adopt:采纳，report:已上报
     */
    @Column(name = "adopt_state", nullable = false)
    private String adoptState;

    /**
     * 采纳意见
     */
    @Column(name = "adopt_opinion")
    private String adoptOpinion;

    /**
     * 创建时间
     */
    @Column(name = "created_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime createdDate;
    /**
     * 创建人
     */
    @Column(name = "created_user", nullable = false)
    private String createdUser;

    /**
     * 修改时间
     */
    @Column(name = "modified_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime modifiedDate;

    /**
     * 修改人
     */
    @Column(name = "modified_user", nullable = false)
    private String modifiedUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReportSource() {
        return reportSource;
    }

    public void setReportSource(String reportSource) {
        this.reportSource = reportSource;
    }

    public String getReportLevel() {
        return reportLevel;
    }

    public void setReportLevel(String reportLevel) {
        this.reportLevel = reportLevel;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getPublishDatetime() {
        return publishDatetime;
    }

    public void setPublishDatetime(LocalDateTime publishDatetime) {
        this.publishDatetime = publishDatetime;
    }

    public String getReplyType() {
        return replyType;
    }

    public void setReplyType(String replyType) {
        this.replyType = replyType;
    }

    public Integer getReplyNumber() {
        return replyNumber;
    }

    public void setReplyNumber(Integer replyNumber) {
        this.replyNumber = replyNumber;
    }

    public String getReportCause() {
        return reportCause;
    }

    public void setReportCause(String reportCause) {
        this.reportCause = reportCause;
    }

    public LocalDateTime getAdoptDate() {
        return adoptDate;
    }

    public void setAdoptDate(LocalDateTime adoptDate) {
        this.adoptDate = adoptDate;
    }

    public String getAdoptUser() {
        return adoptUser;
    }

    public void setAdoptUser(String adoptUser) {
        this.adoptUser = adoptUser;
    }

    public String getAdoptState() {
        return adoptState;
    }

    public void setAdoptState(String adoptState) {
        this.adoptState = adoptState;
    }

    public String getAdoptOpinion() {
        return adoptOpinion;
    }

    public void setAdoptOpinion(String adoptOpinion) {
        this.adoptOpinion = adoptOpinion;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
