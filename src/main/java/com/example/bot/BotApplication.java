package com.example.bot;

import com.example.bot.entity.Report;
import com.example.bot.entity.Rule;
import com.example.bot.entity.Supervisor;
import com.example.bot.payload.RepDto;
import com.example.bot.payload.ReportDto;
import com.example.bot.payload.TimeDto;
import com.example.bot.repository.ReportRepository;
import com.example.bot.repository.RuleRepository;
import com.example.bot.repository.SupervisorRepository;
import org.glassfish.grizzly.http.util.TimeStamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.pinnedmessages.PinChatMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;

@SpringBootApplication
@EnableScheduling
public class BotApplication extends TelegramLongPollingBot {

    @Autowired
    SupervisorRepository supervisorRepository;
    @Autowired
    ReportRepository reportRepository;
    @Autowired
    RuleRepository ruleRepository;

    public static void main(String[] args) {
        SpringApplication.run(BotApplication.class, args);
    }

    @Override
    public String getBotUsername() {
        return Constatns.BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return Constatns.BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        LocalDate localDate = LocalDate.now();
        DayOfWeek currentDay = localDate.getDayOfWeek();

//        if (currentDay.getValue()!=DayOfWeek.SUNDAY.getValue()){
//            System.out.println("DURIS");
//        }

        checkSupervisor(update);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));

        Optional<Rule> optionalRule = ruleRepository.findById(1);
        Rule rule = optionalRule.get();

        if (update.getMessage().hasVideoNote()  &&
                update.getMessage().getForwardDate() == null &&
                currentDay.getValue()!=DayOfWeek.SUNDAY.getValue()) {

            Date date1 = convertIntToDate(update.getMessage().getDate());

            int hours = date1.getHours();
            int minutes = date1.getMinutes();

            /////////////

            if (hours >= Integer.valueOf(rule.getStartHour())) {
                if (hours==Integer.valueOf(rule.getStartHour()) && minutes >=Integer.valueOf(rule.getStartMinute())) {
                    if (hours <= Integer.valueOf(rule.getEndHour())) {
                        if (hours==Integer.valueOf(rule.getStartHour()) && minutes <= Integer.valueOf(rule.getEndMinute())) {
                            //TODO
                            System.out.println(update.getMessage().getChat().getId());
//                            String txt = "tg://openmessage?chat_id=" + (update.getMessage().getChatId() * (-1)) + "&message_id=" + update.getMessage().getMessageId();
                            //https://t.me/c/1651510322/14
//                        String urls = "https://api.telegram.org/file/bot" +Constants.BOT_TOKEN+ '/' +filePath;

                            /////

                            Optional<Supervisor> byChatId = supervisorRepository.findByChatId(update.getMessage().getFrom().getId().toString());
                            Supervisor supervisor = byChatId.get();
                            String txt = "https://t.me/c/" + (update.getMessage().getChatId() % 10000000000l*(-1)) + "/" + update.getMessage().getMessageId();
                            Report report = new Report(
                                    txt,
                                    supervisor,
                                    update.getMessage().getMessageId().toString()
                            );
                            reportRepository.save(report);

                            sendMessage.setText(txt);


                        }else if (minutes <= Integer.valueOf(rule.getEndMinute()) || minutes >= Integer.valueOf(rule.getEndMinute())){
                            //TODO
                            System.out.println(update.getMessage().getChat().getId());
//                            String txt = "tg://openmessage?chat_id=" + (update.getMessage().getChatId() * (-1)) + "&message_id=" + update.getMessage().getMessageId();
                            //https://t.me/c/1651510322/14
//                        String urls = "https://api.telegram.org/file/bot" +Constants.BOT_TOKEN+ '/' +filePath;

                            /////
                            Optional<Supervisor> byChatId = supervisorRepository.findByChatId(update.getMessage().getFrom().getId().toString());
                            Supervisor supervisor = byChatId.get();
                            String txt = "https://t.me/c/" + (update.getMessage().getChatId() % 10000000000l*(-1)) + "/" + update.getMessage().getMessageId();

//                            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

                            Report report = new Report(
                                    txt,
                                    supervisor,
                                    update.getMessage().getMessageId().toString()
                            );
//                            Report report1 = new Report(
//                                    txt,
//                                    timestamp,
//                                    supervisor,
//                                    update.getMessage().getMessageId().toString()
//                            );
                            reportRepository.save(report);

                            sendMessage.setText(txt);
                        }
                    }
                }else if (hours > Integer.valueOf(rule.getStartHour()) && (minutes >=Integer.valueOf(rule.getStartMinute()) || minutes <=Integer.valueOf(rule.getStartMinute()))){
                    if (hours <= Integer.valueOf(rule.getEndHour())) {
                        if (hours==Integer.valueOf(rule.getEndHour()) && minutes <= Integer.valueOf(rule.getEndMinute())) {
                            //TODO
                            System.out.println(update.getMessage().getChat().getId());
//                            String txt = "tg://openmessage?chat_id=" + (update.getMessage().getChatId() * (-1)) + "&message_id=" + update.getMessage().getMessageId();
                            //https://t.me/c/1651510322/14
//                        String urls = "https://api.telegram.org/file/bot" +Constants.BOT_TOKEN+ '/' +filePath;

                            /////
                            Optional<Supervisor> byChatId = supervisorRepository.findByChatId(update.getMessage().getFrom().getId().toString());
                            Supervisor supervisor = byChatId.get();
                            String txt = "https://t.me/c/" + (update.getMessage().getChatId() % 10000000000l*(-1)) + "/" + update.getMessage().getMessageId();
                            Report report = new Report(
                                    txt,
                                    supervisor,
                                    update.getMessage().getMessageId().toString()
                            );
                            reportRepository.save(report);

                            sendMessage.setText(txt);
                        }else if (hours < Integer.valueOf(rule.getEndHour()) && (minutes <=Integer.valueOf(rule.getEndMinute()) || minutes >= Integer.valueOf(rule.getEndMinute()))){
                            //TODO
                            System.out.println(update.getMessage().getChat().getId());
//                            String txt = "tg://openmessage?chat_id=" + (update.getMessage().getChatId() * (-1)) + "&message_id=" + update.getMessage().getMessageId();
                            //https://t.me/c/1651510322/14
//                        String urls = "https://api.telegram.org/file/bot" +Constants.BOT_TOKEN+ '/' +filePath;

                            /////
                            Optional<Supervisor> byChatId = supervisorRepository.findByChatId(update.getMessage().getFrom().getId().toString());
                            Supervisor supervisor = byChatId.get();
//                            String txt = "https://t.me/c/" + (update.getMessage().getChatId()*(-1)) + "/" + update.getMessage().getMessageId();
                            String txt = "https://t.me/c/" + (update.getMessage().getChatId() % 10000000000l*(-1)) + "/" + update.getMessage().getMessageId();
                            Report report = new Report(
                                    txt,
                                    supervisor,
                                    update.getMessage().getMessageId().toString()
                            );
                            reportRepository.save(report);

                            sendMessage.setText(txt);
                        }
                    }
                }
            }



            ////////////


//            if (hours >= 8) {
////                if (minutes>=30){
//                if (hours <= 22) {
//                    if (hours == 22 && minutes <= 59) {
//                        //TODO
//                        System.out.println(update.getMessage().getChat().getId());
//                        String txt = "tg://openmessage?chat_id=" + (update.getMessage().getChatId() * (-1)) + "&message_id=" + update.getMessage().getMessageId();
////                        String urls = "https://api.telegram.org/file/bot" +Constants.BOT_TOKEN+ '/' +filePath;
//                        sendMessage.setText(txt);
//
//                        /////
//                        Optional<Supervisor> byChatId = supervisorRepository.findByChatId(update.getMessage().getChatId().toString());
//                        Supervisor supervisor = byChatId.get();
//                        Report report = new Report(
//                                txt,
//                                supervisor,
//                                update.getMessage().getMessageId().toString()
//                        );
//                        reportRepository.save(report);
//
//                    }
//                    System.out.println("OKKKKKKKKKKK");
//                }
//            }

        }

    }

    private void checkSupervisor(Update update) {
        if (!update.getMessage().getFrom().getIsBot()) {
            boolean existsByChatId = supervisorRepository.existsByChatId(update.getMessage().getFrom().getId().toString());
            if (!existsByChatId) {
                Supervisor supervisor = new Supervisor(
                        update.getMessage().getFrom().getFirstName(),
                        update.getMessage().getFrom().getLastName(),
                        update.getMessage().getFrom().getUserName(),
                        update.getMessage().getFrom().getId().toString(),
                        update.getMessage().getChatId().toString()
                );
                supervisorRepository.save(supervisor);
            }
        }
    }

    public Date convertIntToDate(Integer intDate) {

        int intYear = intDate / 100;
//        int intMonth = intDate - (intYear * 100);
        int intMonth = intDate % 100;
        Calendar result = new GregorianCalendar();
//        result.set(intYear, intMonth - 1, 1, 0, 0, 0);
        result.set(Calendar.YEAR, intYear);
        result.set(Calendar.MONTH, intMonth - 1);
        result.set(Calendar.DAY_OF_MONTH, 1);
        return result.getTime();
    }

    @Scheduled(cron = "0 " + Constatns.SEND_MINUTE +" " + Constatns.SEND_HOUR + " * * *")
    public void test() throws Exception {
        Timestamp start = new Timestamp(System.currentTimeMillis());
        Timestamp end = new Timestamp(System.currentTimeMillis());

        start.setHours(0);
        start.setMinutes(0);
        start.setSeconds(1);

        end.setHours(23);
        end.setMinutes(59);
        end.setSeconds(59);

        List<Report> dayReport = reportRepository.findByDateBetween(start, end);

        List<Supervisor> supervisors = supervisorRepository.findAll();

//        sendApiMethodAsync(PinChatMessage.builder()
//                .chatId(supervisors.get(1).getGroupChatId())
//                .messageId(55)
//                .build());

        Set<ReportDto> reportDtoSet = new HashSet<>();

        for (Supervisor supervisor : supervisors) {
            ReportDto reportDto = new ReportDto();
            reportDto.setSupervisor(supervisor);
            for (Report report : dayReport) {
                if (supervisor.getId()==report.getSupervisor().getId()){
                    reportDto.setTime(report.getDate().toString());
                }
            }
            reportDtoSet.add(reportDto);
        }

        SendMessage sendMessage = new SendMessage();
        sendMessage.enableHtml(true);

        Optional<Rule> byId = ruleRepository.findById(1);
        if (!byId.isPresent()){
            throw new Exception("Bunday qagi'yda ya'ki chat_id tabilmadi!!!");
        }
        Rule rule = byId.get();
        String chatId = rule.getChatId();

//        sendMessage.setChatId(Constatns.SENDER_CHAT_ID);
        sendMessage.setChatId(chatId);

        String repo = "";
        int count = 1;
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        repo = repo + "<b>"+ date + "</b>\n\n";
        for (ReportDto reportDto : reportDtoSet) {
            if (reportDto.getTime()==null){
                repo = repo + count +" - " + reportDto.getSupervisor().getFirstName() + " "+ reportDto.getSupervisor().getLastName() + "     " +"Taslamadi\n";
            }else {
                repo = repo+ count +" - " + reportDto.getSupervisor().getFirstName()+" "+ reportDto.getSupervisor().getLastName() + "     " + reportDto.getTime().substring(11,19) + "\n";
            }
            count++;
        }

        sendMessage.setText(repo);
        execute(sendMessage);
    }

    public boolean pinGroup(TimeDto timeDto) {
        try {
            SendMessage sendMessage = new SendMessage();
            Optional<Supervisor> optionalSupervisor = supervisorRepository.findById(1l);

            sendMessage.setChatId(optionalSupervisor.get().getGroupChatId());
            sendMessage.setText("Video jiberiw waqitlar o'zgerdi!!! " + timeDto.getStartHour() + ":" + timeDto.getStartMinute() + " dan " +
                    timeDto.getEndHour() + ":" + timeDto.getEndMinute() + " qa o'zgerdi!!!");

            Message execute = execute(sendMessage);

            // Pin group message
            Optional<Supervisor> byId = supervisorRepository.findById(1l);

            Supervisor supervisor = byId.get();

//            Optional<Report> optionalReport = reportRepository.findById(supervisor.getId());
//            if (!optionalReport.isPresent()){
//                return false;
//            }
//            Report report = optionalReport.get();

            sendApiMethodAsync(PinChatMessage.builder()
                    .chatId(String.valueOf(supervisor.getGroupChatId()))
                    .messageId(Integer.valueOf(execute.getMessageId()))
                    .build());

            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Scheduled(cron = "0 "+Constatns.SEND_MONTH_MINUTE + " " + Constatns.SEND_MONTH_HOUR +" * * *")
    public void reportMonth() throws Exception {

        String[] monthName = {"Yanvar", "Fevral",
                "Mart", "Aprel", "May", "Iyun", "Iyul",
                "August", "Sentyabr", "Oktyabr", "Noyabr",
                "Dekabr"};

        Calendar cal = Calendar.getInstance();
        String month = monthName[cal.get(Calendar.MONTH)];

        /////////////////////

        int lastDay = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        int currentDay = timestamp.getDate();

        timestamp.setDate(1);
        timestamp.setHours(0);
        timestamp.setMinutes(0);
        timestamp.setSeconds(1);

        Timestamp end = new Timestamp(System.currentTimeMillis());
        end.setDate(lastDay);
        end.setHours(23);
        end.setMinutes(59);
        end.setSeconds(59);

        if (lastDay == currentDay){
            List<Supervisor> supervisors = supervisorRepository.findAll();

            Set<RepDto> repDtoSet = new HashSet<>();

            for (Supervisor supervisor : supervisors) {
//                Set<Report> reports = reportRepository.findDistinctBySupervisorIdAndDateBetween(supervisor.getId(), timestamp, end);
                Set<Report> reports = reportRepository.findDistinctByDateBetweenAndSupervisorId(timestamp, end, supervisor.getId());

                RepDto repDto = new RepDto();
                repDto.setSupervisor(supervisor);

                int counter = 0;
                for (int i = 1; i <= lastDay; i++) {

                    for (Report report : reports) {
                        if (i==report.getDate().getDate()){
                            counter++;
                            break;
                        }
                    }
                }
                repDto.setCount(counter);

                repDtoSet.add(repDto);
            }

            SendMessage sendMessage = new SendMessage();

            Optional<Rule> byId = ruleRepository.findById(1);
            if (!byId.isPresent()){
                throw new Exception("Bunday qagi'yda ya'ki chat_id tabilmadi!!!");
            }
            Rule rule = byId.get();
            String chatId = rule.getChatId();

            sendMessage.setChatId(chatId);
            sendMessage.enableHtml(true);

            String repo = "";
            int num = 1;
            java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
            repo = repo +"<b>"+ date + " - " + month + " ayi ushin esabat.</b>\n\n";
            for (RepDto reportDto : repDtoSet) {
                repo = repo+ num +" - " + reportDto.getSupervisor().getFirstName()+" "+ reportDto.getSupervisor().getLastName() + "     " + reportDto.getCount() + " - video tasladi\n";
                num++;
            }

            sendMessage.setText(repo);
            execute(sendMessage);
        }

    }


}
