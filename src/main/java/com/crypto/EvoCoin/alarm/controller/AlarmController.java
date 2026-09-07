package com.crypto.EvoCoin.alarm.controller;

import com.crypto.EvoCoin.alarm.model.AlarmDetail;
import com.crypto.EvoCoin.alarm.service.AlarmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
public class AlarmController {

    private final AlarmService alarmService;

    public AlarmController(AlarmService alarmService) {
        this.alarmService = alarmService;
    }

    @GetMapping(value = "/api/alarm/findAlarmByUserId/{pUserId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AlarmDetail> findAlarmByUserId(@PathVariable("pUserId") Long pUserId){
        try {
            return alarmService.findAlarmByUserId(pUserId);
        } catch (Exception e) {
            log.error("Failed: findAlarmByUserId", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PostMapping(value = "/api/alarm/saveAlarm", consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
    public void saveAlarm(@RequestBody AlarmDetail pAlarm){
        try {
            alarmService.saveAlarm(pAlarm);
        } catch (Exception e) {
            log.error("Failed: saveAlarm", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
}
