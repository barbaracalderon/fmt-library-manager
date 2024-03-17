package com.fmt.library.repository;

import com.fmt.library.model.Membro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface MembroRepository extends JpaRepository<Membro, Integer> {

    @Query("SELECT m.nome FROM Membro m")
    ArrayList<String> buscarNomesDosMembros();

    @Query("SELECT m.telefone FROM Membro m")
    ArrayList<String> buscarTelefonesDosMembros();


}
