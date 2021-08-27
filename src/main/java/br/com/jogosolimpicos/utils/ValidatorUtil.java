package br.com.jogosolimpicos.utils;

import br.com.jogosolimpicos.controller.dto.CompeticaoDTO;
import br.com.jogosolimpicos.exceptions.Response400Exception;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ValidatorUtil {

    private ValidatorUtil() {throw new IllegalStateException("Classe util");}

    public static void validaDatas(final CompeticaoDTO dto) {
        LocalDateTime dtInicio = dto.getDtHrInicio();
        LocalDateTime dtTermino = dto.getDtHrTermino();

        if (dtInicio.isBefore(LocalDateTime.now())
                || dtTermino.isBefore(LocalDateTime.now())
                || dtInicio.isAfter(dtTermino)) {
            throw new Response400Exception("Data/Hora de inicio e termino da competição devem ser futuras.");
        }

        long tempoCompeticao = ChronoUnit.MINUTES.between(dtInicio, dtTermino);
        if (tempoCompeticao < 30) {
            throw new Response400Exception("A competição deve ter a duração de no mínimo 30 minutos.");
        }
    }
}
