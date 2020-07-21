package com.ongdigit.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Place implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String serviceType;
    private String computerNumber;
    private String dateReservation;
    private String timeReservation;
    private String userEmail;
    @Column(nullable = false, columnDefinition = "boolean default 1")
    private boolean access;
    @Column(nullable = false, columnDefinition = "boolean default 0")
    private boolean presence;
}
