package br.unifil.agendamento.domain.exceptions;

public class HorarioIndisponivelException extends RuntimeException {

    public HorarioIndisponivelException(String mensagem) {
        super(mensagem);
    }
}
