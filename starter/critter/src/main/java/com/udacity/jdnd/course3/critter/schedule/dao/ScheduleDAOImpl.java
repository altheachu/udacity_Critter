package com.udacity.jdnd.course3.critter.schedule.dao;

import com.udacity.jdnd.course3.critter.schedule.entity.Schedule;
import com.udacity.jdnd.course3.critter.schedule.utils.ScheduleSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class ScheduleDAOImpl implements ScheduleDAO{

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Long create(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getJdbcTemplate())
                .withSchemaName("pet")
                .withTableName("schedule")
                .usingGeneratedKeyColumns("schedule_id");
        return jdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(schedule)).longValue();
    }

    @Override
    public List<Schedule> findAll() {
        return jdbcTemplate.query(ScheduleSQL.findAll.getSql(),new BeanPropertyRowMapper<>(Schedule.class));
    }

    @Override
    public Schedule findById(Long id) {
        return jdbcTemplate.queryForObject(ScheduleSQL.findById.getSql(),
                new MapSqlParameterSource().addValue("scheduleId", id),
                new BeanPropertyRowMapper<>(Schedule.class));
    }

    @Override
    public Long update(Schedule schedule) {
        KeyHolder key = new GeneratedKeyHolder();
        jdbcTemplate.update(ScheduleSQL.update.getSql(),
                new MapSqlParameterSource().addValue(
                        "date",schedule.getDate()).addValue("scheduleId",schedule.getId()), key);
        return key.getKey().longValue();
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(ScheduleSQL.delete.getSql(), new MapSqlParameterSource().addValue("scheduleId",id));
    }
}
