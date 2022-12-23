package ca.sait.backup.service;

import ca.sait.backup.model.business.JWTSessionContainer;
import ca.sait.backup.model.entity.User;
import ca.sait.backup.model.entity.UserRole;
import ca.sait.backup.model.request.ChangePasswordRequest;
import ca.sait.backup.model.request.UpdateUserInformationRequest;

import java.util.List;


public interface UserService {

    boolean validateUser(String email, String password);

    boolean processRegister(String email, String password, String name, String phone, String company, String address, String country);

    User dev_GetUserByEmail(String email);

    boolean dev_UpdateUser(JWTSessionContainer container, UpdateUserInformationRequest req);

    boolean dev_ChangePassword(JWTSessionContainer container, ChangePasswordRequest req);

    boolean dev_ChangeAccountStatus(Long userId, boolean status);

    List<User> dev_GetUsersByRole(UserRole role);

    User dev_GetUserById(Long id);

}
