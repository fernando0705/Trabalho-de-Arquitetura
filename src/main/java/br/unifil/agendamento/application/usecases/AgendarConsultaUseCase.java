package br.unifil.agendamento.application.usecases;

import br.unifil.agendamento.application.dtos.AgendarConsultaInput;
import br.unifil.agendamento.application.dtos.ConsultaOutput;
import br.unifil.agendamento.domain.entities.Consulta;
import br.unifil.agendamento.domain.entities.Medico;
import br.unifil.agendamento.domain.entities.Paciente;
import br.unifil.agendamento.domain.exceptions.HorarioIndisponivelException;
import br.unifil.agendamento.domain.gateways.ConsultaGateway;
import br.unifil.agendamento.domain.gateways.MedicoGateway;
import br.unifil.agendamento.domain.gateways.PacienteGateway;
import br.unifil.agendamento.domain.valueobjects.Periodo;

public class AgendarConsultaUseCase {

    private final ConsultaGateway consultaGateway;
    private final MedicoGateway medicoGateway;
    private final PacienteGateway pacienteGateway;

    public AgendarConsultaUseCase(
            ConsultaGateway consultaGateway,
            MedicoGateway medicoGateway,
            PacienteGateway pacienteGateway) {

        this.consultaGateway = consultaGateway;
        this.medicoGateway = medicoGateway;
        this.pacienteGateway = pacienteGateway;
    }

    public ConsultaOutput executar(AgendarConsultaInput input) {

        Medico medico = medicoGateway.buscarPorId(input.medicoId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Médico não encontrado"));

        Paciente paciente = pacienteGateway.buscarPorId(input.pacienteId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Paciente não encontrado"));

        Periodo periodo = new Periodo(input.inicio(), input.fim());

        boolean temConflito = consultaGateway
                .buscarPorMedicoEPeriodo(medico.getId(), periodo)
                .stream()
                .anyMatch(c -> c.conflitaCom(periodo));

        if (temConflito) {
            throw new HorarioIndisponivelException(
                    "Médico já tem consulta neste horário");
        }

        Consulta consulta = new Consulta(
                paciente.getId(),
                medico.getId(),
                periodo
        );

        Consulta salva = consultaGateway.salvar(consulta);

        return new ConsultaOutput(
                salva.getId(),
                salva.getPacienteId(),
                salva.getMedicoId(),
                salva.getPeriodo().inicio(),
                salva.getPeriodo().fim(),
                salva.getStatus().name()
        );
    }
}