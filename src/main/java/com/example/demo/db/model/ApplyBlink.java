package com.example.demo.db.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name="APPLY_BLINK")
public class ApplyBlink {
    @Id
    private int blinkNumber;
    private int studentNumber;
}
