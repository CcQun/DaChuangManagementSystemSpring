package com.example.demo.db.service;

import com.example.demo.db.mapper.BlinkMapper;
import com.example.demo.db.mapper.StudentMapper;
import com.example.demo.db.model.Blink;
import com.example.demo.db.model.Student;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author CcQun
 * @Date 2020/5/18 20:32
 */
@Service
public class BlinkService extends BaseService<Blink,Integer, BlinkMapper>{
    private final BlinkMapper BlinkMapper;


    public BlinkService(BlinkMapper blinkMapper) {
        this.BlinkMapper = blinkMapper;
    }
    public List<Blink> findAll(){
        return BlinkMapper.findAll();
    }
}