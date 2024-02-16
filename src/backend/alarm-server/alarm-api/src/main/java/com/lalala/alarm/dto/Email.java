package com.lalala.alarm.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Email {

    private String subject;
    private String recipient;
    private String content;
}