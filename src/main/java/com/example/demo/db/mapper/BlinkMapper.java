package com.example.demo.db.mapper;

import com.example.demo.db.model.Blink;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author CcQun
 * @Date 2020/5/18 20:29
 */
public interface BlinkMapper extends JpaRepository<Blink,Integer> {

}