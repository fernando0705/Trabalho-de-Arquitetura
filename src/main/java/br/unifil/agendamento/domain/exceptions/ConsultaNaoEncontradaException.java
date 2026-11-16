package br.unifil.agendamento.domain.exceptions;

public class ConsultaNaoEncontradaException extends RuntimeException {

    public ConsultaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
