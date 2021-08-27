package br.com.jogosolimpicos.mapper;

import br.com.jogosolimpicos.controller.dto.CompeticaoDTO;
import br.com.jogosolimpicos.model.entity.CompeticaoEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface CompeticaoMapper {

    CompeticaoDTO mapToDTO(CompeticaoEntity entity);
    List<CompeticaoDTO> mapToDTOList(List<CompeticaoEntity> competicaoEntities);

    CompeticaoEntity mapToEntity(CompeticaoDTO entity);
}
