/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg;

import djf.AppTemplate;
import djf.components.AppClipboardComponent;
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import djf.components.AppWorkspaceComponent;
import java.util.Locale;
import static javafx.application.Application.launch;
import csg.data.AppData;
import csg.files.AppFile;
import csg.workspace.MainWorkspace;

/**
 *
 * @author bingling.dong
 */
public class CourseSiteGeneratorApp extends AppTemplate {
    
    /**
     * This is where program execution begins. Since this is a JavaFX app it
     * will simply call launch, which gets JavaFX rolling, resulting in sending
     * the properly initialized Stage (i.e. window) to the start method inherited
     * from AppTemplate, defined in the Desktop Java Framework.
     * 
     * @param args Command-line arguments, there are no such settings used
     * by this application.
     */
    public static void main(String[] args) {
	Locale.setDefault(Locale.US);
	launch(args);
    }

    @Override
    public AppClipboardComponent buildClipboardComponent(AppTemplate app) {
        return null;
    }

    @Override
    public AppDataComponent buildDataComponent(AppTemplate app) {
        return new AppData(this);
    }

    @Override
    public AppFileComponent buildFileComponent() {
        return new AppFile(this);
    }

    @Override
    public AppWorkspaceComponent buildWorkspaceComponent(AppTemplate app) {
        return new MainWorkspace(this);        
    }
}

