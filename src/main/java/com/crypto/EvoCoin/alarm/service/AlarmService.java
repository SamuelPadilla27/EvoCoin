package com.crypto.EvoCoin.alarm.service;

import com.crypto.EvoCoin.alarm.dao.AlarmDao;
import com.crypto.EvoCoin.alarm.entity.Alarm;
import com.crypto.EvoCoin.alarm.model.AlarmDetail;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class AlarmService {

    private final AlarmDao alarmDao;

    public AlarmService(AlarmDao alarmDao) {
        this.alarmDao = alarmDao;
    }

    @Transactional
    public void saveAlarm(AlarmDetail pAlarm){
        Alarm vAlarm = new Alarm(pAlarm);
        alarmDao.save(vAlarm);
    }

    @Transactional
    public List<AlarmDetail> findAlarmByUserId(Long pUserId){
        return alarmDao.findAlarmByUserId(pUserId);
    }
}
