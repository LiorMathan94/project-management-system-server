package projectManagementSystem.service;

import org.springframework.stereotype.Service;
import projectManagementSystem.controller.request.ItemRequest;
import projectManagementSystem.entity.Item;
import projectManagementSystem.repository.ItemRepository;

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
        Item parent = extractParentFromItemRequest(itemRequest);

        Item newItem = new Item.ItemBuilder(itemRequest.getBoardId(), itemRequest.getCreatorId(), itemRequest.getTitle())
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

    public Item update(ItemRequest itemRequest) {
        Optional<Item> item = itemRepository.findById(itemRequest.getItemId());

        if (!item.isPresent()) {
            throw new IllegalArgumentException(String.format("Item ID: %d was not found!", itemRequest.getItemId()));
        }

        updateItem(item.get(), itemRequest);
        return itemRepository.save(item.get());
    }

    private void updateItem(Item item, ItemRequest itemRequest) {
        switch (itemRequest.getBoardAction()) {
            case ASSIGN_ITEM:
                item.setAssignedToId(itemRequest.getAssignedToId());
                break;
            case SET_ITEM_TYPE:
                item.setType(itemRequest.getType());
                break;
            case SET_ITEM_TITLE:
                item.setTitle(itemRequest.getTitle());
                break;
            case SET_ITEM_PARENT:
                Optional<Item> parentItem = itemRepository.findById(itemRequest.getParentId());
                Item parent = parentItem.orElse(null);
                item.setParent(parent);
                break;
            case SET_ITEM_STATUS:
                item.setStatus(itemRequest.getStatus());
                break;
            case SET_ITEM_DUE_DATE:
                item.setDueDate(itemRequest.getDueDate());
                break;
            case SET_ITEM_IMPORTANCE:
                item.setImportance(itemRequest.getImportance());
                break;
            case SET_ITEM_DESCRIPTION:
                item.setDescription(itemRequest.getDescription());
                break;
            default:
                throw new IllegalArgumentException("Item operation is not supported!");
        }
    }

    public void deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);
    }

    private Item extractParentFromItemRequest(ItemRequest itemRequest) {
        Item parent = null;
        if (itemRequest.getParentId() != null) {
            Optional<Item> parentOptional = itemRepository.findById(itemRequest.getParentId());
            parent = parentOptional.isPresent() ? parentOptional.get() : null;
        }

        return parent;
    }
}
