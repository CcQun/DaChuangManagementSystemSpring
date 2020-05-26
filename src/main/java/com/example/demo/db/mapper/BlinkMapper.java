package com.example.demo.db.mapper;

import com.example.demo.db.model.ApplyBlink;
import com.example.demo.db.model.ApplyBlinkPK;
import com.example.demo.db.model.Blink;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author CcQun
 * @Date 2020/5/18 20:29
 */
public interface BlinkMapper extends BaseMapper<Blink,Integer> {

}
