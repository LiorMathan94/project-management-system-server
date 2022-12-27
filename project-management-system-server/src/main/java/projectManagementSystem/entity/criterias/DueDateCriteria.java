package projectManagementSystem.entity.criterias;

import projectManagementSystem.entity.Item;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DueDateCriteria implements Criteria {
    private LocalDate dueDate;

    public DueDateCriteria(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public List<Item> meetCriteria(List<Item> items) {
        List<Item> filteredItems = new ArrayList<>();

        for (Item item : items) {
            if (item.getDueDate() != null && dueDate.isAfter(item.getDueDate())) {
                filteredItems.add(item);
            }
        }

        return filteredItems;
    }
}
