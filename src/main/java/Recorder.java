import java.io.*;
import java.util.*;

public class Recorder {

    private int eliminateTanks;
    private int hp;
    private int[] heroLocate;
    private List<int[]> enemyLocate;

    private String fileName = "E://resources/records.txt";

    public Recorder(String fileName, int enemyTanks) {
        if(fileName != null && fileName.length() > 0)
            this.fileName = fileName;
        eliminateTanks = 0;
        hp = 3;
        heroLocate = new int[] {500, 400};
        enemyLocate = new LinkedList<>();
        for (int i = 0; i < enemyTanks; i++) {
            enemyLocate.add(new int[]{100*(i+3), 10});
        }
    }

    public void write() {

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write("eliminateTanks: "+ eliminateTanks);
            bw.newLine();
            bw.write("heroHp: " + hp);
            bw.newLine();
            bw.write(heroLocate[0] + " " + heroLocate[1]);
            bw.newLine();
            for (int[] locates : enemyLocate) {
                bw.write(locates[0] + " " + locates[1]);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("存储失败");
            e.printStackTrace();
        }
    }

    public static Recorder init(String fileName, int enemyTanks) {
        Recorder recorder = new Recorder(fileName,enemyTanks);

        try(BufferedReader br = new BufferedReader(new FileReader(recorder.fileName))) {
            String s = br.readLine();
            recorder.setEliminateTanks(Integer.parseInt(s.split(": ")[1]));

            s = br.readLine();
            recorder.setHp(Integer.parseInt(s.split(": ")[1]));

            s = br.readLine();
            recorder.setHeroLocate(new int[]{Integer.parseInt(s.split(" ")[0]), Integer.parseInt(s.split(" ")[1])});

            List<int[]> enemyLocates = new LinkedList<>();
            while((s = br.readLine()) != null) {
                enemyLocates.add(new int[]{Integer.parseInt(s.split(" ")[0]), Integer.parseInt(s.split(" ")[1])});
            }
            recorder.setEnemyLocate(enemyLocates);

        } catch (IOException e) {
            System.out.println("读取失败");
            e.printStackTrace();
        }
        if(enemyTanks - recorder.getEnemyLocate().size() != recorder.eliminateTanks)
            throw new RuntimeException("地方坦克数量不匹配，请重新加载！");
        return recorder;
    }

    public void goal() {
        eliminateTanks++;
    }

    public void hurt() {
        hp--;
    }

    public int[] getHeroLocate() {
        return heroLocate;
    }

    public void setHeroLocate(int[] heroLocate) {
        this.heroLocate = heroLocate;
    }

    public List<int[]> getEnemyLocate() {
        return enemyLocate;
    }

    public void setEnemyLocate(List<int[]> enemyLocate) {
        this.enemyLocate = enemyLocate;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getEliminateTanks() {
        return eliminateTanks;
    }

    public void setEliminateTanks(int eliminateTanks) {
        this.eliminateTanks = eliminateTanks;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setLocations(List<Tank> enemies, Tank hero) {
        enemyLocate = new LinkedList<>();
        for (Tank enemy : enemies) {
            if(enemy.getState() != 1) continue;
            enemyLocate.add(new int[]{enemy.getX(), enemy.getY()});
        }
        heroLocate = new int[]{hero.getX(), hero.getY()};
    }

    public static void main(String[] args) {
        Recorder init = Recorder.init("E://resources/records.txt",5);
        System.out.println(init.eliminateTanks);
        System.out.println(init.hp);
        System.out.println(Arrays.toString(init.heroLocate));
        for (int[] ints : init.enemyLocate) {
            System.out.println(Arrays.toString(ints));
        }
    }
}
