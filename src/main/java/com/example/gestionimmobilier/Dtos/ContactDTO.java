package com.example.gestionimmobilier.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactDTO {

    private Long id;

    private String name;

    private String image;

    private String status;

    private String lastMessage;

    private Date lastMessageTime;
}