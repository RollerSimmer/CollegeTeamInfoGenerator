/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ctig;

import java.util.ArrayList;
import java.util.HashMap;
import myutil.MyRandom;
import placedata.CityData;
import placedata.CityIndex;
import placedata.StateData;
import placedata.StateDemographics;
import placedata.SchoolLocation;

/**
 * Class to pick a location from a list of cities and states
 * @author RollerSimmer
 */
public class LocationPicker 
    {
    CityListPicker citylist;
    StateListPicker statelist;
    HashMap<String,Integer> statemap;
    //HashMap<String,Integer> citymap;
    
    public void clearcities()
        {
        citylist.clear();
        }
    
    public void clearstates()
        {
        statelist.clear();
        }

    public CityListPicker getCitylist()
        {
        return citylist;
        }

    public StateListPicker getStatelist()
        {
        return statelist;
        }
    
    public CityData getcity(int cid)
        {
        if(cid==-1)
            return CityData.makeEmptyCity();
        else 
            return this.citylist.getitem(cid);        
        }
    
    public StateData getstate(int sid)
        {
        if(sid==-1)
            return StateData.makeEmptyState();
        else
            return this.statelist.getitem(sid);        
        }

    public LocationPicker()
        {
        citylist=new CityListPicker();
        statelist=new StateListPicker();
        statemap=new HashMap<>();
        }
    
    public void addstate(String name, String abrv, String nick, String specificTeamNick
                        ,int popk, int weight, StateDemographics demographics
                        , ListPicker<CityIndex> citylist)
        {
        StateData sd=new StateData(name,abrv,nick,specificTeamNick
                                  ,popk,demographics,citylist);
        this.statemap.put(sd.getAbrv(),this.statelist.getList().size());
        this.statelist.additem(sd,weight);
        }
    
    public void addcity(String name,String[] nicks,int popk,int weight,String stateabrv
                       ,boolean northern,boolean southern
                       ,boolean western,boolean eastern)
        {        
        int stateID=this.statemap.get(stateabrv);
        
        CityData cd=new CityData(name,nicks,stateID,popk,stateabrv
                                ,eastern,western,southern,northern);
        StateData sd=this.statelist.getList().get(stateID).getItem();        
        sd.addCity(this.citylist.getList().size(),weight);
        this.citylist.additem(cd,weight);
        }
    
    public SchoolLocation pickLocation()
        {
        int stateId = pickstate();
        // pick city in that state
        int cityId = pickCityInState(stateId);
        // make location and return it
        SchoolLocation loc=new SchoolLocation(stateId,cityId);
        return loc;
        }

    public Integer pickstate()
        {
        Integer ps=this.statelist.pick();
        return ps==null? -1: ps;
        }
    
    public Integer pickCityInState(int stateId)
        {
        StateData state=this.statelist.getitem(stateId);
        ListPicker<CityIndex> citylist=state.getCitylist();
        Integer sci,ci;
        sci=citylist.pick();
        ci = sci==null? -1: citylist.getitem(sci).getIndex();
        return ci;
        }
    
    public void print()
        {
        this.citylist.print("LocationPicker.citylist");
        this.statelist.print("LocationPicker.statelist");
        }
    }
