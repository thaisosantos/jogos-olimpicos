package br.com.jogosolimpicos.integracao;

import br.com.jogosolimpicos.integacao.client.WeatherClient;
import br.com.jogosolimpicos.integacao.response.ClimaResponse;
import br.com.jogosolimpicos.integacao.service.WeatherService;
import br.com.jogosolimpicos.templates.ClimaResponseTemplate;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static br.com.six2six.fixturefactory.Fixture.from;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
public class WeatherServiceTest {

    @Mock
    private WeatherClient weatherClient;
    @InjectMocks
    private WeatherService service;

    @BeforeEach
    public void setUp(){
        FixtureFactoryLoader.loadTemplates("br.com.jogosolimpicos.templates");
    }

    @Test
    @DisplayName("Deve retornar o clima de Tokyo com sucesso")
    public void consultaClima(){
        //GIVEN
        ClimaResponse climaResponse = from(ClimaResponse.class).gimme(ClimaResponseTemplate.VALIDO);

        given(weatherClient.consultaClima(any(),any(),any())).willReturn(ResponseEntity.ok(climaResponse));

        //WHEN
        ClimaResponse clima = service.consultaClima();

        //THAT
        assertThat(clima).isNotNull();
    }

}
