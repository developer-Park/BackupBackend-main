package ca.sait.backup.controller.html.user.project;


import ca.sait.backup.component.user.CategoryAssociation;
import ca.sait.backup.model.business.JWTSessionContainer;
import ca.sait.backup.model.entity.*;
import ca.sait.backup.service.AssetService;

import ca.sait.backup.service.ProjectService;
import ca.sait.backup.service.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/user/project/{projectId}")
public class ProjectController {

    @Autowired
    private AssetService assetService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private SessionService sessionService;

    @GetMapping("/")
    public String GetProjectHome(@PathVariable("projectId") Long projectId, Model model, HttpServletRequest request) {

        // Expose session variables
        this.sessionService.exposeEssentialVariables(request, model);

        // Get project using provided id

        Project project = this.projectService.getProjectUsingId(projectId);

        // Using ORM, just grab the categories
        List<Category> categoryList = project.getCategories();

        // Initialize a CategoryAssociation UI Component
        ArrayList<CategoryAssociation> categoryAssociationList = new ArrayList<CategoryAssociation>();

        // For every category, loop and find linked assets/folders - while populating association list.
        for (Category cat: categoryList) {
            CategoryAssociation categoryAssociation = new CategoryAssociation();
            categoryAssociation.setCategory(cat);

            List<Asset> filteredAssets = cat.getAssets();
            List<AssetFolder> filteredFolder = cat.getAssetFolders();

            for (int i = 0; i < filteredAssets.size(); i++) {
                if (filteredAssets.get(i).getParent() != null) {
                    filteredAssets.remove(i);
                }
            }

            for (int i = 0; i < filteredFolder.size(); i++) {
                if (filteredFolder.get(i).getParent() != null) {
                    filteredFolder.remove(i);
                }
            }

            categoryAssociation.setAssetList(
                filteredAssets
            );

            categoryAssociation.setFolderList(
                filteredFolder
            );

            categoryAssociationList.add(
                categoryAssociation
            );

        }

        // Feed the rendering agent the data through the UI component.
        model.addAttribute("project", project);
        model.addAttribute("categoryAssociationList", categoryAssociationList);
        model.addAttribute("assetService", this.assetService);

        return "/user/asset_explorer.html";
    }

    @GetMapping("/requests")
    public String ProjectAssetRequests(@PathVariable("projectId") Long projectId, Model model, HttpServletRequest request) {

        // Expose session variables
        this.sessionService.exposeEssentialVariables(request, model);

        JWTSessionContainer sessionContainer = this.sessionService.extractSession(
            request
        );

        List<AssetSecurityRequest> requests = this.assetService.getAssetRequests(
            sessionContainer,
            projectId
        );

        ArrayList<AssetSecurityRequest> openRequests = new ArrayList<AssetSecurityRequest>();
        ArrayList<AssetSecurityRequest> closedRequests = new ArrayList<AssetSecurityRequest>();

        for (AssetSecurityRequest req: requests) {
            if (req.getStatus().equals(AssetRequestStatusEnum.UNSEEN)) {
                openRequests.add(req);
            }else {
                closedRequests.add(req);
            }
        }

        model.addAttribute("sessionContainer", sessionContainer);
        model.addAttribute("assetService", this.assetService);
        model.addAttribute("openRequests", openRequests);
        model.addAttribute("closedRequests", closedRequests);

        return "/user/project_request.html";
    }



}
