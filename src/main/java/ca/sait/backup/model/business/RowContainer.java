package ca.sait.backup.model.business;

import ca.sait.backup.model.entity.Project;

import java.util.ArrayList;
import java.util.List;

/** Author: Ibrahim Element
 * Used to contain items of type E in a list, helps for displaying items on UI with Thymeleaf.
 * @param <E> The type of item to contain within the newly created list
 */
public class RowContainer<E> {

    private ArrayList<ArrayList<E>> grid;

    /**
     * This constructor will take all the items provided, and break them into containers of size
     * rowSize, this is helpful for creating a grid of rows that should contain only rowSize items
     * per row.
     * @param items The items to break down (all in one array)
     * @param rowSize How many items to place in each row container
     */
    public RowContainer(List<E> items, int rowSize) {

        ArrayList<ArrayList<E>> rows = new ArrayList<>();

        for (int x = 0; x < items.size(); x += rowSize) {
            ArrayList<E> row = new ArrayList<E>();
            for (int i = 0; i < rowSize && (i + x) < items.size(); i++) {
                row.add(items.get(i + x));
            }
            ArrayList<E> rowContainer = new ArrayList<>(row);
            rows.add(rowContainer);
        }

        this.grid = rows;
    }

    /**
     * Will return the computed grid after processing each item in the constructor
     * @return ArrayList<ArrayList<E>> A 2D array list of provided type E and size rowSize
     */
    public ArrayList<ArrayList<E>> getGrid() {
        return this.grid;
    }

}
