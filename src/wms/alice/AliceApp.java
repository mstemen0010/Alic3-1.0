/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wms.alice;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author MStemen
 */
public class AliceApp extends Application {
    
    static boolean setTest = false;
    
    String appImageName = "alice/whiteRabbit_classic_150.jpg";
    
    String appImageName15 = "alice/icons/whiteRabbit_classic_15.jpg";
    String appImageName32 = "alice/icons/whiteRabbit_classic_32.jpg";
    String appImageName64 = "alice/icons/whiteRabbit_classic_64.jpg";
    Image appIconImage15 = new Image(appImageName15, true);
    Image appIconImage32 = new Image(appImageName15, true);
    Image appIconImage64 = new Image(appImageName15, true);
    
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Alice.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.getIcons().add(appIconImage15);
        stage.getIcons().add(appIconImage32);
        stage.getIcons().add(appIconImage64);
        stage.setTitle("Alice 1.0 built on Apache Commons HttpClient 4.2");
        stage.show();
        if( setTest )
            toggleTest();
        
    }
    
    static public boolean isTest()
    {
        return setTest;
    }
    
    
    static public boolean toggleTest()
    {
        return ! setTest; 
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
