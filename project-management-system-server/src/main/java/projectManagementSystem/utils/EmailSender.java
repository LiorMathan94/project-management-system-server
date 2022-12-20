package projectManagementSystem.utils;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.io.*;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;

import java.util.Objects;
import java.util.Properties;
import java.util.Set;

public class EmailSender {
    private static final Logger LOGGER = LogManager.getLogger(EmailSender.class.getName());
    private static final String FROM_EMAIL_ADDRESS = "project.manage2023@gmail.com";

    /**
     * Creates an authorized Credential object.
     *
     * @param httpTransport The network HTTP Transport.
     * @param jsonFactory   Parses Jason data.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport httpTransport, GsonFactory jsonFactory) throws IOException {
        // Load client secrets.
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory, new InputStreamReader(Objects.requireNonNull(EmailSender.class.getResourceAsStream("/client_secret.json"))));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, jsonFactory, clientSecrets, Set.of(GmailScopes.GMAIL_SEND))
                .setDataStoreFactory(new FileDataStoreFactory(Paths.get("src/main/resources/google_tokens").toFile()))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }


    /**
     * Creates the Gmail API Service.
     *
     * @return Gmail - Gmail API Service object
     * @throws GeneralSecurityException
     * @throws IOException
     */
    private static Gmail getGmailService() throws GeneralSecurityException, IOException {
        GsonFactory jsonFactory = GsonFactory.getDefaultInstance();
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new Gmail.Builder(httpTransport, GsonFactory.getDefaultInstance(), getCredentials(httpTransport, jsonFactory))
                .setApplicationName("Project Management System")
                .build();
    }

    /**
     * Creates an email with the given email subject and email body, and sends it to the given destination email.
     *
     * @param emailSubject   -    Subject of the email.
     * @param emailBody      -    Body of the email.
     * @param toEmailAddress - Email address of the recipient
     * @return the sent Message object - if message was sent successfully, otherwise - null.
     * @throws MessagingException       - if a wrongly formatted address is encountered.
     * @throws IOException              - if service account credentials file not found.
     * @throws GeneralSecurityException
     */
    public static Message sendEmail(String emailSubject, String emailBody, String toEmailAddress) throws MessagingException, IOException, GeneralSecurityException {
        // Encode as MIME message
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage email = new MimeMessage(session);
        email.setFrom(new InternetAddress(FROM_EMAIL_ADDRESS));
        email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(toEmailAddress));
        email.setSubject(emailSubject);
        email.setText(emailBody);

        // Encode and wrap the MIME message into a gmail message
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        email.writeTo(buffer);
        byte[] rawMessageBytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);
        Message message = new Message();
        message.setRaw(encodedEmail);

        try {
            // Create send message
            message = getGmailService().users().messages().send("me", message).execute();
            LOGGER.info("Email sent to: " + toEmailAddress + "\nMessage id: " + message.getId() + "\n" + message.toPrettyString());
            return message;
        } catch (GoogleJsonResponseException e) {
            GoogleJsonError error = e.getDetails();
            LOGGER.error("Error sending email to: " + toEmailAddress + ". Exception: " + e);
            if (error.getCode() == 403) {
                LOGGER.error("Unable to send email to: " + toEmailAddress + ". Details: " + e.getDetails());
            } else {
                throw e;
            }
        }
        return null;
    }
}

