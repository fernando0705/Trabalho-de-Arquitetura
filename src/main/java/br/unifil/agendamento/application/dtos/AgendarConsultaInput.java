package br.unifil.agendamento.application.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record AgendarConsultaInput(
        UUID pacienteId,
        UUID medicoId,
        LocalDateTime inicio,
        LocalDateTime fim
) {
}