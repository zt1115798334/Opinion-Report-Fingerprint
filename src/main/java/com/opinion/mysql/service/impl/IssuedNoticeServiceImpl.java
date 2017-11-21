package com.opinion.mysql.service.impl;

import com.google.common.collect.Lists;
import com.opinion.constants.SysConst;
import com.opinion.constants.SysUserConst;
import com.opinion.mysql.entity.IssuedNotice;
import com.opinion.mysql.entity.IssuedNoticeLog;
import com.opinion.mysql.entity.SysMessage;
import com.opinion.mysql.entity.SysUser;
import com.opinion.mysql.repository.IssuedNoticeRepository;
import com.opinion.mysql.service.IssuedNoticeLogService;
import com.opinion.mysql.service.IssuedNoticeService;
import com.opinion.mysql.service.SysMessageService;
import com.opinion.mysql.service.SysUserService;
import com.opinion.utils.DateUtils;
import com.opinion.utils.PageUtils;
import com.opinion.utils.SNUtil;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangtong
 * Created by on 2017/11/16
 */
@Service
public class IssuedNoticeServiceImpl implements IssuedNoticeService {

    @Autowired
    private IssuedNoticeRepository issuedNoticeRepository;

    @Autowired
    private IssuedNoticeLogService issuedNoticeLogService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMessageService sysMessageService;


    @Override
    public IssuedNotice save(IssuedNotice issuedNotice) {
        SysUser sysUser = new SysUserConst().getSysUser();
        Long userId = sysUser.getId();
        LocalDate currentDate = DateUtils.currentDate();
        LocalDateTime currentDatetime = DateUtils.currentDatetime();

        issuedNotice.setNoticeCode(SNUtil.create15());
        issuedNotice.setReceiptState(SysConst.ReceiptState.UNRECEIPT.getCode());
        issuedNotice.setPublishDatetime(currentDatetime);
        issuedNotice.setCreatedDatetime(currentDatetime);
        issuedNotice.setCreatedDate(currentDate);
        issuedNotice.setCreatedUserId(userId);
        issuedNotice.setModifiedDatetime(currentDatetime);
        issuedNotice.setModifiedDate(currentDate);
        issuedNotice.setModifiedUserId(userId);

        issuedNotice = issuedNoticeRepository.save(issuedNotice);

        String noticeRange = issuedNotice.getNoticeRange();
        List<Long> childIds = Lists.newArrayList();
        //全部
        if (noticeRange.equals(SysConst.NoticeRange.ALL.getCode())) {
            childIds = sysUserService.findDescendantAllIdListByParentId(userId);
            //市级
        } else if (noticeRange.equals(SysConst.NoticeRange.MUNICIPAL.getCode())) {
            childIds = sysUserService.findChildIdListByParentId(userId);
            //县级
        } else if (noticeRange.equals(SysConst.NoticeRange.COUNTY.getCode())) {
            childIds = sysUserService.findDescendantIdListByParentId(userId);
        }

        String noticeCode = issuedNotice.getNoticeCode();
        List<IssuedNoticeLog> issuedNoticeLogs = childIds.stream()
                .map(childId -> {
                    IssuedNoticeLog issuedNoticeLog = new IssuedNoticeLog();
                    issuedNoticeLog.setNoticeCode(noticeCode);
                    issuedNoticeLog.setReceiptState(SysConst.ReceiptState.UNREAD.getCode());
                    issuedNoticeLog.setReceiptUserId(childId);
                    issuedNoticeLog.setCreatedDate(currentDate);
                    issuedNoticeLog.setCreatedDatetime(currentDatetime);
                    issuedNoticeLog.setCreatedUserId(userId);
                    return issuedNoticeLog;
                }).collect(Collectors.toList());
        issuedNoticeLogService.save(issuedNoticeLogs);

        /**
         * 保存系统消息
         */
        StringBuilder title = new StringBuilder();
        title.append("用户：").append(sysUser.getUserName())
                .append("下发了新的通知");
        StringBuilder subtitle = new StringBuilder();
        subtitle.append("《").append(issuedNotice.getTitle()).append("》");
       List<SysMessage> sysMessages =  childIds.stream()
                .map(childId->{
                   SysMessage sysMessage = new SysMessage();
                   sysMessage.setRelationUserId(childId);
                   sysMessage.setTitle(title.toString());
                   sysMessage.setSubtitle(subtitle.toString());
                   return sysMessage;
                }).collect(Collectors.toList());
        sysMessageService.save(sysMessages);

        return issuedNotice;
    }

    @Override
    public IssuedNotice findOneByNoticeCode(String noticeCode) {
        return issuedNoticeRepository.findByNoticeCode(noticeCode);
    }

    @Override
    public Page<IssuedNotice> findPageByCreatedUserId(IssuedNotice issuedNotice) {
        Specification<IssuedNotice> specification = new Specification<IssuedNotice>() {
            @Override
            public Predicate toPredicate(Root<IssuedNotice> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                query.where(builder.and(builder.equal(root.get("createdUserId").as(Long.class), issuedNotice.getCreatedUserId())));
                if (StringUtils.isNotEmpty(issuedNotice.getTitle())) {
                    query.where(builder.and(builder.like(root.get("title").as(String.class), "%" + issuedNotice.getTitle() + "%")));

                }
                if (StringUtils.isNotEmpty(issuedNotice.getReceiptState())) {
                    query.where(builder.and(builder.equal(root.get("receiptState").as(String.class), issuedNotice.getReceiptState())));
                }
                return null;
            }
        };
        Pageable pageable = PageUtils.buildPageRequest(issuedNotice.getPageNum(),
                issuedNotice.getPageSize(),
                issuedNotice.getSortParam(),
                issuedNotice.getSortParam());
        Page<IssuedNotice> result = issuedNoticeRepository.findAll(specification, pageable);
        return result;
    }

    @Override
    public Page<IssuedNotice> findPageByReceiptUserId(IssuedNotice issuedNotice) {
        Long receiptUserId = issuedNotice.getReceiptUserId();
        List<IssuedNoticeLog> issuedNoticeLogs = issuedNoticeLogService.findListByReceiptUserId(receiptUserId);
        List<String> noticeCodes = issuedNoticeLogs.stream().map(IssuedNoticeLog::getNoticeCode).collect(Collectors.toList());

        Specification<IssuedNotice> specification = new Specification<IssuedNotice>() {
            @Override
            public Predicate toPredicate(Root<IssuedNotice> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                CriteriaBuilder.In<String> in = builder.in(root.get("noticeCode").as(String.class));
                noticeCodes.forEach(noticeCode -> in.value(noticeCode));
                query.where(in);
                if (StringUtils.isNotEmpty(issuedNotice.getTitle())) {
                    query.where(builder.and(builder.like(root.get("title").as(String.class), "%" + issuedNotice.getTitle() + "%")));

                }
                if (StringUtils.isNotEmpty(issuedNotice.getReceiptState())) {
                    query.where(builder.and(builder.equal(root.get("receiptState").as(String.class), issuedNotice.getReceiptState())));
                }
                return null;
            }
        };
        Pageable pageable = PageUtils.buildPageRequest(issuedNotice.getPageNum(),
                issuedNotice.getPageSize(),
                issuedNotice.getSortParam(),
                issuedNotice.getSortParam());
        Page<IssuedNotice> result = issuedNoticeRepository.findAll(specification, pageable);
        return result;
    }

    @Override
    public IssuedNotice replyExecution(String noticeCode) {
        Long userId = new SysUserConst().getUserId();
        LocalDateTime currentDatetime = DateUtils.currentDatetime();
        IssuedNoticeLog issuedNoticeLog = issuedNoticeLogService.findByNoticeCodeAndReceiptUserId(noticeCode, userId);
        if (issuedNoticeLog != null) {
            issuedNoticeLog.setReceiptUserId(userId);
            issuedNoticeLog.setReceiptState(SysConst.ReceiptState.RECEIPT.getCode());
        }

        long allIssuedNoticeLogCount = issuedNoticeLogService.findCountByNoticeCode(noticeCode);
        long receiptIssuedNoticeLogCount = issuedNoticeLogService
                .findCountByNoticeCodeAndReceiptState(noticeCode, SysConst.ReceiptState.RECEIPT.getCode());
        IssuedNotice issuedNotice = issuedNoticeRepository.findByNoticeCode(noticeCode);
        if (allIssuedNoticeLogCount != receiptIssuedNoticeLogCount) {
            issuedNotice.setReceiptState(SysConst.ReceiptState.RECEIPTING.getCode());
        } else {
            issuedNotice.setReceiptState(SysConst.ReceiptState.RECEIPT.getCode());
            issuedNotice.setReceiptDatetime(currentDatetime);
        }
        return issuedNoticeRepository.save(issuedNotice);
    }
}
