package br.unifil.agendamento.infrastructure.persistence.entities;

import br.unifil.agendamento.domain.entities.Consulta;
import br.unifil.agendamento.domain.entities.StatusConsulta;
import br.unifil.agendamento.domain.valueobjects.Periodo;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "consultas")
public class ConsultaJpaEntity {
    @Id
    private UUID id;
    @Column(nullable = false)
    private UUID pacienteId;
    @Column(nullable = false)
    private UUID medicoId;
    @Column(nullable = false)
    private LocalDateTime inicio;
    @Column(nullable = false)
    private LocalDateTime fim;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusConsulta status;

    protected ConsultaJpaEntity() {
    }

    public ConsultaJpaEntity(UUID id, UUID pacienteId, UUID medicoId,
                             LocalDateTime inicio, LocalDateTime fim,
                             StatusConsulta status) {
        this.id = id;
        this.pacienteId = pacienteId;
        this.medicoId = medicoId;
        this.inicio = inicio;
        this.fim = fim;
        this.status = status;
    }

    public static ConsultaJpaEntity fromDomain(Consulta c) {
        return new ConsultaJpaEntity(
                c.getId(), c.getPacienteId(), c.getMedicoId(),
                c.getPeriodo().inicio(), c.getPeriodo().fim(), c.getStatus());
    }

    public Consulta toDomain() {
        return new Consulta(
                id, pacienteId, medicoId,
                new Periodo(inicio, fim), status);
    }

    public UUID getId() {
        return id;
    }

    public UUID getPacienteId() {
        return pacienteId;
    }

    public UUID getMedicoId() {
        return medicoId;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public LocalDateTime getFim() {
        return fim;
    }

    public StatusConsulta getStatus() {
        return status;
    }
}
