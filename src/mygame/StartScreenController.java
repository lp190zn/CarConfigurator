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
import com.jme3.input.InputManager;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import javax.swing.JFileChooser;

/**
 * Trieda obsahujúca informácie, metódy atď. na ovládanie základnej obrazovky
 * tzv. menu hry.
 * @author Matej Pazdič
 */
public class StartScreenController extends AbstractAppState implements ScreenController {
    
    /**
     * Inštancia základného uzla pre menu hry.
     */
    public Nifty nifty;
    /**
     * Inštancia obrazovky "Menu Hry".
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
     * Inštancia hudby v celej hre.
     */
    public AudioNode song1;
    /**
     * Premenná zgrupujíca všetky objekty v triede.
     */
    public AssetManager am;
    /**
     * Premenná zgrupujúca všetky 3D objekty.
     */
    public Node rn;
    /**
     * Premenná zgrupujúca všetké mapovanie ovládania v hre
     */
    public InputManager in;
    /**
     * Pomocná binárna premenná na zistenie, či sa pesnička prehrava alebo nie.
     */
    public boolean playing = false;
    
    /**
     * Konštruktor triedy ovládača základnej obrazovky.
     * @param am - parameter zgrupujíci vśetky objekty v triede.
     * @param rn - parameter zgrupujúcu všetky 3D objekty.
     * @param in - parameter zgrupujúci mapovanie ovládania samotnej hry. 
     */
    public StartScreenController(AssetManager am, Node rn, InputManager in) { 
    this.am = am;
    this.rn = rn;
    this.in = in;
  }


    /**
     * Metóda na naviazanie ovládania hlavnej obrazovky.
     * @param nifty - uzol zgrupujúci všetky obrazovky
     * @param screen - Objekt obrazovky "Menu hry"
     */
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
    }

    /**
     * Metóda volajúca sa na začiatku zobrazenia obrazovky "Menu hry".
     */
    public void onStartScreen() {
        Main.app.initKeys(false);
        
        aud1 = new AudioNode(am, "Sounds/Bang.ogg", false);
        aud1.setLooping(false);
        aud1.setVolume(0.7f);
        aud1.setReverbEnabled(false);
        rn.attachChild(aud1);
        
        song1 = new AudioNode(am, "Sounds/song1.ogg", false);
        song1.setLooping(true);
        song1.setVolume(0.6f);
        song1.setReverbEnabled(false);
        rn.attachChild(song1);
        playSong();
    }
    
    /**
     * Pomocná metóda na prehrávanie hudby v celej hre.
     */
    public void playSong(){
        if(playing == false){
            playing = true;
            song1.play();
            
        }
    }

    /**
     * Metóda volajúca sa na konci zobrazenia obrazovky "Menu hry".
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
     * Metóda pre tlačidlo "Spusť hru".
     */
    public void startGame(){
        playClick();
        nifty.gotoScreen("game_screen");
    }
    
    /**
     * Metóda pre tlačidlo "O hre".
     */
    public void aboutGame(){
        playClick();
        nifty.gotoScreen("about_screen");
    }
    
    /**
     * Metóda pre tlačidlo "Ukonč hru".
     */
    public void quitGame(){
        playClick();
        nifty.gotoScreen("exit_screen");
    }
    
    /**
     * Metóda pre tlačidlo "Načítaj hru".
     */
    public void loadGame(){
       playClick();
       nifty.gotoScreen("load_screen");
    }
    
    /**
     * Metóda pre tlačidlo "Ulož hru".
     */
    public void saveGame(){
        playClick();
        nifty.gotoScreen("save_screen");
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
  }
    
}
