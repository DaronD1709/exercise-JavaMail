package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

/**
 * JPA entity: mỗi đối tượng User tương ứng với 1 dòng của bảng "User".
 * USER là từ khoá dành riêng của PostgreSQL nên tên bảng phải để trong ngoặc kép.
 */
@Entity
@Table(name = "\"User\"")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   // id do PostgreSQL tự tăng
    private Long userId;

    private String firstName;
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    // JPA bắt buộc phải có constructor không tham số
    public User() {
    }

    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
