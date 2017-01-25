/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package placedata;

import ctig.ListPicker;
import myutil.MyRandom;

/**
 * Information about a state
 * @author RollerSimmer
 */
public class StateData
    {

    /** the name of the state */
    String name;
    /** the state's two-letter postal abbreviation */
    String abrv;
    /** this state's official or common nickname */
    String nick;     
    /** population in units of 1,000 */
    int popk;
    /** a specified team nickname attached to this state */
    String specificTeamNick;
    /** information about the people of the state */ 
    StateDemographics demographics;
    /** list of cities in state, by index */
    ListPicker<CityIndex> citylist;            
    /** Tells whether or not " State" directional school names in state */
    boolean stateDirSuffix;
    /** Tells whether or not "-ern" is appended to directions in the state's directional schools */
    boolean ernDirSuffix=false;

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

    public String getName()
        {
        return name;
        }

    public void setName(String name)
        {
        this.name = name;
        }

    public String getAbrv()
        {
        return abrv;
        }

    public void setAbrv(String abrv)
        {
        this.abrv = abrv;
        }

    public String getNick()
        {
        return nick;
        }

    public void setNick(String nick)
        {
        this.nick = nick;
        }

    public String getSpecificTeamNick()
        {
        return specificTeamNick;
        }

    public void setSpecificTeamNick(String specificTeamNick)
        {
        this.specificTeamNick = specificTeamNick;
        }

    public StateDemographics getDemographics()
        {
        return demographics;
        }

    public void setDemographics(StateDemographics demographics)
        {
        this.demographics = demographics;
        }

    public boolean hasStateDirSuffix()
        {
        return stateDirSuffix;
        }

    public void setStateDirSuffix(boolean stateDirSuffix)
        {
        this.stateDirSuffix = stateDirSuffix;
        }

    public boolean hasErnDirSuffix()
        {
        return ernDirSuffix;
        }

    public void setErnDirSuffix(boolean ernDirSuffix)
        {
        this.ernDirSuffix = ernDirSuffix;
        }

    public ListPicker<CityIndex> getCitylist()
        {
        return citylist;
        }

    public void setCitylist(ListPicker<CityIndex> citylist)
        {
        this.citylist = citylist;
        }
    
    public StateData(String name, String abrv, String nick, String specificTeamNick,int popk, StateDemographics demographics, ListPicker<CityIndex> citylist)
        {
        this.name = name;
        this.abrv = abrv;
        this.nick = nick;
        this.specificTeamNick = specificTeamNick;
        this.demographics = demographics;
        if(citylist==null)
            this.citylist = new ListPicker<>();
        else
            this.citylist=citylist;
        ernDirSuffix=MyRandom.next(1,100)<=40;
        stateDirSuffix=MyRandom.next(1,100)<=5;
        this.popk=popk;
        }
    
    public StateData()
        {
        }   
    
    public void addCity(int index,int weight)
        {
        CityIndex ci=new CityIndex(index);        
        this.citylist.additem(ci,weight);
        }

    public static StateData makeEmptyState()
        {
        ListPicker<CityIndex> citylist=new ListPicker<>();
        ReligionData rd = new ReligionData((short)0,(short)0,(short)0,(short)0,(short)0,(short)0);
        StateDemographics demographics = new StateDemographics(rd,(short)0);
        StateData es=new StateData("(no state)", "XX", "", "",0,demographics,citylist);
        return es;
        }
    
    @Override
    public String toString()
        {
        ReligionData rd=demographics.getReligion();
        String dgstr=String.format("{spanish=%d,mormon=%d,catholic=%d,"
                                  +"baptist=%d,methodist=%d,jewish=%d,"
                                  +"lutheran=%d}"
                                  ,demographics.getSpanish()
                                  , (int)rd.getMormon()   , (int)rd.getCatholic()
                                  , (int)rd.getBaptist()  , (int)rd.getMethodist()
                                  , (int)rd.getJewish()   , (int)rd.getLutheran()                                
                                  );
        StringBuilder cb=new StringBuilder("");
        int i=0;
        cb.append("{");
        for(WeightedListItem<CityIndex> wci: this.citylist.getList())
            {
            if(i>0) cb.append(",");            
            cb.append(wci.getItem().getIndex());            
            ++i;
            }
        cb.append("}");
        String cities=cb.toString();        
        return String.format("{StateData: name=\"%s\",abrv=\"%s\",nick=\"%s\""
                            +",specificTeamNick=\"%s\",population=%d,demographics=%s,cities=%s}"
                            ,name,abrv,nick,specificTeamNick,this.getPopulation(),dgstr,cities);
        }    
    }
