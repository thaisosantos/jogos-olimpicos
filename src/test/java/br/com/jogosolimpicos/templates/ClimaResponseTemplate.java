package br.com.jogosolimpicos.templates;

import br.com.jogosolimpicos.controller.dto.ClimaDTO;
import br.com.jogosolimpicos.integacao.response.ClimaResponse;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class ClimaResponseTemplate implements TemplateLoader {
    public static final String VALIDO = "response valido";
    public static final String CLIMA_VALIDO = "Clima response valido";

    @Override
    public void load() {
        Fixture.of(ClimaResponse.class).addTemplate(VALIDO, new Rule(){
            {
                add("main",one(ClimaResponse.Main.class,CLIMA_VALIDO));
            }
        });
        Fixture.of(ClimaResponse.Main.class).addTemplate(CLIMA_VALIDO, new Rule(){
            {
                add("umidade","50");
                add("temperatura","25.5");
            }
        });
    }
}
