/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CourseSiteGeneratorApp;
import static csg.SitePropertyType.*;
import csg.workspace.controller.SiteController;
import csg.workspace.foolproof.Site_checkBoxFoolproof;
import static csg.workspace.style.Style.*;
import static djf.AppPropertyType.APP_LEFT_FOOTER;
import static djf.AppPropertyType.APP_PATH_IMAGES;
import static djf.AppPropertyType.APP_RIGHT_FOOTER;
import static djf.AppPropertyType.APP_SITE_FAVICON;
import static djf.AppPropertyType.APP_SITE_NAVBAR;
import static djf.AppPropertyType.APP_STIE_STYLE_CSS_PATH;
import djf.modules.AppFoolproofModule;
import djf.modules.AppGUIModule;
import static djf.modules.AppGUIModule.ENABLED;
import djf.ui.AppNodesBuilder;
import djf.ui.dialogs.AppDialogsFacade;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;
import javafx.embed.swing.SwingFXUtils;
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
import javax.imageio.ImageIO;
import properties_manager.PropertiesManager;

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
        instructor.setSpacing(10);
                
        createBanner(banner);
        createPage(page);
        createStyle(style);
        createInstructor(instructor);
        mainPane.setSpacing(8);
        mainPane.setPadding(new Insets(8,5,8,5));
        SiteTab.setContent(sp);
        sp.setFitToHeight(true);
        sp.setFitToWidth(true);
        
         // INIT THE EVENT HANDLERS
        initControllers();
        // SETUP FOOLPROOF DESIGN FOR THIS APP
        initFoolproofDesign();
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
        updateComboBox(subjectCombo);
        
        csgBuilder.buildLabel(SITE_BANNER_COURSE_NUMBER_LABEL, parentPane, 2, 1, 1, 1, CLASS_MINOR_LABELS, ENABLED);
        ArrayList<String> listForNumber = new ArrayList<>();
        listForNumber.add("219");
        listForNumber.add("220");
        ComboBox numberCombo = csgBuilder.buildComboBox(SITE_BANNER_COURSE_NUMBER_COMBO, listForNumber, "219", parentPane, 3, 1, 1, 1, CLASS_INPUT_CONTROL, ENABLED);
        numberCombo.setEditable(true);
        updateComboBox(numberCombo);
        
        //Second line of the gui
        csgBuilder.buildLabel(SITE_BANNER_COURSE_SEMESTER_LABEL, parentPane, 0, 2, 1, 1, CLASS_MINOR_LABELS, ENABLED);
        ArrayList<String> listForSemester = new ArrayList<>();
        listForSemester.add("Fall");
        listForSemester.add("Winter");
        listForSemester.add("Spring");
        listForSemester.add("Summer");
        ComboBox semesterCombo = csgBuilder.buildComboBox(SITE_BANNER_COURSE_SEMESTER_COMBO, listForSemester, "Fall", parentPane, 1, 2, 1, 1, CLASS_INPUT_CONTROL, ENABLED);
        semesterCombo.setEditable(true);
        updateComboBox(semesterCombo);
        
        csgBuilder.buildLabel(SITE_BANNER_COURSE_YEAR_LABEL, parentPane, 2, 2, 1, 1, CLASS_MINOR_LABELS, ENABLED);
        ArrayList<String> listForYear = new ArrayList<>();
        int currentYear = Year.now().getValue();
        listForYear.add(currentYear+"");
        listForYear.add(currentYear+1+"");
        ComboBox yearCombo = csgBuilder.buildComboBox(SITE_BANNER_COURSE_YEAR_COMBO, listForYear, currentYear+"", parentPane, 3, 2, 1, 1, CLASS_INPUT_CONTROL, ENABLED);
        yearCombo.setEditable(true);
        updateComboBox(yearCombo);
        
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
        GridPane gp = csgBuilder.buildGridPane("", parentPane, CLASS_PANES_FOREGROUND, ENABLED);
        gp.setPadding(new Insets(8,8,8,8));
        gp.setVgap(10);
        gp.setHgap(40);
        
        Button FaviconButton = csgBuilder.buildTextButton(SITE_STYLE_FAVICON_BUTTON, gp, 0, 0, 1, 1, CLASS_SITE_STYLE_BUTTONS, ENABLED);
        Button NavbarImageButton = csgBuilder.buildTextButton(SITE_STYLE_NAVBAR_BUTTON, gp, 0, 1, 1, 1, CLASS_SITE_STYLE_BUTTONS, ENABLED);
        Button LeftFooterImageButton = csgBuilder.buildTextButton(SITE_STYLE_LEFT_FOOTER_BUTTON, gp, 0, 2, 1, 1, CLASS_SITE_STYLE_BUTTONS, ENABLED);
        Button RightFooterImageButton = csgBuilder.buildTextButton(SITE_STYLE_RIGHT_FOOTER_BUTTON, gp, 0, 3, 1, 1, CLASS_SITE_STYLE_BUTTONS, ENABLED);
        FaviconButton.prefWidthProperty().bind((parentPane.widthProperty().divide(3)));
        NavbarImageButton.prefWidthProperty().bind((parentPane.widthProperty().divide(3)));
        LeftFooterImageButton.prefWidthProperty().bind((parentPane.widthProperty().divide(3)));
        RightFooterImageButton.prefWidthProperty().bind((parentPane.widthProperty().divide(3)));
       
        Image faviconImage= loadImage(APP_SITE_FAVICON);
        ImageView faviconImageView = new ImageView(faviconImage);
        faviconImageView.setFitHeight(40);
        faviconImageView.setPreserveRatio(true);
        app.getGUIModule().addGUINode(SITE_STYLE_IMAGE_FAVICON_IMAGEVIEW, faviconImageView);
        
        Image NavbarImage= loadImage(APP_SITE_NAVBAR);
        ImageView NavbarImageView = new ImageView(NavbarImage);
        NavbarImageView.setFitHeight(40);
        NavbarImageView.setPreserveRatio(true);
        app.getGUIModule().addGUINode(SITE_STYLE_IMAGE_NAVBAR_IMAGEVIEW, NavbarImageView);
        
        Image LeftFooterImage = loadImage(APP_LEFT_FOOTER);
        ImageView LeftFooterImageView = new ImageView(LeftFooterImage);
        LeftFooterImageView.setFitHeight(40);
        LeftFooterImageView.setPreserveRatio(true);
        app.getGUIModule().addGUINode(SITE_STYLE_IMAGE_LEFT_IMAGEVIEW, LeftFooterImageView);
        
        Image RightFooterImage = loadImage(APP_RIGHT_FOOTER);
        ImageView RightFooterImageView = new ImageView(RightFooterImage);
        RightFooterImageView.setFitHeight(40);
        RightFooterImageView.setPreserveRatio(true);
        app.getGUIModule().addGUINode(SITE_STYLE_IMAGE_RIGHT_IMAGEVIEW, RightFooterImageView);
        
        gp.add(faviconImageView, 1, 0, 1, 1);
        gp.add(NavbarImageView, 1, 1, 1, 1);
        gp.add(LeftFooterImageView, 1, 2, 1, 1);
        gp.add(RightFooterImageView, 1, 3, 1, 1);
  
        
        HBox FontsHbox= csgBuilder.buildHBox("", parentPane, CLASS_PANES_FOREGROUND, ENABLED);
        FontsHbox.setSpacing(30);
        
        csgBuilder.buildLabel(SITE_STYLE_FONT_COLORS_LABEL, FontsHbox, CLASS_MINOR_LABELS, ENABLED);
        ComboBox cssSheets= csgBuilder.buildComboBox(SITE_STYLE_FONT_COLORS_COMBO, null, "", FontsHbox, CLASS_INPUT_CONTROL, ENABLED);
        csgBuilder.buildLabel(SITE_STYLE_FONT_NOTE_LABEL, parentPane, CLASS_MINOR_LABELS, ENABLED);

        PropertiesManager props = PropertiesManager.getPropertiesManager();
        File directory = new File(props.getProperty(APP_STIE_STYLE_CSS_PATH));
        //get all the files from the directory
        File[] fList = directory.listFiles();
        for (File file : fList){
            if (file.isFile()&&file.getName().contains(".css")){
                cssSheets.getItems().add(file.getName());
            }
        }
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
        TitledPane instructorOHTitledPane = csgBuilder.buildTitledPane(SITE_INSTRUCTOR_OFFICEHOUR_TITLEDPANE, VPane, CLASS_TITILEDPANE, ENABLED);
        instructorOHTitledPane.setContent(instructorOHTextArea);
        instructorOHTitledPane.setExpanded(false);
        instructorOHTitledPane.setMinWidth(VPane.getWidth());
        
    }
    
    private void updateComboBox(ComboBox combo){
        combo.getEditor().focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                String newValue = combo.getEditor().getText();
                combo.setValue(newValue);
                if(!combo.getItems().contains(newValue)&& !newValue.equals("")){
                    combo.getItems().add(combo.getEditor().getText());
                }
            }
        });
    }
    
    private Image loadImage(Object imageId){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        Image image= null;
        try {
            String fileName = props.getProperty(imageId);
            String path = props.getProperty(APP_PATH_IMAGES) + "/" + fileName;
            File file = new File(path);
            BufferedImage bufferedImage = ImageIO.read(file);
            image = SwingFXUtils.toFXImage(bufferedImage, null);
            
        }catch (IOException ex) {
            AppDialogsFacade.showMessageDialog(app.getGUIModule().getWindow(),INVALID_IMAGE_TITLE, IO_IMAGE_EXCEPTION_CONTENT);
        }        
        return image;
    }
    

    private void initControllers(){
        SiteController controller = new SiteController((CourseSiteGeneratorApp) app);
        
        //init PAGE CHECKBOXES
        CheckBox page= (CheckBox)app.getGUIModule().getGUINode(SITE_PAGE_HOME_CHECKBOX);
        page.setSelected(true);
        CheckBox syl= (CheckBox)app.getGUIModule().getGUINode(SITE_PAGE_SYLLABUS_CHECKBOX);
        CheckBox sch= (CheckBox)app.getGUIModule().getGUINode(SITE_PAGE_SCHEDULE_CHECKBOX);
        CheckBox hw= (CheckBox)app.getGUIModule().getGUINode(SITE_PAGE_HWS_CHECKBOX);
        page.selectedProperty().addListener(e->{
             app.getFoolproofModule().updateControls(SITE_CHECKBOX_FOOLPROOF_SETTINGS);
        });
        syl.selectedProperty().addListener(e->{
            app.getFoolproofModule().updateControls(SITE_CHECKBOX_FOOLPROOF_SETTINGS);
        });
        sch.selectedProperty().addListener(e->{
            app.getFoolproofModule().updateControls(SITE_CHECKBOX_FOOLPROOF_SETTINGS);
        });
        hw.selectedProperty().addListener(e->{
            app.getFoolproofModule().updateControls(SITE_CHECKBOX_FOOLPROOF_SETTINGS);
        });
        
        Button FaviconButton = (Button)app.getGUIModule().getGUINode(SITE_STYLE_FAVICON_BUTTON);
        Button NavbarImageButton = (Button)app.getGUIModule().getGUINode(SITE_STYLE_NAVBAR_BUTTON);
        Button LeftFooterImageButton = (Button)app.getGUIModule().getGUINode(SITE_STYLE_LEFT_FOOTER_BUTTON);
        Button RightFooterImageButton = (Button)app.getGUIModule().getGUINode(SITE_STYLE_RIGHT_FOOTER_BUTTON);
 
        FaviconButton.setOnAction(e->{controller.styleButtonsClicked(SITE_STYLE_IMAGE_FAVICON_IMAGEVIEW);});
        NavbarImageButton.setOnAction(e->{controller.styleButtonsClicked(SITE_STYLE_IMAGE_NAVBAR_IMAGEVIEW);});
        LeftFooterImageButton.setOnAction(e->{controller.styleButtonsClicked(SITE_STYLE_IMAGE_LEFT_IMAGEVIEW);});
        RightFooterImageButton.setOnAction(e->{controller.styleButtonsClicked(SITE_STYLE_IMAGE_RIGHT_IMAGEVIEW);});
    }
    
    private void initFoolproofDesign() {
        AppFoolproofModule foolproofSettings = app.getFoolproofModule(); //has a hashmap of all the settings
        foolproofSettings.registerModeSettings(SITE_CHECKBOX_FOOLPROOF_SETTINGS,
                new Site_checkBoxFoolproof((CourseSiteGeneratorApp)app));
    }
    
    
    public void reset(){
       AppGUIModule gui= app.getGUIModule();
       ((ComboBox)gui.getGUINode(SITE_BANNER_COURSE_SUBJECT_COMBO)).getSelectionModel().select(null);
       ((ComboBox)gui.getGUINode(SITE_BANNER_COURSE_NUMBER_COMBO)).getSelectionModel().select(null);
       ((ComboBox)gui.getGUINode(SITE_BANNER_COURSE_SEMESTER_COMBO)).getSelectionModel().select(null);
       ((ComboBox)gui.getGUINode(SITE_BANNER_COURSE_YEAR_COMBO)).getSelectionModel().select(null);
       ((TextField)gui.getGUINode(SITE_BANNER_COURSE_TITLE_TEXTFIELD)).clear();
       
       ((CheckBox)gui.getGUINode(SITE_PAGE_HOME_CHECKBOX)).setSelected(true);
       ((CheckBox)gui.getGUINode(SITE_PAGE_SYLLABUS_CHECKBOX)).setSelected(false);
       ((CheckBox)gui.getGUINode(SITE_PAGE_SCHEDULE_CHECKBOX)).setSelected(false);
       ((CheckBox)gui.getGUINode(SITE_PAGE_HWS_CHECKBOX)).setSelected(false);
       
       ((ComboBox)gui.getGUINode(SITE_STYLE_FONT_COLORS_COMBO)).getSelectionModel().select(null);
       ((ImageView)gui.getGUINode(SITE_STYLE_IMAGE_FAVICON_IMAGEVIEW)).setImage(loadImage(APP_SITE_FAVICON));
       ((ImageView)gui.getGUINode(SITE_STYLE_IMAGE_NAVBAR_IMAGEVIEW)).setImage(loadImage(APP_SITE_NAVBAR));
       ((ImageView)gui.getGUINode(SITE_STYLE_IMAGE_LEFT_IMAGEVIEW)).setImage(loadImage(APP_LEFT_FOOTER));
       ((ImageView)gui.getGUINode(SITE_STYLE_IMAGE_RIGHT_IMAGEVIEW)).setImage(loadImage(APP_RIGHT_FOOTER));
       
       ((TextField)gui.getGUINode(SITE_INSTRUCTOR_NAME_TEXTFIELD)).clear();
       ((TextField)gui.getGUINode(SITE_INSTRUCTOR_ROOM_TEXTFIELD)).clear();
       ((TextField)gui.getGUINode(SITE_INSTRUCTOR_EMAIL_TEXTFIELD)).clear();
       ((TextField)gui.getGUINode(SITE_INSTRUCTOR_HOMEPAGE_TEXTFIELD)).clear();
       
       ((TitledPane)gui.getGUINode(SITE_INSTRUCTOR_OFFICEHOUR_TITLEDPANE)).setExpanded(false);
       ((TextArea)gui.getGUINode(SITE_INSTRUCTOR_OFFICEHOUR_TEXTAREA)).clear();
    }
}
