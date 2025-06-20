package com.nttdata.banking.bootcoin.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Class BootCoin.
 * BootCoin microservice class BootCoin.
 */
@Document(collection = "BootCoin")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BootCoin {

    @Id
    private String idBootCoin;

    private Client client;

    private Double balance;
}
