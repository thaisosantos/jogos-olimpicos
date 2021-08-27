package br.com.jogosolimpicos.integacao.client;

import br.com.jogosolimpicos.integacao.response.ClimaResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "weatherClient",url = "${weather.url}")
public interface WeatherClient {

    @GetMapping(value = "/weather", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ClimaResponse> consultaClima(@RequestParam("q") final String cidade,
                                                @RequestParam("appid") final String appId,
                                                @RequestParam("units") final String unidadeMedida);
}
