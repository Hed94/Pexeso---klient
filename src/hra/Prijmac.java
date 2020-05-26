/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hra;

import static hra.Hra.LOGGER;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Prijmac extends Thread
{
    private Socket socket;
    private FXMLDocumentController controller;
    
    Prijmac (Socket socket,FXMLDocumentController controller)
    {
     this.socket = socket;
     this.controller = controller;
    }
    
    @Override
    public void run()
    {
      try
      {
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            while(true)
            {
            Message returnMessage = (Message)input.readObject();
            zpracujZpravu(returnMessage);
            }
      } 
      catch (IOException ex) 
      {
            Logger.getLogger(Prijmac.class.getName()).log(Level.SEVERE, null, ex);
      } 
      catch (ClassNotFoundException ex) 
      {
            Logger.getLogger(Prijmac.class.getName()).log(Level.SEVERE, null, ex);
      } catch (InterruptedException ex) {
            Logger.getLogger(Prijmac.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void zpracujZpravu(Message message) throws IOException, InterruptedException
    {
        if(message.getOperace().equals("zacatek"))
        {
            LOGGER.info("Server inicializoval hru");
            controller.zacniHru(message);
        }
        
        if(message.getOperace().equals("protihrac"))
        {
            LOGGER.info("Můj protihráč je: "+message.getHodnota());
            controller.nastavProtihrace(message);
        }
        if(message.getOperace().equals("error"))
        {
            LOGGER.info("Přišla nám chyba");
            controller.vypisChybu(message.getHodnota());
        }
        if(message.getOperace().equals("otoc"))
        {
            LOGGER.info("Otáčí se karta");
            controller.otocKartu(message);
        }
        if(message.getOperace().equals("zmenaTahu"))
        {
            LOGGER.info("Na tahu je nyní hráč: "+ message.getHodnota());
            controller.zmenaTahu(message);
        }
        if(message.getOperace().equals("body"))
        {
            LOGGER.info("Bod získal hráč: "+ message.getHrac());
            controller.zmenaBodu(message);
        }
        if(message.getOperace().equals("konec"))
        {
            LOGGER.info("Hra končí");
            controller.ukonciHru(message.getHrac());
        }
    }
}
