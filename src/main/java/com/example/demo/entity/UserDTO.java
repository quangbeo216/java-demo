package com.example.demo.entity;

import lombok.*;


@Setter
@Getter
@Data
@AllArgsConstructor
@Builder
public class UserDTO {

    private Integer id;
    private String email;
    private String firstname;
    private String lastname;
    private String enabled;
    private String role;

}
