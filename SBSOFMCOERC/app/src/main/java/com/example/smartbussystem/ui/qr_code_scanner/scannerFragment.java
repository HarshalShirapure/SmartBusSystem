package com.example.smartbussystem.ui.qr_code_scanner;

import static androidx.core.content.ContextCompat.getSystemService;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smartbussystem.Loginpage;
import com.example.smartbussystem.R;
import com.example.smartbussystem.UserHelper;
import com.example.smartbussystem.busdetls;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;


public class scannerFragment extends Fragment {
    ImageView Img_qrcode;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
 //       context = getActivity();


        View view = inflater.inflate(R.layout.fragment_scanner, container, false);

         Img_qrcode = (ImageView) view.findViewById(R.id.Img_qrcode);

        Loginpage tempObj=new Loginpage();
        UserHelper temp= tempObj.LoggedinUser;

        GenerateQRCode("Student Name :- "+temp.getFullname().toString()+"\n Pending Fees :- "+temp.getTotal_Fees());

        return view;
    }


    public void GenerateQRCode(String text)
    {
//        qrgEncoder = new QRGEncoder(
//                inputValue, null,
//                QRGContents.Type.TEXT,
//                smallerDimension);
//        try {
//            bitmap = qrgEncoder.encodeAsBitmap();
//            qrImage.setImageBitmap(bitmap);
//        } catch (WriterException e) {
//            Log.v(TAG, e.toString());
//        }


        //initializing MultiFormatWriter for QR code
        MultiFormatWriter mWriter = new MultiFormatWriter();
        try {
            //BitMatrix class to encode entered text and set Width & Height
            BitMatrix mMatrix = mWriter.encode(text, BarcodeFormat.QR_CODE, 400,400);
            BarcodeEncoder mEncoder = new BarcodeEncoder();
            Bitmap mBitmap = mEncoder.createBitmap(mMatrix);//creating bitmap of code
            Img_qrcode.setImageBitmap(mBitmap);//Setting generated QR code to imageView
            // to hide the keyboard
//            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            manager.hideSoftInputFromWindow(etText.getApplicationWindowToken(), 0);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }


}