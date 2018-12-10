package br.ufes.informatica.smcss.auth.mail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

/**
 * Classe utilitária para ler arquivos de mensagens localizados.
 *
 * @author luciano
 *
 */
public class MailMessageLoader {

    protected static Pattern pattern = Pattern.compile("\\$\\{(.*?)\\}");
    private final Map<String, String> vars;
    private Locale locale;
    private String messageName;
    private int lineNumber;
    private Message message;

    public static void load(String messageName, Locale locale, Map<String, String> vars, Message message) throws MessagingException {
        new MailMessageLoader(messageName, locale, vars, message).run();
    }

    protected MailMessageLoader(String messageName, Locale locale, Map<String, String> vars, Message message) {
        this.messageName = messageName;
        this.locale = locale;
        this.vars = vars;
        this.message = message;
    }

    private void run() throws MessagingException {
        Multipart multipart = new MimeMultipart("alternative");

        MimeBodyPart textPart = new MimeBodyPart();
        String text = loadResource(messageName, "txt", locale, this::loadText);
        textPart.setText(text, "utf-8");
        multipart.addBodyPart(textPart);

        MimeBodyPart htmlPart = new MimeBodyPart();
        String htmlContent = loadResource(messageName, "html", locale, this::replaceVars);
        htmlPart.setContent(htmlContent, "text/html; charset=utf-8");
        multipart.addBodyPart(htmlPart);

        message.setContent(multipart);
    }

    protected String loadText(String line) {
        if (lineNumber > 1) {
            return line;
        }
        if (lineNumber++ == 0) {
            try {
                message.setSubject(this.replaceVars(line));
            } catch (MessagingException ex) {
                throw new RuntimeException(ex);
            }
        }
        return null;
    }

    protected String replaceVars(String text) {
        Matcher matcher = pattern.matcher(text);
        StringBuilder result = new StringBuilder();
        int lastMatch = 0;
        while (matcher.find()) {
            String value = vars.get(matcher.group(1));
            if (value != null) {
                result.append(text.substring(lastMatch, matcher.start()));
                result.append(value);
                lastMatch = matcher.end();
            }
        }
        result.append(text.substring(lastMatch, text.length()));
        return result.toString();
    }

    public static String loadResource(String baseName, String extension, Locale locale) {
        return loadResource(baseName, extension, locale, Function.identity());
    }

    public static String loadResource(String baseName, String extension, Locale locale, Function<String, String> consumer) {
        StringBuilder result = new StringBuilder();
        ResourceBundle.Control control = ResourceBundle.Control.getControl(ResourceBundle.Control.FORMAT_DEFAULT);
        List<Locale> candidateLocales = control.getCandidateLocales(baseName, locale);
        for (Locale specificLocale : candidateLocales) {
            String bundleName = control.toBundleName(baseName, specificLocale);
            String resourceName = control.toResourceName(bundleName, extension);
            URL url = MailMessageLoader.class.getResource(resourceName);
            if (url != null) {
                try (InputStream inputStream = url.openStream()) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        line = consumer.apply(line);
                        if (line != null) {
                            result.append(line);
                            result.append("\n");
                        }
                    }
                    break;
                } catch (IOException ex) {
                    throw new RuntimeException("Resource for message " + baseName + " has not been found.", ex);
                }
            }
        }
        return result.toString();
    }
}
