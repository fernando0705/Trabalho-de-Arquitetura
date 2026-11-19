package br.unifil.agendamento.infrastructure.persistence.gateways;

import br.unifil.agendamento.domain.entities.Consulta;
import br.unifil.agendamento.domain.gateways.ConsultaGateway;
import br.unifil.agendamento.domain.valueobjects.Periodo;
import br.unifil.agendamento.infrastructure.persistence.entities.ConsultaJpaEntity;
import
        br.unifil.agendamento.infrastructure.persistence.repositories.ConsultaJpaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ConsultaGatewayImpl implements ConsultaGateway {
    private final ConsultaJpaRepository repository;

    public ConsultaGatewayImpl(ConsultaJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Consulta salvar(Consulta consulta) {
        ConsultaJpaEntity salva =
                repository.save(ConsultaJpaEntity.fromDomain(consulta));
        return salva.toDomain();
    }

    @Override
    public Optional<Consulta> buscarPorId(UUID id) {
        return repository.findById(id).map(ConsultaJpaEntity::toDomain);
    }

    @Override
    public List<Consulta> buscarPorMedicoEPeriodo(UUID medicoId, Periodo periodo) {
        return repository
                .findByMedicoIdAndInicioLessThanAndFimGreaterThan(
                        medicoId, periodo.fim(), periodo.inicio())
                .stream()
                .map(ConsultaJpaEntity::toDomain)
                .toList();
    }

    @Override
    public List<Consulta> buscarPorData(LocalDate data) {
        return repository
                .findByInicioBetween(data.atStartOfDay(), data.atTime(23, 59, 59))
                .stream()
                .map(ConsultaJpaEntity::toDomain)
                .toList();
    }
}