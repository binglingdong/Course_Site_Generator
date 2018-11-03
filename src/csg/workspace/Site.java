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
import java.time.Year;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
        ScrollPane sp = csgBuilder.buildScrollPane("", null, CLASS_PANES_BACKGROUND, ENABLED);
        VBox mainPane=  csgBuilder.buildVBox(SITE_PANE, null, CLASS_PANES_BACKGROUND, ENABLED);
        sp.setContent(mainPane);
        
        GridPane banner= csgBuilder.buildGridPane(SITE_BANNER_PANE, mainPane, CLASS_PANES_FOREGROUND, ENABLED);
        HBox page= csgBuilder.buildHBox(SITE_PAGE_PANE, mainPane, CLASS_PANES_FOREGROUND, ENABLED);
        VBox style= csgBuilder.buildVBox(SITE_STYLE_PANE, mainPane, CLASS_PANES_FOREGROUND, ENABLED);
        VBox instructor= csgBuilder.buildVBox(SITE_INSTRUCTOR_PANE, mainPane, CLASS_PANES_FOREGROUND, ENABLED);
        
        banner.setVgap(8);
        banner.setHgap(8);
        banner.setPadding(new Insets(5,5,5,5));
        page.setSpacing(50);
        page.setAlignment(Pos.CENTER_LEFT);
        page.setPadding(new Insets(5,5,5,5));
        style.setSpacing(8);
        style.setPadding(new Insets(5,5,5,5));
                
        createBanner(banner);
        createPage(page);
        createStyle(style);
        createInstructor(instructor);
        mainPane.setSpacing(8);
        mainPane.setPadding(new Insets(8,5,8,5));
        SiteTab.setContent(sp);
        sp.setFitToHeight(true);
        sp.setFitToWidth(true);
    }
    
    private void createBanner(GridPane parentPane){
        AppNodesBuilder csgBuilder = app.getGUIModule().getNodesBuilder();
        csgBuilder.buildLabel(SITE_BANNER_LABEL, parentPane, 0, 0, 1, 1, CLASS_MAJOR_LABELS, ENABLED);
        csgBuilder.buildLabel(SITE_BANNER_COURSE_SUBJECT_LABEL, parentPane, 0, 1, 1, 1, CLASS_MINOR_LABELS, ENABLED);
        ArrayList<String> listForSubject = new ArrayList<>();
        listForSubject.add("CSE");
        listForSubject.add("ISE");
        ComboBox subjectCombo = csgBuilder.buildComboBox(SITE_BANNER_COURSE_SUBJECT_COMBO, listForSubject, "CSE", parentPane, 1, 1, 1, 1, CLASS_INPUT_CONTROL, ENABLED);
        subjectCombo.setEditable(true);
        
        csgBuilder.buildLabel(SITE_BANNER_COURSE_NUMBER_LABEL, parentPane, 2, 1, 1, 1, CLASS_MINOR_LABELS, ENABLED);
        ArrayList<String> listForNumber = new ArrayList<>();
        listForNumber.add("219");
        listForNumber.add("220");
        ComboBox numberCombo = csgBuilder.buildComboBox(SITE_BANNER_COURSE_NUMBER_COMBO, listForNumber, "219", parentPane, 3, 1, 1, 1, CLASS_INPUT_CONTROL, ENABLED);
        numberCombo.setEditable(true);
        
        //Second line of the gui
        csgBuilder.buildLabel(SITE_BANNER_COURSE_SEMESTER_LABEL, parentPane, 0, 2, 1, 1, CLASS_MINOR_LABELS, ENABLED);
        ArrayList<String> listForSemester = new ArrayList<>();
        listForSemester.add("Fall");
        listForSemester.add("Winter");
        listForSemester.add("Spring");
        listForSemester.add("Summer");
        ComboBox semesterCombo = csgBuilder.buildComboBox(SITE_BANNER_COURSE_SEMESTER_COMBO, listForSemester, "Fall", parentPane, 1, 2, 1, 1, CLASS_INPUT_CONTROL, ENABLED);
        semesterCombo.setEditable(true);
        
        csgBuilder.buildLabel(SITE_BANNER_COURSE_YEAR_LABEL, parentPane, 2, 2, 1, 1, CLASS_MINOR_LABELS, ENABLED);
        ArrayList<String> listForYear = new ArrayList<>();
        int currentYear = Year.now().getValue();
        listForYear.add(currentYear+"");
        listForYear.add(currentYear+1+"");
        ComboBox yearCombo = csgBuilder.buildComboBox(SITE_BANNER_COURSE_YEAR_COMBO, listForYear, currentYear+"", parentPane, 3, 2, 1, 1, CLASS_INPUT_CONTROL, ENABLED);
        yearCombo.setEditable(true);
        
        csgBuilder.buildLabel(SITE_BANNER_COURSE_TITLE_LABEL, parentPane, 0, 3, 1, 1, CLASS_MINOR_LABELS, ENABLED);
        TextField titleTextField = csgBuilder.buildTextField(SITE_BANNER_COURSE_TITLE_TEXTFIELD, parentPane, 1, 3, 2, 1, CLASS_INPUT_CONTROL, ENABLED);
        
        csgBuilder.buildLabel(SITE_BANNER_EXPORT_DIR_LABEL, parentPane, 0, 4, 1, 1, CLASS_MINOR_LABELS, ENABLED);
        csgBuilder.buildLabel(SITE_BANNER_EXPORT_DIR_ADDRESS, parentPane, 1, 4, 4, 1, CLASS_LABEL_BACKGROUND, ENABLED);
    }
    
    private void createPage(Pane parentPane){
        AppNodesBuilder csgBuilder = app.getGUIModule().getNodesBuilder();
        csgBuilder.buildLabel(SITE_PAGE_LABEL, parentPane, CLASS_MAJOR_LABELS, ENABLED);
        csgBuilder.buildCheckBox(SITE_PAGE_HOME_CHECKBOX, parentPane, CLASS_LABEL_BACKGROUND, ENABLED);
        csgBuilder.buildCheckBox(SITE_PAGE_SYLLABUS_CHECKBOX, parentPane, CLASS_LABEL_BACKGROUND, ENABLED);
        csgBuilder.buildCheckBox(SITE_PAGE_SCHEDULE_CHECKBOX, parentPane, CLASS_LABEL_BACKGROUND, ENABLED);
        csgBuilder.buildCheckBox(SITE_PAGE_HWS_CHECKBOX, parentPane, CLASS_LABEL_BACKGROUND, ENABLED);
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
        csgBuilder.buildLabel(SITE_STYLE_FONT_COLORS_LABEL, FontsHbox, CLASS_MINOR_LABELS, ENABLED);
        csgBuilder.buildComboBox(label, null, CLASS_TABS, FontsHbox, CLASS_INPUT_CONTROL, ENABLED);
        csgBuilder.buildLabel(SITE_STYLE_FONT_NOTE_LABEL, parentPane, CLASS_MINOR_LABELS, ENABLED);
    }
    
    //GridPane is in the parameter
    private void createInstructor(VBox VPane){ 
        AppNodesBuilder csgBuilder = app.getGUIModule().getNodesBuilder();
        GridPane parentPane= csgBuilder.buildGridPane(SITE_INSTRUCTOR_PANE, VPane, CLASS_PANES_FOREGROUND, ENABLED);
        parentPane.setHgap(8);
        parentPane.setVgap(8);
        parentPane.setPadding(new Insets(5,5,5,5));
        
        csgBuilder.buildLabel(SITE_INSTRUCTOR_LABEL, parentPane, 0, 0, 1, 1, CLASS_MAJOR_LABELS, ENABLED);
        csgBuilder.buildLabel(SITE_INSTRUCTOR_NAME_LABEL, parentPane, 0, 1, 1, 1, CLASS_MINOR_LABELS, ENABLED);
        csgBuilder.buildLabel(SITE_INSTRUCTOR_ROOM_LABEL, parentPane, 3, 1, 1, 1, CLASS_MINOR_LABELS, ENABLED);
        csgBuilder.buildLabel(SITE_INSTRUCTOR_EMAIL_LABEL, parentPane, 0, 2, 1, 1, CLASS_MINOR_LABELS, ENABLED);
        csgBuilder.buildLabel(SITE_INSTRUCTOR_HOMEPAGE_LABEL, parentPane, 3, 2, 1, 1, CLASS_MINOR_LABELS, ENABLED);
        
        TextField nameTextField = csgBuilder.buildTextField(SITE_INSTRUCTOR_NAME_TEXTFIELD, parentPane, 1, 1, 2, 1, CLASS_INPUT_CONTROL, ENABLED);
        TextField roomTextField = csgBuilder.buildTextField(SITE_INSTRUCTOR_ROOM_TEXTFIELD, parentPane, 4, 1, 2, 1, CLASS_INPUT_CONTROL, ENABLED);
        TextField emailTextField = csgBuilder.buildTextField(SITE_INSTRUCTOR_EMAIL_TEXTFIELD, parentPane, 1, 2, 2, 1, CLASS_INPUT_CONTROL, ENABLED);
        TextField homePageTextField = csgBuilder.buildTextField(SITE_INSTRUCTOR_HOMEPAGE_TEXTFIELD, parentPane, 4, 2, 2, 1, CLASS_INPUT_CONTROL, ENABLED);
        TextArea instructorOHTextArea = csgBuilder.buildTextArea(SITE_INSTRUCTOR_OFFICEHOUR_TEXTAREA, null, CLASS_INPUT_CONTROL, ENABLED);
        instructorOHTextArea.setWrapText(true);
        instructorOHTextArea.setMinHeight(300);
        TitledPane instructorOHTitledPane = csgBuilder.buildTitledPane(SITE_INSTRUCTOR_OFFICEHOUR_TITLEDPANE, VPane, CLASS_PANES_FOREGROUND, ENABLED);
        instructorOHTitledPane.setContent(instructorOHTextArea);
        instructorOHTitledPane.setExpanded(false);
        instructorOHTitledPane.setMinWidth(VPane.getWidth());
        
    }
}
