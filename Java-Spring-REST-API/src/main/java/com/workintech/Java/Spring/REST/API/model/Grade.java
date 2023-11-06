package com.workintech.Java.Spring.REST.API.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Grade {

    private int coefficient;
    private String note;
}
