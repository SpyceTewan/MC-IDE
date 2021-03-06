package at.tewan.mcide;

import at.tewan.mcide.app.LauncherApplication;
import at.tewan.mcide.app.MainApplication;
import at.tewan.mcide.item.Items;
import at.tewan.mcide.mcfunction.command.Commands;
import at.tewan.mcide.settings.GlobalSettings;
import at.tewan.mcide.util.StartParameters;
import javafx.application.Application;

/**
 * Alles beginnt in der Main. Hier werden statische seperate Codesegmente initialisiert.
 *
 * */
public class Main {
    public static void main(String[] args) {
        StartParameters.resolveStartArgs(args);

        GlobalSettings.loadConfig();
        Items.init();

        //Application.launch(MainApplication.class, args);
        Application.launch(LauncherApplication.class, args);
    }
}