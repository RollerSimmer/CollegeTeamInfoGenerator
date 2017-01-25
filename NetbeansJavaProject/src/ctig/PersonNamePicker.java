/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ctig;

import java.util.ArrayList;
import placedata.PersonName;
import myutil.MyRandom;

/**
 * Used to pick a person's first and last name based on gender and language.
 * @author RollerSimmer
 */
public class PersonNamePicker 
    {
    public enum OriginType
        {
        spanish,
        english
        }    
    
    public enum GenderType
        {
        male,
        female,
        other;
        
        public static GenderType pick(int amtMales,int amtFemales)
            {
            int pickval=MyRandom.next(1,amtMales+amtFemales);
            if(pickval>amtMales)
                return female;
            else
                return male;
            }
        }
    
    public enum NameType 
        {
        first,
        last
        }
    
    /**  */
    NameListPicker spanishSurnameList;    
    /**  */
    NameListPicker americanSurnameList;
    /**  */
    NameListPicker spanishMaleList;
    /**  */
    NameListPicker spanishFemList;
    /**  */
    NameListPicker americanMaleList;
    /**  */
    NameListPicker americanFemList;
    /** spanish surname weight total */
    int sswtotal;
    /** american surname weight total */
    int aswtotal;
    /** spanish male name weight total */
    int smwtotal;
    /** spanish female name weight total */
    int sfwtotal;
    /** american male name weight total */
    int amwtotal;
    /** american female name weight total */
    int afwtotal;

    public NameListPicker getSpanishSurnameList()
        {
        return spanishSurnameList;
        }

    public NameListPicker getAmericanSurnameList()
        {
        return americanSurnameList;
        }

    public NameListPicker getSpanishMaleList()
        {
        return spanishMaleList;
        }

    public NameListPicker getSpanishFemList()
        {
        return spanishFemList;
        }

    public NameListPicker getAmericanMaleList()
        {
        return americanMaleList;
        }

    public NameListPicker getAmericanFemList()
        {
        return americanFemList;
        }
    
    private int makeSwitchVal(NameType ntype,OriginType otype,GenderType gtype)
        {
        int switchval=0x000;
        
        if(ntype==NameType.first) switchval|=0x100;
        else if(ntype==NameType.last) switchval|=0x200;
        
        if(otype==OriginType.spanish) switchval|=0x010;
        else if(otype==OriginType.english) switchval|=0x020;
        
        if(gtype==GenderType.male) switchval|=0x001;
        else if(gtype==GenderType.female) switchval|=0x002;
        
        return switchval;        
        }
    
    NameListPicker selectListBySwitchVal(int switchval)
        {        
        NameListPicker list;            
        
        switch(switchval)
            {
            case 0x111: //first,spanish,male
                list=this.spanishMaleList; break;
            case 0x112: //first,spanish,female
                list=this.spanishFemList;  break;
            case 0x121: //first,american,male
                list=this.americanMaleList;  break;
            case 0x122: //first,american,female
                list=this.americanFemList;  break;
            case 0x211: //last,spanish,male
            case 0x212: //last,spanish,female
            case 0x210: //last,spanish,other
                list=this.spanishSurnameList; break;            
            case 0x221: //last,american,male
            case 0x222: //last,american,female
            case 0x220: //last,american,other
                list=this.americanSurnameList; break;            
            default:
                list=null;
            }
        return list;
        }   
    
    
    public void add(String name,int weight,NameType ntype,OriginType otype,GenderType gtype)
        {
        int switchval=this.makeSwitchVal(ntype, otype, gtype);
        NameListPicker list=this.selectListBySwitchVal(switchval);    
        PersonName pn=new PersonName(name);
        
        if(list!=null)
            list.additem(pn,weight);
        }
    
    public PersonNamePicker()
        {
        this.americanFemList=new NameListPicker();
        this.americanMaleList=new NameListPicker();
        this.americanSurnameList=new NameListPicker();
        this.spanishFemList=new NameListPicker();
        this.spanishMaleList=new NameListPicker();
        this.spanishSurnameList=new NameListPicker();
        this.afwtotal=0;
        this.amwtotal=0;
        this.aswtotal=0;
        this.sfwtotal=0;
        this.smwtotal=0;
        this.sswtotal=0;           
        }
    
    /**
     * pick whether or not to generate an English or Spanish name
     * @param spanishPct probability, in integer percentage form, that the name should be Spanish.
     * @return the name origin type that was picked
     */
    public OriginType pickNameOrigin(int spanishPct)
        {
        int pickval=MyRandom.getinstance().next(0,100);
        int rangeval=spanishPct;

        if(pickval<=rangeval)
            return OriginType.spanish;
        //default
        return OriginType.english;
        }
    
    public GenderType pickGender(int spanishPct)
        {
        int pickval=MyRandom.getinstance().next(1,100);
        int rangeval=spanishPct;
        
        if(pickval<=75)  // 75% chance of it being a man
            return GenderType.male;
        else            
            return GenderType.female;
        }
    
    public String pickname(NameType ntype, OriginType otype, PersonNamePicker.GenderType gtype)
        {
        String pn="";
        
        int switchval=this.makeSwitchVal(ntype, otype, gtype);
        NameListPicker list=this.selectListBySwitchVal(switchval);    
        
        if(list!=null)
            pn=list.getitem(list.pick()).getName();
        
        return pn;        
        }
    
    public void print()
        {
        this.spanishSurnameList.print("PersonNamePicker.spanishSurnameList");
        this.americanSurnameList.print("PersonNamePicker.americanSurnameList");
        this.spanishMaleList.print("PersonNamePicker.spanishMaleList");
        this.americanMaleList.print("PersonNamePicker.americanMaleList");
        this.spanishFemList.print("PersonNamePicker.spanishFemList");
        this.americanFemList.print("PersonNamePicker.americanFemList");
        }   
    }
