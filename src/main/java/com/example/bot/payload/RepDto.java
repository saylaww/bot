package com.example.bot.payload;

import com.example.bot.entity.Supervisor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepDto {

    private Supervisor supervisor;

    private int count;

}
