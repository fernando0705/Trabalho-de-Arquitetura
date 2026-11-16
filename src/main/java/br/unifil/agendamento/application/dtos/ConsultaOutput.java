package br.unifil.agendamento.application.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record ConsultaOutput(
        UUID id,
        UUID pacienteId,
        UUID medicoId,
        LocalDateTime inicio,
        LocalDateTime fim,
        String status
) {
}