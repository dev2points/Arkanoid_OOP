package uet.arkanoid;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

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
    static {
        new File("data").mkdirs();
    }

    public static void saveGame(GameController gameController) {
        User user = gameController.getUser();
        GameMap gameMap = gameController.getGameMap();
        try {
            FileOutputStream fout = new FileOutputStream(processPath);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            // Ghi Map trước User
            oos.writeObject(gameMap);
            oos.writeObject(user);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Trả về Pair<Map, User>.
     * 
     * @return Pair(GameMap, User)
     */
    public static List<Pair<GameMap, User>> loadGame() {
        try {
            FileInputStream fout = new FileInputStream(processPath);
            ObjectInputStream ois = new ObjectInputStream(fout);
            List<Pair<GameMap, User>> data = (List<Pair<GameMap, User>>) ois.readObject();
            ois.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static void saveScore(String name, int score) {
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
        } catch (IOException e) {
            e.printStackTrace();
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
                String name = parts[0];
                int score = Integer.parseInt(parts[1]);
                scores.add(new Pair<>(name, score));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scores;
    }
}
