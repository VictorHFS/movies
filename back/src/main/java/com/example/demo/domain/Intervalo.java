package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Intervalo {
    private Producer producer;
    private int intervalo;
    private int previousWin;
    private int followingWin;
}
