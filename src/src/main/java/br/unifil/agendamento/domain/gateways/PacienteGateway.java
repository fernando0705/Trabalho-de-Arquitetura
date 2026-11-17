package br.unifil.agendamento.domain.gateways;

import br.unifil.agendamento.domain.entities.Paciente;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PacienteGateway {

    Optional<Paciente> buscarPorId(UUID id);

    List<Paciente> listarTodos();
}
