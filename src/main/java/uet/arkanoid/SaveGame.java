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
        try {
            List<Pair<GameMap, User>> saved = loadGame();
            FileOutputStream fout = new FileOutputStream(processPath);
            if (saved == null)
                saved = new ArrayList<>();
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            saved.add(0, new Pair<GameMap, User>(gameController.getGameMap(), gameController.getUser()));
            oos.writeObject(saved);
            System.out.println("Saved process");
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
        File file = new File(processPath);

        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                return (List<Pair<GameMap, User>>) obj;
            } else {
                System.out.println("Dữ liệu không hợp lệ");
                return new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
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
            System.out.println("Save score successfully!");
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
