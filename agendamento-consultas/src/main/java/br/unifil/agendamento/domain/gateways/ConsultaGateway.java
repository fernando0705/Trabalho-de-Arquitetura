package br.unifil.agendamento.domain.gateways;

import br.unifil.agendamento.domain.entities.Consulta;
import br.unifil.agendamento.domain.valueobjects.Periodo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConsultaGateway {

    Consulta salvar(Consulta consulta);

    Optional<Consulta> buscarPorId(UUID id);

    List<Consulta> buscarPorMedicoEPeriodo(UUID medicoId, Periodo periodo);

    List<Consulta> buscarPorData(LocalDate data);
}
