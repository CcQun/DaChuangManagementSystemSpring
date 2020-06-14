package com.example.demo.db.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author CcQun
 * @Date 2020/6/14 10:22
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="APPLY_DIRECT")
public class ApplyDirect implements Persistable {
    @EmbeddedId
    private ApplyDirectPK applyDirectPK;

    @Column(name = "Direct_Approval")
    private Integer Direct_Approval;

    @Override
    public String toString(){
        return "applyDirect= "+getApplyDirectPK().toString();
    }

    @Override
    public Object getId() {
        return applyDirectPK;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
