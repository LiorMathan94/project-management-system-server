package projectManagementSystem.entity.criterias;

import projectManagementSystem.entity.Item;

import java.util.ArrayList;
import java.util.List;

public class TypeCriteria implements Criteria {
    private List<String> type;

    public TypeCriteria(List<String> type) {
        this.type = type;
    }

    @Override
    public List<Item> meetCriteria(List<Item> items) {
        List<Item> filteredItems = new ArrayList<Item>();

        for (Item item : items) {
            if (type.contains(item.getType())) {
                filteredItems.add(item);
            }
        }

        return filteredItems;
    }
}
