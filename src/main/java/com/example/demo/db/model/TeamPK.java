package com.example.demo.db.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
@Data
public class TeamPK implements Serializable {

    @Column(name = "Project_Number")
    private Integer projectnum;
    @Column(name = "Student_Number")
    private Integer studentnum;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TeamPK)) return false;

        TeamPK orderPK = (TeamPK) o;

        if (projectnum != null ? !projectnum.equals(orderPK.projectnum) : orderPK.projectnum != null) return false;
        return studentnum != null ? studentnum.equals(orderPK.studentnum) : orderPK.studentnum == null;
    }

    @Override
    public int hashCode() {
        int result = projectnum != null ? projectnum.hashCode() : 0;
        result = 31 * result + (studentnum != null ? studentnum.hashCode() : 0);
        return result;
    }

    public String toString() {
        return "TeamPK: bprojectnum= " + projectnum + ",  " + "studentnum= " + studentnum;
    }
}
