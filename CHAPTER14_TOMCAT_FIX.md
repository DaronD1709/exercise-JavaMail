# Chapter 14 Tomcat 10 JavaMail fix

The runtime error was:

    java.lang.NoClassDefFoundError: javax/mail/MessagingException

The JavaMail libraries are now included in:

    src/main/webapp/WEB-INF/lib/javax.mail-1.6.5.jar
    src/main/webapp/WEB-INF/lib/javax.activation.jar

This is intentional for NetBeans/Tomcat exploded deployment.
