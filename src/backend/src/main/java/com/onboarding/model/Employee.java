package com.onboarding.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String fullName;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    private String jobTitle;

    private String department;

    // Optional link to a login account, since not every employee record needs system access
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Employee() {}

    public Employee(String fullName, String email, String jobTitle, String department) {
        this.fullName = fullName;
        this.email = email;
        this.jobTitle = jobTitle;
        this.department = department;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
