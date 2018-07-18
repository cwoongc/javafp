package com.cwoongc.study.fp.vo;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"name"})
public class Major {
    private String name;
    private String desc;
}
