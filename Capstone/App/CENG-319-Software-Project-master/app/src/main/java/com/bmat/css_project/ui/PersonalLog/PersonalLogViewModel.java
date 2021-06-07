package com.bmat.css_project.ui.PersonalLog;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PersonalLogViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PersonalLogViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Personal Log");
    }

    public LiveData<String> getText() {
        return mText;
    }
}