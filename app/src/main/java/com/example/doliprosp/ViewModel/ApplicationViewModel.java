package com.example.doliprosp.ViewModel;

import androidx.lifecycle.ViewModel;
import com.example.doliprosp.treatment.IApplication;
import com.example.doliprosp.treatment.Application;

public class ApplicationViewModel extends ViewModel {
    private IApplication application;

    public ApplicationViewModel() {
        if (application == null) {
            application = new Application();

        }
    }

    public IApplication getApplication() {
        return application;
    }
}