package com.example.bot.repository;

import com.example.bot.entity.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface SupervisorRepository extends JpaRepository<Supervisor,Long> {



    boolean existsByChatId(@NotNull String chatId);

    Optional<Supervisor> findByChatId(@NotNull String chatId);



}
