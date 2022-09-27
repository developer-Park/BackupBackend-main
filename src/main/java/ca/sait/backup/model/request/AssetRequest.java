package ca.sait.backup.model.request;

import lombok.Getter;
import lombok.Setter;

public class AssetRequest {

    /*
    Which folder to add this asset to, on the base page, this will be -1 which
    we assign to as NULL in the DB.
     */
    @Getter @Setter int folderId;

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


    @Getter @Setter String name;
    @Getter @Setter String type;

    /*
    The value of this will be in JSON for the UI to manually process using the type option.
     */
    @Getter @Setter String value;

}
