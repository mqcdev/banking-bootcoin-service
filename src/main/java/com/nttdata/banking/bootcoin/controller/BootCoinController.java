package com.nttdata.banking.bootcoin.controller;

import java.net.URI;
import java.util.Map;
import java.util.Date;
import java.util.HashMap;
import javax.validation.Valid;

import com.nttdata.banking.bootcoin.dto.BootCoinDto;
import com.nttdata.banking.bootcoin.model.BootCoin;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.nttdata.banking.bootcoin.application.BootCoinService;

@RestController
@RequestMapping("/api/bootcoin")
@Slf4j
public class BootCoinController {
    @Autowired
    private BootCoinService service;

    @GetMapping
    public Mono<ResponseEntity<Flux<BootCoin>>> listBootCoins() {
        return Mono.just(ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findAll()));
    }

    @GetMapping("/{idBootCoin}")
    public Mono<ResponseEntity<BootCoin>> getBootCoinDetails(@PathVariable("idBootCoin") String idClient) {
        return service.findById(idClient)
                .map(c -> ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON).body(c))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/documentNumber/{documentNumber}")
    public Mono<ResponseEntity<BootCoinDto>> getBootCoinByDocumentNumber(@PathVariable("documentNumber") String documentNumber) {
        log.info("GetMapping--getBootCoinByAccountNumber-------documentNumber: " + documentNumber);
        return service.findByDocumentNumber(documentNumber)
                .map(c -> ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(c))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Map<String, Object>>> saveBootCoin(@Valid @RequestBody Mono<BootCoinDto> BootCoinDto) {
        Map<String, Object> request = new HashMap<>();
        return BootCoinDto
                .flatMap(bnkAcc -> service.save(bnkAcc)
                        .map(baSv -> {
                            request.put("bootcoin", baSv);
                            request.put("message", "Monedero Movil guardado con exito");
                            request.put("timestamp", new Date());
                            return ResponseEntity.created(URI.create("/api/bootCoin/".concat(baSv.getIdBootCoin())))
                                    .contentType(MediaType.APPLICATION_JSON).body(request);
                        })
                );
    }

    @PutMapping("/{idBootCoin}")
    public Mono<ResponseEntity<BootCoin>> editBootCoin(@Valid @RequestBody BootCoinDto bootCoinDto, @PathVariable("idBootCoin") String idBootCoin) {
        return service.update(bootCoinDto, idBootCoin)
                .map(c -> ResponseEntity.created(URI.create("/api/bootCoin/".concat(idBootCoin)))
                        .contentType(MediaType.APPLICATION_JSON).body(c));
    }

    @PutMapping("/{idBootCoin}/balance/{balance}")
    public Mono<ResponseEntity<BootCoin>> editBalanceBootCoin(@PathVariable("idBootCoin") String idBootCoin, @PathVariable("balance") Double balance) {

        log.info("PutMapping--editBalanceBootCoin-------idBootCoin: " + idBootCoin);
        log.info("PutMapping--editBalanceBootCoin-------balance: " + balance);
        return service.updateBalanceById(idBootCoin, balance)
                .map(c -> ResponseEntity.created(URI.create("/api/bootCoin/".concat(idBootCoin)))
                        .contentType(MediaType.APPLICATION_JSON).body(c));
    }

    @DeleteMapping("/{idBootCoin}")
    public Mono<ResponseEntity<Void>> deleteBootCoin(@PathVariable("idBootCoin") String idBootCoin) {
        return service.delete(idBootCoin)
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
    }

}
