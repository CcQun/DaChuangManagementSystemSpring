package com.example.demo.db.mapper;

import com.example.demo.db.model.ApplyBlinkPK;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * @Author CcQun
 * @Date 2020/5/19 15:22
 */
@NoRepositoryBean
public interface BaseMapper<E, PK> extends JpaRepository<E, PK>, JpaSpecificationExecutor<E> {

}
