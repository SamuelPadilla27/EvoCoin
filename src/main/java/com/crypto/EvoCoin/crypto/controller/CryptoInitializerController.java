package com.crypto.EvoCoin.crypto.controller;

import com.crypto.EvoCoin.crypto.service.GeneralCryptoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class CryptoInitializerController {
    private final GeneralCryptoService generalCryptoService;

    public CryptoInitializerController(GeneralCryptoService generalCryptoService) {
        this.generalCryptoService = generalCryptoService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void cryptoInitialization(){


        generalCryptoService.initializeCacheData("BTCUSDT", "5m").subscribe(
                result -> log.info("initializing crypto values success"),
                error -> log.error(
                        "Error initializing crypto values",
                        error
                ),
                () -> log.info(
                        "========== Initialization of crypto values complete =========="
                )
        );
    }
}
