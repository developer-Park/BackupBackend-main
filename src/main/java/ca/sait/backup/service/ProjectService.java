package ca.sait.backup.service;

import ca.sait.backup.model.business.JWTSessionContainer;
import ca.sait.backup.model.entity.Project;
import ca.sait.backup.model.entity.User;
import ca.sait.backup.model.request.CreateNewProjectRequest;

import java.util.ArrayList;
import java.util.List;


public interface ProjectService {

    boolean createNewProject(Long userId, CreateNewProjectRequest projectRequest);

    Project getProjectUsingId(Long projectId);

    ArrayList<Project> getAllProjects(JWTSessionContainer sessionContainer);

    List<Project> dev_getAllProjects();

}
