package com.fmt.library.controller;

import com.fmt.library.model.Emprestimo;
import com.fmt.library.repository.EmprestimoRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/emprestimo")
public class EmprestimoController {

    EmprestimoRepository emprestimoRepository;

    public EmprestimoController(EmprestimoRepository emprestimoRepository) {
        this.emprestimoRepository = emprestimoRepository;
    }

    @PostMapping
    public ResponseEntity<Emprestimo> criarUmEmprestimo(@RequestBody Emprestimo emprestimo) {
        Emprestimo novoEmprestimo = emprestimoRepository.save(emprestimo);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoEmprestimo);
    }

    @GetMapping
    public ResponseEntity<ArrayList<Emprestimo>> buscarTodosOsEmprestimos() {
        ArrayList<Emprestimo> emprestimos = (ArrayList<Emprestimo>) emprestimoRepository.findAll();
        if (emprestimos.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(emprestimos);
        }
    }

    @GetMapping("{idEmprestimo}")
    public ResponseEntity<Emprestimo> buscarUmEmprestimo(@PathVariable Integer idEmprestimo) {
        Optional<Emprestimo> emprestimoEncontrado = emprestimoRepository.findById(idEmprestimo);
        return emprestimoEncontrado.map(
                emprestimo -> new ResponseEntity<>(emprestimo, HttpStatus.FOUND)
        ).orElseGet(
                () -> new ResponseEntity<>(HttpStatus.NOT_FOUND)
        );
    }

    @PutMapping("{idEmprestimo}")
    public ResponseEntity<Optional<Emprestimo>> atualizarUmEmprestimo(@PathVariable Integer idEmprestimo, @RequestBody Emprestimo emprestimoAtualizado) {
        Optional<Emprestimo> emprestimoParaAtualizar = emprestimoRepository.findById(idEmprestimo);
        if (emprestimoParaAtualizar.isPresent()) {
            Emprestimo emprestimoExistente = emprestimoParaAtualizar.get();
            emprestimoExistente.setDataEmprestimo(emprestimoAtualizado.getDataEmprestimo());
            emprestimoExistente.setDataDevolucao(emprestimoAtualizado.getDataDevolucao());
            emprestimoExistente.setLivro(emprestimoAtualizado.getLivro());
            emprestimoExistente.setMembro(emprestimoAtualizado.getMembro());

            Emprestimo emprestimoAtualizadoSalvo = emprestimoRepository.save(emprestimoExistente);
            return ResponseEntity.ok(Optional.of(emprestimoAtualizadoSalvo));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("{idEmprestimo}")
    public ResponseEntity<Void> deletarUmEmprestimo(@PathVariable Integer idEmprestimo) {
        emprestimoRepository.deleteById(idEmprestimo);
        return ResponseEntity.noContent().build();
    }


}
