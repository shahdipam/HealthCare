package com.example.healthcare.ui.home;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.healthcare.Dashboard;
import com.example.healthcare.R;
import com.example.healthcare.classes.User;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class HomeFragment extends Fragment implements SensorEventListener {

    private TextView steps;
    SensorManager sensorManager;
    private DatabaseReference ref;
    static int no_of_steps;
    FirebaseAuth mAuth;
    boolean running = false;
    Button button;
    private EditText age, height, weight;
    float ht, wt, bmi;
    int AGE;
    LineChart chart;
    User user = new User();

    ArrayList<Entry> entries = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        steps = root.findViewById(R.id.steps);
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("users").child(mAuth.getCurrentUser().getUid()).child("stepdata");
        SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("init_steps",0);
        editor.apply();
        editor.commit();
        Log.d(TAG, "onCreateView: "+sp.getInt("init_steps",5));

        entries.add(new Entry(0, 0));


        mAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("users").child(mAuth.getCurrentUser().getUid());


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild("height")){
                }
                else {
                    openDialog(getActivity());
                }

                if (dataSnapshot.hasChild("stepdata")){


                    HashMap<String,String> map = (HashMap) dataSnapshot.child("stepdata").getValue();
                    Iterator i = map.entrySet().iterator();

                    while (i.hasNext()){
                        Map.Entry pair = (Map.Entry) i.next();
                        int inn=18;
                        Long l1  = (Long) pair.getValue();
                        float f1 = l1;
                        entries.add(new Entry(inn, f1));
                        inn++;
                        i.remove();

                    }


//                          entries.add(new Entry(date, steps));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Make Chart
        chart = root.findViewById(R.id.chart);
        LineDataSet dataSet = new LineDataSet(entries, "Customized values");
        dataSet.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        dataSet.setValueTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1

        YAxis yAxisRight = chart.getAxisRight();
        yAxisRight.setEnabled(false);
        yAxisRight.setStartAtZero(true);
        YAxis yAxisLeft = chart.getAxisLeft();
        yAxisLeft.setGranularity(1f);

        LineData data = new LineData(dataSet);
        chart.setData(data);
        chart.animateX(2500);
        //refresh
        chart.invalidate();
        /* Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);

        if (Calendar.getInstance().getTime().equals(Calendar.getInstance().getTime())){
            Log.d(TAG, "onCreateView: RESETTING STEPS");
            no_of_steps = sp.getInt("init_steps",0);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            ref.child(String.valueOf(dateFormat.format(cal.getTime()))).setValue(no_of_steps);
        }
*/


        return root;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (running) {
            no_of_steps = (int) event.values[0];
//            Toast.makeText(getContext(), ""+event.values[0], Toast.LENGTH_SHORT).show();
            steps.setText(String.valueOf((int) event.values[0]));
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, 0);
            ref.child("stepdata").child(String.valueOf(dateFormat.format(cal.getTime()))).setValue(no_of_steps);
            user.setTodays_steps(no_of_steps);

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Override
    public void onResume() {
        super.onResume();
        running = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null){
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        }
        else Toast.makeText(getContext(),"Sensor not found", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        running = false;

    }

    public void openDialog(Context context){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dietplan_dialogbox);
        dialog.setTitle("Enter your details");

        dialog.show();
        button = (Button)dialog.findViewById(R.id.save);
        age = dialog.findViewById(R.id.age);
        weight = dialog.findViewById(R.id.weight);
        height = dialog.findViewById(R.id.height);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ageEmpty() & heightEmpty() & weightEmpty()){
                    Toast.makeText(getContext(), "entered", Toast.LENGTH_SHORT).show();
                    ht = Float.parseFloat(height.getText().toString());
                    wt = Float.parseFloat(weight.getText().toString());
                    AGE = Integer.parseInt(age.getText().toString());
                    ht = ht/100;

                    user.setAge(AGE);
                    user.setHeight(ht);
                    user.setWeight(wt);
                    bmi = user.cal_BMI();


                    ref.child("height").setValue(ht);
                    ref.child("weight").setValue(wt);
                    ref.child("age").setValue(AGE);
                    ref.child("bmi").setValue(bmi);

                    dialog.dismiss();
                }
            }
        });
    }

    private boolean weightEmpty() {
        if (weight.getText().toString().trim().isEmpty()){
            weight.setError("Empty");
            return false;
        }
        else
            return true;
    }

    private boolean heightEmpty() {
        if (height.getText().toString().trim().isEmpty()){
            height.setError("Empty");
            return false;
        }
        else
            return true;
    }

    private boolean ageEmpty() {
        if (age.getText().toString().trim().isEmpty()){
            age.setError("Empty");
            return false;
        }
        else
            return true;
    }

}