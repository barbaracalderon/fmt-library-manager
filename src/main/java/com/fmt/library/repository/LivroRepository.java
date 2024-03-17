package com.fmt.library.repository;

import com.fmt.library.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Integer> {

    @Query("SELECT l.titulo FROM Livro l")
    ArrayList<String> buscarTitulosDosLivros();

    @Query("SELECT l.autor FROM Livro l")
    ArrayList<String> buscarAutoresDosLivros();

}
