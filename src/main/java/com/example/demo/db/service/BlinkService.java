package com.example.demo.db.service;

import com.example.demo.db.mapper.ApplyBlinkMapper;
import com.example.demo.db.mapper.BlinkMapper;
import com.example.demo.db.mapper.StudentMapper;
import com.example.demo.db.model.ApplyBlink;
import com.example.demo.db.model.ApplyBlinkPK;
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
    private final ApplyBlinkMapper applyBlinkMapper;

    public BlinkService(BlinkMapper blinkMapper,ApplyBlinkMapper applyBlinkMapper) {
        this.BlinkMapper = blinkMapper;
        this.applyBlinkMapper=applyBlinkMapper;
    }
    public List<Blink> findAll(){
        return BlinkMapper.findAll();
    }

    public boolean delete(Integer blink_num){

        try{
            applyBlinkMapper.deleteByblinknum(blink_num);
            BlinkMapper.deleteById(blink_num);
            return true;
        }catch (Exception e){
            System.out.println("不存在该Blink");
            return false;
        }
    }

    public boolean changeState(Blink blink,int blinkapproval,int oldapproval){
        int state=blink.getBlink_state();
        if(blinkapproval==2){
            if(oldapproval==1){
                blink.setBlink_state(state-1);
                try{
                    mapper.save(blink);
                    return true;
                }catch (Exception e){
                    e.printStackTrace();
                    return false;
                }
            }
            else {
                return true;
            }
        }
        else {
            if(oldapproval==1){
                return true;
            }
            else{
                if(state==3){
                    return false;
                }
                else {
                    blink.setBlink_state(state+1);
                    try{
                        mapper.save(blink);
                        return true;
                    }catch (Exception e){
                        e.printStackTrace();
                        return false;
                    }
                }
            }

        }

    }
    public List<Blink> findAllByBlinkNumber(int blinkNumber){
        Blink blink=Blink.builder().blink_number(blinkNumber).build();
        Example<Blink> example = Example.of(blink);
        List<Blink> list = mapper.findAll(example);
        return list;
    }
}