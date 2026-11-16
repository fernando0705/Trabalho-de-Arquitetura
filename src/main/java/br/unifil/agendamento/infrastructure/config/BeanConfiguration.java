package br.unifil.agendamento.infrastructure.config;

import br.unifil.agendamento.application.usecases.AgendarConsultaUseCase;
import br.unifil.agendamento.domain.gateways.ConsultaGateway;
import br.unifil.agendamento.domain.gateways.MedicoGateway;
import br.unifil.agendamento.domain.gateways.PacienteGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public AgendarConsultaUseCase agendarConsultaUseCase(
            ConsultaGateway consultaGateway,
            MedicoGateway medicoGateway,
            PacienteGateway pacienteGateway) {
        return new AgendarConsultaUseCase(consultaGateway, medicoGateway, pacienteGateway);
    }
}