package com.example.smartbussystem;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

public class Pdfshow extends AppCompatActivity {

    Button idBtnGeneratePDF;
    //Bitmap bitmap,scaledmp;
    // int pagewidth=1200;
    Date date;
    DateFormat dateFormat;

    int pageHeight = 1120;
    int pagewidth = 792;

    // creating a bitmap variable
    // for storing our images
    Bitmap bmp, scaledbmp;

    // constant code for runtime permissions
    private static final int PERMISSION_REQUEST_CODE = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfshow);

        idBtnGeneratePDF = findViewById(R.id.idBtnGeneratePDF);

        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.mceorc);
        scaledbmp = Bitmap.createScaledBitmap(bmp, 140, 140, false);

        if (checkPermission()) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }


        idBtnGeneratePDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatePDF ();
            }
        });
    }
    private void generatePDF () {
            PdfDocument pdfDocument = new PdfDocument();
            Paint paint = new Paint();
            Paint title = new Paint();
            PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pagewidth, pageHeight, 1).create();
            PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);
            Canvas canvas = myPage.getCanvas();
            canvas.drawBitmap(scaledbmp, 56, 40, paint);
            title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            title.setTextSize(15);
            title.setColor(ContextCompat.getColor(this, R.color.purple_200));
            canvas.drawText("A portal for IT professionals.", 209, 100, title);
            canvas.drawText("Geeks for Geeks", 209, 80, title);

            // similarly we are creating another text and in this
            // we are aligning this text to center of our PDF file.
            title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            title.setColor(ContextCompat.getColor(this, R.color.purple_200));
            title.setTextSize(15);

            // below line is used for setting
            // our text to center of PDF.
            title.setTextAlign(Paint.Align.CENTER);
            canvas.drawText("This is sample document which we have created.", 396, 560, title);

            // after adding all attributes to our
            // PDF file we will be finishing our page.
            pdfDocument.finishPage(myPage);

            // below line is used to set the name of
            // our PDF file and its path.
            File file = new File(Environment.getExternalStorageDirectory(), "GFG.pdf");

            try {
                // after creating a file name we will
                // write our PDF file to that location.
                pdfDocument.writeTo(new FileOutputStream(file));

                // below line is to print toast message
                // on completion of PDF generation.
                Toast.makeText(Pdfshow.this, "PDF file generated successfully.", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                // below line is used
                // to handle error
                e.printStackTrace();
            }


            // after storing our pdf to that
            // location we are closing our PDF file.
            pdfDocument.close();
        }



        private boolean checkPermission () {
            // checking of permissions.
            int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
            int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
            return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
        }

        private void requestPermission () {
            // requesting permissions if not provided.
            ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }

        @Override
        public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults){
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode == PERMISSION_REQUEST_CODE) {
                if (grantResults.length > 0) {

                    // after requesting permissions we are showing
                    // users a toast message of permission granted.
                    boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (writeStorage && readStorage) {
                        Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Permission Denied.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        }

    }

