package com.lsx.main;

import com.lsx.util.AudioPlayer;
import com.lsx.util.MyPanel;
import com.lsx.util.Recorder;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Scanner;

public class MyTankGame extends JFrame {

    // 定义MyPanel
    final MyPanel mp;

    public static void main(String[] args) {
        System.out.println("是否读取存档？");
        Scanner scanner = new Scanner(System.in);
        if("yes".equals(scanner.next()))
            new MyTankGame(Recorder.init("/records.txt", 5));
        else
            new MyTankGame(null);
        new AudioPlayer(new File("src/main/resources/music.mp3")).start();
    }

    public MyTankGame(Recorder recorder) throws HeadlessException {
        mp = new MyPanel(recorder);
        this.setSize(1300, 750);
        this.add(mp); // 把画板(就是游戏的绘图区域)添加进画框
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(mp);
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                mp.close();
            }
        });
        new Thread(mp).start();
    }
}
