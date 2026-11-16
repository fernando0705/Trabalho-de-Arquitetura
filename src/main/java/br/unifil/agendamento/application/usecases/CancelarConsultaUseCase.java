package br.unifil.agendamento.application.usecases;

import br.unifil.agendamento.domain.entities.Consulta;
import br.unifil.agendamento.domain.exceptions.ConsultaNaoEncontradaException;
import br.unifil.agendamento.domain.gateways.ConsultaGateway;

import java.time.LocalDateTime;
import java.util.UUID;

public class CancelarConsultaUseCase {

    private final ConsultaGateway consultaGateway;

    public CancelarConsultaUseCase(ConsultaGateway consultaGateway) {
        this.consultaGateway = consultaGateway;
    }

    public void executar(UUID consultaId) {

        Consulta consulta = consultaGateway.buscarPorId(consultaId)
                .orElseThrow(() -> new ConsultaNaoEncontradaException(
                        "Consulta não encontrada"));

        consulta.cancelar(LocalDateTime.now());

        consultaGateway.salvar(consulta);
    }
}