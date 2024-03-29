/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg;

/**
 *
 * @author bingling.dong
 */
public enum OfficeHoursPropertyType {

    /* THESE ARE THE NODES IN OUR APP */
    // FOR SIMPLE OK/CANCEL DIALOG BOXES
    OH_OK_PROMPT,
    OH_CANCEL_PROMPT,

    
    
    OH_SCROLLPANE,
    //ADD TA's pane
    OH_TA_REMOVE_BUTTON,
    OH_TAS_LABEL,
    OH_TA_RADIO_TYPE_ALL,
    OH_TA_RADIO_TYPE_UNDERGRADUATE,
    OH_TA_RADIO_TYPE_GRADUATE,
    
    OH_TAS_TABLE_VIEW,
    OH_TA_NAME_TABLE_COLUMN,
    OH_TA_EMAIL_TABLE_COLUMN,
    OH_TA_SLOTS_TABLE_COLUMN,
    OH_TA_TYPE_TABLE_COLUMN,

    OH_TA_ADD_NAME_TEXT_FIELD,
    OH_TA_ADD_EMAIL_TEXT_FIELD,
    OH_TA_ADD_BUTTON,

    //ADD OH's PANE
    OH_OFFICE_HOURS_LABEL,
    OH_OFFICE_HOURS_START_TIME_LABEL,
    OH_OFFICE_HOURS_START_TIME_COMBO,
    OH_OFFICE_HOURS_END_TIME_LABEL,
    OH_OFFICE_HOURS_END_TIME_COMBO,
                
    OH_OFFICE_HOURS_TABLE_VIEW,
    OH_START_TIME_TABLE_COLUMN,
    OH_END_TIME_TABLE_COLUMN,
    OH_MONDAY_TABLE_COLUMN,
    OH_TUESDAY_TABLE_COLUMN,
    OH_WEDNESDAY_TABLE_COLUMN,
    OH_THURSDAY_TABLE_COLUMN,
    OH_FRIDAY_TABLE_COLUMN,

    OH_DAYS_OF_WEEK,

    //EDITING THE TA DIALOG
    OH_EDIT_TITLE,
    OH_EDIT_HEADER,
    OH_EDIT_NAME,
    OH_EDIT_EMAIL,
    OH_EDIT_TYPE,
    OH_EDIT_TYPE_UNDER,
    OH_EDIT_TYPE_GRA,
    OH_EDIT_OK,
    OH_EDIT_CANCEL,
    
    // THESE ARE FOR ERROR MESSAGES PARTICULAR TO THE APP
    INVALID_COMMAND_TITLE,
    DIDNT_CHOOSE_TA_INVALID_CLICK_CONTENT,
    
    //FOLLPROOF
    OH_FOOLPROOF_SETTINGS,
    EDIT_TA_FOOLPROOF_SETTINGS,
    OH_TIME_RANGE_FOOLPROOF_SETTINGS,
}

