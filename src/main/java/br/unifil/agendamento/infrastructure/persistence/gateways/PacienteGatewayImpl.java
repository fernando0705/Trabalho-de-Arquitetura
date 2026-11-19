package br.unifil.agendamento.infrastructure.persistence.gateways;

import br.unifil.agendamento.domain.entities.Paciente;
import br.unifil.agendamento.domain.gateways.PacienteGateway;
import br.unifil.agendamento.infrastructure.persistence.entities.PacienteJpaEntity;
import
        br.unifil.agendamento.infrastructure.persistence.repositories.PacienteJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class PacienteGatewayImpl implements PacienteGateway {
    private final PacienteJpaRepository repository;

    public PacienteGatewayImpl(PacienteJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Paciente> buscarPorId(UUID id) {
        return repository.findById(id).map(PacienteJpaEntity::toDomain);
    }

    @Override
    public List<Paciente> listarTodos() {
        return repository.findAll().stream().map(PacienteJpaEntity::toDomain).toList();
    }
}