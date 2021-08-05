package com.jm.restclient.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User{

    private Long id;
    private String name;
    private String lastName;
    private Byte age;
}
