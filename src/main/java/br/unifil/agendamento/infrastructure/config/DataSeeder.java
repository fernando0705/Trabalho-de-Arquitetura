package br.unifil.agendamento.infrastructure.config;

import br.unifil.agendamento.infrastructure.persistence.entities.MedicoJpaEntity;
import br.unifil.agendamento.infrastructure.persistence.entities.PacienteJpaEntity;
import
        br.unifil.agendamento.infrastructure.persistence.repositories.MedicoJpaRepository;
import
        br.unifil.agendamento.infrastructure.persistence.repositories.PacienteJpaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class DataSeeder {
    @Bean
    CommandLineRunner seed(MedicoJpaRepository medicos, PacienteJpaRepository
            pacientes) {
        return args -> {
            medicos.save(new MedicoJpaEntity(
                    UUID.fromString("11111111-1111-1111-1111-111111111111"),
                    "Dr. Carlos Silva", "CRM-12345", "Cardiologia"));
            medicos.save(new MedicoJpaEntity(
                    UUID.fromString("22222222-2222-2222-2222-222222222222"),
                    "Dra. Ana Souza", "CRM-67890", "Dermatologia"));
            pacientes.save(new PacienteJpaEntity(
                    UUID.fromString("33333333-3333-3333-3333-333333333333"),
                    "João Victor Ferro", "123.456.789-00", "joao@email.com"));
            pacientes.save(new PacienteJpaEntity(
                    UUID.fromString("44444444-4444-4444-4444-444444444444"),
                    "Maria Santos", "987.654.321-00", "maria@email.com"));
        };
    }
}
