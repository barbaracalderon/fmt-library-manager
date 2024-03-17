package com.fmt.library.repository;

import com.fmt.library.model.Bibliotecario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface BibliotecarioRepository extends JpaRepository<Bibliotecario, Integer> {

    @Query("SELECT b.email FROM Bibliotecario b")
    ArrayList<String> buscarEmailsDosBibliotecarios();

}
