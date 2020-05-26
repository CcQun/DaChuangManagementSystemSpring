package com.example.demo.db.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="APPLY_BLINK")
public class ApplyBlink implements Persistable {
    @EmbeddedId
    private ApplyBlinkPK applyBlinkPK;

    @Column(name = "Blink_Approval")
    private Integer Blink_Approval;

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
