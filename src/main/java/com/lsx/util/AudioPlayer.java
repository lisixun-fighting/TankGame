package com.lsx.util;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.*;

public class AudioPlayer extends Thread {
    Player player;
    File music;
    //构造方法
    public AudioPlayer(File file) {
        this.music = file;
    }
    //重写run方法
    @Override
    public void run() {
        super.run();
        try {
            play();
        } catch (FileNotFoundException | JavaLayerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    //播放方法
    public void play() throws FileNotFoundException, JavaLayerException {
        BufferedInputStream buffer =
                new BufferedInputStream(new FileInputStream(music));
        player = new Player(buffer);
        player.play();
    }

    public static void main(String[] args) {
        AudioPlayer player = new AudioPlayer(new File("src/main/resources/music.mp3"));
        player.start();
    }
}
