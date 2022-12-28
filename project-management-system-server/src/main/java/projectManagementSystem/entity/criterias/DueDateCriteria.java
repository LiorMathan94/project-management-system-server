package projectManagementSystem.entity.criterias;

import projectManagementSystem.entity.Item;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class DueDateCriteria implements Criteria {
    private LocalDate dueDate;

    public DueDateCriteria(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public List<Item> meetCriteria(List<Item> items) {
        List<Item> filteredItems = items.stream().filter(item -> item.getDueDate() != null &&
                        dueDate.isAfter(item.getDueDate())).collect(Collectors.toList());

        return filteredItems;
    }
}
