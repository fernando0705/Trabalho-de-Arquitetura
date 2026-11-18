package br.unifil.agendamento.domain;

import br.unifil.agendamento.domain.valueobjects.Periodo;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PeriodoTest {

    @Test
    void deveDetectarSobreposicao() {
        var p1 = new Periodo(
                LocalDateTime.of(2026, 11, 24, 14, 0),
                LocalDateTime.of(2026, 11, 24, 14, 30));
        var p2 = new Periodo(
                LocalDateTime.of(2026, 11, 24, 14, 15),
                LocalDateTime.of(2026, 11, 24, 14, 45));
        assertThat(p1.sobrepoe(p2)).isTrue();
    }

    @Test
    void naoDeveDetectarSobreposicaoEmHorariosDistintos() {
        var p1 = new Periodo(
                LocalDateTime.of(2026, 11, 24, 14, 0),
                LocalDateTime.of(2026, 11, 24, 14, 30));
        var p2 = new Periodo(
                LocalDateTime.of(2026, 11, 24, 15, 0),
                LocalDateTime.of(2026, 11, 24, 15, 30));
        assertThat(p1.sobrepoe(p2)).isFalse();
    }

    @Test
    void deveRejeitarFimAntesDoInicio() {
        assertThatThrownBy(() -> new Periodo(
                LocalDateTime.of(2026, 11, 24, 15, 0),
                LocalDateTime.of(2026, 11, 24, 14, 0)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}