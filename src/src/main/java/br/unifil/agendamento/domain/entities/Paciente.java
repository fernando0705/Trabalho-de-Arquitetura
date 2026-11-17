package br.unifil.agendamento.domain.entities;

import java.util.UUID;

public class Paciente {

    private final UUID id;
    private final String nome;
    private final String cpf;
    private final String email;

    public Paciente(UUID id, String nome, String cpf, String email) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
    }

    public UUID getId() { return id; }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getEmail() { return email; }
}
