package com.fmt.library.controller;

import com.fmt.library.model.Livro;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fmt.library.repository.LivroRepository;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/livro")
public class LivroController {

    LivroRepository livroRepository;

    public LivroController(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    @PostMapping
    public ResponseEntity<Livro> criarUmLivro(@RequestBody Livro livro) {
        Livro novoLivro = livroRepository.save(livro);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoLivro);
    }


    @GetMapping
    public ResponseEntity<ArrayList<Livro>> buscarTodosOsLivros() {
        ArrayList<Livro> livros = (ArrayList<Livro>) livroRepository.findAll();
        if (livros.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(livros);
        }
    }

    @GetMapping("{idLivro}")
    public ResponseEntity<Livro> buscarUmLivro(@PathVariable Integer idLivro) {
        Optional<Livro> livroEncontrado = livroRepository.findById(idLivro);
        return livroEncontrado.map(
                livro -> new ResponseEntity<>(livro, HttpStatus.FOUND)
        ).orElseGet(
                () -> new ResponseEntity<>(HttpStatus.NOT_FOUND)
        );
    }

    @PutMapping("{idLivro}")
    public ResponseEntity<Optional<Livro>> atualizarUmLivro(@PathVariable Integer idLivro, @RequestBody Livro livroAtualizado) {
        Optional<Livro> livroParaAtualizar = livroRepository.findById(idLivro);
        if (livroParaAtualizar.isPresent()) {
            Livro livroExistente = livroParaAtualizar.get();
            livroExistente.setTitulo(livroAtualizado.getTitulo());
            livroExistente.setAutor(livroAtualizado.getAutor());
            Livro livroAtualizadoSalvo = livroRepository.save(livroExistente);
            return ResponseEntity.ok(Optional.of(livroAtualizadoSalvo));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{idLivro}")
    public ResponseEntity<Void> deletarUmLivro(@PathVariable Integer idLivro) {
        livroRepository.deleteById(idLivro);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/titulos")
    public ResponseEntity<ArrayList<String>> buscarTitulosDosLivros() {
        ArrayList<String> titulos = livroRepository.buscarTitulosDosLivros();
        return ResponseEntity.ok(titulos);
    }

    @GetMapping("/autores")
    public ResponseEntity<ArrayList<String>> buscarAutoresDosLivros() {
        ArrayList<String> autores = livroRepository.buscarAutoresDosLivros();
        return ResponseEntity.ok(autores);
    }


}
