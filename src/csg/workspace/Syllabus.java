/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CourseSiteGeneratorApp;
import static csg.SyllabusPropertyType.*;
import static csg.workspace.style.Style.CLASS_INPUT_CONTROL;
import static csg.workspace.style.Style.CLASS_PANES_BACKGROUND;
import static csg.workspace.style.Style.CLASS_PANES_FOREGROUND;
import static csg.workspace.style.Style.CLASS_TITILEDPANE;
import djf.modules.AppGUIModule;
import static djf.modules.AppGUIModule.ENABLED;
import djf.ui.AppNodesBuilder;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author bingling.dong
 */
public class Syllabus {
    private Tab SyllabusTab; 
    CourseSiteGeneratorApp app;
    
    public Syllabus(Tab Syllabus, CourseSiteGeneratorApp app){
        SyllabusTab= Syllabus;
        this.app = app;
    }
    
    public void initLayout(){
        AppNodesBuilder csgBuilder = app.getGUIModule().getNodesBuilder();
        ScrollPane sp = csgBuilder.buildScrollPane("", null, CLASS_PANES_BACKGROUND, ENABLED);
        VBox mainPane=  csgBuilder.buildVBox(SYLLABUS_PANE, null, CLASS_PANES_BACKGROUND, ENABLED);
        VBox mainPane2=  csgBuilder.buildVBox(SYLLABUS_PANE, mainPane, CLASS_PANES_FOREGROUND, ENABLED);
        mainPane.setPadding(new Insets(10,10,10,10));
        
        TextArea desTextArea = csgBuilder.buildTextArea(SYLLABUS_DES_TEXTAREA, null, CLASS_INPUT_CONTROL, ENABLED);
        TextArea topicTextArea = csgBuilder.buildTextArea(SYLLABUS_TOPICS_TEXTAREA, null, CLASS_INPUT_CONTROL, ENABLED);
        TextArea prereqTextArea = csgBuilder.buildTextArea(SYLLABUS_PREREQ_TEXTAREA, null, CLASS_INPUT_CONTROL, ENABLED);
        TextArea outcomesTextArea = csgBuilder.buildTextArea(SYLLABUS_OUTCOMES_TEXTAREA, null, CLASS_INPUT_CONTROL, ENABLED);
        TextArea textbookTextArea = csgBuilder.buildTextArea(SYLLABUS_TEXTBOOKS_TEXTAREA, null, CLASS_INPUT_CONTROL, ENABLED);
        TextArea gradeComTextArea = csgBuilder.buildTextArea(SYLLABUS_GRADEDCOM_TEXTAREA, null, CLASS_INPUT_CONTROL, ENABLED);
        TextArea gradingNoteTextArea = csgBuilder.buildTextArea(SYLLABUS_GRADING_NOTE_TEXTAREA, null, CLASS_INPUT_CONTROL, ENABLED);
        TextArea academicDisTextArea = csgBuilder.buildTextArea(SYLLABUS_ACADEMIC_DIS_TEXTAREA, null, CLASS_INPUT_CONTROL, ENABLED);
        TextArea specialAssisTextArea = csgBuilder.buildTextArea(SYLLABUS_SPECIAL_ASSISTANCE_TEXTAREA, null, CLASS_INPUT_CONTROL, ENABLED);
        
       
        TitledPane desTitledPane = csgBuilder.buildTitledPane(SYLLABUS_DES_TITLEDPANE, mainPane2, CLASS_TITILEDPANE, ENABLED);
        desTitledPane.setContent(desTextArea);
        TitledPane topicTitledPane = csgBuilder.buildTitledPane(SYLLABUS_TOPICS_TITLEDPANE, mainPane2, CLASS_TITILEDPANE, ENABLED);
        topicTitledPane.setContent(topicTextArea);
        TitledPane prereqTitledPane = csgBuilder.buildTitledPane(SYLLABUS_PREREQ_TITLEDPANE, mainPane2, CLASS_TITILEDPANE, ENABLED);
        prereqTitledPane.setContent(prereqTextArea);
        TitledPane outcomesTitledPane = csgBuilder.buildTitledPane(SYLLABUS_OUTCOMES_TITLEDPANE, mainPane2, CLASS_TITILEDPANE, ENABLED);
        outcomesTitledPane.setContent(outcomesTextArea);
        TitledPane textbookTitledPane = csgBuilder.buildTitledPane(SYLLABUS_TEXTBOOKS_TITLEDPANE, mainPane2, CLASS_TITILEDPANE, ENABLED);
        textbookTitledPane.setContent(textbookTextArea);
        TitledPane gradeComTitledPane = csgBuilder.buildTitledPane(SYLLABUS_GRADEDCOM_TITLEDPANE, mainPane2, CLASS_TITILEDPANE, ENABLED);
        gradeComTitledPane.setContent(gradeComTextArea);
        TitledPane gradingNoteTitledPane = csgBuilder.buildTitledPane(SYLLABUS_GRADING_NOTE_TITLEDPANE, mainPane2, CLASS_TITILEDPANE, ENABLED);
        gradingNoteTitledPane.setContent(gradingNoteTextArea);
        TitledPane academicDisTitledPane = csgBuilder.buildTitledPane(SYLLABUS_ACADEMIC_DIS_TITLEDPANE, mainPane2, CLASS_TITILEDPANE, ENABLED);
        academicDisTitledPane.setContent(academicDisTextArea);
        TitledPane specialAssisTitledPane = csgBuilder.buildTitledPane(SYLLABUS_SPECIAL_ASSISTANCE_TITLEDPANE, mainPane2, CLASS_TITILEDPANE, ENABLED);
        specialAssisTitledPane.setContent(specialAssisTextArea);
        
        
        ObservableList<Node> titledPanes= mainPane2.getChildren();
        for(Node t: titledPanes){
           TitledPane tp= (TitledPane)t;
           tp.setExpanded(false);
        }
        sp.setContent(mainPane);
        sp.setFitToWidth(true);
        sp.setFitToHeight(true);
        SyllabusTab.setContent(sp);
    }
    
    public void reset(){
        AppGUIModule gui= app.getGUIModule();
        ((TitledPane)gui.getGUINode(SYLLABUS_DES_TITLEDPANE)).setExpanded(false);
        ((TitledPane)gui.getGUINode(SYLLABUS_TOPICS_TITLEDPANE)).setExpanded(false);
        ((TitledPane)gui.getGUINode(SYLLABUS_PREREQ_TITLEDPANE)).setExpanded(false);
        ((TitledPane)gui.getGUINode(SYLLABUS_OUTCOMES_TITLEDPANE)).setExpanded(false);
        ((TitledPane)gui.getGUINode(SYLLABUS_TEXTBOOKS_TITLEDPANE)).setExpanded(false);
        ((TitledPane)gui.getGUINode(SYLLABUS_GRADEDCOM_TITLEDPANE)).setExpanded(false);
        ((TitledPane)gui.getGUINode(SYLLABUS_GRADING_NOTE_TITLEDPANE)).setExpanded(false);
        ((TitledPane)gui.getGUINode(SYLLABUS_ACADEMIC_DIS_TITLEDPANE)).setExpanded(false);
        ((TitledPane)gui.getGUINode(SYLLABUS_SPECIAL_ASSISTANCE_TITLEDPANE)).setExpanded(false);
        
        ((TextArea)gui.getGUINode(SYLLABUS_DES_TEXTAREA)).clear();
        ((TextArea)gui.getGUINode(SYLLABUS_TOPICS_TEXTAREA)).clear();
        ((TextArea)gui.getGUINode(SYLLABUS_PREREQ_TEXTAREA)).clear();
        ((TextArea)gui.getGUINode(SYLLABUS_OUTCOMES_TEXTAREA)).clear();
        ((TextArea)gui.getGUINode(SYLLABUS_TEXTBOOKS_TEXTAREA)).clear();
        ((TextArea)gui.getGUINode(SYLLABUS_GRADEDCOM_TEXTAREA)).clear();
        ((TextArea)gui.getGUINode(SYLLABUS_GRADING_NOTE_TEXTAREA)).clear();
        ((TextArea)gui.getGUINode(SYLLABUS_ACADEMIC_DIS_TEXTAREA)).clear();
        ((TextArea)gui.getGUINode(SYLLABUS_SPECIAL_ASSISTANCE_TEXTAREA)).clear();
    }
}
