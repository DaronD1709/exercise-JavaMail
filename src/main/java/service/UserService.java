package service;

import dao.UserDAO;
import model.User;

/**
 * Lớp nghiệp vụ (business): chứa luật xử lý của ứng dụng.
 * Không có JPA/SQL (việc đó của DAO) và không biết gì về HTTP (việc đó của controller).
 */
public class UserService {

    private final UserDAO userDAO = new UserDAO();

    /**
     * Đăng ký user vào email list.
     *
     * @return true nếu đăng ký thành công, false nếu email đã tồn tại (hoặc lưu thất bại)
     */
    public boolean register(User user) {
        if (userDAO.emailExists(user.getEmail())) {
            return false;
        }
        return userDAO.insert(user);
    }
}
