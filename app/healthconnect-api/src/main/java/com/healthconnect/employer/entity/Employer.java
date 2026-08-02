package com.healthconnect.employer.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String employerCode;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String contactPerson;

    @Column(nullable = false, unique = true)
    private String contactEmail;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String status;
}
