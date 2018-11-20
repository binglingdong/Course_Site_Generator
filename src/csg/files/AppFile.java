/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.files;

import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import java.io.IOException;
import csg.CourseSiteGeneratorApp;
import static csg.SchedulePropertyType.*;
import static csg.SitePropertyType.*;
import static csg.SyllabusPropertyType.*; 
import csg.data.AppData;
import csg.data.Lab;
import csg.data.Lecture;
import csg.data.LocateImages;
import csg.data.Recitation;
import csg.data.ScheduleItem;
import csg.data.TeachingAssistantPrototype;
import csg.data.TimeSlot;
import csg.data.TimeSlot.DayOfWeek;
import static csg.files.AppFileProperties.*;
import csg.workspace.MainWorkspace;
import csg.workspace.OfficeHours;
import djf.modules.AppGUIModule;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import properties_manager.PropertiesManager;

/**
 *
 * @author bingling.dong
 */
public class AppFile implements AppFileComponent {
    CourseSiteGeneratorApp app;
    
    
    public AppFile(CourseSiteGeneratorApp App){
        this.app= App;
    }

    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {
	// CLEAR THE OLD DATA OUT
	AppData dataManager = (AppData)data;
        dataManager.reset();
        app.getWorkspaceComponent().reset();
	// LOAD THE JSON FILE WITH ALL THE DATA
	JsonObject json = loadJSONFile(filePath);
        
        //LOAD ALL THE TABS SEPAREATELY
        loadSite(json.getJsonObject(JSON_SITE_TAB));
        loadSyllabus(json.getJsonObject(JSON_SYLLABUS_TAB));
        loadMT(json.getJsonObject(JSON_MT_TAB), dataManager);
        loadOH(json.getJsonObject(JSON_OH_TAB), dataManager);
        loadSch(json.getJsonObject(JSON_SCHEDULE_TAB), dataManager);
    }
     
    public void loadSite(JsonObject json) throws IOException{
        AppGUIModule gui= app.getGUIModule();
        
        String subject = json.getString(JSON_SITE_SUBJECT);
        String number = json.getString(JSON_SITE_NUMBER);
        String semester = json.getString(JSON_SITE_SEMESTER);
        String year = json.getString(JSON_SITE_YEAR);
        String title = json.getString(JSON_SITE_TITLE);
        
        if(!subject.equals("")&&!subject.equals("null")){
            ((ComboBox)gui.getGUINode(SITE_BANNER_COURSE_SUBJECT_COMBO)).getSelectionModel().select(subject);
        }
        if(!number.equals("")&&!number.equals("null")){
            ((ComboBox)gui.getGUINode(SITE_BANNER_COURSE_NUMBER_COMBO)).getSelectionModel().select(number);
        }
        if(!semester.equals("")&&!semester.equals("null")){
            ((ComboBox)gui.getGUINode(SITE_BANNER_COURSE_SEMESTER_COMBO)).getSelectionModel().select(semester);
        }
        if(!year.equals("")&&!year.equals("null")){
            ((ComboBox)gui.getGUINode(SITE_BANNER_COURSE_YEAR_COMBO)).getSelectionModel().select(year);
        }
        ((TextField)gui.getGUINode(SITE_BANNER_COURSE_TITLE_TEXTFIELD)).setText(title);
        
        JsonArray pagesArray = json.getJsonArray(JSON_SITE_PAGES);
        ((CheckBox)gui.getGUINode(SITE_PAGE_HOME_CHECKBOX)).setSelected(false);
        for(int i=0; i<pagesArray.size(); i++){
            JsonObject page = pagesArray.getJsonObject(i);
            String name = page.getString(JSON_SITE_NAME);
            switch(name){
                case JSON_SITE_PAGES_HOME: ((CheckBox)gui.getGUINode(SITE_PAGE_HOME_CHECKBOX)).setSelected(true);
                    break;
                case JSON_SITE_PAGES_SYLLABUS:((CheckBox)gui.getGUINode(SITE_PAGE_SYLLABUS_CHECKBOX)).setSelected(true);
                    break;
                case JSON_SITE_PAGES_SCHEDULE: ((CheckBox)gui.getGUINode(SITE_PAGE_SCHEDULE_CHECKBOX)).setSelected(true);
                    break;
                case JSON_SITE_PAGES_HWS: ((CheckBox)gui.getGUINode(SITE_PAGE_HWS_CHECKBOX)).setSelected(true); 
                    break;
            }
        }
        
        JsonObject logoImages = json.getJsonObject(JSON_SITE_LOGOS);
        JsonObject favicon = logoImages.getJsonObject(JSON_SITE_FAVICON);
        String faviconPath = favicon.getString("src");
        ((LocateImages)gui.getGUINode(SITE_STYLE_IMAGE_FAVICON_LOCATEIMAGEVIEW)).setImage(loadImage(faviconPath,SITE_STYLE_IMAGE_FAVICON_LOCATEIMAGEVIEW));
        
        JsonObject navbar = logoImages.getJsonObject(JSON_SITE_NAVBAR);
        String navbarPath = navbar.getString("src");
        ((LocateImages)gui.getGUINode(SITE_STYLE_IMAGE_NAVBAR_LOCATEIMAGEVIEW)).setImage(loadImage(navbarPath, SITE_STYLE_IMAGE_NAVBAR_LOCATEIMAGEVIEW));
        
        JsonObject left = logoImages.getJsonObject(JSON_SITE_BOTTOM_LEFT);
        String leftPath = left.getString("src");
        ((LocateImages)gui.getGUINode(SITE_STYLE_IMAGE_LEFT_LOCATEIMAGEVIEW)).setImage(loadImage(leftPath, SITE_STYLE_IMAGE_LEFT_LOCATEIMAGEVIEW));
        
        JsonObject right = logoImages.getJsonObject(JSON_SITE_BOTTOM_RIGHT);
        String rightPath = right.getString("src");
        ((LocateImages)gui.getGUINode(SITE_STYLE_IMAGE_RIGHT_LOCATEIMAGEVIEW)).setImage(loadImage(rightPath, SITE_STYLE_IMAGE_RIGHT_LOCATEIMAGEVIEW));
        
        JsonObject instructorObject = json.getJsonObject(JSON_SITE_INSTRUCTOR);
        String inName = instructorObject.getString(JSON_SITE_NAME);
        String inLink = instructorObject.getString(JSON_SITE_LINK);
        String inEmail = instructorObject.getString(JSON_SITE_EMAIL);
        String inRoom = instructorObject.getString(JSON_SITE_ROOM);
        String inHours = instructorObject.getString(JSON_SITE_HOURS);
        
        ((TextField)gui.getGUINode(SITE_INSTRUCTOR_NAME_TEXTFIELD)).setText(inName);
        ((TextField)gui.getGUINode(SITE_INSTRUCTOR_ROOM_TEXTFIELD)).setText(inRoom);
        ((TextField)gui.getGUINode(SITE_INSTRUCTOR_EMAIL_TEXTFIELD)).setText(inEmail);
        ((TextField)gui.getGUINode(SITE_INSTRUCTOR_HOMEPAGE_TEXTFIELD)).setText(inLink);
        ((TextArea)gui.getGUINode(SITE_INSTRUCTOR_OFFICEHOUR_TEXTAREA)).setText(inHours);
    }
    
    public void loadSyllabus(JsonObject json){
        AppGUIModule gui= app.getGUIModule();
        ((TextArea)gui.getGUINode(SYLLABUS_DES_TEXTAREA)).setText(json.getString(JSON_SYL_DES));
        ((TextArea)gui.getGUINode(SYLLABUS_TOPICS_TEXTAREA)).setText(json.getString(JSON_SYL_TOPICS));
        ((TextArea)gui.getGUINode(SYLLABUS_PREREQ_TEXTAREA)).setText(json.getString(JSON_SYL_PREREQ));
        ((TextArea)gui.getGUINode(SYLLABUS_OUTCOMES_TEXTAREA)).setText(json.getString(JSON_SYL_OUTCOMES));
        ((TextArea)gui.getGUINode(SYLLABUS_TEXTBOOKS_TEXTAREA)).setText(json.getString(JSON_SYL_TB));
        ((TextArea)gui.getGUINode(SYLLABUS_GRADEDCOM_TEXTAREA)).setText(json.getString(JSON_SYL_GC));
        ((TextArea)gui.getGUINode(SYLLABUS_GRADING_NOTE_TEXTAREA)).setText(json.getString(JSON_SYL_GN));
        ((TextArea)gui.getGUINode(SYLLABUS_ACADEMIC_DIS_TEXTAREA)).setText(json.getString(JSON_SYL_ACADIS));
        ((TextArea)gui.getGUINode(SYLLABUS_SPECIAL_ASSISTANCE_TEXTAREA)).setText(json.getString(JSON_SYL_SA));
    }
    
    public void loadMT(JsonObject json, AppData dataManager){
        JsonArray lecturesArray = json.getJsonArray(JSON_MT_LECTURES);
        JsonArray recArray = json.getJsonArray(JSON_MT_RECITATIONS);
        JsonArray labArray = json.getJsonArray(JSON_MT_LABS);
        
        for (int i = 0; i < lecturesArray.size(); i++) {
            JsonObject lectureObject = lecturesArray.getJsonObject(i);
            String section = lectureObject.getString(JSON_MT_SECTION);
            String days= lectureObject.getString(JSON_MT_DAYS);
            String time= lectureObject.getString(JSON_MT_TIME);
            String room = lectureObject.getString(JSON_MT_ROOM);
            
            Lecture newLecture = new Lecture(section, days, time, room);
            dataManager.addLecture(newLecture);
        }
        
        for (int i = 0; i < recArray.size(); i++) {
            JsonObject recObject = recArray.getJsonObject(i);
            String section = recObject.getString(JSON_MT_SECTION);
            String daysAndTimes= recObject.getString(JSON_MT_DAY_TIME);
            String location= recObject.getString(JSON_MT_LOCATION);
            String ta1 = recObject.getString(JSON_MT_TA1);
            String ta2 = recObject.getString(JSON_MT_TA2);
            
            Recitation rec = new Recitation(section, daysAndTimes, location, ta1, ta2);
            dataManager.addRec(rec);
        }
        
        for (int i = 0; i < labArray.size(); i++) {
            JsonObject labObject = labArray.getJsonObject(i);
            String section = labObject.getString(JSON_MT_SECTION);
            String daysAndTimes= labObject.getString(JSON_MT_DAY_TIME);
            String location= labObject.getString(JSON_MT_LOCATION);
            String ta1 = labObject.getString(JSON_MT_TA1);
            String ta2 = labObject.getString(JSON_MT_TA2);
            
            Lab lab = new Lab(section, daysAndTimes, location, ta1, ta2);
            dataManager.addLab(lab);
        }
    }
    
    public void loadOH(JsonObject json, AppData dataManager){
        MainWorkspace workspace= (MainWorkspace)app.getWorkspaceComponent();
        OfficeHours ohws = workspace.getOh();
	// LOAD THE START AND END HOURS
	String startHour = json.getString(JSON_OH_START_HOUR);
        String endHour = json.getString(JSON_OH_END_HOUR);
        dataManager.initHours(startHour, endHour);

        // NOW LOAD ALL THE UNDERGRAD TAs
        ArrayList<TeachingAssistantPrototype> copyTAs= dataManager.getTABackup();
        JsonArray jsonUnderTAArray = json.getJsonArray(JSON_OH_UNDERGRAD_TAS);
        
        for (int i = 0; i < jsonUnderTAArray.size(); i++) {
            JsonObject jsonTA = jsonUnderTAArray.getJsonObject(i);
            String name = jsonTA.getString(JSON_OH_NAME);
            String email= jsonTA.getString(JSON_OH_EMAIL);
            String type= jsonTA.getString(JSON_OH_TYPE);
            TeachingAssistantPrototype ta = new TeachingAssistantPrototype(name,email,0,type);
            dataManager.addTA(ta);
            if (!copyTAs.contains(ta))copyTAs.add(ta);
        }
        
        JsonArray jsonGradTAArray = json.getJsonArray(JSON_OH_GRAD_TAS);
        for (int i = 0; i < jsonGradTAArray.size(); i++) {
            JsonObject jsonTA = jsonGradTAArray.getJsonObject(i);
            String name = jsonTA.getString(JSON_OH_NAME);
            String email= jsonTA.getString(JSON_OH_EMAIL);
            String type= jsonTA.getString(JSON_OH_TYPE);
            TeachingAssistantPrototype ta = new TeachingAssistantPrototype(name,email,0,type);
            dataManager.addTA(ta);
            if (!copyTAs.contains(ta))copyTAs.add(ta);
        }
        
        JsonArray jsonOHArray= json.getJsonArray(JSON_OH_OFFICE_HOURS);
        
        for(int i=0; i<jsonOHArray.size();i++){
            JsonObject jsonOH= jsonOHArray.getJsonObject(i);
            String time= jsonOH.getString(JSON_OH_TIMESLOTS);
            String day= jsonOH.getString(JSON_OH_DAY_OF_WEEK);
            String name= jsonOH.getString(JSON_OH_NAME);
            
            TeachingAssistantPrototype ta= dataManager.getTAWithName(name);
            TimeSlot slots= dataManager.getTimeSlot(time);
            dataManager.addOH(slots, ta, DayOfWeek.valueOf(day));
            
            TimeSlot copyTime= ohws.getTimeSlotInCopyOH(slots);
            ArrayList<TeachingAssistantPrototype> taListForThatDay_Copy= copyTime.getTas().get(DayOfWeek.valueOf(day));
            taListForThatDay_Copy.add(ta);
        }
        
        ohws.updateTaTableForRadio(dataManager.getTeachingAssistants());
        ohws.resetOHToMatchTA(dataManager,dataManager.getOfficeHours());
        ohws.removeOHToMatchTA(dataManager, dataManager.getTeachingAssistants(), dataManager.getOfficeHours());
    
    }
    
    public void loadSch(JsonObject json, AppData dataManager){
        AppGUIModule gui= app.getGUIModule();
        int startingMonth = (int)json.getInt(JSON_STARTING_MONDAY_MONTH);
        if(startingMonth!=0){
            int startingDay = (int)json.getInt(JSON_STARTING_MONDAY_DAY);
            int startingYear = (int)json.getInt(JSON_STARTING_MONDAY_YEAR); 
            LocalDate startingDate = LocalDate.of(startingYear,startingMonth,startingDay);
            ((DatePicker)gui.getGUINode(CALENDAR_BOUNDARIES_STARTING_DATEPICKER)).setValue(startingDate);
        }
        else{
            ((DatePicker)gui.getGUINode(CALENDAR_BOUNDARIES_STARTING_DATEPICKER)).setValue(null);
        }
        
        int endingMonth = (int)json.getInt(JSON_ENDING_FRIDAY_MONTH);
        if(endingMonth!=0){
            int endingDay = (int)json.getInt(JSON_ENDING_FRIDAY_DAY);
            int endingYear = (int)json.getInt(JSON_ENDING_FIRDAY_YEAR);
            LocalDate endingDate = LocalDate.of(endingYear,endingMonth,endingDay);
            ((DatePicker)gui.getGUINode(CALENDAR_BOUNDARIES_ENDING_DATEPICKER)).setValue(endingDate);
        }
        else{
            ((DatePicker)gui.getGUINode(CALENDAR_BOUNDARIES_ENDING_DATEPICKER)).setValue(null);
        }
        
        JsonArray holidaysArray = json.getJsonArray(JSON_HOLIDAYS);
        JsonArray lecturesArray = json.getJsonArray(JSON_LECTURES);
        JsonArray referencesArray = json.getJsonArray(JSON_REFERENCE);
        JsonArray recitationsArray = json.getJsonArray(JSON_RECITATIONS);
        JsonArray hwsArray = json.getJsonArray(JSON_HWS);
        loadScheduleItems(holidaysArray, dataManager.getScheduleItem(), "Holiday");
        loadScheduleItems(lecturesArray, dataManager.getScheduleItem(), "Lecture");
        loadScheduleItems(referencesArray, dataManager.getScheduleItem(), "Reference");
        loadScheduleItems(recitationsArray, dataManager.getScheduleItem(), "Recitation");
        loadScheduleItems(hwsArray, dataManager.getScheduleItem(), "HW");
    }
    // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException{
	InputStream is = new FileInputStream(jsonFilePath);
	JsonReader jsonReader = Json.createReader(is);
        JsonObject json = jsonReader.readObject();
        jsonReader.close();
        is.close();
        return json;
    }

    private Image loadImage(String path, Object locateImageView) throws IOException{
        LocateImages li = (LocateImages)app.getGUIModule().getGUINode(locateImageView);
        File file = new File(path);
        BufferedImage bufferedImage = ImageIO.read(file);
        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        li.setURL(path);
        return image;
    }
    
    private void loadScheduleItems(JsonArray array, ObservableList<ScheduleItem> list, String type){
        for(int i= 0; i<array.size(); i++){
            JsonObject item = array.getJsonObject(i);
            int month = (int)item.getInt(JSON_SCHEDULE_MONTH);
            int day = (int)item.getInt(JSON_SCHEDULE_DAY);
            int year = (int)item.getInt(JSON_SCHEDULE_YEAR);
            String title = item.getString(JSON_SCHEDULE_TITLE);
            String topic = item.getString(JSON_SCHEDULT_TOPIC);
            String link = item.getString(JSON_SCHEDULE_LINK);
            
            LocalDate date= LocalDate.of(year,month,day);
            ScheduleItem schItem = new ScheduleItem(type, date, title, topic,link);
            list.add(schItem);
        }
    }
    
    @Override
    public void saveData(AppDataComponent data, String filePath) throws IOException {
	AppData dataManager = (AppData)data;
        
	// THEN PUT IT ALL TOGETHER IN A JsonObject
	JsonObject dataManagerJSO = Json.createObjectBuilder()
		.add(JSON_SITE_TAB, buildJsonSite(dataManager))
		.add(JSON_SYLLABUS_TAB, buildJsonSyllabus(dataManager))
                .add(JSON_MT_TAB, buildJsonMT(dataManager))
                .add(JSON_OH_TAB, buildJsonOH(dataManager))
                .add(JSON_SCHEDULE_TAB, buildJsonSchedule(dataManager))
		.build();
        
	// AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();
        

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
        
        saveItemsInComboBox();
    }
    
    private JsonObject buildJsonSite(AppData dataManager){
        ComboBox subjectCombo = (ComboBox)app.getGUIModule().getGUINode(SITE_BANNER_COURSE_SUBJECT_COMBO);
        ComboBox numberCombo = (ComboBox)app.getGUIModule().getGUINode(SITE_BANNER_COURSE_NUMBER_COMBO);
        ComboBox semesterCombo = (ComboBox)app.getGUIModule().getGUINode(SITE_BANNER_COURSE_SEMESTER_COMBO);
        ComboBox yearCombo = (ComboBox)app.getGUIModule().getGUINode(SITE_BANNER_COURSE_YEAR_COMBO);
        TextField title = (TextField)app.getGUIModule().getGUINode(SITE_BANNER_COURSE_TITLE_TEXTFIELD);
        CheckBox homeCB = (CheckBox)app.getGUIModule().getGUINode(SITE_PAGE_HOME_CHECKBOX);
        CheckBox sylCB = (CheckBox)app.getGUIModule().getGUINode(SITE_PAGE_SYLLABUS_CHECKBOX);
        CheckBox schCB = (CheckBox)app.getGUIModule().getGUINode(SITE_PAGE_SCHEDULE_CHECKBOX);
        CheckBox hwCB = (CheckBox)app.getGUIModule().getGUINode(SITE_PAGE_HWS_CHECKBOX);
        LocateImages fav = (LocateImages)app.getGUIModule().getGUINode(SITE_STYLE_IMAGE_FAVICON_LOCATEIMAGEVIEW);
        LocateImages nav = (LocateImages)app.getGUIModule().getGUINode(SITE_STYLE_IMAGE_NAVBAR_LOCATEIMAGEVIEW);
        LocateImages left = (LocateImages)app.getGUIModule().getGUINode(SITE_STYLE_IMAGE_LEFT_LOCATEIMAGEVIEW);
        LocateImages right = (LocateImages)app.getGUIModule().getGUINode(SITE_STYLE_IMAGE_RIGHT_LOCATEIMAGEVIEW);
        TextField name = (TextField)app.getGUIModule().getGUINode(SITE_INSTRUCTOR_NAME_TEXTFIELD);
        TextField room = (TextField)app.getGUIModule().getGUINode(SITE_INSTRUCTOR_ROOM_TEXTFIELD);
        TextField email = (TextField)app.getGUIModule().getGUINode(SITE_INSTRUCTOR_EMAIL_TEXTFIELD);
        TextField homepg = (TextField)app.getGUIModule().getGUINode(SITE_INSTRUCTOR_HOMEPAGE_TEXTFIELD);
        TextArea oh = (TextArea)app.getGUIModule().getGUINode(SITE_INSTRUCTOR_OFFICEHOUR_TEXTAREA);
        
        //IMAGEVIEWS
        JsonObject faviconObject = Json.createObjectBuilder().add("src", fav.getURL()).build();
        JsonObject navbarObject = Json.createObjectBuilder().add("src", nav.getURL()).build();
        JsonObject bottomLeftObject = Json.createObjectBuilder().add("src", left.getURL()).build();
        JsonObject bottomRightObject = Json.createObjectBuilder().add("src", right.getURL()).build();
        JsonObject logosObject = Json.createObjectBuilder()
                                .add(JSON_SITE_FAVICON, faviconObject)
                                .add(JSON_SITE_NAVBAR, navbarObject)
                                .add(JSON_SITE_BOTTOM_LEFT, bottomLeftObject)
                                .add(JSON_SITE_BOTTOM_RIGHT, bottomRightObject).build();
        //INSTRUCTOR
        JsonObject instructorObject = Json.createObjectBuilder()
                .add(JSON_SITE_NAME, name.getText())
                .add(JSON_SITE_LINK, homepg.getText())
                .add(JSON_SITE_EMAIL, email.getText())
                .add(JSON_SITE_ROOM, room.getText())
                .add(JSON_SITE_HOURS, oh.getText()).build();
        
        //PAGES
        JsonArrayBuilder pagesArrayBuilder = Json.createArrayBuilder();
        if(homeCB.isSelected()){
            JsonObject object = Json.createObjectBuilder().add(JSON_SITE_NAME, JSON_SITE_PAGES_HOME)
                    .add(JSON_SITE_LINK, "index.html").build();
            pagesArrayBuilder.add(object);
        }
        if(sylCB.isSelected()){
            JsonObject Object = Json.createObjectBuilder().add(JSON_SITE_NAME, JSON_SITE_PAGES_SYLLABUS)
                    .add(JSON_SITE_LINK, "syllabus.html").build();
            pagesArrayBuilder.add(Object);
        }
        if(schCB.isSelected()){
            JsonObject Object = Json.createObjectBuilder().add(JSON_SITE_NAME, JSON_SITE_PAGES_SCHEDULE)
                    .add(JSON_SITE_LINK, "schedule.html").build();
            pagesArrayBuilder.add(Object);
        }
        if(hwCB.isSelected()){
            JsonObject Object = Json.createObjectBuilder().add(JSON_SITE_NAME, JSON_SITE_PAGES_HWS)
                    .add(JSON_SITE_LINK, "hws.html").build();
            pagesArrayBuilder.add(Object);
        }
        JsonArray pagesArray = pagesArrayBuilder.build();
        
        JsonObject SiteTab = Json.createObjectBuilder()
		.add(JSON_SITE_SUBJECT, "" + subjectCombo.getSelectionModel().getSelectedItem())
		.add(JSON_SITE_NUMBER, "" + numberCombo.getSelectionModel().getSelectedItem())
                .add(JSON_SITE_SEMESTER, ""+semesterCombo.getSelectionModel().getSelectedItem())
                .add(JSON_SITE_YEAR, ""+yearCombo.getSelectionModel().getSelectedItem())
                .add(JSON_SITE_TITLE, title.getText())
                .add(JSON_SITE_LOGOS, logosObject)
                .add(JSON_SITE_INSTRUCTOR, instructorObject)
                .add(JSON_SITE_PAGES, pagesArray)
		.build();
        return SiteTab;
    }
    
    private JsonObject buildJsonSyllabus(AppData dataManager){
        AppGUIModule gui = app.getGUIModule();
        JsonObject SyllabusTab = Json.createObjectBuilder()
		.add(JSON_SYL_DES, ((TextArea)gui.getGUINode(SYLLABUS_DES_TEXTAREA)).getText())
		.add(JSON_SYL_TOPICS, ((TextArea)gui.getGUINode(SYLLABUS_TOPICS_TEXTAREA)).getText())
                .add(JSON_SYL_PREREQ, ((TextArea)gui.getGUINode(SYLLABUS_PREREQ_TEXTAREA)).getText())
                .add(JSON_SYL_OUTCOMES, ((TextArea)gui.getGUINode(SYLLABUS_OUTCOMES_TEXTAREA)).getText())
                .add(JSON_SYL_TB, ((TextArea)gui.getGUINode(SYLLABUS_TEXTBOOKS_TEXTAREA)).getText())
                .add(JSON_SYL_GC,((TextArea)gui.getGUINode(SYLLABUS_GRADEDCOM_TEXTAREA)).getText())
                .add(JSON_SYL_GN, ((TextArea)gui.getGUINode(SYLLABUS_GRADING_NOTE_TEXTAREA)).getText())
                .add(JSON_SYL_ACADIS,((TextArea)gui.getGUINode(SYLLABUS_ACADEMIC_DIS_TEXTAREA)).getText())
                .add(JSON_SYL_SA,((TextArea)gui.getGUINode(SYLLABUS_SPECIAL_ASSISTANCE_TEXTAREA)).getText())
		.build();
        return SyllabusTab;
    }
    
    private JsonObject buildJsonMT(AppData dataManager){
        JsonArrayBuilder lecArrayBuilder= Json.createArrayBuilder();
	JsonArrayBuilder labArrayBuilder= Json.createArrayBuilder();
	JsonArrayBuilder recArrayBuilder= Json.createArrayBuilder();
	ObservableList<Lecture> lectures = dataManager.getLectures();
        ObservableList<Recitation> recitations = dataManager.getRecitations();
        ObservableList<Lab> labs = dataManager.getLabs();

        for(Lecture l: lectures){
            JsonObject lecObject = Json.createObjectBuilder()
                    .add(JSON_MT_SECTION, l.getSection())
                    .add(JSON_MT_DAYS, l.getDays())
                    .add(JSON_MT_TIME, l.getTime())
                    .add(JSON_MT_ROOM, l.getRoom()).build();
            lecArrayBuilder.add(lecObject);
        }
        
        for(Lab lab: labs){
            JsonObject labObject = Json.createObjectBuilder()
                    .add(JSON_MT_SECTION, lab.getSection())
                    .add(JSON_MT_DAY_TIME, lab.getDayAndTime())
                    .add(JSON_MT_LOCATION, lab.getRoom())
                    .add(JSON_MT_TA1, lab.getTA1())
                    .add(JSON_MT_TA2, lab.getTA2())
                    .build();
            labArrayBuilder.add(labObject);
        }
        
        for(Recitation r: recitations){
            JsonObject recObject = Json.createObjectBuilder()
                    .add(JSON_MT_SECTION, r.getSection())
                    .add(JSON_MT_DAY_TIME, r.getDayAndTime())
                    .add(JSON_MT_LOCATION, r.getRoom())
                    .add(JSON_MT_TA1, r.getTA1())
                    .add(JSON_MT_TA2, r.getTA2())
                    .build();
            recArrayBuilder.add(recObject);
        }
        
        JsonObject MTTab = Json.createObjectBuilder()
                .add(JSON_MT_LECTURES, lecArrayBuilder.build())
                .add(JSON_MT_LABS, labArrayBuilder.build())
                .add(JSON_MT_RECITATIONS,recArrayBuilder.build())
                .build();
        return MTTab;
    }
    
    private JsonObject buildJsonOH(AppData dataManager){
        JsonArrayBuilder underTaArrayBuilder = Json.createArrayBuilder();//that is the content of the array
        JsonArrayBuilder gradTaArrayBuilder= Json.createArrayBuilder();
	ArrayList<TeachingAssistantPrototype> tasIterator = dataManager.getTABackup();
        for (TeachingAssistantPrototype ta: tasIterator){
            JsonObject taJson = Json.createObjectBuilder()
                .add(JSON_OH_NAME, ta.getName())
                .add(JSON_OH_EMAIL,ta.getEmail())
                .add(JSON_OH_TYPE, ta.getType()).build();
            
            if(ta.getType().equals("Undergraduate")){
                underTaArrayBuilder.add(taJson);
            }
            else gradTaArrayBuilder.add(taJson);
        }
	JsonArray undergradTAsArray = underTaArrayBuilder.build();
        JsonArray gradTAsArray = gradTaArrayBuilder.build();

        //DO THE SAME THING FOR THE OFFICE HOURS
        JsonArrayBuilder OHArrayBuilder= Json.createArrayBuilder();
        Iterator<TimeSlot> OHIterator= dataManager.getOHBackup().iterator();
        while(OHIterator.hasNext()){
            TimeSlot time= OHIterator.next();
            HashMap allTheTAsForTheTime= time.getTas();  //for that time slot
            for(DayOfWeek day: DayOfWeek.values()){
                ArrayList<TeachingAssistantPrototype> listOfTa= (ArrayList)allTheTAsForTheTime.get(day);
                for(TeachingAssistantPrototype ta: listOfTa){
                    
                    JsonObject timeJson= Json.createObjectBuilder().add(JSON_OH_TIMESLOT_START_TIME,time.getStartTime().replace(":", "_"))
                            .add(JSON_OH_DAY_OF_WEEK,day.toString()).add(JSON_OH_NAME, ta.getName()).add(JSON_OH_TYPE, ta.getType())
                            .build();
                    OHArrayBuilder.add(timeJson);
                }
            }  
        }
        JsonArray officeHour= OHArrayBuilder.build();
   	JsonObject OfficeHoursTab = Json.createObjectBuilder()
		.add(JSON_OH_START_HOUR, "" + dataManager.getStartHour())
		.add(JSON_OH_END_HOUR, "" + dataManager.getEndHour())
                .add(JSON_OH_UNDERGRAD_TAS, undergradTAsArray)
                .add(JSON_OH_GRAD_TAS, gradTAsArray)
                .add(JSON_OH_OFFICE_HOURS, officeHour)
		.build();
        return OfficeHoursTab;
    }
    
    private JsonObject buildJsonSchedule(AppData dataManager){
        AppGUIModule gui = app.getGUIModule();
        DatePicker startingDate = (DatePicker)gui.getGUINode(CALENDAR_BOUNDARIES_STARTING_DATEPICKER);
        DatePicker endingDate = (DatePicker)gui.getGUINode(CALENDAR_BOUNDARIES_ENDING_DATEPICKER);
        JsonObjectBuilder ScheduleTabBuilder = Json.createObjectBuilder();
        if(startingDate.getValue()!=null){
            ScheduleTabBuilder.add(JSON_STARTING_MONDAY_MONTH, startingDate.getValue().getMonthValue())
                              .add(JSON_STARTING_MONDAY_DAY, startingDate.getValue().getDayOfMonth())
                              .add(JSON_STARTING_MONDAY_YEAR, startingDate.getValue().getYear());
        }else{
            ScheduleTabBuilder.add(JSON_STARTING_MONDAY_MONTH, 0)
                              .add(JSON_STARTING_MONDAY_DAY, 0);
        }
        if(endingDate.getValue()!=null){
            ScheduleTabBuilder.add(JSON_ENDING_FRIDAY_MONTH, endingDate.getValue().getMonthValue())
                              .add(JSON_ENDING_FRIDAY_DAY, endingDate.getValue().getDayOfMonth())
                              .add(JSON_ENDING_FIRDAY_YEAR, endingDate.getValue().getYear());
        }else{
            ScheduleTabBuilder.add(JSON_ENDING_FRIDAY_MONTH, 0)
                              .add(JSON_ENDING_FRIDAY_DAY, 0);
        }
        
        JsonArrayBuilder holidays = Json.createArrayBuilder();
        JsonArrayBuilder lectures = Json.createArrayBuilder();
        JsonArrayBuilder references = Json.createArrayBuilder();
        JsonArrayBuilder recitations = Json.createArrayBuilder();
        JsonArrayBuilder hws = Json.createArrayBuilder();
        
        ObservableList<ScheduleItem> scheduleItems = dataManager.getScheduleItem();
        for(ScheduleItem item: scheduleItems){
            switch(item.getType()){
                case "Holiday": buildScheduleArray(holidays,item);
                    break;
                case "Lecture":buildScheduleArray(lectures,item);
                    break;
                case "HW":buildScheduleArray(hws,item);
                    break;
                case "Reference":buildScheduleArray(references,item);
                    break;
                case "Recitation":buildScheduleArray(recitations,item);
                    break;
                default:
            }
        }
        
        ScheduleTabBuilder
                .add(JSON_HOLIDAYS, holidays.build())
                .add(JSON_LECTURES, lectures.build())
                .add(JSON_REFERENCE, references.build())
                .add(JSON_RECITATIONS, recitations.build())
                .add(JSON_HWS, hws.build());
        
        JsonObject ScheduleTab= ScheduleTabBuilder.build();
        return ScheduleTab;
    }
    
    public void buildScheduleArray(JsonArrayBuilder thisArray, ScheduleItem item){
        JsonObject newObject = Json.createObjectBuilder()
                .add(JSON_SCHEDULE_MONTH, item.getDate().getMonthValue())
                .add(JSON_SCHEDULE_DAY, item.getDate().getDayOfMonth())
                .add(JSON_SCHEDULE_YEAR, item.getDate().getYear())
                .add(JSON_SCHEDULE_TITLE, item.getTitle())
                .add(JSON_SCHEDULT_TOPIC, item.getType())
                .add(JSON_SCHEDULE_LINK, item.getLink()).build();
        thisArray.add(newObject);
    }
    
    public void saveItemsInComboBox() throws IOException{
        AppGUIModule gui= app.getGUIModule();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String NumberfilePath = props.getProperty(COMBOBOX_ITEMS_PATH_NUM);
	String SubjectfilePath = props.getProperty(COMBOBOX_ITEMS_PATH_SUB);
	
        ComboBox subjectCombo =(ComboBox)gui.getGUINode(SITE_BANNER_COURSE_SUBJECT_COMBO);
        ComboBox courseNumberCombo =(ComboBox)gui.getGUINode(SITE_BANNER_COURSE_NUMBER_COMBO);
        
        File numberItems = new File(NumberfilePath);
        FileWriter numberfw = new FileWriter(numberItems, true);
        PrintWriter numberpw = new PrintWriter(numberfw);
        
        File subjectItems = new File(SubjectfilePath);
        FileWriter subjectfw = new FileWriter(subjectItems, true);
        PrintWriter subjectpw = new PrintWriter(subjectfw);
        
        String newSubject = subjectCombo.getEditor().getText();
        String newNumber = courseNumberCombo.getEditor().getText();
        
        if(!subjectCombo.getItems().contains(newSubject)&& !newSubject.equals("")){
            subjectpw.println(newSubject);
            subjectCombo.getItems().add(newSubject);
        }
        if(!courseNumberCombo.getItems().contains(newNumber)&&!newNumber.equals("")){
            numberpw.println(newNumber);
            courseNumberCombo.getItems().add(newNumber);
        }
        
        numberpw.close();
        subjectpw.close();
    }
    
    
    public void outputItemsInComboBox() throws IOException{
        AppGUIModule gui= app.getGUIModule();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String NumberfilePath = props.getProperty(COMBOBOX_ITEMS_PATH_NUM);
	String SubjectfilePath = props.getProperty(COMBOBOX_ITEMS_PATH_SUB);
	
        ComboBox subjectCombo =(ComboBox)gui.getGUINode(SITE_BANNER_COURSE_SUBJECT_COMBO);
        ComboBox courseNumberCombo =(ComboBox)gui.getGUINode(SITE_BANNER_COURSE_NUMBER_COMBO);
        subjectCombo.getItems().clear();
        courseNumberCombo.getItems().clear();
        
        File numberItems = new File(NumberfilePath);
        
        File subjectItems = new File(SubjectfilePath);
        Scanner numberSC = new Scanner(numberItems);
        while (numberSC.hasNext()) {
            String numberItem = numberSC.nextLine();
            if(!courseNumberCombo.getItems().contains(numberItem)&&!numberItem.equals("")){
                courseNumberCombo.getItems().add(numberItem);
            }        
        }
        Scanner subjectSC = new Scanner(subjectItems);
        while (subjectSC.hasNext()) {
            String subjectItem = subjectSC.nextLine();
            if(!subjectCombo.getItems().contains(subjectItem)&& !subjectItem.equals("")){
                subjectCombo.getItems().add(subjectItem);
            }        
        }
        courseNumberCombo.getEditor().clear();
        subjectCombo.getEditor().clear();
        numberSC.close();
        subjectSC.close();
    }
    
    // IMPORTING/EXPORTING DATA IS USED WHEN WE READ/WRITE DATA IN AN
    // ADDITIONAL FORMAT USEFUL FOR ANOTHER PURPOSE, LIKE ANOTHER APPLICATION

    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void exportData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
    
