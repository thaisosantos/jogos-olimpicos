package br.com.jogosolimpicos.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;


@Getter
public enum EtapaEnum {

    ELIMINATORIAS("Eliminatórias"),
    OITAVAS_DE_FINAL("Oitavas de Final"),
    QUARTAS_DE_FINAL("Quartas de Final"),
    SEMIFINAL("Semifinal"),
    FINAL("Final");

    @JsonValue
    private final String value;

    EtapaEnum(final String value) {this.value = value;}
}
