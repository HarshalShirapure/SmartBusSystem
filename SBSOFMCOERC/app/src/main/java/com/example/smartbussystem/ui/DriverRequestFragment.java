package com.example.smartbussystem.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.smartbussystem.Bus1Details;
import com.example.smartbussystem.Loginpage;
import com.example.smartbussystem.R;
import com.example.smartbussystem.UserHelper;
import com.example.smartbussystem.driver;
import com.example.smartbussystem.driver_problem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DriverRequestFragment extends Fragment {
    //TextView dissuelist;
    ListView dissue;
    private static DatabaseReference databaseReference_Drivertable;
    private ArrayList<driver> List_Problem;
    private cadapter cd;

    public DriverRequestFragment() {
        // Required empty public constructor
    }
    private TextView myTextView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_driver_request, container, false);
        //myTextView = (TextView)view.findViewById(
        //      R.id.fragmentTextView);
        databaseReference_Drivertable= FirebaseDatabase.getInstance().getReference("driver");

        dissue=view.findViewById(R.id.dissue);

        //new Loginpage().GetDriverdetailsTable();
        String[] data={"Hello","GoodMorning"};
        driver obj_Problem=new driver();
        obj_Problem.setdriver_Problem("");
         List_Problem=new ArrayList<>();
        GetDriverdetailsTable();
      //  List_Problem.add();
        //List_Problem.


        return view;
    }
    public  void GetDriverdetailsTable()
    {
        databaseReference_Drivertable.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    // UserHelper businfo=snapshot.getValue(UserHelper.class);
                    //String picp="pickup: "+businfo.getPickup_point();


                    // temp.setEmail("z@gmail.com");
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    // Name, email address, and profile photo Url
                   // String name = user.getDisplayName();
                   // Object tempp = snapshot.getChildren();
                    Map<String, List<driver>> value = (Map<String, List<driver>>) snapshot.getValue();

                    UserHelper uh = snapshot.getValue(UserHelper.class);
                    // SharedPreferences SP= getApplicationContext().getSharedPreferences("LoginUser",Context.MODE_PRIVATE);

                    for(Map.Entry<String,List<driver>> entry :value.entrySet())
                    {

                        Map<String , driver> TempValue = (Map<String, driver>) entry.getValue();
                      //  Object currentroot = LoggedinUser.getPickup_point();
                        String Temp2=entry.getKey();


                        //if(currentroot.toString().contains(""+Temp2))
                        {
                            driver TempObj=new driver();
                            Object email=TempValue.get("EmailID");
                            Object Location_Current=TempValue.get("Location_Current");
                            Object busid=TempValue.get("busid");
                            Object name_=TempValue.get("name");
                            Object DriverProblem=TempValue.get("driver_Problem");

                            TempObj.setEmailID(email.toString());
                            TempObj.setLocation_Current(Location_Current.toString());
                            TempObj.setBusid(busid.toString());
                            TempObj.setName(name_.toString());
                            TempObj.setdriver_Problem(DriverProblem.toString());
//                            Object dname = TempValue.get("dname");
//                            Object dno = TempValue.get("dno");
//                            Object root = TempValue.get("root");
//                            TempObj.setDname(dname.toString());
//                            TempObj.setDno(dno.toString());
//                            TempObj.setRoot(root.toString());
if(DriverProblem.toString()==null || !(DriverProblem.toString().equalsIgnoreCase(""))) {
    List_Problem.add(TempObj);
}
                            //CurrentDriverdetailwithLocation =TempObj;

                            // DriverDetails.setText(pickup_point.toString());
                        }
                    }

                    cd=new cadapter(getActivity(),List_Problem);
                    dissue.setAdapter(cd);

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



             /*   arrayList.clear();
                String startpicup=snapshot.child(uid).child("pickup_point").getValue(String.class);

                arrayList.add(startpicup);
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),R.layout.fragment_busdetail,arrayList);
                bdfrag.setAdapter(adapter);

              */
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                String err = error.toString();
            }
        });

    }

    public class cadapter extends ArrayAdapter<driver>
    {
        private final Context context;
        private final ArrayList<driver> data;
        String [] temp;

        public cadapter(@NonNull Context context,ArrayList<driver> data) {
            super(context,R.layout.customlist,data);

            this.context=context;
            this.data=data;
        }

        public View getView(int i,View convertview,ViewGroup viewGroup)
        {

                LayoutInflater inflater=getLayoutInflater();
                View rowView=inflater.inflate(R.layout.customlist, null,true);
           // View v1=getLayoutInflater().inflate(R.layout.customlist,viewGroup,true);
            TextView BusNo=rowView.findViewById(R.id.busno);
            TextView DriverIssue=rowView.findViewById(R.id.DriverIssue);
            TextView DriverName=rowView.findViewById(R.id.DriverName);
            BusNo.setText(data.get(i).getBusid().toString());
            DriverIssue.setText(data.get(i).getdriver_Problem().toString());
            DriverName.setText(data.get(i).getName());
             //   name.setText(this.data.indexOf(i));
            //goodm.setText(data[i]);


            // TextView gm=v1.findViewById(R.id.goodm);

           return rowView;
        }
    }
}