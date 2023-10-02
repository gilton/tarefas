package com.desafio.tarefas.controller;


import com.desafio.tarefas.dto.TarefaDTO;
import com.desafio.tarefas.entity.Tarefa;
import com.desafio.tarefas.services.TarefaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    TarefaService service;


    @PostMapping("/add")
    public ResponseEntity<Object> saveTarefa(@RequestBody @Valid TarefaDTO tarefaDTO) {
        Tarefa tarefa = new Tarefa();

        BeanUtils.copyProperties(tarefaDTO, tarefa);
        tarefa.setDataLimite( LocalDate.parse(String.valueOf(tarefaDTO.getDataLimite())) );

        if( service.existeNomeTarefa(tarefaDTO.getNomTarefa()) ) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflit: Esse nome já está cadastrado.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(tarefa));
    }

    @GetMapping
    public ResponseEntity<List<Tarefa>> getAllTarefas() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneTarefa(@PathVariable(value = "id") UUID id) {
        Optional<Tarefa> tarefaOptional = service.findById(id);
        if(!tarefaOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarefa não encontrada.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(tarefaOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTarefa(@PathVariable(value = "id") UUID id) {
        Optional<Tarefa> tarefaOptional = service.findById(id);
        if(!tarefaOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarefa não encontrada.");
        }
        service.delete(tarefaOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Tarefa excluída com sucesso.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarTarefa(@PathVariable(value = "id") UUID id,
                                                  @RequestBody @Valid TarefaDTO tarefaDTO) {
        Optional<Tarefa> tarefaOptional = service.findById(id);
        if(!tarefaOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarefa não encontrada.");
        }

        Tarefa tarefa = new Tarefa();
        BeanUtils.copyProperties(tarefaDTO, tarefa);
        tarefa.setIdTarefa(tarefaOptional.get().getIdTarefa());
        tarefa.setDataLimite( LocalDate.parse(String.valueOf(tarefaDTO.getDataLimite())) );
        return ResponseEntity.status(HttpStatus.OK).body(service.save(tarefa));
    }



}
