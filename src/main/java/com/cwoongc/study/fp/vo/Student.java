package com.cwoongc.study.fp.vo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Student {

    private String name;
    private int gradYear;
    private Major major;

}
