package br.com.jogosolimpicos.templates;


import br.com.jogosolimpicos.controller.dto.CompeticaoDTO;
import br.com.jogosolimpicos.enums.EtapaEnum;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

import java.time.LocalDateTime;

public class CompeticaoDTOTemplate implements TemplateLoader {

    public static final String VALIDO = "DTO valido";
    public static final String VALIDO_2 = "DTO valido 2";
    public static final String VALIDO_ID = "DTO valido com id";
    public static final String INVALIDO = "DTO invalido";
    public static final String DATAS_INVALIDAS = "DTO datas invalidas";
    public static final String DURACAO_INVALIDA = "DTO duracao invalida";

    @Override
    public void load() {
        Fixture.of(CompeticaoDTO.class).addTemplate(VALIDO, new Rule() {
            {
                add("modalidade", "Futebol");
                add("local", "Estádio XX");
                add("dtHrInicio", LocalDateTime.now().plusHours(1));
                add("dtHrTermino", LocalDateTime.now().plusHours(2));
                add("pais1", "Japão");
                add("pais2", "Brasil");
                add("etapa", EtapaEnum.ELIMINATORIAS);
            }
        }).addTemplate(VALIDO_2).inherits(VALIDO, new Rule(){
            {
                add("dtHrInicio", LocalDateTime.now().plusHours(2));
                add("dtHrTermino", LocalDateTime.now().plusHours(3));
            }
        }).addTemplate(VALIDO_ID).inherits(VALIDO, new Rule() {
            {
                add("id", 1);
            }
        }).addTemplate(INVALIDO).inherits(VALIDO, new Rule() {
            {
                add("modalidade", null);
            }
        }).addTemplate(DATAS_INVALIDAS).inherits(VALIDO, new Rule() {
            {
                add("dtHrInicio", LocalDateTime.now().minusHours(24));
                add("dtHrTermino", LocalDateTime.now().minusHours(23));
            }
        }).addTemplate(DURACAO_INVALIDA).inherits(VALIDO, new Rule() {
            {
                add("dtHrInicio", LocalDateTime.now().plusHours(1));
                add("dtHrTermino", LocalDateTime.now().plusHours(1));
            }
        });
    }
}
