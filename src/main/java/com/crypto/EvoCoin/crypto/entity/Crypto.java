package com.crypto.EvoCoin.crypto.entity;

import com.crypto.EvoCoin.common.enums.CryptoState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "crypto")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Crypto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "crypto", nullable = false, length = 10)
    private String crypto;

    @Enumerated(EnumType.STRING)
    @Column(name = "crypto_state", nullable = false, length = 20)
    private CryptoState cryptoState;

    public Crypto(Long pId){
        this.Id = pId;
    }
}
