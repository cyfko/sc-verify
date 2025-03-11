package com.github.cyfko.sc_verify;

import io.github.cyfko.dverify.DataSigner;
import io.github.cyfko.dverify.DataVerifier;
import io.github.cyfko.dverify.exceptions.JsonEncodingException;
import io.github.cyfko.dverify.impl.kafka.KafkaDataSigner;
import io.github.cyfko.dverify.impl.kafka.KafkaDataVerifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Duration;

@SpringBootApplication
public class ScVerifyApplication {

	@Bean
	public DataSigner dataSigner() {
		return new KafkaDataSigner();
	}

	@Bean
	public DataVerifier dataVerifier() {
		return new KafkaDataVerifier();
	}

	public static void main(String[] args) {
		SpringApplication.run(ScVerifyApplication.class, args);
	}

}
