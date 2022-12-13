package projectManagementSystem.entity.criterias;

import projectManagementSystem.entity.Item;

import java.util.List;

public interface Criteria {
    public List<Item> meetCriteria(List<Item> items);
}
