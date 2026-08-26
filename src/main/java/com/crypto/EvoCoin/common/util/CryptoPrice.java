package com.crypto.EvoCoin.common.util;

import java.math.BigDecimal;

public record CryptoPrice(
    BigDecimal open,
    BigDecimal close,
    BigDecimal high,
    BigDecimal low,
    long timestamp
) {
}
