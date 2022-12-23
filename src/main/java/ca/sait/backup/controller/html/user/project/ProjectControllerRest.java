package ca.sait.backup.controller.html.user.project;

import ca.sait.backup.model.business.JWTSessionContainer;
import ca.sait.backup.model.entity.Asset;
import ca.sait.backup.model.entity.AssetFolder;
import ca.sait.backup.model.entity.Category;
import ca.sait.backup.model.entity.Project;
import ca.sait.backup.model.request.*;
import ca.sait.backup.service.AssetService;
import ca.sait.backup.service.ProjectService;
import ca.sait.backup.service.SessionService;
import ca.sait.backup.utils.JsonData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/** Author: Ibrahim Element
 * Description: Rest APIs for user/agency project management
 * Usage: Asset Exploration
 */

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("api/v1/pri/user/project")
@RequiredArgsConstructor
public class ProjectControllerRest {

    @Autowired
    private final AssetService assetService;

    @Autowired
    private final ProjectService projectService;

    @Autowired
    private final SessionService sessionService;

    @PostMapping("/create")
    public JsonData createNewProject(@RequestBody CreateNewProjectRequest projReq, HttpServletRequest request) throws Exception {

        JWTSessionContainer sessionContainer = this.sessionService.extractSession(
            request
        );

        this.projectService.createNewProject(
            sessionContainer.getUserId(),
            projReq
        );

        return JsonData.buildSuccess("");
    }

    @GetMapping("/folders/{folderId}")
    public JsonData listFoldersInsideFolder(@PathVariable("folderId") Integer folderId) throws Exception {

        AssetFolder currentFolder = this.assetService.getAssetFolderFromId(
            (long) folderId
        );

        List<AssetFolder> childFolders = this.assetService.getAllFoldersInsideFolder(
            currentFolder
        );

        // Convert to json
        GsonBuilder gsonBuilder = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation();

        Gson gson = gsonBuilder.create();

        String jsnChildFolders = gson.toJson(childFolders);

        return JsonData.buildSuccess(jsnChildFolders);
    }

    @GetMapping("/files/{folderId}")
    public JsonData listFilesInsideFolder(@PathVariable("folderId") Long folderId) throws Exception {

        AssetFolder currentFolder = this.assetService.getAssetFolderFromId(
            folderId
        );

        List<Asset> childAssets = currentFolder.getChildAssets();

        GsonBuilder gsonBuilder = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation();

        Gson gson = gsonBuilder.create();
        String jsnChildAssets = gson.toJson(childAssets);

        return JsonData.buildSuccess(jsnChildAssets);
    }

    @PostMapping("/assets/{projectId}")
    public JsonData createNewAsset(@PathVariable("projectId") Integer projectId, @RequestBody AssetRequest assetRequest) throws Exception {

        Project project = this.projectService.getProjectUsingId((long)projectId);

        List<Category> categoryList = project.getCategories();

        Asset asset = new Asset();

        // If no category id is provided (meaning no category is selected).
        if (assetRequest.getCategoryId() == -1) {
            // Find the category id using the name and project id.
            for (Category category : categoryList) {
                if (assetRequest.getCategorySelection().equals(category.getName())) {
                    asset.setCategory(category);
                }
            }
        } else {
            // Otherwise, we are creating a new asset inside of a folder which is already associated to a category.
            asset.setCategory(
                this.assetService.getCategoryById((long)assetRequest.getCategoryId())
            );
        }

        // If folder id is provided (meaning this is a folder on the root).
        if (assetRequest.getFolderId() != -1) {
            asset.setParent(
                this.assetService.getAssetFolderFromId(
                    (long)assetRequest.getFolderId()
                )
            );
        }

        // Configure values
        asset.setAssetValue(assetRequest.getValue());
        asset.setAssetName(assetRequest.getName());
        asset.setAssetType(assetRequest.getType());

        // Save
        this.assetService.createAsset(asset);

        return JsonData.buildSuccess("");
    }

    @GetMapping("/assets/{assetId}")
    public JsonData getAssetContents(@PathVariable("assetId") Long assetId) throws Exception {
        Asset asset = this.assetService.getAssetById(assetId);
        return JsonData.buildSuccess(asset.getAssetValue());
    }

    @PostMapping("/folders/{projectId}")
    public JsonData createNewFolder(@PathVariable("projectId") Long projectId, @RequestBody FolderRequest folderRequest) throws Exception {

        Project project = this.projectService.getProjectUsingId(projectId);

        List<Category> categoryList = project.getCategories();

        AssetFolder assetFolder = new AssetFolder();

        // If no category id is provided (meaning no category is selected).
        if (folderRequest.getCategoryId() == -1) {
            // Find the category id using the name and project id.
            for (Category category : categoryList) {
                if (folderRequest.getCategorySelection().equals(category.getName())) {
                    assetFolder.setCategory(category);
                }
            }
        } else {
            // Otherwise, we are creating a new asset inside of a folder which is already associated to a category.
            assetFolder.setCategory(
                this.assetService.getCategoryById(
                    (long)folderRequest.getCategoryId()
                )
            );
        }

        // If folder id is provided (meaning this is a folder on the root).
        if (folderRequest.getFolderId() != -1) {

            assetFolder.setParent(
                this.assetService.getAssetFolderFromId(
                    (long)folderRequest.getFolderId()
                )
            );

        }

        // Create new asset folder
        assetFolder.setName(folderRequest.getFolderName());

        this.assetService.createFolder(assetFolder);

        return JsonData.buildSuccess("");
    }

    @PostMapping("/categories/{projectId}")
    public JsonData createNewCategory(@PathVariable("projectId") Integer projectId, @RequestBody CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setProject(
            this.projectService.getProjectUsingId((long)projectId)
        );
        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());

        this.assetService.createCategory(category);

        return JsonData.buildSuccess("");
    }

    @PostMapping("/assets/security/{projectId}/getSecurityConfig")
    public JsonData getSecConfig(@PathVariable("projectId") Integer projectId, @RequestBody LockAssetRequest lockAssetRequest) {

        String securityConfig = this.assetService.getSecurityConfig(
            lockAssetRequest
        );

        return JsonData.buildSuccess(securityConfig);
    }

    @PostMapping("/assets/security/{projectId}/createAssetRequest")
    public JsonData createAssetRequest(@PathVariable("projectId") Integer projectId, HttpServletRequest request, @RequestBody SecurityAssetRequest securityAssetRequest) {

        JWTSessionContainer sessionContainer = this.sessionService.extractSession(
            request
        );

        this.assetService.createSecurityAssetRequest(
            sessionContainer,
            securityAssetRequest
        );

        return JsonData.buildSuccess("");
    }

    @PostMapping("/assets/security/approve/{projectId}")
    public JsonData unlockAsset(@PathVariable("projectId") Long projectId, @RequestBody UnlockAssetRequest unlockAssetRequest) {
        this.assetService.dev_approveMember(
            projectId,
            unlockAssetRequest
        );
        return JsonData.buildSuccess("");
    }

    @PostMapping("/assets/security/tryunlock/{projectId}")
    public JsonData tryUnlockAsset(@PathVariable("projectId") Long projectId, HttpServletRequest request, @RequestBody TryUnlockDataLockerRequest unlockAssetRequest) {
        JWTSessionContainer jwtSessionContainer = this.sessionService.extractSession(
            request
        );

        boolean isSuccess = this.assetService.tryUnlockAsset(
            projectId,
            jwtSessionContainer,
            unlockAssetRequest
        );

        return JsonData.buildSuccess(isSuccess ? "true" : "false");
    }

    @PostMapping("/assets/security/lock/{projectId}")
    public JsonData lockAsset(@PathVariable("projectId") Long projectId, @RequestBody LockAssetRequest lockAssetRequest) {

        this.assetService.lockAsset(
            lockAssetRequest,
            projectId
        );

        return JsonData.buildSuccess("");
    }

}