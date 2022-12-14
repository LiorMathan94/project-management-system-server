package projectManagementSystem.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Long userId;
    private String content;
    private LocalDateTime timestamp;

    public Comment() {
    }

    public Comment(Long userId) {
        this.userId = userId;
        this.content = "";
        this.timestamp = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
