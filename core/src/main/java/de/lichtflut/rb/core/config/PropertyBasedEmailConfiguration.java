package de.lichtflut.rb.core.config;

import java.util.Properties;

/**
 * <p>
 *  E-Mail configuration based on a set of properties.
 * </p>
 *
 * <p>
 * Created 02.11.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class PropertyBasedEmailConfiguration implements EmailConfiguration {

    public static final String SMTP_SERVER = "email-service.smtp-server";
    public static final String SMTP_USER = "email-service.smtp-user";
    public static final String SMTP_PASSWORD = "email-service.smtp-password";
    public static final String SUPPORT_NAME = "email-service.suppert-name";
    public static final String SUPPORT_EMAIL = "email-service.support-email";
    public static final String APP_NAME = "email-service.application-name";
    public static final String APP_EMAIL = "email-service.application-email";

    private final Properties properties = new Properties();

    // ----------------------------------------------------

    public PropertyBasedEmailConfiguration() {
    }

    public PropertyBasedEmailConfiguration(Properties properties) {
        this.properties.putAll(properties);
    }

    // ----------------------------------------------------

    @Override
    public String getSmtpServer() {
        return properties.getProperty(SMTP_SERVER);
    }

    @Override
    public String getSmtpUser() {
        return properties.getProperty(SMTP_USER);
    }

    @Override
    public String getSmtpPassword() {
        return properties.getProperty(SMTP_PASSWORD);
    }

    @Override
    public String getApplicationSupportName() {
        return properties.getProperty(SUPPORT_NAME);
    }

    @Override
    public String getApplicationSupportEmail() {
        return properties.getProperty(SUPPORT_EMAIL);
    }

    @Override
    public String getApplicationName() {
        return properties.getProperty(APP_NAME);
    }

    @Override
    public String getApplicationEmail() {
        return properties.getProperty(APP_EMAIL);
    }

    // ----------------------------------------------------

    public void setSmtpServer(String smtpServer) {
        properties.setProperty(SMTP_SERVER, smtpServer);
    }

    public void setSmtpUser(String smtpUser) {
        properties.setProperty(SMTP_USER, smtpUser);
    }

    public void setSmtpPassword(String smtpPassword) {
        properties.setProperty(SMTP_PASSWORD, smtpPassword);
    }

    public void setApplicationSupportName(String applicationSupportName) {
        properties.setProperty(SUPPORT_NAME, applicationSupportName);
    }

    public void setApplicationSupportEmail(String applicationSupportEmail) {
        properties.setProperty(SUPPORT_EMAIL, applicationSupportEmail);
    }

    public void setApplicationName(String applicationName) {
        properties.setProperty(APP_NAME, applicationName);
    }

    public void setApplicationEmail(String applicationEmail) {
        properties.setProperty(APP_EMAIL, applicationEmail);
    }

    // ----------------------------------------------------

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("EmailConfiguration{ ");
        sb.append(SMTP_SERVER).append("=").append(getSmtpServer()).append(", ");
        sb.append(SMTP_USER).append("=").append(getSmtpServer()).append(", ");
        sb.append(SUPPORT_NAME).append("=").append(getApplicationSupportName()).append(", ");
        sb.append(SUPPORT_EMAIL).append("=").append(getApplicationSupportName()).append(", ");
        sb.append(APP_NAME).append("=").append(getApplicationName()).append(", ");
        sb.append(APP_EMAIL).append("=").append(getApplicationEmail()).append(", ");
        sb.append("}");
        return sb.toString();
    }
}
