package com.opinion.mysql.repository;

import com.opinion.mysql.entity.IssuedNotice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author zhangtong
 * Created by on 2017/11/16
 */
public interface IssuedNoticeRepository extends CrudRepository<IssuedNotice, Long>, JpaSpecificationExecutor<IssuedNotice> {

    @Override
    Page<IssuedNotice> findAll(Specification<IssuedNotice> specification, Pageable pageable);

    IssuedNotice findByNoticeCode(String noticeCode);

    List<IssuedNotice> findByCreatedUserId(Long createdUserId);

}
