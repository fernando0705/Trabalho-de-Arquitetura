
package br.unifil.agendamento.interfaces.controllers;

import br.unifil.agendamento.domain.entities.Paciente;
import br.unifil.agendamento.domain.gateways.PacienteGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final PacienteGateway pacienteGateway;

    public PacienteController(PacienteGateway pacienteGateway) {
        this.pacienteGateway = pacienteGateway;
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> listar() {
        return ResponseEntity.ok(pacienteGateway.listarTodos());
    }
}
