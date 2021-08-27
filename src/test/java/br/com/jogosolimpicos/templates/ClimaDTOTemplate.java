package br.com.jogosolimpicos.templates;

import br.com.jogosolimpicos.controller.dto.ClimaDTO;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class ClimaDTOTemplate implements TemplateLoader {
    public static final String VALIDO = "DTO valido";

    @Override
    public void load() {
        Fixture.of(ClimaDTO.class).addTemplate(VALIDO, new Rule(){
            {
                add("umidade","50");
                add("temperatura","25.5");
            }
        });
    }
}
