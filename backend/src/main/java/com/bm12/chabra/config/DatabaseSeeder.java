package com.bm12.chabra.config;

import com.bm12.chabra.dto.user.SaveUser;
import com.bm12.chabra.model.Priority;
import com.bm12.chabra.model.Status;
import com.bm12.chabra.model.enums.PriorityLevel;
import com.bm12.chabra.model.enums.StatusType;
import com.bm12.chabra.model.enums.UserRole;
import com.bm12.chabra.repository.PriorityRepository;
import com.bm12.chabra.repository.StatusRepository;
import com.bm12.chabra.repository.UserRepository;
import com.bm12.chabra.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DatabaseSeeder {

    @Bean
    CommandLineRunner initDatabase(
            StatusRepository statusRepository,
            PriorityRepository priorityRepository,
            UserRepository userRepository,
            UserService userService
    ) {
        return args -> {
            if (statusRepository.count() == 0) {
                statusRepository.saveAndFlush(new Status(
                        "#ED1C24",
                        "Não iniciado",
                        StatusType.NOT_STARTED));
                statusRepository.saveAndFlush(new Status(
                        "#FFFD55",
                        "Em progresso",
                        StatusType.IN_PROGRESS));
                statusRepository.saveAndFlush(new Status(
                        "#75F94D",
                        "Concluído",
                        StatusType.COMPLETED));
            }

            if (priorityRepository.count() == 0) {
                priorityRepository.saveAndFlush(new Priority(
                        "Urgente",
                        "#C62A2F",
                        PriorityLevel.URGENT));

                priorityRepository.saveAndFlush(new Priority(
                        "Alta",
                        "#FFC53D",
                        PriorityLevel.HIGH));
                priorityRepository.saveAndFlush(new Priority(
                        "Média",
                        "#3E63DD",
                        PriorityLevel.NORMAL));

                priorityRepository.saveAndFlush(new Priority(
                        "Baixa",
                        "#BBBBBB",
                        PriorityLevel.LOW));

            }

            if(userRepository.count() == 0) {
                userService.signUp(new SaveUser("SUPERVISOR", "chabra@chabra.com", "@Chabra2025", UserRole.ADMIN));
            }
        };
    }
}
