package com.nttdata.banking.bootcoin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Class msbootCoinApplication Main.
 * BootCoin microservice class msbootCoinApplication.
 */
@SpringBootApplication
@EnableEurekaClient
public class MsBootCoinApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsBootCoinApplication.class, args);
	}

}
