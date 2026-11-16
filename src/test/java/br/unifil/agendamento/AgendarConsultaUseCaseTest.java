package br.unifil.agendamento;

import br.unifil.agendamento.application.dtos.AgendarConsultaInput;
import br.unifil.agendamento.application.usecases.AgendarConsultaUseCase;
import br.unifil.agendamento.domain.entities.Consulta;
import br.unifil.agendamento.domain.entities.Medico;
import br.unifil.agendamento.domain.entities.Paciente;
import br.unifil.agendamento.domain.exceptions.HorarioIndisponivelException;
import br.unifil.agendamento.domain.gateways.ConsultaGateway;
import br.unifil.agendamento.domain.gateways.MedicoGateway;
import br.unifil.agendamento.domain.gateways.PacienteGateway;
import br.unifil.agendamento.domain.valueobjects.Periodo;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AgendarConsultaUseCaseTest {

    private final UUID medicoId = UUID.randomUUID();
    private final UUID pacienteId = UUID.randomUUID();

    private final List<Consulta> consultas = new ArrayList<>();

    private final ConsultaGateway consultaGateway = new ConsultaGateway() {

        @Override
        public Consulta salvar(Consulta c) {
            consultas.add(c);
            return c;
        }

        @Override
        public Optional<Consulta> buscarPorId(UUID id) {
            return consultas.stream()
                    .filter(c -> c.getId().equals(id))
                    .findFirst();
        }

        @Override
        public List<Consulta> buscarPorMedicoEPeriodo(UUID mId, Periodo p) {
            return consultas.stream()
                    .filter(c -> c.getMedicoId().equals(mId))
                    .filter(c -> c.getPeriodo().sobrepoe(p))
                    .toList();
        }

        @Override
        public List<Consulta> buscarPorData(LocalDate d) {
            return consultas;
        }
    };

    private final MedicoGateway medicoGateway = new MedicoGateway() {

        @Override
        public Optional<Medico> buscarPorId(UUID id) {
            return Optional.of(
                    new Medico(id, "Dr. Silva", "12345", "Cardiologia")
            );
        }

        @Override
        public List<Medico> listarTodos() {
            return List.of();
        }

        @Override
        public List<Medico> listarPorEspecialidade(String e) {
            return List.of();
        }
    };

    private final PacienteGateway pacienteGateway = new PacienteGateway() {

        @Override
        public Optional<Paciente> buscarPorId(UUID id) {
            return Optional.of(
                    new Paciente(id, "João", "000", "joao@email.com")
            );
        }

        @Override
        public List<Paciente> listarTodos() {
            return List.of();
        }
    };

    @Test
    void naoDeveAgendarQuandoMedicoJaTemConsultaNoHorario() {

        var useCase = new AgendarConsultaUseCase(
                consultaGateway,
                medicoGateway,
                pacienteGateway
        );

        var input = new AgendarConsultaInput(
                pacienteId,
                medicoId,
                LocalDateTime.of(2026, 11, 24, 14, 0),
                LocalDateTime.of(2026, 11, 24, 14, 30)
        );

        useCase.executar(input);

        assertThatThrownBy(() -> useCase.executar(input))
                .isInstanceOf(HorarioIndisponivelException.class);
    }
}
