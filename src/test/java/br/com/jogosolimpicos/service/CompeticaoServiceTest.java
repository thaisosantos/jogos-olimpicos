package br.com.jogosolimpicos.service;

import br.com.jogosolimpicos.exceptions.Response404Exception;
import br.com.jogosolimpicos.model.entity.CompeticaoEntity;
import br.com.jogosolimpicos.model.repository.CompeticaoRepository;
import br.com.jogosolimpicos.templates.CompeticaoEntityTemplate;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static br.com.six2six.fixturefactory.Fixture.from;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)

public class CompeticaoServiceTest {

    @Mock
    private CompeticaoRepository repository;

    @InjectMocks
    private CompeticaoService competicaoService;

    @BeforeEach
    public void setUp(){
        FixtureFactoryLoader.loadTemplates("br.com.jogosolimpicos.templates");
    }

    @Test
    @DisplayName("Deve cadastrar competicao com sucesso")
    public void cadastrarCompeticaoSucesso(){
        //GIVEN
        CompeticaoEntity entity = from(CompeticaoEntity.class).gimme(CompeticaoEntityTemplate.VALIDO);

        //WHEN
        given(repository.save(any())).willReturn(entity);

        //THAT
        CompeticaoEntity entitySalva = competicaoService.cadastrar(entity);

        assertThat(entitySalva).isNotNull();
        assertThat(entitySalva.getId()).isEqualTo(entity.getId());
    }

    @Test()
    @DisplayName("Deve retornar lista de todas as competicoes cadastradas")
    public void consultaCompeticoes(){
        //GIVEN
        List<CompeticaoEntity> entityMock = from(CompeticaoEntity.class).gimme(1, CompeticaoEntityTemplate.VALIDO);

        //WHEN
        given(repository.findByOrderByDtHrInicio()).willReturn(entityMock);

        //THAT
        List<CompeticaoEntity> entityList = competicaoService.buscar();

        assertThat(entityList).isNotEmpty();
    }

    @Test()
    @DisplayName("Deve retornar lista de competicoes por filtro de modalidade")
    public void buscaCompeticaoModalidade(){
        //GIVEN
        String modalidade = "Futebol";
        List<CompeticaoEntity> entityMock = from(CompeticaoEntity.class).gimme(1, CompeticaoEntityTemplate.VALIDO);

        //WHEN
        given(repository.findByModalidadeOrderByDtHrInicio(modalidade)).willReturn(entityMock);

        //THAT
        List<CompeticaoEntity> entityList = competicaoService.buscarByModalidade(modalidade);

        assertThat(entityList).isNotEmpty();
    }

    @Test
    @DisplayName("Deve retornar erro ao buscar competicao por modalidade nao cadastrada")
    public void erroBuscaCompeticaoModalidade(){
        //GIVEN
        List<CompeticaoEntity> list = new ArrayList<>();
        String modalidade = "Volei";
        String msgErro = "Nenhuma competição cadastrada para a modalidade Volei";

        given(repository.findByModalidadeOrderByDtHrInicio(modalidade)).willReturn(list);

        //WHEN
        Throwable exception = Assertions.catchThrowable(()->competicaoService.buscarByModalidade(modalidade));

        //THAT
        assertThat(exception)
                .isInstanceOf(Response404Exception.class)
                .hasMessage(msgErro);
    }

    @Test
    @DisplayName("Deve retornar mensagem de erro ao consultar competicoes")
    public void erroBuscaCompeticao(){
        //GIVEN
        List<CompeticaoEntity> list = new ArrayList<>();
        String msgErro = "Nenhuma competição cadastrada";

        given(repository.findByOrderByDtHrInicio()).willReturn(list);

        //WHEN
        Throwable exception = Assertions.catchThrowable(()->competicaoService.buscar());

        //THAT
        assertThat(exception)
                .isInstanceOf(Response404Exception.class)
                .hasMessage(msgErro);
    }

    @Test()
    @DisplayName("Deve retornar lista de competicoes por filtro de modalidade e local")
    public void buscaCompeticaoModalidadeLocal(){
        //GIVEN
        String modalidade = "Futebol";
        String local = "Estádio XX";
        List<CompeticaoEntity> entityMock = from(CompeticaoEntity.class).gimme(1, CompeticaoEntityTemplate.VALIDO);

        //WHEN
        given(repository.findByModalidadeAndLocal(modalidade, local)).willReturn(entityMock);

        //THAT
        List<CompeticaoEntity> entityList = competicaoService.buscarByModalidadeLocal(modalidade,local);

        assertThat(entityList).isNotEmpty();
    }

}
