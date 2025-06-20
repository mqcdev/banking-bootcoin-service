package com.nttdata.banking.bootcoin.dto;

import org.springframework.data.annotation.Id;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.nttdata.banking.bootcoin.model.Client;
import com.nttdata.banking.bootcoin.model.BootCoin;
import com.nttdata.banking.bootcoin.model.Movement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * Class BootCoinDto.
 * BootCoin microservice class BootCoinDto.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Slf4j
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BootCoinDto {

    @Id
    private String idBootCoin;

    private String documentNumber;

    private String cellphone;

    private String imei_cellphone;

    private String email;

    private String cardNumber;

    private Client client;

    private Double balance;

    private List<Movement> movements;

    public Mono<BootCoin> mapperToBootCoin() {
        log.info("ini mapperToBootCoin-------: ");

        Client client = Client.builder()
                .documentNumber(this.getDocumentNumber())
                .cellphone(this.getCellphone())
                .imei_cellphone(this.getImei_cellphone())
                .email(this.getEmail())
                .build();

        BootCoin bootCoin = BootCoin.builder()
                .idBootCoin(this.getIdBootCoin())
                .client(client)
                .balance(this.getBalance() == null ? 0 : this.getBalance())
                .build();
        log.info("fn MapperToBootCoin-------: ");
        return Mono.just(bootCoin);
    }
}