package com.healthconnect.benefit.entity;

import com.healthconnect.enrollment.entity.Enrollment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "benefits")
public class Benefit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String benefitCode;

    @Column(nullable = false)
    private String benefitName;

    private String description;

    @Column(nullable = false)
    private BigDecimal coverageAmount;

    @Column(nullable = false)
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_id", nullable = false)
    private Enrollment enrollment;
}