package com.fmt.library.model;

import jakarta.persistence.*;


import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_livro")
    private Livro livro;

    @ManyToOne
    @JoinColumn(name = "id_membro")
    private Membro membro;

    @Column(nullable = false)
    private LocalDate dataEmprestimo;

    @Column(nullable = false)
    private LocalDate dataDevolucao;

    public Emprestimo() {
    }

    public Emprestimo(Livro livro, Membro membro, LocalDate dataEmprestimo, LocalDate dataDevolucao) {
        this.livro = livro;
        this.membro = membro;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
    }

    public Integer getId() {
        return id;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public Integer getLivro() {
        return livro.getId();
    }

    public Integer getMembro() {
        return membro.getId();
    }



}
