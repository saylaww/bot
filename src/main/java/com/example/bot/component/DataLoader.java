package com.example.bot.component;

import com.example.bot.entity.Rule;
import com.example.bot.repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Value("${spring.datasource.initialization-mode}")
    private String initialMode;
    @Autowired
    RuleRepository ruleRepository;

    @Override
    public void run(String... args) throws Exception {
        if (initialMode.equals("always")) {
            ruleRepository.save(new Rule("0","23","1","59"));
        }
    }

}
