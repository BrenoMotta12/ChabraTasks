package com.bm12.chabra.config;

import com.bm12.chabra.model.Status;
import com.bm12.chabra.model.enums.StatusType;
import com.bm12.chabra.repository.StatusRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DatabaseSeeder {

    @Bean
    CommandLineRunner initDatabase(StatusRepository statusRepository) {
        return args -> {
            if (statusRepository.count() == 0) {
                statusRepository.saveAndFlush(new Status(
                        "#FFF",
                        "Não iniciado",
                        StatusType.NOT_STARTED));
                statusRepository.saveAndFlush(new Status(
                        "#FFF",
                        "Em progresso",
                        StatusType.IN_PROGRESS));
                statusRepository.saveAndFlush(new Status(
                        "#FFF",
                        "Concluído",
                        StatusType.COMPLETED));
            }
        };
    }
}
