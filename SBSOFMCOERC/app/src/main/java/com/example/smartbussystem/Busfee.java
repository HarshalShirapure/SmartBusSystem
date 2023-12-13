package com.example.smartbussystem;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.smartbussystem.ui.Busdetails.BusdetailFragment;
import com.example.smartbussystem.ui.feestatus.FeestatusFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Busfee extends AppCompatActivity implements PaymentResultListener{

    //EditText mName, mUPI, mAmount, mpincode;
    Button mPay;
    TextView dwnpdf;
    Toolbar ToolbarBusFee;
    EditText name,amount;
    ListView list;
    Bitmap bitmap,scaledmp;
    int pagewidth=1200;
    Date date;
    DateFormat dateFormat;
    private PdfDocument document;
    TextView txt_totalfees;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busfee);

        //   Checkout.preload(getApplicationContext());
        //checkout.setKeyID("<YOUR_KEY_ID>");

        // dwnpdf=findViewById(R.id.dwnpdf);
        list=findViewById(R.id.list);
        //amount=findViewById(R.id.amount);
        bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.mceorc);
        scaledmp=Bitmap.createScaledBitmap(bitmap,1200,518,false);

        mPay = findViewById(R.id.Pay);



//        ToolbarBusFee = findViewById(R.id.ToolbarBusFee);

       /* setSupportActionBar(ToolbarBusFee);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Pay Your Bus Fee");
        }
       */
        mPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makepayment();
            }

        });

    }

    private void makepayment() {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_LkdBbZt4F9vyyw");

        checkout.setImage(R.drawable.mceorc);
        final Activity activity = this;

        try {
            JSONObject options = new JSONObject();

            options.put("name", "MCOERC BUS");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg");
            //  options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");

            options.put("amount", "50000");//pass amount in currency subunits
            options.put("prefill.email", "gaurav.kumar@example.com");
            options.put("prefill.contact","8421304438");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {

        Loginpage CurrentUser= new Loginpage();

        int Fees =Integer.parseInt(CurrentUser.LoggedinUser.getTotal_Fees());
        int pendingfees=Fees-500;
        // txt_totalfees.setText("Total Fees is " + Fees + "/Pending fees is "+ pendingfees);
        FeestatusFragment Previosclassobj= new FeestatusFragment();
        Previosclassobj.tf.setText("Total Fees is " + Fees);
        Previosclassobj.Pendingfees.setText("Total Pending Fees is " + pendingfees);

        // Map<String, Object> childUpdates = (Map<String, Object>) CurrentUser.LoggedinUser;


//        CurrentUser.LoggedinUser.setPending_Fees(""+pendingfees);
        mDatabase=FirebaseDatabase.getInstance().getReference();

        mDatabase.child("Students/"+CurrentUser.LoggedinUser.getFullname()+"/total_Fees").setValue(pendingfees);

        this.finish();
    }


    @Override
    public void onPaymentError(int i, String s) {
        onPaymentError(404,"payment fail");
    }
}