package ca.sait.backup.model.request;


import lombok.Data;

import java.util.List;

@Data
public class CreateNewProjectRequest {
    private String name;
    private String description;
    private String bannerLocation;
    private List<String> invitationList;
}
