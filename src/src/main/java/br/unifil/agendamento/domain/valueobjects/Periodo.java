package br.unifil.agendamento.domain.valueobjects;

import java.time.LocalDateTime;

public record Periodo(LocalDateTime inicio, LocalDateTime fim) {

    public Periodo {
        if (inicio == null || fim == null) {
            throw new IllegalArgumentException("Início e fim são obrigatórios");
        }

        if (!fim.isAfter(inicio)) {
            throw new IllegalArgumentException("Fim deve ser depois do início");
        }
    }

    public boolean sobrepoe(Periodo outro) {
        return this.inicio.isBefore(outro.fim)
                && outro.inicio.isBefore(this.fim);
    }
}
