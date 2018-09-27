
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.LyricsHandler;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Lokesh Chandra
 */
public class MusicList {
    
    static int totalsong=0;
    
    static List <File> mp3Files;
    static List <String> Title;
    static List <String> Artists;
    static List <String> Genre;
    static List <String> Albumm;
    static List <String> Duration;   
       
    
    
    MusicList() throws IOException, FileNotFoundException, SAXException, TikaException, UnsupportedTagException, InvalidDataException
    {
        try {
            convertmp3();
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MusicList.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(totalsong==0)
            scan();      
    }
    public void convertmp3() throws IOException, InterruptedException
    {
            ProcessBuilder pb = new ProcessBuilder("python", "mp3converter.py");
            pb.directory(new File("C:\\Users\\Lokesh Chandra\\Documents\\NetBeansProjects\\MusicPlayer\\src\\"));
            Process p = pb.start();
            p.waitFor();
            p.destroy();
    }
    private static void scan() throws FileNotFoundException, IOException, SAXException, TikaException, UnsupportedTagException, InvalidDataException
    {
        File dir = new File("C:\\Users\\Lokesh Chandra\\Music\\Mymusic");
        int tno=0;
        mp3Files = new ArrayList<File>();
        Title = new ArrayList<String>();
        Artists = new ArrayList<String>();
        Genre = new ArrayList<String>();
        Albumm = new ArrayList<String>();
        Duration = new ArrayList<String>();
        
       
        Welcome wel = new Welcome();
        wel.setVisible(true);
    
        for(File file : dir.listFiles())
        {
            if(file.getName().endsWith("mp3"))
            {
                   mp3Files.add(file);
            }
        }
        ContentHandler handler = new DefaultHandler();
        Metadata metadata = new Metadata();
        Parser parser = new Mp3Parser();
        ParseContext parseCtx = new ParseContext();
        
        for(File file : mp3Files){
           // System.out.println(file.getAbsoluteFile());
            
            InputStream input = new FileInputStream(file);
        //    System.out.println(file.getName()); 
            parser.parse(input, handler, metadata, parseCtx);
            
        /*    LyricsHandler lyrics = new LyricsHandler(input,handler);  
            while(lyrics.hasLyrics()) {  
            System.out.println(lyrics.toString());  
            }  
       */
            /*    System.out.println("-----------------------------------");
            System.out.println("Title : "+metadata.get("title"));
            System.out.println("Artists : "+metadata.get("xmpDM:artist"));
            System.out.println("Genre : "+metadata.get("xmpDM:genre"));
            System.out.println("Album : "+metadata.get("xmpDM:album"));
          */
          //      System.out.println("Rating : "+metadata.get("xmpDM:audioSampleRate"));
             
              Title.add(metadata.get("title"));
              Artists.add(metadata.get("xmpDM:artist"));
              Genre.add(metadata.get("xmpDM:genre"));
              Albumm.add(metadata.get("xmpDM:album"));
              Duration.add(metadata.get("xmpDM:duration"));
              
              totalsong++;
              
            try {
                wel.perload.setText(Integer.toString(totalsong));
                Thread.sleep(1);
                //  getLength(file);
            } catch (InterruptedException ex) {
                Logger.getLogger(MusicList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        wel.setVisible(false);
        MusicInterface mi = new MusicInterface();
        mi.setVisible(true);
    }
    
    
    
   
    public static void main(String args[]) throws IOException, FileNotFoundException, SAXException, TikaException, UnsupportedTagException, InvalidDataException
    {
        new MusicList();
    }
}
