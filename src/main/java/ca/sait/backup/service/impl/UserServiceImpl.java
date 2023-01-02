package ca.sait.backup.service.impl;

import ca.sait.backup.exception.XDException;
import ca.sait.backup.mapper.UserRepository;
import ca.sait.backup.model.business.JWTSessionContainer;
import ca.sait.backup.model.entity.User;
import ca.sait.backup.model.entity.UserRole;
import ca.sait.backup.model.request.*;
import ca.sait.backup.model.response.SuspendResponse;
import ca.sait.backup.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder bCryptPasswordEncoder;

    @Override
    public boolean loginUser(LoginRequest loginRequest) {
        // Search for user that has the specified email
        User user = uRepository.findByEmail(loginRequest.getEmail());
        System.out.println(user);

        if (user == null) {
            throw new XDException(404, "user not exist");
        }
        // Hash what was provided to us
        String inputpassword = loginRequest.getPassword();
        // Compare both hashes
        if (bCryptPasswordEncoder.matches(inputpassword,user.getPassword())) {
            // Check if user is disabled
            return !user.isDisabled();
            // Valid
        }else {
            System.out.println("로그인실패");
            return false;
        }
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
    @Override
    public boolean processRegister(RegisterRequest registerRequest) {
        // Check if email is already used
        if (uRepository.findByEmail(registerRequest.getEmail()) != null) {
            throw new XDException(404, "user exist");
        }
        String password = bCryptPasswordEncoder.encode(registerRequest.getPassword());
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
        String unhashedProvided = req.getCurrentPassword();
        String userPasswordHashed = user.get().getPassword();
        boolean correctPassword = bCryptPasswordEncoder.matches(unhashedProvided,userPasswordHashed);
        if (!correctPassword) {
            return false;
        }
        String newPassword = bCryptPasswordEncoder.encode(req.getNewPassword());
        // If it's correct, update the user's password with the new one
        user.get().updatePassword(newPassword);
        return true;
    }

    //Writer : Park
    @Transactional
    @Override
    public void deleteUser(Long userId, boolean status) {
        User user = dev_GetUserById(userId);
        uRepository.delete(user);
    }

    @Transactional
    @Override
    public SuspendResponse suspendUser(Long userId) {
        User user = dev_GetUserById(userId);
        if (!user.isDisabled()) {
            user.deleteUser(true);
            return new SuspendResponse(true);
        } else {
            user.deleteUser(false);
            return new SuspendResponse(false);
        }
    }


    //Writer : Park
    @Override
    @Transactional
    public void dev_UpdateUser(JWTSessionContainer container, UpdateUserInformationRequest req) {
        User user = uRepository.findByEmail(container.getEmail());
        if (user.getEmail() == null) {
            return;
        }
        user.Update(req);
    }

    @Override
    public List<User> dev_GetUsersByRole(UserRole role) {
        return uRepository.findByRole(role);
    }

    @Override
    public User dev_GetUserByEmail(String email) {
        return uRepository.findByEmail(email);
    }

}
