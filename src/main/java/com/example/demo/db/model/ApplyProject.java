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

@Table(name = "APPLY_PROJECT")
public class ApplyProject implements Persistable {
    @EmbeddedId
    private ApplyProjectPK applyProjectPK;

    @Column(name = "Project_Approval")
    private Integer Project_Approval;

    public String toString() {
        return "applyproject= " + getApplyProjectPK();
    }

    @Override
    public Object getId() {
        return applyProjectPK;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
