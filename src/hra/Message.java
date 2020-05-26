
package hra;

import java.io.Serializable;


public class Message implements Serializable
{
    private String hrac;
    private String operace;
    private String hodnota;

    public Message(String hrac, String operace, String hodnota) {
        this.hrac = hrac;
        this.operace = operace;
        this.hodnota = hodnota;
    }

    public String getHrac() 
    {
        return hrac;
    }

    public void setHrac(String hrac) 
    {
        this.hrac = hrac;
    }

    public String getOperace() 
    {
        return operace;
    }

    public void setOperace(String operace) 
    {
        this.operace = operace;
    }

    public String getHodnota() 
    {
        return hodnota;
    }

    public void setHodnota(String hodnota) 
    {
        this.hodnota = hodnota;
    }
    
    
    
    
}