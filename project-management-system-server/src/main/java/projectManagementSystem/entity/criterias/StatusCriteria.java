package projectManagementSystem.entity.criterias;

import projectManagementSystem.entity.Item;

import java.util.ArrayList;
import java.util.List;

public class StatusCriteria implements Criteria {
    private String status;

    public StatusCriteria(String status) {
        this.status = status;
    }

    @Override
    public List<Item> meetCriteria(List<Item> items) {
        List<Item> filteredItems = new ArrayList<Item>();

        for (Item item : items) {
            if (item.getStatus().equalsIgnoreCase(status)) {
                filteredItems.add(item);
            }
        }

        return filteredItems;
    }
}
