package com.example.demo.db.mapper;

import com.example.demo.db.model.ApplyBlink;
import com.example.demo.db.model.ApplyBlinkPK;
import com.example.demo.db.model.ApplyProject;
import com.example.demo.db.model.ApplyProjectPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface ApplyProjectMapper extends BaseMapper<ApplyProject, ApplyProjectPK> {
    @Modifying
    @Transactional
    @Query(value = "delete from apply_project where Project_Number=(:Project_Number)", nativeQuery = true)
    void deleteByProjectnum(Integer Project_Number);
}
