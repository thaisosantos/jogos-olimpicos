package br.com.jogosolimpicos.model.repository;

import br.com.jogosolimpicos.model.entity.CompeticaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompeticaoRepository extends JpaRepository<CompeticaoEntity, Integer> {

    List<CompeticaoEntity> findByOrderByDtHrInicio();

    List<CompeticaoEntity> findByModalidadeAndLocal(@Param("modalidade") String modalidade,
                                                     @Param("local") String local);

    List<CompeticaoEntity> findByModalidadeOrderByDtHrInicio(@Param("modalidade") String modalidade);
}
