package projectManagementSystem.service;

import org.springframework.stereotype.Service;
import projectManagementSystem.controller.request.ItemRequest;
import projectManagementSystem.entity.Importance;
import projectManagementSystem.entity.Item;
import projectManagementSystem.repository.ItemRepository;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Optional<Item> getById(long itemId) {
        return this.itemRepository.findById(itemId);
    }

    public Item createItem(ItemRequest itemRequest) {
        Optional<Item> parentOptional = itemRepository.findById(itemRequest.getParentId());
        Item parent = parentOptional.isPresent() ? parentOptional.get() : null;

        Item newItem = new Item.ItemBuilder(itemRequest.getCreatorId(), itemRequest.getTitle())
                .setAssignedToId(itemRequest.getAssignedToId())
                .setDescription(itemRequest.getDescription())
                .setDueDate(itemRequest.getDueDate())
                .setImportance(itemRequest.getImportance())
                .setParent(parent)
                .setStatus(itemRequest.getStatus())
                .setType(itemRequest.getType())
                .build();

        return itemRepository.save(newItem);
    }

    public Item updateItem(ItemRequest itemRequest) throws IllegalAccessException {
        Optional<Item> item = itemRepository.findById(itemRequest.getItemId());

        if (!item.isPresent()) {
            throw new IllegalAccessException(String.format("Item ID: %d was not found!", itemRequest.getItemId()));
        }

        for (Field field : itemRequest.getClass().getDeclaredFields()) {
            updateItemField(item.get(), field, itemRequest);
        }

        return itemRepository.save(item.get());
    }

    public void deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);
    }

    private void updateItemField(Item item, Field field, ItemRequest itemRequest) throws IllegalAccessException {
        field.setAccessible(true);

        Object fieldValue = field.get(itemRequest);
        if (fieldValue != null) {
            switch (field.getName()) {
                case "status":
                    item.setStatus((String) fieldValue);
                    break;
                case "type":
                    item.setType((String) fieldValue);
                    break;
                case "parentId":
                    Optional<Item> parentOptional = itemRepository.findById(itemRequest.getParentId());
                    Item parent = parentOptional.isPresent() ? parentOptional.get() : null;
                    item.setParent(parent);
                    break;
                case "assignedToId":
                    item.setAssignedToId((Long) fieldValue);
                    break;
                case "dueDate":
                    item.setDueDate((LocalDate) fieldValue);
                    break;
                case "importance":
                    item.setImportance((Importance) fieldValue);
                    break;
                case "item":
                    item.setTitle((String) fieldValue);
                    break;
                case "description":
                    item.setDescription((String) fieldValue);
                    break;
            }
        }
    }
}
