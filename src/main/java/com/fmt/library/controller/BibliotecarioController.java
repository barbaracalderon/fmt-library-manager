package com.fmt.library.controller;

import com.fmt.library.model.Bibliotecario;
import com.fmt.library.model.Livro;
import com.fmt.library.repository.BibliotecarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/bibliotecario")
public class BibliotecarioController {

    BibliotecarioRepository bibliotecarioRepository;

    public BibliotecarioController(BibliotecarioRepository bibliotecarioRepository) {
        this.bibliotecarioRepository = bibliotecarioRepository;
    }

    @PostMapping
    public ResponseEntity<Bibliotecario> criarUmBibliotecario(@RequestBody Bibliotecario bibliotecario) {
        Bibliotecario novoBibliotecario = bibliotecarioRepository.save(bibliotecario);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoBibliotecario);
    }

    @GetMapping
    public ResponseEntity<ArrayList<Bibliotecario>> buscarTodosOsBibliotecarios() {
        ArrayList<Bibliotecario> bibliotecarios = (ArrayList<Bibliotecario>) bibliotecarioRepository.findAll();
        if (bibliotecarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(bibliotecarios);
        }
    }

    @GetMapping("{idBibliotecario}")
    public ResponseEntity<Bibliotecario> buscarUmBibliotecario(@PathVariable Integer idBibliotecario) {
        Optional<Bibliotecario> bibliotecarioEncontrado = bibliotecarioRepository.findById(idBibliotecario);
        return bibliotecarioEncontrado.map(
                livro -> new ResponseEntity<>(livro, HttpStatus.FOUND)
        ).orElseGet(
                () -> new ResponseEntity<>(HttpStatus.NOT_FOUND)
        );
    }

    @PutMapping("{idBibliotecario}")
    public ResponseEntity<Optional<Bibliotecario>> atualizarUmBibliotecario(@PathVariable Integer idBibliotecario, @RequestBody Bibliotecario bibliotecarioAtualizado) {
        Optional<Bibliotecario> bibliotecarioParaAtualizar = bibliotecarioRepository.findById(idBibliotecario);
        if (bibliotecarioParaAtualizar.isPresent()) {
            Bibliotecario bibliotecarioExistente = bibliotecarioParaAtualizar.get();
            bibliotecarioExistente.setNome(bibliotecarioAtualizado.getNome());
            bibliotecarioExistente.setEmail(bibliotecarioAtualizado.getEmail());
            bibliotecarioExistente.setSenha(bibliotecarioAtualizado.getSenha());
            Bibliotecario bibliotecarioAtualizadoSalvo = bibliotecarioRepository.save(bibliotecarioExistente);
            return ResponseEntity.ok(Optional.of(bibliotecarioAtualizadoSalvo));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{idBibliotecario}")
    public ResponseEntity<Void> deletarUmBibliotecario(@PathVariable Integer idBibliotecario) {
        bibliotecarioRepository.deleteById(idBibliotecario);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/emails")
    public ResponseEntity<ArrayList<String>> buscarEmailsDosBibliotecarios() {
        ArrayList<String> emails = bibliotecarioRepository.buscarEmailsDosBibliotecarios();
        return ResponseEntity.ok(emails);
    }

    @PutMapping("{idBibliotecario}/senha")
    public ResponseEntity<Void> atualizarSenhaDoBibliotecario(@PathVariable Integer idBibliotecario, @RequestBody String novaSenha) {
        Optional<Bibliotecario> bibliotecarioOptional = bibliotecarioRepository.findById(idBibliotecario);
        if (bibliotecarioOptional.isPresent()) {
            Bibliotecario bibliotecario = bibliotecarioOptional.get();
            bibliotecario.setSenha(novaSenha);
            bibliotecarioRepository.save(bibliotecario);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
