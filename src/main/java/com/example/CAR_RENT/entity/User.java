package com.example.CAR_RENT.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "main_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;

    @Email(message = "Неккоректный email")
    @NotBlank(message = "Email не может быть пустым")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Имя пользователя не может быть пустым")
    @Size(max = 15, message = "Имя пользователя слишком длинное")
    @Size(min = 3, message = "Имя пользователя слишком короткое")
    private String username;

    @NotBlank(message = "Пароль не может быть пустым")
    private String password;
    private boolean active;
    private Integer currentbalance;

    private String activationCode;

    @Transient
    private String password2;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            foreignKey = @ForeignKey(
                    name = "user_fk")
    )
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    private Integer numberOfRents;


    public User() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public Integer getBalance() {
        return currentbalance;
    }

    public void setBalance(Integer balance) {
        this.currentbalance = balance;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getCurrentbalance() {
        return currentbalance;
    }

    public void setCurrentbalance(Integer currentbalance) {
        this.currentbalance = currentbalance;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    public Integer getNumberOfRents() {
        return numberOfRents;
    }

    public void setNumberOfRents(Integer numberOfRents) {
        this.numberOfRents = numberOfRents;
    }

    public Boolean isAdmin() {
        return roles.contains(Role.ADMIN);
    }
}
