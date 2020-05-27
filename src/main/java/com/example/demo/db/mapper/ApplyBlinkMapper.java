package com.example.demo.db.mapper;

import com.example.demo.db.model.ApplyBlink;
import com.example.demo.db.model.ApplyBlinkPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface ApplyBlinkMapper extends BaseMapper<ApplyBlink, ApplyBlinkPK> {
    @Modifying
    @Transactional
    @Query(value = "delete from apply_blink where Blink_Number=(:Blink_Number)", nativeQuery = true)
    void deleteByblinknum(Integer Blink_Number);
}
