package br.com.jogosolimpicos.service;

import br.com.jogosolimpicos.exceptions.Response404Exception;
import br.com.jogosolimpicos.model.entity.CompeticaoEntity;
import br.com.jogosolimpicos.model.repository.CompeticaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompeticaoService {

    @Autowired
    private CompeticaoRepository repository;

    public CompeticaoEntity cadastrar(CompeticaoEntity entity){
        return repository.save(entity);
    }

    public List<CompeticaoEntity> buscarByModalidade(String modalidade){
        List<CompeticaoEntity> entityList = repository.findByModalidadeOrderByDtHrInicio(modalidade);
        if(entityList.isEmpty())
            throw new Response404Exception("Nenhuma competição cadastrada para a modalidade ".concat(modalidade));
        return entityList;
    }

    public List<CompeticaoEntity> buscar(){
        List<CompeticaoEntity> entityList = repository.findByOrderByDtHrInicio();
        if(entityList.isEmpty())
            throw new Response404Exception("Nenhuma competição cadastrada");
        return entityList;
    }

    public List<CompeticaoEntity> buscarByModalidadeLocal(String modalidade, String local){
        return repository.findByModalidadeAndLocal(modalidade, local);
    }
}
