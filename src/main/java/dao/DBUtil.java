package dao;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

/**
 * Giữ EntityManagerFactory (tạo 1 lần cho cả ứng dụng, tốn kém) của persistence unit "emailListPU".
 * EntityManager thì rẻ nhưng KHÔNG thread-safe, nên mỗi phương thức DAO tự tạo EntityManager riêng.
 */
public class DBUtil {

    private static EntityManagerFactory emf = null;

    private DBUtil() {
    }

    public static synchronized EntityManagerFactory getEmFactory() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("emailListPU", envOverrides());
        }
        return emf;
    }

    /** Gọi khi ứng dụng dừng: đóng EntityManagerFactory để trả hết connection về DB. */
    public static synchronized void closeEmFactory() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
        emf = null;
    }

    /** Biến môi trường (Docker/Render) ghi đè giá trị mặc định trong persistence.xml. */
    private static Map<String, String> envOverrides() {
        Map<String, String> props = new HashMap<>();
        put(props, "jakarta.persistence.jdbc.url", System.getenv("DB_URL"));
        put(props, "jakarta.persistence.jdbc.user", System.getenv("DB_USER"));
        put(props, "jakarta.persistence.jdbc.password", System.getenv("DB_PASSWORD"));
        return props;
    }

    private static void put(Map<String, String> props, String key, String value) {
        if (value != null && !value.isEmpty()) {
            props.put(key, value);
        }
    }
}
