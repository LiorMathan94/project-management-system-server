package projectManagementSystem.entity.criterias;

import projectManagementSystem.entity.Item;

import java.util.List;
import java.util.stream.Collectors;

public class StatusCriteria implements Criteria {
    private List<String> status;

    public StatusCriteria(List<String> status) {
        this.status = status;
    }

    @Override
    public List<Item> meetCriteria(List<Item> items) {
        List<Item> filteredItems = items.stream().filter(item -> status.contains(item.getStatus()))
                .collect(Collectors.toList());

        return filteredItems;
    }
}
