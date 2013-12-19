 /*
 * Mená autorov: Ľubomír Petrus, Gabriel Mikloš, Matej Pazdič
 * Dátum: 20.01.2012
 * Číslo verzie: 1.2
 * Názov programu: Car Configurator
 */
package mygame;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.export.binary.BinaryImporter;
import com.jme3.scene.Node;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.controls.TextFieldChangedEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import mygame.Main.CarColorCarrera;
import mygame.Main.CarColorHummer;
import mygame.Main.CarColorMercedes;

/**
 * Trieda obsahujúca informácie, metódy atď. na ovládanie obrazovky "Načítaj hru".
 * @author Matej Pazdič
 */
public class LoadGameController extends AbstractAppState implements ScreenController {
    
    /**
     * Inštancia základného uzla pre menu hry.
     */
    public Nifty nifty;
    /**
     * Inštancia obrazovky "Načítaj hru".
     */
    public Screen screen;
    /**
     * Inštancia hry.
     */
    public SimpleApplication appi;
    /**
     * Inštancia zvukového efektu kliknutia na tlačidlo.
     */
    public AudioNode aud1;
    /**
     * Premenná zgrupujíca všetky objekty v triede.
     */
    public AssetManager am;
    /**
     * Premenná zgrupujíca všetky 3D objekty.
     */
    public Node rn;
    /**
     * Názov súboru s uloženou hrou.
     */
    public String fileName;
    
    /**
     * Konštruktor triedy ovládača okna "Načítaj hru".
     * @param am - parameter zgrupujíci vśetky objekty v triede.
     * @param rn - parameter zgrupujúcu všetky 3D objekty.
     */
    public LoadGameController(AssetManager am, Node rn) { 
    this.am = am;
    this.rn = rn;
  }


    /**
     * Metóda na naviazanie ovládania obrazovky "Načítaj hru".
     * @param nifty - uzol zgrupujúci všetky obrazovky
     * @param screen - Objekt obrazovky "Načítaj hru"
     */
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
    }

    /**
     * Metóda volajúca sa na začiatku zobrazenia obrazovky "Načítaj hru".
     */
    public void onStartScreen() {
        aud1 = new AudioNode(am, "Sounds/Bang.ogg", false);
        aud1.setLooping(false);
        aud1.setVolume(0.5f);
        aud1.setReverbEnabled(false);
        rn.attachChild(aud1);
    }

    /**
     * Metóda volajúca sa na konci zobrazenia obrazovky "Načítaj hru".
     */
    public void onEndScreen() {

    }
    
    /**
     * Pomocná metóda ktorá prehráva zvuk pri stlačení ľubovoľného tlačidla.
     */
    public void playClick(){
        aud1.playInstance();
    }
    
    /**
     * Metóda pre tlačidlo náhratu na hlavnú obrazovku.
     */
    public void getBack(){
        playClick();
        nifty.gotoScreen("start_screen");
    }
    
    /**
     * Metóda vykonávajúca samotné uloženie hrz.
     */
    public void LoadGame(){
        playClick();
        
        String userHome = System.getProperty("user.home")+"\\CarConfiguratorSAVEFILES\\";
        BinaryImporter importer = BinaryImporter.getInstance();
        importer.setAssetManager(am);
        File file;
        if(fileName != null){
            if(!fileName.endsWith(".sav")){
                file = new File(userHome + fileName + ".sav");
                
                if(!file.exists()){
                    nifty.gotoScreen("error_screen");
                }
                
            }else{
                file = new File(userHome + fileName);
                
                if(!file.exists()){
                    nifty.gotoScreen("error_screen");
                }
                
            }
            
            if(file.exists()){
                try {
                    FileInputStream fstream = new FileInputStream(file);
                    DataInputStream in = new DataInputStream(fstream);
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String line = br.readLine();
                    if(line != null){
                        if(Main.app.isGameInitialized == false){
                            Main.app.initGame();
                        }
                        int number = Integer.valueOf(line);
                        Main.app.number = number;
                        line = br.readLine();
                        if(number == 0){
                            Main.app.colorM = CarColorMercedes.valueOf(line);
                            System.out.println("SOS: "+Main.app.colorM);
                            Main.app.FBnumber = Integer.parseInt(br.readLine());
                            System.out.println("SOS: "+Main.app.FBnumber);
                            Main.app.BBnumber = Integer.parseInt(br.readLine());
                            System.out.println("SOS: "+Main.app.BBnumber);
                            Main.app.WHnumber = Integer.parseInt(br.readLine());
                            System.out.println("SOS: "+Main.app.WHnumber);
                        }
                        if(number == 1){
                            Main.app.colorH = CarColorHummer.valueOf(line);
                            Main.app.FBnumber = Integer.parseInt(br.readLine());
                            Main.app.BBnumber = Integer.parseInt(br.readLine());
                            Main.app.WHnumber = Integer.parseInt(br.readLine());
                        }
                        if(number == 2){
                            Main.app.colorC = CarColorCarrera.valueOf(line);
                            Main.app.FBnumber = Integer.parseInt(br.readLine());
                            Main.app.BBnumber = Integer.parseInt(br.readLine());
                            Main.app.WHnumber = Integer.parseInt(br.readLine());
                        }
                        Main.app.refreshCar();
                        nifty.gotoScreen("game_screen");
                        
                    }
                } catch (IOException ex) {
                    System.out.println("ERROR: Cannot read from file!!!");
                    nifty.gotoScreen("error_screen");
                }
            }
            
        }
        
    }
    
    
    /**
     * Metóda na inicializovanie obrazovky.
     * @param stateManager - objekt pre stav hry
     * @param app - Objekt hry
     */
    @Override
  public void initialize(AppStateManager stateManager, Application app) {
    super.initialize(stateManager, app);
    this.appi=(SimpleApplication)app;
  }
 
    /**
     * Metóda volajúca sa v určitom časovom intervale, kvôli obnove obrazovky.
     */
    @Override
  public void update(float tpf) { 
    /** jME update loop! */ 
  }
  
  /**
   * Pomocná metóda slúžiaca k zaregistrovaniu a uloženiu zmeny textu vo vstupnom poli.
   * @param id - názov vstupného poľa.
   * @param event - inštancia vykonanej, alebo vykonávanej udalosti.
   */
  @NiftyEventSubscriber(id = "Load")
public void onUserNameChanged(final String id, final TextFieldChangedEvent event) {
     TextField score = event.getTextFieldControl();
     fileName = event.getText();
}
    
}
