package com.example.demo.db.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="APPLY_BLINK")
public class ApplyBlink {
    @EmbeddedId
    private ApplyBlinkPK applyBlinkPK;

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
