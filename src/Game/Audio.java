/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 *
 * @author Uriel
 */

import java.io.*;
import javax.sound.sampled.*;

public class Audio implements Runnable
{
        String fileLocation = "dubstep.wav";
        public void play()
        {
                Thread t = new Thread(this);
                t.start();
        }
    public void run ()
    {
        playSound(fileLocation);
    }
        private void playSound(String fileName)
        {
                File    soundFile = new File(fileName);
                AudioInputStream        audioInputStream = null;
                try
                {
                        audioInputStream = AudioSystem.getAudioInputStream(soundFile);
                }
                catch (Exception e)
                {
                        e.printStackTrace();
                }
                AudioFormat     audioFormat = audioInputStream.getFormat();
                SourceDataLine  line = null;
                DataLine.Info   info = new DataLine.Info(SourceDataLine.class,audioFormat);
                try
                {
                        line = (SourceDataLine) AudioSystem.getLine(info);
                        line.open(audioFormat);
                }
                catch (LineUnavailableException e)
                {
                        e.printStackTrace();
                }
                catch (Exception e)
                {
                        e.printStackTrace();
                }
                line.start();
                int     nBytesRead = 0;
                byte[]  abData = new byte[128000];
                while (nBytesRead != -1)
                {
                        try
                        {
                                nBytesRead = audioInputStream.read(abData, 0, abData.length);
                        }
                        catch (IOException e)
                        {
                                e.printStackTrace();
                        }
                        if (nBytesRead >= 0)
                        {
                                int     nBytesWritten = line.write(abData, 0, nBytesRead);
                        }
                }
                line.drain();
                line.close();
        }
}
