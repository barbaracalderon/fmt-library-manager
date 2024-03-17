package model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

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
    private Date dataEmprestimo;

    @Column(nullable = false)
    private Date dataDevolucao;
}
