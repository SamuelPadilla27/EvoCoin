package com.crypto.EvoCoin.crypto.service;

import com.crypto.EvoCoin.common.util.CryptoPrice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Mono;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class BinanceRestClientService {

    private final ObjectMapper mapper;
    private final WebClient client;
    private final WebSocketClient ws = new ReactorNettyWebSocketClient();
    private final CryptoCacheService cryptoCacheService;

    public BinanceRestClientService(ObjectMapper mapper, WebClient client, CryptoCacheService cryptoCacheService) {
        this.mapper = mapper;
        this.client = client;
        this.cryptoCacheService = cryptoCacheService;
    }

    public Mono<List<CryptoPrice>> getLast100(
            String symbol,
            String interval) {

        log.info(symbol);
        log.info(interval);
        return  client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v3/klines")
                        .queryParam("symbol", symbol)
                        .queryParam("interval", interval)
                        .queryParam("limit", 100)
                        .build()
                )
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(x -> toSnapshots(x));
    }

    public void connect(String symbol, String interval) {

        String key = "crypto" + symbol + ":" + interval;

        String url = "wss://stream.binance.com:9443/ws/"
                + symbol.toLowerCase()
                + "@kline_" + interval;

        ws.execute(URI.create(url),
                session -> session.receive()
                        .map(WebSocketMessage::getPayloadAsText)
                        .map(this::toSnapshot)
                        .doOnNext(x -> cryptoCacheService.update(key, x))
                        .then()
        ).subscribe();
    }

    private CryptoPrice toSnapshot(String payload) {
        try {
            JsonNode root = mapper.readTree(payload);
            JsonNode kline = root.get("k");

            CryptoPrice snapshot = new CryptoPrice(
                    new BigDecimal(kline.get("o").asText()),
                    new BigDecimal(kline.get("c").asText()),
                    new BigDecimal(kline.get("h").asText()),
                    new BigDecimal(kline.get("l").asText()),
                    kline.get("t").asLong()
            );

            return snapshot;
        }catch(Exception e) {
            throw new RuntimeException("Error parsing Binance kline payload", e);
        }
    }

    private List<CryptoPrice> toSnapshots(JsonNode array) {

        List<CryptoPrice> list = new ArrayList<>();

        for (JsonNode kline : array) {
            list.add(new CryptoPrice(
                    kline.get(1).asDecimal(),
                    kline.get(4).asDecimal(),
                    kline.get(2).asDecimal(),
                    kline.get(3).asDecimal(),
                    kline.get(0).asLong()));
        }

        // IMPORTANTE: más reciente primero
        Collections.reverse(list);
        return list;
    }
}
