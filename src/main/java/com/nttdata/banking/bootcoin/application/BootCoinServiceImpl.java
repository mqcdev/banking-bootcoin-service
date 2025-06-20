package com.nttdata.banking.bootcoin.application;

import com.nttdata.banking.bootcoin.dto.BootCoinDto;
import com.nttdata.banking.bootcoin.exception.ResourceNotFoundException;
import com.nttdata.banking.bootcoin.infrastructure.BootCoinRepository;
import com.nttdata.banking.bootcoin.model.BootCoin;
import com.nttdata.bootcamp.msbootcoin.infrastructure.*;
import com.nttdata.bootcamp.msbootcoin.model.*;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
@Slf4j
public class BootCoinServiceImpl implements BootCoinService {
    @Autowired
    private BootCoinRepository bootCoinRepository;

    @Override
    public Flux<BootCoin> findAll() {
        return bootCoinRepository.findAll();
    }

    @Override
    public Mono<BootCoin> findById(String idBootCoin) {
        return Mono.just(idBootCoin)
                .flatMap(bootCoinRepository::findById)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Monedero bootcoin", "idBootCoin", idBootCoin)));
    }

    @Override
    public Mono<BootCoinDto> findByDocumentNumber(String documentNumber) {
        return Mono.just(documentNumber)
                .flatMap(bootCoinRepository::findByDocumentNumber)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Monedero bootcoin", "documentNumber", documentNumber)));
    }

    @Override
    public Mono<BootCoin> save(BootCoinDto bootCoinDto) {
        log.info("----save-------bootCoinDto : " + bootCoinDto.toString());
        return Mono.just(bootCoinDto)
                .flatMap(mwd -> validateNumberClientAccounts(mwd, "save").then(Mono.just(mwd)))
                .flatMap(mwd -> mwd.mapperToBootCoin())
                .flatMap(bootCoinRepository::save);
    }

    @Override
    public Mono<BootCoin> update(BootCoinDto bootCoinDto, String idBootCoin) {
        log.info("----update-------bootCoinDto -- idBootCoin: " + bootCoinDto.toString() + " -- " + idBootCoin);
        return Mono.just(bootCoinDto)
                .flatMap(mwd -> validateNumberClientAccounts(mwd, "update").then(Mono.just(mwd)))
                .flatMap(mwd -> mwd.mapperToBootCoin())
                .flatMap(mwd -> bootCoinRepository.findById(idBootCoin)
                        .switchIfEmpty(Mono.error(new ResourceNotFoundException("Monedero bootcoin", "idBootCoin", idBootCoin)))
                        .flatMap(x -> {
                            mwd.setIdBootCoin(x.getIdBootCoin());
                            return bootCoinRepository.save(mwd);
                        })
                );
    }

    @Override
    public Mono<Void> delete(String idBootCoin) {
        return Mono.just(idBootCoin)
                .flatMap(b -> bootCoinRepository.findById(b))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Monedero bootcoin", "idBootCoin", idBootCoin)))
                .flatMap(bootCoinRepository::delete);
    }

    public Mono<Boolean> validateNumberClientAccounts(BootCoinDto BootCoinDto, String method) {
        log.info("--validateNumberClientAccounts-------: ");
        if (method.equals("save")) {
            return bootCoinRepository.findAllByCellphone(BootCoinDto.getCellphone())
                    .count().flatMap(cnt -> {
                        if (cnt >= 1) {
                            return Mono.error(new ResourceNotFoundException("Monedero bootcoin ya existe : Cellphone", BootCoinDto.getCellphone()));
                        } else {
                            return Mono.just(true);
                        }
                    });
        } else {
            return Mono.just(true);
        }
    }

    @Override
    public Mono<BootCoin> updateBalanceById(String idBootCoin, Double balance) {
        return bootCoinRepository.findById(idBootCoin)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Monedero bootcoin", "idBootCoin", idBootCoin)))
                .flatMap(x -> {
                    x.setBalance(balance);
                    return bootCoinRepository.save(x);
                });
    }

}
