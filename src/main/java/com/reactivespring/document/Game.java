package com.reactivespring.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Game {

    @Id
    private String id;
    private List<Team> teams;
    //@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime date;
}
