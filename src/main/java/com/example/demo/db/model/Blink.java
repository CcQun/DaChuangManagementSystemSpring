package com.example.demo.db.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name="BLINK")
public class Blink {
    @Id
    private Integer blinkNumber;
    private Integer studentNumber;
    private String blinkTitle;
    private String blinkContent;
    private Date creatTime;
}
