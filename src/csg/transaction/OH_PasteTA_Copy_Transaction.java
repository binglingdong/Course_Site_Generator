/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transaction;

import csg.data.AppData;
import csg.data.TeachingAssistantPrototype;
import csg.workspace.OfficeHours;
import java.util.ArrayList;
import jtps.jTPS_Transaction;

/**
 *
 * @author bingling.dong
 */
public class OH_PasteTA_Copy_Transaction implements jTPS_Transaction{
    TeachingAssistantPrototype TAToBePasted;
    OfficeHours ohws;
    AppData data;
    ArrayList<Integer> numberOfPastesForName;
    ArrayList<Integer> numberOfPastesForEmail;
    TeachingAssistantPrototype newTA;
    int copyIforName;
    int copyJforEmail;
    int index;

    public OH_PasteTA_Copy_Transaction(TeachingAssistantPrototype selectedTA,OfficeHours ohws,AppData data
                               , ArrayList<Integer> numberOfPasteForName, ArrayList<Integer> numberOfPasteForEmail, int index){
        this.TAToBePasted= selectedTA;
        this.ohws= ohws;
        this.data= data;
        this.numberOfPastesForName= numberOfPasteForName;
        this.numberOfPastesForEmail= numberOfPasteForEmail;
        this.index= index;
    }
    
    @Override
    public void doTransaction() {
        String name= TAToBePasted.getName();
        String email= TAToBePasted.getEmail();
        String type= TAToBePasted.getType();
        ArrayList<TeachingAssistantPrototype> copyTAs= data.getTABackup();
        ArrayList<String> allNames= new ArrayList<>();
        ArrayList<String> allEmails= new ArrayList<>();
        
        for(TeachingAssistantPrototype ta: copyTAs){
            allNames.add(ta.getName());
            allEmails.add(ta.getEmail());
        }
        
        copyIforName=numberOfPastesForName.get(index);       //keeps a copy for undo
        int i=numberOfPastesForName.get(index);          //get the number of pastes of the last ta.
        while(allNames.contains(name)){
            name= TAToBePasted.getName()+i;
            i=i+1;
        }
        
        numberOfPastesForName.set(index, i);      //add the number of pastes for the next paste. 

        copyJforEmail=numberOfPastesForEmail.get(numberOfPastesForEmail.size()-1);       //keeps a copy for undo
        int j=numberOfPastesForEmail.get(numberOfPastesForEmail.size()-1);          //get the number of pastes of the last ta.
        while(allEmails.contains(email)){
            String e= TAToBePasted.getEmail();
            String pre= e.substring(0,e.indexOf('@'));
            String post= e.substring(e.indexOf('@'));
            email= pre+j+post;
            j=j+1;
        }
        
        numberOfPastesForEmail.set(index, j);       //add the number of pastes for the next paste. 

        newTA= new TeachingAssistantPrototype(name,email,0,type);
        data.getTABackup().add(newTA);
        ohws.updateTaTableForRadio(data.getTeachingAssistants());  
    }

    @Override
    public void undoTransaction() {
        data.getTABackup().remove(newTA);       //subtract the number of pastes for the next paste. 
        numberOfPastesForName.set(index, copyIforName);
        numberOfPastesForEmail.set(index, copyJforEmail);

        ohws.updateTaTableForRadio(data.getTeachingAssistants());
    }
}

    
