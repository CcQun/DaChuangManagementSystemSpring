package com.example.demo.db.model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Entity
@Data
@Table(name="BLINK")
public class Blink {
    @Id
    private int blinkNumber;
    private int studentNumber;
    private String blinkTitle;
    private String blinkContent;
    private Date creatTime;
}
