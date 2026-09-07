package com.crypto.EvoCoin.alarm.entity;

import com.crypto.EvoCoin.alarm.model.AlarmDetail;
import com.crypto.EvoCoin.common.enums.AlarmType;
import com.crypto.EvoCoin.crypto.entity.Crypto;
import com.crypto.EvoCoin.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "alarm")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "alarm_type", nullable = false, length = 50)
    private AlarmType alarmType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_alarm_user")
    )
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "crypto_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_alarm_crypto")
    )
    private Crypto crypto;

    public Alarm(AlarmDetail pAlarm){
        this.id = pAlarm.getId();
        this.alarmType = pAlarm.getAlarmType();
        this.user = new User(pAlarm.getUserId());
        this.crypto = new Crypto(pAlarm.getId());
    }
}
