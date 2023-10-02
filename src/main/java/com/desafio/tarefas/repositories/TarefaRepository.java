package com.desafio.tarefas.repositories;

import com.desafio.tarefas.entity.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, UUID> {

    boolean existsTarefaByNomTarefa(String nomeTarefa);
}
