 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author bingling.dong
 */
public class ScheduleItem {
    private StringProperty type;
    private LocalDate date;
    private StringProperty dateString;
    private StringProperty title;
    private StringProperty topic;
    private StringProperty link;
    
    public ScheduleItem(String type, LocalDate date, String title, String topic, String link){
        this.type= new SimpleStringProperty(type);
        this.date= date;
        this.title= new SimpleStringProperty(title);
        this.topic= new SimpleStringProperty(topic);
        this.link= new SimpleStringProperty(link);
        if(date!=null){
            DateTimeFormatter formatters = DateTimeFormatter.ofPattern("MM/dd/uu");
            String dateS = formatters.format(date);
            dateString = new SimpleStringProperty(dateS);
        }
        else{
            dateString = new SimpleStringProperty("");
        }
    }
    
    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }
    
    public StringProperty typeProperty() {
        return type;
    }
    
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
        if(date!=null){
            DateTimeFormatter formatters = DateTimeFormatter.ofPattern("MM/dd/uu");
            String dateS = formatters.format(date);
            this.dateString.set(dateS); 
        }
        else{
            this.dateString.set(""); 
        }
    }
    
    public String getDateString() {
        return dateString.get();
    }

    public void setDateString(String dateString) {
        this.dateString.set(dateString);
    }
    
    public StringProperty dateStringProperty() {
        return dateString;
    }
    
    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }
    
    public StringProperty titleProperty() {
        return title;
    }
    
    public String getTopic() {
        return topic.get();
    }

    public void setTopic(String topic) {
        this.topic.set(topic);
    }
    
    public StringProperty topicProperty() {
        return topic;
    }
    
    public String getLink() {
        return link.get();
    }

    public void setLink(String link) {
        this.link.set(link);
    }
    
    public StringProperty linkProperty() {
        return link;
    }
}
