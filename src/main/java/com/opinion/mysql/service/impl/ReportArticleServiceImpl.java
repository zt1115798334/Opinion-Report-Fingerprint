package com.opinion.mysql.service.impl;

import com.opinion.constants.SysConst;
import com.opinion.mysql.entity.ReportArticle;
import com.opinion.mysql.entity.ReportArticleLog;
import com.opinion.mysql.repository.ReportArticleRepository;
import com.opinion.mysql.service.ReportArticleLogService;
import com.opinion.mysql.service.ReportArticleService;
import com.opinion.utils.DateUtils;
import com.opinion.utils.PageUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;

/**
 * @author zhangtong
 * Created by on 2017/11/13
 */
@Service
public class ReportArticleServiceImpl implements ReportArticleService {

    @Autowired
    private ReportArticleRepository reportArticleRepository;

    @Autowired
    private ReportArticleLogService reportArticleLogService;

    @Override
    public ReportArticle save(ReportArticle reportArticle) {
        reportArticle = reportArticleRepository.save(reportArticle);
        saveReportArticleLog(reportArticle.getId(), reportArticle.getAdoptState(), null);
        return reportArticle;
    }

    @Override
    public ReportArticle findOneById(Long id) {
        return reportArticleRepository.findOne(id);
    }

    @Override
    public Page<ReportArticle> findPageByCreateUser(ReportArticle reportArticle) {

        Specification<ReportArticle> specification = new Specification<ReportArticle>() {
            @Override
            public Predicate toPredicate(Root<ReportArticle> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                if (StringUtils.isNotEmpty(reportArticle.getCreatedUser())) {
                    query.where(builder.and(builder.equal(root.get("createdUser").as(String.class), reportArticle.getCreatedUser())));
                }
                if (StringUtils.isNotEmpty(reportArticle.getTitle())) {
                    query.where(builder.and(builder.like(root.get("title").as(String.class), reportArticle.getTitle())));
                }
                if (StringUtils.isEmpty(reportArticle.getAdoptState())) {
                    query.where(builder.and(builder.equal(root.get("adoptState").as(String.class), reportArticle.getAdoptState())));
                }
                if (StringUtils.isNotEmpty(reportArticle.getSourceType())) {
                    query.where(builder.and(builder.equal(root.get("sourceType").as(String.class), reportArticle.getSourceType())));
                }
                return null;
            }
        };
        Pageable pageable = PageUtils.buildPageRequest(reportArticle.getPageNum(),
                reportArticle.getPageSize(),
                reportArticle.getSortParam(),
                reportArticle.getSortParam());
        Page<ReportArticle> result = reportArticleRepository.findAll(specification, pageable);
        return result;
    }

    @Override
    public ReportArticle examineAndVerify(Long id, LocalDateTime adoptDate, String adoptUser, String adoptState, String adoptOpinion) {
        ReportArticle reportArticle = reportArticleRepository.findOne(id);
        if (reportArticle != null) {
            reportArticle.setAdoptDate(adoptDate);
            reportArticle.setAdoptUser(adoptUser);
            reportArticle.setAdoptState(adoptState);
            reportArticle.setAdoptOpinion(adoptOpinion);
            reportArticle = reportArticleRepository.save(reportArticle);
            saveReportArticleLog(id, adoptState, adoptOpinion);
        }
        return reportArticle;
    }

    public ReportArticleLog saveReportArticleLog(Long reportArticleId,
                                                 String adoptState,
                                                 String adoptOpinion) {
        String userAccount = SysConst.USER_ACCOUNT;
        LocalDateTime currentDate = DateUtils.currentDate();
        ReportArticleLog reportArticleLog = new ReportArticleLog();
        reportArticleLog.setReportArticleId(reportArticleId);
        reportArticleLog.setAdoptDate(currentDate);
        reportArticleLog.setAdoptUser(userAccount);
        reportArticleLog.setAdoptState(adoptState);
        reportArticleLog.setAdoptOpinion(adoptOpinion);
        reportArticleLog.setCreatedDate(currentDate);
        reportArticleLog.setCreatedUser(userAccount);
        reportArticleLogService.save(reportArticleLog);
        return reportArticleLog;
    }
}
