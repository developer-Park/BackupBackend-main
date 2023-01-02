package ca.sait.backup.model.entity;


import ca.sait.backup.model.request.RegisterRequest;
import ca.sait.backup.model.request.UpdateUserInformationRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Table(name = "user")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {

    public User(Long id) {
        this.setId(id);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Project> projects;
    private String name;
    private String password;
    private String email;
    private String phone;
    private String address;
    private String company;
    private String country;

    private boolean disabled;


    @Enumerated(EnumType.STRING)
    private UserRole role;

    @CreationTimestamp
    private Date creationDate;


    public User(RegisterRequest registerRequest, String password, UserRole userRole) {
        this.name = registerRequest.getName();
        this.password = password;
        this.email = registerRequest.getEmail();
        this.phone = registerRequest.getPhone();
        this.address = registerRequest.getAddress();
        this.company = registerRequest.getCompany();
        this.country = registerRequest.getCountry();
        this.disabled = false;
        this.role = userRole;
        this.creationDate = creationDate;
    }

    public void Update(UpdateUserInformationRequest updateUserInformationRequest) {
        this.name = updateUserInformationRequest.getName();
        this.phone = updateUserInformationRequest.getPhone();
        this.address = updateUserInformationRequest.getAddress();
        this.company = updateUserInformationRequest.getCompany();
        this.country = updateUserInformationRequest.getCountry();
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void deleteUser(boolean disabled) {
        this.disabled = disabled;
    }
}
