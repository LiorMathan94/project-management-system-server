package projectManagementSystem.entity.criterias;

import projectManagementSystem.entity.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreatorCriteria implements Criteria {
    private Long[] creatorsId;

    public CreatorCriteria(Long[] creatorId) {
        this.creatorsId = creatorId;
    }

    @Override
    public List<Item> meetCriteria(List<Item> items) {
        List<Item> filteredItems = new ArrayList<>();

        for (Item item : items) {
            if (Arrays.asList(creatorsId).contains(item.getCreatorId())) {
                filteredItems.add(item);
            }
        }

        return filteredItems;
    }
}
