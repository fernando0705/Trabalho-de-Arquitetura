package br.unifil.agendamento.infrastructure.persistence.entities;

import br.unifil.agendamento.domain.entities.Paciente;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "pacientes")
public class PacienteJpaEntity {
    @Id
    private UUID id;
    @Column(nullable = false)
    private String nome;
    private String cpf;
    private String email;

    protected PacienteJpaEntity() {
    }

    public PacienteJpaEntity(UUID id, String nome, String cpf, String email) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
    }

    public static PacienteJpaEntity fromDomain(Paciente p) {
        return new PacienteJpaEntity(p.getId(), p.getNome(), p.getCpf(), p.getEmail());
    }

    public Paciente toDomain() {
        return new Paciente(id, nome, cpf, email);
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }
}
