package com.oleh.chui.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {

    private int id;

    private String login;

    private char[] password;

    private String email;

    private Role role;

    private Boolean blocked;

    public enum Role {
        ADMIN,
        USER,
        UNKNOWN;

        public String getValue() {
            return this.name();
        }
    }

}
