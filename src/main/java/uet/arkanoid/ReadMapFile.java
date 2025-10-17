package uet.arkanoid;

import javafx.scene.layout.Pane;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ReadMapFile {

    public static List<Brick> readMapFile(int level, Pane pane) {
        String fileName = "/assets/maps/map" + level + ".txt";
        // String fileName = "/src/main/resources/assets/maps/map1.txt";
        System.out.println(fileName);
        List<Brick> bricks = new ArrayList<>();

        try (InputStream is = ReadMapFile.class.getResourceAsStream(fileName)) {
            if (is == null) {
                throw new FileNotFoundException("Không tìm thấy file: " + fileName);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            // Dòng đầu là số lượng brick
            String firstLine = br.readLine();
            if (firstLine == null)
                return bricks;

            int brickCount = Integer.parseInt(firstLine.trim());

            // Đọc từng dòng: x y type
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty())
                    continue; // bỏ dòng trống
                String[] parts = line.trim().split("\\s+");
                if (parts.length < 3)
                    continue;

                double x = Double.parseDouble(parts[0]);
                double y = Double.parseDouble(parts[1]);
                int type = Integer.parseInt(parts[2]);

                Brick brick = new Brick(x, y, pane, type);
                bricks.add(brick);

                // Kiểm tra số lượng
                System.out.println("Đã tạo " + bricks.size() + " brick từ file map" + level +
                        ".txt");

            }

        } catch (Exception e) {
            System.err.println("Lỗi khi đọc file map: " + e.getMessage());
            e.printStackTrace();
        }
        String filePath = "src/main/resources/assets/maps/map1.txt";
        // List<Brick> bricks = new ArrayList<>();

        return bricks;
    }
}
