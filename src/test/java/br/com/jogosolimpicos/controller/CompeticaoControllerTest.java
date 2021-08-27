package br.com.jogosolimpicos.controller;

import br.com.jogosolimpicos.controller.dto.ClimaDTO;
import br.com.jogosolimpicos.controller.dto.CompeticaoDTO;
import br.com.jogosolimpicos.exceptions.Response404Exception;
import br.com.jogosolimpicos.facade.CompeticaoFacade;
import br.com.jogosolimpicos.templates.ClimaDTOTemplate;
import br.com.jogosolimpicos.templates.CompeticaoDTOTemplate;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static br.com.six2six.fixturefactory.Fixture.from;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@AutoConfigureMockMvc
public class CompeticaoControllerTest {

    static String COMPETICAO_API = "/api/competicao";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CompeticaoFacade competicaoFacade;

    @BeforeEach
    public void setUp(){
        FixtureFactoryLoader.loadTemplates("br.com.jogosolimpicos.templates");
    }

    @Test
    @DisplayName("Deve cadastrar competicao com sucesso")
    public void cadastraCompeticao() throws Exception {
        CompeticaoDTO dto = from(CompeticaoDTO.class).gimme(CompeticaoDTOTemplate.VALIDO);
        CompeticaoDTO competicaoSalva = from(CompeticaoDTO.class).gimme(CompeticaoDTOTemplate.VALIDO_ID);

        given(competicaoFacade.cadastrar(Mockito.any(CompeticaoDTO.class))).willReturn(competicaoSalva);
        String json = new ObjectMapper().writeValueAsString(dto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(COMPETICAO_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").isNotEmpty());
    }

    @Test
    @DisplayName("Deve retornar mensagem erro ao cadastrar competicao sem dados obrigatorios")
    public void deveRetornarErroCompeticaoInvalida() throws Exception{
        String json = new ObjectMapper().writeValueAsString(new CompeticaoDTO());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(COMPETICAO_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", hasSize(7)));

    }


    @Test
    @DisplayName("Deve retornar mensagem de erro ao cadastrar competicao com duracao menor que 30 minutos")
    public void cadastroCompeticaoDuracaoInvalida() throws Exception{
        CompeticaoDTO dto = from(CompeticaoDTO.class).gimme(CompeticaoDTOTemplate.DURACAO_INVALIDA);
        String json = new ObjectMapper().writeValueAsString(dto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(COMPETICAO_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", hasSize(1)))
                .andExpect(jsonPath("errors[0]", is("A competição deve ter a duração de no mínimo 30 minutos.")));
    }

    @Test
    @DisplayName("Deve retornar mensagem de erro ao cadastrar competicao com datas passadas")
    public void cadastroCompeticaoDatasInvalidas() throws Exception{
        CompeticaoDTO dto = from(CompeticaoDTO.class).gimme(CompeticaoDTOTemplate.DATAS_INVALIDAS);
        String json = new ObjectMapper().writeValueAsString(dto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(COMPETICAO_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", hasSize(1)))
                .andExpect(jsonPath("errors[0]", is("Data/Hora de inicio e termino da competição devem ser futuras.")));
    }

    @Test
    @DisplayName("Deve retornar lista de competicoes cadastradas com sucesso")
    public void consultaCompeticoes() throws Exception {
        List<CompeticaoDTO> competicaoSalva = from(CompeticaoDTO.class).gimme(1, CompeticaoDTOTemplate.VALIDO_ID);

        given(competicaoFacade.buscarCompeticoes(null)).willReturn(competicaoSalva);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(COMPETICAO_API)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNotEmpty());
    }

    @Test
    @DisplayName("Deve retornar lista de competicoes cadastradas com filtro de modalidade")
    public void consultaCompeticoesModalidade() throws Exception {
        List<CompeticaoDTO> competicaoSalva = from(CompeticaoDTO.class).gimme(1, CompeticaoDTOTemplate.VALIDO_ID);
        String modalidade = "Futebol";

        given(competicaoFacade.buscarCompeticoes(modalidade)).willReturn(competicaoSalva);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(COMPETICAO_API)
                .param("modalidade",modalidade)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id",is(competicaoSalva.get(0).getId())))
                .andExpect(jsonPath("$[0].modalidade",is(modalidade)));
    }

    @Test
    @DisplayName("Deve retornar mensagem de erro ao buscar competicoes por modalidade nao cadastrada")
    public void buscaModalidadeNaoCadastrada() throws Exception {
        String modalidade = "Futebol";
        String msgErro = "Nenhuma competição cadastrada para a modalidade Futebol";

        given(competicaoFacade.buscarCompeticoes(modalidade)).willThrow(new Response404Exception(msgErro));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(COMPETICAO_API)
                .param("modalidade",modalidade)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("errors", hasSize(1)))
                .andExpect(jsonPath("errors[0]", is(msgErro)));
    }

    @Test
    @DisplayName("Deve retornar o clima atual de Tokyo com sucesso")
    public void consultaClima() throws Exception{
        ClimaDTO dto = from(ClimaDTO.class).gimme(ClimaDTOTemplate.VALIDO);

        given(competicaoFacade.consultaClima()).willReturn(dto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(COMPETICAO_API.concat("/clima"))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("umidade",is(dto.getUmidade())))
                .andExpect(jsonPath("temperatura", is(dto.getTemperatura())));
    }
}