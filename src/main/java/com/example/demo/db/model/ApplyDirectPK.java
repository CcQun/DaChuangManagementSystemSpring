package com.example.demo.db.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @Author CcQun
 * @Date 2020/6/14 10:24
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
@Data
public class ApplyDirectPK implements Serializable {
    @Column(name = "Project_Number")
    private Integer projectnum;
    @Column(name = "Teacher_Number")
    private Integer teachernum;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ApplyDirectPK)) return false;

        ApplyDirectPK orderPK = (ApplyDirectPK) o;

        if (projectnum != null ? !projectnum.equals(orderPK.teachernum) : orderPK.projectnum != null) return false;
        return teachernum != null ? teachernum.equals(orderPK.teachernum) : orderPK.teachernum == null;
    }

    @Override
    public int hashCode() {
        int result = projectnum != null ? projectnum.hashCode() : 0;
        result = 31 * result + (teachernum != null ? teachernum.hashCode() : 0);
        return result;
    }

    public String toString() {
        return "applyDirectPK: projectnum= " + projectnum + ",  " + "teachernum= " + teachernum;
    }
}
