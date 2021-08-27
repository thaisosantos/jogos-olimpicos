package br.com.jogosolimpicos.facade;

import br.com.jogosolimpicos.controller.dto.ClimaDTO;
import br.com.jogosolimpicos.controller.dto.CompeticaoDTO;
import br.com.jogosolimpicos.exceptions.Response400Exception;
import br.com.jogosolimpicos.integacao.response.ClimaResponse;
import br.com.jogosolimpicos.integacao.service.WeatherService;
import br.com.jogosolimpicos.mapper.ClimaMapper;
import br.com.jogosolimpicos.mapper.CompeticaoMapper;
import br.com.jogosolimpicos.model.entity.CompeticaoEntity;
import br.com.jogosolimpicos.service.CompeticaoService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompeticaoFacade {

    @Autowired
    private CompeticaoService service;

    @Autowired
    private WeatherService weatherService;

    private final CompeticaoMapper competicaoMapper = Mappers.getMapper(CompeticaoMapper.class);
    private final ClimaMapper climaMapper = Mappers.getMapper(ClimaMapper.class);

    public CompeticaoDTO cadastrar(CompeticaoDTO request){
        List<CompeticaoEntity> competicaoEntityList = service.buscarByModalidadeLocal(request.getModalidade(), request.getLocal());

        if (competicaoEntityList!=null && !competicaoEntityList.isEmpty()) {
            verificaDataHoraDisponivel(competicaoEntityList, request);
        }
        CompeticaoEntity entitySalva = service.cadastrar(competicaoMapper.mapToEntity(request));

        return competicaoMapper.mapToDTO(entitySalva);
    }

    public List<CompeticaoDTO> buscarCompeticoes(String modalidade){
        List<CompeticaoEntity> competicaoEntities =
                modalidade==null||modalidade.isEmpty()? service.buscar() : service.buscarByModalidade(modalidade);
        return competicaoMapper.mapToDTOList(competicaoEntities);
    }

    private void verificaDataHoraDisponivel(List<CompeticaoEntity> competicaoEntityList, CompeticaoDTO request){
        competicaoEntityList.stream().forEach(comp -> {
            if (comp.getDtHrInicio().isEqual(request.getDtHrInicio())
                || comp.getDtHrInicio().isBefore(request.getDtHrTermino())
                    && comp.getDtHrTermino().isAfter(request.getDtHrInicio()))
                throw new Response400Exception("Data/Hora indisponivel para a modalidade e local escolhido");
            }
        );
    }

    public ClimaDTO consultaClima(){
        ClimaResponse climaResponse = weatherService.consultaClima();
        return climaMapper.mapToDTO(climaResponse.getMain());
    }
}
