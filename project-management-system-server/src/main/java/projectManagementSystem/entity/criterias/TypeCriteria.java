package projectManagementSystem.entity.criterias;

import projectManagementSystem.entity.Item;

import java.util.List;
import java.util.stream.Collectors;

public class TypeCriteria implements Criteria {
    private List<String> type;

    public TypeCriteria(List<String> type) {
        this.type = type;
    }

    @Override
    public List<Item> meetCriteria(List<Item> items) {
        List<Item> filteredItems = items.stream().filter(item -> type.contains(item.getType()))
                .collect(Collectors.toList());

        return filteredItems;
    }
}
