package ca.sait.backup.model.request;


import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String name;
    private String password;
    private String phone;
    private String company;
    private String address;
    private String country;
}
