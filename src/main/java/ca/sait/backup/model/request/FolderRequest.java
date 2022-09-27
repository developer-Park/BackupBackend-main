package ca.sait.backup.model.request;

import lombok.Getter;
import lombok.Setter;

public class FolderRequest {

    /*
    The category to add this item to, we need to query the DB to find the id.
    This option is only available on the first page before any navigation occurs.
     */
    @Getter @Setter String categorySelection;

    /*
    The category to add this item to, if the request comes from the first page
    then this will be -1 and we resort the categorySelection option.
    */
    @Getter @Setter int categoryId;

    @Getter @Setter int folderId;

    @Getter @Setter String folderName;
}
