package com.desafio.tarefas.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;


@Data
@Builder
public class TarefaDTO {

    @NotBlank
    @Size(max = 120)
    private String nomTarefa;

    private Float custo;

    private LocalDate dataLimite;

    private int ordem;
}
