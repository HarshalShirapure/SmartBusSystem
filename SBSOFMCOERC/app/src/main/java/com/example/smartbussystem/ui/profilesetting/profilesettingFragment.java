package com.example.smartbussystem.ui.profilesetting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.smartbussystem.ChangeUsername;
import com.example.smartbussystem.Changemobileno;
import com.example.smartbussystem.Changepickuppoint;
import com.example.smartbussystem.Help;
import com.example.smartbussystem.R;
import com.example.smartbussystem.UserHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Map;

public class profilesettingFragment extends Fragment {
    Button changename;
    Activity context;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userId;
    public static UserHelper LoggedinUser= new UserHelper();


    TextView name,pemail,tfee,pickup,pinst;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        context=getActivity();

        View view=inflater.inflate(R.layout.fragment_profilesetting,container,false);
     /*   user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Students");
        userId=user.getUid();

        final TextView name=view.findViewById(R.id.name);
        final TextView pemail=view.findViewById(R.id.pemail);
        final TextView tfee=view.findViewById(R.id.tfee);
        final TextView pickuppoint=view.findViewById(R.id.pickup);
        final TextView pinst=view.findViewById(R.id.pinst);

        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserHelper userHelper=snapshot.getValue(UserHelper.class);

                if(userHelper!=null)
                {
                    String fullname=userHelper.getFullname();
                    String email=userHelper.getEmail();
                    String totalfee=userHelper.getTotal_Fees();
                    String pickup=userHelper.getPickup_point();
                    String installment=userHelper.getTotal_instalment();

                    name.setText(fullname);
                    pemail.setText(email);
                    tfee.setText(totalfee);
                    pickuppoint.setText(pickup);
                    pinst.setText(installment);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
      */
         TextView sname=view.findViewById(R.id.name);
         TextView pemail=view.findViewById(R.id.pemail);
         TextView tfee=view.findViewById(R.id.tfee);
         TextView pickuppoint=view.findViewById(R.id.pickup);
         TextView pinst=view.findViewById(R.id.pinst);
        reference= FirebaseDatabase.getInstance().getReference("Students");

            reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    // UserHelper businfo=snapshot.getValue(UserHelper.class);
                    //String picp="pickup: "+businfo.getPickup_point();


                    // temp.setEmail("z@gmail.com");
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    // Name, email address, and profile photo Url
                    String name = user.getDisplayName();
                    Object tempp = snapshot.getChildren();
                    Map<String, List<UserHelper>> value = (Map<String, List<UserHelper>>) snapshot.getValue();

                    UserHelper uh = snapshot.getValue(UserHelper.class);
                    SharedPreferences SP = context.getSharedPreferences("LoginUser", Context.MODE_PRIVATE);

                    for (Map.Entry<String, List<UserHelper>> entry : value.entrySet()) {

                        Map<String, UserHelper> TempValue = (Map<String, UserHelper>) entry.getValue();
                        Object currentemail = TempValue.get("email");
                        String Temp2 = entry.getKey();


                        if (currentemail.toString().equalsIgnoreCase(SP.getString("UserID", ""))) {
                            UserHelper TempObj = new UserHelper();

                            Object email = TempValue.get("email");
                            Object fullname = TempValue.get("fullname");
                            Object mobileno1 = TempValue.get("mobileno1");
                            Object mobileno2 = TempValue.get("mobileno2");
                            Object pickup_point = TempValue.get("pickup_point");
                            String[] pickuppoint_edited = pickup_point.toString().split(",");
                            Object pw = TempValue.get("pw");
                            Object repw = TempValue.get("repw");
                            Object total_Fees = TempValue.get("total_Fees");
                            Object total_instalment = TempValue.get("total_instalment");
                            Object yearbranch = TempValue.get("yearbranch");
                            TempObj.setEmail(email.toString());
                            TempObj.setFullname(fullname.toString());
                            TempObj.setMobileno1(mobileno1.toString());
                            TempObj.setMobileno2(mobileno2.toString());
                            TempObj.setPickup_point(pickup_point.toString());
                            TempObj.setPw(pw.toString());
                            TempObj.setRepw(repw.toString());
                            TempObj.setTotal_Fees(total_Fees.toString());
                            TempObj.setTotal_instalment(total_instalment.toString());
                            TempObj.setYearbranch(yearbranch.toString());

                            LoggedinUser = TempObj;
                            sname.setText("Full Name: " +fullname.toString());
                            pemail.setText("Your Gmail: " +email.toString());
                            tfee.setText("Total_fee: " +total_Fees.toString());
                            pickuppoint.setText("Pickup_point: " +pickuppoint_edited[0]);
                            pinst.setText("Route: \n" +total_instalment.toString());

                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
            });

     /*  changename= view.findViewById(R.id.changenamebtn);
        changename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, ChangeUsername.class));
            }
        });
        changename= view.findViewById(R.id.changenobtn);
        changename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, Changemobileno.class));
            }
        });

        changename= view.findViewById(R.id.changepickuppointbtn);
        changename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, Changepickuppoint.class));
            }
        });


        changename= view.findViewById(R.id.help);
        changename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, Help.class));
            }
        });

      */
        return view;
    }
}