package edu.miu.PropertyManagement.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.miu.PropertyManagement.enums.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "users")
public class User implements UserDetails, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonInclude(JsonInclude.Include.NON_NULL)

    private List<Role> role;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Offer> offers;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    List<FavoriteList> favoriteLists;

    @OneToMany(mappedBy = "users_id",cascade = CascadeType.ALL)
    List<Property> properties;
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private List<Message> sentMessages;

    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL)
    private List<Message> receivedMessages;

    private String isactive = "inactive";

    public User(Roles roles) {
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.stream().map(r -> new SimpleGrantedAuthority(r.getRole().name())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
        return true;
    }
}
