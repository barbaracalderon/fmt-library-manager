package com.fmt.library.controller;

import com.fmt.library.model.Membro;
import com.fmt.library.repository.MembroRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/membro")
public class MembroController {
    
    MembroRepository membroRepository;
    
    public MembroController(MembroRepository membroRepository) {
        this.membroRepository = membroRepository;
    }

    @PostMapping
    public ResponseEntity<Membro> criarUmMembro(@RequestBody Membro Membro) {
        Membro novoMembro = membroRepository.save(Membro);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoMembro);
    }


    @GetMapping
    public ResponseEntity<ArrayList<Membro>> buscarTodosOsMembros() {
        ArrayList<Membro> membros = (ArrayList<Membro>) membroRepository.findAll();
        if (membros.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(membros);
        }
    }

    @GetMapping("{idMembro}")
    public ResponseEntity<Membro> buscarUmMembro(@PathVariable Integer idMembro) {
        Optional<Membro> membroEncontrado = membroRepository.findById(idMembro);
        return membroEncontrado.map(
                Membro -> new ResponseEntity<>(Membro, HttpStatus.FOUND)
        ).orElseGet(
                () -> new ResponseEntity<>(HttpStatus.NOT_FOUND)
        );
    }

    @PutMapping("{idMembro}")
    public ResponseEntity<Optional<Membro>> atualizandoUmMembro(@PathVariable Integer idMembro, @RequestBody Membro membroAtualizado) {
        Optional<Membro> membroParaAtualizar = membroRepository.findById(idMembro);
        if (membroParaAtualizar.isPresent()) {
            Membro membroExistente = membroParaAtualizar.get();
            membroExistente.setNome(membroAtualizado.getNome());
            membroExistente.setTelefone(membroAtualizado.getTelefone());
            membroExistente.setEndereco(membroAtualizado.getEndereco());
            Membro membroAtualizadoSalvo = membroRepository.save(membroExistente);
            return ResponseEntity.ok(Optional.of(membroAtualizadoSalvo));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{idMembro}")
    public ResponseEntity<Void> deletarUmMembro(@PathVariable Integer idMembro) {
        membroRepository.deleteById(idMembro);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/nomes")
    public ResponseEntity<ArrayList<String>> buscarNomesDosMembros() {
        ArrayList<String> nomes = membroRepository.buscarNomesDosMembros();
        return ResponseEntity.ok(nomes);
    }

    @GetMapping("/telefones")
    public ResponseEntity<ArrayList<String>> buscarTelefonesDosMembros() {
        ArrayList<String> telefones = membroRepository.buscarTelefonesDosMembros();
        return ResponseEntity.ok(telefones);
    }



}
