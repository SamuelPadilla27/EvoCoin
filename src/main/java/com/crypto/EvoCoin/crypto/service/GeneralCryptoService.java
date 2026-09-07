package com.crypto.EvoCoin.crypto.service;

import com.crypto.EvoCoin.common.util.CryptoPrice;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;
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

//    public Mono<Void> sendMessage(String symbol, String interval){
//        return binanceRestClientService.getLast100(symbol, interval).doOnSuccess(
//                price -> {
//
//                }
//        )
//    }

    // Calculate Mean
    public static double calculateMean(List<CryptoPrice> pData, String symbol) {
        if (pData == null || pData.isEmpty()){
            log.error("The list of data used to calculate the average is empty{}", symbol);
            return 0.0;
        }
        OptionalDouble vMena = pData.stream()
                .map(CryptoPrice::close)
                .mapToDouble(BigDecimal::doubleValue).average();

        if(vMena.isPresent()){
            return vMena.getAsDouble();
        }else{
            log.error("Error in calculating the mean{}", symbol);
            return 0.0;
        }
    }

    // Calculate Standard Deviation
    public static double calculateStandardDeviation(List<CryptoPrice> pData, String symbol) {
        if (pData == null || pData.isEmpty()){
            log.error("The list of data used to calculate the standard deviation is empty{}", symbol);
            return 0.0;
        }

        double vMean = calculateMean(pData, symbol);

        double squaredDifferenceSum =  pData.stream()
                .map(ele -> Math.pow(ele.close().doubleValue() - vMean, 2))
                .mapToDouble(Double::doubleValue)
                .sum();

        double vVariance =  squaredDifferenceSum / (pData.size() - 1);

        return Math.sqrt(vVariance);
    }
}
