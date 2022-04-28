package com.endava.web.application.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "departments")
@Data
@NoArgsConstructor
public class Department {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "departments_seq")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;
}
