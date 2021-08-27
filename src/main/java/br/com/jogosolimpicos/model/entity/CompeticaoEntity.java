package br.com.jogosolimpicos.model.entity;

import br.com.jogosolimpicos.enums.EtapaEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CompeticaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String modalidade;
    private String local;
    private LocalDateTime dtHrInicio;
    private LocalDateTime dtHrTermino;
    private String pais1;
    private String pais2;
    private EtapaEnum etapa;
}
