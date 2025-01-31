package com.example.demo.model;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Utilisateur")
public class Utilisateur implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private boolean isActive;

    public Utilisateur() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.isActive = true; 
    }

    public Utilisateur(String name, String email, String password, UserRole role, Department department) {
        this.name = name;
        this.email = email;
        this.password = password; 
        this.role = role;
        this.department = department;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.isActive = true;
    }


    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { 
        this.name = name; 
        this.updatedAt = LocalDateTime.now(); 
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { 
        this.email = email; 
        this.updatedAt = LocalDateTime.now();
    }

    public String getPassword() { return password; }
    public void setPassword(String password) { 
        this.password = password; 
        this.updatedAt = LocalDateTime.now();
    }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { 
        this.role = role; 
        this.updatedAt = LocalDateTime.now();
    }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { 
        this.department = department; 
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { this.isActive = active; }

    
    public void activateUser() { 
        this.isActive = true;
        this.updatedAt = LocalDateTime.now();
    }

    public void deactivateUser() { 
        this.isActive = false;
        this.updatedAt = LocalDateTime.now();
    }
}
