package com.udacity.jdnd.course3.critter.schedule.utils;

import com.udacity.jdnd.course3.critter.schedule.entity.Schedule;

public enum ScheduleSQL {

    findAll("select * from schedule"),
    findById("select * from schedule where schedule_id := scheduleId"),
    update("update schedule set date := date where schedule_id := scheduleId"),
    delete("delete from schedule where schedule_id := scheduleId");

    private String sql;

    ScheduleSQL(String sql){
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }
}
