package com.bmat.css_project.ui.Override;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OverrideViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public OverrideViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Override fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}