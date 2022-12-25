package projectManagementSystem.entity.criterias;

import projectManagementSystem.entity.Importance;
import projectManagementSystem.entity.Item;

import java.util.ArrayList;
import java.util.List;

public class ImportanceCriteria implements Criteria {
    private List<Importance> importance;

    public ImportanceCriteria(List<Importance> importance) {
        this.importance = importance;
    }

    @Override
    public List<Item> meetCriteria(List<Item> items) {
        List<Item> filteredItems = new ArrayList<>();

        for (Item item : items) {
            if (importance.contains(item.getImportance())) {
                filteredItems.add(item);
            }
        }

        return filteredItems;
    }
}