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
import static csg.SitePropertyType.SITE_BANNER_EXPORT_DIR_ADDRESS;
import csg.data.TimeSlot.DayOfWeek;
import csg.workspace.MainWorkspace;
import djf.modules.AppGUIModule;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

/**
 *
 * @author bingling.dong
 */
public class AppData implements AppDataComponent{
    CourseSiteGeneratorApp app;
    private String expDir;
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
    private ArrayList<ScheduleItem> scheduleItemBackup = new ArrayList<>();
   
    
    
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
        Text expDirectory = (Text) gui.getGUINode(SITE_BANNER_EXPORT_DIR_ADDRESS);
        expDir = expDirectory.getText();
        
        // THESE ARE THE DEFAULT OFFICE HOURS
        startHour = MIN_START_HOUR;
        endHour = MAX_END_HOUR;
        
        resetOfficeHours();
        resetOHBackup();
        resetTimeRange();
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

    public void resetTimeRange(){
        AppGUIModule gui = app.getGUIModule();
        defaultTimeRangeBackup.clear();
        ComboBox startTime = (ComboBox)gui.getGUINode(OH_OFFICE_HOURS_START_TIME_COMBO);
        ComboBox endTime= (ComboBox)gui.getGUINode(OH_OFFICE_HOURS_END_TIME_COMBO);
       
        for(TimeSlot time:OHBackup){
            String timeString = time.getStartTime();
            String timeString2 = time.getEndTime();
            if(!startTime.getItems().contains(timeString)){
                startTime.getItems().add(timeString);
            }
            if(!endTime.getItems().contains(timeString2)){
                endTime.getItems().add(timeString2);
            }
            if(!defaultTimeRangeBackup.contains(timeString)){
                defaultTimeRangeBackup.add(timeString);
            }
            if(!defaultTimeRangeBackup.contains(timeString2)){
                defaultTimeRangeBackup.add(timeString2);
            }
        }
        
        startTime.getSelectionModel().select("9:00am");
        endTime.getSelectionModel().select("9:00pm");
    }
    
    public void resetOHBackup() {
        if(!OHBackup.isEmpty()){
            OHBackup.clear();
        }
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
        scheduleItemBackup.clear();
        
        for (int i = 0; i < officeHours.size(); i++) {
            TimeSlot timeSlot = officeHours.get(i);
            timeSlot.reset();
        }
        for (int i = 0; i < officeHours.size(); i++) {
            TimeSlot timeSlot = OHBackup.get(i);
            timeSlot.reset();
        }
        resetTimeRange();
        MainWorkspace ohws = (MainWorkspace)app.getWorkspaceComponent();
        ohws.getOh().resetOHToMatchTA(this, officeHours);
        ohws.getOh().removeOHToMatchTA(this, teachingAssistants, officeHours);
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
    
    public TimeSlot getTimeSlotFromOHBackup(String startTime) {
        Iterator<TimeSlot> timeSlotsIterator = OHBackup.iterator();
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
    
    public ArrayList<String> exportTimeToOHTime(String from){
        String[] StringTimes = from.split("-");
        for(int i= 0; i<StringTimes.length; i++){         //String[] contains the starting and ending time of the oh
            String s= StringTimes[i];
            if(s.contains(":")){
                StringTimes[i]= s.replace(":", "_");
            }
            s = StringTimes[i];
            if(s.contains("_")){
                int indexForUnderScroll = s.indexOf("_");
                String postTime = s.substring(indexForUnderScroll+1);
                if(!postTime.contains("00")&&!postTime.contains("30")){
                    String digitStr = postTime.substring(0,2);
                    int digit = Integer.parseInt(digitStr);
                    if(digit<30){
                        StringTimes[i] = s.substring(0,indexForUnderScroll+1)+"00"+s.substring(s.length()-2, s.length());
                    }
                    else if(digit>30){
                         StringTimes[i] = s.substring(0,indexForUnderScroll+1)+"30"+s.substring(s.length()-2, s.length());
                    }
                }
            }
        }//Now s contains start and end time in the correct format.
        ArrayList<String> result = new ArrayList<>();
        String startTime = StringTimes[0];
        String endTime = StringTimes[1];
        TimeSlot startTimeSlot = getTimeSlotFromOHBackup(startTime);
        TimeSlot endTimeSlot = getTimeSlotFromOHBackup(endTime);
        int startIndexInOHArray = OHBackup.indexOf(startTimeSlot);
        int endIndexInOHArray = OHBackup.indexOf(endTimeSlot);
        
        for(int i = startIndexInOHArray; i<=endIndexInOHArray; i++){
            String time = OHBackup.get(i).getStartTime();
            result.add(time.replace(":", "_"));
        }
        return result;
    }
    
    public ArrayList<String> exportDayToOHDay(String day){
        ArrayList<String> result = new ArrayList<>();
        String[] times = day.split(" &amp; ");
        for(String s: times){
            String a;
            if(s.contains("days")){
                a = s.substring(0, s.length()-1);
            }
            else{
                a = s;
            }
            result.add(a.toUpperCase());
        }
        return result;
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

    /**
     * @param s the expDir to set
     */
    public void setExpDir(String s) {
        expDir = s;
    }

    @Override
    public String getExportDirectory() {
        return expDir;
    }

    /**
     * @return the scheduleItemBackup
     */
    public ArrayList<ScheduleItem> getScheduleItemBackup() {
        return scheduleItemBackup;
    }

    /**
     * @param scheduleItemBackup the scheduleItemBackup to set
     */
    public void setScheduleItemBackup(ArrayList<ScheduleItem> scheduleItemBackup) {
        this.scheduleItemBackup = scheduleItemBackup;
    }
}
