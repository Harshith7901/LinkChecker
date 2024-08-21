package LinkChecker;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class LinkChecker {

    public static int checkLinkType(String link) throws IOException {
        URL url = new URL(link);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setInstanceFollowRedirects(true); // Follow redirects
        connection.setRequestProperty("User-Agent", "Mozilla/5.0"); // Set User-Agent

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Get the content type from the response header
            String contentType = connection.getContentType();
            System.out.println("Content-Type: " + contentType);

            if (contentType != null) {
                if (contentType.equals("application/pdf")) {
                    return 0; // It's a PDF
                } else if (contentType.contains("text/html")) {
                    return 1; // It's a web page
                }
            }
        } else {
            System.out.println("Failed to get a valid response. HTTP Response Code: " + responseCode);
        }
        return -1; // Unknown type or failed to determine type
    }

    public static void main(String[] args) {
        try {
            String link = "https://en.wikipedia.org/wiki/Google";
            int result = checkLinkType(link);
            if (result == 0) {
                System.out.println("It's a PDF file.");
            } else if (result == 1) {
                System.out.println("It's a web page.");
            } else {
                System.out.println("Unknown type.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
