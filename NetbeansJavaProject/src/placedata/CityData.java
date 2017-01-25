/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package placedata;

import java.util.ArrayList;

/**
 * Houses data describing a single city.
 * @author RollerSimmer
 */
public class CityData
    {
    /** this city's name */ 
    String name;
    /** string array for two optional city nicknames */
    String[] nicks;    
    /** integer state ID */
    int stateId;
    /** the containing state's two letter postal abbreviation */
    String stateabrv;
    /** population in units of 1,000 */
    int popk;
    boolean eastern;
    boolean western;
    boolean southern;
    boolean northern;    

    public int getPopk()
        {
        return popk;
        }

    public int getPopulation()
        {
        return getPopk()*1000;
        }

    public void setPopulation(int pop)
        {
        this.popk = pop/1000;
        }
    
    public boolean isEastern()
        {
        return eastern;
        }

    public boolean isWestern()
        {
        return western;
        }

    public boolean isSouthern()
        {
        return southern;
        }

    public boolean isNorthern()
        {
        return northern;
        }
              
    public boolean isCentral()
        {
        return !(eastern||western||southern||northern);
        }

    public void setEastern(boolean eastern)
        {
        this.eastern = eastern;
        }

    public void setWestern(boolean western)
        {
        this.western = western;
        }

    public void setSouthern(boolean southern)
        {
        this.southern = southern;
        }

    public void setNorthern(boolean northern)
        {
        this.northern = northern;
        }

    public String getName()
        {
        return name;
        }

    public void setName(String name)
        {
        this.name = name;
        }

    public String[] getNicks()
        {
        return nicks;
        }

    public void setNicks(String[] nicks)
        {
        this.nicks = nicks;
        }

    public int getStateId()
        {
        return stateId;
        }

    public void setStateId(int stateId)
        {
        this.stateId = stateId;
        }

    public String getStateabrv()
        {
        return stateabrv;
        }

    public void setStateabrv(String stateabrv)
        {
        this.stateabrv = stateabrv;
        }
    

    public CityData(String name, String[] nicks,int stateID,int popk,String stateabrv, boolean eastern, boolean western, boolean southern, boolean northern)
        {
        this.name = name;
        this.nicks = new String[2];
        this.nicks[0] = nicks[0];
        this.nicks[1] = nicks[1];
        this.stateId = stateID;
        this.stateabrv = stateabrv;
        this.eastern = eastern;
        this.western = western;
        this.southern = southern;
        this.northern = northern;
        this.popk=popk;
        }

    public static CityData makeEmptyCity()
        {
        String[] nicks={"",""};
        CityData ec=new CityData("(no city)",nicks,-1,0,"XX", false,false,false,false);
        return ec;
        }
    
    @Override
    public String toString()
        {
        String dirs=String.format("(%s%s%s%s)"
            ,(this.northern? "N": " ")
            ,(this.southern? "S": " ")
            ,(this.western? "W": " ")
            ,(this.eastern? "E": " ")
            );
        String nickstr=String.format("{\"%s\",\"%s\"}",nicks[0],nicks[1]);
        return String.format("{CityData: name=\"%s\",nicks=%s,population=%d,stateId=%d,stateAbrv=\"%s\",dirs=%s}"
                            ,name,nickstr,this.getPopulation(),stateId,stateabrv,dirs);
        }    

    }
