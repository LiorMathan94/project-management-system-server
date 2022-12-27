package projectManagementSystem.entity.criterias;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import projectManagementSystem.entity.Item;
import projectManagementSystem.entity.User;
import projectManagementSystem.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class AssignedToCriteria implements Criteria {
    private List<String> assignedToId;
    UserRepository userRepository;


    public AssignedToCriteria(List<String> assignedToId) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("com.journaldev.spring");
        context.refresh();

        userRepository = context.getBean(UserRepository.class);

        this.assignedToId = assignedToId;
    }

    private String getEmailOfExistsUser(long userId){
        User user = userRepository.findById(userId).orElse(null);
        if (user == null){
            throw new IllegalArgumentException("User with Id: "+userId +"can't be assigned to item");
        }
        return user.getEmail();
    }
    @Override
    public List<Item> meetCriteria(List<Item> items) {
        List<Item> filteredItems = new ArrayList<>();
        String email;
        for (Item item : items) {
            email = getEmailOfExistsUser(item.getAssignedToId());
            if (assignedToId.contains(email)) {
                    filteredItems.add(item);
                }
            }

        return filteredItems;
    }
}
