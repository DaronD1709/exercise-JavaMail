package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.mail.MessagingException;
import model.User;
import service.UserService;
import util.MailUtilGmail;

/**
 * Controller: đọc tham số request, gọi Service, chọn trang (view) để hiển thị.
 * Chapter 14: sau khi đăng ký thành công, gửi email bằng JavaMail/Jakarta-independent
 * JavaMail API qua Gmail SMTP.
 */
@WebServlet("/emailList")
public class EmailListServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String url = "/index.jsp";
        String action = request.getParameter("action");
        if (action == null) {
            action = "join";
        }

        if (action.equals("join")) {
            url = "/index.jsp";
        } else if (action.equals("add")) {
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");

            User user = new User(firstName, lastName, email);
            String message;

            if (userService.register(user)) {
                message = "";
                url = "/thanks.jsp";

                // Chapter 14: send a confirmation/registration email.
                try {
                    String to = MailUtilGmail.getMailTo();
                    String from = MailUtilGmail.getMailFrom();
                    String subject = "New email list registration";
                    String body = "Dear " + firstName + ",\n\n"
                            + "Thanks for joining our email list.\n\n"
                            + "Registration information:\n"
                            + "First Name: " + firstName + "\n"
                            + "Last Name: " + lastName + "\n"
                            + "Email: " + email + "\n\n"
                            + "This message was sent by the exercise-email-list-jpa Chapter 14 project.";

                    MailUtilGmail.sendMail(to, from, subject, body, false);
                    request.setAttribute("mailStatus", "Email sent successfully to " + to + ".");
                } catch (MessagingException | IllegalStateException e) {
                    // The user has already been saved. Do not report registration as failed
                    // just because the external mail server is unavailable/misconfigured.
                    request.setAttribute("mailError", "Registration was saved, but the email could not be sent. "
                            + "Check the Gmail SMTP settings in the README.");
                    this.log("Unable to send Chapter 14 email.", e);
                }
            } else {
                message = "This email address already exists.<br>"
                        + "Please enter another email address.";
                url = "/index.jsp";
            }

            request.setAttribute("user", user);
            request.setAttribute("message", message);
        }

        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
}
