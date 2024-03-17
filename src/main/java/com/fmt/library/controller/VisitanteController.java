package com.fmt.library.controller;

import com.fmt.library.model.Visitante;
import com.fmt.library.repository.VisitanteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/visitante")
public class VisitanteController {

    VisitanteRepository visitanteRepository;

    public VisitanteController(VisitanteRepository visitanteRepository) {
        this.visitanteRepository = visitanteRepository;
    }

    @PostMapping
    public ResponseEntity<Visitante> criarUmVisitante(@RequestBody Visitante visitante) {
        Visitante novoVisitante = visitanteRepository.save(visitante);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoVisitante);
    }


    @GetMapping
    public ResponseEntity<ArrayList<Visitante>> buscarTodosOsvisitantes() {
        ArrayList<Visitante> visitantes = (ArrayList<Visitante>) visitanteRepository.findAll();
        if (visitantes.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(visitantes);
        }
    }

    @GetMapping("{idVisitante}")
    public ResponseEntity<Visitante> buscarUmVisitante(@PathVariable Integer idVisitante) {
        Optional<Visitante> visitanteEncontrado = visitanteRepository.findById(idVisitante);
        return visitanteEncontrado.map(
                visitante -> new ResponseEntity<>(visitante, HttpStatus.FOUND)
        ).orElseGet(
                () -> new ResponseEntity<>(HttpStatus.NOT_FOUND)
        );
    }

    @PutMapping("{idVisitante}")
    public ResponseEntity<Optional<Visitante>> atualizarUmVisitante(@PathVariable Integer idVisitante, @RequestBody Visitante visitanteAtualizado) {
        Optional<Visitante> visitanteParaAtualizar = visitanteRepository.findById(idVisitante);
        if (visitanteParaAtualizar.isPresent()) {
            Visitante visitanteExistente = visitanteParaAtualizar.get();
            visitanteExistente.setNome(visitanteAtualizado.getNome());
            visitanteExistente.setTelefone(visitanteAtualizado.getTelefone());
            Visitante visitanteAtualizadoSalvo = visitanteRepository.save(visitanteExistente);
            return ResponseEntity.ok(Optional.of(visitanteAtualizadoSalvo));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{idVisitante}")
    public ResponseEntity<Void> deletarUmVisitante(@PathVariable Integer idVisitante) {
        visitanteRepository.deleteById(idVisitante);
        return ResponseEntity.noContent().build();
    }

}
