package com.lihao.springredditclone.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@NoArgsConstructor
public class NotificationEmail {
    private String subject;
    private String recipient;
    private String body;

    public NotificationEmail(String subject, String recipient, String body) {
        System.out.println("ctor called");
        this.subject = subject;
        this.recipient = recipient;
        this.body = body;
    }
}
