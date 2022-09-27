package ca.sait.backup.service.impl;

import ca.sait.backup.mapper.ProjectMemberRepository;
import ca.sait.backup.mapper.ProjectRepository;
import ca.sait.backup.model.business.JWTSessionContainer;
import ca.sait.backup.model.entity.*;
import ca.sait.backup.model.request.CreateNewProjectRequest;
import ca.sait.backup.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectMemberRepository projectMemberRepository;

    @Autowired
    private AssetService assetService;

    @Autowired
    private NotificationService notificationService;

    @Override
    public ArrayList<Project> getAllProjects(JWTSessionContainer sessionContainer) {

        List<ProjectMember> membershipList = this.projectMemberRepository.findByUser(
            new User(sessionContainer.getUserId())
        );

        ArrayList<Project> projects = new ArrayList<Project>();
        for (ProjectMember membership: membershipList) {
            projects.add(
                membership.getProject()
            );
        }

        return projects;
    }

    @Override
    public List<Project> dev_getAllProjects() {
        List<Project> projectList = this.projectRepository.findAll();
        return projectList;
    }



    @Override
    public Project getProjectUsingId(Long id) {
        return this.projectRepository.getById(id);
    }

    @Override
    public boolean createNewProject(Long userId, CreateNewProjectRequest projectRequest){

        // Create the project using basic information
        Project project = new Project();
        project.setUser(new User(userId));
        project.setDescription(projectRequest.getDescription());
        project.setBannerLocation(projectRequest.getBannerLocation());
        project.setName(projectRequest.getName());

        if (projectRequest.getBannerLocation().length() == 0) {
            project.setBannerLocation("/images/project.svg");
        }

        this.projectRepository.save(
            project
        );


        // Create a default category
        Category defaultCategory = new Category();
        defaultCategory.setName("Onboarding");
        defaultCategory.setDescription("Default category for project initialization");
        defaultCategory.setProject(project);

        this.assetService.createCategory(
            defaultCategory
        );

        // Add the project creator to the invitation list as well.
        User ownerUser = this.userService.dev_GetUserById(
            userId
        );
        projectRequest.getInvitationList().add(
            ownerUser.getEmail()
        );

        // Add each member after verifying membership
        for (String memberInvitation: projectRequest.getInvitationList()) {

            User user = this.userService.dev_GetUserByEmail(
                memberInvitation
            );
            if (user == null) continue;

            ProjectMember projectMember = new ProjectMember();
            projectMember.setProject(project);
            projectMember.setUser(user);

            if (memberInvitation.equals(ownerUser.getEmail())) {
                projectMember.setProjectRole(
                    ProjectRoleEnum.OWNER
                );
            }else {
                projectMember.setProjectRole(
                    ProjectRoleEnum.CLIENT
                );
            }

            // Save
            this.projectMemberRepository.save(
                projectMember
            );

            // Send user a notification
            this.notificationService.backend_createNotification(
                user,
                UserNotificationEnum.PROJECT_INVITATION,
                "You have been added to " + project.getName() + " project!"
            );

        }

        return false;
    }

}
