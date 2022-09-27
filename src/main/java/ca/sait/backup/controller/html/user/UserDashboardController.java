package ca.sait.backup.controller.html.user;


import ca.sait.backup.mapper.ProjectMemberRepository;
import ca.sait.backup.model.business.JWTSessionContainer;
import ca.sait.backup.model.business.RowContainer;
import ca.sait.backup.model.entity.*;
import ca.sait.backup.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/user")
class UserDashboardController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private SupportTicketService supportTicketService;

    @Autowired
    private UserService userService;

    @Autowired
    private AssetService assetService;

    @Autowired
    private ProjectMemberRepository projectMemberRepository;

    @GetMapping("/dashboard")
    public String GetDashboard(Model model, HttpServletRequest request) {

        // Expose session variables
        this.sessionService.exposeEssentialVariables(request, model);

        JWTSessionContainer sessionContainer = this.sessionService.extractSession(
                request
        );

        // Get list of projects from Project Service.
        List<Project> projectList = this.projectService.getAllProjects(
            sessionContainer
        );

        // Sort by most categories
        projectList.sort((Project a, Project b) -> {
            return b.getCategories().size() - a.getCategories().size();
        });

        if (projectList.size() >= 3) {
            projectList = projectList.subList(0, 3);
        }

        // Process project list into a grid format.
        RowContainer<Project> gridContainer = new RowContainer<>(projectList, 3);

        model.addAttribute("projectGrid", gridContainer.getGrid());

        Integer numProjects = 0;
        Integer numAssets = 0;
        Integer numSupportTickets = 0;

        User user = this.userService.dev_GetUserById(
            sessionContainer.getUserId()
        );


        List<ProjectMember> membershipList = this.projectMemberRepository.findByUser(
                user
        );

        for (Project project: user.getProjects()) {
            for (Category cat: project.getCategories()) {
                numAssets += cat.getAssets().size() + cat.getAssetFolders().size();
            }
        }

        for (ProjectMember membership: membershipList) {
            for (Category cat: membership.getProject().getCategories()) {
                numAssets += cat.getAssets().size() + cat.getAssetFolders().size();
            }
        }

        numSupportTickets = this.supportTicketService.getSupportTicketsForUser(
            user
        ).size();

        numProjects = user.getProjects().size() + membershipList.size();

        model.addAttribute("numAssets", numAssets);
        model.addAttribute("numProjects", numProjects);
        model.addAttribute("numTickets", numSupportTickets);
        model.addAttribute("numManagers", numProjects);

        return ("/user/dashboard");

    }

}