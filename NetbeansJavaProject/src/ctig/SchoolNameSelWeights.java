/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ctig;

/**
 * Used for specifying custom school name selection weights, which govern probabilities in generating school names.
 * @author RollerSimmer
 */
public class SchoolNameSelWeights 
    {
    // public, private and military already decided by school operator list
    
    // public name selection weights
        int dirState=20;
            int axialState=67;
            int diagState=33;
    
        int publicSys=70;
            int psState=45;
            int psStatePlusSpecialty=45;
            int psPerson=5;
            int psStateNickname=5;
    
        int urbanPublic=10;
            int upCity = 50;
            int upCityPlusSpecialty = 50;
            int upPublicSystem = 40;
            int upCityNickname = 5;
    
    // private name selection weights
        int privCollege = 10;
        int privBase = 90;
            int pbCity = 5;
            int pbState = 5;
            int pbReligious = 5;
            int pbPerson = 44;
            int pbCityNickname=3;
            int pbStateNickname=3;
    
    // military name selection weights
        int milNavy=1;
        int milArmy=1;
        int milAirForce=1;
        int milCoastGuard=1;
        int milCitadel=1;
        int milState=2;

    public int getPublic_children()
        {
        return getDirState()+getPublicSys()+getUrbanPublic();
        }

    public int getDirState()
        {
        return dirState;
        }

    public void setDirState(int dirState)
        {
        this.dirState = dirState;
        }

    public int getDirState_children()
        {
        return getAxialState()+getDiagState();
        }

    public int getAxialState()
        {
        return axialState;
        }

    public void setAxialState(int axialState)
        {
        this.axialState = axialState;
        }

    public int getDiagState()
        {
        return diagState;
        }

    public void setDiagState(int diagState)
        {
        this.diagState = diagState;
        }

    public int getPublicSys()
        {
        return publicSys;
        }

    public void setPublicSys(int publicSys)
        {
        this.publicSys = publicSys;
        }

    public int getPublicSys_children()
        {
        return getPsState()+getPsPerson()+getPsStateNickname()+getPsStatePlusSpecialty();
        }

    public int getPsState()
        {
        return psState;
        }

    public void setPsState(int psState)
        {
        this.psState = psState;
        }

    public int getPsStatePlusSpecialty()
        {
        return psStatePlusSpecialty;
        }

    public void setPsStatePlusSpecialty(int psStatePlusSpecialty)
        {
        this.psStatePlusSpecialty = psStatePlusSpecialty;
        }

    public int getPsPerson()
        {
        return psPerson;
        }

    public void setPsPerson(int psPerson)
        {
        this.psPerson = psPerson;
        }

    public int getPsStateNickname()
        {
        return psStateNickname;
        }

    public void setPsStateNickname(int psStateNickname)
        {
        this.psStateNickname = psStateNickname;
        }

    public int getUrbanPublic()
        {
        return urbanPublic;
        }

    public void setUrbanPublic(int urbanPublic)
        {
        this.urbanPublic = urbanPublic;
        }

    public int getUrbanPublic_children()
        {
        return getUpCity()+getUpCityNickname()+getUpCityPlusSpecialty()+getUpPublicSystem();
        }

    public int getUpCity()
        {
        return upCity;
        }

    public void setUpCity(int upCity)
        {
        this.upCity = upCity;
        }

    public int getUpCityPlusSpecialty()
        {
        return upCityPlusSpecialty;
        }

    public void setUpCityPlusSpecialty(int upCityPlusSpecialty)
        {
        this.upCityPlusSpecialty = upCityPlusSpecialty;
        }

    public int getUpPublicSystem()
        {
        return upPublicSystem;
        }

    public void setUpPublicSystem(int upPublicSystem)
        {
        this.upPublicSystem = upPublicSystem;
        }

    public int getUpCityNickname()
        {
        return upCityNickname;
        }

    public void setUpCityNickname(int upCityNickname)
        {
        this.upCityNickname = upCityNickname;
        }

    public int getPrivate_children()
        {
        return getPrivCollege()+getPrivBase();
        }

    public int getPrivCollege()
        {
        return privCollege;
        }

    public void setPrivCollege(int privCollege)
        {
        this.privCollege = privCollege;
        }

    public int getPrivBase()
        {
        return privBase;
        }

    public void setPrivBase(int privBase)
        {
        this.privBase = privBase;
        }

    public int getPrivBase_children()
        {
        return this.getPbCity()+this.getPbCityNickname()+this.getPbPerson()
              +this.getPbReligious()+this.getPbState()+this.getPbStateNickname();
        }

    public int getPbCity()
        {
        return pbCity;
        }

    public void setPbCity(int pbCity)
        {
        this.pbCity = pbCity;
        }

    public int getPbState()
        {
        return pbState;
        }

    public void setPbState(int pbState)
        {
        this.pbState = pbState;
        }

    public int getPbReligious()
        {
        return pbReligious;
        }

    public void setPbReligious(int pbReligious)
        {
        this.pbReligious = pbReligious;
        }

    public int getPbPerson()
        {
        return pbPerson;
        }

    public void setPbPerson(int pbPerson)
        {
        this.pbPerson = pbPerson;
        }

    public int getPbCityNickname()
        {
        return pbCityNickname;
        }

    public void setPbCityNickname(int pbCityNickname)
        {
        this.pbCityNickname = pbCityNickname;
        }

    public int getPbStateNickname()
        {
        return pbStateNickname;
        }

    public void setPbStateNickname(int pbStateNickname)
        {
        this.pbStateNickname = pbStateNickname;
        }

    public int getMilitary_children()
        {
        return this.getMilAirForce()+this.getMilArmy()+this.getMilCitadel()
              +this.getMilCoastGuard()+this.getMilNavy()+this.getMilState();
        }

    public int getMilNavy()
        {
        return milNavy;
        }

    public void setMilNavy(int milNavy)
        {
        this.milNavy = milNavy;
        }

    public int getMilArmy()
        {
        return milArmy;
        }

    public void setMilArmy(int milArmy)
        {
        this.milArmy = milArmy;
        }

    public int getMilAirForce()
        {
        return milAirForce;
        }

    public void setMilAirForce(int milAirForce)
        {
        this.milAirForce = milAirForce;
        }

    public int getMilCoastGuard()
        {
        return milCoastGuard;
        }

    public void setMilCoastGuard(int milCoastGuard)
        {
        this.milCoastGuard = milCoastGuard;
        }

    public int getMilCitadel()
        {
        return milCitadel;
        }

    public void setMilCitadel(int milCitadel)
        {
        this.milCitadel = milCitadel;
        }

    public int getMilState()
        {
        return milState;
        }

    public void setMilState(int milState)
        {
        this.milState = milState;
        }

    protected static SchoolNameSelWeights theinstance=null;
    
    protected SchoolNameSelWeights()
        {
        
        }
    
    public static SchoolNameSelWeights getinst()
        {
        if(theinstance==null)
            theinstance=new SchoolNameSelWeights();
        return theinstance;        
        }
    
    public String toString()
        {
        StringBuilder ts=new StringBuilder("");
        ts.append("SchoolNameSelWeights:\n");
        ts.append(String.format("  public=%d, children=%d\n",224,this.getPublic_children()));
        ts.append(String.format("    dirState=%d, children=%d\n",this.dirState,this.getDirState_children()));
        ts.append(String.format("      axialState=%d\n",this.axialState));
        ts.append(String.format("      diagState=%d\n",this.diagState));
        ts.append(String.format("    publicSys=%d, children=%d\n",this.publicSys,this.getPublicSys_children()));
        ts.append(String.format("      psState=%d\n",this.psState));
        ts.append(String.format("      psStatePlusSpecialty=%d\n",this.psStatePlusSpecialty));
        ts.append(String.format("      psPerson=%d\n",this.psPerson));
        ts.append(String.format("      psStateNickname=%d\n",this.psStateNickname));
        ts.append(String.format("    urbanPublic=%d, children=%d\n",this.urbanPublic,this.getUrbanPublic_children()));
        ts.append(String.format("      upCity=%d\n",this.upCity));
        ts.append(String.format("      upCityPlusSpecialty=%d\n",this.upCityPlusSpecialty));
        ts.append(String.format("      upPublicSystem=%d\n",this.upPublicSystem));
        ts.append(String.format("      upCityNickname=%d\n",this.upCityNickname));
        ts.append(String.format("  private=%d, children=%d\n",117,this.getPrivate_children()));
        ts.append(String.format("    privCollege=%d\n",this.privCollege));
        ts.append(String.format("    privBase=%d, children=%d\n",this.privBase,this.getPrivBase_children()));
        ts.append(String.format("      pbCity=%d\n",this.pbCity));
        ts.append(String.format("      pbState=%d\n",this.pbState));
        ts.append(String.format("      pbReligious=%d\n",this.pbReligious));
        ts.append(String.format("      pbPerson=%d\n",this.pbPerson));
        ts.append(String.format("      pbCityNickname=%d\n",this.pbCityNickname));
        ts.append(String.format("      pbStateNickname=%d\n",this.pbStateNickname));
        ts.append(String.format("  military=%d, children=%d\n",5,this.getMilitary_children()));
        ts.append(String.format("    milNavy=%d\n",this.milNavy));
        ts.append(String.format("    milArmy=%d\n",this.milArmy));
        ts.append(String.format("    milAirForce=%d\n",this.milAirForce));
        ts.append(String.format("    milCoastGuard=%d\n",this.milCoastGuard));
        ts.append(String.format("    milCitadel=%d\n",this.milCitadel));
        ts.append(String.format("    milState=%d\n",this.milState));
        return ts.toString();
        }
    }
