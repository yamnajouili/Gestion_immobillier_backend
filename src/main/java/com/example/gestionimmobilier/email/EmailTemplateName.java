package com.example.gestionimmobilier.email;

import lombok.Getter;

@Getter
public enum EmailTemplateName {

    CONTRAT_SIGNATURE("contrat_signature"),
    CONTRAT_SIGNE("contrat_signe");

    private final String name;

    EmailTemplateName(String name) {
        this.name = name;
    }
}