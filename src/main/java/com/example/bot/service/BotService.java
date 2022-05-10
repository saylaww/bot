package com.example.bot.service;

import com.example.bot.BotApplication;
import com.example.bot.entity.Report;
import com.example.bot.entity.Rule;
import com.example.bot.entity.Supervisor;
import com.example.bot.payload.ApiResponse;
import com.example.bot.payload.ChatDto;
import com.example.bot.payload.DateDto;
import com.example.bot.payload.TimeDto;
import com.example.bot.repository.ReportRepository;
import com.example.bot.repository.RuleRepository;
import com.example.bot.repository.SupervisorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BotService {

    @Autowired
    SupervisorRepository supervisorRepository;
    @Autowired
    ReportRepository reportRepository;
    @Autowired
    RuleRepository ruleRepository;
    @Autowired
    BotApplication botApplication;

    public ApiResponse getAllSupervisors() {
        List<Supervisor> all = supervisorRepository.findAll();
        return new ApiResponse("Supervisor list:", true, all);
    }

    public ApiResponse byDate(DateDto dateDto) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Date date = dateFormat.parse(dateDto.getStart());
        long time = date.getTime();
        Timestamp timestamp = new Timestamp(time);


        Date date2 = dateFormat.parse(dateDto.getEnd());
        long time2 = date2.getTime();
        Timestamp timestamp2 = new Timestamp(time2);
        timestamp2.setHours(23);
        timestamp2.setMinutes(59);
        timestamp2.setSeconds(59);

        List<Report> allReportList = reportRepository.findByDateBetween(timestamp, timestamp2);

        return new ApiResponse("All Supervisor report by date : ", true, allReportList);
    }


    public ApiResponse byDateSupervisor(Long id, DateDto dateDto) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Date date = dateFormat.parse(dateDto.getStart());
        long time = date.getTime();
        Timestamp timestamp = new Timestamp(time);

        Date date2 = dateFormat.parse(dateDto.getEnd());
        long time2 = date2.getTime();
        Timestamp timestamp2 = new Timestamp(time2);
        timestamp2.setHours(23);
        timestamp2.setMinutes(59);
        timestamp2.setSeconds(59);

        List<Report> byDateSupervisor = reportRepository.findBySupervisorIdAndDateBetween(id, timestamp, timestamp2);

        return new ApiResponse("All Supervisor report by date And by Supervisor Id: ", true, byDateSupervisor);
    }

    public ApiResponse editTime(TimeDto timeDto) {
        Optional<Rule> byId = ruleRepository.findById(1);
        if (byId.isPresent()){
            Rule rule = byId.get();
            rule.setStartHour(timeDto.getStartHour());
            rule.setStartMinute(timeDto.getStartMinute());
            rule.setEndHour(timeDto.getEndHour());
            rule.setEndMinute(timeDto.getEndMinute());
            ruleRepository.save(rule);

            ///
            boolean b = botApplication.pinGroup(timeDto);
            if (b){
                return new ApiResponse("Waqit o'zgerdi!", true);
            }
        }
        return new ApiResponse("Error", false);
    }


    public ApiResponse getAllReport() {
        List<Report> all = reportRepository.findAll();
        return new ApiResponse("All Report list", true, all);
    }

    public ApiResponse getRule() {
        Optional<Rule> byId = ruleRepository.findById(1);
        return new ApiResponse("Qag`iyda", true, byId.get());
    }

    public ApiResponse editChatId(ChatDto chatDto) {
        Optional<Rule> byId = ruleRepository.findById(1);
        if (byId.isPresent()){
            Rule rule = byId.get();
            rule.setChatId(chatDto.getChatId());
            ruleRepository.save(rule);
            return new ApiResponse("Chat id o'zgerdi!!!", true);
        }
        return new ApiResponse("Error", false);
    }
}
