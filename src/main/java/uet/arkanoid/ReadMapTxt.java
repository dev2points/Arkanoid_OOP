package uet.arkanoid;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.List;

public class ReadMapTxt{
    public static String getText(int level) {
        try {
            // Load from classpath (inside resources folder)
            Path path = Paths.get(ReadMapTxt.class
                                  .getResource("/assets/maps/map" + level + ".txt")
                                  .toURI());

            List<String> lines = Files.readAllLines(path);

            // Join all lines into one string (with newlines)
            StringBuilder content = new StringBuilder();
            for (String line : lines) {
                content.append(line).append("\n");
            }

            return content.toString();

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            return null; // return null if error
        }
    }

}
