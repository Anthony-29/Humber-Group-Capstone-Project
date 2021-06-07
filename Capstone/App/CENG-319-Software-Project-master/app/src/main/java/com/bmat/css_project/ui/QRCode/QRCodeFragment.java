package com.bmat.css_project.ui.QRCode;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bmat.css_project.R;
import com.google.firebase.auth.FirebaseAuth;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QRCodeFragment extends Fragment {

    private ImageView qrCode;
    private QRCodeViewModel QRCodeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        QRCodeViewModel = ViewModelProviders.of(this).get(QRCodeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_qrrcode, container, false);

        return root;
    }

    //Function below generates a QR code corresponding to current users information
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        qrCode = (ImageView) getView().findViewById(R.id.qrImage); //Creating image view

        //Getting email and company name of current user
        String userID = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        int index = userID.indexOf("-");
        int end = userID.indexOf("=");
        String companyName = userID.substring(index + 1, end);
        String email = userID.substring(0, index);

        //String user info together
        String userInfo = companyName + "-" + email;
        QRGEncoder qrgEncoder = new QRGEncoder(userInfo, null, QRGContents.Type.TEXT, 1500); //Putting user info into generated QR code
        qrgEncoder.setColorWhite(Color.TRANSPARENT);

        Bitmap qrBits = qrgEncoder.getBitmap(); //Getting bitmap image from the encoder
        qrCode.setImageBitmap(qrBits); //Displaying bit map image on imageview
    }
}