package projectManagementSystem.entity.criterias;

import projectManagementSystem.entity.Item;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DueDateCriteria implements Criteria {
    private LocalDate dueDate;

    public DueDateCriteria(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public List<Item> meetCriteria(List<Item> items) {
        List<Item> filteredItems = new ArrayList<Item>();

        for (Item item : items) {
            if (dueDate.equals(item.getDueDate())) {
                filteredItems.add(item);
            }
        }

        return filteredItems;
    }
}
