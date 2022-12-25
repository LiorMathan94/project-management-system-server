package projectManagementSystem.service;

import org.springframework.stereotype.Service;
import projectManagementSystem.controller.request.ItemRequest;
import projectManagementSystem.entity.Comment;
import projectManagementSystem.entity.DTO.ItemDTO;
import projectManagementSystem.entity.Item;
import projectManagementSystem.repository.ItemRepository;

import java.util.Optional;

@Service
public class ItemService {
    private ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item createItem(ItemRequest itemRequest) {
        Item item = create(itemRequest);
        return itemRepository.save(item);
    }

    private Item create(ItemRequest itemRequest) {
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

        return newItem;
    }

    private Item extractParentFromItemRequest(ItemRequest itemRequest) {
        Item parent = null;

        if (itemRequest.getParentId() != 0) {
            Optional<Item> parentOptional = itemRepository.findById(itemRequest.getParentId());
            parent = parentOptional.isPresent() ? parentOptional.get() : null;
        }

        return parent;
    }

    public ItemDTO updateItem(ItemRequest itemRequest) {
        Optional<Item> item = itemRepository.findById(itemRequest.getItemId());
        if (!item.isPresent()) {
            throw new IllegalArgumentException("Could not find item ID: " + itemRequest.getItemId());
        }

        updateBoardItem(item.get(), itemRequest);
        return new ItemDTO(itemRepository.save(item.get()));
    }

    private void updateBoardItem(Item item, ItemRequest itemRequest) {
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
                Item parent = extractParentFromItemRequest(itemRequest);
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

    public ItemDTO addComment(long itemId, long userId, String content) {
        Optional<Item> item = itemRepository.findById(itemId);
        if (!item.isPresent()) {
            throw new IllegalArgumentException("Could not find item ID: " + itemId);
        }

        item.get().addComment(new Comment(userId, content));
        return new ItemDTO(itemRepository.save(item.get()));
    }

    public void deleteItem(long itemId) {
        itemRepository.deleteById(itemId);
    }
}
