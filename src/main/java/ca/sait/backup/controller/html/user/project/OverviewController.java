package ca.sait.backup.controller.html.user.project;

import ca.sait.backup.model.business.JWTSessionContainer;
import ca.sait.backup.model.business.RowContainer;
import ca.sait.backup.model.entity.Project;
import ca.sait.backup.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ca.sait.backup.service.ProjectService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/user/project")
@RequiredArgsConstructor
public class OverviewController {

    @Autowired
    private final ProjectService projectService;

    @Autowired
    private final SessionService sessionService;


    @GetMapping("/overview")
    public String overview(Model model, HttpServletRequest request) {

        // Expose session variables
        this.sessionService.exposeEssentialVariables(request, model);

        JWTSessionContainer sessionContainer = this.sessionService.extractSession(
            request
        );

        // Get list of projects from Project Service.
        List<Project> projectList = this.projectService.getAllProjects(
            sessionContainer
        );

        // Process project list into a grid format.
        RowContainer<Project> gridContainer = new RowContainer<>(projectList, 3);

        // Add attribute to rendering system (Thyme).
        model.addAttribute("projectGrid", gridContainer.getGrid());

        // Trigger template processing and return.
        return "user/project_overview";

    }

}
