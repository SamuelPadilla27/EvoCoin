package com.crypto.EvoCoin.alarm.dao;

import com.crypto.EvoCoin.alarm.entity.Alarm;
import com.crypto.EvoCoin.alarm.model.AlarmDetail;
import com.crypto.EvoCoin.common.abstractDao.AbstractDao;
import com.crypto.EvoCoin.common.enums.AlarmType;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class AlarmDao extends AbstractDao<Alarm, Long> {

    public AlarmDao() {
        this.setClazz(Alarm.class);
    }

    public List<AlarmDetail> findAlarmsByAlarmType(AlarmType pAlarmType){
        try {
            return entityManager.createQuery("SELECT new com.crypto.EvoCoin.alarm.model.AlarmDetail(alarm, user.id, crypto.id) FROM Alarm AS alarm " +
                    "INNER JOIN User AS user ON user.id = alarm.user.id " +
                    "INNER JOIN Crypto AS crypto ON crypto.id = alarm.crypto.id " +
                    "WHERE alarm.alarmType = :pAlarmType ", AlarmDetail.class)
                    .setParameter("pAlarmType", pAlarmType)
                    .getResultStream()
                    .toList();
        } catch (Exception e) {
            log.error("Failed: findAlarmsByAlarmType", e);
            throw new RuntimeException("Failed: findAlarmsByAlarmType",e);
        }
    }

    public List<AlarmDetail> findAlarmByUserId(Long pUserId){
        try {
            return entityManager.createQuery("SELECT new com.crypto.EvoCoin.alarm.model.AlarmDetail(alarm, user.id) FROM Alarm AS alarm " +
                    "INNER JOIN User AS user ON user.id = alarm.user.id " +
                    "WHERE user.id = :pUserId ", AlarmDetail.class)
                    .setParameter("pUserId", pUserId)
                    .getResultStream()
                    .toList();
        } catch (Exception e) {
            log.error("Failed: findAlarmByUserId", e);
            throw new RuntimeException("Failed: findAlarmByUserId",e);
        }
    }
}
