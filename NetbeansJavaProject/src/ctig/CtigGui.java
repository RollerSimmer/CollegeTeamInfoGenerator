/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctig;

import html.TeamListSaver;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import team.CollegeTeamInfo;
import team.CollegeTeamList;
import xml.DataReader;

/**
 * The main GUI for the college team information generator.
 * @author RollerSimmer
 */
public class CtigGui extends Application
    {        
    ArrayList<TeamRow> teamRowList;
    
    Stage stage;

    TableView table;
    GridPane grid;
    
    ScrollPane gridscroll;
    VBox root;
    HBox btnrow;
    
    Label teamAmtLabel;
    TextField teamAmtText;
    Label minEnrollLabel;
    TextField minEnrollText;
    Label maxEnrollLabel;
    TextField maxEnrollText;
    Button genbtn;    
    Button savebtn;
    ProgressBar progress;
    
    CollegeTeamList tmlist;
    ArrayList<TeamTableRow> trlist;  // team row list
    ObservableList<TeamTableRow> otrlist;  // obseravable team row list 
    CtiFactory ctif;
    
    int workdone=0;
    int amtWorkItems=1;
    
    enum TeamColumn
        {
        index
        ,primary        
        ,secondary        
        ,helmet
        ,full
        ,common
        ,abrv
        ,nick
        ,operator
        ,affiliation
        ,flagship
        ,enrollment
        ,city
        ,state
        }
    
    HashMap<String,TeamColumn> columnmap;
    
    @Override
    public void start(Stage stage)
        {
        this.stage=stage;
        otrlist=null;       
        //trlist.clear();
        trlist=new ArrayList<>();
        teamRowList=new ArrayList<>();                
        
        ctif=CtiFactory.getInstance();
        DataReader dr=new DataReader(ctif,false);
        dr.read("data/name-elements.xml"); 
        tmlist=new CollegeTeamList(20000,75000);        
        
        table=new TableView();
        table.setPrefSize(1000,800);
        
        teamAmtLabel=new Label("Team Amount:");
        teamAmtText=new TextField("300");        
        teamAmtText.setPrefWidth(75);
        minEnrollLabel=new Label("Min Enrollment:");
        minEnrollText=new TextField("500");
        minEnrollText.setPrefWidth(75);
        maxEnrollLabel=new Label("Max Enrollment:");
        maxEnrollText=new TextField("50000");
        maxEnrollText.setMaxWidth(75);
        genbtn = new Button("Generate Teams");
        EventHandler<ActionEvent> genhandler=
            new EventHandler<ActionEvent>()
                {
                @Override
                public void handle(ActionEvent event)
                    { 
                    doGenerateTeams(); 
                    }
                };
        genbtn.setOnAction(genhandler);
        savebtn=new Button("Save as HTML");
        EventHandler<ActionEvent> savehandler=
            new EventHandler<ActionEvent>()
                {
                @Override
                public void handle(ActionEvent event)
                    { 
                    doSaveTeamsAsHtml(); 
                    }
                };
        savebtn.setOnAction(savehandler);
        
        workdone=0;
        amtWorkItems=100;

        Task<Void> task = new Task<Void>() 
            {
            @Override public Void call() 
                {
                updateProgress(workdone,amtWorkItems);
                System.out.printf("Updating progress: workdone=%d, amtWorkItems=%d\n"
                                 ,workdone,amtWorkItems);
                return null;
                }
            };
        
        progress=new ProgressBar();
        //progress.setVisible(true);
        progress.progressProperty().bind(task.progressProperty());
        
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();

        btnrow=new HBox();
        btnrow.setSpacing(10);
        btnrow.getChildren().add(teamAmtLabel);                
        btnrow.getChildren().add(teamAmtText);        
        btnrow.getChildren().add(minEnrollLabel);                
        btnrow.getChildren().add(minEnrollText);
        btnrow.getChildren().add(maxEnrollLabel);                
        btnrow.getChildren().add(maxEnrollText);
        btnrow.getChildren().add(genbtn);        
        btnrow.getChildren().add(savebtn);        
        btnrow.getChildren().add(progress);                
        
        root = new VBox();        
        root.setSpacing(10);
        root.setPadding(new Insets(10,10,10,10));
        root.getChildren().add(table);
        root.getChildren().add(btnrow);
        
        Scene scene = new Scene(root, 1000,650);
        
        stage.setTitle("College Team Info Generator");
        stage.setScene(scene);
        stage.show();
        }

    private void doGenerateTeams()
        {
        tmlist.getList().clear();
        trlist.clear();
        tmlist.setMinEnrollment(Integer.parseInt(this.minEnrollText.getText()));
        tmlist.setMaxEnrollment(Integer.parseInt(this.maxEnrollText.getText()));

        CollegeTeamInfo team;
        
        int amtTeams=Integer.parseInt(this.teamAmtText.getText());
        amtWorkItems=amtTeams;
        for(int i=0;i<amtTeams;i++)
            {
            team=CtiFactory.quickproduce();        
            
            if(tmlist.add(team))
                {
                workdone=i;
                trlist.add(new TeamTableRow(trlist.size(),team));                    
                System.out.printf("\"%s %s\" were added to team row list.\n"
                                  ,team.getCommonSchoolName(),team.getNickname());
                }
                        
            }
        
        updateTeamTable();
        }

    private void doSaveTeamsAsHtml()
        {
        TeamListSaver.getInst().save(tmlist);
        }
    
    private void addColumnLabelToGrid(String text,int x,double maxwidth)
        {
        Button btn;
        btn=new Button(text);
        btn.setWrapText(true);  
        if(maxwidth>0)
            btn.setPrefWidth(maxwidth);
        grid.add(btn,x,0);
        }

    private void addTextCellToGrid(Region n,int x,int y,double maxwidth)
        {
        if(maxwidth>0)
            n.setPrefWidth(maxwidth);
        grid.add(n,x,y);
        }
        
    private void updateTeamTable()
        {
        this.otrlist=FXCollections.observableList(this.trlist);
        this.table.setItems(otrlist);
        this.table.getColumns().clear();
        
        int i=0;
        
        TableColumn<TeamRow,Integer> indexcol = new TableColumn<TeamRow,Integer>("#");
        indexcol.setCellValueFactory(new PropertyValueFactory<TeamRow,Integer>("index"));
        
        TableColumn<TeamRow,Canvas> primcol = new TableColumn<TeamRow,Canvas>("C1");
        primcol.setCellValueFactory(new PropertyValueFactory<TeamRow,Canvas>("primary"));        

        TableColumn<TeamRow,Canvas> seccol = new TableColumn<TeamRow,Canvas>("C2");
        seccol.setCellValueFactory(new PropertyValueFactory<TeamRow,Canvas>("secondary"));        
        
        TableColumn<TeamRow,Group> helmcol = new TableColumn<TeamRow,Group>("Helmet");
        helmcol.setCellValueFactory(new PropertyValueFactory<TeamRow,Group>("helmet"));        
        
        TableColumn<TeamRow,String> fullcol = new TableColumn<TeamRow,String>("Full School Name");
        fullcol.setCellValueFactory(new PropertyValueFactory<TeamRow,String>("full"));
        
        TableColumn<TeamRow,String> commoncol = new TableColumn<TeamRow,String>("Common School Name");
        commoncol.setCellValueFactory(new PropertyValueFactory<TeamRow,String>("common"));
        
        TableColumn<TeamRow,String> abrvcol = new TableColumn<TeamRow,String>("Abrv");
        abrvcol.setCellValueFactory(new PropertyValueFactory<TeamRow,String>("abrv"));
        
        TableColumn<TeamRow,String> nickcol = new TableColumn<TeamRow,String>("Nickname");
        nickcol.setCellValueFactory(new PropertyValueFactory<TeamRow,String>("tnick"));
        
        TableColumn<TeamRow,String> opcol = new TableColumn<TeamRow,String>("Operator");
        opcol.setCellValueFactory(new PropertyValueFactory<TeamRow,String>("operator"));
        
        TableColumn<TeamRow,String> affilcol = new TableColumn<TeamRow,String>("Affiliation");
        affilcol.setCellValueFactory(new PropertyValueFactory<TeamRow,String>("affiliation"));
        
        TableColumn<TeamRow,String> flagcol = new TableColumn<TeamRow,String>("Flagship?");
        flagcol.setCellValueFactory(new PropertyValueFactory<TeamRow,String>("flagshipStatus"));
        
        TableColumn<TeamRow,Integer> enrolcol = new TableColumn<TeamRow,Integer>("Enrollment");
        enrolcol.setCellValueFactory(new PropertyValueFactory<TeamRow,Integer>("enrollment"));
        
        TableColumn<TeamRow,String> citycol = new TableColumn<TeamRow,String>("City");
        citycol.setCellValueFactory(new PropertyValueFactory<TeamRow,String>("city"));
        
        TableColumn<TeamRow,Integer> cityPopCol = new TableColumn<TeamRow,Integer>("City Pop");
        cityPopCol.setCellValueFactory(new PropertyValueFactory<TeamRow,Integer>("cityPop"));
        
        TableColumn<TeamRow,String> statecol = new TableColumn<TeamRow,String>("State");
        statecol.setCellValueFactory(new PropertyValueFactory<TeamRow,String>("state"));
        
        TableColumn<TeamRow,Integer> statePopCol = new TableColumn<TeamRow,Integer>("State Pop");
        statePopCol.setCellValueFactory(new PropertyValueFactory<TeamRow,Integer>("statePop"));
        
        table.getColumns().addAll(indexcol,primcol,seccol,helmcol,fullcol,commoncol
                                 ,abrvcol,nickcol,opcol,affilcol,flagcol,enrolcol
                                 ,citycol,cityPopCol,statecol,statePopCol);
        
        }
    
    private void updateTeamGrid()
        {        
        this.grid.getChildren().clear();
        this.teamRowList.clear();
        grid.getColumnConstraints().clear();

        double w=this.root.getWidth()-150;
        double wcolor=20*w/100;
        double wname=40*w/100;
        double winfo=20*w/100;
        double wloc=20*w/100;            
        
        double[] widths=
            {
            wcolor/4,   wcolor/4,   wcolor/4,    wcolor/4,
            wname/3,    wname/3,    wname/9,     2*wname/9,            
            winfo/4,    winfo/4,    winfo/4,     winfo/4,
            wloc/2,     wloc/2                
            };        
        
        // make a heading row first
        if(true)
            {
            Button btn;
            int j=0;
            addColumnLabelToGrid("#",j,widths[j++]);
            addColumnLabelToGrid("C1",j,widths[j++]);
            addColumnLabelToGrid("C2",j,widths[j++]);
            addColumnLabelToGrid("Helmet",j,widths[j++]);
            addColumnLabelToGrid("Full School Name",j,widths[j++]);
            addColumnLabelToGrid("Common School Name",j,widths[j++]);
            addColumnLabelToGrid("Abrv",j,widths[j++]);
            addColumnLabelToGrid("Nickname",j,widths[j++]);
            addColumnLabelToGrid("Operator",j,widths[j++]);
            addColumnLabelToGrid("Affiliation",j,widths[j++]);
            addColumnLabelToGrid("Flagship?",j,widths[j++]);
            addColumnLabelToGrid("Enrollment",j,widths[j++]);
            addColumnLabelToGrid("City (pop)",j,widths[j++]);
            addColumnLabelToGrid("State (pop)",j,widths[j++]);
            }
        
        // then fill-in all team rows
        CollegeTeamInfo team;
        System.out.printf("The team list contains %d teams.\n",tmlist.getList().size());
        for(int i=0;i<tmlist.getList().size();i++)
            {
            team = tmlist.getteam(i);            
            TeamRow trow;
                        
            trow=new TeamRow(i+1,team);
            this.teamRowList.add(i,trow);
            trow=this.teamRowList.get(i);
            
            int j=0;
            //addCellToGrid(
            //,j,i+1,widths[j++]);
            addTextCellToGrid(trow.getIndex(),j,i+1,widths[j++]);
            grid.add(trow.getPrimary(),j++,i+1);
            grid.add(trow.getSecondary(),j++,i+1);
            grid.add(trow.getHelmet(),j++,i+1);
            addTextCellToGrid(trow.getFull(),j,i+1,widths[j++]);
            addTextCellToGrid(trow.getCommon(),j,i+1,widths[j++]);
            addTextCellToGrid(trow.getAbrv(),j,i+1,widths[j++]);
            addTextCellToGrid(trow.getTnick(),j,i+1,widths[j++]);
            addTextCellToGrid(trow.getOperator(),j,i+1,widths[j++]);
            addTextCellToGrid(trow.getAffiliation(),j,i+1,widths[j++]);
            addTextCellToGrid(trow.getFlagshipStatus(),j,i+1,widths[j++]);
            addTextCellToGrid(trow.getEnrollment(),j,i+1,widths[j++]);
            addTextCellToGrid(trow.getCity(),j,i+1,widths[j++]);
            addTextCellToGrid(trow.getState(),j,i+1,widths[j++]);
            }        
        
        }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
        {
        launch(args);
        }    
    }
