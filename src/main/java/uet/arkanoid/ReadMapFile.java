package uet.arkanoid;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class ReadMapFile {

    public static String readMapFile(int level) {
        String fileName = "/assets/maps/map" + level + ".txt";

        try (InputStream is = ReadMapFile.class.getResourceAsStream(fileName);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            return br.lines().collect(Collectors.joining("\n"));

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
