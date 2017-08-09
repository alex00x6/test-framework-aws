package mechanics.system.email;

import mechanics.system.credentials.RoleSwitcher;
import org.junit.Assert;
import ru.yandex.qatools.allure.annotations.Step;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Kutafin Oleg on 24.04.2017.
 */
public class EmailChecker {
    private static boolean checkEmail(String host, String storeType, String userName, String password, String subject, long searchStartTime, String textForCheck) {
        boolean found = false;
        try {
            // create properties field
            Properties properties = new Properties();
            properties.put("mail.pop3s.host", host);
            properties.put("mail.pop3s.port", "995");
            properties.put("mail.pop3s.starttls.enable", "true");
            // Setup authentication, get session
            Session emailSession = Session.getInstance(properties, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(userName, password);
                }
            });
            //emailSession.setDebug(true);
            // create the POP3 store object and connect with the pop server
            Store store = emailSession.getStore("pop3s");
            store.connect();
            // create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);
            // retrieve the messages from the folder in an array and print it
            Message[] messages = emailFolder.getMessages();
            System.out.println("============ Checking mail for income message. =============");
            System.out.println("Inbox - " + messages.length);
            for (int i = 0, n = messages.length; i < n; i++) {
                Message message = messages[i];
                System.out.println("Looking for: '" + subject + "' Current: '" + message.getSubject() + "'");
                if (message.getSubject().contains(subject)) {
                    if (message.getSentDate().getTime() > searchStartTime) {

                        if (message.getContent().toString().contains(textForCheck)) {
                            System.out.println("---------------------------------");
                            System.out.println("Message with suitable Subject was found");
                            System.out.println("Message was sent not earlier than 20 seconds before email check started: " + message.getSentDate().getTime() + ">" + searchStartTime + ".");
                            System.out.println("Subject: " + message.getSubject());
                            System.out.println("From: " + message.getFrom()[0]);
                            System.out.println("Send at: " + message.getSentDate());
                            found = true;
                        } else {
                            System.out.println("=======Wrong text included in message.======");
                        }
                    } else {
                        System.out.println("No message with suitable time stamp found. Continue to search...");
                    }
                }
            }
            // close the store and folder objects
            emailFolder.close(false);
            store.close();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return found;
    }

    private static boolean checkEmailForReport(String host, String storeType, String userName, String password, String subject, long searchStartTime, String fileName) {
        boolean found = false;
        try {
            // create properties field
            Properties properties = new Properties();
            properties.put("mail.pop3s.host", host);
            properties.put("mail.pop3s.port", "995");
            properties.put("mail.pop3s.starttls.enable", "true");
            // Setup authentication, get session
            Session emailSession = Session.getInstance(properties, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(userName, password);
                }
            });
            //emailSession.setDebug(true);
            // create the POP3 store object and connect with the pop server
            Store store = emailSession.getStore("pop3s");
            store.connect();
            // create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);
            // retrieve the messages from the folder in an array and print it
            Message[] messages = emailFolder.getMessages();
            System.out.println("============ Checking mail for income message. =============");
            System.out.println("Inbox - " + messages.length);
            for (int i = 0, n = messages.length; i < n; i++) {
                Message message = messages[i];
                System.out.println("Looking for: '" + subject + "' Current: '" + message.getSubject() + "'");
                if (message.getSubject().contains(subject)) {
                    if (message.getSentDate().getTime() > searchStartTime) {
                        String contentType = message.getContentType();
                        if (contentType.contains("multipart")) {
                            Multipart multiPart = (Multipart) message.getContent();
                            for (int i1 = 0; i1 < multiPart.getCount(); i1++) {
                                MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(i1);
                                if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                                    if (part.getFileName().contains(fileName)) {
                                        if (part.getFileName().contains(".pdf")) {
                                            System.out.println("---------------------------------");
                                            System.out.println("Message with suitable Subject was found");
                                            System.out.println("Message was sent not earlier than 60 seconds before email check started: " + message.getSentDate().getTime() + ">" + searchStartTime + ".");
                                            System.out.println("Subject: " + message.getSubject());
                                            System.out.println("From: " + message.getFrom()[0]);
                                            System.out.println("Send at: " + message.getSentDate());
                                            System.out.println("Included file name: " + part.getFileName() + ".");
                                            System.out.println("Message size: " + message.getSize() + " KB.");
                                            found = true;
                                        } else {
                                            System.out.println("+++++++++++The file extension is not .pdf+++++++++++");
                                        }
                                    } else {
                                        System.out.println("++++++++++The file that included had wrong name.+++++++++++");
                                    }
                                }
                            }

                        }
                    } else {
                        System.out.println("No message with suitable time stamp found. Continue to search...");
                    }

                }
            }
            // close the store and folder objects
            emailFolder.close(false);
            store.close();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return found;
    }

    private static void cleanInbox(String host, String userName, String password) {
        try {
            // create properties field
            Properties properties = new Properties();
            properties.put("mail.pop3s.host", host);
            properties.put("mail.pop3s.port", "995");
            properties.put("mail.pop3s.starttls.enable", "true");
            // Setup authentication, get session
            Session emailSession = Session.getInstance(properties, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(userName, password);
                }
            });
            //emailSession.setDebug(true);
            // create the POP3 store object and connect with the pop server
            Store store = emailSession.getStore("pop3s");
            store.connect();
            // create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_WRITE);
            // retrieve the messages from the folder in an array and print it
            Message[] messages = emailFolder.getMessages();
            for (int i = 0, n = messages.length; i < n; i++) {
                Message message = messages[i];
                message.setFlag(Flags.Flag.DELETED, true);
            }
            // close the store and folder objects
            emailFolder.close(true);
            store.close();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void cleanInbox() {
        String mail = RoleSwitcher.getCurrentUser().getEmail();
        String pass = RoleSwitcher.getCurrentUser().getPassword();
        String host = "pop.gmail.com";// change accordingly
        cleanInbox(host, mail, pass);
    }

    private boolean getAttachments(Message message, String fileName) throws MessagingException, IOException {
        String contentType = message.getContentType();
        if (contentType.contains("multipart")) {
            Multipart multiPart = (Multipart) message.getContent();
            for (int i1 = 0; i1 < multiPart.getCount(); i1++) {
                MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(i1);
                if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                    if (part.getFileName().contains(fileName)) {
                        return true;
                    }
                }
            }

        }

        return false;
    }

    @Step("Checking if notification comes to e-mail.")
    public boolean checkLong(String subject, String textForCheck) {
        String mail = RoleSwitcher.getCurrentUser().getEmail();
        String pass = RoleSwitcher.getCurrentUser().getPassword();
        String host = "pop.gmail.com";// change accordingly
        String mailStoreType = "pop3";
        long searchStartTime = System.currentTimeMillis() - 60000;
        boolean found = false;
        int counter = 0;
        System.out.println("============ Starting email check... =============");
        while (!found && counter < 10) {
            found = checkEmail(host, mailStoreType, mail, pass, subject, searchStartTime, textForCheck);
            if (!found) {
                System.out.println("============ No suitable message found. ============");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            counter++;
        }
        Assert.assertTrue(found);
        return found;
    }

    @Step("Checking if report comes to e-mail.")
    public boolean checkReportLong(String subject, String fileName) {
        String mail = RoleSwitcher.getCurrentUser().getEmail();
        String pass = RoleSwitcher.getCurrentUser().getPassword();
        String host = "pop.gmail.com";// change accordingly
        String mailStoreType = "pop3";
        long searchStartTime = System.currentTimeMillis() - 60000;
        boolean found = false;
        int counter = 0;
        System.out.println("============ Starting email check... =============");
        while (!found && counter < 10) {
            found = checkEmailForReport(host, mailStoreType, mail, pass, subject, searchStartTime, fileName);
            if (!found) {
                System.out.println("============ No suitable message found. ============");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            counter++;
        }
        Assert.assertTrue(found);
        return found;
    }

    @Step("Checking if notification comes to e-mail(Without assert).")
    public boolean checkLongNoAssert(String subject, String textForCheck) {
        String mail = RoleSwitcher.getCurrentUser().getEmail();
        String pass = RoleSwitcher.getCurrentUser().getPassword();
        String host = "pop.gmail.com";// change accordingly
        String mailStoreType = "pop3";
        long searchStartTime = System.currentTimeMillis() - 60000;
        boolean found = false;
        int counter = 0;
        System.out.println("============ Starting email check... =============");
        while (!found && counter < 15) {
            found = checkEmail(host, mailStoreType, mail, pass, subject, searchStartTime, textForCheck);
            if (!found) {
                System.out.println("============ No suitable message found. ============");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            counter++;
        }
        return found;
    }

}
