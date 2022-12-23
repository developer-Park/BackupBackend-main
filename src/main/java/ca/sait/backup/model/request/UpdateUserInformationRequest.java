package ca.sait.backup.model.request;

import lombok.Data;
import lombok.NonNull;

@Data
public class UpdateUserInformationRequest {
    @NonNull
    private String name;
    @NonNull
    private String company;
    @NonNull
    private String address;
    @NonNull
    private String country;
    @NonNull
    private String phone;
}
