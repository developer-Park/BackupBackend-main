package ca.sait.backup.service.impl;

import ca.sait.backup.exception.XDException;
import ca.sait.backup.mapper.UserRepository;
import ca.sait.backup.model.business.JWTSessionContainer;
import ca.sait.backup.model.entity.User;
import ca.sait.backup.model.entity.UserRole;
import ca.sait.backup.model.request.ChangePasswordRequest;
import ca.sait.backup.model.request.UpdateUserInformationRequest;
import ca.sait.backup.service.UserService;
import ca.sait.backup.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository uRepository;

    public boolean validateUser(String email, String password) {
        // Search for user that has the specified email
        User user = this.uRepository.findByEmail(email);
        if (user == null) {
            throw new XDException(404,"user not exist");
        }
        // Hash what was provided to us
        String hashProvidedPassword = CommonUtils.SHA256(password);
        // Compare both hashes
        if (user.getPassword().equals(hashProvidedPassword)) {
            // Check if user is disabled
            if (user.isDisabled()) {
                return false;
            }
            // Valid
            return true;
        }
        return false;
    }
    //Writer : Park
    @Override
    public User dev_GetUserById(Long id) {
        //get user by id
        Optional<User> user = uRepository.findById(id);
        //check user is present or not
        if (user.isPresent()) {
            return user.get();
        }
        return null;
    }

    @Override
    public boolean processRegister(String email, String password, String name, String phone, String company, String address, String country) {

        // Check if email is already used
        if (this.uRepository.findByEmail(email) != null) {
            return false;
        }

        // Hash password
        String hashedPassword = CommonUtils.SHA256(password);

        // Create a new user and save
        User user = new User();
        user.setEmail(email);
        user.setPassword(hashedPassword);
        user.setName(name);
        user.setPhone(phone);
        user.setCreationDate(new Date());
        user.setCompany(company);
        user.setAddress(address);
        user.setCountry(country);

        user.setRole(
            UserRole.USER
        );

        this.uRepository.save(user);

        return true;
    }

    @Override
    public boolean dev_ChangePassword(JWTSessionContainer container, ChangePasswordRequest req) {

        User user = this.dev_GetUserById(
            container.getUserId()
        );

        // Hash user's password and compare with database

        String hashedProvided = CommonUtils.SHA256(req.getCurrentPassword());
        String userPasswordHashed = user.getPassword();

        boolean correctPassword = userPasswordHashed.equals(hashedProvided);
        if (!correctPassword) return false;

        // If it's correct, update the user's password with the new one
        user.setPassword(
            CommonUtils.SHA256(
                req.getNewPassword()
            )
        );

        // Save user
        this.uRepository.save(
            user
        );

        return true;
    }

    @Override
    public boolean dev_ChangeAccountStatus(Long userId, boolean status) {
        User user = this.dev_GetUserById(
            userId
        );
        user.setDisabled(status);
        this.uRepository.save(
            user
        );
        return true;
    }

    @Override
    public boolean dev_UpdateUser(JWTSessionContainer container, UpdateUserInformationRequest req) {

        User user = this.dev_GetUserById(
            container.getUserId()
        );

        if(req.getName().length() > 0) {
            user.setName(
                req.getName()
            );
        }
        if(req.getCompany().length() > 0) {
            user.setCompany(
                req.getCompany()
            );
        }
        if(req.getPhone().length() > 0) {
            user.setPhone(
                req.getPhone()
            );
        }
        if(req.getAddress().length() > 0) {
            user.setAddress(
                req.getAddress()
            );
        }
        if(req.getCountry().length() > 0) {
            user.setCountry(
                req.getCountry()
            );
        }

        this.uRepository.save(
            user
        );

        return true;

    }

    public List<User> dev_GetUsersByRole(UserRole role) {
        return this.uRepository.findByRole(role);
    }

    @Override
    public User dev_GetUserByEmail(String email) {
        return this.uRepository.findByEmail(email);
    }

}
