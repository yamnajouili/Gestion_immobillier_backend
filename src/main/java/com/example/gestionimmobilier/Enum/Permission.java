package com.example.gestionimmobilier.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    EMPLOYE_READ("employe:read"),
    EMPLOYE_UPDATE("employe:update"),
    EMPLOYE_CREATE("employe:create"),
    EMPLOYE_DELETE("employe:delete"),
    CLIENT_READ("client:read"),
    CLIENT_UPDATE("client:update"),
    CLIENT_CREATE("client:create"),
    CLIENT_DELETE("client:delete"),
    PROPRIETAIRE_READ("proprietaire:read"),
    PROPRIETAIRE_UPDATE("proprietaire:update"),
    PROPRIETAIRE_CREATE("proprietaire:create"),
    PROPRIETAIRE_DELETE("proprietaire:delete"),
    TECHNICIEN_READ("technicien:read"),
    TECHNICIEN_UPDATE("technicien:update"),
    TECHNICIEN_CREATE("technicien:create"),
    TECHNICIEN_DELETE("technicien:delete")

    ;

    @Getter
    private final String permission;
}
