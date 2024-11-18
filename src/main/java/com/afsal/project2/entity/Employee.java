package com.afsal.project2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Employee")
public class Employee {

    @Id
    @Column(nullable = false)
    private int empId;

    @Column(name="name", nullable = false)
    private String name;
}
