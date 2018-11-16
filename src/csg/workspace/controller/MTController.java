/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace.controller;

import csg.CourseSiteGeneratorApp;
import static csg.MeetingTimePropertyType.MT_LAB_TABLEVIEW;
import static csg.MeetingTimePropertyType.MT_LECTURE_TABLEVIEW;
import static csg.MeetingTimePropertyType.MT_REC_TABLEVIEW;
import csg.data.AppData;
import csg.data.Lab;
import csg.data.Lecture;
import csg.data.Recitation;
import csg.transaction.MT_AddLab_Transaction;
import csg.transaction.MT_AddLectures_Transaction;
import csg.transaction.MT_AddRec_Transaction;
import csg.transaction.MT_RemoveLab_Transaction;
import csg.transaction.MT_RemoveLectures_Transaction;
import csg.transaction.MT_RemoveRec_Transaction;
import djf.modules.AppGUIModule;
import javafx.scene.control.TableView;

/**
 *
 * @author bingling.dong
 */
public class MTController {
    CourseSiteGeneratorApp app;
    public MTController(CourseSiteGeneratorApp initApp) {
        app = initApp;
    }
    public void processAddLecture(){
        Lecture newLec = new Lecture("?", "?", "?", "?");
        AppData data=(AppData)app.getDataComponent();
        MT_AddLectures_Transaction tran = new MT_AddLectures_Transaction(data,newLec);
        app.processTransaction(tran);
    }
    public void processAddLab(){
        Lab newLab = new Lab("?", "?", "?", "?","?");
        AppData data=(AppData)app.getDataComponent();
        MT_AddLab_Transaction tran = new MT_AddLab_Transaction(data, newLab);
        app.processTransaction(tran);
    }
    public void processAddRec(){
        Recitation newRec = new Recitation("?", "?", "?", "?","?");
        AppData data=(AppData)app.getDataComponent();
        MT_AddRec_Transaction tran = new MT_AddRec_Transaction(data, newRec);
        app.processTransaction(tran);
        
    }
    public void processRemoveLecture(){
        AppGUIModule gui = app.getGUIModule();
        AppData data=(AppData)app.getDataComponent();
        TableView<Lecture> lectureTable = (TableView)(gui.getGUINode(MT_LECTURE_TABLEVIEW));
        Lecture selected = lectureTable.getSelectionModel().getSelectedItem();
        if(selected!= null){
            MT_RemoveLectures_Transaction tran = new MT_RemoveLectures_Transaction(data,selected);
            app.processTransaction(tran);
        }
    }
    public void processRemoveLab(){
        AppGUIModule gui = app.getGUIModule();
        AppData data=(AppData)app.getDataComponent();
        TableView<Lab> labTable = (TableView)(gui.getGUINode(MT_LAB_TABLEVIEW));
        Lab selected = labTable.getSelectionModel().getSelectedItem();
        if(selected!= null){
            MT_RemoveLab_Transaction tran = new MT_RemoveLab_Transaction(data,selected);
            app.processTransaction(tran);
        }
        
    }
    public void processRemoveRec(){
        AppGUIModule gui = app.getGUIModule();
        AppData data=(AppData)app.getDataComponent();
        TableView<Recitation> recitationTable = (TableView)(gui.getGUINode(MT_REC_TABLEVIEW));
        Recitation selected = recitationTable.getSelectionModel().getSelectedItem();
        if(selected!= null){
            MT_RemoveRec_Transaction tran = new MT_RemoveRec_Transaction(data,selected);
            app.processTransaction(tran);
        }
    }
}
