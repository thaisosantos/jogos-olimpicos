package br.com.jogosolimpicos.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClimaDTO {
    private String umidade;
    private String temperatura;
}
