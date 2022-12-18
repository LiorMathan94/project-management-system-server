package projectManagementSystem.entity;

import javax.persistence.*;

@Entity
@Table(name = "user_in_board")
public class UserInBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "board_id", referencedColumnName = "id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Enumerated(EnumType.ORDINAL)
    private Role role;

    public UserInBoard() {
    }

    public UserInBoard(Board board, User user, Role role) {
        this.board = board;
        this.user = user;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public Board getBoard() {
        return board;
    }

    public User getUser() {
        return user;
    }

    public Role getRole() {
        return role;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
