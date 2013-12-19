 /*
 * Mená autorov: Ľubomír Petrus, Gabriel Mikloš, Matej Pazdič
 * Dátum: 20.01.2012
 * Číslo verzie: 1.2
 * Názov programu: Car Configurator
 */
package mygame;

import com.jme3.light.PointLight;
import com.jme3.math.Quaternion;
import com.jme3.scene.Spatial;
import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.system.AppSettings;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ImageBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.controls.label.builder.LabelBuilder;
import de.lessvoid.nifty.controls.textfield.builder.TextFieldBuilder;

/**
 * Hlavná (spustiteľná) trieda hry, obsahujúca všetky hlavné inicializačné funkcie.
 * @author Matej Pazdič
 */
public class Main extends SimpleApplication {

    /**
     * Inštancia celého programu hry.
     */
    public static Main app;
    /**
     * Pomocná binárna premenná, zisťujúca stav inicializácie celej 3D scény.
     */
    public boolean isGameInitialized = false;
    /**
     * Pomocná premenná zistujúca či je aktívna 3D scéna alebo je aktívne menu, alebo jeho časť.
     */
    public int isInGame = 1;
    
    /**
     * Inštancia vstupného textového poľa pre zadávanie názvu pre uloženie hry.
     */
    public TextFieldBuilder textField = new TextFieldBuilder("Save", "Enter a filename...") {{
        valignCenter();
        alignCenter();
        height("50%");
        width("50%");
    }};
    
     /**
      * Inštancia vstupného textového poľa pre zadávanie názvu pre načítanie hry.
      */
     public TextFieldBuilder textField1 = new TextFieldBuilder("Load", "Enter a filename...") {{
        valignCenter();
        alignCenter();
        height("50%");
        width("50%");
    }};
    
     /**
      * Pomocná binárna premenná slúžiaca na kontrolu zapnutia kamery v hre.
      */
     public boolean isCamera = false;
    
    /**
     * Pomocná premenná pre zistenie aktuálneho modelu auta.
     */
    public int number = 0;
    /**
     * Pomocná premenná pre zistenie aktuálneho modelu predného narazníku auta.
     */
    public int FBnumber = 0;
    /**
     * Pomocná premenná pre zistenie aktuálneho modelu zadného narazníku auta.
     */
    public int BBnumber = 0;
    /**
     * Pomocná premenná pre zistenie aktuálneho modelu kolies auta.
     */
    public int WHnumber = 0;
    /**
     * Inštancia základného uzla pre menu hry.
     */
    public Nifty nifty;
    
    /**
     * Enumeračná premenná na zistenie dostupných farieb pre model auta Mercedes.
     */
    public enum CarColorMercedes {
        /**
         * Sivá farba
         */
        SILVER,
        /**
         * Modrá farba
         */
        BLUE,
        /**
         * Zlatá farba
         */
        GOLD,
        /**
         * Azúrová farba
         */
        AZURE;
    }
    
    /**
     * Enumeračná premenná na zistenie dostupných farieb pre model auta Hummer.
     */
    public enum CarColorHummer {
        /**
         * Strieborná farba
         */
        SILVER,
        /**
         * Červená farba
         */
        RED,
        /**
         * Čierna farba
         */
        BLACK,
        /**
         * Biela farba
         */
        WHITE,
        /**
         * Modrá farba
         */
        BLUE,
        /**
         * Zelená farba
         */
        GREEN,
        /**
         * Žltá farba
         */
        YELLOW,
        /**
         * Sivá farba
         */
        GREY;
    }
    
    /**
     * Enumeračná premenná na zistenie dostupných farieb pre model auta Porsche.
     */
    public enum CarColorCarrera {
        /**
         * Sivá farba
         */
        SILVER,
        /**
         * Modrá farba
         */
        BLUE,
        /**
         * Červená farba
         */
        RED,
        /**
         * Žltá farba
         */
        YELLOW;
    }
    
    /**
     * Pomocná premenná pre zistenie aktuálnej farby modelu auta Hummer.
     */
    public CarColorHummer colorH = CarColorHummer.SILVER;
    /**
     * Pomocná premenná pre zistenie aktuálnej farby modelu auta Mercedes.
     */
    public CarColorMercedes colorM = CarColorMercedes.SILVER;
    /**
     * Pomocná premenná pre zistenie aktuálnej farby modelu auta Porsche.
     */
    public CarColorCarrera colorC = CarColorCarrera.SILVER;
    

    /**
     * Hlavná metóda volajúca sa ihneď po spustení hry.
     * @param args
     */
    public static void main(String[] args) {
        app = new Main();
        AppSettings set = new AppSettings(false);

        set.setTitle("Car Configurator");
        set.setSettingsDialogImage("/StartScreen/StartScreen.png");
        set.setFullscreen(true);
        app.setSettings(set);

        app.start();
    }
    
    
    
    /**
     * 
     */
    public Boolean isRunning = true;
    /**
     * Inštancia pre načítanie modelu pontónu pod auto.
     */
    public Spatial ponton; 
    /**
     * Inštancia pre načítanie modelu auta.
     */
    public Spatial auto;
    /**
     * Inštancia pre načítanie modelu preného nárazníka pre auto.
     */
    public Spatial prednyNaraznik;
    /**
     * Inštancia pre načítanie modelu zadného nárazníka pre auto.
     */
    public Spatial zadnyNaraznik;
    /**
     * Inštancia pre načítanie modelu kolies pre auto.
     */
    public Spatial kolesa;
    /**
     * Inštancia svetla osvetľujúceho celý model auta.
     */
    public PointLight lamp_light0;
    /**
     * Inštancia svetla osvetľujúceho celý model auta.
     */
    public PointLight lamp_light1;
    /**
     * Inštancia svetla osvetľujúceho celý model auta.
     */
    public PointLight lamp_light2;
    /**
     * Inštancia svetla osvetľujúceho celý model auta.
     */
    public PointLight lamp_light3;
    /**
     * Inštancia svetla osvetľujúceho celú garáž.
     */
    public PointLight stredne;

    /**
     * Metóda slúžiaca na inicializáciu začiatočných prvkov hry, ako sú menu, kamera atď.
     */
    @Override
    public void simpleInitApp() {

        inputManager.clearMappings();

        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(
                assetManager, inputManager, audioRenderer, guiViewPort);
        nifty = niftyDisplay.getNifty();
        guiViewPort.addProcessor(niftyDisplay);
        flyCam.setDragToRotate(true);

        initMenu();

        app.setDisplayFps(false);
        app.setDisplayStatView(false);
        nifty.gotoScreen("start_screen");

    }
    
    /**
     * Metóda slúžiaca na prvé inicializovanie 3D scény, ako aj na obnovenie už
     * inicializovanej 3D scény.
     */
    public void initGame(){
        
        if(isGameInitialized == false){
            isGameInitialized = true;
            
            
        auto = assetManager.loadModel("Models/CLK/zaklad sivy.j3o");                 
        prednyNaraznik = assetManager.loadModel("Models/CLK/predny naraznik/sivy 0.j3o");
        zadnyNaraznik = assetManager.loadModel("Models/CLK/zadny naraznik/sivy 0.j3o");
        kolesa = assetManager.loadModel("Models/CLK/kola/kola 0.j3o");
       
            
        Spatial garaz = assetManager.loadModel("Models/miestnostF.j3o");
        ponton = assetManager.loadModel("Models/PontonFFFF.j3o");
        Spatial strop = assetManager.loadModel("Models/strop1 skuska 12.j3o");

        // pridanie modelov

        rootNode.attachChild(auto);
        rootNode.attachChild(prednyNaraznik);
        rootNode.attachChild(zadnyNaraznik);
        rootNode.attachChild(kolesa);
        rootNode.attachChild(garaz);
        rootNode.attachChild(ponton);
        rootNode.attachChild(strop);

        auto.setLocalTranslation(0.0f, 0.63f, 0.0f);


        /// VYTVORENIE KAMERY

        cam.setLocation(new Vector3f(0.0f, 2f, -8f));
        cam.lookAt(new Vector3f(0f, 0.0f, 0f), new Vector3f(0f, 1f, 0f));

        // SVETLA

        lamp_light0 = new PointLight();
        lamp_light0.setColor(ColorRGBA.White.mult(1.5f));
        lamp_light0.setPosition(new Vector3f(3f, 1.5f, 6f));

        auto.addLight(lamp_light0);

        lamp_light1 = new PointLight();
        lamp_light1.setColor(ColorRGBA.White.mult(1.5f));
        lamp_light1.setPosition(new Vector3f(-5f, 1.5f, 6f));

        auto.addLight(lamp_light1);

        stredne = new PointLight();
        stredne.setColor(ColorRGBA.White.mult(1.5f));
        stredne.setPosition(new Vector3f(-0.8f, 3f, 1));

        auto.addLight(stredne);
        garaz.addLight(stredne);

        lamp_light2 = new PointLight();
        lamp_light2.setColor(ColorRGBA.White.mult(1.5f));
        lamp_light2.setPosition(new Vector3f(-3f, 1.5f, -2.8f));

        auto.addLight(lamp_light2);

        lamp_light3 = new PointLight();
        lamp_light3.setColor(ColorRGBA.White.mult(1.5f));
        lamp_light3.setPosition(new Vector3f(2.2f, 1.5f, -2.8f));

        auto.addLight(lamp_light3);

        //SVETLO PONTON 

        PointLight pontonlight = new PointLight();
        pontonlight.setColor(ColorRGBA.White.mult(1.0f));
        pontonlight.setPosition(new Vector3f(-0.8f, 4f, 1f));

        ponton.addLight(pontonlight);

        //GUI SETUP

        guiNode.detachAllChildren();
        flyCam.setMoveSpeed(5f);

        initKeys(true);
        
        preparecar(prednyNaraznik);
        preparecar(zadnyNaraznik);
        preparecar(kolesa);
        
        }
        else{
            initKeys(true);
        }
    }
    
    /**
     * Inicializácia a vytvorenie štruktúry menu, a definovanie metód správania obrazoviek menu.
     */
    public void initMenu(){
        //nastavenie defaultného správania a štýlu obrazoviek.
    nifty.loadStyleFile("nifty-default-styles.xml");
    nifty.loadControlFile("nifty-default-controls.xml");
        
        //Vytvorenie a definícia hlavnej obrazovky, tzv. menu obrazovky.
    
    nifty.addScreen("start_screen", new ScreenBuilder("start_screen") {{
        controller(new StartScreenController(assetManager, rootNode, inputManager));
        
        layer(new LayerBuilder("pozadie") {{
            childLayoutCenter();
            backgroundColor("#0000");
            
            image(new ImageBuilder() {{
                //filename("Nifty/START_screen_WALLPAPER_R.png");
                filename("Nifty/obraz.jpg");
            }});
            
        }});
        
        layer(new LayerBuilder("popredie") {{
            childLayoutVertical();
            backgroundColor("#0000");
            
            panel(new PanelBuilder("nazovPane"){{
                childLayoutCenter();
                alignCenter();
                height("10%");
                width("100%");
                
                text(new TextBuilder() {{
                    text("Car Configurator");
                    font("Nifty/Skuska.fnt");
                    height("100%");
                    width("100%");
                }});
                
            }});
            
            panel(new PanelBuilder("othersPane"){{
                childLayoutHorizontal();
                alignCenter();
                //backgroundColor("#812f");
                height("90%");
                width("100%");
                
                panel(new PanelBuilder("gapPane"){{
                    childLayoutVertical();
                    alignCenter();
                    //backgroundColor("#712f");
                    height("100%");
                    width("50%");
                }});
                
                panel(new PanelBuilder("volbyPane"){{
                    childLayoutVertical();
                    alignCenter();
                    //backgroundColor("#7789");
                    height("100%");
                    width("50%");
                    
                    panel(new PanelBuilder("Gap2Pane"){{
                    childLayoutCenter();
                    alignCenter();
                    //backgroundColor("#1ff1");
                    height("10%");
                    width("100%");

                    
                }});
                    
                    panel(new PanelBuilder("NewGamePAne"){{
                    childLayoutCenter();
                    alignCenter();
                    //backgroundColor("#ffff");
                    height("7%");
                    width("100%");
                    
                    control(new ButtonBuilder("NewGameButton", "Start a new game") {{
                      alignCenter();
                      valignCenter();
                      height("50%");
                      width("50%");
                      
                      visibleToMouse(true);
                      interactOnClick("startGame()");
                    }});
                }});
                    
                    panel(new PanelBuilder("LoadGamePAne"){{
                    childLayoutCenter();
                    alignCenter();
                    //backgroundColor("#89ff");
                    height("7%");
                    width("100%");
                    
                    control(new ButtonBuilder("LoadButton", "Load game") {{
                      alignCenter();
                      valignCenter();
                      height("50%");
                      width("50%");
                      
                      visibleToMouse(true);
                      interactOnClick("loadGame()");
                    }});
                }});
                    
                    panel(new PanelBuilder("SaveGamePAne"){{
                    childLayoutCenter();
                    alignCenter();
                    //backgroundColor("#ffff");
                    height("7%");
                    width("100%");
                    
                    control(new ButtonBuilder("SaveGameButton", "Save game") {{
                      alignCenter();
                      valignCenter();
                      height("50%");
                      width("50%");
                      
                      visibleToMouse(true);
                      interactOnClick("saveGame()");
                    }});
                }});
                    
                    panel(new PanelBuilder("AboutGamePAne"){{
                    childLayoutCenter();
                    alignCenter();
                    //backgroundColor("#f0f0");
                    height("7%");
                    width("100%");
                    
                    control(new ButtonBuilder("AboutGameButton", "About the game") {{
                      alignCenter();
                      valignCenter();
                      height("50%");
                      width("50%");
                      
                      visibleToMouse(true);
                      interactOnClick("aboutGame()");
                    }});
                }});
                    
                    panel(new PanelBuilder("ExitGamePAne"){{
                    childLayoutCenter();
                    alignCenter();
                    //backgroundColor("#0f0f");
                    height("7%");
                    width("100%");
                    
                    control(new ButtonBuilder("ExitGameButton", "Exit game") {{
                      alignCenter();
                      valignCenter();
                      height("50%");
                      width("50%");
                      
                      visibleToMouse(true);
                      interactOnRelease("quitGame()");
                    }});
                }});
                    
                }});
                
            }});
            
        }});

        
    }}.build(nifty));
    
     //Vytvorenie a definícia obrazovky v hre, tzv. Grafického použiv. rozhrania.
    
     nifty.addScreen("game_screen", new ScreenBuilder("game_screen") {{
        controller(new GameHudController(""));
        
        layer(new LayerBuilder("popredie"){{
            childLayoutVertical();
            backgroundColor("#0000");
            
            panel(new PanelBuilder("upperPane"){{
                childLayoutHorizontal();
                alignCenter();
                backgroundColor("#0006");
                width("100%");
                height("7%");
                
                panel(new PanelBuilder("1Pane"){{
                    childLayoutHorizontal();
                    alignCenter();
                    //backgroundColor("#3354");
                    width("20%");
                    height("100%");
                    
                    panel(new PanelBuilder("1LeftPane"){{
                        childLayoutCenter();
                        alignCenter();
                        //backgroundColor("#ffff");
                        width("20%");
                        height("100%");
                        
                        control(new ButtonBuilder("1LeftButton", "<"){{
                            alignCenter();
                            valignCenter();
                            width("90%");
                            height("90%");
                            
                            visibleToMouse(true);
                            interactOnClick("changeModelBackward()");
                        }});
                        
                    }});
                    
                    panel(new PanelBuilder("1CenterPane"){{
                        childLayoutCenter();
                        alignCenter();
                        //backgroundColor("#ffff");
                        width("60%");
                        height("100%");
                        
                        control(new LabelBuilder("1CenterLabel"){{
                        color("#ffff"); 
                        text("Car model"); 
                        width("100%"); 
                        height("100%");
                    }});
                        
                    }});
                    
                    panel(new PanelBuilder("1RightPane"){{
                        childLayoutCenter();
                        alignCenter();
                        //backgroundColor("#ffff");
                        width("20%");
                        height("100%");
                        
                        control(new ButtonBuilder("1RightButton", ">"){{
                            alignCenter();
                            valignCenter();
                            width("90%");
                            height("90%");
                            
                            visibleToMouse(true);
                            interactOnClick("changeModelForward()");
                        }});
                        
                    }});
                    
                }});
                
                panel(new PanelBuilder("2Pane"){{
                    childLayoutHorizontal();
                    alignCenter();
                    //backgroundColor("#9f99");
                    width("20%");
                    height("100%");
                    
                    panel(new PanelBuilder("2LeftPane"){{
                        childLayoutCenter();
                        alignCenter();
                        //backgroundColor("#ffff");
                        width("20%");
                        height("100%");
                        
                        control(new ButtonBuilder("2LeftButton", "<"){{
                            alignCenter();
                            valignCenter();
                            width("90%");
                            height("90%");
                            
                            visibleToMouse(true);
                            interactOnClick("changeCarColorBackward()");
                        }});
                        
                    }});
                    
                    panel(new PanelBuilder("2CenterPane"){{
                        childLayoutCenter();
                        alignCenter();
                        //backgroundColor("#ffff");
                        width("60%");
                        height("100%");
                        
                        control(new LabelBuilder("2CenterLabel"){{
                        color("#ffff"); 
                        text("Car color"); 
                        width("100%"); 
                        height("100%");
                    }});
                        
                    }});
                    
                    panel(new PanelBuilder("2RightPane"){{
                        childLayoutCenter();
                        alignCenter();
                        //backgroundColor("#ffff");
                        width("20%");
                        height("100%");
                        
                        control(new ButtonBuilder("2RightButton", ">"){{
                            alignCenter();
                            valignCenter();
                            width("90%");
                            height("90%");
                            
                            visibleToMouse(true);
                            interactOnClick("changeCarColorForward()");
                        }});
                        
                    }});
                    
                }});
                
                panel(new PanelBuilder("3Pane"){{
                    childLayoutHorizontal();
                    alignCenter();
                    //backgroundColor("#00f0");
                    width("20%");
                    height("100%");
                    
                    panel(new PanelBuilder("3LeftPane"){{
                        childLayoutCenter();
                        alignCenter();
                        //backgroundColor("#ffff");
                        width("20%");
                        height("100%");
                        
                        control(new ButtonBuilder("3LeftButton", "<"){{
                            alignCenter();
                            valignCenter();
                            width("90%");
                            height("90%");
                            
                            visibleToMouse(true);
                            interactOnClick("changeCarFrontBumperBackward()");
                        }});
                        
                    }});
                    
                    panel(new PanelBuilder("3CenterPane"){{
                        childLayoutCenter();
                        alignCenter();
                        //backgroundColor("#ffff");
                        width("60%");
                        height("100%");
                        
                        control(new LabelBuilder("3CenterLabel"){{
                        color("#ffff"); 
                        text("Front bumper"); 
                        width("100%"); 
                        height("100%");
                    }});
                        
                    }});
                    
                    panel(new PanelBuilder("3RightPane"){{
                        childLayoutCenter();
                        alignCenter();
                        //backgroundColor("#ffff");
                        width("20%");
                        height("100%");
                        
                        control(new ButtonBuilder("3RightButton", ">"){{
                            alignCenter();
                            valignCenter();
                            width("90%");
                            height("90%");
                            
                            visibleToMouse(true);
                            interactOnClick("changeCarFrontBumperForward()");
                        }});
                        
                    }});
                    
                }});
                
                panel(new PanelBuilder("4Pane"){{
                    childLayoutHorizontal();
                    alignCenter();
                    //backgroundColor("#7103");
                    width("20%");
                    height("100%");
                    
                    panel(new PanelBuilder("4LeftPane"){{
                        childLayoutCenter();
                        alignCenter();
                        //backgroundColor("#ffff");
                        width("20%");
                        height("100%");
                        
                        control(new ButtonBuilder("4LeftButton", "<"){{
                            alignCenter();
                            valignCenter();
                            width("90%");
                            height("90%");
                            
                            visibleToMouse(true);
                            interactOnClick("changeCarBackBumperBackward()");
                        }});
                        
                    }});
                    
                    panel(new PanelBuilder("4CenterPane"){{
                        childLayoutCenter();
                        alignCenter();
                        //backgroundColor("#ffff");
                        width("60%");
                        height("100%");
                        
                        control(new LabelBuilder("4CenterLabel"){{
                        color("#ffff"); 
                        text("Rear bumper"); 
                        width("100%"); 
                        height("100%");
                    }});
                        
                    }});
                    
                    panel(new PanelBuilder("4RightPane"){{
                        childLayoutCenter();
                        alignCenter();
                        //backgroundColor("#ffff");
                        width("20%");
                        height("100%");
                        
                        control(new ButtonBuilder("4RightButton", ">"){{
                            alignCenter();
                            valignCenter();
                            width("90%");
                            height("90%");
                            
                            visibleToMouse(true);
                            interactOnClick("changeCarBackBumperForward()");
                        }});
                        
                    }});
                    
                }});
                
                panel(new PanelBuilder("5Pane"){{
                    childLayoutHorizontal();
                    alignCenter();
                    //backgroundColor("#ffff");
                    width("20%");
                    height("100%");
                    
                    panel(new PanelBuilder("5LeftPane"){{
                        childLayoutCenter();
                        alignCenter();
                        //backgroundColor("#ffff");
                        width("20%");
                        height("100%");
                        
                        control(new ButtonBuilder("5LeftButton", "<"){{
                            alignCenter();
                            valignCenter();
                            width("90%");
                            height("90%");
                            
                            visibleToMouse(true);
                            interactOnClick("changeWheelsBackward()");
                        }});
                        
                    }});
                    
                    panel(new PanelBuilder("5CenterPane"){{
                        childLayoutCenter();
                        alignCenter();
                        //backgroundColor("#ffff");
                        width("60%");
                        height("100%");
                        
                        control(new LabelBuilder("5CenterLabel"){{
                        color("#ffff"); 
                        text("Car wheels"); 
                        width("100%"); 
                        height("100%");
                    }});
                        
                    }});
                    
                    panel(new PanelBuilder("5RightPane"){{
                        childLayoutCenter();
                        alignCenter();
                        //backgroundColor("#ffff");
                        width("20%");
                        height("100%");
                        
                        control(new ButtonBuilder("5RightButton", ">"){{
                            alignCenter();
                            valignCenter();
                            width("90%");
                            height("90%");
                            
                            visibleToMouse(true);
                            interactOnClick("changeWheelsForward()");
                        }});
                        
                    }});
                    
                }});
                
            }});
            
        }});
        
    }}.build(nifty));
     
    //Vytvorenie a definícia obrazovky "O hre".
    
    nifty.addScreen("about_screen", new ScreenBuilder("about_screen") {{
        controller(new AboutController(assetManager, rootNode));
        
        layer(new LayerBuilder("pozadie") {{
            childLayoutCenter();
            backgroundColor("#0000");
            
            image(new ImageBuilder() {{
                //filename("Nifty/START_screen_WALLPAPER_R.png");
                filename("Nifty/obraz.jpg");
            }});
            
        }});
        
        layer(new LayerBuilder("popredie") {{
            childLayoutVertical();
            backgroundColor("#0000");
            
            panel(new PanelBuilder("nazovPane"){{
                childLayoutCenter();
                alignCenter();
                //backgroundColor("#312f");
                height("10%");
                width("100%");
                
                text(new TextBuilder() {{
                    text("About the game");
                    font("Nifty/Skuska.fnt");
                    height("100%");
                    width("100%");
                }});
                
            }});
            
            panel(new PanelBuilder("othersPane"){{
                childLayoutHorizontal();
                alignCenter();
                //backgroundColor("#812f");
                height("80%");
                width("100%");
                
                text(new TextBuilder() {{
                    text("About:\n"+
                            "\n" +
                            "\n" +
                            "This game is about tunning some smokin' fast cars, and great music. We hope you will enjoy it!"+
                            "\n"+
                         "\n\n" + 
                           "Name of the game: Pro Street Tuner\n\n"+
                            "Game authors: Ľubomír Petrus, Gabriel Mikloš, Matej Pazdič\n\n"+
                            "Date: 12.1.2012\n\n"+
                            "Subject name: Počítačová grafika\n\n"+
                            "Name of teacher: Ing. František Hrozek\n\n"+
                            "Institute: Katedra počítačov a informatiky (KPI)");
                    font("Nifty/CourierNew_OUT.fnt");
                    wrap(true);
                    height("100%");
                    width("100%");
                }});
                
            }});
            
            panel(new PanelBuilder("othersPane"){{
                childLayoutHorizontal();
                alignCenter();
                //backgroundColor("#812f");
                height("10%");
                width("100%");
                
                panel(new PanelBuilder("buttonPanel"){{
                    childLayoutCenter();
                    alignCenter();
                    height("100%");
                    width("20%");
                    
                    control(new ButtonBuilder("backButton", "Back to main menu"){{
                        alignCenter();
                        valignCenter();
                        height("50%");
                        width("50%");
                        
                        visibleToMouse(true);
                        interactOnClick("getBack()");
                    }});
                }});
                        
            }});
            
        }});
        
    }}.build(nifty));
    
    //Vytvorenie a definícia obrazovky "Uloženie hry".
    
    nifty.addScreen("save_screen", new ScreenBuilder("save_screen") {{
        controller(new SaveGameController(assetManager, rootNode));
        
        layer(new LayerBuilder("pozadie") {{
            childLayoutCenter();
            //backgroundColor("#0000");
            
            image(new ImageBuilder() {{
                //filename("Nifty/START_screen_WALLPAPER_R.png");
                filename("Nifty/obraz.jpg");
            }});
            
        }});
        
        layer(new LayerBuilder("popredie") {{
            childLayoutVertical();
            //backgroundColor("#0000");
            
            panel(new PanelBuilder("nazovPane"){{
                childLayoutCenter();
                alignCenter();
                //backgroundColor("#312f");
                height("10%");
                width("100%");
                
                text(new TextBuilder() {{
                    text("Save game");
                    font("Nifty/Skuska.fnt");
                    height("100%");
                    width("100%");
                }});
                
            }});
            
            panel(new PanelBuilder("othersPane"){{
                childLayoutVertical();
                alignCenter();
                //backgroundColor("#63ff");
                height("80%");
                width("100%");
                
                panel(new PanelBuilder("Gap1pane"){{
                childLayoutCenter();
                alignCenter();
                //backgroundColor("#0f0f");
                height("10%");
                width("100%");
                }});
                
                panel(new PanelBuilder("activitPane"){{
                childLayoutHorizontal();
                alignCenter();
                //backgroundColor("#9f9f");
                height("10%");
                width("100%");
                
                panel(new PanelBuilder("PopisokPane"){{
                childLayoutCenter();
                alignRight();
                //backgroundColor("#0032");
                height("100%");
                width("50%");
                
                text(new TextBuilder(){{
                    text("Enter a save game name: ");
                    font("Nifty/CourierNew_OUT.fnt");
                    width("100%");
                    height("100%");
                }});
                
                }});
                
                panel(new PanelBuilder("TextFPane"){{
                childLayoutCenter();
                alignCenter();
                //backgroundColor("#74f0");
                height("100%");
                width("50%");
                
                control(textField);
                
                }});
                
                }});
                
            }});
            
            panel(new PanelBuilder("buttonPane"){{
                childLayoutHorizontal();
                alignCenter();
                //backgroundColor("#639f");
                height("10%");
                width("100%");
                
                panel(new PanelBuilder("saveButtPane"){{
                    childLayoutCenter();
                    alignCenter();
                    //backgroundColor("#1111");
                    height("100%");
                    width("20%");
                    
                    control(new ButtonBuilder("SaveButt", "Save game ..."){{
                        alignCenter();
                        valignCenter();
                        height("50%");
                        width("50%");
                        
                        visibleToMouse(true);
                        String str = "saveGame(textField)";
                        interactOnClick(str);
                    }});
                    
                }});
                
                panel(new PanelBuilder("BackButtPane"){{
                    childLayoutCenter();
                    alignCenter();
                    //backgroundColor("#9999");
                    height("100%");
                    width("20%");
                    
                    control(new ButtonBuilder("BackButt", "Back to main menu"){{
                        alignCenter();
                        valignCenter();
                        height("50%");
                        width("50%");
                        
                        visibleToMouse(true);
                        interactOnClick("getBack()");
                    }});
                    
                }});
                
            }});
            
        }});
        
    }}.build(nifty));
    
    //Vytvorenie a definícia obrazovky "Načítanie hry".
    
    nifty.addScreen("load_screen", new ScreenBuilder("load_screen") {{
        controller(new LoadGameController(assetManager, rootNode));
        
        layer(new LayerBuilder("pozadie") {{
            childLayoutCenter();
            //backgroundColor("#0000");
            
            image(new ImageBuilder() {{
                filename("Nifty/obraz.jpg");
            }});
            
        }});
        
        layer(new LayerBuilder("popredie") {{
            childLayoutVertical();
            //backgroundColor("#0000");
            
            panel(new PanelBuilder("nazovPane"){{
                childLayoutCenter();
                alignCenter();
                //backgroundColor("#312f");
                height("10%");
                width("100%");
                
                text(new TextBuilder() {{
                    text("Load game");
                    font("Nifty/Skuska.fnt");
                    height("100%");
                    width("100%");
                }});
                
            }});
            
            panel(new PanelBuilder("othersPane"){{
                childLayoutVertical();
                alignCenter();
                //backgroundColor("#63ff");
                height("80%");
                width("100%");
                
                panel(new PanelBuilder("Gap1pane"){{
                childLayoutCenter();
                alignCenter();
                //backgroundColor("#0f0f");
                height("10%");
                width("100%");
                }});
                
                panel(new PanelBuilder("activitPane"){{
                childLayoutHorizontal();
                alignCenter();
                //backgroundColor("#9f9f");
                height("10%");
                width("100%");
                
                panel(new PanelBuilder("PopisokPane"){{
                childLayoutCenter();
                alignRight();
                //backgroundColor("#0032");
                height("100%");
                width("50%");
                
                text(new TextBuilder(){{
                    text("Enter a game name to load: ");
                    font("Nifty/CourierNew_OUT.fnt");
                    width("100%");
                    height("100%");
                }});
                
                }});
                
                panel(new PanelBuilder("TextFPane"){{
                childLayoutCenter();
                alignCenter();
                //backgroundColor("#74f0");
                height("100%");
                width("50%");
                
                control(textField1);
                
                }});
                
                }});
                
            }});
            
            panel(new PanelBuilder("buttonPane"){{
                childLayoutHorizontal();
                alignCenter();
                //backgroundColor("#639f");
                height("10%");
                width("100%");
                
                panel(new PanelBuilder("LoadButtPane"){{
                    childLayoutCenter();
                    alignCenter();
                    //backgroundColor("#1111");
                    height("100%");
                    width("20%");
                    
                    control(new ButtonBuilder("LoadButt", "Load game ..."){{
                        alignCenter();
                        valignCenter();
                        height("50%");
                        width("50%");
                        
                        visibleToMouse(true);
                        String str = "loadGame(textField)";
                        interactOnClick(str);
                    }});
                    
                }});
                
                panel(new PanelBuilder("BackButtPane"){{
                    childLayoutCenter();
                    alignCenter();
                    //backgroundColor("#9999");
                    height("100%");
                    width("20%");
                    
                    control(new ButtonBuilder("BackButt", "Back to main menu"){{
                        alignCenter();
                        valignCenter();
                        height("50%");
                        width("50%");
                        
                        visibleToMouse(true);
                        interactOnClick("getBack()");
                    }});
                    
                }});
                
            }});
            
        }});
        
    }}.build(nifty));
    
    //Vytvorenie a definícia chzbovej obrazovky pri ukladaní alebo načitavaní hry.
    
    nifty.addScreen("error_screen", new ScreenBuilder("error_screen") {{
        controller(new ExitController(assetManager, rootNode));
        
        layer(new LayerBuilder("pozadie") {{
            childLayoutCenter();
            //backgroundColor("#0000");
            
            image(new ImageBuilder() {{
                //filename("Nifty/START_screen_WALLPAPER_R.png");
                filename("Nifty/obraz.jpg");
            }});
            
        }});
        
        layer(new LayerBuilder("popredie") {{
            childLayoutVertical();
            //backgroundColor("#0000");
            
            panel(new PanelBuilder("nazovPane"){{
                childLayoutCenter();
                alignCenter();
                //backgroundColor("#312f");
                height("10%");
                width("100%");
                
                text(new TextBuilder() {{
                    text("Error!!!");
                    font("Nifty/Skuska.fnt");
                    height("100%");
                    width("100%");
                }});
                
            }});
            
            panel(new PanelBuilder("othersPane"){{
                childLayoutVertical();
                alignCenter();
                //backgroundColor("#63ff");
                height("80%");
                width("100%");
                
                panel(new PanelBuilder("Gap1pane"){{
                childLayoutCenter();
                alignCenter();
                //backgroundColor("#0f0f");
                height("10%");
                width("100%");
                
                text(new TextBuilder(){{
                    text("This file is not correct!!! Try it again!");
                    font("Nifty/CourierNew_OUT.fnt");
                    width("100%");
                    height("100%");
                }});
                
                }});
                
            }});
            
            panel(new PanelBuilder("buttonPane"){{
                childLayoutHorizontal();
                alignCenter();
                //backgroundColor("#639f");
                height("10%");
                width("100%");
                
                panel(new PanelBuilder("BackButtPane"){{
                    childLayoutCenter();
                    alignCenter();
                    //backgroundColor("#9999");
                    height("100%");
                    width("20%");
                    
                    control(new ButtonBuilder("BackButt", "Back to main menu"){{
                        alignCenter();
                        valignCenter();
                        height("50%");
                        width("50%");
                        
                        visibleToMouse(true);
                        interactOnClick("getBack()");
                    }});
                    
                }});
                
            }});
            
        }});
        
    }}.build(nifty));
    
    //Vytvorenie a definícia potvrdzovacej obrazovky pri vypnutí hry.
    
    nifty.addScreen("exit_screen", new ScreenBuilder("exit_screen") {{
        controller(new ExitController(assetManager, rootNode));
        
        layer(new LayerBuilder("pozadie") {{
            childLayoutCenter();
            //backgroundColor("#0000");
            
            image(new ImageBuilder() {{
                //filename("Nifty/START_screen_WALLPAPER_R.png");
                filename("Nifty/obraz.jpg");
            }});
            
        }});
        
        layer(new LayerBuilder("popredie") {{
            childLayoutVertical();
            //backgroundColor("#0000");
            
            panel(new PanelBuilder("nazovPane"){{
                childLayoutCenter();
                alignCenter();
                //backgroundColor("#312f");
                height("10%");
                width("100%");
                
                text(new TextBuilder() {{
                    text("Do you really want to exit?");
                    font("Nifty/Skuska.fnt");
                    height("100%");
                    width("100%");
                }});
                
            }});
            
            panel(new PanelBuilder("othersPane"){{
                childLayoutVertical();
                alignCenter();
                //backgroundColor("#63ff");
                height("80%");
                width("100%");
                
                panel(new PanelBuilder("Gap1pane"){{
                childLayoutCenter();
                alignCenter();
                //backgroundColor("#0f0f");
                height("10%");
                width("100%");
                
                text(new TextBuilder(){{
                    text("Coose your option.");
                    font("Nifty/CourierNew_OUT.fnt");
                    width("100%");
                    height("100%");
                }});
                
                }});
                
            }});
            
            panel(new PanelBuilder("buttonPane"){{
                childLayoutHorizontal();
                alignCenter();
                //backgroundColor("#639f");
                height("10%");
                width("100%");
                
                panel(new PanelBuilder("NoButtPane"){{
                    childLayoutCenter();
                    alignCenter();
                    //backgroundColor("#9999");
                    height("100%");
                    width("20%");
                    
                    control(new ButtonBuilder("NoButt", "No"){{
                        alignCenter();
                        valignCenter();
                        height("50%");
                        width("50%");
                        
                        visibleToMouse(true);
                        interactOnClick("getBack()");
                    }});
                    
                }});
                
                panel(new PanelBuilder("YesButtPane"){{
                    childLayoutCenter();
                    alignCenter();
                    //backgroundColor("#ffff");
                    height("100%");
                    width("60%");
                    
                }});
                
                panel(new PanelBuilder("YesButtPane"){{
                    childLayoutCenter();
                    alignCenter();
                    //backgroundColor("#9999");
                    height("100%");
                    width("20%");
                    
                    control(new ButtonBuilder("YesButt", "Yes"){{
                        alignCenter();
                        valignCenter();
                        height("50%");
                        width("50%");
                        
                        visibleToMouse(true);
                        interactOnClick("exitGame()");
                    }});
                    
                }});
                
            }});
            
        }});
        
    }}.build(nifty));
    }

    /** 
     * Metóda slúžiaca na mapovanie ovládania, alebo na premapovanie
     * ovládanie pri zmene pozície v hre.
     * @param inGame - pomocná premenná zapuzdrujúca informáciu o tom či sme
     * v hráčskom okne, alebo sme v menu
     */
    public void initKeys(boolean inGame) {
        if (inGame == true) {
            inputManager.clearMappings();
            inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_P));
            inputManager.addMapping("ZOOM_IN", new KeyTrigger(KeyInput.KEY_UP));
            inputManager.addMapping("ZOOM_OUT", new KeyTrigger(KeyInput.KEY_DOWN));
            inputManager.addMapping("RotateL", new KeyTrigger(KeyInput.KEY_LEFT),
                    new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
            inputManager.addMapping("RotateR", new KeyTrigger(KeyInput.KEY_RIGHT),
                    new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
            inputManager.addMapping("Escape", new KeyTrigger(KeyInput.KEY_ESCAPE));

            inputManager.addListener(actionListener, new String[]{"Pause", "Escape"});
            inputManager.addListener(analogListener, new String[]{"RotateL", "RotateR", "ZOOM_IN", "ZOOM_OUT"});
        }
        else{
            inputManager.clearMappings();
            inputManager.addMapping("Escape", new KeyTrigger(KeyInput.KEY_ESCAPE));
            inputManager.addListener(actionListener, new String[]{"Escape"});
        }
        
        

    }
    /**
     * Inštancia počúvača klávesnice, ktorá obsahuje ako odchytávať odozvy z klávesnice počítača.
     */
    public ActionListener actionListener = new ActionListener() {

        public void onAction(String name, boolean keyPressed, float tpf) {
            if (name.equals("Pause") && !keyPressed) {
                isRunning = !isRunning;
            }
            if (name.equals("Escape") && !keyPressed) {
                    if(isGameInitialized == true){
                        switch(isInGame){
                            case 0: isInGame = 1;
                                    nifty.gotoScreen("game_screen");
                                    break;
                            case 1: isInGame = 0;
                                    nifty.gotoScreen("start_screen");
                                    break;
                        }
                    }
                }
            
            
            if (name.equals("Cam") && !keyPressed) {

                flyCam.registerWithInput(inputManager);
                flyCam.setEnabled(isCamera);
                isCamera = !isCamera;
            }
        }
    };
    /**
     * Inštancia počúvača klávesnice, ktorá obsahuje ako odchytávať odozvy z klávesnice počítača.
     */
    public AnalogListener analogListener = new AnalogListener() {

        public void onAnalog(String name, float value, float tpf) {
            if (isRunning) {
                if (name.equals("RotateR")) {
                    auto.rotate(0, value * speed, 0);
                    prednyNaraznik.rotate(0, value * speed, 0);
                    zadnyNaraznik.rotate(0, value * speed, 0);
                    kolesa.rotate(0, value * speed, 0);
                    //autosilver.rotate(0, value * speed, 0);
                    //autoblack.rotate(0, value * speed, 0);
                    ponton.rotate(0, value * speed, 0);
                    //porsche.rotate(0, value * speed, 0);
                }
                if (name.equals("RotateL")) {
                    auto.rotate(0, -value * speed, 0);
                    prednyNaraznik.rotate(0, -value * speed, 0);
                    zadnyNaraznik.rotate(0, -value * speed, 0);
                    kolesa.rotate(0, -value * speed, 0);
                    //autosilver.rotate(0, -value * speed, 0);
                    //autoblack.rotate(0, -value * speed, 0);
                    ponton.rotate(0, -value * speed, 0);
                    //porsche.rotate(0, -value * speed, 0);
                }
                
                if (name.equals("ZOOM_IN")) {
                    Vector3f v = cam.getLocation();
                    if (v.z <= -5.8f) {
                        cam.setLocation(new Vector3f(v.x, v.y, v.z + value * 3));
                    }
                }
                if (name.equals("ZOOM_OUT")) {

                    Vector3f v = cam.getLocation();
                    if (v.z >= -8f) {
                        cam.setLocation(new Vector3f(v.x, v.y, v.z - value * 3));
                    }
                }
            }
        }
    };

    /**
     * Pomocná metóda na prípravu modelu auta.
     * @param auto - inštancia 3D modelu auta.
     */
    public void preparecar(Spatial auto) {

        auto.setLocalTranslation(0.0f, 0.63f, 0.0f);
        auto.addLight(lamp_light0);
        auto.addLight(lamp_light1);
        auto.addLight(lamp_light2);
        auto.addLight(lamp_light3);
        auto.addLight(stredne);

    }
    
    /**
     * Metóda pre zmenu 3D modelu auta inkrementatívne.
     */
    public void changeModelForward(){
        if (number == 0) {
                    Quaternion x = auto.getLocalRotation();
                    rootNode.detachChild(auto);
                    rootNode.detachChild(prednyNaraznik);
                    rootNode.detachChild(zadnyNaraznik);
                    rootNode.detachChild(kolesa);
                    auto = assetManager.loadModel("Models/H3/zaklad siva.j3o");
                    kolesa = assetManager.loadModel("Models/H3/kolesa/kola 0.j3o");
                    preparecar(auto);
                    preparecar(kolesa);
                    auto.setLocalRotation(x);
                    prednyNaraznik.setLocalRotation(x);
                    zadnyNaraznik.setLocalRotation(x);
                    kolesa.setLocalRotation(x);
                    rootNode.attachChild(auto);
                    rootNode.attachChild(kolesa);
                    number = 1;
                } else if (number == 1) {
                    Quaternion x = auto.getLocalRotation();
                    rootNode.detachChild(auto);
                    rootNode.detachChild(prednyNaraznik);
                    rootNode.detachChild(zadnyNaraznik);
                    rootNode.detachChild(kolesa);
                    auto = assetManager.loadModel("Models/PorscheCarrera/zaklad sivy UZ FULL.j3o");
                    prednyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik predny/SIVY0.j3o");
                    zadnyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik zadny/sivy 0.j3o");
                    kolesa = assetManager.loadModel("Models/PorscheCarrera/kolesa/kola 0.j3o");
                    preparecar(auto);
                    preparecar(prednyNaraznik);
                    preparecar(zadnyNaraznik);
                    preparecar(kolesa);
                    auto.setLocalRotation(x);
                    prednyNaraznik.setLocalRotation(x);
                    zadnyNaraznik.setLocalRotation(x);
                    kolesa.setLocalRotation(x);
                    rootNode.attachChild(auto);
                    rootNode.attachChild(prednyNaraznik);
                    rootNode.attachChild(zadnyNaraznik);
                    rootNode.attachChild(kolesa);
                    number = 2;
                }else if (number == 2) {
                    Quaternion x = auto.getLocalRotation();
                    rootNode.detachChild(auto);
                    rootNode.detachChild(prednyNaraznik);
                    rootNode.detachChild(zadnyNaraznik);
                    rootNode.detachChild(kolesa);
                    auto = assetManager.loadModel("Models/CLK/zaklad sivy.j3o");
                    prednyNaraznik = assetManager.loadModel("Models/CLK/predny naraznik/sivy 0.j3o");
                    zadnyNaraznik = assetManager.loadModel("Models/CLK/zadny naraznik/sivy 0.j3o");
                    kolesa = assetManager.loadModel("Models/CLK/kola/kola 0.j3o");
                    preparecar(auto);
                    preparecar(prednyNaraznik);
                    preparecar(zadnyNaraznik);
                    preparecar(kolesa);
                    auto.setLocalRotation(x);
                    prednyNaraznik.setLocalRotation(x);
                    zadnyNaraznik.setLocalRotation(x);
                    kolesa.setLocalRotation(x);
                    rootNode.attachChild(auto);
                    rootNode.attachChild(prednyNaraznik);
                    rootNode.attachChild(zadnyNaraznik);
                    rootNode.attachChild(kolesa);
                    number = 0;
                }
    }
        
    /**
     * Metóda pre zmenu 3D modelu auta dekrementatívne.
     */
    public void changeModelBackward(){
    
        if (number == 0) {
                    Quaternion x = auto.getLocalRotation();
                    rootNode.detachChild(auto);
                    rootNode.detachChild(prednyNaraznik);
                    rootNode.detachChild(zadnyNaraznik);
                    rootNode.detachChild(kolesa);
                    auto = assetManager.loadModel("Models/PorscheCarrera/zaklad sivy UZ FULL.j3o");
                    prednyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik predny/SIVY0.j3o");
                    zadnyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik zadny/sivy 0.j3o");
                    kolesa = assetManager.loadModel("Models/PorscheCarrera/kolesa/kola 0.j3o");
                    preparecar(auto);
                    preparecar(prednyNaraznik);
                    preparecar(zadnyNaraznik);
                    preparecar(kolesa);
                    auto.setLocalRotation(x);
                    prednyNaraznik.setLocalRotation(x);
                    zadnyNaraznik.setLocalRotation(x);
                    kolesa.setLocalRotation(x);
                    rootNode.attachChild(auto);
                    rootNode.attachChild(prednyNaraznik);
                    rootNode.attachChild(zadnyNaraznik);
                    rootNode.attachChild(kolesa);
                    number = 2;
                } else if (number == 2) {
                    Quaternion x = auto.getLocalRotation();
                    rootNode.detachChild(auto);
                    rootNode.detachChild(prednyNaraznik);
                    rootNode.detachChild(zadnyNaraznik);
                    rootNode.detachChild(kolesa);
                    auto = assetManager.loadModel("Models/H3/zaklad siva.j3o");
                    kolesa = assetManager.loadModel("Models/H3/kolesa/kola 0.j3o");
                    preparecar(auto);
                    preparecar(kolesa);
                    auto.setLocalRotation(x);
                    prednyNaraznik.setLocalRotation(x);
                    zadnyNaraznik.setLocalRotation(x);
                    kolesa.setLocalRotation(x);
                    rootNode.attachChild(auto);
                    rootNode.attachChild(kolesa);
                    number = 1;
                }else if (number == 1) {
                    Quaternion x = auto.getLocalRotation();
                    rootNode.detachChild(auto);
                    rootNode.detachChild(prednyNaraznik);
                    rootNode.detachChild(zadnyNaraznik);
                    rootNode.detachChild(kolesa);
                    auto = assetManager.loadModel("Models/CLK/zaklad sivy.j3o");
                    prednyNaraznik = assetManager.loadModel("Models/CLK/predny naraznik/sivy 0.j3o");
                    zadnyNaraznik = assetManager.loadModel("Models/CLK/zadny naraznik/sivy 0.j3o");
                    kolesa = assetManager.loadModel("Models/CLK/kola/kola 0.j3o");
                    preparecar(auto);
                    preparecar(prednyNaraznik);
                    preparecar(zadnyNaraznik);
                    preparecar(kolesa);
                    auto.setLocalRotation(x);
                    prednyNaraznik.setLocalRotation(x);
                    zadnyNaraznik.setLocalRotation(x);
                    kolesa.setLocalRotation(x);
                    rootNode.attachChild(auto);
                    rootNode.attachChild(prednyNaraznik);
                    rootNode.attachChild(zadnyNaraznik);
                    rootNode.attachChild(kolesa);
                    number = 0;
                }
}
        
    /**
     * Metóda pre zmenu farby modelu auta inkrementatívne.
     */
    public void changeCarColorForward(){
                if (number == 2 && colorC == CarColorCarrera.SILVER){
                    Quaternion x = auto.getLocalRotation();
                    rootNode.detachChild(auto);
                    rootNode.detachChild(prednyNaraznik);
                    rootNode.detachChild(zadnyNaraznik);
                    rootNode.detachChild(kolesa);
                    auto = assetManager.loadModel("Models/PorscheCarrera/zaklad cerveny okk.j3o");
                    prednyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik predny/cerveny0.j3o");
                    zadnyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik zadny/cerveny 0.j3o");
                    kolesa = assetManager.loadModel("Models/PorscheCarrera/kolesa/kola 0.j3o");
                    preparecar(auto);
                    preparecar(prednyNaraznik);
                    preparecar(zadnyNaraznik);
                    preparecar(kolesa);
                    auto.setLocalRotation(x);
                    prednyNaraznik.setLocalRotation(x);
                    zadnyNaraznik.setLocalRotation(x);
                    kolesa.setLocalRotation(x);
                    rootNode.attachChild(auto);
                    rootNode.attachChild(prednyNaraznik);
                    rootNode.attachChild(zadnyNaraznik);
                    rootNode.attachChild(kolesa);
                    colorC = CarColorCarrera.RED;
                }
                
                else if (number == 2 && colorC == CarColorCarrera.RED){
                    Quaternion x = auto.getLocalRotation();
                    rootNode.detachChild(auto);
                    rootNode.detachChild(prednyNaraznik);
                    rootNode.detachChild(zadnyNaraznik);
                    rootNode.detachChild(kolesa);
                    auto = assetManager.loadModel("Models/PorscheCarrera/zaklad modry.j3o");
                    prednyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik predny/modry 0.j3o");
                    zadnyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik zadny/modry 0.j3o");
                    kolesa = assetManager.loadModel("Models/PorscheCarrera/kolesa/kola 0.j3o");
                    preparecar(auto);
                    preparecar(prednyNaraznik);
                    preparecar(zadnyNaraznik);
                    preparecar(kolesa);
                    auto.setLocalRotation(x);
                    prednyNaraznik.setLocalRotation(x);
                    zadnyNaraznik.setLocalRotation(x);
                    kolesa.setLocalRotation(x);
                    rootNode.attachChild(auto);
                    rootNode.attachChild(prednyNaraznik);
                    rootNode.attachChild(zadnyNaraznik);
                    rootNode.attachChild(kolesa);
                    colorC = CarColorCarrera.BLUE;
                }
                else if (number == 2 && colorC == CarColorCarrera.BLUE){
                    Quaternion x = auto.getLocalRotation();
                    rootNode.detachChild(auto);
                    rootNode.detachChild(prednyNaraznik);
                    rootNode.detachChild(zadnyNaraznik);
                    rootNode.detachChild(kolesa);
                    auto = assetManager.loadModel("Models/PorscheCarrera/zaklad zlty.j3o");
                    prednyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik predny/zlty 0.j3o");
                    zadnyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik zadny/zlty 0.j3o");
                    kolesa = assetManager.loadModel("Models/PorscheCarrera/kolesa/kola 0.j3o");
                    preparecar(auto);
                    preparecar(prednyNaraznik);
                    preparecar(zadnyNaraznik);
                    preparecar(kolesa);
                    auto.setLocalRotation(x);
                    prednyNaraznik.setLocalRotation(x);
                    zadnyNaraznik.setLocalRotation(x);
                    kolesa.setLocalRotation(x);
                    rootNode.attachChild(auto);
                    rootNode.attachChild(prednyNaraznik);
                    rootNode.attachChild(zadnyNaraznik);
                    rootNode.attachChild(kolesa);
                    colorC = CarColorCarrera.YELLOW;
                }
                else if (number == 2 && colorC == CarColorCarrera.YELLOW){
                    Quaternion x = auto.getLocalRotation();
                    rootNode.detachChild(auto);
                    rootNode.detachChild(prednyNaraznik);
                    rootNode.detachChild(zadnyNaraznik);
                    rootNode.detachChild(kolesa);
                    auto = assetManager.loadModel("Models/PorscheCarrera/zaklad sivy UZ FULL.j3o");
                    prednyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik predny/SIVY0.j3o");
                    zadnyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik zadny/sivy 0.j3o");
                    kolesa = assetManager.loadModel("Models/PorscheCarrera/kolesa/kola 0.j3o");
                    preparecar(auto);
                    preparecar(prednyNaraznik);
                    preparecar(zadnyNaraznik);
                    preparecar(kolesa);
                    auto.setLocalRotation(x);
                    prednyNaraznik.setLocalRotation(x);
                    zadnyNaraznik.setLocalRotation(x);
                    kolesa.setLocalRotation(x);
                    rootNode.attachChild(auto);
                    rootNode.attachChild(prednyNaraznik);
                    rootNode.attachChild(zadnyNaraznik);
                    rootNode.attachChild(kolesa);
                    colorC = CarColorCarrera.SILVER;
                }
                
                else if (number == 0 && colorM == CarColorMercedes.SILVER){
                    Quaternion x = auto.getLocalRotation();
                    rootNode.detachChild(auto);
                    rootNode.detachChild(prednyNaraznik);
                    rootNode.detachChild(zadnyNaraznik);
                    rootNode.detachChild(kolesa);
                    auto = assetManager.loadModel("Models/CLK/zaklad zeleny.j3o");
                    prednyNaraznik = assetManager.loadModel("Models/CLK/predny naraznik/zeleny 0.j3o");
                    zadnyNaraznik = assetManager.loadModel("Models/CLK/zadny naraznik/zeleny 0.j3o");
                    kolesa = assetManager.loadModel("Models/CLK/kola/kola 0.j3o");
                    preparecar(auto);
                    preparecar(prednyNaraznik);
                    preparecar(zadnyNaraznik);
                    preparecar(kolesa);
                    auto.setLocalRotation(x);
                    prednyNaraznik.setLocalRotation(x);
                    zadnyNaraznik.setLocalRotation(x);
                    kolesa.setLocalRotation(x);
                    rootNode.attachChild(auto);
                    rootNode.attachChild(prednyNaraznik);
                    rootNode.attachChild(zadnyNaraznik);
                    rootNode.attachChild(kolesa);
                    colorM = CarColorMercedes.AZURE;
                }
                
                else if (number == 0 && colorM == CarColorMercedes.AZURE){
                    Quaternion x = auto.getLocalRotation();
                    rootNode.detachChild(auto);
                    rootNode.detachChild(prednyNaraznik);
                    rootNode.detachChild(zadnyNaraznik);
                    rootNode.detachChild(kolesa);
                    auto = assetManager.loadModel("Models/CLK/zaklad modry.j3o");
                    prednyNaraznik = assetManager.loadModel("Models/CLK/predny naraznik/modry 0.j3o");
                    zadnyNaraznik = assetManager.loadModel("Models/CLK/zadny naraznik/modry 0.j3o");
                    kolesa = assetManager.loadModel("Models/CLK/kola/kola 0.j3o");
                    preparecar(auto);
                    preparecar(prednyNaraznik);
                    preparecar(zadnyNaraznik);
                    preparecar(kolesa);
                    auto.setLocalRotation(x);
                    prednyNaraznik.setLocalRotation(x);
                    zadnyNaraznik.setLocalRotation(x);
                    kolesa.setLocalRotation(x);
                    rootNode.attachChild(auto);
                    rootNode.attachChild(prednyNaraznik);
                    rootNode.attachChild(zadnyNaraznik);
                    rootNode.attachChild(kolesa);
                    colorM = CarColorMercedes.BLUE;
                }
                else if (number == 0 && colorM == CarColorMercedes.BLUE){
                    Quaternion x = auto.getLocalRotation();
                    rootNode.detachChild(auto);
                    rootNode.detachChild(prednyNaraznik);
                    rootNode.detachChild(zadnyNaraznik);
                    rootNode.detachChild(kolesa);
                    auto = assetManager.loadModel("Models/CLK/zaklad oranzovy.j3o");
                    prednyNaraznik = assetManager.loadModel("Models/CLK/predny naraznik/oranzovy 0.j3o"); 
                    zadnyNaraznik = assetManager.loadModel("Models/CLK/zadny naraznik/oranzovy 0.j3o");
                    kolesa = assetManager.loadModel("Models/CLK/kola/kola 0.j3o");
                    preparecar(auto);
                    preparecar(prednyNaraznik);
                    preparecar(zadnyNaraznik);
                    preparecar(kolesa);
                    auto.setLocalRotation(x);
                    prednyNaraznik.setLocalRotation(x);
                    zadnyNaraznik.setLocalRotation(x);
                    kolesa.setLocalRotation(x);
                    rootNode.attachChild(auto);
                    rootNode.attachChild(prednyNaraznik);
                    rootNode.attachChild(zadnyNaraznik);
                    rootNode.attachChild(kolesa);
                    colorM = CarColorMercedes.GOLD;
                }
                else if (number == 0 && colorM == CarColorMercedes.GOLD){
                    Quaternion x = auto.getLocalRotation();
                    rootNode.detachChild(auto);
                    rootNode.detachChild(prednyNaraznik);
                    rootNode.detachChild(zadnyNaraznik);
                    rootNode.detachChild(kolesa);
                    auto = assetManager.loadModel("Models/CLK/zaklad sivy.j3o");
                    prednyNaraznik = assetManager.loadModel("Models/CLK/predny naraznik/sivy 0.j3o");
                    zadnyNaraznik = assetManager.loadModel("Models/CLK/zadny naraznik/sivy 0.j3o");
                    kolesa = assetManager.loadModel("Models/CLK/kola/kola 0.j3o");
                    preparecar(auto);
                    preparecar(prednyNaraznik);
                    preparecar(zadnyNaraznik);
                    preparecar(kolesa);
                    auto.setLocalRotation(x);
                    prednyNaraznik.setLocalRotation(x);
                    zadnyNaraznik.setLocalRotation(x);
                    kolesa.setLocalRotation(x);
                    rootNode.attachChild(auto);
                    rootNode.attachChild(prednyNaraznik);
                    rootNode.attachChild(zadnyNaraznik);
                    rootNode.attachChild(kolesa);
                    colorM = CarColorMercedes.SILVER;
                }
                else if (number == 1 && colorH == CarColorHummer.SILVER){
                    Quaternion x = auto.getLocalRotation();
                    rootNode.detachChild(auto);
                    rootNode.detachChild(prednyNaraznik);
                    rootNode.detachChild(zadnyNaraznik);
                    rootNode.detachChild(kolesa);
                    auto = assetManager.loadModel("Models/H3/zaklad cierna.j3o");
                    kolesa = assetManager.loadModel("Models/H3/kolesa/kola 0.j3o");
                    preparecar(auto);
                    preparecar(kolesa);
                    auto.setLocalRotation(x);
                    kolesa.setLocalRotation(x);
                    rootNode.attachChild(auto);
                    rootNode.attachChild(kolesa);
                    colorH = CarColorHummer.BLACK;
                }
                else if (number == 1 && colorH == CarColorHummer.BLACK){
                    Quaternion x = auto.getLocalRotation();
                    rootNode.detachChild(auto);
                    rootNode.detachChild(prednyNaraznik);
                    rootNode.detachChild(zadnyNaraznik);
                    rootNode.detachChild(kolesa);
                    auto = assetManager.loadModel("Models/H3/zaklad modra.j3o");
                    kolesa = assetManager.loadModel("Models/H3/kolesa/kola 0.j3o");
                    preparecar(auto);
                    preparecar(kolesa);
                    auto.setLocalRotation(x);
                    kolesa.setLocalRotation(x);
                    rootNode.attachChild(auto);
                    rootNode.attachChild(kolesa);
                    colorH = CarColorHummer.BLUE;
                }
                else if (number == 1 && colorH == CarColorHummer.BLUE){
                    Quaternion x = auto.getLocalRotation();
                    rootNode.detachChild(auto);
                    rootNode.detachChild(prednyNaraznik);
                    rootNode.detachChild(zadnyNaraznik);
                    rootNode.detachChild(kolesa);
                    auto = assetManager.loadModel("Models/H3/zaklad biela.j3o");
                    kolesa = assetManager.loadModel("Models/H3/kolesa/kola 0.j3o");
                    preparecar(auto);
                    preparecar(kolesa);
                    auto.setLocalRotation(x);
                    kolesa.setLocalRotation(x);
                    rootNode.attachChild(auto);
                    rootNode.attachChild(kolesa);
                    colorH = CarColorHummer.WHITE;
                }
                else if (number == 1 && colorH == CarColorHummer.WHITE){
                    Quaternion x = auto.getLocalRotation();
                    rootNode.detachChild(auto);
                    rootNode.detachChild(prednyNaraznik);
                    rootNode.detachChild(zadnyNaraznik);
                    rootNode.detachChild(kolesa);
                    auto = assetManager.loadModel("Models/H3/zaklad zelena.j3o");
                    kolesa = assetManager.loadModel("Models/H3/kolesa/kola 0.j3o");
                    preparecar(auto);
                    preparecar(kolesa);
                    auto.setLocalRotation(x);
                    kolesa.setLocalRotation(x);
                    rootNode.attachChild(auto);
                    rootNode.attachChild(kolesa);
                    colorH = CarColorHummer.GREEN;
                }
                else if (number == 1 && colorH == CarColorHummer.GREEN){
                    Quaternion x = auto.getLocalRotation();
                    rootNode.detachChild(auto);
                    rootNode.detachChild(prednyNaraznik);
                    rootNode.detachChild(zadnyNaraznik);
                    rootNode.detachChild(kolesa);
                    auto = assetManager.loadModel("Models/H3/zaklad siva.j3o");
                    kolesa = assetManager.loadModel("Models/H3/kolesa/kola 0.j3o");
                    preparecar(auto);
                    preparecar(kolesa);
                    auto.setLocalRotation(x);
                    kolesa.setLocalRotation(x);
                    rootNode.attachChild(auto);
                    rootNode.attachChild(kolesa);
                    colorH = CarColorHummer.GREY;
                }
                else if (number == 1 && colorH == CarColorHummer.GREY){
                    Quaternion x = auto.getLocalRotation();
                    rootNode.detachChild(auto);
                    rootNode.detachChild(prednyNaraznik);
                    rootNode.detachChild(zadnyNaraznik);
                    rootNode.detachChild(kolesa);
                    auto = assetManager.loadModel("Models/H3/zaklad cervena .j3o");
                    kolesa = assetManager.loadModel("Models/H3/kolesa/kola 0.j3o");
                    preparecar(auto);
                    preparecar(kolesa);
                    auto.setLocalRotation(x);
                    kolesa.setLocalRotation(x);
                    rootNode.attachChild(auto);
                    rootNode.attachChild(kolesa);
                    colorH = CarColorHummer.RED;
                }
                else if (number == 1 && colorH == CarColorHummer.RED){
                    Quaternion x = auto.getLocalRotation();
                    rootNode.detachChild(auto);
                    rootNode.detachChild(prednyNaraznik);
                    rootNode.detachChild(zadnyNaraznik);
                    rootNode.detachChild(kolesa);
                    auto = assetManager.loadModel("Models/H3/zaklad zlty.j3o");
                    kolesa = assetManager.loadModel("Models/H3/kolesa/kola 0.j3o");
                    preparecar(auto);
                    preparecar(kolesa);
                    auto.setLocalRotation(x);
                    kolesa.setLocalRotation(x);
                    rootNode.attachChild(auto);
                    rootNode.attachChild(kolesa);
                    colorH = CarColorHummer.YELLOW;
                }
                else if (number == 1 && colorH == CarColorHummer.YELLOW){
                    Quaternion x = auto.getLocalRotation();
                    rootNode.detachChild(auto);
                    rootNode.detachChild(prednyNaraznik);
                    rootNode.detachChild(zadnyNaraznik);
                    rootNode.detachChild(kolesa);
                    auto = assetManager.loadModel("Models/H3/zaklad strieborna.j3o");
                    kolesa = assetManager.loadModel("Models/H3/kolesa/kola 0.j3o");
                    preparecar(auto);
                    preparecar(kolesa);
                    auto.setLocalRotation(x);
                    kolesa.setLocalRotation(x);
                    rootNode.attachChild(auto);
                    rootNode.attachChild(kolesa);
                    colorH = CarColorHummer.SILVER;
                }
                
        }
    
    /**
     * Metóda pre zmenu farby modelu auta dekrementatívne.
     */
    public void changeCarColorBackward(){
                if (number == 2 && colorC == CarColorCarrera.SILVER){
                    Quaternion x = auto.getLocalRotation();
                    rootNode.detachChild(auto);
                    rootNode.detachChild(prednyNaraznik);
                    rootNode.detachChild(zadnyNaraznik);
                    rootNode.detachChild(kolesa);
                    auto = assetManager.loadModel("Models/PorscheCarrera/zaklad zlty.j3o");
                    prednyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik predny/zlty 0.j3o");
                    zadnyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik zadny/zlty 0.j3o");
                    kolesa = assetManager.loadModel("Models/PorscheCarrera/kolesa/kola 0.j3o");
                    preparecar(auto);
                    preparecar(prednyNaraznik);
                    preparecar(zadnyNaraznik);
                    preparecar(kolesa);
                    auto.setLocalRotation(x);
                    prednyNaraznik.setLocalRotation(x);
                    zadnyNaraznik.setLocalRotation(x);
                    kolesa.setLocalRotation(x);
                    rootNode.attachChild(auto);
                    rootNode.attachChild(prednyNaraznik);
                    rootNode.attachChild(zadnyNaraznik);
                    rootNode.attachChild(kolesa);
                    colorC = CarColorCarrera.YELLOW;
                }
                
                else if (number == 2 && colorC == CarColorCarrera.YELLOW){
                    Quaternion x = auto.getLocalRotation();
                    rootNode.detachChild(auto);
                    rootNode.detachChild(prednyNaraznik);
                    rootNode.detachChild(zadnyNaraznik);
                    rootNode.detachChild(kolesa);
                    auto = assetManager.loadModel("Models/PorscheCarrera/zaklad modry.j3o");
                    prednyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik predny/modry 0.j3o");
                    zadnyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik zadny/modry 0.j3o");
                    kolesa = assetManager.loadModel("Models/PorscheCarrera/kolesa/kola 0.j3o");
                    preparecar(auto);
                    preparecar(prednyNaraznik);
                    preparecar(zadnyNaraznik);
                    preparecar(kolesa);
                    auto.setLocalRotation(x);
                    prednyNaraznik.setLocalRotation(x);
                    zadnyNaraznik.setLocalRotation(x);
                    kolesa.setLocalRotation(x);
                    rootNode.attachChild(auto);
                    rootNode.attachChild(prednyNaraznik);
                    rootNode.attachChild(zadnyNaraznik);
                    rootNode.attachChild(kolesa);
                    colorC = CarColorCarrera.BLUE;
                }
                else if (number == 2 && colorC == CarColorCarrera.BLUE){
                    Quaternion x = auto.getLocalRotation();
                    rootNode.detachChild(auto);
                    rootNode.detachChild(prednyNaraznik);
                    rootNode.detachChild(zadnyNaraznik);
                    rootNode.detachChild(kolesa);
                    auto = assetManager.loadModel("Models/PorscheCarrera/zaklad cerveny okk.j3o");
                    prednyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik predny/cerveny0.j3o");
                    zadnyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik zadny/cerveny 0.j3o");
                    kolesa = assetManager.loadModel("Models/PorscheCarrera/kolesa/kola 0.j3o");
                    preparecar(auto);
                    preparecar(prednyNaraznik);
                    preparecar(zadnyNaraznik);
                    preparecar(kolesa);
                    auto.setLocalRotation(x);
                    prednyNaraznik.setLocalRotation(x);
                    zadnyNaraznik.setLocalRotation(x);
                    kolesa.setLocalRotation(x);
                    rootNode.attachChild(auto);
                    rootNode.attachChild(prednyNaraznik);
                    rootNode.attachChild(zadnyNaraznik);
                    rootNode.attachChild(kolesa);
                    colorC = CarColorCarrera.RED;
                }
                else if (number == 2 && colorC == CarColorCarrera.RED){
                    Quaternion x = auto.getLocalRotation();
                    rootNode.detachChild(auto);
                    rootNode.detachChild(prednyNaraznik);
                    rootNode.detachChild(zadnyNaraznik);
                    rootNode.detachChild(kolesa);
                    auto = assetManager.loadModel("Models/PorscheCarrera/zaklad sivy UZ FULL.j3o");
                    prednyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik predny/SIVY0.j3o");
                    zadnyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik zadny/sivy 0.j3o");
                    kolesa = assetManager.loadModel("Models/PorscheCarrera/kolesa/kola 0.j3o");
                    preparecar(auto);
                    preparecar(prednyNaraznik);
                    preparecar(zadnyNaraznik);
                    preparecar(kolesa);
                    auto.setLocalRotation(x);
                    prednyNaraznik.setLocalRotation(x);
                    zadnyNaraznik.setLocalRotation(x);
                    kolesa.setLocalRotation(x);
                    rootNode.attachChild(auto);
                    rootNode.attachChild(prednyNaraznik);
                    rootNode.attachChild(zadnyNaraznik);
                    rootNode.attachChild(kolesa);
                    colorC = CarColorCarrera.SILVER;
                }
                
                else if (number == 0 && colorM == CarColorMercedes.SILVER){
                    Quaternion x = auto.getLocalRotation();
                    rootNode.detachChild(auto);
                    rootNode.detachChild(prednyNaraznik);
                    rootNode.detachChild(zadnyNaraznik);
                    rootNode.detachChild(kolesa);
                    auto = assetManager.loadModel("Models/CLK/zaklad oranzovy.j3o");
                    prednyNaraznik = assetManager.loadModel("Models/CLK/predny naraznik/oranzovy 0.j3o");
                    zadnyNaraznik = assetManager.loadModel("Models/CLK/zadny naraznik/oranzovy 0.j3o");
                    kolesa = assetManager.loadModel("Models/CLK/kola/kola 0.j3o");
                    preparecar(auto);
                    preparecar(prednyNaraznik);
                    preparecar(zadnyNaraznik);
                    preparecar(kolesa);
                    auto.setLocalRotation(x);
                    prednyNaraznik.setLocalRotation(x);
                    zadnyNaraznik.setLocalRotation(x);
                    kolesa.setLocalRotation(x);
                    rootNode.attachChild(auto);
                    rootNode.attachChild(prednyNaraznik);
                    rootNode.attachChild(zadnyNaraznik);
                    rootNode.attachChild(kolesa);
                    colorM = CarColorMercedes.GOLD;
                }
                
                else if (number == 0 && colorM == CarColorMercedes.GOLD){
                    Quaternion x = auto.getLocalRotation();
                    rootNode.detachChild(auto);
                    rootNode.detachChild(prednyNaraznik);
                    rootNode.detachChild(zadnyNaraznik);
                    rootNode.detachChild(kolesa);
                    auto = assetManager.loadModel("Models/CLK/zaklad modry.j3o");
                    prednyNaraznik = assetManager.loadModel("Models/CLK/predny naraznik/modry 0.j3o");
                    zadnyNaraznik = assetManager.loadModel("Models/CLK/zadny naraznik/modry 0.j3o");
                    kolesa = assetManager.loadModel("Models/CLK/kola/kola 0.j3o");
                    preparecar(auto);
                    preparecar(prednyNaraznik);
                    preparecar(zadnyNaraznik);
                    preparecar(kolesa);
                    auto.setLocalRotation(x);
                    prednyNaraznik.setLocalRotation(x);
                    zadnyNaraznik.setLocalRotation(x);
                    kolesa.setLocalRotation(x);
                    rootNode.attachChild(auto);
                    rootNode.attachChild(prednyNaraznik);
                    rootNode.attachChild(zadnyNaraznik);
                    rootNode.attachChild(kolesa);
                    colorM = CarColorMercedes.BLUE;
                }
                else if (number == 0 && colorM == CarColorMercedes.BLUE){
                    Quaternion x = auto.getLocalRotation();
                    rootNode.detachChild(auto);
                    rootNode.detachChild(prednyNaraznik);
                    rootNode.detachChild(zadnyNaraznik);
                    rootNode.detachChild(kolesa);
                    auto = assetManager.loadModel("Models/CLK/zaklad zeleny.j3o");
                    prednyNaraznik = assetManager.loadModel("Models/CLK/predny naraznik/zeleny 0.j3o");
                    zadnyNaraznik = assetManager.loadModel("Models/CLK/zadny naraznik/zeleny 0.j3o");
                    kolesa = assetManager.loadModel("Models/CLK/kola/kola 0.j3o");
                    preparecar(auto);
                    preparecar(prednyNaraznik);
                    preparecar(zadnyNaraznik);
                    preparecar(kolesa);
                    auto.setLocalRotation(x);
                    prednyNaraznik.setLocalRotation(x);
                    zadnyNaraznik.setLocalRotation(x);
                    kolesa.setLocalRotation(x);
                    rootNode.attachChild(auto);
                    rootNode.attachChild(prednyNaraznik);
                    rootNode.attachChild(zadnyNaraznik);
                    rootNode.attachChild(kolesa);
                    colorM = CarColorMercedes.AZURE;
                }
                else if (number == 0 && colorM == CarColorMercedes.AZURE){
                    Quaternion x = auto.getLocalRotation();
                    rootNode.detachChild(auto);
                    rootNode.detachChild(prednyNaraznik);
                    rootNode.detachChild(zadnyNaraznik);
                    rootNode.detachChild(kolesa);
                    auto = assetManager.loadModel("Models/CLK/zaklad sivy.j3o");
                    prednyNaraznik = assetManager.loadModel("Models/CLK/predny naraznik/sivy 0.j3o");
                    zadnyNaraznik = assetManager.loadModel("Models/CLK/zadny naraznik/sivy 0.j3o");
                    kolesa = assetManager.loadModel("Models/CLK/kola/kola 0.j3o");
                    preparecar(auto);
                    preparecar(prednyNaraznik);
                    preparecar(zadnyNaraznik);
                    preparecar(kolesa);
                    auto.setLocalRotation(x);
                    prednyNaraznik.setLocalRotation(x);
                    zadnyNaraznik.setLocalRotation(x);
                    kolesa.setLocalRotation(x);
                    rootNode.attachChild(auto);
                    rootNode.attachChild(prednyNaraznik);
                    rootNode.attachChild(zadnyNaraznik);
                    rootNode.attachChild(kolesa);
                    colorM = CarColorMercedes.SILVER;
                }
                else if (number == 1 && colorH == CarColorHummer.SILVER){
                    Quaternion x = auto.getLocalRotation();
                    rootNode.detachChild(auto);
                    rootNode.detachChild(prednyNaraznik);
                    rootNode.detachChild(zadnyNaraznik);
                    rootNode.detachChild(kolesa);
                    auto = assetManager.loadModel("Models/H3/zaklad zlty.j3o");
                    kolesa = assetManager.loadModel("Models/H3/kolesa/kola 0.j3o");
                    preparecar(auto);
                    preparecar(kolesa);
                    auto.setLocalRotation(x);
                    kolesa.setLocalRotation(x);
                    rootNode.attachChild(auto);
                    rootNode.attachChild(kolesa);
                    colorH = CarColorHummer.YELLOW;
                }
                else if (number == 1 && colorH == CarColorHummer.YELLOW){
                    Quaternion x = auto.getLocalRotation();
                    rootNode.detachChild(auto);
                    rootNode.detachChild(prednyNaraznik);
                    rootNode.detachChild(zadnyNaraznik);
                    rootNode.detachChild(kolesa);
                    auto = assetManager.loadModel("Models/H3/zaklad cervena .j3o");
                    kolesa = assetManager.loadModel("Models/H3/kolesa/kola 0.j3o");
                    preparecar(auto);
                    preparecar(kolesa);
                    auto.setLocalRotation(x);
                    kolesa.setLocalRotation(x);
                    rootNode.attachChild(auto);
                    rootNode.attachChild(kolesa);
                    colorH = CarColorHummer.RED;
                }
                else if (number == 1 && colorH == CarColorHummer.RED){
                    Quaternion x = auto.getLocalRotation();
                    rootNode.detachChild(auto);
                    rootNode.detachChild(prednyNaraznik);
                    rootNode.detachChild(zadnyNaraznik);
                    rootNode.detachChild(kolesa);
                    auto = assetManager.loadModel("Models/H3/zaklad siva.j3o");
                    kolesa = assetManager.loadModel("Models/H3/kolesa/kola 0.j3o");
                    preparecar(auto);
                    preparecar(kolesa);
                    auto.setLocalRotation(x);
                    kolesa.setLocalRotation(x);
                    rootNode.attachChild(auto);
                    rootNode.attachChild(kolesa);
                    colorH = CarColorHummer.GREY;
                }
                else if (number == 1 && colorH == CarColorHummer.GREY){
                    Quaternion x = auto.getLocalRotation();
                    rootNode.detachChild(auto);
                    rootNode.detachChild(prednyNaraznik);
                    rootNode.detachChild(zadnyNaraznik);
                    rootNode.detachChild(kolesa);
                    auto = assetManager.loadModel("Models/H3/zaklad zelena.j3o");
                    kolesa = assetManager.loadModel("Models/H3/kolesa/kola 0.j3o");
                    preparecar(auto);
                    preparecar(kolesa);
                    auto.setLocalRotation(x);
                    kolesa.setLocalRotation(x);
                    rootNode.attachChild(auto);
                    rootNode.attachChild(kolesa);
                    colorH = CarColorHummer.GREEN;
                }
                else if (number == 1 && colorH == CarColorHummer.GREEN){
                    Quaternion x = auto.getLocalRotation();
                    rootNode.detachChild(auto);
                    rootNode.detachChild(prednyNaraznik);
                    rootNode.detachChild(zadnyNaraznik);
                    rootNode.detachChild(kolesa);
                    auto = assetManager.loadModel("Models/H3/zaklad biela.j3o");
                    kolesa = assetManager.loadModel("Models/H3/kolesa/kola 0.j3o");
                    preparecar(auto);
                    preparecar(kolesa);
                    auto.setLocalRotation(x);
                    kolesa.setLocalRotation(x);
                    rootNode.attachChild(auto);
                    rootNode.attachChild(kolesa);
                    colorH = CarColorHummer.WHITE;
                }
                else if (number == 1 && colorH == CarColorHummer.WHITE){
                    Quaternion x = auto.getLocalRotation();
                    rootNode.detachChild(auto);
                    rootNode.detachChild(prednyNaraznik);
                    rootNode.detachChild(zadnyNaraznik);
                    rootNode.detachChild(kolesa);
                    auto = assetManager.loadModel("Models/H3/zaklad modra.j3o");
                    kolesa = assetManager.loadModel("Models/H3/kolesa/kola 0.j3o");
                    preparecar(auto);
                    preparecar(kolesa);
                    auto.setLocalRotation(x);
                    kolesa.setLocalRotation(x);
                    rootNode.attachChild(auto);
                    rootNode.attachChild(kolesa);
                    colorH = CarColorHummer.BLUE;
                }
                else if (number == 1 && colorH == CarColorHummer.BLUE){
                    Quaternion x = auto.getLocalRotation();
                    rootNode.detachChild(auto);
                    rootNode.detachChild(prednyNaraznik);
                    rootNode.detachChild(zadnyNaraznik);
                    rootNode.detachChild(kolesa);
                    auto = assetManager.loadModel("Models/H3/zaklad cierna.j3o");
                    kolesa = assetManager.loadModel("Models/H3/kolesa/kola 0.j3o");
                    preparecar(auto);
                    preparecar(kolesa);
                    auto.setLocalRotation(x);
                    kolesa.setLocalRotation(x);
                    rootNode.attachChild(auto);
                    rootNode.attachChild(kolesa);
                    colorH = CarColorHummer.BLACK;
                }
                else if (number == 1 && colorH == CarColorHummer.BLACK){
                    Quaternion x = auto.getLocalRotation();
                    rootNode.detachChild(auto);
                    rootNode.detachChild(prednyNaraznik);
                    rootNode.detachChild(zadnyNaraznik);
                    rootNode.detachChild(kolesa);
                    auto = assetManager.loadModel("Models/H3/zaklad strieborna.j3o");
                    kolesa = assetManager.loadModel("Models/H3/kolesa/kola 0.j3o");
                    preparecar(auto);
                    preparecar(kolesa);
                    auto.setLocalRotation(x);
                    kolesa.setLocalRotation(x);
                    rootNode.attachChild(auto);
                    rootNode.attachChild(kolesa);
                    colorH = CarColorHummer.SILVER;
                } 
        }
        
        /**
         * Pomocná metóda pre obnovu 3D modelu auta využívajúca sa pri načítaní uloženej hry.
         */
        public void refreshCar(){
            changeModelForward();
            changeModelBackward();
            this.changeCarColorForward();
            this.changeCarColorBackward();
            this.changeFrontBumperForward();
            this.changeFrontBumperBackward();
            this.changeBackBumperForward();
            this.changeBackBumperBackward();
            this.changeWheelsForward();
            this.changeWheelsBackward();
        }
        
        /**
         * Metóda pre zmenu predného náraznika modelu auta inkrementatívne.
         */
        public void changeFrontBumperForward(){
            if(number == 2 && colorC == CarColorCarrera.SILVER && FBnumber == 0){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik predny/SIVY1.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 1;
            }
            else if(number == 2 && colorC == CarColorCarrera.SILVER && FBnumber == 1){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik predny/SIVY2f .j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 2;
            }
            else if(number == 2 && colorC == CarColorCarrera.SILVER && FBnumber == 2){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik predny/SIVY3f.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 3;
            }
            else if(number == 2 && colorC == CarColorCarrera.SILVER && FBnumber == 3){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik predny/SIVY0.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 0;
            }
            else if(number == 2 && colorC == CarColorCarrera.RED && FBnumber == 0){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik predny/cerveny 1.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 1;
            }
            else if(number == 2 && colorC == CarColorCarrera.RED && FBnumber == 1){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik predny/cerveny 2k.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 2;
            }
            else if(number == 2 && colorC == CarColorCarrera.RED && FBnumber == 2){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik predny/cerveny 3 .j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 3;
            }
            else if(number == 2 && colorC == CarColorCarrera.RED && FBnumber == 3){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik predny/cerveny0.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 0;
            }
            else if(number == 2 && colorC == CarColorCarrera.BLUE && FBnumber == 0){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik predny/modry 1.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 1;
            }
            else if(number == 2 && colorC == CarColorCarrera.BLUE && FBnumber == 1){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik predny/modry 2.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 2;
            }
            else if(number == 2 && colorC == CarColorCarrera.BLUE && FBnumber == 2){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik predny/modry 3.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 3;
            }
            else if(number == 2 && colorC == CarColorCarrera.BLUE && FBnumber == 3){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik predny/modry 0.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 0;
            }
            else if(number == 2 && colorC == CarColorCarrera.YELLOW && FBnumber == 0){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik predny/zlty 1.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 1;
            }
            else if(number == 2 && colorC == CarColorCarrera.YELLOW && FBnumber == 1){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik predny/zlty 2.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 2;
            }
            else if(number == 2 && colorC == CarColorCarrera.YELLOW && FBnumber == 2){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik predny/zlty 3.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 3;
            }
            else if(number == 2 && colorC == CarColorCarrera.YELLOW && FBnumber == 3){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik predny/zlty 0.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 0;
            }
            
           //
            else if(number == 0 && colorM == CarColorMercedes.AZURE && FBnumber == 0){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/CLK/predny naraznik/zeleny 1.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 1;
            }
            else if(number == 0 && colorM == CarColorMercedes.AZURE && FBnumber == 1){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/CLK/predny naraznik/zeleny 2.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 2;
            }
            else if(number == 0 && colorM == CarColorMercedes.AZURE && FBnumber == 2){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/CLK/predny naraznik/zeleny 3.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 3;
            }
            else if(number == 0 && colorM == CarColorMercedes.AZURE && FBnumber == 3){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/CLK/predny naraznik/zeleny 0.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 0;
            }
            else if(number == 0 && colorM == CarColorMercedes.BLUE && FBnumber == 0){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/CLK/predny naraznik/modry 1.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 1;
            }
            else if(number == 0 && colorM == CarColorMercedes.BLUE && FBnumber == 1){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/CLK/predny naraznik/modry 2.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 2;
            }
            else if(number == 0 && colorM == CarColorMercedes.BLUE && FBnumber == 2){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/CLK/predny naraznik/modry 3.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 3;
            }
            else if(number == 0 && colorM == CarColorMercedes.BLUE && FBnumber == 3){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/CLK/predny naraznik/modry 0.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 0;
            }
            else if(number == 0 && colorM == CarColorMercedes.GOLD && FBnumber == 0){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/CLK/predny naraznik/oranzovy 1.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 1;
            }
            else if(number == 0 && colorM == CarColorMercedes.GOLD && FBnumber == 1){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/CLK/predny naraznik/oranzovy 2.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 2;
            }
            else if(number == 0 && colorM == CarColorMercedes.GOLD && FBnumber == 2){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/CLK/predny naraznik/oranzovy 3.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 3;
            }
            else if(number == 0 && colorM == CarColorMercedes.GOLD && FBnumber == 3){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/CLK/predny naraznik/oranzovy 0.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 0;
            }
            else if(number == 0 && colorM == CarColorMercedes.SILVER && FBnumber == 0){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/CLK/predny naraznik/sivy 1.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 1;
            }
            else if(number == 0 && colorM == CarColorMercedes.SILVER && FBnumber == 1){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/CLK/predny naraznik/sivy 2.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 2;
            }
            else if(number == 0 && colorM == CarColorMercedes.SILVER && FBnumber == 2){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/CLK/predny naraznik/sivy 3.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 3;
            }
            else if(number == 0 && colorM == CarColorMercedes.SILVER && FBnumber == 3){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/CLK/predny naraznik/sivy 0.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 0;
            }
        }
        
        /**
         * Metóda pre zmenu predného náraznika modelu auta dekrementatívne.
         */
        public void changeFrontBumperBackward(){
            if(number == 2 && colorC == CarColorCarrera.SILVER && FBnumber == 0){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik predny/SIVY3f.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 3;
            }
            else if(number == 2 && colorC == CarColorCarrera.SILVER && FBnumber == 3){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik predny/SIVY2f .j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 2;
            }
            else if(number == 2 && colorC == CarColorCarrera.SILVER && FBnumber == 2){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik predny/SIVY1.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 1;
            }
            else if(number == 2 && colorC == CarColorCarrera.SILVER && FBnumber == 1){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik predny/SIVY0.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 0;
            }
            else if(number == 2 && colorC == CarColorCarrera.RED && FBnumber == 0){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik predny/cerveny 3 .j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 3;
            }
            else if(number == 2 && colorC == CarColorCarrera.RED && FBnumber == 3){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik predny/cerveny 2k.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 2;
            }
            else if(number == 2 && colorC == CarColorCarrera.RED && FBnumber == 2){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik predny/cerveny 1.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 1;
            }
            else if(number == 2 && colorC == CarColorCarrera.RED && FBnumber == 1){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik predny/cerveny0.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 0;
            }
            else if(number == 2 && colorC == CarColorCarrera.BLUE && FBnumber == 0){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik predny/modry 3.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 3;
            }
            else if(number == 2 && colorC == CarColorCarrera.BLUE && FBnumber == 3){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik predny/modry 2.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 2;
            }
            else if(number == 2 && colorC == CarColorCarrera.BLUE && FBnumber == 2){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik predny/modry 1.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 1;
            }
            else if(number == 2 && colorC == CarColorCarrera.BLUE && FBnumber == 1){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik predny/modry 0.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 0;
            }
            else if(number == 2 && colorC == CarColorCarrera.YELLOW && FBnumber == 0){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik predny/zlty 3.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 3;
            }
            else if(number == 2 && colorC == CarColorCarrera.YELLOW && FBnumber == 3){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik predny/zlty 2.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 2;
            }
            else if(number == 2 && colorC == CarColorCarrera.YELLOW && FBnumber == 2){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik predny/zlty 1.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 1;
            }
            else if(number == 2 && colorC == CarColorCarrera.YELLOW && FBnumber == 1){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik predny/zlty 0.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 0;
            }
            //
            else if(number == 0 && colorM == CarColorMercedes.AZURE && FBnumber == 2){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/CLK/predny naraznik/zeleny 1.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 1;
            }
            else if(number == 0 && colorM == CarColorMercedes.AZURE && FBnumber == 3){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/CLK/predny naraznik/zeleny 2.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 2;
            }
            else if(number == 0 && colorM == CarColorMercedes.AZURE && FBnumber == 0){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/CLK/predny naraznik/zeleny 3.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 3;
            }
            else if(number == 0 && colorM == CarColorMercedes.AZURE && FBnumber == 1){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/CLK/predny naraznik/zeleny 0.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 0;
            }
            else if(number == 0 && colorM == CarColorMercedes.BLUE && FBnumber == 2){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/CLK/predny naraznik/modry 1.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 1;
            }
            else if(number == 0 && colorM == CarColorMercedes.BLUE && FBnumber == 3){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/CLK/predny naraznik/modry 2.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 2;
            }
            else if(number == 0 && colorM == CarColorMercedes.BLUE && FBnumber == 0){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/CLK/predny naraznik/modry 3.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 3;
            }
            else if(number == 0 && colorM == CarColorMercedes.BLUE && FBnumber == 1){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/CLK/predny naraznik/modry 0.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 0;
            }
            else if(number == 0 && colorM == CarColorMercedes.GOLD && FBnumber == 2){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/CLK/predny naraznik/oranzovy 1.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 1;
            }
            else if(number == 0 && colorM == CarColorMercedes.GOLD && FBnumber == 3){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/CLK/predny naraznik/oranzovy 2.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 2;
            }
            else if(number == 0 && colorM == CarColorMercedes.GOLD && FBnumber == 0){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/CLK/predny naraznik/oranzovy 3.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 3;
            }
            else if(number == 0 && colorM == CarColorMercedes.GOLD && FBnumber == 1){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/CLK/predny naraznik/oranzovy 0.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 0;
            }
            else if(number == 0 && colorM == CarColorMercedes.SILVER && FBnumber == 2){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/CLK/predny naraznik/sivy 1.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 1;
            }
            else if(number == 0 && colorM == CarColorMercedes.SILVER && FBnumber == 3){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/CLK/predny naraznik/sivy 2.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 2;
            }
            else if(number == 0 && colorM == CarColorMercedes.SILVER && FBnumber == 0){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/CLK/predny naraznik/sivy 3.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 3;
            }
            else if(number == 0 && colorM == CarColorMercedes.SILVER && FBnumber == 1){
                Quaternion x = prednyNaraznik.getLocalRotation();
                rootNode.detachChild(prednyNaraznik);
                prednyNaraznik = assetManager.loadModel("Models/CLK/predny naraznik/sivy 0.j3o");
                preparecar(prednyNaraznik);
                prednyNaraznik.setLocalRotation(x);
                rootNode.attachChild(prednyNaraznik);
                FBnumber = 0;
            }
        }
        
        /**
         * Metóda pre zmenu zadného náraznika modelu auta dekrementatívne.
         */
        public void changeBackBumperBackward(){
            if(number == 2 && colorC == CarColorCarrera.SILVER && BBnumber == 0){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik zadny/sivy 3.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 3;
            }
            else if(number == 2 && colorC == CarColorCarrera.SILVER && BBnumber == 3){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik zadny/sivy 2.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 2;
            }
            else if(number == 2 && colorC == CarColorCarrera.SILVER && BBnumber == 2){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik zadny/sivy 1OKK.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 1;
            }
            else if(number == 2 && colorC == CarColorCarrera.SILVER && BBnumber == 1){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik zadny/sivy 0.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 0;
            }
            else if(number == 2 && colorC == CarColorCarrera.RED && BBnumber == 0){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik zadny/cerveny 3.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 3;
            }
            else if(number == 2 && colorC == CarColorCarrera.RED && BBnumber == 3){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik zadny/cerveny 2.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 2;
            }
            else if(number == 2 && colorC == CarColorCarrera.RED && BBnumber == 2){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik zadny/cerveny 1.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 1;
            }
            else if(number == 2 && colorC == CarColorCarrera.RED && BBnumber == 1){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik zadny/cerveny 0.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 0;
            }
            else if(number == 2 && colorC == CarColorCarrera.BLUE && BBnumber == 0){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik zadny/modry 3.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 3;
            }
            else if(number == 2 && colorC == CarColorCarrera.BLUE && BBnumber == 3){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik zadny/modry 2.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 2;
            }
            else if(number == 2 && colorC == CarColorCarrera.BLUE && BBnumber == 2){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik zadny/modry 1.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 1;
            }
            else if(number == 2 && colorC == CarColorCarrera.BLUE && BBnumber == 1){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik zadny/modry 0.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 0;
            }
            else if(number == 2 && colorC == CarColorCarrera.YELLOW && BBnumber == 0){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik zadny/zlty 3.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 3;
            }
            else if(number == 2 && colorC == CarColorCarrera.YELLOW && BBnumber == 3){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik zadny/zlty 2.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 2;
            }
            else if(number == 2 && colorC == CarColorCarrera.YELLOW && BBnumber == 2){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik zadny/zlty 1.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 1;
            }
            else if(number == 2 && colorC == CarColorCarrera.YELLOW && BBnumber == 1){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik zadny/zlty 0.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 0;
            }
            //
            if(number == 0 && colorM == CarColorMercedes.AZURE && BBnumber == 0){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/CLK/zadny naraznik/zeleny 3.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 3;
            }
            else if(number == 0 && colorM == CarColorMercedes.AZURE && BBnumber == 3){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/CLK/zadny naraznik/zeleny 2.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 2;
            }
            else if(number == 0 && colorM == CarColorMercedes.AZURE && BBnumber == 2){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/CLK/zadny naraznik/zeleny 1.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 1;
            }
            else if(number == 0 && colorM == CarColorMercedes.AZURE && BBnumber == 1){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/CLK/zadny naraznik/zeleny 0.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 0;
            }
            else if(number == 0 && colorM == CarColorMercedes.BLUE && BBnumber == 0){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/CLK/zadny naraznik/modry 3.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 3;
            }
            else if(number == 0 && colorM == CarColorMercedes.BLUE && BBnumber == 3){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/CLK/zadny naraznik/modry 2.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 2;
            }
            else if(number == 0 && colorM == CarColorMercedes.BLUE && BBnumber == 2){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/CLK/zadny naraznik/modry 1.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 1;
            }
            else if(number == 0 && colorM == CarColorMercedes.BLUE && BBnumber == 1){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/CLK/zadny naraznik/modry 0.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 0;
            }
            else if(number == 0 && colorM == CarColorMercedes.GOLD && BBnumber == 0){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/CLK/zadny naraznik/oranzovy 3.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 3;
            }
            else if(number == 0 && colorM == CarColorMercedes.GOLD && BBnumber == 3){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/CLK/zadny naraznik/oranzovy 2.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 2;
            }
            else if(number == 0 && colorM == CarColorMercedes.GOLD && BBnumber == 2){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/CLK/zadny naraznik/oranzovy 1.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 1;
            }
            else if(number == 0 && colorM == CarColorMercedes.GOLD && BBnumber == 1){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/CLK/zadny naraznik/oranzovy 0.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 0;
            }
            else if(number == 0 && colorM == CarColorMercedes.SILVER && BBnumber == 0){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/CLK/zadny naraznik/siva 3.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 3;
            }
            else if(number == 0 && colorM == CarColorMercedes.SILVER && BBnumber == 3){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/CLK/zadny naraznik/sivy 2.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 2;
            }
            else if(number == 0 && colorM == CarColorMercedes.SILVER && BBnumber == 2){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/CLK/zadny naraznik/sivy 1.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 1;
            }
            else if(number == 0 && colorM == CarColorMercedes.SILVER && BBnumber == 1){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/CLK/zadny naraznik/sivy 0.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 0;
            }
        }
        
        /**
         * Metóda pre zmenu zadného náraznika modelu auta inkrementatívne.
         */
        public void changeBackBumperForward(){
            if(number == 2 && colorC == CarColorCarrera.SILVER && BBnumber == 0){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik zadny/sivy 1OKK.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 1;
            }
            else if(number == 2 && colorC == CarColorCarrera.SILVER && BBnumber == 1){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik zadny/sivy 2.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 2;
            }
            else if(number == 2 && colorC == CarColorCarrera.SILVER && BBnumber == 2){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik zadny/sivy 3.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 3;
            }
            else if(number == 2 && colorC == CarColorCarrera.SILVER && BBnumber == 3){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik zadny/sivy 0.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 0;
            }
            else if(number == 2 && colorC == CarColorCarrera.RED && BBnumber == 0){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik zadny/cerveny 1.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 1;
            }
            else if(number == 2 && colorC == CarColorCarrera.RED && BBnumber == 1){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik zadny/cerveny 2.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 2;
            }
            else if(number == 2 && colorC == CarColorCarrera.RED && BBnumber == 2){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik zadny/cerveny 3.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 3;
            }
            else if(number == 2 && colorC == CarColorCarrera.RED && BBnumber == 3){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik zadny/cerveny 0.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 0;
            }
            else if(number == 2 && colorC == CarColorCarrera.BLUE && BBnumber == 0){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik zadny/modry 1.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 1;
            }
            else if(number == 2 && colorC == CarColorCarrera.BLUE && BBnumber == 1){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik zadny/modry 2.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 2;
            }
            else if(number == 2 && colorC == CarColorCarrera.BLUE && BBnumber == 2){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik zadny/modry 3.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 3;
            }
            else if(number == 2 && colorC == CarColorCarrera.BLUE && BBnumber == 3){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik zadny/modry 0.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 0;
            }
            else if(number == 2 && colorC == CarColorCarrera.YELLOW && BBnumber == 0){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik zadny/zlty 1.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 1;
            }
            else if(number == 2 && colorC == CarColorCarrera.YELLOW && BBnumber == 1){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik zadny/zlty 2.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 2;
            }
            else if(number == 2 && colorC == CarColorCarrera.YELLOW && BBnumber == 2){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik zadny/zlty 3.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 3;
            }
            else if(number == 2 && colorC == CarColorCarrera.YELLOW && BBnumber == 3){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/PorscheCarrera/naraznik zadny/zlty 0.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 0;
            }
            //
            if(number == 0 && colorM == CarColorMercedes.AZURE && BBnumber == 2){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/CLK/zadny naraznik/zeleny 3.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 3;
            }
            else if(number == 0 && colorM == CarColorMercedes.AZURE && BBnumber == 1){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/CLK/zadny naraznik/zeleny 2.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 2;
            }
            else if(number == 0 && colorM == CarColorMercedes.AZURE && BBnumber == 0){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/CLK/zadny naraznik/zeleny 1.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 1;
            }
            else if(number == 0 && colorM == CarColorMercedes.AZURE && BBnumber == 3){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/CLK/zadny naraznik/zeleny 0.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 0;
            }
            else if(number == 0 && colorM == CarColorMercedes.BLUE && BBnumber == 2){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/CLK/zadny naraznik/modry 3.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 3;
            }
            else if(number == 0 && colorM == CarColorMercedes.BLUE && BBnumber == 1){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/CLK/zadny naraznik/modry 2.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 2;
            }
            else if(number == 0 && colorM == CarColorMercedes.BLUE && BBnumber == 0){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/CLK/zadny naraznik/modry 1.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 1;
            }
            else if(number == 0 && colorM == CarColorMercedes.BLUE && BBnumber == 3){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/CLK/zadny naraznik/modry 0.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 0;
            }
            else if(number == 0 && colorM == CarColorMercedes.GOLD && BBnumber == 2){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/CLK/zadny naraznik/oranzovy 3.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 3;
            }
            else if(number == 0 && colorM == CarColorMercedes.GOLD && BBnumber == 1){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/CLK/zadny naraznik/oranzovy 2.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 2;
            }
            else if(number == 0 && colorM == CarColorMercedes.GOLD && BBnumber == 0){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/CLK/zadny naraznik/oranzovy 1.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 1;
            }
            else if(number == 0 && colorM == CarColorMercedes.GOLD && BBnumber == 3){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/CLK/zadny naraznik/oranzovy 0.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 0;
            }
            else if(number == 0 && colorM == CarColorMercedes.SILVER && BBnumber == 2){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/CLK/zadny naraznik/siva 3.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 3;
            }
            else if(number == 0 && colorM == CarColorMercedes.SILVER && BBnumber == 1){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/CLK/zadny naraznik/sivy 2.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 2;
            }
            else if(number == 0 && colorM == CarColorMercedes.SILVER && BBnumber == 0){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/CLK/zadny naraznik/sivy 1.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 1;
            }
            else if(number == 0 && colorM == CarColorMercedes.SILVER && BBnumber == 3){
                Quaternion x = zadnyNaraznik.getLocalRotation();
                rootNode.detachChild(zadnyNaraznik);
                zadnyNaraznik = assetManager.loadModel("Models/CLK/zadny naraznik/sivy 0.j3o");
                preparecar(zadnyNaraznik);
                zadnyNaraznik.setLocalRotation(x);
                rootNode.attachChild(zadnyNaraznik);
                BBnumber = 0;
            }
        }
        
        /**
         * Metóda pre zmenu kolies modelu auta inkrementatívne.
         */
        public void changeWheelsForward(){
            if(number == 2 && WHnumber == 0){
                Quaternion x = kolesa.getLocalRotation();
                rootNode.detachChild(kolesa);
                kolesa = assetManager.loadModel("Models/PorscheCarrera/kolesa/kola 1.j3o");
                preparecar(kolesa);
                kolesa.setLocalRotation(x);
                rootNode.attachChild(kolesa);
                WHnumber = 1;
            }
            else if(number == 2 && WHnumber == 1){
                Quaternion x = kolesa.getLocalRotation();
                rootNode.detachChild(kolesa);
                kolesa = assetManager.loadModel("Models/PorscheCarrera/kolesa/kola 2.j3o");
                preparecar(kolesa);
                kolesa.setLocalRotation(x);
                rootNode.attachChild(kolesa);
                WHnumber = 2;
            }
            else if(number == 2 && WHnumber == 2){
                Quaternion x = kolesa.getLocalRotation();
                rootNode.detachChild(kolesa);
                kolesa = assetManager.loadModel("Models/PorscheCarrera/kolesa/kola 3.j3o");
                preparecar(kolesa);
                kolesa.setLocalRotation(x);
                rootNode.attachChild(kolesa);
                WHnumber = 3;
            }
            else if(number == 2 && WHnumber == 3){
                Quaternion x = kolesa.getLocalRotation();
                rootNode.detachChild(kolesa);
                kolesa = assetManager.loadModel("Models/PorscheCarrera/kolesa/kola 0.j3o");
                preparecar(kolesa);
                kolesa.setLocalRotation(x);
                rootNode.attachChild(kolesa);
                WHnumber = 0;
            }
            if(number == 0 && WHnumber == 0){
                Quaternion x = kolesa.getLocalRotation();
                rootNode.detachChild(kolesa);
                kolesa = assetManager.loadModel("Models/CLK/kola/kola 1.j3o");
                preparecar(kolesa);
                kolesa.setLocalRotation(x);
                rootNode.attachChild(kolesa);
                WHnumber = 1;
            }
            else if(number == 0 && WHnumber == 1){
                Quaternion x = kolesa.getLocalRotation();
                rootNode.detachChild(kolesa);
                kolesa = assetManager.loadModel("Models/CLK/kola/kola 2.j3o");
                preparecar(kolesa);
                kolesa.setLocalRotation(x);
                rootNode.attachChild(kolesa);
                WHnumber = 2;
            }
            else if(number == 0 && WHnumber == 2){
                Quaternion x = kolesa.getLocalRotation();
                rootNode.detachChild(kolesa);
                kolesa = assetManager.loadModel("Models/CLK/kola/kola 3.j3o");
                preparecar(kolesa);
                kolesa.setLocalRotation(x);
                rootNode.attachChild(kolesa);
                WHnumber = 3;
            }
            else if(number == 0 && WHnumber == 3){
                Quaternion x = kolesa.getLocalRotation();
                rootNode.detachChild(kolesa);
                kolesa = assetManager.loadModel("Models/CLK/kola/kola 0.j3o");
                preparecar(kolesa);
                kolesa.setLocalRotation(x);
                rootNode.attachChild(kolesa);
                WHnumber = 0;
            }
            if(number == 1 && WHnumber == 0){
                Quaternion x = kolesa.getLocalRotation();
                rootNode.detachChild(kolesa);
                kolesa = assetManager.loadModel("Models/H3/kolesa/kola 1.j3o");
                preparecar(kolesa);
                kolesa.setLocalRotation(x);
                rootNode.attachChild(kolesa);
                WHnumber = 1;
            }
            else if(number == 1 && WHnumber == 1){
                Quaternion x = kolesa.getLocalRotation();
                rootNode.detachChild(kolesa);
                kolesa = assetManager.loadModel("Models/H3/kolesa/kola 2.j3o");
                preparecar(kolesa);
                kolesa.setLocalRotation(x);
                rootNode.attachChild(kolesa);
                WHnumber = 2;
            }
            else if(number == 1 && WHnumber == 2){
                Quaternion x = kolesa.getLocalRotation();
                rootNode.detachChild(kolesa);
                kolesa = assetManager.loadModel("Models/H3/kolesa/kola 3.j3o");
                preparecar(kolesa);
                kolesa.setLocalRotation(x);
                rootNode.attachChild(kolesa);
                WHnumber = 3;
            }
            else if(number == 1 && WHnumber == 3){
                Quaternion x = kolesa.getLocalRotation();
                rootNode.detachChild(kolesa);
                kolesa = assetManager.loadModel("Models/H3/kolesa/kola 0.j3o");
                preparecar(kolesa);
                kolesa.setLocalRotation(x);
                rootNode.attachChild(kolesa);
                WHnumber = 0;
            }
        }
        
        /**
         * Metóda pre zmenu kolies modelu auta dekrementatívne.
         */
        public void changeWheelsBackward(){
            if(number == 2 && WHnumber == 0){
                Quaternion x = kolesa.getLocalRotation();
                rootNode.detachChild(kolesa);
                kolesa = assetManager.loadModel("Models/PorscheCarrera/kolesa/kola 3.j3o");
                preparecar(kolesa);
                kolesa.setLocalRotation(x);
                rootNode.attachChild(kolesa);
                WHnumber = 3;
            }
            else if(number == 2 && WHnumber == 1){
                Quaternion x = kolesa.getLocalRotation();
                rootNode.detachChild(kolesa);
                kolesa = assetManager.loadModel("Models/PorscheCarrera/kolesa/kola 0.j3o");
                preparecar(kolesa);
                kolesa.setLocalRotation(x);
                rootNode.attachChild(kolesa);
                WHnumber = 0;
            }
            else if(number == 2 && WHnumber == 2){
                Quaternion x = kolesa.getLocalRotation();
                rootNode.detachChild(kolesa);
                kolesa = assetManager.loadModel("Models/PorscheCarrera/kolesa/kola 1.j3o");
                preparecar(kolesa);
                kolesa.setLocalRotation(x);
                rootNode.attachChild(kolesa);
                WHnumber = 1;
            }
            else if(number == 2 && WHnumber == 3){
                Quaternion x = kolesa.getLocalRotation();
                rootNode.detachChild(kolesa);
                kolesa = assetManager.loadModel("Models/PorscheCarrera/kolesa/kola 2.j3o");
                preparecar(kolesa);
                kolesa.setLocalRotation(x);
                rootNode.attachChild(kolesa);
                WHnumber = 2;
            }
            if(number == 0 && WHnumber == 0){
                Quaternion x = kolesa.getLocalRotation();
                rootNode.detachChild(kolesa);
                kolesa = assetManager.loadModel("Models/CLK/kola/kola 3.j3o");
                preparecar(kolesa);
                kolesa.setLocalRotation(x);
                rootNode.attachChild(kolesa);
                WHnumber = 3;
            }
            else if(number == 0 && WHnumber == 1){
                Quaternion x = kolesa.getLocalRotation();
                rootNode.detachChild(kolesa);
                kolesa = assetManager.loadModel("Models/CLK/kola/kola 0.j3o");
                preparecar(kolesa);
                kolesa.setLocalRotation(x);
                rootNode.attachChild(kolesa);
                WHnumber = 0;
            }
            else if(number == 0 && WHnumber == 2){
                Quaternion x = kolesa.getLocalRotation();
                rootNode.detachChild(kolesa);
                kolesa = assetManager.loadModel("Models/CLK/kola/kola 1.j3o");
                preparecar(kolesa);
                kolesa.setLocalRotation(x);
                rootNode.attachChild(kolesa);
                WHnumber = 1;
            }
            else if(number == 0 && WHnumber == 3){
                Quaternion x = kolesa.getLocalRotation();
                rootNode.detachChild(kolesa);
                kolesa = assetManager.loadModel("Models/CLK/kola/kola 2.j3o");
                preparecar(kolesa);
                kolesa.setLocalRotation(x);
                rootNode.attachChild(kolesa);
                WHnumber = 2;
            }
            if(number == 1 && WHnumber == 0){
                Quaternion x = kolesa.getLocalRotation();
                rootNode.detachChild(kolesa);
                kolesa = assetManager.loadModel("Models/H3/kolesa/kola 3.j3o");
                preparecar(kolesa);
                kolesa.setLocalRotation(x);
                rootNode.attachChild(kolesa);
                WHnumber = 3;
            }
            else if(number == 1 && WHnumber == 1){
                Quaternion x = kolesa.getLocalRotation();
                rootNode.detachChild(kolesa);
                kolesa = assetManager.loadModel("Models/H3/kolesa/kola 0.j3o");
                preparecar(kolesa);
                kolesa.setLocalRotation(x);
                rootNode.attachChild(kolesa);
                WHnumber = 0;
            }
            else if(number == 1 && WHnumber == 2){
                Quaternion x = kolesa.getLocalRotation();
                rootNode.detachChild(kolesa);
                kolesa = assetManager.loadModel("Models/H3/kolesa/kola 1.j3o");
                preparecar(kolesa);
                kolesa.setLocalRotation(x);
                rootNode.attachChild(kolesa);
                WHnumber = 1;
            }
            else if(number == 1 && WHnumber == 3){
                Quaternion x = kolesa.getLocalRotation();
                rootNode.detachChild(kolesa);
                kolesa = assetManager.loadModel("Models/H3/kolesa/kola 2.j3o");
                preparecar(kolesa);
                kolesa.setLocalRotation(x);
                rootNode.attachChild(kolesa);
                WHnumber = 2;
            }
        }
        
}