package projectManagementSystem.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Long userId;
    private String content;
    private LocalDateTime timestamp;

    public Comment() {
    }

    /**
     * Constructor for Comment.
     * @param userId
     * @param content
     */
    public Comment(Long userId, String content) {
        this.userId = userId;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * @return comment's ID
     */
    public long getId() {
        return id;
    }

    /**
     * @return user's ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @return comment's content
     */
    public String getContent() {
        return content;
    }

    /**
     * @return comment's timestamp
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Sets comment's content.
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Sets comment's timestamp.
     * @param timestamp
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
