package com.example.demo.db.mapper;

import com.example.demo.db.model.ApplyProjectPK;
import com.example.demo.db.model.Team;
import com.example.demo.db.model.TeamPK;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface TeamMapper extends BaseMapper<Team, TeamPK>  {
    @Modifying
    @Transactional
    @Query(value = "delete from team where Project_Number=(:Project_Number)", nativeQuery = true)
    void deleteByProjectnum(Integer Project_Number);
}
