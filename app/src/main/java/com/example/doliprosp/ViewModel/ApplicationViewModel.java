package com.example.doliprosp.ViewModel;

import androidx.lifecycle.ViewModel;
import com.example.doliprosp.treatment.IApplication;
import com.example.doliprosp.treatment.Application;

public class ApplicationViewModel extends ViewModel {
    private static IApplication application;

    public static void createApplication()
    {
        if (application == null) {
            application = new Application();

        }
    }

    public static IApplication getApplication() {
        return application;
    }
}