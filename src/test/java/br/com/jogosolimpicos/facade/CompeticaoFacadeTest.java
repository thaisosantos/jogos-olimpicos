package br.com.jogosolimpicos.facade;

import br.com.jogosolimpicos.controller.dto.ClimaDTO;
import br.com.jogosolimpicos.controller.dto.CompeticaoDTO;
import br.com.jogosolimpicos.exceptions.Response400Exception;
import br.com.jogosolimpicos.exceptions.Response404Exception;
import br.com.jogosolimpicos.integacao.response.ClimaResponse;
import br.com.jogosolimpicos.integacao.service.WeatherService;
import br.com.jogosolimpicos.model.entity.CompeticaoEntity;
import br.com.jogosolimpicos.service.CompeticaoService;
import br.com.jogosolimpicos.templates.ClimaDTOTemplate;
import br.com.jogosolimpicos.templates.ClimaResponseTemplate;
import br.com.jogosolimpicos.templates.CompeticaoDTOTemplate;
import br.com.jogosolimpicos.templates.CompeticaoEntityTemplate;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static br.com.six2six.fixturefactory.Fixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CompeticaoFacadeTest {

    @Mock
    private CompeticaoService competicaoService;
    @Mock
    private WeatherService weatherService;
    @InjectMocks
    private CompeticaoFacade competicaoFacade;

    @BeforeEach
    public void setUp(){
        FixtureFactoryLoader.loadTemplates("br.com.jogosolimpicos.templates");
    }

    @Test
    @DisplayName("Deve cadastrar competicao com sucesso")
    public void cadastrarCompeticao(){
        //GIVEN
        CompeticaoDTO request = from(CompeticaoDTO.class).gimme(CompeticaoDTOTemplate.VALIDO);
        CompeticaoEntity entity = from(CompeticaoEntity.class).gimme(CompeticaoEntityTemplate.VALIDO);

        //WHEN
        given(competicaoService.buscarByModalidadeLocal(anyString(), anyString())).willReturn(null);
        given(competicaoService.cadastrar(any(CompeticaoEntity.class))).willReturn(entity);

        //THAT
        CompeticaoDTO compSalva = competicaoFacade.cadastrar(request);

        assertThat(compSalva);
    }

    @Test
    @DisplayName("Deve cadastrar competicao com sucesso verificando conflito de horarios")
    public void cadastrarCompeticaoSucesso(){
        //GIVEN
        CompeticaoDTO request = from(CompeticaoDTO.class).gimme(CompeticaoDTOTemplate.VALIDO_2);
        List<CompeticaoEntity> entityList = from(CompeticaoEntity.class).gimme(1, CompeticaoEntityTemplate.VALIDO);

        //WHEN
        given(competicaoService.buscarByModalidadeLocal(anyString(), anyString())).willReturn(entityList);
        given(competicaoService.cadastrar(any(CompeticaoEntity.class))).willReturn(entityList.get(0));

        //THAT
        CompeticaoDTO compSalva = competicaoFacade.cadastrar(request);

        assertThat(compSalva);
    }

    @Test()
    @DisplayName("Deve retornar lista de competicoes por filtro de modalidade")
    public void buscaCompeticaoModalidade(){
        //GIVEN
        String modalidade = "Futebol";
        List<CompeticaoEntity> entityList = from(CompeticaoEntity.class).gimme(1, CompeticaoEntityTemplate.VALIDO);

        //WHEN
        given(competicaoService.buscarByModalidade(modalidade)).willReturn(entityList);

        //THAT
        List<CompeticaoDTO> competicaoDTOS = competicaoFacade.buscarCompeticoes(modalidade);

        assertThat(competicaoDTOS).isNotEmpty();
    }

    @Test()
    @DisplayName("Deve retornar erro ao cadastrar competicao em local e horario ja cadastrado")
    public void erroCadastroCompeticao(){
        //GIVEN
        CompeticaoDTO request = from(CompeticaoDTO.class).gimme(CompeticaoDTOTemplate.VALIDO);
        List<CompeticaoEntity> entityList = from(CompeticaoEntity.class).gimme(1, CompeticaoEntityTemplate.VALIDO);

        given(competicaoService.buscarByModalidadeLocal(anyString(), anyString())).willReturn(entityList);
        given(competicaoService.cadastrar(any(CompeticaoEntity.class))).willReturn(entityList.get(0));

        //WHEN
        Throwable exception = Assertions.catchThrowable(()->competicaoFacade.cadastrar(request));

        //THAT
        assertThat(exception)
                .isInstanceOf(Response400Exception.class)
                .hasMessage("Data/Hora indisponivel para a modalidade e local escolhido");
    }

    @Test
    @DisplayName("Deve retornar erro ao buscar competicao por modalidade nao cadastrada")
    public void erroBuscaCompeticaoModalidade(){
        //GIVEN
        String modalidade = "Volei";
        String msgErro = "Nenhuma competição cadastrada para a modalidade Volei";

        given(competicaoService.buscarByModalidade(modalidade)).willThrow(new Response404Exception(msgErro));

        //WHEN
        Throwable exception = Assertions.catchThrowable(()->competicaoFacade.buscarCompeticoes(modalidade));

        //THAT
        assertThat(exception)
                .isInstanceOf(Response404Exception.class)
                .hasMessage(msgErro);
    }

    @Test
    @DisplayName("Deve consultar o clima de Tokyo com sucesso")
    public void consultaClima(){
        //GIVEN
        ClimaResponse clima = from(ClimaResponse.class).gimme(ClimaResponseTemplate.VALIDO);

        given(weatherService.consultaClima()).willReturn(clima);

        //WHEN
        ClimaDTO climaDTO = competicaoFacade.consultaClima();

        //THAT
        assertThat(climaDTO).isNotNull();
        assertThat(climaDTO.getTemperatura()).isEqualTo(clima.getMain().getTemperatura());
        assertThat(climaDTO.getUmidade()).isEqualTo(clima.getMain().getUmidade());
    }
}
