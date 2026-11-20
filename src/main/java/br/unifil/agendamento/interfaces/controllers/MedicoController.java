
package br.unifil.agendamento.interfaces.controllers;

import br.unifil.agendamento.domain.entities.Medico;
import br.unifil.agendamento.domain.gateways.MedicoGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicos")
public class MedicoController {

    private final MedicoGateway medicoGateway;

    public MedicoController(MedicoGateway medicoGateway) {
        this.medicoGateway = medicoGateway;
    }

    @GetMapping
    public ResponseEntity<List<Medico>> listar(
            @RequestParam(required = false) String especialidade) {

        if (especialidade != null && !especialidade.isBlank()) {
            return ResponseEntity.ok(
                    medicoGateway.listarPorEspecialidade(especialidade));
        }

        return ResponseEntity.ok(medicoGateway.listarTodos());
    }
}
