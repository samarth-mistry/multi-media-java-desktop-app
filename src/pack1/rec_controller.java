package pack1;

import java.io.*;
import javafx.concurrent.Task;
import javax.sound.sampled.*;

public class rec_controller extends Task<Void>
{

    static final long RECORD_TIME = 60000;  
    File wavFile = new File("RecordAudio.wav");
    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
    TargetDataLine line;
    @Override
    protected Void call() throws Exception
    {
        try {
            AudioFormat format = getAudioFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

            // checks if system supports the data line
            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line not supported");
                System.exit(0);
            }
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();   // start capturing

            System.out.println("Start capturing...");

            AudioInputStream ais = new AudioInputStream(line);

            System.out.println("Start recording...");            // start recording
            AudioSystem.write(ais, fileType, wavFile);
        }
        catch (LineUnavailableException | IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    AudioFormat getAudioFormat()
    {
        float sampleRate = 16000;
        int sampleSizeInBits = 8;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
                channels, signed, bigEndian);
        return format;
    }
    void finish()
    {
        line.stop();
        line.close();
        System.out.println("Finished");
    }

}