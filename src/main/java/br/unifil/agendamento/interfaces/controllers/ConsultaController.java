
package br.unifil.agendamento.interfaces.controllers;

import br.unifil.agendamento.application.dtos.AgendarConsultaInput;
import br.unifil.agendamento.application.dtos.ConsultaOutput;
import br.unifil.agendamento.application.usecases.AgendarConsultaUseCase;
import br.unifil.agendamento.application.usecases.CancelarConsultaUseCase;
import br.unifil.agendamento.application.usecases.ListarAgendaDoDiaUseCase;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/consultas")
public class ConsultaController {

    private final AgendarConsultaUseCase agendarConsulta;
    private final CancelarConsultaUseCase cancelarConsulta;
    private final ListarAgendaDoDiaUseCase listarAgenda;

    public ConsultaController(
            AgendarConsultaUseCase agendarConsulta,
            CancelarConsultaUseCase cancelarConsulta,
            ListarAgendaDoDiaUseCase listarAgenda) {

        this.agendarConsulta = agendarConsulta;
        this.cancelarConsulta = cancelarConsulta;
        this.listarAgenda = listarAgenda;
    }

    @PostMapping
    public ResponseEntity<ConsultaOutput> agendar(
            @RequestBody AgendarConsultaInput input) {

        ConsultaOutput output = agendarConsulta.executar(input);

        return ResponseEntity.status(201).body(output);
    }

    @GetMapping("/agenda")
    public ResponseEntity<List<ConsultaOutput>> agenda(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate data) {

        return ResponseEntity.ok(listarAgenda.executar(data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable UUID id) {

        cancelarConsulta.executar(id);

        return ResponseEntity.noContent().build();
    }
}
