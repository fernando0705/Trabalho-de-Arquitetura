package br.unifil.agendamento.application.usecases;

import br.unifil.agendamento.application.dtos.ConsultaOutput;
import br.unifil.agendamento.domain.gateways.ConsultaGateway;

import java.time.LocalDate;
import java.util.List;

public class ListarAgendaDoDiaUseCase {

    private final ConsultaGateway consultaGateway;

    public ListarAgendaDoDiaUseCase(ConsultaGateway consultaGateway) {
        this.consultaGateway = consultaGateway;
    }

    public List<ConsultaOutput> executar(LocalDate data) {

        return consultaGateway.buscarPorData(data).stream()
                .map(c -> new ConsultaOutput(
                        c.getId(),
                        c.getPacienteId(),
                        c.getMedicoId(),
                        c.getPeriodo().inicio(),
                        c.getPeriodo().fim(),
                        c.getStatus().name()
                ))
                .toList();
    }
}