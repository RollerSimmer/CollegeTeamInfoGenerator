/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ctig;

import java.util.ArrayList;
import myutil.MyRandom;
import placedata.CityData;
import placedata.ReligionID;
import placedata.StateData;
import team.CollegeSpecialty;
import team.CollegeTeamInfo;
import team.SchoolOperator;
import univdata.SpecialtyData;

/**
 * Factory class for generating university names based on grammar rules define in docs/grammar.txt. 
 * @author RollerSimmer
 */
public class SchoolNameFactory 
    {
    LocationPicker lp;
    PersonNamePicker pnp;   
    SpecialtyPicker sp;
    OperatorPicker op;
    SymbolAdjectivePicker sap;
    SymbolNounPicker snp;
    /** the team */
    CollegeTeamInfo team;   
    /** selection weights */
    SchoolNameSelWeights selwgts;   

    public SchoolNameFactory(LocationPicker lp, PersonNamePicker pnp, SpecialtyPicker sp, OperatorPicker op, SymbolAdjectivePicker sap, SymbolNounPicker snp, CollegeTeamInfo team)
        {
        this.lp = lp;
        this.pnp = pnp;
        this.sp = sp;
        this.op = op;
        this.sap = sap;
        this.snp = snp;
        this.team = team;
        
        initWeights();
        }
    
    
    private void initWeights()
        {
        selwgts=SchoolNameSelWeights.getinst();
        return;
        }
    
    /**
     * 
     * @return a compass direction, with an optional "ern" tacked on if that is the state's convention.
     */
    private String makeDirectionTerm()
        {
        StateData state=lp.getstate(team.getLoc().getStateId());
        CityData city=lp.getcity(team.getLoc().getCityId());
        StringBuilder db=new StringBuilder("");
        if(city.isCentral())        
            db.append("Central");
        else 
            {
            int sum=selwgts.getDirState_children();
            boolean singleDirection=MyRandom.next(1,sum)<=selwgts.getAxialState();
            if(singleDirection)
                {
                int dn=0, ds=1,dw=2,de=3;                                
                ArrayList<Integer> dirs=new ArrayList<>();
                if(city.isNorthern()) dirs.add(dn);
                if(city.isSouthern()) dirs.add(ds);
                if(city.isWestern()) dirs.add(dw);
                if(city.isEastern()) dirs.add(de);
                int dpick=dirs.get(MyRandom.next(0,dirs.size()-1));
                if(dpick==dn)      db.append("North");
                else if(dpick==ds) db.append("South");
                else if(dpick==dw) db.append("West");
                else if(dpick==de) db.append("East");
                }
            else
                {
                if(city.isNorthern())
                    db.append("North");
                else if (city.isSouthern())
                    db.append("South");
                if(city.isWestern())
                    db.append("West");
                else if (city.isEastern())
                    db.append("East");            
                }
            if(db.toString().length()>0 && state.hasErnDirSuffix())
                db.append("ern");
            }
        if(db.toString().length()==0)
            db.append("(No Direction Found)");
        
        return db.toString();        
        }
    
    /**
     * 
     * @return a directional state term (ex: "Northern Utah" or "West Ohio"); whether "-ern" is appended is a flag shared statewide. 
     */
    private String makeDirStateTerm()
        {       
        StringBuilder dsb=new StringBuilder("");
        StateData state=lp.getstate(team.getLoc().getStateId());
        dsb.append(makeDirectionTerm() + " " + makeStateTerm());
        if(state.hasStateDirSuffix())
            {
            dsb.append(" State");
            team.setUpos(CollegeTeamInfo.UnivPos.suffix);
            }
        else
            team.setUpos(CollegeTeamInfo.UnivPos.prefix);
        return dsb.toString();
        }
    
    /** 
     * @return an affiliation term from the enumerated affiliation value in the team.
     * @throws Exception 
     */
    private String makeAffiliationTerm() throws Exception
        {
        if(team==null)
            throw new Exception("No team from which to read affiliation");
        String mat="";
        ReligionID rid=team.getAffiliation();
        switch(rid)
            {
            case mormon: 
                mat="Mormon"; break;
            case catholic: 
                mat="Catholic"; break;
            case methodist: 
                mat=MyRandom.next(1,10)<=3? "Methodist": "Wesleyan"; break;
            case baptist: 
                mat="Baptist"; break;
            case jewish: 
                mat="Hebrew";  break;
            case lutheran:
                mat="Lutheran"; break;
            case other: case none: default:
                break;
            }        
        return mat;
        }    

    /**
     * @return a geographical denomination term like "Utah Mormon" or "Alabama Baptist".
     * @throws Exception 
     */
    private String makeGeoDenomTerm() throws Exception
        {                
        String sterm;
        StringBuilder gdt=new StringBuilder("");
        if(MyRandom.next(1,100)<=90)
            sterm=this.lp.getstate(team.getLoc().getStateId()).getName();
        else
            sterm="";
        team.setUpos(CollegeTeamInfo.UnivPos.suffix);
        String aterm=this.makeAffiliationTerm();        
        gdt.append(sterm);
        if(sterm.length()>0)
            gdt.append(" ");
        gdt.append(aterm);
        return gdt.toString();
        }
    
    /**
     * @return a randomly picked gender, male or female.
     */
    private PersonNamePicker.GenderType pickGender()
        {
        int amtMales=55;
        int amtFemales=100-amtMales;
        return PersonNamePicker.GenderType.pick(amtMales,amtFemales);
        }
    
    /** 
     * @return a saint name, with an optional possessive "'s" if English-based; uses "Santa" for female and "San" or "Santo" for male if Spanish.
     */
    private String makeSaintTerm()
        {
        int upick=MyRandom.next(0,2);
        CollegeTeamInfo.UnivPos upos = upick==0? CollegeTeamInfo.UnivPos.prefix: CollegeTeamInfo.UnivPos.suffix; 

        int spanishPct=lp.getstate(team.getLoc().getStateId()).getDemographics().getSpanish();
        boolean spanish=MyRandom.next(1,100)<=spanishPct;
        PersonNamePicker.GenderType gender=pickGender();
        StringBuilder fn=new StringBuilder("");
        fn.append(makeFirstNameTerm(spanish,gender))
          /*
          .append("(")
          .append(spanish? "Spanish": "American")
          .append(",").append(gender.toString()).append(")")
          */
          ;
        String firstname=fn.toString();
        String st="";
        
        // if american name, do this
        if(!spanish)
            {
            String possessiveSuffix;
            if(MyRandom.next(1,5)<=4)
                {
                possessiveSuffix = "'s";
                team.setUpos(CollegeTeamInfo.UnivPos.suffix);
                }
            else
                {
                possessiveSuffix = "";
                team.setUpos(CollegeTeamInfo.UnivPos.prefix);
                }
            
            // return "Saint " + firstname + possessive suffix
            st = "Saint " + firstname + possessiveSuffix;
            }
        // else (if spanish), do this
        else 
            {
            // if gender is female, do this
            if(gender==PersonNamePicker.GenderType.female)
                // return "Santa " + firstname
                st = "Santa " + firstname;
            // elif gender is male, do this
            else // if(gender==GenderType.male)
                {
                String loname=firstname.toLowerCase();
                // if name starts with "Do" or "To", do this
                if(loname.startsWith("to") || loname.startsWith("do"))
                    // return "Santo " + firstname
                    st = "Santo " + firstname;
                // else, do this
                else
                    // return "San " + firstname
                    st = "San " + firstname;
                }
            team.setUpos(upos);
            }
        return st;
        }

    /**
     * Picks a first name from language only; gender is random.
     * @param spanish true if this name is to be of Spanish origin
     * @return the first name chosen from the language's list of names.
     */
    private String makeFirstNameTerm(boolean spanish)
        {
        return makeFirstNameTerm(spanish,pickGender());    
        }
    
    /**
     * Picks a first name from both language and gender.
     * @param spanish is the person's name Spanish in origin?
     * @param gender the person's gender
     * @return the first name chosen from the proper list.
     */
    private String makeFirstNameTerm(boolean spanish,PersonNamePicker.GenderType gender)
        {
        int switchval=0x00; 
        if(!spanish)  switchval |= 0x10;
        if(gender==PersonNamePicker.GenderType.male) switchval |= 0x01;
        switch (switchval)
            {
            case 0x00: // spanish, female
                return pnp.getSpanishFemList().pickItem().getName();
            case 0x01: // spanish, male
                return pnp.getSpanishMaleList().pickItem().getName();
            case 0x10: // american, female
                return pnp.getAmericanFemList().pickItem().getName();
            case 0x11: // american, male
            default:
                return pnp.getAmericanMaleList().pickItem().getName();
            }
        }

    /**
     * make a surname from a given language.
     * @param spanish is this surname of Spanish origin?
     * @return the surname chosen.
     */
    private String makeSurnameTerm(boolean spanish)
        {
        if(spanish)
            return pnp.getSpanishSurnameList().pickItem().getName();
        else
            return pnp.getAmericanSurnameList().pickItem().getName();            
        }

    /**
     * Makes a term for the public "system" which contains this university. 
     * @return the name of the system
     */
    private String makePublicSystemTerm()
        {
        String pst="";
        int pickval=MyRandom.next(1,selwgts.getPublicSys_children());
        int sum=0;
        int histate=sum+=selwgts.getPsState();
        int hisps=sum+=selwgts.getPsStatePlusSpecialty();
        int hiperson=sum+=selwgts.getPsPerson();
        int hisnick=sum+=selwgts.getPsStateNickname();
        if(pickval<=histate)
            {
            pst=makeStateTerm();        
            team.setUpos(CollegeTeamInfo.UnivPos.prefix);
            }
        else if(pickval<=hisps)
            {
            pst=makeStateTerm() + " " + makeSpecialtyTerm();        
            }
        else if(pickval<=hiperson)
            {
            pst=this.makePersonTerm();        
            team.setUpos(CollegeTeamInfo.UnivPos.suffix);
            }
        else // if(pickval<=hisnick)
            {
            pst=makeStateNicknameTerm();                        
            team.setUpos(CollegeTeamInfo.UnivPos.suffix);
            }
        return pst;
        }

    /**
     * @return a term for an urban public institution chosen from a variety of formats like "U of State-City", "City State", "City U", etc.
     */
    private String makeUrbanPublicTerm()
        {
        String upt="";
        team.setFlagship(true);
        int pickval=MyRandom.next(1,selwgts.getUrbanPublic_children());
        int sum=0;
        int hicity=sum+=selwgts.getUpCity();
        int hicps=sum+=selwgts.getUpCityPlusSpecialty();
        int hips=sum+=selwgts.getUpPublicSystem();
        int hicnick=sum+=selwgts.getUpCityNickname();
        if(pickval<=hicity)
            {            
            upt=makeCityTerm();
            team.setUpos(CollegeTeamInfo.UnivPos.prefix);            
            }
        else if(pickval<=hicps)
            {            
            upt=makeCityTerm() + " " + makeSpecialtyTerm();
            team.setUrban(true);
            }
        else if(pickval<=hips)
            {
            team.setFlagship(false);
            upt=makePublicSystemTerm();            
            }
        else // if(pickval <=hicnick
            {
            upt=makeCityNicknameTerm();                        
            team.setUpos(CollegeTeamInfo.UnivPos.suffix);
            }
        return upt;
        }

    /**
     * @return the name of the city corresponding to the one identified in the team object.
     */
    private String makeCityTerm()
        {
        return lp.getcity(team.getLoc().getCityId()).getName();
        }

    /**
     * @return the name of the state corresponding to the one identified in the team object.
     */
    private String makeStateTerm()
        {
        return lp.getstate(team.getLoc().getStateId()).getName();
        }

    /**
     * @return a term commonly used by religiously affiliated institutions, such as a saint ("Saint Mark's"), geo-denom ("New York Catholic"), or a person's full name ("Brigham Young").
     * @throws Exception 
     */
    private String makeReligiousTerm() throws Exception
        {
        int upick=MyRandom.next(0,2);
        CollegeTeamInfo.UnivPos upos = upick==0? CollegeTeamInfo.UnivPos.prefix: CollegeTeamInfo.UnivPos.suffix; 
        String mrt="";
        int pickval=MyRandom.next(1,100);
        if(pickval<=33)
            {
            mrt=makeSymbolTerm(); 
            // upos already set in the makeSymbolTerm() method
            }
        else if(pickval<=66)
            {
            mrt=makeGeoDenomTerm();
            team.setUpos(CollegeTeamInfo.UnivPos.suffix);
            }
        else
            {
            mrt=makeSaintTerm();            
            }
        return mrt;
        }

    /**
     * 
     * @return a person's full name - first name followed by last name.
     */
    private String makePersonTerm()
        {
        int spanishPct=lp.getstate(team.getLoc().getStateId())
                         .getDemographics().getSpanish();
        boolean isSpanish=MyRandom.next(1,100)<=spanishPct;
        boolean hasFirstName=MyRandom.next(1,100)<=33;
        StringBuilder mpt=new StringBuilder("");
        if(hasFirstName)
            {
            mpt.append(makeFirstNameTerm(isSpanish));
            mpt.append(" ");
            }
        mpt.append(makeSurnameTerm(isSpanish));
        return mpt.toString();        
        }

    /**
     * 
     * @return the nickname (unofficial or official) for the team's home state.
     */
    private String makeStateNicknameTerm()
        {
        StateData state=this.lp.getstate(team.getLoc().getStateId());
        String nick=state.getNick();
        if(!nick.isEmpty())
            return nick;
        else
            return state.getName();
        }

    /**
     * Makes a nickname term from the school's home city.
     * @return one of the city's nicknames or the name of the city if no nicknames exist.
     */
    private String makeCityNicknameTerm()
        {
        CityData city=this.lp.getcity(team.getLoc().getCityId());
        String[] nicks=city.getNicks();
        int whichnick=MyRandom.next(0,1);
        if(!nicks[whichnick].isEmpty())
            return nicks[whichnick];
        else if (whichnick==1 && !nicks[0].isEmpty())
            return nicks[0];
        else
            return makeCityTerm();
        }
    
    /**
     * 
     * @return a religious symbol formed from a list of adjectives and a list of nouns, such as "Notre Dame" or "Holy Cross".
     */
    private String makeSymbolTerm()
        {
        String symbadj=this.sap.pickItem();
        String symbnoun=this.snp.pickItem();        
        return symbadj+" "+symbnoun;
        }    

    /**
     * 
     * @return a university specialty, like "A&M" or "Tech"
     */
    private String makeSpecialtyTerm()
        {
        SchoolOperator sop=team.getSoperator();
        String specialtyTerm="";        
        if(sop==SchoolOperator.opublic)
            {
            SpecialtyData sd=this.sp.getitem(this.sp.pick());
            specialtyTerm=sd.getSuffix();
            team.setUpos(sd.getUpos());
            team.setSpecialty(CollegeSpecialty.fromString(specialtyTerm));
            }
        else
            {
            specialtyTerm="";            
            team.setSpecialty(CollegeSpecialty.none);
            }
                   
        return specialtyTerm;
        }
    
    /**
     * 
     * @return a full military academy name.
     */
    private String makeMilitarySchoolName()
        {
        int pickval=MyRandom.next(1,selwgts.getMilitary_children());
        team.setUpos(CollegeTeamInfo.UnivPos.nowhere);
        team.setFlagship(true);
        int sum=0;
        int hinavy=sum+=selwgts.getMilNavy();
        int hiarmy=sum+=selwgts.getMilArmy();
        int hiaf=sum+=selwgts.getMilAirForce();
        int hicg=sum+=selwgts.getMilCoastGuard();
        int hicit=sum+=selwgts.getMilCitadel();
        int histate=sum+=selwgts.getMilState();
        if(pickval<=hinavy)
            return "Navy";
        else if(pickval<=hiarmy)
            return "Army";
        else if(pickval<=hiaf)
            return "Air Force";
        else if(pickval<=hicg)
            return "Coast Guard";
        else if(pickval<=hicit)
            return "The Citadel";
        else //if(pickval<=histate)
            return this.makeStateTerm()+" Military Institute";
        }

    /**
     * 
     * @return a full public university name
     */
    private String makePublicUniversityName()
        {
        String mpun="";
        
        team.setFlagship(true);
        int pickval=MyRandom.next(1,selwgts.getPublic_children());
        int sum=0;
        int hids=sum+=selwgts.getDirState();
        int hips=sum+=selwgts.getPublicSys();
        int hiup=sum+=selwgts.getUrbanPublic();
        
        if(pickval<=hids)
            {
            mpun=makeDirStateTerm();
            team.setUpos(CollegeTeamInfo.UnivPos.suffix);
            team.setUrban(false);
            team.setRegional(true);
            }            
        else if(pickval<=hips)
            {
            mpun= makePublicSystemTerm();
            team.setUrban(false);
            team.setRegional(false);
            }
        else 
            {
            mpun=makeUrbanPublicTerm();
            team.setUrban(true);
            team.setRegional(false);
            }
        return mpun;
        }

    /**
     * 
     * @return a full private university base name, without the optional " College" suffix.
     * @throws Exception 
     */
    private String makePrivateSchoolBaseTerm() throws Exception
        {
        team.setFlagship(true);
        int pickval=MyRandom.next(1,selwgts.getPrivBase_children());
        int hival=0;
        String psb="";
        int sum=0;
        int hicity=sum=selwgts.getPbCity();
        int histate=sum+=selwgts.getPbState();
        int hirlgs=sum+=selwgts.getPbReligious();
        int hiperson=sum+=selwgts.getPbPerson();
        int hicnick=sum+=selwgts.getPbCityNickname();
        int hisnick=sum+=selwgts.getPbStateNickname();
        if(pickval<=hicity)
            {
            psb=makeCityTerm();
            team.setUpos(MyRandom.next(0,1)==1? CollegeTeamInfo.UnivPos.prefix: CollegeTeamInfo.UnivPos.suffix);
            }
        else if(pickval<=histate)
            {
            psb=makeStateTerm();
            team.setUpos(CollegeTeamInfo.UnivPos.prefix);
            }
        else if(pickval<=hirlgs && team.getAffiliation()!= ReligionID.none)
            psb=makeReligiousTerm();
        else if(pickval<=hiperson)
            {
            psb=makePersonTerm();
            team.setUpos(CollegeTeamInfo.UnivPos.suffix);
            }
        else if(pickval<=hicnick)
            {
            psb=makeCityNicknameTerm();
            team.setUpos(CollegeTeamInfo.UnivPos.suffix);
            }
        else //if(pickval<=100)
            {
            psb=makeStateNicknameTerm();
            team.setUpos(CollegeTeamInfo.UnivPos.suffix);
            }
        return psb;
        }
    
    /**
     * 
     * @return a private university name with " College" tacked onto the end.
     * @throws Exception 
     */    
    private String makePrivateCollegeTerm() throws Exception
        {
        String mpcn=makePrivateSchoolBaseTerm();
        team.setUpos(CollegeTeamInfo.UnivPos.college);
        return mpcn;
        }
    
    /**
     * 
     * @return the name of a private institution, in either "(Name) College" or "Name" form.
     * @throws Exception 
     */
    private String makePrivateUniversityTerm() throws Exception
        {
        return this.makePrivateSchoolBaseTerm();
        }
    
    /**
     * The top-level generator method for this class.
     * @return the name of a university, be it either public, private, or military.
     * @throws Exception
     */
    public String produce() throws Exception
        {        
        // based on affiliation, make a sacred symbol
        //String symbolPhrase=this.makeSymbolTerm(team.getAffiliation());
        //boolean useSymbol=MyRandom.next(1,100)<=10; // right of <= is percent chance
        
        switch(team.getSoperator())
            {
            case oprivate:
                int pickval=MyRandom.next(1,selwgts.getPrivate_children());
                if(pickval<=selwgts.getPrivBase())
                    return makePrivateUniversityTerm();
                else                    
                    return makePrivateCollegeTerm();
            case omilitary:
                return makeMilitarySchoolName();
            case opublic: 
            default:
                return makePublicUniversityName();
            }        
        }

    }
