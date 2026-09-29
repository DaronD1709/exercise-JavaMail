package controller;

import dao.DBUtil;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * Khi ứng dụng bị dừng hoặc redeploy: đóng EntityManagerFactory để trả hết connection về PostgreSQL.
 * Thiếu bước này, mỗi lần redeploy sẽ bỏ lại các connection mở và dần cạn số connection của DB.
 */
@WebListener
public class JpaShutdownListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DBUtil.closeEmFactory();
    }
}
