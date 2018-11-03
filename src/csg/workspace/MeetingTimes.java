/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CourseSiteGeneratorApp;
import static csg.MeetingTimePropertyType.*;
import static csg.workspace.style.Style.*;
import static djf.modules.AppGUIModule.ENABLED;
import djf.ui.AppNodesBuilder;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 *
 * @author bingling.dong
 */
public class MeetingTimes {
    private Tab MTTab; 
    CourseSiteGeneratorApp app;
    
    public MeetingTimes(Tab tab, CourseSiteGeneratorApp app){
        MTTab= tab;
        this.app = app;
    }
    
    public void initLayout(){
        AppNodesBuilder csgBuilder = app.getGUIModule().getNodesBuilder();
        VBox mainPane=  csgBuilder.buildVBox(MT_Pane, null, CLASS_PANES_BACKGROUND, ENABLED);
        mainPane.setSpacing(10);
        mainPane.setPadding(new Insets(7,7,7,7));
        VBox vb1= csgBuilder.buildVBox("", mainPane, CLASS_PANES_FOREGROUND, ENABLED);
        VBox vb2= csgBuilder.buildVBox("", mainPane, CLASS_PANES_FOREGROUND, ENABLED);
        VBox vb3= csgBuilder.buildVBox("", mainPane, CLASS_PANES_FOREGROUND, ENABLED);
        vb1.setPadding(new Insets(5,5,5,5));
        vb2.setPadding(new Insets(5,5,5,5));
        vb3.setPadding(new Insets(5,5,5,5));
        
        //LECTURE PANE
        HBox hb1 =csgBuilder.buildHBox("", vb1, CLASS_PANES_FOREGROUND, ENABLED);
        hb1.setSpacing(10);
        hb1.setPadding(new Insets(5,5,5,5));
        Button addLecture= csgBuilder.buildTextButton(MT_ADD_LECUTRE_BUTTON, hb1, CLASS_ADD_REMOVE_BUTTON, ENABLED);
        Button removeLecture= csgBuilder.buildTextButton(MT_REMOVE_LECTURE_BUTTON, hb1, CLASS_ADD_REMOVE_BUTTON, ENABLED);
        Label lecutreLabels = csgBuilder.buildLabel(MT_LECUTRE_LABEL, hb1, CLASS_MINOR_LABELS, ENABLED);
        TableView lectures = csgBuilder.buildTableView(MT_LECTURE_TABLEVIEW, vb1, CLASS_TABLEVIEW, ENABLED);
        VBox.setVgrow(lectures, Priority.ALWAYS);
        initLectureTable(lectures);
        
        //RECITATION PANE
        HBox hb2 =csgBuilder.buildHBox("", vb2, CLASS_PANES_FOREGROUND, ENABLED);
        hb2.setSpacing(10);
        hb2.setPadding(new Insets(5,5,5,5));
        Button addRec= csgBuilder.buildTextButton(MT_ADD_REC_BUTTON, hb2, CLASS_ADD_REMOVE_BUTTON, ENABLED);
        Button removeRec= csgBuilder.buildTextButton(MT_REMOVE_REC_BUTTON, hb2, CLASS_ADD_REMOVE_BUTTON, ENABLED);
        Label RecLabels = csgBuilder.buildLabel(MT_REC_LABEL, hb2, CLASS_MINOR_LABELS, ENABLED);
        TableView recitations = csgBuilder.buildTableView(MT_REC_TABLEVIEW, vb2, CLASS_TABLEVIEW, ENABLED);
        initRecTable(recitations);
        VBox.setVgrow(recitations, Priority.ALWAYS);  
        
        //LAB PANE
        HBox hb3 =csgBuilder.buildHBox("", vb3, CLASS_PANES_FOREGROUND, ENABLED);
        hb3.setSpacing(10);
        hb3.setPadding(new Insets(5,5,5,5));
        Button addLab= csgBuilder.buildTextButton(MT_ADD_LAB_BUTTON, hb3, CLASS_ADD_REMOVE_BUTTON, ENABLED);
        Button removeLab= csgBuilder.buildTextButton(MT_REMOVE_LAB_BUTTON, hb3, CLASS_ADD_REMOVE_BUTTON, ENABLED);
        Label labLabels = csgBuilder.buildLabel(MT_LAB_LABEL, hb3, CLASS_MINOR_LABELS, ENABLED);
        TableView labs = csgBuilder.buildTableView(MT_LAB_TABLEVIEW, vb3, CLASS_TABLEVIEW, ENABLED);
        initLabTable(labs);
        VBox.setVgrow(labs, Priority.ALWAYS);
        
        MTTab.setContent(mainPane);
    }
    
    public void initLectureTable(TableView lectures){
        AppNodesBuilder csgBuilder = app.getGUIModule().getNodesBuilder();
        TableColumn lecSectionColumn= csgBuilder.buildTableColumn(MT_LECTURE_SECTION_COLUMN, lectures, CLASS_TABLE_COLUMNS);
        TableColumn lecDaysColumn= csgBuilder.buildTableColumn(MT_LECTURE_DAYS_COLUMN, lectures, CLASS_TABLE_COLUMNS);
        TableColumn lecTimeColumn= csgBuilder.buildTableColumn(MT_LECTURE_TIMES_COLUMN, lectures, CLASS_TABLE_COLUMNS);
        TableColumn lecRoomColumn= csgBuilder.buildTableColumn(MT_LECTURE_ROOM_COLUMN, lectures, CLASS_TABLE_COLUMNS);
        
        lecSectionColumn.setCellValueFactory(new PropertyValueFactory<String, String>("section"));
        lecSectionColumn.prefWidthProperty().bind(lectures.widthProperty().multiply(1.0/4.0));
        
        lecDaysColumn.setCellValueFactory(new PropertyValueFactory<String, String>("days"));
        lecDaysColumn.prefWidthProperty().bind(lectures.widthProperty().multiply(1.0/4.0));
        
        lecTimeColumn.setCellValueFactory(new PropertyValueFactory<String, String>("time"));
        lecTimeColumn.prefWidthProperty().bind(lectures.widthProperty().multiply(1.0/4.0));
        
        lecRoomColumn.setCellValueFactory(new PropertyValueFactory<String, String>("room"));
        lecRoomColumn.prefWidthProperty().bind(lectures.widthProperty().multiply(1.0/4.0));
    }
    
    public void initRecTable(TableView recitations){
        AppNodesBuilder csgBuilder = app.getGUIModule().getNodesBuilder();
        TableColumn recSectionColumn= csgBuilder.buildTableColumn(MT_REC_SECTION_COLUMN, recitations, CLASS_TABLE_COLUMNS);
        TableColumn recDaysAndTimeColumn= csgBuilder.buildTableColumn(MT_REC_DAYSANDTIME_COLUMN, recitations, CLASS_TABLE_COLUMNS);
        TableColumn recRoomColumn= csgBuilder.buildTableColumn(MT_REC_ROOM_COLUMN, recitations, CLASS_TABLE_COLUMNS);
        TableColumn recTA1Column= csgBuilder.buildTableColumn(MT_REC_TA1_COLUMN, recitations, CLASS_TABLE_COLUMNS);
        TableColumn recTA2Column= csgBuilder.buildTableColumn(MT_REC_TA2_COLUMN, recitations, CLASS_TABLE_COLUMNS);
       
        recSectionColumn.setCellValueFactory(new PropertyValueFactory<String, String>("section"));
        recSectionColumn.prefWidthProperty().bind(recitations.widthProperty().multiply(1.0/5.0));
        
        recDaysAndTimeColumn.setCellValueFactory(new PropertyValueFactory<String, String>("dayAndTime"));
        recDaysAndTimeColumn.prefWidthProperty().bind(recitations.widthProperty().multiply(1.0/5.0));
        
        recRoomColumn.setCellValueFactory(new PropertyValueFactory<String, String>("room"));
        recRoomColumn.prefWidthProperty().bind(recitations.widthProperty().multiply(1.0/5.0));
        
        recTA1Column.setCellValueFactory(new PropertyValueFactory<String, String>("TA1"));
        recTA1Column.prefWidthProperty().bind(recitations.widthProperty().multiply(1.0/5.0));
        
        recTA2Column.setCellValueFactory(new PropertyValueFactory<String, String>("TA2"));
        recTA2Column.prefWidthProperty().bind(recitations.widthProperty().multiply(1.0/5.0));
    
    }
    
    public void initLabTable(TableView labs){
        AppNodesBuilder csgBuilder = app.getGUIModule().getNodesBuilder();
        TableColumn labSectionColumn= csgBuilder.buildTableColumn(MT_LAB_SECTION_COLUMN, labs, CLASS_TABLE_COLUMNS);
        TableColumn labDaysAndTimeColumn= csgBuilder.buildTableColumn(MT_LAB_DAYSANDTIME_COLUMN, labs, CLASS_TABLE_COLUMNS);
        TableColumn labRoomColumn= csgBuilder.buildTableColumn(MT_LAB_ROOM_COLUMN, labs, CLASS_TABLE_COLUMNS);
        TableColumn labTA1Column= csgBuilder.buildTableColumn(MT_LAB_TA1_COLUMN, labs, CLASS_TABLE_COLUMNS);
        TableColumn labTA2Column= csgBuilder.buildTableColumn(MT_LAB_TA2_COLUMN, labs, CLASS_TABLE_COLUMNS);
       
        labSectionColumn.setCellValueFactory(new PropertyValueFactory<String, String>("section"));
        labSectionColumn.prefWidthProperty().bind(labs.widthProperty().multiply(1.0/5.0));
        
        labDaysAndTimeColumn.setCellValueFactory(new PropertyValueFactory<String, String>("dayAndTime"));
        labDaysAndTimeColumn.prefWidthProperty().bind(labs.widthProperty().multiply(1.0/5.0));
        
        labRoomColumn.setCellValueFactory(new PropertyValueFactory<String, String>("room"));
        labRoomColumn.prefWidthProperty().bind(labs.widthProperty().multiply(1.0/5.0));
        
        labTA1Column.setCellValueFactory(new PropertyValueFactory<String, String>("TA1"));
        labTA1Column.prefWidthProperty().bind(labs.widthProperty().multiply(1.0/5.0));
        
        labTA2Column.setCellValueFactory(new PropertyValueFactory<String, String>("TA2"));
        labTA2Column.prefWidthProperty().bind(labs.widthProperty().multiply(1.0/5.0));
    }
    
}
