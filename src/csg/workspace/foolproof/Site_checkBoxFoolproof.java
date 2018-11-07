/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace.foolproof;

import csg.CourseSiteGeneratorApp;
import static csg.SitePropertyType.SITE_PAGE_HOME_CHECKBOX;
import static csg.SitePropertyType.SITE_PAGE_HWS_CHECKBOX;
import static csg.SitePropertyType.SITE_PAGE_SCHEDULE_CHECKBOX;
import static csg.SitePropertyType.SITE_PAGE_SYLLABUS_CHECKBOX;
import djf.ui.foolproof.FoolproofDesign;
import javafx.scene.control.CheckBox;

/**
 *
 * @author bingling.dong
 */
public class Site_checkBoxFoolproof implements FoolproofDesign{
    CourseSiteGeneratorApp app;
    
    public Site_checkBoxFoolproof(CourseSiteGeneratorApp app){
        this.app= app;
    }

    @Override
    public void updateControls() {
        int counter=0;
        CheckBox page= (CheckBox)app.getGUIModule().getGUINode(SITE_PAGE_HOME_CHECKBOX);
        CheckBox syl= (CheckBox)app.getGUIModule().getGUINode(SITE_PAGE_SYLLABUS_CHECKBOX);
        CheckBox sch= (CheckBox)app.getGUIModule().getGUINode(SITE_PAGE_SCHEDULE_CHECKBOX);
        CheckBox hw= (CheckBox)app.getGUIModule().getGUINode(SITE_PAGE_HWS_CHECKBOX);
        if(page.isSelected())counter++;
        if(syl.isSelected())counter++;
        if(sch.isSelected())counter++;
        if(hw.isSelected())counter++;
        if(counter==1){
            if(page.isSelected())page.setDisable(true);
            if(syl.isSelected())syl.setDisable(true);
            if(sch.isSelected())sch.setDisable(true);
            if(hw.isSelected())hw.setDisable(true);
        }
        else{
            page.setDisable(false);
            syl.setDisable(false);
            sch.setDisable(false);
            hw.setDisable(false);
        }
    }
}
