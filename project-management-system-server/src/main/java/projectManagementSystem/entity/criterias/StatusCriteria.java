package projectManagementSystem.entity.criterias;

import projectManagementSystem.entity.Item;

import java.util.ArrayList;
import java.util.List;

public class StatusCriteria implements Criteria {
    private List<String> status;

    public StatusCriteria(List<String> status) {
        this.status = status;
    }

    @Override
    public List<Item> meetCriteria(List<Item> items) {
        List<Item> filteredItems = new ArrayList<Item>();

        for (Item item : items) {
            if (status.contains(item.getStatus())) {
                filteredItems.add(item);
            }
        }

        return filteredItems;
    }
}
