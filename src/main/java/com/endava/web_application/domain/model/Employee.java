package com.endava.web_application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Table(name="employees")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @Column(name = "employee_id")
    @GeneratedValue(generator = "employees_seq")
    private Integer id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(name="email")
    private String email;

    @Column(name="phone_number")
    @NotBlank(message = "The phone number cannot be empty")
    @Pattern(regexp = "\\d{3}.\\d{3}.\\d{4}", message = "For the phone number please use pattern XXX-XXX-XXXX")
    private String phoneNumber;

    @Column(name="salary")
    private double salary;
}
