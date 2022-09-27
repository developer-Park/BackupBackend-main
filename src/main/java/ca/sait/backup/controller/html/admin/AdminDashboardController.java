package ca.sait.backup.controller.html.admin;


import ca.sait.backup.model.business.JWTSessionContainer;
import ca.sait.backup.model.entity.Project;
import ca.sait.backup.model.entity.SupportTicket;
import ca.sait.backup.model.entity.User;
import ca.sait.backup.model.entity.UserRole;
import ca.sait.backup.service.ProjectService;
import ca.sait.backup.service.SessionService;
import ca.sait.backup.service.SupportTicketService;
import ca.sait.backup.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private SupportTicketService supportTicketService;

    @Autowired
    private ProjectService projectService;

    @GetMapping("/")
    public String GetDashboard(Model model, HttpServletRequest request) {

        this.sessionService.exposeEssentialVariables(
            request, model
        );


        // Get all project managers
        List<Project> projectList = this.projectService.dev_getAllProjects();
        List<User> projectOwnerList = projectList.stream().map( (Project project) -> {
            return project.getUser();
        }).collect(Collectors.toList());

        // Get all users by type
        ArrayList<User> allUsers = new ArrayList<User>();

        List<User> mediatorList = this.userService.dev_GetUsersByRole(
            UserRole.MEDIATOR
        );
        List<User> adminList = this.userService.dev_GetUsersByRole(
            UserRole.ADMIN
        );
        List<User> userList = this.userService.dev_GetUsersByRole(
            UserRole.USER
        );

        for (UserRole role: UserRole.values()) {
            List<User> users = this.userService.dev_GetUsersByRole(
                role
            );
            String attrName = role.toString().toLowerCase() + "_list";
            allUsers.addAll(users);
            model.addAttribute(attrName, users);
        }

        List<User> suspendedUser = allUsers.stream().filter( (User user) -> {
           if (user.isDisabled()) {
               return true;
           }else {
               return false;
           }
        }).collect(Collectors.toList());

        model.addAttribute("admin_list", adminList);
        model.addAttribute("user_list", userList);
        model.addAttribute("suspended_list", userList);
        model.addAttribute("manager_list", projectOwnerList);
        model.addAttribute("mediator_list", mediatorList);
        model.addAttribute("allUsers_list", allUsers);

        return ("/admin/admin_dashboard");
    }

    @GetMapping("/edit/{userId}")
    public String EditUser(@PathVariable("userId") Long userId, Model model, HttpServletRequest request) {

        List<SupportTicket> ticketList = this.supportTicketService.getSupportTicketsForUser(
            new User(userId)
        );

        model.addAttribute("userTickets", ticketList);

        return ("/admin/edit_user");
    }

    @GetMapping("/fragment")
    public String fragment() {
        return ("/fragment/top-user-bar");
    }


}
