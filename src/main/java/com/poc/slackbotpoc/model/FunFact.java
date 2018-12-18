package com.poc.slackbotpoc.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "fun_facts")
public class FunFact {
    @Id
    private String id;
    private String author;
    private String funFact;
    private Date updateDate = new Date();
}
