package br.com.jogosolimpicos.mapper;

import br.com.jogosolimpicos.controller.dto.ClimaDTO;
import br.com.jogosolimpicos.integacao.response.ClimaResponse;
import org.mapstruct.Mapper;

@Mapper
public interface ClimaMapper {

    ClimaDTO mapToDTO(ClimaResponse.Main response);
}
