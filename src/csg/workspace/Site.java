/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CourseSiteGeneratorApp;
import static csg.SitePropertyType.*;
import static csg.workspace.style.Style.*;
import static djf.modules.AppGUIModule.ENABLED;
import djf.ui.AppNodesBuilder;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 *
 * @author bingling.dong
 */
public class Site {
    private Tab SiteTab;
    CourseSiteGeneratorApp app;
    
    public Site(Tab site, CourseSiteGeneratorApp app){
        SiteTab= site;
        this.app = app;
    }
    
    public void initLayout(){
        AppNodesBuilder csgBuilder = app.getGUIModule().getNodesBuilder();
        VBox mainPane=  csgBuilder.buildVBox(SITE_PANE, null, CLASS_PANES_BACKGROUND, ENABLED);
        
        VBox banner= csgBuilder.buildVBox(SITE_BANNER_PANE, mainPane, CLASS_PANES_FOREGROUND, ENABLED);
        HBox page= csgBuilder.buildHBox(SITE_PAGE_PANE, mainPane, CLASS_PANES_FOREGROUND, ENABLED);
        VBox style= csgBuilder.buildVBox(SITE_STYLE_PANE, mainPane, CLASS_PANES_FOREGROUND, ENABLED);
        GridPane instructor= csgBuilder.buildGridPane(SITE_INSTRUCTOR_PANE, mainPane, CLASS_PANES_FOREGROUND, ENABLED);
        banner.setSpacing(8);
        banner.setPadding(new Insets(5,5,5,5));
        page.setSpacing(50);
        page.setAlignment(Pos.CENTER_LEFT);
        page.setPadding(new Insets(5,5,5,5));
        style.setSpacing(8);
        style.setPadding(new Insets(5,5,5,5));
        instructor.setHgap(8);
        instructor.setVgap(8);
        instructor.setPadding(new Insets(5,5,5,5));
                
        createBanner(banner);
        createPage(page);
        createStyle(style);
        createInstructor(instructor);
        mainPane.setSpacing(8);
        mainPane.setPadding(new Insets(8,5,8,5));
        SiteTab.setContent(mainPane);
    }
    
    private void createBanner(Pane parentPane){
        AppNodesBuilder csgBuilder = app.getGUIModule().getNodesBuilder();
        Label label = csgBuilder.buildLabel(SITE_BANNER_LABEL, parentPane, CLASS_MAJOR_LABELS, ENABLED);
        
        
        //First line of the gui
        HBox hBox1= csgBuilder.buildHBox("", parentPane, CLASS_PANES_FOREGROUND, ENABLED);
        hBox1.setSpacing(10);
        Label subjectLabel = csgBuilder.buildLabel(SITE_BANNER_COURSE_SUBJECT_LABEL, hBox1, CLASS_MINOR_LABELS, ENABLED);
        ArrayList<String> listForSubject = new ArrayList<>();
        listForSubject.add("CSE");
        ComboBox subjectCombo = csgBuilder.buildComboBox(SITE_BANNER_COURSE_SUBJECT_COMBO, listForSubject, "CSE", hBox1, CLASS_INPUT_CONTROL, ENABLED);
        
        Label numberLabel = csgBuilder.buildLabel(SITE_BANNER_COURSE_NUMBER_LABEL, hBox1, CLASS_MINOR_LABELS, ENABLED);
        ArrayList<String> listForNumber = new ArrayList<>();
        listForNumber.add("219");
        listForNumber.add("220");
        ComboBox numberCombo = csgBuilder.buildComboBox(SITE_BANNER_COURSE_NUMBER_COMBO, listForNumber, "219", hBox1, CLASS_INPUT_CONTROL, ENABLED);
        
        //Second line of the gui
        HBox hBox2= csgBuilder.buildHBox("", parentPane, CLASS_PANES_FOREGROUND, ENABLED);
        hBox2.setSpacing(10);
        Label semesterLabel = csgBuilder.buildLabel(SITE_BANNER_COURSE_SEMESTER_LABEL, hBox2, CLASS_MINOR_LABELS, ENABLED);
        ArrayList<String> listForSemester = new ArrayList<>();
        listForSemester.add("Fall");
        ComboBox semesterCombo = csgBuilder.buildComboBox(SITE_BANNER_COURSE_SEMESTER_COMBO, listForSemester, "Fall", hBox2, CLASS_INPUT_CONTROL, ENABLED);
        
        Label yearLabel = csgBuilder.buildLabel(SITE_BANNER_COURSE_YEAR_LABEL, hBox2, CLASS_MINOR_LABELS, ENABLED);
        ArrayList<String> listForYear = new ArrayList<>();
        listForYear.add("2018");
        ComboBox yearCombo = csgBuilder.buildComboBox(SITE_BANNER_COURSE_YEAR_COMBO, listForYear, "2018", hBox2, CLASS_INPUT_CONTROL, ENABLED);
        
        //third line of the pane
        HBox hBox3= csgBuilder.buildHBox("", parentPane, CLASS_PANES_FOREGROUND, ENABLED);
        hBox3.setSpacing(10);
        Label titleLabel = csgBuilder.buildLabel(SITE_BANNER_COURSE_TITLE_LABEL, hBox3, CLASS_MINOR_LABELS, ENABLED);
        TextField titleTextField = csgBuilder.buildTextField(SITE_BANNER_COURSE_TITLE_TEXTFIELD, hBox3, CLASS_INPUT_CONTROL, ENABLED);
        
        //fourth line of the pane
        HBox hBox4= csgBuilder.buildHBox("", parentPane, CLASS_PANES_FOREGROUND, ENABLED);
        hBox4.setSpacing(10);
        Label exportDirLabel = csgBuilder.buildLabel(SITE_BANNER_EXPORT_DIR_LABEL, hBox4, CLASS_MINOR_LABELS, ENABLED);
        Label exportDirAddressLabel = csgBuilder.buildLabel(SITE_BANNER_EXPORT_DIR_ADDRESS, hBox4, CLASS_LABEL_BACKGROUND, ENABLED);
    }
    
    private void createPage(Pane parentPane){
        AppNodesBuilder csgBuilder = app.getGUIModule().getNodesBuilder();
        Label label = csgBuilder.buildLabel(SITE_PAGE_LABEL, parentPane, CLASS_MAJOR_LABELS, ENABLED);
        CheckBox homeCheckBox= csgBuilder.buildCheckBox(SITE_PAGE_HOME_CHECKBOX, parentPane, CLASS_LABEL_BACKGROUND, ENABLED);
        CheckBox syllabusCheckBox= csgBuilder.buildCheckBox(SITE_PAGE_SYLLABUS_CHECKBOX, parentPane, CLASS_LABEL_BACKGROUND, ENABLED);
        CheckBox scheduleCheckBox= csgBuilder.buildCheckBox(SITE_PAGE_SCHEDULE_CHECKBOX, parentPane, CLASS_LABEL_BACKGROUND, ENABLED);
        CheckBox hwsCheckBox= csgBuilder.buildCheckBox(SITE_PAGE_HWS_CHECKBOX, parentPane, CLASS_LABEL_BACKGROUND, ENABLED);
    }
    
    private void createStyle(Pane parentPane){
        AppNodesBuilder csgBuilder = app.getGUIModule().getNodesBuilder();
        Label label = csgBuilder.buildLabel(SITE_STYLE_LABEL, parentPane, CLASS_MAJOR_LABELS, ENABLED);
        HBox hboxMain = csgBuilder.buildHBox("", parentPane, CLASS_PANES_FOREGROUND, ENABLED);
        VBox vboxButtons = csgBuilder.buildVBox("", hboxMain, CLASS_PANES_FOREGROUND, ENABLED);
        VBox vboxImages = csgBuilder.buildVBox("", hboxMain, CLASS_PANES_FOREGROUND, ENABLED);
        vboxButtons.setSpacing(10);
        vboxImages.setSpacing(10);
        
        Button FaviconButton = csgBuilder.buildTextButton(SITE_STYLE_FAVICON_BUTTON, vboxButtons, CLASS_SITE_STYLE_BUTTONS, ENABLED);
        Button NavbarImageButton = csgBuilder.buildTextButton(SITE_STYLE_NAVBAR_BUTTON, vboxButtons, CLASS_SITE_STYLE_BUTTONS, ENABLED);
        Button LeftFooterImageButton = csgBuilder.buildTextButton(SITE_STYLE_LEFT_FOOTER_BUTTON, vboxButtons, CLASS_SITE_STYLE_BUTTONS, ENABLED);
        Button RightFooterImageButton = csgBuilder.buildTextButton(SITE_STYLE_RIGHT_FOOTER_BUTTON, vboxButtons, CLASS_SITE_STYLE_BUTTONS, ENABLED);
        FaviconButton.prefWidthProperty().bind((parentPane.widthProperty().divide(3)));
        NavbarImageButton.prefWidthProperty().bind((parentPane.widthProperty().divide(3)));
        LeftFooterImageButton.prefWidthProperty().bind((parentPane.widthProperty().divide(3)));
        RightFooterImageButton.prefWidthProperty().bind((parentPane.widthProperty().divide(3)));
        vboxImages.getChildren().addAll(FaviconButton,NavbarImageButton,LeftFooterImageButton,RightFooterImageButton);
        
//        Image faviconImage= new Image("");
//        ImageView faviconImageView = new ImageView(faviconImage);
//        
//        Image NavbarImage= new Image("");
//        ImageView NavbarImageView = new Image
//        
        HBox FontsHbox= csgBuilder.buildHBox("", parentPane, CLASS_PANES_FOREGROUND, ENABLED);
        Label fontColorLabel = csgBuilder.buildLabel(SITE_STYLE_FONT_COLORS_LABEL, FontsHbox, CLASS_MINOR_LABELS, ENABLED);
        ComboBox fontAndColorsCombo = csgBuilder.buildComboBox(label, null, CLASS_TABS, FontsHbox, CLASS_INPUT_CONTROL, ENABLED);
        Label noteLabel = csgBuilder.buildLabel(SITE_STYLE_FONT_NOTE_LABEL, parentPane, CLASS_MINOR_LABELS, ENABLED);
    }
    
    //GridPane is in the parameter
    private void createInstructor(GridPane parentPane){ 
        AppNodesBuilder csgBuilder = app.getGUIModule().getNodesBuilder();
        Label label = csgBuilder.buildLabel(SITE_INSTRUCTOR_LABEL, parentPane, 0, 0, 1, 1, CLASS_MAJOR_LABELS, ENABLED);
        Label nameLabel = csgBuilder.buildLabel(SITE_INSTRUCTOR_NAME_LABEL, parentPane, 0, 1, 1, 1, CLASS_MINOR_LABELS, ENABLED);
        Label roomLabel = csgBuilder.buildLabel(SITE_INSTRUCTOR_ROOM_LABEL, parentPane, 3, 1, 1, 1, CLASS_MINOR_LABELS, ENABLED);
        Label emailLabel = csgBuilder.buildLabel(SITE_INSTRUCTOR_EMAIL_LABEL, parentPane, 0, 2, 1, 1, CLASS_MINOR_LABELS, ENABLED);
        Label homePageLabel = csgBuilder.buildLabel(SITE_INSTRUCTOR_HOMEPAGE_LABEL, parentPane, 3, 2, 1, 1, CLASS_MINOR_LABELS, ENABLED);
        
        TextField nameTextField = csgBuilder.buildTextField(SITE_INSTRUCTOR_NAME_TEXTFIELD, parentPane, 1, 1, 2, 1, CLASS_INPUT_CONTROL, ENABLED);
        TextField roomTextField = csgBuilder.buildTextField(SITE_INSTRUCTOR_ROOM_TEXTFIELD, parentPane, 4, 1, 2, 1, CLASS_INPUT_CONTROL, ENABLED);
        TextField emailTextField = csgBuilder.buildTextField(SITE_INSTRUCTOR_EMAIL_TEXTFIELD, parentPane, 1, 2, 2, 1, CLASS_INPUT_CONTROL, ENABLED);
        TextField homePageTextField = csgBuilder.buildTextField(SITE_INSTRUCTOR_HOMEPAGE_TEXTFIELD, parentPane, 4, 2, 2, 1, CLASS_INPUT_CONTROL, ENABLED);
        TextArea instructorOHTextArea = csgBuilder.buildTextArea(SITE_INSTRUCTOR_OFFICEHOUR_TEXTAREA, null, CLASS_INPUT_CONTROL, ENABLED);
        
        TitledPane instructorOHTitledPane = csgBuilder.buildTitledPane(SITE_INSTRUCTOR_OFFICEHOUR_TITLEDPANE, null, CLASS_PANES_FOREGROUND, ENABLED);
        instructorOHTitledPane.setContent(instructorOHTextArea);
        parentPane.add(instructorOHTitledPane, 0, 3, 6, 1);
    }
}
