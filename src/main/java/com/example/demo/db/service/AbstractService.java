package com.example.demo.db.service;

import com.example.demo.db.mapper.BaseMapper;
import com.example.demo.db.model.ApplyBlink;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import com.example.demo.db.model.ApplyBlinkPK;

import java.util.List;

/**
 * @Author CcQun
 * @Date 2020/5/19 15:27
 */
public abstract class AbstractService<E, PK> {
    protected abstract BaseMapper<E, PK> getMapper();

    // 查询所有
    public List<E> findAll() {
        return getMapper().findAll();
    }

    // 按页查询所有
    public Page<E> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<E> Page = getMapper().findAll(pageable);
        return Page;
    }

    // Example动态查询
    public List<E> findAll(E e) {
        Example<E> example = Example.of(e);
        List<E> list = getMapper().findAll(example);
        return list;
    }

    // Example动态查询、分页
    public Page<E> findAll(E e, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Example<E> example = Example.of(e);
        Page<E> Page = getMapper().findAll(example, pageable);
        return Page;
    }

    // Specification条件查询、分页
    public Page<E> findAll(Specification<E> specification, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<E> Page = getMapper().findAll(specification, pageable);
        return Page;
    }

    public List<E> findAll(Specification<E> specification) {
        List<E> list = getMapper().findAll(specification);
        return list;
    }


}
