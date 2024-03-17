package model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Visitante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    private String telefone;
}
