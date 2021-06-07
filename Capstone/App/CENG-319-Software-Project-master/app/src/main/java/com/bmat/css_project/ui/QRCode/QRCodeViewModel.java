package com.bmat.css_project.ui.QRCode;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class QRCodeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public QRCodeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("QR Code");
    }

    public LiveData<String> getText() {
        return mText;
    }
}