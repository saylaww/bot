package com.example.bot.repository;

import com.example.bot.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ReportRepository extends JpaRepository<Report,Long> {

    List<Report> findByDateBetween(Timestamp date, Timestamp date2);

    List<Report> findBySupervisorIdAndDateBetween(Long supervisor_id, Timestamp date, Timestamp date2);

    Optional<Report> findByMessageId(@NotNull String messageId);

    Set<Report> findDistinctByDateBetweenAndSupervisorId(Timestamp date, Timestamp date2, Long supervisor_id);



}
