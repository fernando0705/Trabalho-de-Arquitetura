package br.unifil.agendamento.infrastructure.config;

import br.unifil.agendamento.infrastructure.persistence.entities.MedicoJpaEntity;
import br.unifil.agendamento.infrastructure.persistence.entities.PacienteJpaEntity;
import br.unifil.agendamento.infrastructure.persistence.repositories.MedicoJpaRepository;
import br.unifil.agendamento.infrastructure.persistence.repositories.PacienteJpaRepository;
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
            medicos.save(new MedicoJpaEntity(
                    UUID.fromString("55555555-5555-5555-5555-555555555555"),
                    "Dr. Rafael Lima", "CRM-34567", "Ortopedia"));
            medicos.save(new MedicoJpaEntity(
                    UUID.fromString("66666666-6666-6666-6666-666666666666"),
                    "Dra. Juliana Alves", "CRM-45678", "Pediatria"));
            medicos.save(new MedicoJpaEntity(
                    UUID.fromString("77777777-7777-7777-7777-777777777777"),
                    "Dr. Marcos Pereira", "CRM-56789", "Clínica Geral"));

            pacientes.save(new PacienteJpaEntity(
                    UUID.fromString("33333333-3333-3333-3333-333333333333"),
                    "João Victor Ferro", "123.456.789-00", "joao@email.com"));
            pacientes.save(new PacienteJpaEntity(
                    UUID.fromString("44444444-4444-4444-4444-444444444444"),
                    "Maria Santos", "987.654.321-00", "maria@email.com"));
            pacientes.save(new PacienteJpaEntity(
                    UUID.fromString("88888888-8888-8888-8888-888888888888"),
                    "Pedro Henrique Costa", "111.222.333-44", "pedro@email.com"));
            pacientes.save(new PacienteJpaEntity(
                    UUID.fromString("99999999-9999-9999-9999-999999999999"),
                    "Beatriz Oliveira", "222.333.444-55", "beatriz@email.com"));
            pacientes.save(new PacienteJpaEntity(
                    UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"),
                    "Lucas Martins", "333.444.555-66", "lucas@email.com"));
            pacientes.save(new PacienteJpaEntity(
                    UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"),
                    "Camila Rocha", "444.555.666-77", "camila@email.com"));
            pacientes.save(new PacienteJpaEntity(
                    UUID.fromString("cccccccc-cccc-cccc-cccc-cccccccccccc"),
                    "Thiago Nunes", "555.666.777-88", "thiago@email.com"));
            pacientes.save(new PacienteJpaEntity(
                    UUID.fromString("dddddddd-dddd-dddd-dddd-dddddddddddd"),
                    "Fernanda Dias", "666.777.888-99", "fernanda@email.com"));
            pacientes.save(new PacienteJpaEntity(
                    UUID.fromString("eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee"),
                    "Bruno Carvalho", "777.888.999-00", "bruno@email.com"));
            pacientes.save(new PacienteJpaEntity(
                    UUID.fromString("ffffffff-ffff-ffff-ffff-ffffffffffff"),
                    "Larissa Mendes", "888.999.000-11", "larissa@email.com"));
        };
    }
}