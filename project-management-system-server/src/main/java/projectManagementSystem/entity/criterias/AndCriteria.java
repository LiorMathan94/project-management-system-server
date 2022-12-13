package projectManagementSystem.entity.criterias;

import projectManagementSystem.entity.Item;

import java.util.List;

public class AndCriteria implements Criteria {
    private Criteria firstCriteria;
    private Criteria secondCriteria;

    public AndCriteria(Criteria firstCriteria, Criteria secondCriteria) {
        this.firstCriteria = firstCriteria;
        this.secondCriteria = secondCriteria;
    }

    @Override
    public List<Item> meetCriteria(List<Item> items) {
        List<Item> firstCriteriaItems = firstCriteria.meetCriteria(items);
        return secondCriteria.meetCriteria(firstCriteriaItems);
    }
}
