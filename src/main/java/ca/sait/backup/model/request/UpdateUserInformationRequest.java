package ca.sait.backup.model.request;

import lombok.Data;

@Data
public class UpdateUserInformationRequest {
    private String name;
    private String company;
    private String address;
    private String country;
    private String phone;
}
