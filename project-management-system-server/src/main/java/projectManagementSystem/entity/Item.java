package projectManagementSystem.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long boardId;
    private String status;
    private String type;
    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Item parent;
    @Column(name = "creator_id")
    private Long creatorId;
    @Column(name = "assigned_user_id")
    private Long assignedToId;
    @Column(name = "due_date")
    private LocalDate dueDate;
    @Enumerated(EnumType.ORDINAL)
    private Importance importance;
    private String title;
    private String description;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "comments")
    private List<Comment> commentList;

    public Item() {
    }

    /**
     * Constructor for Item.
     * @param builder
     */
    public Item(ItemBuilder builder) {
        this.boardId = builder.boardId;
        this.title = builder.title;
        this.status = builder.status;
        this.type = builder.type;
        this.dueDate = builder.dueDate;
        this.commentList = builder.commentList;
        this.assignedToId = builder.assignedToId;
        this.creatorId = builder.creatorId;
        this.description = builder.description;
        this.importance = builder.importance;
        this.parent = builder.parent;
    }

    /**
     * @return item's ID
     */
    public long getId() {
        return id;
    }

    /**
     * @return board's ID
     */
    public long getBoardId() {
        return boardId;
    }

    /**
     * @return item's status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @return item's type
     */
    public String getType() {
        return type;
    }

    /**
     * @return item's parent item
     */
    public Item getParent() {
        return parent;
    }

    /**
     * @return item's creator's ID
     */
    public Long getCreatorId() {
        return creatorId;
    }

    /**
     * @return item's assigned user's ID
     */
    public Long getAssignedToId() {
        return assignedToId;
    }

    /**
     * @return item's due date
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * @return item's importance
     */
    public Importance getImportance() {
        return importance;
    }

    /**
     * @return item's title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return item's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return item's comments
     */
    public List<Comment> getCommentList() {
        return commentList;
    }

    /**
     * Sets item's status.
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Sets item's type.
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Sets item's parent item.
     * @param parent
     */
    public void setParent(Item parent) {
        this.parent = parent;
    }

    /**
     * Sets item's assigned user.
     * @param assignedToId
     */
    public void setAssignedToId(Long assignedToId) {
        this.assignedToId = assignedToId;
    }

    /**
     * Sets item's due date.
     * @param dueDate
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Sets item's importance.
     * @param importance
     */
    public void setImportance(Importance importance) {
        this.importance = importance;
    }

    /**
     * Sets item's title.
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets item's description.
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Adds a comment to item's comment list.
     * @param comment
     */
    public void addComment(Comment comment) {
        this.commentList.add(comment);
    }

    public static class ItemBuilder {
        private long boardId;
        private String status;
        private String type;
        private Item parent;
        private long creatorId;
        private long assignedToId;
        private LocalDate dueDate;
        private Importance importance;
        private String title;
        private String description;
        private List<Comment> commentList;

        public ItemBuilder(long boardId, long creatorId, String title) {
            this.boardId = boardId;
            this.creatorId = creatorId;
            this.title = title;
            this.description = "";
            this.commentList = new ArrayList<>();
        }

        public ItemBuilder setBoardId(long boardId) {
            this.boardId = boardId;
            return this;
        }

        public ItemBuilder setStatus(String status) {
            this.status = status;
            return this;
        }

        public ItemBuilder setType(String type) {
            this.type = type;
            return this;
        }

        public ItemBuilder setParent(Item parent) {
            this.parent = parent;
            return this;
        }

        public ItemBuilder setAssignedToId(long assignedToId) {
            this.assignedToId = assignedToId;
            return this;
        }

        public ItemBuilder setDueDate(LocalDate dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public ItemBuilder setImportance(Importance importance) {
            this.importance = importance;
            return this;
        }

        public ItemBuilder setDescription(String description) {
            this.description = (description == null) ? "" : description;
            return this;
        }

        public Item build() {
            return new Item(this);
        }

        @Override
        public String toString() {
            return "ItemBuilder{" +
                    "boardId=" + boardId +
                    ", status='" + status + '\'' +
                    ", type='" + type + '\'' +
                    ", parent=" + parent +
                    ", creatorId=" + creatorId +
                    ", assignedToId=" + assignedToId +
                    ", dueDate=" + dueDate +
                    ", importance=" + importance +
                    ", title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    ", commentList=" + commentList +
                    '}';
        }
    }
}
