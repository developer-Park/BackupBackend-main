package ca.sait.backup.service;


import ca.sait.backup.model.business.JWTSessionContainer;
import ca.sait.backup.model.entity.*;
import ca.sait.backup.model.request.LockAssetRequest;
import ca.sait.backup.model.request.SecurityAssetRequest;
import ca.sait.backup.model.request.TryUnlockDataLockerRequest;
import ca.sait.backup.model.request.UnlockAssetRequest;

import java.util.List;

/**
 Author: Ibrahim Element
 Service: Asset Service
 Purpose: Serve data-collection purposes and unifies all Asset related tables through one interface.
 Models: Asset, Category, Folder
 */

public interface AssetService {

    // Categories
    int createCategory(Category category);

    List<Category> getAllCategories();

    List<Category> getAllCategoriesForProject(Long projectId) throws Exception;

    List<AssetFolder> getAllFoldersForCategory(Category category);

    List<Asset> getAllAssetsForCategory(Category category);

    Category getCategoryById(Long categoryId) throws Exception;

    // Folders

    void createFolder(AssetFolder folder);

    List<AssetFolder> getAllFoldersInsideFolder(AssetFolder assetFolder);

    List<Asset> getAllAssetsInsideFolder(AssetFolder assetFolder);

    AssetFolder getAssetFolderFromId(Long assetFolderId) throws Exception;

    Asset getAssetById(Long assetId);

    // Assets
    void createAsset(Asset asset);

    void updateAsset(Asset asset, Integer categoryId);

    void deleteAsset(Asset asset);

    // Asset Security
    void lockAsset(LockAssetRequest lockRequest, Long projectId);

    String getSecurityConfig(LockAssetRequest assetInfo);

    boolean createSecurityAssetRequest(JWTSessionContainer sessionContainer, SecurityAssetRequest request);

    boolean tryUnlockAsset(Long projectId, JWTSessionContainer sessionContainer, TryUnlockDataLockerRequest tryUnlock);

    List<AssetSecurityRequest> getAssetRequests(JWTSessionContainer sessionContainer, Long projectId);

    String ui_getAssetRequestTitle(JWTSessionContainer sessionContainer, AssetSecurityRequest securityRequest);

    boolean ui_isApprovedMember(JWTSessionContainer sessionContainer, Asset asset);

    boolean ui_isApprovedMember(JWTSessionContainer sessionContainer, AssetFolder assetFolder);

    void dev_approveMember(Long userId, UnlockAssetRequest unlockRequest);

}
