package com.afsal.project2.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Data
@Entity
@Table(name="Product", uniqueConstraints = {@UniqueConstraint(columnNames = {"client_id", "skuCode"})})
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "client_id", nullable = false)
    private int clientId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "emp_id")
    private Employee employee;

    @Column(name="skuCode", nullable = false)
    private String skuCode;

    @Column(name = "name", nullable = false)
    private String name;

    @UpdateTimestamp
    @Column(name = "last_modified_date" , nullable = false)
    private Timestamp last_modified_date;

    @Column(name = "enable", nullable = false)
    private Boolean enable;

}
