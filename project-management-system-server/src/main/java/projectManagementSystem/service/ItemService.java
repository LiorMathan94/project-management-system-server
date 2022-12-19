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

    public Item setParent(ItemRequest itemRequest) {
        Optional<Item> item = itemRepository.findById(itemRequest.getItemId());

        if (!item.isPresent()) {
            throw new IllegalArgumentException(String.format("Item ID: %d was not found!", itemRequest.getItemId()));
        }

        Optional<Item> parentItem = itemRepository.findById(itemRequest.getParentId());
        Item parent = parentItem.isPresent() ? parentItem.get() : null;
        item.get().setParent(parent);

        return itemRepository.save(item.get());
    }

    public Item assign(ItemRequest itemRequest) {
        Optional<Item> item = itemRepository.findById(itemRequest.getItemId());

        if (!item.isPresent()) {
            throw new IllegalArgumentException(String.format("Item ID: %d was not found!", itemRequest.getItemId()));
        }

        item.get().setAssignedToId(itemRequest.getAssignedToId());
        return itemRepository.save(item.get());
    }

    public Item setType(ItemRequest itemRequest) {
        Optional<Item> item = itemRepository.findById(itemRequest.getItemId());

        if (!item.isPresent()) {
            throw new IllegalArgumentException(String.format("Item ID: %d was not found!", itemRequest.getItemId()));
        }

        item.get().setType(itemRequest.getType());
        return itemRepository.save(item.get());
    }

    public Item setStatus(ItemRequest itemRequest) {
        Optional<Item> item = itemRepository.findById(itemRequest.getItemId());

        if (!item.isPresent()) {
            throw new IllegalArgumentException(String.format("Item ID: %d was not found!", itemRequest.getItemId()));
        }

        item.get().setStatus(itemRequest.getStatus());
        return itemRepository.save(item.get());
    }

    public Item setDueDate(ItemRequest itemRequest) {
        Optional<Item> item = itemRepository.findById(itemRequest.getItemId());

        if (!item.isPresent()) {
            throw new IllegalArgumentException(String.format("Item ID: %d was not found!", itemRequest.getItemId()));
        }

        item.get().setDueDate(itemRequest.getDueDate());
        return itemRepository.save(item.get());
    }

    public Item setImportance(ItemRequest itemRequest) {
        Optional<Item> item = itemRepository.findById(itemRequest.getItemId());

        if (!item.isPresent()) {
            throw new IllegalArgumentException(String.format("Item ID: %d was not found!", itemRequest.getItemId()));
        }

        item.get().setImportance(itemRequest.getImportance());
        return itemRepository.save(item.get());
    }

    public Item setTitle(ItemRequest itemRequest) {
        Optional<Item> item = itemRepository.findById(itemRequest.getItemId());

        if (!item.isPresent()) {
            throw new IllegalArgumentException(String.format("Item ID: %d was not found!", itemRequest.getItemId()));
        }

        item.get().setTitle(itemRequest.getTitle());
        return itemRepository.save(item.get());
    }

    public Item setDescription(ItemRequest itemRequest) {
        Optional<Item> item = itemRepository.findById(itemRequest.getItemId());

        if (!item.isPresent()) {
            throw new IllegalArgumentException(String.format("Item ID: %d was not found!", itemRequest.getItemId()));
        }

        item.get().setDescription(itemRequest.getDescription());
        return itemRepository.save(item.get());
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
