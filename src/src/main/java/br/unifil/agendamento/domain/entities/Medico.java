package br.unifil.agendamento.domain.entities;

import java.util.UUID;

public class Medico {

    private final UUID id;
    private final String nome;
    private final String crm;
    private final String especialidade;

    public Medico(UUID id, String nome, String crm, String especialidade) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        this.id = id;
        this.nome = nome;
        this.crm = crm;
        this.especialidade = especialidade;
    }

    public UUID getId() { return id; }
    public String getNome() { return nome; }
    public String getCrm() { return crm; }
    public String getEspecialidade() { return especialidade; }
}
