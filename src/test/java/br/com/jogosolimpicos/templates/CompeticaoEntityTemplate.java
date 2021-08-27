package br.com.jogosolimpicos.templates;


import br.com.jogosolimpicos.enums.EtapaEnum;
import br.com.jogosolimpicos.model.entity.CompeticaoEntity;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

import java.time.LocalDateTime;

public class CompeticaoEntityTemplate implements TemplateLoader {

    public static final String VALIDO = "entity valido";

    @Override
    public void load() {
        Fixture.of(CompeticaoEntity.class).addTemplate(VALIDO, new Rule(){
            {
                add("id",1);
                add("modalidade","Futebol");
                add("local","Estádio XX");
                add("dtHrInicio", LocalDateTime.now().plusHours(1));
                add("dtHrTermino", LocalDateTime.now().plusHours(2));
                add("pais1","Japão");
                add("pais2","Brasil");
                add("etapa", EtapaEnum.ELIMINATORIAS);
            }
        });
    }
}
