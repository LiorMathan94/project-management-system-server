package projectManagementSystem.entity.criterias;

import projectManagementSystem.entity.Importance;
import projectManagementSystem.entity.Item;

import java.util.List;
import java.util.stream.Collectors;

public class ImportanceCriteria implements Criteria {
    private List<Importance> importance;

    public ImportanceCriteria(List<Importance> importance) {
        this.importance = importance;
    }

    @Override
    public List<Item> meetCriteria(List<Item> items) {
        List<Item> filteredItems = items.stream().filter(item -> importance.contains(item.getImportance()))
                .collect(Collectors.toList());

        return filteredItems;
    }
}