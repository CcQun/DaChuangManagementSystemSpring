package com.example.demo.db.model;

import lombok.Data;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Entity
@Data
@Table(name="APPLY_BLINK")
public class ApplyBlink implements Persistable {
    @EmbeddedId
    private ApplyBlinkPK applyBlinkPK;

    private Integer Blink_Approval;

    public Integer getBlink_Approval() {
        return Blink_Approval;
    }

    public void setBlink_Approval(int blink_Approval) {
        Blink_Approval = blink_Approval;
    }
    
    public ApplyBlinkPK getApplyBlinkPK() {
        return applyBlinkPK;
    }

    public void setApplyBlinkPK(ApplyBlinkPK applyBlinkPK) {
        this.applyBlinkPK = applyBlinkPK;
    }

    public String toString(){
        return "applyblink= "+getApplyBlinkPK().toString();
    }

    @Override
    public Object getId() {
        return applyBlinkPK;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
