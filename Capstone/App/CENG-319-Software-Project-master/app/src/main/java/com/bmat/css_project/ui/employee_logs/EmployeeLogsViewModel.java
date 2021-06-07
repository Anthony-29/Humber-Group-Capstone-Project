package com.bmat.css_project.ui.employee_logs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EmployeeLogsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public EmployeeLogsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is EmployeeLogs fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}