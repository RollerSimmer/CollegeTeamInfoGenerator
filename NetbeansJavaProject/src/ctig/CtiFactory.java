/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ctig;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Color;
import myutil.ColorUtil;
import myutil.MyRandom;
import placedata.CityData;
import placedata.ReligionID;
import placedata.StateData;
import team.TeamAdjective;
import team.TeamNickname;
import team.CollegeTeamInfo;
import team.CollegeTeamList;
import team.ColorPair;
import team.SchoolOperator;
import xml.DataReader;

/**
 * factory class to generate college team information
 * @author RollerSimmer
 */
public class CtiFactory 
    {

    LocationPicker lp;
    PersonNamePicker pnp;   
    SpecialtyPicker sp;
    OperatorPicker op;
    SymbolAdjectivePicker sap;
    SymbolNounPicker snp;
    
    SchoolNameFactory snf;
    
    TeamAdjectivePicker tap;
    TeamNicknamePicker tnp;
    ColorTable ct;
    
    CollegeTeamInfo team;
    
    
    private static final boolean debugprint=false;
    
    public LocationPicker getLocationPicker()
        {
        return lp;
        }

    public PersonNamePicker getPersonNamePicker()
        {
        return pnp;
        }

    public SpecialtyPicker getSpecialtyPicker()
        {
        return sp;
        }

    public OperatorPicker getOperatorPicker()
        {
        return op;
        }

    public SymbolAdjectivePicker getSymbolAdjectivePicker()
        {
        return sap;
        }

    public SymbolNounPicker getSymbolNounPicker()
        {
        return snp;
        }

    public TeamAdjectivePicker getTeamAdjectivePicker()
        {
        return tap;
        }

    public TeamNicknamePicker getTeamNicknamePicker()
        {
        return tnp;
        }

    public ColorTable getColorTable()
        {
        return ct;
        }

    protected static CtiFactory theInstance=null;
    
    protected CtiFactory()
        {
        lp=new LocationPicker();
        pnp=new PersonNamePicker();
        sp=new SpecialtyPicker();
        op=new OperatorPicker();
        sap=new SymbolAdjectivePicker();
        snp=new SymbolNounPicker();
        tap=new TeamAdjectivePicker();
        tnp=new TeamNicknamePicker();
        
        ct=new ColorTable();
        team=null;

        }
    
    public static CtiFactory getInstance()
        {
        if(theInstance==null)
            theInstance=new CtiFactory();
        return theInstance;
        }

    
    private void makeNicknameAndColors()
        {
        // generate nickname adjective
        StringBuilder nb=new StringBuilder(""); // nickname builder
        TeamAdjective tadj=this.tap.pickItem();        

        // generate a nickname base and combine with adjective to get full nickname
        TeamNickname tnickbase;
        StateData state=lp.getstate(team.getLoc().getStateId());
        if(  MyRandom.next(1,100)<=10 && team.isFlagship() && !state.getSpecificTeamNick().isEmpty())
            // pick prefered name from state
            tnickbase=new TeamNickname(state.getSpecificTeamNick()+"*",false,null);
        else 
            tnickbase=this.tnp.pickItem();        
        
        if(tadj.getWord().length()>0)
            {
            if(tadj.getAdjtype()!=TeamAdjective.Type.color)
                {
                // join adjective and a nickname
                nb.append(tadj.getWord());
                nb.append(" ");
                nb.append(tnickbase.getWord());
                }
            else if(MyRandom.next(1,100)<=75)
                {
                // join adjective and a nickname
                nb.append(tadj.getWord());
                nb.append(" ");
                nb.append(tnickbase.getWord());
                }
            else 
                {
                // make adjective-only nickname
                String adjname=tadj.getWord().trim();
                nb.append(adjname.equals("Golden")? "Gold": adjname);
                }
            }
        else
            nb.append(tnickbase.getWord());
        String tnick;
        tnick = nb.toString();
        team.setNickname(tnick);
        
        // pick primary and secondary color considering any prefered colors for given nickname and adjective
        // order of preference: adjective > nick > pick-from-table        
        ColorMatcher colorRow=null;
        boolean isColorAdj=tadj.getAdjtype()==TeamAdjective.Type.color;
        boolean isColoredNick=tnickbase.isColorUsed();

        Color primary;
        Color secondary;
            
        if(isColorAdj && isColoredNick)        
            {
            primary=tnickbase.getPrefcolor();
            secondary=tadj.getColor();
            }
        else
            {
            if(isColorAdj)                
                primary=tadj.getColor();
            else if(isColoredNick)
                primary=tnickbase.getPrefcolor();
            else
                {
                colorRow=this.ct.pickrow();        
                primary = colorRow.getPrimary();
                }
            if(isColorAdj||isColoredNick)
                colorRow=this.ct.getClosestColor(primary);            
            secondary = colorRow.pickmatch();
            }

        ColorPair colors=new ColorPair(primary,secondary);
        
        if(primary==null || secondary==null)
            System.out.print("");
        
        if (MyRandom.next(0,1)==1)
            colors.swap();
        colors.smartswap(); 
        
        colors.randomizeColors(ColorUtil.getStaticVariance());
        
        team.getColors().setPrimary(colors.getPrimary());
        team.getColors().setSecondary(colors.getSecondary());        
        }

    public CollegeTeamInfo produce()
        {
        try 
            {
            team=new CollegeTeamInfo();
            
            // select state and city
            team.setLoc(lp.pickLocation());                
            int sid=team.getLoc().getStateId();
            StateData state=lp.getStatelist().getitem(sid);
            int cid=team.getLoc().getCityId();
            CityData city=lp.getCitylist().getitem(cid);

            // pick public or private
            team.setSoperator(this.op.pickItem());

            // set religious affiliation
            SchoolOperator sop=team.getSoperator();
            ReligionID affiliation;
            if(true)
                {        
                // if public, do this
                if(sop==SchoolOperator.opublic)
                    // set affiliation to none
                    affiliation=ReligionID.none;
                // elif military, do this
                else if (sop==SchoolOperator.omilitary)
                    //specialtyStr="";
                    affiliation=ReligionID.none;
                // else (if private), do this
                else 
                    // set specialty string to blank and pick an affiliation from state data
                    affiliation = state.getDemographics().getReligion().pick();
                }
            team.setAffiliation(affiliation);        

            // generate school name using defined grammar rules
            snf=new SchoolNameFactory(lp,pnp,sp,op,sap,snp,team);
            team.setSchool(snf.produce());            

            // generate nickname and colors
            makeNicknameAndColors();

            return team;
            }
        catch(Exception ex)
            {
            Logger.getLogger(CtiFactory.class.getName()).log(Level.SEVERE, null, ex);
            return team;        
            }
        }
    
    public static CollegeTeamInfo quickproduce()
        {
        return CtiFactory.getInstance().produce();
        }

    
    public void print()
        {
        System.out.println("**** Contents of CtiFactory lists ****");
        this.lp.print();
        this.pnp.print(); 
        this.sp.print();
        this.sap.print();
        this.snp.print();
        this.tap.print();
        this.tnp.print();
        this.op.print();
        this.ct.print();        
        }
    
    /**
     * print the contents for now...
     * @param argv command line arguments
     */
    public static void _main(String argv[]) 
        {
        CtiFactory ctif=CtiFactory.getInstance();
        DataReader dr=new DataReader(ctif,false);
        dr.read("data/name-elements.xml"); 
        ctif.print();
        LocationPicker lp=ctif.getLocationPicker();
        ArrayList<Integer> scl=new ArrayList<>(); // state count list        
        int emptycount=0;

        for(int i=0;i<lp.getStatelist().getList().size();i++)
            scl.add(0);

        for(int i=0;i<lp.getStatelist().getWeightsum();i++)
            {
            Integer ci=lp.pickstate();
            if(ci==-1)
                emptycount++;
            else
                scl.set(ci,scl.get(ci)+1);
            }

        System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        
        for(int i=0;i<scl.size();i++)
            {
            Integer pickcount=scl.get(i);
            System.out.printf("State%d (\"%s\") was picked %d times.  Its weight is %d.\n"
                             ,i,lp.getstate(i).getName(),pickcount
                             ,lp.getStatelist().getItemWeight(i));
            }
        System.out.printf("Empty State was picked %d times.\n",emptycount);
        }
    
    public static void main(String argv[]) 
        {
        CtiFactory ctif=CtiFactory.getInstance();
        DataReader dr=new DataReader(ctif,false);
        dr.read("data/name-elements.xml"); 
        System.out.print("\n\n\n\n\n\n\n\n\n\n");
        //ctif.print();
        System.out.print("\n\n\n\n\n\n\n\n\n\n");
        team.CollegeTeamInfo team;
        
        CollegeTeamList teamlist=new CollegeTeamList(1000,75000);        
        for(int i=0;i<1000;i++)
            {            
            team=CtiFactory.quickproduce();        
            if(!teamlist.add(team))
                /*
                System.out.printf("The team \"%s %s\" was not added for whatever reason.\n"
                                 ,team.getFullSchoolName()
                                 ,team.getNickname()
                                 )
                */
                ;            
            }
        System.out.print("\n\n\n\n\n\n\n\n\n\n");
        
        // print out the html code
        System.out.print("<!DOCTYPE html><html><body>\n");
        System.out.print("  <table>\n");
        System.out.printf("    <!--heading-->%s\n",CollegeTeamInfo.makeHtmlHeading(false));
        for(int i=0;i<teamlist.listsize();i++)
            {
            team=teamlist.getteam(i);
            if(team!=null)
                System.out.printf("    <!--%d-->%s\n",i,team.toHtmlRow(false));
            else
                System.out.printf("    <!--%d. There was no team -->\n",i);
            }        
        System.out.print("  </table>\n");
        System.out.print("</body></html>");
        System.out.print("\n\n\n\n\n\n\n\n\n\n");

        return;
        }
    }
