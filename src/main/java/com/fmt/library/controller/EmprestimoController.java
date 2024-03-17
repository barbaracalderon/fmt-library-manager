package com.fmt.library.controller;

import com.fmt.library.model.Emprestimo;
import com.fmt.library.model.Livro;
import com.fmt.library.repository.EmprestimoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
