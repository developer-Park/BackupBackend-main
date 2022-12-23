package ca.sait.backup.service.impl;

import ca.sait.backup.exception.XDException;
import ca.sait.backup.mapper.UserRepository;
import ca.sait.backup.model.business.JWTSessionContainer;
import ca.sait.backup.model.entity.User;
import ca.sait.backup.model.entity.UserRole;
import ca.sait.backup.model.request.ChangePasswordRequest;
import ca.sait.backup.model.request.LoginRequest;
import ca.sait.backup.model.request.RegisterRequest;
import ca.sait.backup.model.request.UpdateUserInformationRequest;
import ca.sait.backup.model.response.RegisterResponse;
import ca.sait.backup.service.UserService;
import ca.sait.backup.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;


// Writer : Park,Ibrahim,John
@Service
@Validated
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository uRepository;

    public boolean loginUser(LoginRequest loginRequest) {
        // Search for user that has the specified email
        User user = uRepository.findByEmail(loginRequest.getEmail());
        if (user == null) {
            throw new XDException(404, "user not exist");
        }
        // Hash what was provided to us
        String hashProvidedPassword = CommonUtils.SHA256(loginRequest.getPassword());
        // Compare both hashes
        if (user.getPassword().equals(hashProvidedPassword)) {
            // Check if user is disabled
            return !user.isDisabled();
            // Valid
        }
        return false;
    }

    //Writer : Park
    @Override
    public User dev_GetUserById(Long id) {
        //get user by id
        Optional<User> user = uRepository.findById(id);
        //check user is present or not
        return user.orElse(null);
    }

    //writer : Park
    @Transactional
    public boolean processRegister(RegisterRequest registerRequest) {
        // Check if email is already used
        if (uRepository.findByEmail(registerRequest.getEmail()) != null) {
            throw new XDException(404, "user exist");
        }
        String password = CommonUtils.SHA256(registerRequest.getPassword());
        UserRole role = UserRole.USER;
        User user = new User(registerRequest, password, role);
        uRepository.save(user);
        return true;
    }
    //Writer:Park,Ibrahim
    @Transactional
    @Override
    public boolean dev_ChangePassword(JWTSessionContainer container, ChangePasswordRequest req) {
        Optional<User> user = uRepository.findById(container.getUserId());
        if (!user.isPresent()) {
            return false;
        }
        // Hash user's password and compare with database
        String hashedProvided = CommonUtils.SHA256(req.getCurrentPassword());
        String userPasswordHashed = user.get().getPassword();
        boolean correctPassword = userPasswordHashed.equals(hashedProvided);
        if (!correctPassword) {
            return false;
        }
        String newPassword = CommonUtils.SHA256(req.getNewPassword());
        // If it's correct, update the user's password with the new one
        user.get().updatePassword(newPassword);
        return true;
    }

    @Transactional
    @Override
    public boolean dev_ChangeAccountStatus(Long userId, boolean status) {
        User user = dev_GetUserById(userId);
        user.deleteUser(status);
        return true;
    }

    //Writer : Park
    @Override
    @Transactional
    public boolean dev_UpdateUser(JWTSessionContainer container, UpdateUserInformationRequest req) {
        User user = uRepository.findByEmail(container.getEmail());
        if (user.getEmail() == null) {
            return false;
        }
        user.Update(req);
        return true;
    }

    public List<User> dev_GetUsersByRole(UserRole role) {
        return uRepository.findByRole(role);
    }

    @Override
    public User dev_GetUserByEmail(String email) {
        return uRepository.findByEmail(email);
    }

}
