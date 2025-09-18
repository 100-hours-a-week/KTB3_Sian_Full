import com.sun.tools.javac.Main;

// BGMPlay.java
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class BGMPlayer extends Thread {

    @Override
    public void run() {
        try (InputStream audioSrc = BGMPlayer.class.getResourceAsStream("cafeBGM.wav");
             BufferedInputStream bufferedIn = new BufferedInputStream(audioSrc);
             AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn)) {

            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
