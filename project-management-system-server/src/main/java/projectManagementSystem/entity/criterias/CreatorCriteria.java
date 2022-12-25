package projectManagementSystem.entity.criterias;

import projectManagementSystem.entity.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreatorCriteria implements Criteria {
    private List<Long> creatorsId;

    public CreatorCriteria(List<Long> creatorId) {
        this.creatorsId = creatorId;
    }

    @Override
    public List<Item> meetCriteria(List<Item> items) {
        List<Item> filteredItems = new ArrayList<>();
        System.out.println(creatorsId);
        for (Item item : items) {
            System.out.println(item.getCreatorId());
            if (creatorsId.contains(item.getCreatorId())) {
                filteredItems.add(item);
            }
        }

        return filteredItems;
    }
}
