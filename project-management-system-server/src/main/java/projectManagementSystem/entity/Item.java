package projectManagementSystem.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long boardId;
    private String status;
    private String type;
    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
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
    @ElementCollection
    @Column(name = "comments")
    private List<Comment> commentList;

    public Item() {
    }

    public Item(ItemBuilder builder) {
        this.title = builder.title;
        this.status = builder.status;
        this.type = builder.type;
        this.dueDate = builder.dueDate;
        this.parent = builder.parent;
        this.commentList = builder.commentList;
        this.assignedToId = builder.assignedToId;
        this.creatorId = builder.creatorId;
        this.description = builder.description;
        this.importance = builder.importance;
    }

    public long getId() {
        return id;
    }

    public long getBoardId() {
        return boardId;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public Item getParent() {
        return parent;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public Long getAssignedToId() {
        return assignedToId;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Importance getImportance() {
        return importance;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setParent(Item parent) {
        this.parent = parent;
    }

    public void setAssignedToId(Long assignedToId) {
        this.assignedToId = assignedToId;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setImportance(Importance importance) {
        this.importance = importance;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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
            this.description = description;
            return this;
        }

        public Item build() {
            return new Item(this);
        }
    }
}
