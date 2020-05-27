package com.example.demo.db.service;

import com.example.demo.db.mapper.TeamMapper;
import com.example.demo.db.model.ApplyProjectPK;
import com.example.demo.db.model.Team;
import com.example.demo.db.model.TeamPK;
import org.springframework.stereotype.Service;

@Service
public class TeamService extends BaseService<Team, TeamPK, TeamMapper> {

}
