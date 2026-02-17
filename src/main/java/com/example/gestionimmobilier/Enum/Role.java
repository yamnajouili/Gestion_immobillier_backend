package com.example.gestionimmobilier.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.gestionimmobilier.Enum.Permission.*;

@AllArgsConstructor
public enum Role {
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE

            )
    ),
    EMPLOYE(
            Set.of(

                    EMPLOYE_READ,
                    EMPLOYE_UPDATE,
                    EMPLOYE_DELETE,
                    EMPLOYE_CREATE
            )
    ),
    CLIENT(
            Set.of(

                    CLIENT_READ,
                    CLIENT_UPDATE,
                    CLIENT_DELETE,
                    CLIENT_CREATE
            )
    ),
    PROPRIETAIRE(
            Set.of(

                    PROPRIETAIRE_READ,
                    PROPRIETAIRE_UPDATE,
                    PROPRIETAIRE_DELETE,
                    PROPRIETAIRE_CREATE
            )
    ),
    TECHNICIEN(
            Set.of(

                    TECHNICIEN_READ,
                    TECHNICIEN_UPDATE,
                    TECHNICIEN_DELETE,
                    TECHNICIEN_CREATE
            )
    )
    ;
    @Getter
    private Set<Permission> permissions;



    public List<SimpleGrantedAuthority> getAuthorities() {

        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }

}
