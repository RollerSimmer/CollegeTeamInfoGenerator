/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package team;

import ctig.CtiFactory;
import ctig.LocationPicker;
import myutil.MyRandom;
import placedata.CityData;
import placedata.ReligionData;
import placedata.ReligionID;
import placedata.SchoolLocation;
import placedata.StateData;

/**
 * Information about a single college team, including its location, names, and colors.
 * @author RollerSimmer
 */
public class CollegeTeamInfo 
    {
    /** school location */
    SchoolLocation loc;
    /** team colors */
    ColorPair colors;     
    /** short name of the school */
    String school;
    /** full name of the school */
    //String fullSchool;    
    /** the nickname of the school's athletic teams  */
    String nickname;    
    /** the operator of the school */
    SchoolOperator soperator;
    /** religious affiliation (if any) */
    ReligionID affiliation;    
    /** is this a system flagship? */
    boolean flagship; 
    /** is this an urban school? */
    boolean urban;    
    /** is this a regional (directional) school? */
    boolean regional;    

    public enum UnivPos
        {
        suffix,
        prefix,
        nowhere,        
        college,
        institute;
        }
    
    UnivPos upos;
    
    CollegeSpecialty specialty;
    
    public int getEnrollment()
        {
        return estimateEnrollment();
        }

    public boolean isUrban()
        {
        return urban;
        }

    public boolean isRegional()
        {
        return regional;
        }
    
    public CityData getcity()
        {
        LocationPicker lp=CtiFactory.getInstance().getLocationPicker();
        return lp.getcity(this.loc.getCityId());
        }
    
    public String getCityName()
        {
        return this.getcity().getName();
        }
    
    public StateData getstate()
        {
        LocationPicker lp=CtiFactory.getInstance().getLocationPicker();
        return lp.getstate(this.loc.getStateId());        
        }

    public String getStateName()
        {        
        return this.getstate().getName();
        }
    
    public String getStateAbrv()
        {
        return this.getstate().getAbrv();
        }        

    public String getFullSchoolName()
        {
        LocationPicker lp=CtiFactory.getInstance().getLocationPicker();
        StringBuilder fs= new StringBuilder("");
        if(upos==UnivPos.prefix)
            fs.append("University of ");
        fs.append(this.school);        
        if(upos==UnivPos.suffix)
            fs.append(" University");
        else if(upos==UnivPos.college)
            fs.append(" College");
        else if(upos==UnivPos.institute)
            fs.append(" Institute");
        if(!isFlagship())
            {
            fs.append(" - ");        
            fs.append(lp.getcity(loc.getCityId()).getName());
            }
        return fs.toString();        
        }
    
    public String getCommonSchoolName()
        {
        LocationPicker lp=CtiFactory.getInstance().getLocationPicker();
        StringBuilder fs= new StringBuilder("");
        fs.append(this.school);        
        if(upos==UnivPos.college)
            fs.append(" College");
        if(!isFlagship())
            {
            fs.append(" - ");        
            fs.append(lp.getcity(loc.getCityId()).getName());
            }
        return fs.toString();        
        }
    
    public String getSchoolInitialism()
        {
        // get full name and make initialism from name's capital letters 
        StringBuilder ss=new StringBuilder("");
        String fullschool=getFullSchoolName();
        for(int i=0;i<fullschool.length();i++)
            {            
            char c=fullschool.charAt(i);
            if ( c>='A' && c<='Z' )
                ss.append(c);
            }
        return ss.toString();
        }
    
    public String getShortSchoolName(int lengthThreshold)
        {
        String gss="";
        String fullschool=getFullSchoolName();
        if(fullschool.length()>lengthThreshold)
            {
            gss=getSchoolInitialism();
            if(gss.length()==0)
                gss=fullschool;
            }
        else
            gss=fullschool;
        return gss;
        }

    public void setFullSchool(String fullSchool)
        {
        //this.fullSchool = fullSchool;
        }

    public UnivPos getUpos()
        {
        return upos;
        }

    public void setUpos(UnivPos upos)
        {
        this.upos = upos;
        }

    public void setUrban(boolean urban)
        {
        this.urban = urban;
        }

    public void setRegional(boolean regional)
        {
        this.regional = regional;
        }
    
    public boolean isFlagship()
        {
        return flagship;
        }

    public void setFlagship(boolean flagship)
        {
        this.flagship = flagship;
        }  

    public ColorPair getColors()
        {
        return colors;
        }

    public SchoolLocation getLoc()
        {
        return loc;
        }

    public void setLoc(SchoolLocation loc)
        {
        this.loc = loc;
        }

    public String getSchool()
        {
        return school;
        }

    public void setSchool(String school)
        {
        this.school = school;
        }

    public String getNickname()
        {
        return nickname;
        }

    public void setNickname(String nickname)
        {
        this.nickname = nickname;
        }

    public SchoolOperator getSoperator()
        {
        return soperator;
        }

    public void setSoperator(SchoolOperator soperator)
        {
        this.soperator = soperator;
        }

    public ReligionID getAffiliation()
        {
        return affiliation;
        }

    public void setAffiliation(ReligionID affiliation)
        {
        this.affiliation = affiliation;
        }

    public CollegeSpecialty getSpecialty()
        {
        return specialty;
        }

    public void setSpecialty(CollegeSpecialty specialty)
        {
        this.specialty = specialty;
        }

    public CollegeTeamInfo(SchoolLocation loc, ColorPair colors, String school, String nickname, SchoolOperator soperator, ReligionID affiliation)
        {        
        this.regional = false;
        this.urban = false;
        this.upos = UnivPos.nowhere;
        this.flagship = false;
        this.loc = loc;
        this.colors = colors;
        this.school = school;
        this.nickname = nickname;
        this.soperator = soperator;
        this.affiliation = affiliation;
        this.specialty=CollegeSpecialty.none;
        }

    public CollegeTeamInfo()
        {
        this.regional = false;
        this.urban = false;
        this.upos = UnivPos.nowhere;
        this.flagship = false;
        this.loc = null;
        this.colors = new ColorPair();
        this.school = "";
        this.nickname = "";
        this.soperator = SchoolOperator.opublic;
        this.affiliation = ReligionID.other;
        this.specialty=CollegeSpecialty.none;        
        }
    
    public boolean isStatePubFlagship()
        {  return isStateFlagship();  }

    public boolean isStateFlagship()
        {
        return this.flagship && !this.regional && !this.urban 
             && this.soperator==SchoolOperator.opublic;
        }
    
    public boolean isUrbanPublic()
        {
        return this.urban && this.soperator==SchoolOperator.opublic;
        }
    
    public boolean isRegionalPublic()
        {
        return this.soperator==SchoolOperator.opublic && this.isRegional();
        }
    
    public boolean isMilitary()
        {
        return this.soperator==SchoolOperator.omilitary;
        }  
    
    public boolean isReligiousPrivate()
        {
        return this.soperator==SchoolOperator.oprivate 
              && this.affiliation!=ReligionID.none;
        }

    private int estimateEnrollment()
        {
        // estimate nation size (for military)
        int nationsize=310*1000*1000; //310,000,000
        
        // estimate state size
        int statesize=this.getstate().getPopulation();
        statesize=Math.max(Math.min(statesize,5000000),1000000);
        
        // estimate city size
        int citysize=Math.max(Math.min(this.getcity().getPopulation(),5000000),50000);
        
        // get state percentage from religion
        int religionPct;
        if(isReligiousPrivate())
            religionPct=getstate().getDemographics().getReligiousPct(affiliation);
        else 
            religionPct=0;
        
        // init domain population and enrollment
        int ee=0;
        int domainsize=0;

        // init school type flags
                        
        
        // if military, then do this
        if(this.isMilitary())
            // set domain size to a portion of nationsize
            domainsize=nationsize/1500;
        // if public flagship, then do this
        else if(this.isStateFlagship())
            // set population to state
            domainsize=statesize/4;
        // elif regional public, then do this
        else if(this.isRegionalPublic())
            // set population to a portion of the state population
            domainsize=statesize/6;            
        // elif urban public, then do this
        else if(this.isUrbanPublic())
            // set population to city population plus a little of the rest of the state
            domainsize=citysize/3+Math.max(0,statesize-citysize)/8;            
        // elif religious private, then do this
        else if(this.isReligiousPrivate())
            // set domain size to number of adherents to affiliation in state
            domainsize=statesize/3*religionPct/100;
        // else, do this
        else 
            // set domain size randomly between city and state size
            domainsize=MyRandom.next(citysize/3,statesize/4);
        // estimate enrollment from domain size and return
        //int eefactor=MyRandom.next(5,40);  // between 0.5% and 4% of domain enrolled in university
        int eefactor=MyRandom.next(25,30);  // between 2.5% and 3% of domain enrolled in university
        int domainEnrollment=domainsize*eefactor/1000;                
        int maxEnrollment=50000;
        ee=Math.max(Math.min(domainEnrollment,maxEnrollment),250);
        
        return ee;
        }
    
    @Override
    public String toString()
        {        
        //return super.toString(); //To change body of generated methods, choose Tools | Templates.
        LocationPicker lp=CtiFactory.getInstance().getLocationPicker();
        return String.format("{school = \"%s\" (\"%s\", \"%s\"), nickname=\"%s\""
                            +", colors={%s,%s}, operator=%s, affiliation=%s"
                            +", isStateFlagship? %s, city=\"%s\",state=\"%s\" (\"%s\")}"
                            ,this.getFullSchoolName(),this.getCommonSchoolName()
                            ,this.getShortSchoolName(15)
                            ,this.nickname
                            ,this.colors.getPrimary().toString()
                            ,this.colors.getSecondary().toString()
                            ,this.soperator.toString()
                            ,this.affiliation.toString()
                            ,this.isStateFlagship(),this.getCityName()
                            ,this.getStateName(),this.getStateAbrv()
                            );
        }

    public String toHtmlRow(boolean includeNewline)
        {        
        //return super.toString(); //To change body of generated methods, choose Tools | Templates.
        LocationPicker lp=CtiFactory.getInstance().getLocationPicker();
        // row format:
        // primary color | secondary color | full school | common school | abrv | nickname | operator | affiliation | flagship? | city | state  
        return String.format("<tr><td bgcolor=\"#%s\"></td><td bgcolor=\"#%s\"></td>"
                            +"<td>%s</td><td>%s</td><td>%s</td>"
                            +"<td>%s</td><td>%s</td><td>%s</td><td>%s</td>"
                            +"<td>%d</td><td>%s (%dK)</td><td>%s (%dK)</td>"          // city and state
                            +"</tr>"+(includeNewline? "\n": "")
                            ,this.colors.getPrimary().toString().substring(2,8)
                            ,this.colors.getSecondary().toString().substring(2,8)
                            ,this.getFullSchoolName()
                            ,this.getCommonSchoolName()
                            ,this.getSchoolInitialism()
                            ,this.nickname
                            ,this.soperator.toString()
                            ,this.affiliation.toString()
                            ,(this.isStateFlagship()? "yes": "no")
                            ,this.getEnrollment()
                            ,this.getCityName()
                            ,this.getcity().getPopk()
                            ,this.getStateAbrv()
                            ,this.getstate().getPopk()            
                            );
        }
    
    public static String makeHtmlHeading(boolean includeNewline)
        {        
        //return super.toString(); //To change body of generated methods, choose Tools | Templates.
        // row format:
        // primary color | secondary color | full school | common school | abrv | nickname | operator | affiliation | flagship? | city | state  
        LocationPicker lp=CtiFactory.getInstance().getLocationPicker();
        return "<tr><td>Primary</td><td>Secondary</td>"
              +"<td>Full School Name</td><td>Common School Name</td><td>Abrv</td>"
              +"<td>Nickname</td><td>Operator</td><td>Affiliation</td><td>Flagship?</td>"
              +"<td>Enrollment</td><td>City (pop)</td><td>State (pop)</td>"
              +"</tr>"+(includeNewline? "\n": "")
              ;
        }
    }
