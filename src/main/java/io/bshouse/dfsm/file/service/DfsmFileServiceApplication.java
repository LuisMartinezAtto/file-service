package io.bshouse.dfsm.file.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class DfsmFileServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(DfsmFileServiceApplication.class, args);
	}
}
