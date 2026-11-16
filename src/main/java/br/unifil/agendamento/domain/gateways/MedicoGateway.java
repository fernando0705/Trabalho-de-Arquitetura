package br.unifil.agendamento.domain.gateways;

import br.unifil.agendamento.domain.entities.Medico;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MedicoGateway {

    Optional<Medico> buscarPorId(UUID id);

    List<Medico> listarTodos();

    List<Medico> listarPorEspecialidade(String especialidade);
}
