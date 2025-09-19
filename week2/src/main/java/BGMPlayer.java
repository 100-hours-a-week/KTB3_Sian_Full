// BGMPlay.java
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class BGMPlayer extends Thread {
    private static final String CAFE_BGM = "cafeBGM.wav";
    @Override
    public void run() {
        InputStream audioSrc = BGMPlayer.class.getResourceAsStream(CAFE_BGM);
        if (audioSrc == null) {
                throw new IllegalStateException("파일을 찾을 수 없음 : " + CAFE_BGM);
        }
        try (BufferedInputStream bufferedIn = new BufferedInputStream(audioSrc);
             AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn)) {

            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY); // 반복재생
            clip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

