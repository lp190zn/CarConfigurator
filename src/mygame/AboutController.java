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
import com.jme3.scene.Node;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 *  Trieda obsahujúca informácie, metódy atď. na ovládanie obrazovky "O hre".
 * @author Matej Pazdič
 */
public class AboutController extends AbstractAppState implements ScreenController {
    
    /**
     * Inštancia základného uzla pre menu hry.
     */
    public Nifty nifty;
    /**
     * Inštancia obrazovky "O hre".
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
     * Premenná zgrupujúca všetky 3D objekty.
     */
    public Node rn;
    
    /**
     * Konštruktor triedy ovládača okna "O hre".
     * @param am - parameter zgrupujíci všetky objekty v triede.
     * @param rn - parameter zgrupujúcu všetky 3D objekty.
     */
    public AboutController(AssetManager am, Node rn) { 
    this.am = am;
    this.rn = rn;
  }


    /**
     * Metóda na naviazanie ovládania obrazovky "O hre".
     * @param nifty - uzol zgrupujúci všetky obrazovky
     * @param screen - Objekt obrazovky "O hre"
     */
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
    }

    /**
     * Metóda volajúca sa na začiatku zobrazenia obrazovky "O hre".
     */
    public void onStartScreen() {
        aud1 = new AudioNode(am, "Sounds/Bang.ogg", false);
        aud1.setLooping(false);
        aud1.setVolume(0.5f);
        aud1.setReverbEnabled(false);
        rn.attachChild(aud1);
    }

    /**
     * Metóda volajúca sa na konci zobrazenia obrazovky "O hre".
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
