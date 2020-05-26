/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hra;


import static hra.Hra.LOGGER;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Button buttonPripojit;
    @FXML
    private TextField textNick;
    @FXML
    private AnchorPane spusteni,cekani,hra;
    @FXML
    private Label aHrac,hrac1,body1,hrac2,body2;
    @FXML
    private ImageView im1,im2,im3,im4,im5,im6,im7,im8,im9,im10,im11,im12;
    
    Vysilac vysilac;
    Prijmac prijmac;
    String nick ="";
    
    @FXML
    private void tah(MouseEvent event) throws IOException, InterruptedException
    {
       if(aHrac.getText().equals(hrac1.getText()))
       {
       String nazev = event.getPickResult().getIntersectedNode().getId();
       String pomocny = nazev.substring(2);
       LOGGER.info("klikám na: " + pomocny);
       vysilac.PosliZpravu(nick,"tah",pomocny);
       }
       else
       {
           LOGGER.info("Hráč klepl na políčko ikdyž není na tahu");
           vypisChybu("Nejsi na tahu!");
       }
    }
    
    @FXML
    public void vypisChybu(String chyba) throws IOException, InterruptedException
    {
        Platform.runLater(new Runnable() 
        {
            @Override
            public void run() 
            {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Chyba!");
            alert.setHeaderText("Stala se chyba!");
            alert.setContentText(chyba);
            alert.showAndWait(); 
            }
        });
    }
    
    @FXML
    private void pripojSe() throws IOException
    {
       LOGGER.info("Hráč klepl na připoj se a čeká nyní než server najde protihráče");
       nick = textNick.getText();
       if (nick != null && !nick.equals("")) 
       {
        spusteni.setVisible(false);
        cekani.setVisible(true);
        vysilac = new Vysilac(nick);
        prijmac = new Prijmac(vysilac.getSocket(),this);
        prijmac.start();
        vysilac.PosliZpravu(nick,"zacatek","");
       }
    }
    
    public void zacniHru(Message message) throws IOException, InterruptedException
    {
      LOGGER.info("Hra začíná! - Server našel protihráče");
      cekani.setVisible(false);
      hra.setVisible(true);
     // Thread.sleep(1000);
      Platform.runLater(new Runnable() {
        @Override
        public void run() {
        aHrac.setText(message.getHrac());
            try {
                vysilac.PosliZpravu(nick, "protihrac", "");
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        });
    }
    
    public void otocKartu(Message message) throws IOException, InterruptedException
    {
      Platform.runLater(new Runnable() {
        @Override
        public void run() 
        {
            LOGGER.info("Otáčí se obrázek číslo "+message.getHodnota()+" s obrazkem +"+message.getHrac());
            Image image = new Image(getClass().getResourceAsStream(message.getHrac()+".jpg"));
          if(message.getHodnota().equals("1"))
              im1.setImage(image);
          if(message.getHodnota().equals("2"))
              im2.setImage(image);
          if(message.getHodnota().equals("3"))
              im3.setImage(image);
          if(message.getHodnota().equals("4"))
              im4.setImage(image);
          if(message.getHodnota().equals("5"))
              im5.setImage(image);
          if(message.getHodnota().equals("6"))
              im6.setImage(image);
          if(message.getHodnota().equals("7"))
              im7.setImage(image);
          if(message.getHodnota().equals("8"))
              im8.setImage(image);
          if(message.getHodnota().equals("9"))
              im9.setImage(image);
          if(message.getHodnota().equals("10"))
              im10.setImage(image);
          if(message.getHodnota().equals("11"))
              im11.setImage(image);
          if(message.getHodnota().equals("12"))
              im12.setImage(image); 
        }
        });
 
    }
    
    public void zmenaTahu(Message message) throws IOException, InterruptedException
    {
      Platform.runLater(new Runnable() {
        @Override
        public void run() 
        {
                aHrac.setText(message.getHodnota());
        }
        });
    }
    
    public void ukonciHru(String vitez) throws IOException, InterruptedException
    {
      Platform.runLater(new Runnable() {
        @Override
        public void run() 
        {
            LOGGER.info("Hra skončila");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Konec Hry!");
            alert.setHeaderText("Hru vyhrál hráč: "+vitez);
            alert.setContentText("Hra se nyní vypne");
            alert.showAndWait();
            System.exit(0);
        }
        });
    }
    
    
    public void zmenaBodu(Message message) throws IOException, InterruptedException
    {
      Platform.runLater(new Runnable() {
        @Override
        public void run() 
        {
            LOGGER.info("Aktualizují se body");
            if(message.getHrac().equals(nick))
            {
                body1.setText(message.getHodnota());
            }
            else
            {
                body2.setText(message.getHodnota());
            }
        }
        });
    }
    
    public void nastavProtihrace(Message message) throws IOException, InterruptedException
    {
      Platform.runLater(new Runnable() {
        @Override
        public void run() 
        {
                LOGGER.info("Já jsem: " + nick);
                LOGGER.info("Můj protihráč je " +message.getHodnota());
                hrac1.setText(nick);
                hrac2.setText(message.getHodnota());
                body2.setText("0");
                body1.setText("0");
        }
        });
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
