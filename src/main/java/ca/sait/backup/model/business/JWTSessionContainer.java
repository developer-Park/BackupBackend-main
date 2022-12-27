package ca.sait.backup.model.business;

import ca.sait.backup.mapper.ProjectMemberRepository;
import ca.sait.backup.model.entity.Project;
import ca.sait.backup.model.entity.ProjectMember;
import ca.sait.backup.model.entity.User;
import ca.sait.backup.model.entity.UserRole;
import ca.sait.backup.service.ProjectService;
import ca.sait.backup.service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationImportEvent;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Data
public class JWTSessionContainer {

    // User id
    @Expose
    private Long userId;

    // Personal user info
    @Expose
    private String name;
    @Expose
    private String email;
    @Expose
    private String phoneNumber;

    // User type
    @Expose
    private UserRole userRole;

    // Methods
    public static JWTSessionContainer Process(String json) {

        // Convert to json
        Gson gson = new Gson();
        JWTSessionContainer session = gson.fromJson(json, JWTSessionContainer.class);
        return session;

    }

}
