package xml;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import ctig.CtiFactory;
import ctig.PersonNamePicker.GenderType;
import ctig.PersonNamePicker.NameType;
import ctig.PersonNamePicker.OriginType;
import ctig.SchoolNameSelWeights;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import placedata.ReligionData;
import placedata.StateDemographics;
import javafx.scene.paint.Color;
import team.CollegeTeamInfo.UnivPos;
import team.TeamAdjective;
import team.TeamNickname;
import team.SchoolOperator;
import univdata.SpecialtyData;

/**
 * Used to read a data file for the college team information generator.
 * @author RollerSimmer
 */
public class DataReader
    {   
    CtiFactory ctif;
    Document doc;
    boolean doprint;    
    static boolean debug=false;
    SchoolNameSelWeights selwgts;

    public DataReader(CtiFactory ctif,boolean doprint)
        {
        this.ctif = ctif;
        this.doprint=doprint;
        this.selwgts=SchoolNameSelWeights.getinst();
        }

    /**
     * Adds a state to the factory state list from XML file.
     * pre: the factory should not be null.
     * post: the factory's state list will contain a state corresponding to the XML element.
     * @param e the XML element
     */
    private void addstate(Element e)
        {
        String name=e.getAttribute("name");
        String abrv=e.getAttribute("abrv");
        String nick=e.getAttribute("nick");
        String stn=e.getAttribute("spec-team-nick");
        int popk=Integer.parseInt(e.getAttribute("popk"),10);        
        int weight=Integer.parseInt(e.getAttribute("weight"),10);
        short spanish=(short) Integer.parseInt(e.getAttribute("spanish"),10);
        short mormon=(short) Integer.parseInt(e.getAttribute("mormon"),10);
        short catholic=(short) Integer.parseInt(e.getAttribute("catholic"),10);
        short methodist= (short) Integer.parseInt(e.getAttribute("methodist"), 10);
        short baptist=(short) Integer.parseInt(e.getAttribute("baptist"),10);
        short jewish=(short) Integer.parseInt(e.getAttribute("jewish"),10);
        short lutheran=(short) Integer.parseInt(e.getAttribute("lutheran"),10);
       
        ReligionData rd=new ReligionData(mormon,catholic,baptist,methodist,jewish,lutheran);
        StateDemographics sd=new StateDemographics(rd,spanish);
        
        this.ctif.getLocationPicker().addstate(name,abrv,nick,stn,popk,weight,sd,null);
        }

    /**
     * Adds a city to the factory city list from XML file.
     * pre: the factory should not be null.
     * post: the factory's city list will contain a city corresponding to the XML element.
     * @param e the XML element
     */
    private void addcity(Element e)
        {
        String name=e.getAttribute("name");
        String[] nicks = new String[2];
        nicks[0]=e.getAttribute("nick1");
        nicks[1]=e.getAttribute("nick2");
        int popk=Integer.parseInt(e.getAttribute("popk"),10);
        int weight=Integer.parseInt(e.getAttribute("weight"),10);
        String stateabrv=e.getAttribute("state");
        
        int v;
        v=Integer.parseInt(e.getAttribute("northern"),10);
        boolean northern = false;
        if (v==0) northern = false; else if(v==1) northern = true;
        v=Integer.parseInt(e.getAttribute("southern"),10);
        boolean southern = false;
        if (v==0) southern = false; else if(v==1) southern = true;
        v=Integer.parseInt(e.getAttribute("western"),10);
        boolean western = false;
        if (v==0) western = false; else if(v==1) western = true;
        v=Integer.parseInt(e.getAttribute("eastern"),10);
        boolean eastern = false;
        if (v==0) eastern = false; else if(v==1) eastern = true;
        
        ctif.
            getLocationPicker().
            addcity
            (name,nicks,popk,weight,stateabrv,northern,southern,western,eastern);
        }
    
    private void readstates(Document doc)
        {
        NodeList stateListList = doc.getElementsByTagName("state-list");

        if(doprint) System.out.println("--------------States--------------");

        for(int i=0;i<Math.min(1,stateListList.getLength());i++)
            {
            NodeList statelist = stateListList.item(i).getChildNodes();                

            for(int j=0;j<statelist.getLength();j++)
                {

                Node state=statelist.item(j);

                if (state.getNodeType() == Node.ELEMENT_NODE) 
                    {
                    Element e = (Element) state;                        
                    String nodename=e.getNodeName();
                    if(doprint) System.out.println("Current Element: " + nodename);
                    if(nodename.equals("state"))
                        {
                        if(doprint) System.out.println("  City Name: " + e.getAttribute("name"));
                        if(doprint) System.out.println("  Abbreviation: " + e.getAttribute("abrv"));
                        if(doprint) System.out.println("  Nickname: " + e.getAttribute("nick"));
                        if(doprint) System.out.println("  Selection Weight: " + e.getAttribute("weight"));
                        
                        if(!debug)
                            addstate(e);
                        }                    
                    else
                        if(doprint) System.out.println("  bad node name for list entry");
                    }
                }
            }
        }
    
    private void readcities(Document doc)
        {
        if(doc==null) return;
        
        NodeList cityListList = doc.getElementsByTagName("city-list");

        if(doprint) System.out.println("---------------Cities-------------");

        for(int i=0;i<Math.min(1,cityListList.getLength());i++)
            {
            NodeList citylist = cityListList.item(i).getChildNodes();                

            for(int j=0;j<citylist.getLength();j++)
                {

                Node city=citylist.item(j);

                if (city.getNodeType() == Node.ELEMENT_NODE) 
                    {
                    Element e = (Element) city;                        
                    String nodename=e.getNodeName();
                    if(doprint) System.out.println("Current Element: " + nodename);
                    if(nodename.equals("city"))
                        {
                        if(doprint) 
                            {
                            System.out.println("  City Name: " + e.getAttribute("name"));
                            System.out.println("  Nickname A: " + e.getAttribute("nick1"));
                            System.out.println("  Nickname B: " + e.getAttribute("nick2"));
                            System.out.println("  Selection Weight: " + e.getAttribute("weight"));
                            System.out.println("  State: " + e.getAttribute("state"));
                            System.out.println("  Is in northern part? " + e.getAttribute("northern"));
                            System.out.println("  Is in southern part? " + e.getAttribute("southern"));
                            System.out.println("  Is in western part? " + e.getAttribute("western"));
                            System.out.println("  Is in eastern part? " + e.getAttribute("eastern"));
                            System.out.println("  popk: " + e.getAttribute("popk"));                            
                            }

                        if(!debug)
                            addcity(e);
                        }
                    else
                        System.out.println("  bad node name for list entry");
                    }
                }
            }
        }
    
    private void readPersonNames(Document doc)
        {
        if(doc==null) return;
        
        NodeList ssll = doc.getElementsByTagName("spanish-surname-list");
        NodeList asll = doc.getElementsByTagName("american-surname-list");
        NodeList amll = doc.getElementsByTagName("american-male-name-list");
        NodeList smll = doc.getElementsByTagName("spanish-male-name-list");
        NodeList afll = doc.getElementsByTagName("american-fem-name-list");
        NodeList sfll = doc.getElementsByTagName("spanish-fem-name-list");
        
        NodeList listlist=null;
        NodeList[] lla={ssll,asll,smll,amll,sfll,afll};
        OriginType[] otypes={OriginType.spanish,OriginType.english
                            ,OriginType.spanish,OriginType.english
                            ,OriginType.spanish,OriginType.english };
        NameType[] ntypes={NameType.last,NameType.last
                          ,NameType.first,NameType.first
                          ,NameType.first,NameType.first};
        GenderType[] gtypes={GenderType.other,GenderType.other
                            ,GenderType.male,GenderType.male
                            ,GenderType.female,GenderType.female};

        if(doprint) System.out.println("-------------Personal Names---------------");
        
        for(int i=0;i<6;i++)
            {
            listlist=lla[i];
            for(int j=0;j<Math.min(1,listlist.getLength());j++)
                {
                NodeList list = listlist.item(j).getChildNodes();                

                for(int k=0;k<list.getLength();k++)
                    {
                    Node namenode = list.item(k);

                    if (namenode.getNodeType() == Node.ELEMENT_NODE) 
                        {
                        Element e = (Element) namenode;                        
                        String nodename=e.getNodeName();
                        if(doprint) System.out.println("Current Element: " + nodename);
                        if(nodename.equals("first-name") || nodename.equals("surname"))
                            {
                            if(doprint) System.out.println("  Person Name: " + e.getAttribute("name"));
                            if(doprint) System.out.println("  weight: " + e.getAttribute("weight"));
                            String name=e.getAttribute("name");
                            Integer weight=Integer.parseInt(e.getAttribute("weight"),10);                    

                            if( name!=null && weight!=null && !debug )
                                ctif.getPersonNamePicker().add(name,weight,ntypes[i],otypes[i],gtypes[i]);
                            }
                        else
                            if(doprint) System.out.println("  bad node name for list entry");
                        }
                    }
                }
            }

        }
    
    private void readSpecialties(Document doc)
        {
        if(doc==null) return;
        
        NodeList sll= doc.getElementsByTagName("specialty-list"); // list of specialty lists

        if(doprint) System.out.println("--------------Specialties--------------");

        for(int i=0;i<Math.min(1,sll.getLength());i++)
            {
            NodeList sl = sll.item(i).getChildNodes();  // current specialty list

            for(int j=0;j<sl.getLength();j++)
                {

                Node specialty=sl.item(j);

                if (specialty.getNodeType() == Node.ELEMENT_NODE) 
                    {
                    Element e = (Element) specialty;     
                    String nodename=e.getNodeName();
                    if(doprint) System.out.println("Current Element: " + nodename);
                    if(nodename.equals("specialty"))
                        {
                        if(doprint) 
                            {
                            System.out.println("  Specialty Name: " + e.getAttribute("suffix"));
                            System.out.println("  Nickname A: " + e.getAttribute("weight"));                    
                            System.out.println("  university-position: " + e.getAttribute("university-position"));
                            }
                        String suffix=e.getAttribute("suffix");                    
                        int weight=Integer.parseInt(e.getAttribute("weight"),10);                    
                        UnivPos upos;
                        try
                            {
                            upos=UnivPos.valueOf(e.getAttribute("university-position"));
                            }
                        catch(Exception ex)
                            {
                            upos=UnivPos.suffix;
                            }
                        if(!debug)
                            this.ctif.getSpecialtyPicker().additem(new SpecialtyData(suffix,upos), weight);
                        }
                    else
                        if(doprint) System.out.println("  bad node name for list entry");
                    }
                }
            }
        }

    private void readSymbolNouns(Document doc)
        {
        if(doc==null) return;
        
        NodeList snll= doc.getElementsByTagName("symbol-noun-list"); // list of symbol noun lists

        if(doprint) System.out.println("--------------Symbol Nouns--------------");

        for(int i=0;i<Math.min(1,snll.getLength());i++)
            {
            NodeList snl = snll.item(i).getChildNodes();  // current symbol noun list

            for(int j=0;j<snl.getLength();j++)
                {

                Node symnoun=snl.item(j);

                if (symnoun.getNodeType() == Node.ELEMENT_NODE) 
                    {
                    Element e = (Element) symnoun;                        
                    String nodename=e.getNodeName();
                    if(doprint) System.out.println("Current Element: " + nodename);
                    if(nodename.equals("symbol-noun"))
                        {
                        if(doprint) System.out.println("  term: " + e.getAttribute("term"));
                        if(doprint) System.out.println("  weight: " + e.getAttribute("weight"));                    

                        String term=e.getAttribute("term");                    
                        int weight=Integer.parseInt(e.getAttribute("weight"),10);                    
                        if(!debug)
                            this.ctif.getSymbolNounPicker().additem(term,weight);
                        }
                    else
                        if(doprint) System.out.println("  bad node name for list entry");
                    }
                }
            }
        }

    private void readSymbolAdjectives(Document doc)
        {
        if(doc==null) return;
        
        NodeList sall= doc.getElementsByTagName("symbol-adjective-list"); // list of symbol adjective lists

        if(doprint) System.out.println("--------------Symbol Adjectives--------------");

        for(int i=0;i<Math.min(1,sall.getLength());i++)
            {
            NodeList sal = sall.item(i).getChildNodes();  // current symbol adjective list

            for(int j=0;j<sal.getLength();j++)
                {

                Node symadj=sal.item(j);

                if (symadj.getNodeType() == Node.ELEMENT_NODE) 
                    {
                    Element e = (Element) symadj;     
                    String nodename=e.getNodeName();
                    if(doprint) System.out.println("Current Element: " + nodename);
                    if(nodename.equals("symbol-adjective"))
                        {
                        if(doprint) System.out.println("  term: " + e.getAttribute("term"));
                        if(doprint) System.out.println("  weight: " + e.getAttribute("weight"));
                        String term=e.getAttribute("term");
                        int weight=Integer.parseInt(e.getAttribute("weight"),10);
                        if(!debug)
                            this.ctif.getSymbolAdjectivePicker().additem(term,weight);
                        }
                    else
                        if(doprint) System.out.println("  bad node name for list entry");
                    }
                }
            }
        }

    private void readTeamAdjectives(Document doc)
        {
        if(doc==null) return;
        
        NodeList tall= doc.getElementsByTagName("team-adjective-list"); // list of team adjective lists

        if(doprint) System.out.println("--------------Team Adjectives--------------");

        for(int i=0;i<Math.min(1,tall.getLength());i++)
            {
            NodeList tal = tall.item(i).getChildNodes();  // current team adjective list

            for(int j=0;j<tal.getLength();j++)
                {

                Node symadj=tal.item(j);

                if (symadj.getNodeType() == Node.ELEMENT_NODE) 
                    {
                    Element e = (Element) symadj;                                            
                    String nodename=e.getNodeName();
                    if(doprint) System.out.println("Current Element: " + nodename);
                    if(nodename.equals("team-adjective"))
                        {
                        if(doprint) 
                            {
                            System.out.println("  word: " + e.getAttribute("word"));
                            System.out.println("  weight: " + e.getAttribute("weight"));
                            System.out.println("  atype: " + e.getAttribute("atype"));
                            System.out.println("  red: " + e.getAttribute("red"));
                            System.out.println("  green: " + e.getAttribute("green"));
                            System.out.println("  blue: " + e.getAttribute("blue"));                    
                            }
                        String word=e.getAttribute("word");    
                        int weight=Integer.parseInt(e.getAttribute("weight"),10);
                        TeamAdjective.Type atype;
                        atype = TeamAdjective.getTypeVal(e.getAttribute("atype"));
                        String redstr=e.getAttribute("red");
                        int red = redstr.equals("")?  0: Integer.parseInt(redstr,10);
                        String greenstr=e.getAttribute("green");
                        int green = greenstr.equals("")?  0: Integer.parseInt(greenstr,10);
                        String bluestr=e.getAttribute("blue");
                        int blue = bluestr.equals("")?  0: Integer.parseInt(bluestr,10);                        
                        Color adjcolor=Color.rgb(red,green,blue);
                        TeamAdjective ta=new TeamAdjective(word,atype,adjcolor);
                        if(!debug)
                            this.ctif.getTeamAdjectivePicker().additem(ta,weight);
                        }
                    else
                        if(doprint) System.out.println("  bad node name for list entry");
                    }
                }
            }
        }

    private void readTeamNicknames(Document doc)
        {
        if(doc==null) return;
        
        NodeList tnll= doc.getElementsByTagName("team-nickname-list"); // list of team nickname lists

        if(doprint) System.out.println("--------------Team Nicknames--------------");

        for(int i=0;i<Math.min(1,tnll.getLength());i++)
            {
            NodeList tnl = tnll.item(i).getChildNodes();  // current team nickname list

            for(int j=0;j<tnl.getLength();j++)
                {

                Node symadj=tnl.item(j);

                if (symadj.getNodeType() == Node.ELEMENT_NODE) 
                    {
                    Element e = (Element) symadj;                                            
                    String nodename=e.getNodeName();
                    if(doprint) System.out.println("Current Element: " + nodename);
                    if(nodename.equals("team-nickname"))
                        {
                        if(doprint) 
                            {
                            System.out.println("  word: " + e.getAttribute("word"));
                            System.out.println("  weight: " + e.getAttribute("weight"));
                            System.out.println("  use-pref-color: " + e.getAttribute("use-pref-color"));
                            System.out.println("  pref-red: " + e.getAttribute("pref-red"));
                            System.out.println("  pref-green: " + e.getAttribute("pref-green"));
                            System.out.println("  pref-blue: " + e.getAttribute("pref-blue"));
                            }
                        String word=e.getAttribute("word");    
                        int weight=Integer.parseInt(e.getAttribute("weight"),10);
                        String upcs=e.getAttribute("use-pref-color");
                        boolean colorused = upcs.toLowerCase().equals("yes");                                        
                        String redstr=e.getAttribute("pref-red");
                        int red = redstr.equals("")?  0: Integer.parseInt(redstr,10);
                        String greenstr=e.getAttribute("pref-green");
                        int green = greenstr.equals("")?  0: Integer.parseInt(greenstr,10);
                        String bluestr=e.getAttribute("pref-blue");
                        int blue = bluestr.equals("")?  0: Integer.parseInt(bluestr,10);                       
                        Color tncolor=Color.rgb(red,green,blue);
                        TeamNickname tn=new TeamNickname(word,colorused,tncolor);
                        if(!debug)
                            this.ctif.getTeamNicknamePicker().additem(tn,weight);
                        }
                    else
                        if(doprint) System.out.println("  bad node name for list entry");
                    }
                }
            }
        }



    private void readColorDefs(Document doc)
        {
        if(doc==null) return;
        
        NodeList cdll= doc.getElementsByTagName("color-def-list"); // list of operator lists

        if(doprint) System.out.println("--------------Color Definitions--------------");

        for(int i=0;i<Math.min(1,cdll.getLength());i++)
            {
            NodeList cdl = cdll.item(i).getChildNodes();  // current operator list
            
            if(cdl.getLength()>0)
                {
                ctif.getColorTable().getLookup().clear();
                
                for(int j=0;j<cdl.getLength();j++)
                    {
                    Node cdef=cdl.item(j);

                    if (cdef.getNodeType() == Node.ELEMENT_NODE) 
                        {
                        Element e = (Element) cdef;            
                        String nodename=e.getNodeName();
                        if(doprint) System.out.println("Current Element: " + nodename);
                        if(nodename.equals("color-def"))
                            {
                            if(doprint) 
                                {
                                System.out.println("  color: " + e.getAttribute("color"));
                                System.out.println("  red: " + e.getAttribute("red"));
                                System.out.println("  green: " + e.getAttribute("green"));
                                System.out.println("  blue: " + e.getAttribute("blue"));
                                }
                            String colorname=e.getAttribute("color");
                            int red=Integer.parseInt(e.getAttribute("red"),10);
                            int green=Integer.parseInt(e.getAttribute("green"),10);
                            int blue=Integer.parseInt(e.getAttribute("blue"),10);
                            Color color=Color.rgb(red,green,blue);
                            
                            if(!debug)
                                this.ctif.getColorTable().defineColor(colorname,color);
                            }
                        else
                            if(doprint) System.out.println("  bad node name for list entry");
                        }
                    }
                }
            else
                this.ctif.getOperatorPicker().autoinit();
            }
        }

    private void readColorRow(Node cmrow)
        {
        if(cmrow==null) return;
        
        Element e = (Element) cmrow;
        
        NodeList celllist = cmrow.getChildNodes();

        if(doprint) System.out.println("  color: " + e.getAttribute("color"));
        if(doprint) System.out.println("  weight: " + e.getAttribute("weight"));
        String colorname=e.getAttribute("color");
        int colorweight=Integer.parseInt(e.getAttribute("weight"),10);
        
        if(!debug)
            // add row to factory's table
            ctif.getColorTable().addrow(colorname,colorweight);
        
        for(int k=0;k<celllist.getLength();k++)
            {                            
            Node cell=celllist.item(k); // color match cell

            if (cell.getNodeType() == Node.ELEMENT_NODE) 
                {
                Element ee = (Element) cell;
                String cellname=ee.getNodeName();                                
                if(doprint) System.out.println("  Current Element: " + cellname);
                if(cellname.equals("cm"))
                    {
                    if(doprint) System.out.println("    color: " + ee.getAttribute("color"));
                    if(doprint) System.out.println("    weight: " + ee.getAttribute("weight"));
                    String matchname=ee.getAttribute("color");
                    int matchweight=Integer.parseInt(ee.getAttribute("weight"),10);
                    if(!debug)
                        // add match-cell to factory's table
                        ctif.getColorTable().getrow(colorname).addmatch(matchname,matchweight);
                    }                                
                }
            }
        }
    
    private void readColorTable(Document doc)
        {
        if(doc==null) return;
        
        NodeList cmtl= doc.getElementsByTagName("color-match-table"); // list of color match tables
        
        if(doprint) System.out.println("--------------Color Match Table--------------");

        for(int i=0;i<Math.min(1,cmtl.getLength());i++)
            {
            NodeList cmt = cmtl.item(i).getChildNodes();  // current team nickname list

            for(int j=0;j<cmt.getLength();j++)
                {

                Node cmrow=cmt.item(j); // color match row

                if (cmrow.getNodeType() == Node.ELEMENT_NODE) 
                    {
                    Element e = (Element) cmrow;                                            
                    String nodename=e.getNodeName();
                    if(doprint) System.out.println("Current Element: " + nodename);
                    if(nodename.equals("cmr"))
                        {                        
                        readColorRow(cmrow);
                        } // if cmrow
                    else
                        if(doprint) System.out.println("  bad node name for list entry");
                    }
                }
            }
        }
    
    private void readNameSelWeights(Document doc)
        {
        if(doc==null) return;
        
        NodeList cmtl= doc.getElementsByTagName("school-name-selection-weights"); // list of color match tables
        
        if(doprint) System.out.println("--------------School Name Selection Weights--------------");
        
        NodeList publist=doc.getElementsByTagName("public");        
        Node npublic=publist.getLength()>0? publist.item(0): null;
        if(npublic!=null)
            {
            Element epublic=npublic.getNodeType() == Node.ELEMENT_NODE? (Element) npublic: null;
            if(epublic != null)
                readPublicSelElement(epublic);
            }
                
        NodeList privlist=doc.getElementsByTagName("private");
        Node nprivate=privlist.getLength()>0? privlist.item(0): null;
        if(nprivate!=null)
            {
            Element eprivate=nprivate.getNodeType() == Node.ELEMENT_NODE? (Element) nprivate: null;
            if(eprivate != null)
                readPrivateSelElement(eprivate);
            }
                
        NodeList mililist=doc.getElementsByTagName("military");
        Node nmilitary=mililist.getLength()>0? mililist.item(0): null;
        if(nmilitary!=null)
            {
            Element emilitary=nmilitary.getNodeType() == Node.ELEMENT_NODE? (Element) nmilitary: null;
            if(emilitary != null)
                readMilitarySelElement(emilitary);
            }
        
        if(this.doprint)
            System.out.println(this.selwgts.toString());
        }

    private void readPublicSelElement(Element epublic)
        {
        int weight=Integer.parseInt(epublic.getAttribute("weight"),10);                                
        SchoolOperator otype=SchoolOperator.opublic;                
        if(!debug)
            this.ctif.getOperatorPicker().additem(otype,weight);
        
        NodeList lvl1children;
        NodeList lvl2children;
        
        lvl1children=epublic.getChildNodes();
        for(int i=0;i<lvl1children.getLength();i++)
            {
            Node n=lvl1children.item(i);
            Element e=makeElementFromNode(n);
            if(e==null) continue;
            int childweight=Integer.parseInt(e.getAttribute("weight"),10);
            try {int gcws=Integer.parseInt(e.getAttribute("children-weight-sum"),10);}
            catch(Exception ex){}
            if(n.getNodeName().equals("dir-state"))
                {
                selwgts.setDirState(childweight);
                lvl2children=e.getChildNodes();
                for(int j=0;j<lvl2children.getLength();j++)
                    {
                    Node nn=lvl2children.item(j);
                    Element ee=makeElementFromNode(nn);
                    if(ee==null) continue;
                    int gchildweight=Integer.parseInt(ee.getAttribute("weight"),10);
                    if(nn.getNodeName().equals("axial-state"))
                        selwgts.setAxialState(gchildweight);
                    else if(nn.getNodeName().equals("diag-state"))
                        selwgts.setDiagState(gchildweight);
                    }                                           
                }
            else if(n.getNodeName().equals("public-system"))
                {                
                selwgts.setPublicSys(childweight);
                lvl2children=e.getChildNodes();
                for(int j=0;j<lvl2children.getLength();j++)
                    {
                    Node nn=lvl2children.item(j);
                    Element ee=makeElementFromNode(nn);
                    if(ee==null) continue;
                    int gchildweight=Integer.parseInt(ee.getAttribute("weight"),10);
                    if(nn.getNodeName().equals("state"))
                        selwgts.setPsState(gchildweight);
                    else if(nn.getNodeName().equals("state-plus-specialty"))
                        selwgts.setPsStatePlusSpecialty(gchildweight);
                    else if(nn.getNodeName().equals("person"))
                        selwgts.setPsPerson(gchildweight);
                    else if(nn.getNodeName().equals("state-nickname"))
                        selwgts.setPsStateNickname(gchildweight);
                    }
                }
            else if(n.getNodeName().equals("urban-public"))
                {                
                selwgts.setUrbanPublic(childweight);
                lvl2children=e.getChildNodes();
                for(int j=0;j<lvl2children.getLength();j++)
                    {
                    Node nn=lvl2children.item(j);
                    Element ee=makeElementFromNode(nn);
                    if(ee==null) continue;
                    int gchildweight=Integer.parseInt(ee.getAttribute("weight"),10);
                    if(nn.getNodeName().equals("city"))
                        selwgts.setUpCity(gchildweight);
                    else if(nn.getNodeName().equals("city-plus-specialty"))
                        selwgts.setUpCityPlusSpecialty(gchildweight);
                    else if(nn.getNodeName().equals("public-system"))
                        selwgts.setUpPublicSystem(gchildweight);
                    else if(nn.getNodeName().equals("city-nickname"))
                        selwgts.setUpCityNickname(gchildweight);
                    }
                }
            }
        }

    private void readPrivateSelElement(Element eprivate)
        {
        int weight=Integer.parseInt(eprivate.getAttribute("weight"),10);                                
        SchoolOperator otype=SchoolOperator.oprivate;                
        if(!debug)
            this.ctif.getOperatorPicker().additem(otype,weight);

        NodeList lvl1children;
        NodeList lvl2children;
        
        lvl1children=eprivate.getChildNodes();
        for(int i=0;i<lvl1children.getLength();i++)
            {
            Node n=lvl1children.item(i);
            Element e=makeElementFromNode(n);
            if(e==null) continue;
            int childweight=Integer.parseInt(e.getAttribute("weight"),10);
            try {int gcws=Integer.parseInt(e.getAttribute("children-weight-sum"),10);}
            catch(Exception ex){}
            if(n.getNodeName().equals("private-college"))
                selwgts.setPrivCollege(childweight);
            else if(n.getNodeName().equals("private-base"))
                {
                lvl2children=e.getChildNodes();
                for(int j=0;j<lvl2children.getLength();j++)
                    {
                    Node nn=lvl2children.item(j);
                    Element ee=makeElementFromNode(nn);
                    if(ee==null) continue;
                    int gchildweight=Integer.parseInt(ee.getAttribute("weight"),10);
                    if(nn.getNodeName().equals("city"))
                        selwgts.setPbCity(gchildweight);
                    else if(nn.getNodeName().equals("state"))
                        selwgts.setPbState(gchildweight);
                    else if(nn.getNodeName().equals("religious"))
                        selwgts.setPbReligious(gchildweight);
                    else if(nn.getNodeName().equals("person"))
                        selwgts.setPbPerson(gchildweight);
                    else if(nn.getNodeName().equals("state-nickname"))
                        selwgts.setPbStateNickname(gchildweight);
                    else if(nn.getNodeName().equals("city-nickname"))
                        selwgts.setPbCityNickname(gchildweight);
                    }
                }
            }
        }

    private void readMilitarySelElement(Element emilitary)
        {
        int weight=Integer.parseInt(emilitary.getAttribute("weight"),10);                                
        SchoolOperator otype=SchoolOperator.omilitary;                
        if(!debug)
            this.ctif.getOperatorPicker().additem(otype,weight);

        NodeList lvl1children;
        NodeList lvl2children;
        
        lvl1children=emilitary.getChildNodes();
        for(int i=0;i<lvl1children.getLength();i++)
            {
            Node n=lvl1children.item(i);
            Element e=makeElementFromNode(n);
            if(e==null) continue;
            int childweight=Integer.parseInt(e.getAttribute("weight"),10);
            try {int gcws=Integer.parseInt(e.getAttribute("children-weight-sum"),10);}
            catch(Exception ex){}
            if(n.getNodeName().equals("navy"))
                selwgts.setMilNavy(childweight);
            else if(n.getNodeName().equals("army"))
                selwgts.setMilArmy(childweight);
            else if(n.getNodeName().equals("air-force"))
                selwgts.setMilAirForce(childweight);
            else if(n.getNodeName().equals("coast-guard"))
                selwgts.setMilCoastGuard(childweight);
            else if(n.getNodeName().equals("the-citadel"))
                selwgts.setMilCitadel(childweight);
            else if(n.getNodeName().equals("state-military-inst"))
                selwgts.setMilState(childweight);
            }
        }
    
    private void readSchoolOperators(Document doc)
        {
        if(doc==null) return;
        
        NodeList opll= doc.getElementsByTagName("school-operator-list"); // list of operator lists

        if(doprint) System.out.println("--------------School Operators--------------");

        for(int i=0;i<Math.min(1,opll.getLength());i++)
            {
            NodeList opl = opll.item(i).getChildNodes();  // current operator list
            
            if(opl.getLength()>0)
                {
                this.ctif.getOperatorPicker().clear();
                
                for(int j=0;j<opl.getLength();j++)
                    {

                    Node symadj=opl.item(j);

                    if (symadj.getNodeType() == Node.ELEMENT_NODE) 
                        {
                        Element e = (Element) symadj;            
                        String nodename=e.getNodeName();
                        if(doprint) System.out.println("Current Element: " + nodename);
                        if(nodename.equals("school-operator"))
                            {
                            if(doprint) System.out.println("  type: " + e.getAttribute("type"));
                            if(doprint) System.out.println("  weight: " + e.getAttribute("weight"));
                            SchoolOperator otype;
                            otype=SchoolOperator.valueOf(e.getAttribute("type"));
                            int weight=Integer.parseInt(e.getAttribute("weight"),10);
                            if(!debug)
                                this.ctif.getOperatorPicker().additem(otype,weight);
                            }
                        else
                            if(doprint) System.out.println("  bad node name for list entry");
                        }
                    }
                }
            else
                this.ctif.getOperatorPicker().autoinit();
            }
        }
    
    private Element makeElementFromNode(Node n)
        {
        return n.getNodeType() == Node.ELEMENT_NODE? (Element) n: null;
        }

    public void read(String datafilename)
        {
        try 
            {
            File xmlfile;
            xmlfile = new File(datafilename);
            
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(xmlfile);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            Element root=doc.getDocumentElement();
            if(doprint) System.out.println("Root element :" + root.getNodeName());

            readstates(doc);            
            readcities(doc);    
            readPersonNames(doc);            
            readSpecialties(doc);
            readSymbolNouns(doc);          
            readSymbolAdjectives(doc); 
            readTeamAdjectives(doc);
            readTeamNicknames(doc);
            readSchoolOperators(doc);
            readColorDefs(doc);
            readColorTable(doc);
            readNameSelWeights(doc);
            } 
        catch (Exception e) 
            {
            e.printStackTrace();
            }
        }

    }
