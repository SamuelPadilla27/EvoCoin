package com.crypto.EvoCoin.alarm.model;

import com.crypto.EvoCoin.alarm.entity.Alarm;
import com.crypto.EvoCoin.common.enums.AlarmType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlarmDetail {

    private Long id;
    private AlarmType alarmType;
    private Long userId;
    private Long cryptoId;



    public AlarmDetail(Alarm pAlarm, Long pUserId, Long pCryptoId){
        this.id = pAlarm.getId();
        this.alarmType = getAlarmType();
        this.userId = pUserId;
        this.cryptoId = pCryptoId;
    }
}
