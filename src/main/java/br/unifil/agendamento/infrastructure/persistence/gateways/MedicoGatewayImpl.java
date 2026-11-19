package br.unifil.agendamento.infrastructure.persistence.gateways;

import br.unifil.agendamento.domain.entities.Medico;
import br.unifil.agendamento.domain.gateways.MedicoGateway;
import br.unifil.agendamento.infrastructure.persistence.entities.MedicoJpaEntity;
import
        br.unifil.agendamento.infrastructure.persistence.repositories.MedicoJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class MedicoGatewayImpl implements MedicoGateway {
    private final MedicoJpaRepository repository;

    public MedicoGatewayImpl(MedicoJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Medico> buscarPorId(UUID id) {
        return repository.findById(id).map(MedicoJpaEntity::toDomain);
    }

    @Override
    public List<Medico> listarTodos() {
        return repository.findAll().stream().map(MedicoJpaEntity::toDomain).toList();
    }

    @Override
    public List<Medico> listarPorEspecialidade(String especialidade) {
        return repository.findByEspecialidade(especialidade).stream()
                .map(MedicoJpaEntity::toDomain).toList();
    }
}