package com.example.demo.db.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.Date;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Table(name="TEAM")
public class Team  implements Persistable {
    @EmbeddedId
    private TeamPK teamPK;

    @Column(name = "Group_Description")
    private String group_description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    @Column(name="Join_Time")
    private Date join_time;

    @Override
    public Object getId() {
        return teamPK;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
