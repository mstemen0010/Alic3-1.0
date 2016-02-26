/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wms.alice;

import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import wms.alice.archtypes.AliceProtoClient;
import wms.alice.archtypes.AliceProtoClient31;
import wms.alice.archtypes.AliceProtoClientInterface;
import wms.alice.archtypes.AliceProtoClientInterface.AliceClientId;
import wms.alice.archtypes.AliceProtoClientInterface.AliceClientType;

/**
 *
 * @author Mathias
 */
class Throbber implements Runnable {

    private final ImageView throbberImageView;
    private final Stack<Image> imageStack = new Stack<>();
    private final ArrayList<Image> imageArray = new ArrayList<>(5);
    boolean running = true;
    boolean throbbing = false;
    Iterator<Image> it = null;

    public Throbber(ImageView viewToThrob) {

        throbberImageView = viewToThrob;

    }

    public void toggleThrob() {
        throbbing = !throbbing;
    }

    @Override
    public void run() {
        it = imageArray.iterator();
        Image throbImage;
        while (running) {
            while (throbbing) {

                if (it.hasNext()) {
                    throbImage = it.next();
                } else {
                    it = imageArray.iterator();
                    throbImage = it.next();
                }
                if (throbImage != null) {
                    this.throbberImageView.setImage(throbImage);
                    System.out.println("Image is: " + throbImage.toString());
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Throbber.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Throbber.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void stop() {
        running = false;
    }

    public void addImage(Image newImageToAdd) {
        imageArray.add(newImageToAdd);
    }
}

public class Alic3Controller implements Initializable {

    @FXML
    Stage myStage;

    @FXML
    AnchorPane anchorPane;

    @FXML
    ComboBox clientTypeCombo;

    @FXML
    ComboBox clientIdCombo;

    @FXML
    ImageView throbberImageView;
    @FXML
    private TextField textfeild;
    @FXML
    private Label label;
    @FXML
    private Label statusLabel;
    @FXML
    private WebView webViewResponse;
    @FXML
    private WebView webViewContent;
    // private ProgressBar progBar;
    private AliceProtoClientInterface alice;
    // below was: sb = null;
    static StringBuilder pageBuffer = null;
    Image throbberImage1 = null;
    Image throbberImage2 = null;
    Image throbberImage3 = null;
    Scene myScene;
    Throbber wrThrobber = null;
    Thread throbberThread = null;

    public Alic3Controller() {
        // this.wrappedClient = new HttpClient();
        String im1Path = "wms/alice/whiteRabbit_classic_150.jpg";
        String im2Path = "wms/alice/whiteRabbit_classic_invert_150.jpg";
        String im3Path = "wms/alice/whiteRabbit_classic_invert2_150.jpg";
        throbberImage1 = new Image(im1Path, true);
        throbberImage2 = new Image(im2Path, true);
        throbberImage3 = new Image(im3Path, true);
        pageBuffer = new StringBuilder(0);
        if (AliceApp.isTest()) {
            alice = AliceProtoClient.getInstance(AliceProtoClient31.class, AliceClientType.HttpClient31);
            alice.doGet("http://www.test.com");
            alice.release();
        }
        alice = AliceProtoClient.getInstance(AliceProtoClient31.class, AliceClientType.HttpClient31);
        alice.setClientId(AliceProtoClientInterface.AliceClientId.Alice1);
        if (AliceApp.isTest()) {
            alice.doGet("http://www.test.com");
            alice.release();
        }

    }

    @Override
    public void finalize() throws Throwable {
        try {
            this.wrThrobber.stop();
        } finally {
            super.finalize();
        }
    }

    public void setupChoices() {
        String defaultClientId = alice.getClientId();
        String defaultClientType = alice.getClientType();
        ObservableList<String> options1 = FXCollections.observableArrayList();
        ObservableList<String> options2 = FXCollections.observableArrayList();
        Iterator<String> ids = alice.getIdentIds();
        Iterator<String> types = alice.getIdentTypes();
        String idChoice = ids.next();
        options1.add(idChoice);

        String typeChoice = types.next();
        options2.add(typeChoice);
        while (ids.hasNext()) {
            String id = ids.next();
            options1.add(id);
            System.out.println("Adding: " + id);
        }
        while (types.hasNext()) {
            options2.add(types.next());
        }

        this.clientIdCombo.setItems(options1);
        this.clientIdCombo.setValue(defaultClientId);
        this.clientTypeCombo.setItems(options2);
        this.clientTypeCombo.setValue(defaultClientType);

    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        statusLabel.setText("Loading Page...");
        String val = this.textfeild.getText();
        // StringBuilder sb = new StringBuilder("http://");
        StringBuilder sb = new StringBuilder("");
        sb.append(val);
        this.go(sb.toString());
    }

//    @FXML
//    private void handleKeyPressed(Event event)
//    {
//        if( KeyEvent.class.isInstance(event))
//        {
//            KeyEvent ke = KeyEvent.class.cast(event);
//            System.out.println("Key is: " + ke.paramString());
//        }
//        System.out.println("Got Event!!");;
//    }
    @FXML
    private void handleTextFieldAction(Event event) {
        String val = this.textfeild.getText();
        // StringBuilder sb = new StringBuilder("http://");
        StringBuilder sb = new StringBuilder("");
        sb.append(val);
        this.go(sb.toString());
    }

    @FXML
    private void handleKeyPressed(Event event) {
        KeyEvent keyEvent = null;
        if (KeyEvent.class.isInstance(event)) {
            keyEvent = KeyEvent.class.cast(event);
        }
        if (keyEvent == null) {
            return;
        }
        if (keyEvent.getID() == KeyEvent.VK_DELETE || keyEvent.getID() == KeyEvent.VK_BACK_SPACE) {
            // String tb = sb.toString().substring(0, sb.length() - 1 );
            pageBuffer = new StringBuilder(pageBuffer.toString().substring(0, pageBuffer.length() - 1));
            System.out.println("Buffer is: " + pageBuffer);
        } else {
            pageBuffer.append(keyEvent.getKeyChar());
            System.out.println("Buffer is: " + pageBuffer);
        }
    }

    @FXML
    private void setClientType() {
        AliceClientType currentAliceType = alice.getAliceClientType();
        String currentComboSelect = (String) clientTypeCombo.getSelectionModel().getSelectedItem();
        if (currentComboSelect != null && currentComboSelect.equals(currentAliceType.friendlyName())) {
            AliceClientType newType = AliceClientType.fromStringName(currentComboSelect);
            alice = newType.spinClient();
            alice.setClientType(newType);
        } else if (currentComboSelect != null && !currentComboSelect.equals(currentAliceType.friendlyName())) {
            AliceClientType newType = AliceClientType.fromStringName(currentComboSelect);
//            AliceProtoClient newClient = (AliceProtoClient) newType.getProtoClass().cast(alice);
            alice = newType.spinClient();
            alice = AliceProtoClient.getInstance( newType.getProtoClass(), newType );
            alice.setClientType(newType);

        }
    }

    @FXML
    private void setClientId() {
        String currentComboSelect = null;
        if (this.anchorPane == null || !this.anchorPane.isVisible() || alice == null) {
            return;
        }
        AliceClientId currentAliceId = alice.getAliceClientId();
        if (currentComboSelect == null) {
            currentComboSelect = (String) clientIdCombo.getSelectionModel().getSelectedItem();
        }
        if (currentComboSelect != null && !currentComboSelect.equals(currentAliceId.friendlyName())) {
            AliceClientId newType = AliceClientId.fromStringName(currentComboSelect);
            alice.setClientId(newType);

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.anchorPane.getScene();
        this.setupChoices();
    }

    public void go(String urlAsString) {
        myScene = anchorPane.getScene();
        String cleanUrlAsString = makePrettyURL(urlAsString);
        if (wrThrobber == null) {
            wrThrobber = new Throbber(throbberImageView);
            wrThrobber.addImage(throbberImage1);
            wrThrobber.addImage(throbberImage2);
            wrThrobber.addImage(throbberImage3);
            throbberThread = new Thread(wrThrobber);
        }

        this.throbberImageView.setImage(throbberImage3);
        startThrobber();
        if (myStage != null) {
            myStage.setTitle(cleanUrlAsString);
        }

        HttpMethod method = new GetMethod(cleanUrlAsString);
        if (method != null) {
            statusLabel.setText("Status: Loading Page: " + cleanUrlAsString);

        }
        alice.doGet(cleanUrlAsString);
        String responseStr = alice.getReponseAsString();

        webViewResponse.getEngine().loadContent(responseStr);

        String content = alice.getContentAsString();

        webViewContent.getEngine().loadContent(content);
        alice.getStatusAsString();
        statusLabel.setText(alice.getStatusAsString());
        stopThrobber();
        alice.release();
    }

    private String makePrettyURL(String unPrettyURL) {
        StringBuilder sb = new StringBuilder("http://");
        /// first see if the passed is allready "well formed"
        String delims = "[ .,?!]+";
        String[] bits = unPrettyURL.split(delims);
        if (bits.length == 1) {
            sb.append("www.");
            sb.append(unPrettyURL);
            sb.append(".com");
        } else if (bits.length == 2) {
            if (unPrettyURL.contains("www")) {
                sb.append(unPrettyURL);
                sb.append(".com");
            } else {
                sb.append("www.");
                sb.append(unPrettyURL);
            }
        } else {
            sb = new StringBuilder(unPrettyURL);
        }

        return sb.toString();
    }

    private void startThrobber() {
        if (!this.throbberThread.isAlive()) {
            this.throbberThread.start();
        }
        this.wrThrobber.toggleThrob();
    }

    private void stopThrobber() {
        this.wrThrobber.toggleThrob();
    }
}
