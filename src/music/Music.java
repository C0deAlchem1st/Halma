package music;

import java.applet.AudioClip;
import java.io.*;
import java.applet.Applet;
import java.awt.Frame;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import javax.swing.JFrame;
public class Music extends JFrame{
    public AudioClip audioClip;
    private File file;
    private URI uri;
    private URL url;

    public Music(String musicName){
        try {
            file = new File(musicName);
            uri = file.toURI();
            url = uri.toURL();
            audioClip = Applet.newAudioClip(url);
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }
    }

}// end of class