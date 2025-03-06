package com.personalapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "data")
@Builder
public class DataPersonal {

    @Id
    @Column(name = "nik", nullable = false, unique = true)
    private Long nik;

    @NotBlank(message = "nama tidak boleh kosong")
    @Column(nullable = false)
    private String fullname;

    private String gender;

    @Temporal(TemporalType.DATE)
    private Date birth;

    private String address;

    private String country;
}
