package projectManagementSystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import projectManagementSystem.entity.Item;
import projectManagementSystem.service.FilterCriteriaService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootApplication
public class SpringApp {

    @Autowired
    FilterCriteriaService filterCriteriaService;


    public static void main(String[] args) {
        SpringApplication.run(SpringApp.class, args);
    }




    @EventListener(ApplicationReadyEvent.class)
    public void createDoc() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("to-do", "type1", LocalDate.of(2023, 2, 10)));
        items.add(new Item("to-do", "type2", LocalDate.of(2023, 2, 10)));
        items.add(new Item("done", "type1", LocalDate.of(2023, 2, 10)));
        items.add(new Item("done", "type1", LocalDate.of(2023, 2, 3)));

        HashMap<String, Object> map = new HashMap<>();
        map.put("status", "to-do");
        map.put("type", "type1");

//        List<Item> list = filterCriteriaService.filterByProperty(items, map);
//        for (Item item: list) {
//            System.out.println(item.getStatus() + " "+ item.getType());
//
//        }
        }
}
