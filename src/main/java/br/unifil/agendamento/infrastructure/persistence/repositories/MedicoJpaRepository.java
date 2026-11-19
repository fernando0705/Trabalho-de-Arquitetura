package br.unifil.agendamento.infrastructure.persistence.repositories;

import br.unifil.agendamento.infrastructure.persistence.entities.MedicoJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MedicoJpaRepository extends JpaRepository<MedicoJpaEntity, UUID> {
    List<MedicoJpaEntity> findByEspecialidade(String especialidade);
}
