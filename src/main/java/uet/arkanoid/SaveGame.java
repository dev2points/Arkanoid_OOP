package uet.arkanoid;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javafx.application.Platform;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * Lưu tiến trình chơi trước khi thoát Game.
 */
public class SaveGame {
    private static final String processPath = "data/process.txt";
    private static final String rankingPath = "data/ranking.txt";
    // process sẽ được tải vào memory khi cần và lưu lại
    private static List<Pair<GameMap, User>> processCache = new ArrayList<>();

    static {
        new File("data").mkdirs();
        // Load process vào cache khi khởi động ứng dụng
        processCache = loadGameProcessFromFile();
    }

    // Phương thức để lưu game, sẽ được gọi trong một Task
    public static void saveGame(GameMap gameMap, User user) throws IOException {
        // Cần tạo một bản sao của GameMap và User để tránh thay đổi trong quá trình lưu
        // Hoặc đảm bảo rằng GameMap và User là immutable hoặc được đồng bộ hóa
        // Tuy nhiên, đối với game Arkanoid, chúng ta giả định GameMap và User là
        // snapshot tại thời điểm lưu.

        // Cập nhật cache trước khi ghi file
        if (processCache.isEmpty()) {
            processCache = loadGameProcessFromFile(); // Tải lại nếu chưa có
        }
        // Thêm tiến trình mới vào đầu danh sách (tiến trình mới nhất)
        processCache.add(0, new Pair<>(gameMap, user));

        // Giữ lại tối đa 5 tiến trình (hoặc số lượng tùy ý)
        if (processCache.size() > 5) {
            processCache = processCache.subList(0, 5);
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(processPath))) {
            oos.writeObject(processCache);
            System.out.println("Saved process");
        }
    }

    public static void processChoosed(int index) {
        if (!processCache.isEmpty() && index >= 0 && index < processCache.size()) {
            processCache.remove(index);
            // Ghi lại file sau khi xóa
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(processPath))) {
                oos.writeObject(processCache);
                System.out.println("Process removed and file updated.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Tải danh sách các Pair<GameMap, User> từ file, chạy trên luồng nền.
     *
     * @return List<Pair<GameMap, User>>
     */
    public static List<Pair<GameMap, User>> loadGameProcessFromFile() {
        File file = new File(processPath);

        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                // Kiểm tra kiểu an toàn hơn
                List<?> rawList = (List<?>) obj;
                List<Pair<GameMap, User>> loadedProcess = new ArrayList<>();
                for (Object item : rawList) {
                    if (item instanceof Pair<?, ?> pair) {
                        if (pair.getKey() instanceof GameMap && pair.getValue() instanceof User) {
                            loadedProcess.add((Pair<GameMap, User>) item);
                        } else {
                            System.err.println("Dữ liệu không hợp lệ trong Pair: " + item);
                        }
                    } else {
                        System.err.println("Dữ liệu không hợp lệ trong List: " + item);
                    }
                }
                return loadedProcess;
            } else {
                System.err.println("Dữ liệu không hợp lệ: Không phải List.");
                return new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Phương thức để lấy danh sách game đã lưu từ cache
    public static List<Pair<GameMap, User>> getSavedGames() {
        return processCache;
    }

    // Phương thức để lưu điểm, sẽ được gọi trong một Task
    public static void saveScore(String name, int score) throws IOException {
        List<Pair<String, Integer>> scores = loadRanking();
        scores.add(new Pair<String, Integer>(name, score));
        // Sắp xếp giảm dần theo score
        scores.sort((a, b) -> Integer.compare(b.getValue(), a.getValue()));

        // Giữ lại tối đa 10 người
        if (scores.size() > 10)
            scores = scores.subList(0, 10);
        try (PrintWriter pw = new PrintWriter(new FileWriter(rankingPath))) {
            for (Pair<String, Integer> p : scores) {
                pw.println(p.getKey() + "|" + p.getValue());
            }
            System.out.println("Save score successfully!");
        }
    }

    public static List<Pair<String, Integer>> loadRanking() {
        List<Pair<String, Integer>> scores = new ArrayList<>();
        File file = new File(rankingPath);
        if (!file.exists())
            return scores;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Phân cách name và score bằng '|'
                String[] parts = line.split("\\|");
                if (parts.length == 2) { // Đảm bảo đúng định dạng
                    String name = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    scores.add(new Pair<>(name, score));
                } else {
                    System.err.println("Dòng xếp hạng không đúng định dạng: " + line);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return scores;
    }

}