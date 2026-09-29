# Chapter 14 - Use JavaMail to Send Mail

This project is cloned from `exercise-email-list-jpa` (Chapter 13) and extended for Chapter 14.

## What was added

- `src/main/java/util/MailUtilGmail.java`: JavaMail helper that connects to Gmail SMTP.
- `EmailListServlet`: after a successful email-list registration, sends a plain-text email.
- `thanks.jsp`: shows whether the mail send succeeded or failed.
- `lib/javax.mail-1.6.5.jar` and `lib/javax.activation.jar`: local JavaMail dependencies for NetBeans/Maven.
- `README_CHAPTER14.md`: this setup guide.

The default test recipient is:

`danhhuunguyen1709@gmail.com`

You can override it with `MAIL_TO` or JVM property `mail.to`.

## Gmail configuration

The project does **not** store a real Gmail password in the source code.

Configure these values before starting Tomcat:

```text
MAIL_USERNAME=your-sender-account@gmail.com
MAIL_PASSWORD=your-16-digit-app-password
MAIL_TO=danhhuunguyen1709@gmail.com
MAIL_FROM=your-sender-account@gmail.com
MAIL_DEBUG=false
```

`MAIL_FROM` is optional and defaults to `MAIL_USERNAME`.
`MAIL_TO` is optional and defaults to `danhhuunguyen1709@gmail.com`.

The code uses Gmail SMTP over SSL on port 465.

### NetBeans / Tomcat

A simple option is to set the JVM system properties for the Tomcat process:

```text
-Dmail.username=your-sender-account@gmail.com
-Dmail.password=your-16-digit-app-password
-Dmail.to=danhhuunguyen1709@gmail.com
-Dmail.from=your-sender-account@gmail.com
```

Alternatively, set the environment variables above before launching NetBeans/Tomcat.

Do not commit a real password to Git.

## Important Gmail note

For this JavaMail/basic-SMTP exercise, use a Gmail **App Password**, not your normal Gmail password. Google documents that App Passwords require 2-Step Verification and are intended for apps that do not provide the normal "Sign in with Google" flow.

## Database

The existing JPA/PostgreSQL configuration is unchanged. The same database settings from Chapter 13 still apply:

```text
DB_URL=jdbc:postgresql://localhost:5432/email_list_jpa_db
DB_USER=darond
DB_PASSWORD=
```

The values in `DB_URL`, `DB_USER`, and `DB_PASSWORD` override the defaults in `persistence.xml`.

## Running

Open this folder in NetBeans as a Maven Web Application and deploy to Tomcat 10.1+.

Then open:

```text
http://localhost:8080/exercise-email-list-jpa/
```

Enter a **new** email address, submit the form, and check `danhhuunguyen1709@gmail.com`.

The database registration is saved independently of email delivery. If Gmail is not configured correctly, the registration still succeeds and the thanks page shows a mail error message.

## NetBeans/Tomcat 10 fix: JavaMail runtime libraries

If Tomcat reports `NoClassDefFoundError: javax/mail/MessagingException`, the JavaMail JARs are not visible to the exploded web application used by NetBeans.

This project includes the JavaMail runtime JARs directly under `src/main/webapp/WEB-INF/lib/` so Tomcat can load them during NetBeans Run/Deploy as well as in the packaged WAR.

After opening this fixed project in NetBeans:
1. Clean and Build the project.
2. Stop and restart Tomcat (or undeploy/redeploy the old application).
3. Run the project again.

Do not remove the two JARs from `src/main/webapp/WEB-INF/lib/`.
