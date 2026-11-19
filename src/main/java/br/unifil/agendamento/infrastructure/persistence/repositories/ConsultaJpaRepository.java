package br.unifil.agendamento.infrastructure.persistence.repositories;

import br.unifil.agendamento.infrastructure.persistence.entities.ConsultaJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ConsultaJpaRepository extends JpaRepository<ConsultaJpaEntity, UUID> {
    List<ConsultaJpaEntity> findByMedicoIdAndInicioLessThanAndFimGreaterThan(
            UUID medicoId, LocalDateTime fim, LocalDateTime inicio);

    List<ConsultaJpaEntity> findByInicioBetween(LocalDateTime inicio, LocalDateTime
            fim);
}
