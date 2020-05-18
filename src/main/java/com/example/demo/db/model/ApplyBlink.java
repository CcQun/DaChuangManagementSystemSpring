package com.example.demo.db.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="APPLY_BLINK")
public class ApplyBlink {
    @EmbeddedId
    private ApplyBlinkPK applyBlinkPK;

    private int Blink_Approval;

    public int getBlink_Approval() {
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

}
