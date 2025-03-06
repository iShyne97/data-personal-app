package com.personalapp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataPersonalDTO {
    private Long nik;
    private String fullname;
    private String gender;
    private Date birth;
    private String address;
    private String country;
    private Integer age;
}
