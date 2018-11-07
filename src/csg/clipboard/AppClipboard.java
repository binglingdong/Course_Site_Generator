/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.clipboard;

import djf.components.AppClipboardComponent;
import csg.CourseSiteGeneratorApp;
import static csg.OfficeHoursPropertyType.OH_TAS_TABLE_VIEW;
import csg.data.AppData;
import csg.data.TeachingAssistantPrototype;
import csg.transaction.OH_CutTA_Transaction;
import csg.transaction.OH_PasteTA_Copy_Transaction;
import csg.transaction.OH_PasteTA_Cut_Transaction;
import csg.workspace.MainWorkspace;
import csg.workspace.OfficeHours;
import java.util.ArrayList;
import javafx.scene.control.TableView;

/**
 *
 * @author bingling.dong
 */
public class AppClipboard implements AppClipboardComponent {
    CourseSiteGeneratorApp app;
    private ArrayList<TeachingAssistantPrototype> clipboardCutAndCopiedItems;
    private ArrayList<Integer> numberOfPastesForName= new ArrayList<>();                       //assigns the number behind the name pastedName
    private ArrayList<Integer> numberOfPastesForEmail= new ArrayList<>();
    private ArrayList<Boolean> cutOrCopy = new ArrayList<>();         // if true= cut, if false= copy
    
    public AppClipboard(CourseSiteGeneratorApp app){
        this.app= app;
        clipboardCutAndCopiedItems = new ArrayList<>();
    }
   
    @Override
    public void cut() {
        MainWorkspace workspace= (MainWorkspace)app.getWorkspaceComponent();
        OfficeHours ohws = workspace.getOh();
        AppData data= (AppData)app.getDataComponent();
        TableView<TeachingAssistantPrototype> taTable= (TableView<TeachingAssistantPrototype>)(app.getGUIModule().getGUINode(OH_TAS_TABLE_VIEW));
        TeachingAssistantPrototype selectedTA= taTable.getSelectionModel().getSelectedItem();
        clipboardCutAndCopiedItems.add(selectedTA);      //Add the removed TA to the clipboard
        cutOrCopy.add(true);
        numberOfPastesForName.add(1);                           //new TA added to the clipboard, it has not yet been pasted. 
        numberOfPastesForEmail.add(1);                          //if it's been pasted, the first one will be 1. 
        OH_CutTA_Transaction cutTATransaction = new OH_CutTA_Transaction(ohws, selectedTA,data);
        app.processTransaction(cutTATransaction);
    }

    @Override
    public void copy() {
        TableView<TeachingAssistantPrototype> taTable= (TableView<TeachingAssistantPrototype>)(app.getGUIModule().getGUINode(OH_TAS_TABLE_VIEW));
        TeachingAssistantPrototype selectedTA= taTable.getSelectionModel().getSelectedItem();
        TeachingAssistantPrototype TA_clone= new TeachingAssistantPrototype(selectedTA.getName(),selectedTA.getEmail(),0,selectedTA.getType());
        clipboardCutAndCopiedItems.add(TA_clone);      //clone here is to ensure if you change the name of the original ta, the 
                                                       // one to be pasted will stay the same.
        cutOrCopy.add(false);
        numberOfPastesForName.add(1);
        numberOfPastesForEmail.add(1);
    }
    
    @Override
    public void paste() {
        MainWorkspace workspace= (MainWorkspace)app.getWorkspaceComponent();
        OfficeHours ohws = workspace.getOh();
        AppData data= (AppData)app.getDataComponent();
        //get the last ta in the clipboard
        int index= clipboardCutAndCopiedItems.size()-1;
        TeachingAssistantPrototype taInClipboard= clipboardCutAndCopiedItems.get(index);
        TeachingAssistantPrototype TA_clone= new TeachingAssistantPrototype(taInClipboard.getName(),taInClipboard.getEmail(),0,taInClipboard.getType());
        
        boolean whetherCut= cutOrCopy.get(index);
        boolean alreadyExist=false;
        
        if(data.getTABackup().contains(taInClipboard)){      //if you added another ta with the same name or you undo
            alreadyExist=true;
        }
        else{
            for(TeachingAssistantPrototype ta: data.getTABackup()){
                if (ta.getName().equalsIgnoreCase(taInClipboard.getName())||
                        ta.getEmail().equalsIgnoreCase(taInClipboard.getEmail())){
                    alreadyExist=true;
                    break;
                }
            }
        }
        
        if(alreadyExist==false){    // if the ta didnt exist, add it like normal. either with or without oh
            if(whetherCut==false){  //meaning the item was copied
                OH_PasteTA_Copy_Transaction pasteTATransaction= new OH_PasteTA_Copy_Transaction(TA_clone,ohws,data,numberOfPastesForName,numberOfPastesForEmail,index);
                app.processTransaction(pasteTATransaction);
            }
            else{                   //meaning the item was cutted
                OH_PasteTA_Cut_Transaction pasteTACutTransaction = new OH_PasteTA_Cut_Transaction(taInClipboard,ohws,data);
                app.processTransaction(pasteTACutTransaction);
            }
        }
        else{       //if the ta already exists, add it all with no oh paste
            OH_PasteTA_Copy_Transaction pasteTATransaction= new OH_PasteTA_Copy_Transaction(TA_clone,ohws,data,numberOfPastesForName,numberOfPastesForEmail,index);
            app.processTransaction(pasteTATransaction);
        }
        
       
    }    

    @Override
    public boolean hasSomethingToCut() {
        return ((AppData)app.getDataComponent()).isTASelected();
    }

    @Override
    public boolean hasSomethingToCopy() {
        return ((AppData)app.getDataComponent()).isTASelected();
    }

    @Override
    public boolean hasSomethingToPaste() {
        if ((clipboardCutAndCopiedItems != null) && (!clipboardCutAndCopiedItems.isEmpty()))
            {return true;}
        //else if ((clipboardCopiedItems != null) && (!clipboardCopiedItems.isEmpty()))
            //return true;
        else
            return false;
    }
}

