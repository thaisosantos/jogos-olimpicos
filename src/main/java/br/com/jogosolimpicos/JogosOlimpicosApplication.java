package br.com.jogosolimpicos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class JogosOlimpicosApplication{

	public static void main(String[] args) {
		SpringApplication.run(JogosOlimpicosApplication.class, args);
	}
}
