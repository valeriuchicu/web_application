package com.endava.web_application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "departments")
@Data
@Builder
@AllArgsConstructor
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
