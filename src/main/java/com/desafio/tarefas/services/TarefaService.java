package com.desafio.tarefas.services;

import com.desafio.tarefas.entity.Tarefa;
import com.desafio.tarefas.repositories.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TarefaService {

    @Autowired
    TarefaRepository repository;


    @Transactional
    public Tarefa save(Tarefa tarefa) {
        return repository.save(tarefa);
    }

    public boolean existeNomeTarefa(String nomTarefa) {
        return repository.existsTarefaByNomTarefa(nomTarefa);
    }

    public List<Tarefa> findAll() {
        return repository.findAll();
    }

    public Optional<Tarefa> findById(UUID id) {
        return repository.findById(id);
    }

    @Transactional
    public void delete(Tarefa tarefa) {
        repository.delete(tarefa);
    }


}
