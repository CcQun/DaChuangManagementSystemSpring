package com.example.demo.db.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
@Data
public class ApplyBlinkPK implements Serializable {

    @Column(name="Blink_Number")
    private Integer blinknum;
    @Column(name="Student_Number")
    private Integer studentnum;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ApplyBlinkPK)) return false;

        ApplyBlinkPK orderPK = (ApplyBlinkPK) o;

        if (blinknum != null ? !blinknum.equals(orderPK.blinknum) : orderPK.blinknum != null) return false;
        return studentnum != null ? studentnum.equals(orderPK.studentnum) : orderPK.studentnum == null;
    }

    @Override
    public int hashCode() {
        int result = blinknum != null ? blinknum.hashCode() : 0;
        result = 31 * result + (studentnum != null ? studentnum.hashCode() : 0);
        return result;
    }
    public String toString(){
        return "applyblinkPK: blinknum= "+blinknum+",  "+"studentnum= "+studentnum;
    }
}
