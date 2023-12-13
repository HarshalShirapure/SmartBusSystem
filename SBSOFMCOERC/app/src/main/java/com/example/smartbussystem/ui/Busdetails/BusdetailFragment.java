package com.example.smartbussystem.ui.Busdetails;

import static android.content.Intent.getIntent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smartbussystem.Bus1Details;
import com.example.smartbussystem.Busstop;
import com.example.smartbussystem.Loginpage;
import com.example.smartbussystem.R;
import com.example.smartbussystem.UserHelper;
import com.example.smartbussystem.busdetls;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.content.SharedPreferences;

import org.json.JSONObject;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BusdetailFragment extends Fragment {
    TextView busstop,pickup;
    Activity context;
    DatabaseReference databaseReference;
    FirebaseUser user;
    String uid;
    ListView list;
    TextView test;

   // ArrayList list;

    UserHelper u;

    public static UserHelper LoggedinUser= new UserHelper();
    public static busdetls lu=new busdetls();
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        context=getActivity();
        View view= inflater.inflate(R.layout.fragment_busdetail, container, false);

        TextView DriverDetails=(TextView)view.findViewById(R.id.test);
        TextView DriverNo=(TextView)view.findViewById(R.id.dno);
        TextView Root=(TextView)view.findViewById(R.id.root);
        Loginpage tempObj=new Loginpage();
        busdetls temp= tempObj.DriverDetails;
        DriverDetails.setText("Driver Name :- "+temp.getDname());
        DriverNo.setText("Contact No. :- "+temp.getDno());
        Root.setText("Bus Route :-"+temp.getRoot());

        user= FirebaseAuth.getInstance().getCurrentUser();
        user.getUid();
        ArrayList<String> arrayList=new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference("Students");

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDb = mDatabase.getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userKey = user.getUid();



      /*  databaseReference.addValueEventListener(new ValueEventListener() {
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
                        Map<String,List<UserHelper>> value = (Map<String, List<UserHelper>>) snapshot.getValue();

                        UserHelper uh = snapshot.getValue(UserHelper.class);
                        SharedPreferences SP= context.getSharedPreferences("LoginUser",Context.MODE_PRIVATE);

                        for(Map.Entry<String,List<UserHelper>> entry :value.entrySet())
                        {

                            Map<String , UserHelper> TempValue = (Map<String, UserHelper>) entry.getValue();
                            Object currentemail = TempValue.get("email");
                            String Temp2=entry.getKey();


                            if(currentemail.toString().equalsIgnoreCase(SP.getString("UserID","")))
                            {
                                UserHelper TempObj=new UserHelper();

                                Object email = TempValue.get("email");
                                Object fullname = TempValue.get("fullname");
                                Object mobileno1 = TempValue.get("mobileno1");
                                Object mobileno2 = TempValue.get("mobileno2");
                                Object pickup_point = TempValue.get("pickup_point");
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

                                LoggedinUser=TempObj;

                               // DriverDetails.setText(pickup_point.toString());
                            }
                        }


                  //  FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//----------------------------------------------------------------------------------------------------------------



//                        for(int i=0;i<value.size();i++)
//                        {
//                            UserHelper current_obj = value.get(value.keySet());
//                            if(current_obj.getEmail()=="z@gmail.com")
//                            {
//                                LoggedinUser=current_obj;
//
//                            }
//
//                        }



             *//*   arrayList.clear();
                String startpicup=snapshot.child(uid).child("pickup_point").getValue(String.class);

                arrayList.add(startpicup);
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),R.layout.fragment_busdetail,arrayList);
                bdfrag.setAdapter(adapter);

              *//*
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                String err = error.toString();
            }
        });
*/
        return view;
    }
}
