package br.com.jogosolimpicos.integacao.service;

import br.com.jogosolimpicos.integacao.client.WeatherClient;
import br.com.jogosolimpicos.integacao.response.ClimaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    @Value("${app_id}")
    private String apiId;

    @Autowired
    private WeatherClient weatherClient;

    private static final String CIDADE = "Tokyo";
    private static final String UNIDADE = "metric";

    public ClimaResponse consultaClima(){
        return weatherClient.consultaClima(CIDADE,apiId,UNIDADE).getBody();
    }
}
