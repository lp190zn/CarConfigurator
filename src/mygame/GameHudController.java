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
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 * Trieda obsahujúca informácie, metódy atď. na ovládanie tlačidiel v hre.
 * @author Matej Pazdič
 */
public class GameHudController extends AbstractAppState implements ScreenController {
    
    /**
     * Inštancia základného uzla pre menu hry.
     */
    public Nifty nifty;
    /**
     * Inštancia obrazovky "Hlavné okno".
     */
    public Screen screen;
    /**
     * Inštancia hry.
     */
    public SimpleApplication appi;
    
    /**
     * Konštruktor triedy ovládača okna v hre.
     * @param data - Správa odovzdávaná konštruktoru.
     */
    public GameHudController(String data) { 
  }


    /**
     * Metóda na naviazanie ovládania obrazovky v hre.
     * @param nifty - uzol zgrupujúci všetky obrazovky
     * @param screen - Objekt obrazovky "Hlavné okno"
     */
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
    }

    /**
     * Metóda volajúca sa na začiatku zobrazenia obrazovky v hre.
     */
    public void onStartScreen() {
        Main.app.initGame();
    }

    /**
     * Metóda volajúca sa na konci zobrazenia obrazovky v hre.
     */
    public void onEndScreen() {

    }
    
    /**
     * Metóda ovládajúca tlačidlo pre zmenu modelu auta do prava.
     */
    public void changeModelForward(){
        
        Main.app.changeModelForward();
    }
    
    /**
     * Metóda ovládajúca tlačidlo pre zmenu modelu auta do ľava.
     */
    public void changeModelBackward(){
        
        Main.app.changeModelBackward();
    }
    
    /**
     * Metóda ovládajúca tlačidlo pre zmenu farby modelu auta do prava.
     */
    public void changeCarColorForward(){
        Main.app.changeCarColorForward();
    }
    
    /**
     * Metóda ovládajúca tlačidlo pre zmenu farby modelu auta do ľava.
     */
    public void changeCarColorBackward(){
        Main.app.changeCarColorBackward();
    }
    
    /**
     * Metóda ovládajúca tlačidlo pre zmenu predného nárazníka modelu auta do prava.
     */
    public void changeCarFrontBumperForward(){
        Main.app.changeFrontBumperForward();
    }
    
    /**
     * Metóda ovládajúca tlačidlo pre zmenu predného nárazníka modelu auta do ľava.
     */
    public void changeCarFrontBumperBackward(){
        Main.app.changeFrontBumperBackward();
    }
    
    /**
     * Metóda ovládajúca tlačidlo pre zmenu zadného nárazníka modelu auta do prava.
     */
    public void changeCarBackBumperForward(){
        Main.app.changeBackBumperForward();
    }
    
    /**
     * Metóda ovládajúca tlačidlo pre zmenu zadného nárazníka modelu auta do ľava.
     */
    public void changeCarBackBumperBackward(){
        Main.app.changeBackBumperBackward();
    }
    
    /**
     * Metóda ovládajúca tlačidlo pre zmenu kolies modelu auta do prava.
     */
    public void changeWheelsForward(){
        Main.app.changeWheelsForward();
    }
    
    /**
     * Metóda ovládajúca tlačidlo pre zmenu kolies modelu auta do ľava.
     */
    public void changeWheelsBackward(){
        Main.app.changeWheelsBackward();
    }
    
    /**
     * TOTO TU NEBUDE !!!
     */
    //public void change(){
    //    Element niftyElement = nifty.getCurrentScreen().findElementByName("2CenterLabel");
    //    
    //    niftyElement.getRenderer(TextRenderer.class).setText("124");
    //}
    
    
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
    
}
