package com.crypto.EvoCoin.crypto.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@Slf4j
@Service
public class GeneralCryptoService {

    private final CryptoCacheService cryptoCacheService;
    private final BinanceRestClientService binanceRestClientService;


    public GeneralCryptoService(CryptoCacheService cryptoCacheService, BinanceRestClientService binanceRestClientService) {
        this.cryptoCacheService = cryptoCacheService;
        this.binanceRestClientService = binanceRestClientService;
    }

    public Mono<Void> initializeCacheData(String symbol, String interval){

        String key = "crypto" + symbol + ":" + interval;

        return binanceRestClientService.getLast100(symbol, interval)
                .doOnSuccess(prices ->
                        cryptoCacheService.initialize(key, prices)
                ).doOnSuccess(prices ->
                        log.info(
                                "Cache initialized with "+ prices.size() + " datos"
                        )
                ).doOnError(error ->
                        log.info(
                                "Error initializing cache",
                                error
                        )
                ).then(Mono.fromRunnable(() ->
                        binanceRestClientService.connect(symbol, interval)
                ));
    }
}
