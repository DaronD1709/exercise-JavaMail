package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import model.User;

/**
 * Lớp truy cập dữ liệu (DAO) dùng JPA. Không còn JDBC thủ công: EntityManager tự sinh SQL
 * (PreparedStatement có tham số) từ @Entity. Thao tác ghi (persist/merge/remove) phải nằm trong transaction.
 */
public class UserDAO {

    /** INSERT: lưu user mới. Trả true nếu commit thành công. */
    public boolean insert(User user) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(user);
            trans.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            if (trans.isActive()) {
                trans.rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }

    /** UPDATE: user phải có userId (thường lấy từ selectUser), nếu không merge sẽ INSERT dòng mới. */
    public boolean update(User user) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(user);
            trans.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            if (trans.isActive()) {
                trans.rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }

    /** DELETE: user phải có userId. merge() đưa user "detached" về trạng thái quản lý rồi mới remove(). */
    public boolean delete(User user) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.remove(em.merge(user));
            trans.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            if (trans.isActive()) {
                trans.rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }

    /** Tìm theo khoá chính: em.find(). Không có thì trả null. */
    public User getUserById(long userId) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            return em.find(User.class, userId);
        } finally {
            em.close();
        }
    }

    /** Tìm theo email bằng JPQL với tham số có tên (:email). Không có thì trả null. */
    public User selectUser(String email) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT u FROM User u WHERE u.email = :email";
        TypedQuery<User> q = em.createQuery(qString, User.class);
        q.setParameter("email", email);
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public boolean emailExists(String email) {
        return selectUser(email) != null;
    }
}
