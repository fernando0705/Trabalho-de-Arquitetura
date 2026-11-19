package br.unifil.agendamento.infrastructure.persistence.entities;

import br.unifil.agendamento.domain.entities.Medico;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "medicos")
public class MedicoJpaEntity {
    @Id
    private UUID id;
    @Column(nullable = false)
    private String nome;
    private String crm;
    private String especialidade;

    protected MedicoJpaEntity() {
    }

    public MedicoJpaEntity(UUID id, String nome, String crm, String especialidade) {
        this.id = id;
        this.nome = nome;
        this.crm = crm;
        this.especialidade = especialidade;
    }

    public static MedicoJpaEntity fromDomain(Medico m) {
        return new MedicoJpaEntity(m.getId(), m.getNome(), m.getCrm(),
                m.getEspecialidade());
    }

    public Medico toDomain() {
        return new Medico(id, nome, crm, especialidade);
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCrm() {
        return crm;
    }

    public String getEspecialidade() {
        return especialidade;
    }
}