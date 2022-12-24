package ca.sait.backup.controller.html.admin;


import ca.sait.backup.model.entity.Project;
import ca.sait.backup.model.entity.SupportTicket;
import ca.sait.backup.model.entity.User;
import ca.sait.backup.model.entity.UserRole;
import ca.sait.backup.service.ProjectService;
import ca.sait.backup.service.SessionService;
import ca.sait.backup.service.SupportTicketService;
import ca.sait.backup.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


//Writer : Park
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminDashboardController {


    private final UserService userService;
    private final SessionService sessionService;
    private final SupportTicketService supportTicketService;
    private final ProjectService projectService;

    //Writer : Park
    @GetMapping("/")
    public String GetDashboard(Model model, HttpServletRequest request) {
        // Check JWT and get sessionContainer and userNotifications
        sessionService.exposeEssentialVariables(request, model);
        // Get all project managerList
        List<Project> projectList = projectService.dev_getAllProjects();
        List<User> managerList = projectList.stream().map(Project::getUser).collect(Collectors.toList());
        // Get all users by role
        ArrayList<User> allUsers = new ArrayList<>();
        for (UserRole role : UserRole.values()) {
            List<User> users = userService.dev_GetUsersByRole(role);
            allUsers.addAll(users);
        }
        List<User> mediatorList = userService.dev_GetUsersByRole(UserRole.MEDIATOR);
        List<User> adminList = userService.dev_GetUsersByRole(UserRole.ADMIN);
        List<User> userList = userService.dev_GetUsersByRole(UserRole.USER);
        List<User> suspendedUser = allUsers.stream().filter(User::isDisabled).collect(Collectors.toList());

        // pass to view the list.
        model.addAttribute("admin_list", adminList);
        model.addAttribute("user_list", userList);
        model.addAttribute("suspended_list", suspendedUser);
        model.addAttribute("manager_list", managerList);
        model.addAttribute("mediator_list", mediatorList);
        model.addAttribute("allUsers_list", allUsers);

        return ("/admin/admin_dashboard");
    }
    //Writer : Park
    //Edit user
    @GetMapping("/edit/{userId}")
    public String EditUser(@PathVariable("userId") Long userId, Model model, HttpServletRequest request) {
        sessionService.exposeEssentialVariables(request, model);
        //get support ticket for edit support ticket section
        List<SupportTicket> ticketList = supportTicketService.getSupportTicketsForUserId(userId);
        model.addAttribute("user", userService.dev_GetUserById(userId));
        model.addAttribute("userTickets", ticketList);

        return ("/admin/edit_user");
    }

}
