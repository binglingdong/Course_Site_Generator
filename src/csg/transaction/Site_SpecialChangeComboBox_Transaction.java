/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transaction;

import csg.CourseSiteGeneratorApp;
import static csg.SitePropertyType.SITE_BANNER_COURSE_SEMESTER_COMBO;
import static csg.SitePropertyType.SITE_BANNER_COURSE_YEAR_COMBO;
import static csg.SitePropertyType.SITE_BANNER_EXPORT_DIR_ADDRESS;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import jtps.jTPS_Transaction;

/**
 *
 * @author bingling.dong
 */
public class Site_SpecialChangeComboBox_Transaction implements jTPS_Transaction{
    Object oldValue;
    Object newValue;
    ComboBox cb;
    Text tx;
    CourseSiteGeneratorApp app;
    String oldTextString;

    public Site_SpecialChangeComboBox_Transaction(Object oldValue, Object newValue, ComboBox cb, 
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
        if(newValue==null){
            cb.getSelectionModel().clearSelection();
        }
        else{
            cb.getSelectionModel().select(newValue);
            //if it's year combo
            String textString = tx.getText();
            String newString="";
            int lastUnderscrool = textString.lastIndexOf("_");
            int thirdUnderScroll = textString.lastIndexOf("_", lastUnderscrool-1);
            int secondUnderScroll = textString.lastIndexOf("_", thirdUnderScroll-1);
            int firstUnderScroll =textString.lastIndexOf("_", secondUnderScroll-1);
            if(cb == (ComboBox)app.getGUIModule().getGUINode(SITE_BANNER_COURSE_YEAR_COMBO)){
                newString = textString.substring(0,thirdUnderScroll+1)+newValue+textString.substring(thirdUnderScroll+5);
            }
            // or if it's the semester combo
            else if (cb == (ComboBox)app.getGUIModule().getGUINode(SITE_BANNER_COURSE_SEMESTER_COMBO)){
                newString = textString.substring(0,secondUnderScroll+1)+
                        newValue+textString.substring(thirdUnderScroll);
            }
            tx.setText(newString);
        }
        
    }

    @Override
    public void undoTransaction() {
        if(oldValue ==null){
            cb.getSelectionModel().clearSelection();
        }else{
            cb.getSelectionModel().select(oldValue);
        }
        tx.setText(oldTextString);
    }
}
