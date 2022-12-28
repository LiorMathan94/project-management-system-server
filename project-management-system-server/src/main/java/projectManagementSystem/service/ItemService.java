package projectManagementSystem.service;

import org.springframework.stereotype.Service;
import projectManagementSystem.controller.request.ItemRequest;
import projectManagementSystem.entity.Board;
import projectManagementSystem.entity.Comment;
import projectManagementSystem.entity.DTO.BoardDTO;
import projectManagementSystem.entity.DTO.ItemDTO;
import projectManagementSystem.entity.Item;
import projectManagementSystem.repository.BoardRepository;
import projectManagementSystem.repository.ItemRepository;

import java.util.Optional;

@Service
public class ItemService {
    private ItemRepository itemRepository;
    private BoardRepository boardRepository;

    /**
     * Constructor for ItemService
     *
     * @param itemRepository
     */
    public ItemService(ItemRepository itemRepository, BoardRepository boardRepository) {
        this.itemRepository = itemRepository;
        this.boardRepository = boardRepository;
    }

    /**
     * Creates a new item and stores it in the database.
     *
     * @param itemRequest
     * @return the new item
     */
    public Item createItem(ItemRequest itemRequest) {
        Item item = create(itemRequest);
        return itemRepository.save(item);
    }

    /**
     * Creates a new item and stores it in the database.
     *
     * @param itemRequest
     * @return the new item
     */
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

    /**
     * Extracts parent item from itemRequest.
     *
     * @param itemRequest
     * @return the parent item
     */
    private Item extractParentFromItemRequest(ItemRequest itemRequest) {
        Item parent = null;

        if (itemRequest.getParentId() != 0) {
            Optional<Item> parentOptional = itemRepository.findById(itemRequest.getParentId());
            parent = parentOptional.isPresent() ? parentOptional.get() : null;
        }

        return parent;
    }

    /**
     * Updates an existing item.
     *
     * @param itemRequest
     * @return the updated item's DTO version
     */
    public ItemDTO updateItem(ItemRequest itemRequest) {
        Optional<Item> item = itemRepository.findById(itemRequest.getItemId());
        if (!item.isPresent()) {
            throw new IllegalArgumentException("Could not find item ID: " + itemRequest.getItemId());
        }

        updateBoardItem(item.get(), itemRequest);
        return ItemDTO.createFromItem(itemRepository.save(item.get()));
    }

    /**
     * Updates an existing item.
     *
     * @param item
     * @param itemRequest
     */
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
                setParent(item, itemRequest);
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

    /**
     * Adds a comment to an item.
     *
     * @param itemId
     * @param userId
     * @param content
     * @return the updated item's DTO version
     */
    public ItemDTO addComment(long itemId, long userId, String content) {
        Optional<Item> item = itemRepository.findById(itemId);
        if (!item.isPresent()) {
            throw new IllegalArgumentException("Could not find item ID: " + itemId);
        }

        item.get().addComment(new Comment(userId, content));
        return ItemDTO.createFromItem(itemRepository.save(item.get()));
    }

    /**
     * Deletes an item.
     *
     * @param itemId
     * @return the updated board's DTO version
     */
    public BoardDTO delete(long itemId) {
        Optional<Item> item = itemRepository.findById(itemId);
        if (!item.isPresent()) {
            throw new IllegalArgumentException("Could not find item ID: " + itemId);
        }

        Optional<Board> board = boardRepository.findById(item.get().getBoardId());
        if (!board.isPresent()) {
            throw new IllegalArgumentException("Could not find board ID: " + item.get().getBoardId());
        }

        preRemove(item.get());
        board.get().removeItem(item.get());
        return BoardDTO.createFromBoard(boardRepository.save(board.get()));
    }

    private void preRemove(Item item) {
        for (Item subItem : itemRepository.getItemsByParent(item)) {
            subItem.setParent(null);
            itemRepository.save(subItem);
        }
    }

    /**
     * Sets a parent item to an item.
     *
     * @param item
     * @param itemRequest
     */
    private void setParent(Item item, ItemRequest itemRequest) {
        Item parent = extractParentFromItemRequest(itemRequest);
        item.setParent(parent);

        try {
            validateNoSelfReferenceLoop(item);
        } catch (Exception e) {
            item.setParent(null);
            throw e;
        }
    }

    /**
     * Validates item does not have any self reference loop caused by the parent item reference.
     * If such loop is found - throws an IllegalArgumentException.
     *
     * @param item
     */
    private void validateNoSelfReferenceLoop(Item item) {
        Item fastPointer = item;
        Item slowPointer = item;

        while (slowPointer != null && fastPointer != null && fastPointer.getParent() != null) {
            slowPointer = slowPointer.getParent();
            fastPointer = fastPointer.getParent().getParent();

            if (fastPointer == slowPointer) {
                throw new IllegalArgumentException("Items cannot contain self reference loop!");
            }
        }
    }
}
