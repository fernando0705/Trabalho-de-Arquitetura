package br.unifil.agendamento.domain.entities;

import br.unifil.agendamento.domain.exceptions.HorarioIndisponivelException;
import br.unifil.agendamento.domain.valueobjects.Periodo;

import java.time.LocalDateTime;
import java.util.UUID;

public class Consulta {

    private final UUID id;
    private final UUID pacienteId;
    private final UUID medicoId;
    private final Periodo periodo;
    private StatusConsulta status;

    public Consulta(UUID pacienteId, UUID medicoId, Periodo periodo) {
        if (pacienteId == null || medicoId == null) {
            throw new IllegalArgumentException("Paciente e médico são obrigatórios");
        }

        this.id = UUID.randomUUID();
        this.pacienteId = pacienteId;
        this.medicoId = medicoId;
        this.periodo = periodo;
        this.status = StatusConsulta.AGENDADA;
    }

    public Consulta(UUID id, UUID pacienteId, UUID medicoId,
                    Periodo periodo, StatusConsulta status) {
        this.id = id;
        this.pacienteId = pacienteId;
        this.medicoId = medicoId;
        this.periodo = periodo;
        this.status = status;
    }

    public void cancelar(LocalDateTime agora) {
        if (this.status == StatusConsulta.CANCELADA) {
            throw new IllegalStateException("Consulta já está cancelada");
        }

        if (periodo.inicio().isBefore(agora)) {
            throw new HorarioIndisponivelException(
                    "Não é possível cancelar consulta que já começou");
        }

        this.status = StatusConsulta.CANCELADA;
    }

    public boolean conflitaCom(Periodo outro) {
        return this.status == StatusConsulta.AGENDADA
                && this.periodo.sobrepoe(outro);
    }

    public UUID getId() { return id; }
    public UUID getPacienteId() { return pacienteId; }
    public UUID getMedicoId() { return medicoId; }
    public Periodo getPeriodo() { return periodo; }
    public StatusConsulta getStatus() { return status; }
}
