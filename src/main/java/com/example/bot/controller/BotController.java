package com.example.bot.controller;

import com.example.bot.payload.ApiResponse;
import com.example.bot.payload.DateDto;
import com.example.bot.payload.TimeDto;
import com.example.bot.service.BotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

//@PreAuthorize(value = "hasRole('ADMIN')")
//@PreAuthorize(value = "hasAnyAuthority('SUPER_ADMIN','ADMIN')")
@RestController
@PreAuthorize(value = "hasAuthority('SUPER_ADMIN')")
@RequestMapping("/bot")
public class BotController {

    @Autowired
    BotService botService;

    @GetMapping("/getAllSupervisor")
    public HttpEntity<?> getSupervisors() {
        ApiResponse apiResponse = botService.getAllSupervisors();
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    @GetMapping("/getRule")
    public HttpEntity<?> getRule() {
        ApiResponse apiResponse = botService.getRule();
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/getAllReport")
    public HttpEntity<?> getAllReport() {
        ApiResponse apiResponse = botService.getAllReport();
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/byDate")
    public HttpEntity<?> getByDate(@RequestBody DateDto dateDto) throws ParseException {
        ApiResponse apiResponse = botService.byDate(dateDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/byDateSupervisor/{id}")
    public HttpEntity<?> getByDateSupervisor(@RequestBody DateDto dateDto, @PathVariable Long id) throws ParseException {
        ApiResponse apiResponse = botService.byDateSupervisor(id, dateDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/editTime")
    public HttpEntity<?> editTime(@RequestBody TimeDto timeDto) {
        ApiResponse apiResponse = botService.editTime(timeDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


}
