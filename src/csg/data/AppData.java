/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import djf.components.AppDataComponent;
import csg.CourseSiteGeneratorApp;
import static csg.MeetingTimePropertyType.*;
import static csg.OfficeHoursPropertyType.*;
import static csg.SchedulePropertyType.*;
import static csg.SitePropertyType.SITE_BANNER_COURSE_NUMBER_COMBO;
import static csg.SitePropertyType.SITE_BANNER_COURSE_SEMESTER_COMBO;
import static csg.SitePropertyType.SITE_BANNER_COURSE_SUBJECT_COMBO;
import csg.data.TimeSlot.DayOfWeek;
import djf.modules.AppGUIModule;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

/**
 *
 * @author bingling.dong
 */
public class AppData implements AppDataComponent{
    CourseSiteGeneratorApp app;
    //MT
    private ObservableList<Lecture> lectures;
    private ObservableList<Recitation> recitations;
    private ObservableList<Lab> labs;
    //OH
    private ObservableList<TeachingAssistantPrototype> teachingAssistants;
    private ObservableList<TimeSlot> officeHours;
    private ArrayList <TeachingAssistantPrototype> TABackup= new ArrayList<>();
    private ArrayList <TimeSlot> OHBackup= new ArrayList<>(); 
    private ArrayList <String> defaultTimeRangeBackup = new ArrayList<>();
    int startHour;
    int endHour;
        // DEFAULT VALUES FOR START AND END HOURS IN MILITARY HOURS
    public static final int MIN_START_HOUR = 9;
    public static final int MAX_END_HOUR = 20;
    //SCHEDULE
    private ObservableList<ScheduleItem> scheduleItem;
   
    
    
    public AppData(CourseSiteGeneratorApp app){
        this.app= app;
        AppGUIModule gui = app.getGUIModule();

        // CONSTRUCT THE LIST OF TAs FOR THE TABLE
        TableView<TeachingAssistantPrototype> taTableView = (TableView)gui.getGUINode(OH_TAS_TABLE_VIEW);
        teachingAssistants = taTableView.getItems();
        TableView<ScheduleItem> scheduleTableView = (TableView)gui.getGUINode(CALENDAR_SCHEDULE_ITEMS_TABLEVIEW);
        scheduleItem = scheduleTableView.getItems();
        TableView<Lecture> lecTableView = (TableView)gui.getGUINode(MT_LECTURE_TABLEVIEW);
        lectures = lecTableView.getItems();
        TableView<Recitation> recTableView = (TableView)gui.getGUINode(MT_REC_TABLEVIEW);
        recitations = recTableView.getItems();
        TableView<Lab> labTableView = (TableView)gui.getGUINode(MT_LAB_TABLEVIEW);
        labs = labTableView.getItems();
        
        // THESE ARE THE DEFAULT OFFICE HOURS
        startHour = MIN_START_HOUR;
        endHour = MAX_END_HOUR;
        
        resetOfficeHours();
        resetOHBackup();
        initTimeRange();
    }
    
    public void resetOfficeHours() {
        //THIS WILL STORE OUR OFFICE HOURS
        AppGUIModule gui = app.getGUIModule();
        TableView<TimeSlot> officeHoursTableView = (TableView)gui.getGUINode(OH_OFFICE_HOURS_TABLE_VIEW);
        officeHours = officeHoursTableView.getItems(); 
        officeHours.clear();
        for (int i = startHour; i <= endHour; i++) {
            TimeSlot timeSlot = new TimeSlot(   this.getTimeString(i, true),
                                                this.getTimeString(i, false));
            officeHours.add(timeSlot);
            
            TimeSlot halfTimeSlot = new TimeSlot(   this.getTimeString(i, false),
                                                    this.getTimeString(i+1, true));
            officeHours.add(halfTimeSlot);
        }
    } 

    public void initTimeRange(){
        AppGUIModule gui = app.getGUIModule();
        defaultTimeRangeBackup.clear();
        ComboBox startTime = (ComboBox)gui.getGUINode(OH_OFFICE_HOURS_START_TIME_COMBO);
        ComboBox endTime= (ComboBox)gui.getGUINode(OH_OFFICE_HOURS_END_TIME_COMBO);
       
       
        for(TimeSlot time:OHBackup){
            String timeString = time.getStartTime();
            if(!startTime.getItems().contains(timeString)){
                startTime.getItems().add(timeString);
            }
            if(!defaultTimeRangeBackup.contains(timeString)){
                defaultTimeRangeBackup.add(timeString);
            }
        }
        int i=0;
        for(TimeSlot time:OHBackup){
            String timeString2 = time.getEndTime();
            if(!endTime.getItems().contains(timeString2)){
                endTime.getItems().add(i,timeString2);
            }
            if(!defaultTimeRangeBackup.contains(timeString2)){
                defaultTimeRangeBackup.add(timeString2);
            }
            i++;
        }
    }
    public void resetOHBackup() {
        for (int i = startHour; i <= endHour; i++) {
            TimeSlot timeSlot = new TimeSlot(   this.getTimeString(i, true),
                                                this.getTimeString(i, false));
            OHBackup.add(timeSlot);
            
            TimeSlot halfTimeSlot = new TimeSlot(   this.getTimeString(i, false),
                                                    this.getTimeString(i+1, true));
            OHBackup.add(halfTimeSlot);
        }
    }
    private String getTimeString(int militaryHour, boolean onHour) {
        String minutesText = "00";
        if (!onHour) {
            minutesText = "30";
        }

        // FIRST THE START AND END CELLS
        int hour = militaryHour;
        if (hour > 12) {
            hour -= 12;
        }
        String cellText = "" + hour + ":" + minutesText;
        if (militaryHour < 12) {
            cellText += "am";
        } else {
            cellText += "pm";
        }
        return cellText;
    }
    
    public void initHours(String startHourText, String endHourText) {
        int initStartHour = Integer.parseInt(startHourText);
        int initEndHour = Integer.parseInt(endHourText);
        if (initStartHour <= initEndHour) {
            // THESE ARE VALID HOURS SO KEEP THEM
            // NOTE THAT THESE VALUES MUST BE PRE-VERIFIED
            startHour = initStartHour;
            endHour = initEndHour;
        }
        resetOfficeHours();
        resetOHBackup();
    }
    
    /**
     * Called each time new work is created or loaded, it resets all data
     * and data structures such that they can be used for new values.
     */
    @Override
    public void reset() {
        startHour = MIN_START_HOUR;
        endHour = MAX_END_HOUR;
        teachingAssistants.clear();
        TABackup.clear();
        lectures.clear();
        recitations.clear();
        labs.clear();
        scheduleItem.clear();
        
        for (int i = 0; i < officeHours.size(); i++) {
            TimeSlot timeSlot = officeHours.get(i);
            timeSlot.reset();
        }
        for (int i = 0; i < officeHours.size(); i++) {
            TimeSlot timeSlot = OHBackup.get(i);
            timeSlot.reset();
        }
    }
    
    // ACCESSOR METHODS

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }
    
    public boolean isTASelected() {
        AppGUIModule gui = app.getGUIModule();
        TableView tasTable = (TableView)gui.getGUINode(OH_TAS_TABLE_VIEW);
        return tasTable.getSelectionModel().getSelectedItem() != null;
    }
    
    public void addTA(TeachingAssistantPrototype ta) {
        if (!this.teachingAssistants.contains(ta))
            this.teachingAssistants.add(ta);
    }
    
    public void removeTA(TeachingAssistantPrototype ta) {
        // REMOVE THE TA FROM THE LIST OF TAs
        this.teachingAssistants.remove(ta);
        
        // AND REMOVE THE TA FROM ALL THEIR OFFICE HOURS
    }
    
    public void addLecture(Lecture lec){
        lectures.add(lec);
    }
    
    public void addRec(Recitation rec){
        recitations.add(rec);
    }
    
    public void addLab(Lab lab){
        labs.add(lab);
    }
    
    public void removeLecture(Lecture lec){
        lectures.remove(lec);
    }
    
    public void removeRec(Recitation rec){
        recitations.remove(rec);
    }
    
    public void removeLab(Lab lab){
        labs.remove(lab);
    }
    
    public void addScheduleItem(ScheduleItem item){
        scheduleItem.add(item);
    }
    
    public void removeScheduleItem(ScheduleItem item){
        scheduleItem.remove(item);
    }
    
    public boolean isDayOfWeekColumn(int columnNumber) {
        return columnNumber >= 2;
    }
    
    public DayOfWeek getColumnDayOfWeek(int columnNumber) {
        return TimeSlot.DayOfWeek.values()[columnNumber-2];
    }

    public Iterator<TeachingAssistantPrototype> teachingAssistantsIterator() {
        return teachingAssistants.iterator();
    }
    
    public Iterator<TimeSlot> officeHoursIterator() {
        return officeHours.iterator();
    }
    
    public TeachingAssistantPrototype getTAWithName(String name) {
        Iterator<TeachingAssistantPrototype> taIterator = teachingAssistants.iterator();
        while (taIterator.hasNext()) {
            TeachingAssistantPrototype ta = taIterator.next();
            if (ta.getName().equals(name))
                return ta;
        }
        return null;
    }

    public TimeSlot getTimeSlot(String startTime) {
        Iterator<TimeSlot> timeSlotsIterator = officeHours.iterator();
        while (timeSlotsIterator.hasNext()) {
            TimeSlot timeSlot = timeSlotsIterator.next();
            String timeSlotStartTime = timeSlot.getStartTime().replace(":", "_");
            if (timeSlotStartTime.equals(startTime))
                return timeSlot;
        }
        return null;
    }
    
    public TimeSlot getStartTimeSlotUsingRegularTime(String startTime) {
        Iterator<TimeSlot> timeSlotsIterator = officeHours.iterator();
        while (timeSlotsIterator.hasNext()) {
            TimeSlot timeSlot = timeSlotsIterator.next();
            if (timeSlot.getStartTime().equals(startTime))
                return timeSlot;
        }
        return null;
    }
    
    public TimeSlot getEndTimeSlotUsingRegularTime(String endTime) {
        Iterator<TimeSlot> timeSlotsIterator = officeHours.iterator();
        while (timeSlotsIterator.hasNext()) {
            TimeSlot timeSlot = timeSlotsIterator.next();
            if (timeSlot.getEndTime().equals(endTime))
                return timeSlot;
        }
        return null;
    }
    
     public void addOH(TimeSlot time,TeachingAssistantPrototype selectedTA, DayOfWeek day){
        ArrayList<TeachingAssistantPrototype> tas= time.getTas().get(day); //tas: the list of TAs of that cell
        tas.add(selectedTA);
        String property= updateTAPropertyForCell(tas);
        setCellProperty(time, property, day);
        
        //updating the timeSlot column
        selectedTA.setSlots(selectedTA.getSlots()+1);
    }
    
    
    public void removeOH(TimeSlot time,TeachingAssistantPrototype selectedTA, DayOfWeek day){
        ArrayList<TeachingAssistantPrototype> tas= time.getTas().get(day); //tas: the list of TAs of that cell
        tas.remove(selectedTA);
        String property= updateTAPropertyForCell(tas);
        setCellProperty(time, property, day);; 
        selectedTA.setSlots(selectedTA.getSlots()-1);
    }
    
    //UPDATE THE PROPERTY STRING SO WE CAN UPDATE THE ACTUALLY PROPERTY OF THE CELLS
    public String updateTAPropertyForCell(ArrayList<TeachingAssistantPrototype> tas){
        String result="";
        for(TeachingAssistantPrototype ta: tas){
            result=result+ta.getName()+"\n";
        }
        return result;
    }
    
    public void setCellProperty(TimeSlot time,String property, DayOfWeek day){
        switch(day){
            case MONDAY: time.setMonday(property);
                break;
            case TUESDAY: time.setTuesday(property);
                break;
            case WEDNESDAY: time.setWednesday(property);
                break;
            case THURSDAY: time.setThursday(property);
                break;
            case FRIDAY: time.setFriday(property);
                break;
            default:                               
        }
    }
      
    /**
    * @return the teachingAssistants
    */
    public ObservableList<TeachingAssistantPrototype> getTeachingAssistants() {
        return teachingAssistants;
    }

    /**
     * @return the officeHours
     */
    public ObservableList<TimeSlot> getOfficeHours() {
        return officeHours;
    }

    /**
     * @return the TABackup
     */
    public ArrayList <TeachingAssistantPrototype> getTABackup() {
        return TABackup;
    }

    /**
     * @return the OHBackup
     */
    public ArrayList <TimeSlot> getOHBackup() {
        return OHBackup;
    }

    /**
     * @return the defaultTimeRangeBackup
     */
    public ArrayList <String> getDefaultTimeRangeBackup() {
        return defaultTimeRangeBackup;
    }

    /**
     * @param OHBackup the OHBackup to set
     */
    public void setOHBackup(ArrayList <TimeSlot> OHBackup) {
        this.OHBackup = OHBackup;
    }

    /**
     * @return the lectures
     */
    public ObservableList<Lecture> getLectures() {
        return lectures;
    }

    /**
     * @return the recitations
     */
    public ObservableList<Recitation> getRecitations() {
        return recitations;
    }

    /**
     * @return the labs
     */
    public ObservableList<Lab> getLabs() {
        return labs;
    }

    /**
     * @return the scheduleItem
     */
    public ObservableList<ScheduleItem> getScheduleItem() {
        return scheduleItem;
    }
    
}
