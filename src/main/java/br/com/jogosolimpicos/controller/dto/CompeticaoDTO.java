package br.com.jogosolimpicos.controller.dto;

import br.com.jogosolimpicos.enums.EtapaEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompeticaoDTO implements Serializable {

    private Integer id;
    @NotEmpty(message = "O campo modalidade é obrigatório")
    private String modalidade;
    @NotEmpty(message = "O campo local é obrigatório")
    private String local;
    @NotNull(message = "O campo data_hora_inicio é obrigatorio")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonProperty("data_hora_inicio")
    private LocalDateTime dtHrInicio;
    @NotNull(message = "O campo data_hora_termino é obrigatorio")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonProperty("data_hora_termino")
    private LocalDateTime dtHrTermino;
    @NotEmpty(message = "O campo país_1 é obrigatório")
    @JsonProperty("pais_1")
    private String pais1;
    @NotEmpty(message = "O campo país_2 é obrigatório")
    @JsonProperty("pais_2")
    private String pais2;
    @NotNull(message = "O campo etapa é obrigatório")
    private EtapaEnum etapa;
}
