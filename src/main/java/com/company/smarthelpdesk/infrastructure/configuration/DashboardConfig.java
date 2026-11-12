package com.company.smarthelpdesk.infrastructure.configuration;

import com.company.smarthelpdesk.application.query.dashboard.GetDashboardSummaryUseCase;

import com.company.smarthelpdesk.domain.port.out.TicketRepositoryPort;

import com.company.smarthelpdesk.infrastructure.adapters.persistence.memory.InMemoryTicketRepositoryAdapter;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

@Configuration

public class DashboardConfig {

    @Bean

    public TicketRepositoryPort ticketRepositoryPort() {

        return new InMemoryTicketRepositoryAdapter();

    }

    @Bean

    public GetDashboardSummaryUseCase getDashboardSummaryUseCase(

            TicketRepositoryPort ticketRepositoryPort

    ) {

        return new GetDashboardSummaryUseCase(

                ticketRepositoryPort

        );

    }

}
