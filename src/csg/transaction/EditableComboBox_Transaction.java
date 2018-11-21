/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transaction;

import csg.CourseSiteGeneratorApp;
import static csg.SitePropertyType.SITE_BANNER_COURSE_NUMBER_COMBO;
import static csg.SitePropertyType.SITE_BANNER_COURSE_SEMESTER_COMBO;
import static csg.SitePropertyType.SITE_BANNER_COURSE_SUBJECT_COMBO;
import static csg.SitePropertyType.SITE_BANNER_COURSE_YEAR_COMBO;
import static csg.SitePropertyType.SITE_BANNER_EXPORT_DIR_ADDRESS;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import jtps.jTPS_Transaction;

/**
 *
 * @author bingling.dong
 */
public class EditableComboBox_Transaction implements jTPS_Transaction{
    Object oldValue;
    Object newValue;
    ComboBox cb;
    Text tx;
    CourseSiteGeneratorApp app;
    String oldTextString;

    public EditableComboBox_Transaction(Object oldValue, Object newValue, ComboBox cb, 
    CourseSiteGeneratorApp app){
        this.oldValue= oldValue;
        this.newValue= newValue;
        this.cb = cb;
        this.app = app;
        tx = (Text)app.getGUIModule().getGUINode(SITE_BANNER_EXPORT_DIR_ADDRESS);
        oldTextString = tx.getText();
    }
    @Override
    public void doTransaction() {
        if(newValue==null||newValue.equals("")){
            cb.getEditor().setText((String)newValue);
            //if it's subject combo
            String textString = tx.getText();
            String newString="";
            int lastUnderscrool = textString.lastIndexOf("_");
            int thirdUnderScroll = textString.lastIndexOf("_", lastUnderscrool-1);
            int secondUnderScroll = textString.lastIndexOf("_", thirdUnderScroll-1);
            int firstUnderScroll =textString.lastIndexOf("_", secondUnderScroll-1);
            int preUnderScroll = textString.indexOf("t\\");
            
            if(cb == (ComboBox)app.getGUIModule().getGUINode(SITE_BANNER_COURSE_SUBJECT_COMBO)){
                newString = textString.substring(0,preUnderScroll+2)+"subject"+textString.substring(firstUnderScroll);
            }
            // or if it's the number combo
            else{
                newString = textString.substring(0,firstUnderScroll+1)+
                        "number"+textString.substring(secondUnderScroll);
            }
            tx.setText(newString);
        }
        else{
            cb.getEditor().setText((String)newValue);
            //if it's subject combo
            String textString = tx.getText();
            String newString="";
            int lastUnderscrool = textString.lastIndexOf("_");
            int thirdUnderScroll = textString.lastIndexOf("_", lastUnderscrool-1);
            int secondUnderScroll = textString.lastIndexOf("_", thirdUnderScroll-1);
            int firstUnderScroll =textString.lastIndexOf("_", secondUnderScroll-1);
            int preUnderScroll = textString.indexOf("t\\");
            
            if(cb == (ComboBox)app.getGUIModule().getGUINode(SITE_BANNER_COURSE_SUBJECT_COMBO)){
                newString = textString.substring(0,preUnderScroll+2)+newValue+textString.substring(firstUnderScroll);
            }
            // or if it's the number combo
            else{
                newString = textString.substring(0,firstUnderScroll+1)+
                        newValue+textString.substring(secondUnderScroll);
            }
            tx.setText(newString);
        }
        
    }

    @Override
    public void undoTransaction() {
        cb.getEditor().setText((String)oldValue);
        tx.setText(oldTextString);
    }
}

