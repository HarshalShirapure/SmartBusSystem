package com.example.smartbussystem.ui.feestatus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smartbussystem.Bus1Details;
import com.example.smartbussystem.Busfee;
import com.example.smartbussystem.Loginpage;
import com.example.smartbussystem.R;
import com.example.smartbussystem.UserHelper;
import com.example.smartbussystem.ui.Busdetails.BusdetailFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FeestatusFragment extends Fragment implements PaymentResultListener {

    Button goback;
    Activity context;

    ListView l1;
    DatabaseReference reference;
    private Button Btnpay;
    public static TextView tf;
    public static TextView Pendingfees;
    private DatabaseReference mDatabase;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        context=getActivity();
        View view= inflater.inflate(R.layout.fragment_feestatus, container, false);
        //    l1=view.findViewById(R.id.l1);
        goback=view.findViewById(R.id.goback);
        Btnpay=view.findViewById(R.id.goback);
        tf = view.findViewById((R.id.tf));
        Pendingfees=view.findViewById(R.id.Pendingfees);
        // txt_totalfees = view.findViewById(R.id.txt_totalfees);
//        goback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(context,Busfee.class));
//            }
//        });
        Loginpage CurrentUser= new Loginpage();
        int Fees =Integer.parseInt(CurrentUser.LoggedinUser.getTotal_Fees());
        int pendingfees=Fees-500;
        // txt_totalfees.setText("Total Fees is " + Fees + "/Pending fees is "+ pendingfees);
        FeestatusFragment Previosclassobj= new FeestatusFragment();
        Previosclassobj.tf.setText("Total Fees is " + Fees);
        Previosclassobj.Pendingfees.setText("Total Pending Fees is " + Fees);

        Btnpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  makepayment();
                startActivity(new Intent(context,Busfee.class));
            }
        });
        //FirebaseDatabase firebaseDatabase;
        return view;
    }

    private void makepayment() {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_LkdBbZt4F9vyyw");

        checkout.setImage(R.drawable.mceorc);


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

            checkout.open(context, options);

        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }


    }


    @Override
    public void onPaymentSuccess(String s) {
        Loginpage CurrentUser= new Loginpage();

        int Fees =Integer.parseInt(CurrentUser.LoggedinUser.getTotal_Fees());
        int pendingfees=Fees-500;
        tf.setText("Total Fees is " + Fees );
        Pendingfees.setText("Pending Fees is "+pendingfees);

        // CurrentUser.CurrentUserMapValueTemp.get("").set

// ...
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        String key = mDatabase.child("Students").push().getKey();
//        UserHelper post = new UserHelper();


        Map<String, Object> childUpdates = (Map<String, Object>) CurrentUser.LoggedinUser;
       // CurrentUser.LoggedinUser.setPending_Fees(""+pendingfees);

        mDatabase.updateChildren(childUpdates);


//        String key = mDatabase.child("posts").push().getKey();
//        UserHelper post = new Post(userId, username, title, body);
//        Map<String, Object> postValues = post.toMap();
//
//        Map<String, Object> childUpdates = new HashMap<>();
//        childUpdates.put("/posts/" + key, postValues);
//        childUpdates.put("/user-posts/" + userId + "/" + key, postValues);



    }

    @Override
    public void onPaymentError(int i, String s) {

    }
}