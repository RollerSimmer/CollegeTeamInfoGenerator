/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package html;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import team.CollegeTeamInfo;
import team.CollegeTeamList;

/**
 * Used to save the list of teams to a file.
 * @author RollerSimmer
 */
public class TeamListSaver 
    {
    CollegeTeamList thelist;
    BufferedWriter bfw;
    
    protected static TeamListSaver inst;
    
    protected TeamListSaver()
        {
        }

    public static TeamListSaver getInst()
        {
        if(inst==null)
            inst=new TeamListSaver();
        return inst;
        }
    
    public void save(CollegeTeamList thelist)
        {
        try 
            {
            this.thelist=thelist;
            
            FileChooser fc=new FileChooser();
            fc.setTitle("Save Team List as HTML");
            fc.setInitialDirectory(new File("output/html"));
            fc.setSelectedExtensionFilter(new ExtensionFilter("HTML file","html"));
            File file=fc.showSaveDialog(null);
            if(file==null)
                return;

            FileWriter fw;
              fw=new FileWriter(file); 
            bfw = new BufferedWriter(fw);

            writeHeader();
            writeLines();
            writeFooter();
            
            bfw.close();
            }
        catch(Exception ex) { System.err.println("In TeamListSaver.save(): "+ex); return; }
        }

    private void writeHeader()
        {
        try 
            {
            bfw.write("<!DOCTYPE html><html><body>\n");
            bfw.write("  <table>\n");
            bfw.write(String.format("    <!--heading-->%s\n",CollegeTeamInfo.makeHtmlHeading(false)));
            }
        catch(Exception ex) { System.err.println("In TeamListSaver.writeHeader(): "+ex); return; }
        }

    private void writeLines()
        {
        try 
            {
            for(int i=0;i<thelist.getList().size();i++)
                {
                bfw.write(thelist.getteam(i).toHtmlRow(true));
                }
            }
        catch(Exception ex) { System.err.println("In TeamListSaver.writeLines(): "+ex); return; }
        }

    private void writeFooter()
        {
        try 
            {
            bfw.write("  </table>\n");
            bfw.write("</body></html>\n");
            }
        catch(Exception ex) { System.err.println("In TeamListSaver.writeFooter(): "+ex); return; }
        }
    }
