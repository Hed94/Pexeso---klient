/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hra;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Vysilac 
{
    private String nick = "Chyba - nick neexistuje";
    private Socket socket;
    private ObjectOutputStream output;
    
    Vysilac( String nick) throws IOException
    {
        this.nick = nick;
        socket = new Socket("localhost",33);
        output = new ObjectOutputStream(socket.getOutputStream());
    }
    
    public Socket getSocket()
    {
        return this.socket;
    }
    
    public void PosliZpravu(String a,String b,String c) throws IOException
    {
        Message message = new Message(a,b,c);
        output.writeObject(message);
    }
    
    
    
}
