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
import com.jme3.export.binary.BinaryExporter;
import com.jme3.scene.Node;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.ButtonClickedEvent;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.controls.TextFieldChangedEvent;
import de.lessvoid.nifty.controls.textfield.builder.TextFieldBuilder;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Trieda obsahujúca informácie, metódy atď. na ovládanie obrazovky "Uloženie hry".
 * @author Matej Pazdič
 */
public class SaveGameController extends AbstractAppState implements ScreenController {
    
    /**
     * Inštancia základného uzla pre menu hry.
     */
    public Nifty nifty;
    /**
     * Inštancia obrazovky "Uloženie hru".
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
     * Názov súboru pre uloženie hrou.
     */
    public String fileName;
    
    /**
     * Konštruktor triedy ovládača okna "Uloženie hry".
     * @param am - parameter zgrupujíci vśetky objekty v triede.
     * @param rn - parameter zgrupujúcu všetky 3D objekty.
     */
    public SaveGameController(AssetManager am, Node rn) { 
    this.am = am;
    this.rn = rn;
  }


    /**
     * Metóda na naviazanie ovládania obrazovky "Uloženie hry".
     * @param nifty - uzol zgrupujúci všetky obrazovky
     * @param screen - Objekt obrazovky "Uloženie hry"
     */
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
    }

    /**
     * Metóda volajúca sa na začiatku zobrazenia obrazovky "Uloženie hry".
     */
    public void onStartScreen() {
        aud1 = new AudioNode(am, "Sounds/Bang.ogg", false);
        aud1.setLooping(false);
        aud1.setVolume(0.5f);
        aud1.setReverbEnabled(false);
        rn.attachChild(aud1);
    }

    /**
     * Metóda volajúca sa na konci zobrazenia obrazovky "Uloženie hry".
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
     * Metóda vykonávajúca samotné uloženie hry, a vytvorenie súboru s informáciami
     * o uloženej hre.
     */
    public void saveGame() {
        playClick();
        if (Main.app.isGameInitialized == true) {
            String userHome = System.getProperty("user.home") + "\\" + "CarConfiguratorSAVEFILES";
            if (!new File(userHome).exists()) {
                new File(userHome).mkdir();
            }

            File file;
            if (fileName != null) {
                if (!fileName.endsWith(".sav")) {
                    file = new File(userHome + "\\" + fileName + ".sav");

                    if (file.exists()) {
                        nifty.gotoScreen("error_screen");
                    }

                } else {
                    file = new File(userHome + "\\" + fileName);

                    if (file.exists()) {
                        nifty.gotoScreen("error_screen");
                    }

                }

                if (!file.exists()) {
                    try {
                        Writer writer = new BufferedWriter(new FileWriter(file));
                        file.createNewFile();
                        System.out.println("SOS: " + Main.app.number);
                        writer.write(Integer.toString(Main.app.number));
                        writer.write("\n");

                        if (Main.app.number == 0) {
                            writer.write(Main.app.colorM.toString());
                            writer.write("\n");
                            writer.write(Integer.toString(Main.app.FBnumber));
                            writer.write("\n");
                            writer.write(Integer.toString(Main.app.BBnumber));
                            writer.write("\n");
                            writer.write(Integer.toString(Main.app.WHnumber));
                            writer.close();
                            nifty.gotoScreen("game_screen");
                        }

                        if (Main.app.number == 1) {
                            writer.write(Main.app.colorH.toString());
                            writer.write("\n");
                            writer.write(Integer.toString(Main.app.FBnumber));
                            writer.write("\n");
                            writer.write(Integer.toString(Main.app.BBnumber));
                            writer.write("\n");
                            writer.write(Integer.toString(Main.app.WHnumber));
                            writer.close();
                            nifty.gotoScreen("game_screen");
                        }

                        if (Main.app.number == 2) {
                            writer.write(Main.app.colorC.toString());
                            writer.write("\n");
                            writer.write(Integer.toString(Main.app.FBnumber));
                            writer.write("\n");
                            writer.write(Integer.toString(Main.app.BBnumber));
                            writer.write("\n");
                            writer.write(Integer.toString(Main.app.WHnumber));
                            writer.close();
                            nifty.gotoScreen("game_screen");
                        }
                        
                        


                    } catch (IOException ex) {
                        System.out.println("ERROR: Cannot create a new savefile!!!");
                        nifty.gotoScreen("error_screen");
                    }
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
  }
  
  /**
   * Pomocná metóda slúžiaca k zaregistrovaniu a uloženiu zmeny textu vo vstupnom poli.
   * @param id - názov vstupného poľa.
   * @param event - inštancia vykonanej, alebo vykonávanej udalosti.
   */
  @NiftyEventSubscriber(id = "Save")
public void onUserNameChanged(final String id, final TextFieldChangedEvent event) {
     TextField score = event.getTextFieldControl();
     fileName = event.getText();
}
    
}
