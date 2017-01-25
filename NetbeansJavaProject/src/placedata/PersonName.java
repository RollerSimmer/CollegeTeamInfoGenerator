/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package placedata;

/**
 * contains information on personal names (like given name and surname)
 * @author RollerSimmer
 */
public class PersonName 
    {
    String name;

    public String getName()
        {
        return name;
        }

    public void setName(String name)
        {
        this.name = name.trim();
        }

    public PersonName(String name)
        {
        this.setName(name);
        }
    
    @Override
    public String toString()
        {
        return "{PersonName: name=\""+name+"\"}";
        }
    }
