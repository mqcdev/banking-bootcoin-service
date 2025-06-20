package com.nttdata.banking.bootcoin.consumer.mapper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

/**
 * Class BalanceBootCoinModel.
 * BootCoin microservice class BalanceBootCoinModel.
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BalanceBootCoinModel {

    @JsonIgnore
    private String id;

    private String idBootCoin;

    private Double balance;
}
