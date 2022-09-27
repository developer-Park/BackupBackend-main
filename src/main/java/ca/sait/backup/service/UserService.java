package ca.sait.backup.service;

import ca.sait.backup.model.business.JWTSessionContainer;
import ca.sait.backup.model.entity.User;
import ca.sait.backup.model.entity.UserRole;
import ca.sait.backup.model.request.ChangePasswordRequest;
import ca.sait.backup.model.request.UpdateUserInformationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService{

    boolean validateUser(String email, String password);

    boolean processRegister(String email, String password, String name, String phone, String company, String address, String country);

    public User dev_GetUserByEmail(String email);

    public boolean dev_UpdateUser(JWTSessionContainer container, UpdateUserInformationRequest req);

    public boolean dev_ChangePassword(JWTSessionContainer container, ChangePasswordRequest req);

    public boolean dev_ChangeAccountStatus(Long userId, boolean status);

    public List<User> dev_GetUsersByRole(UserRole role);

    User dev_GetUserById(Long id);

}
