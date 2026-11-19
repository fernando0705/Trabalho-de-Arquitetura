package br.unifil.agendamento.infrastructure.persistence.repositories;

import br.unifil.agendamento.infrastructure.persistence.entities.PacienteJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PacienteJpaRepository extends JpaRepository<PacienteJpaEntity, UUID> {
}