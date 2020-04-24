package com.azhar.couplecat.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.azhar.couplecat.Adapter.CACKucingAdapter;
import com.azhar.couplecat.Model.MyCat;
import com.azhar.couplecat.Model.ResponseJadwal;
import com.azhar.couplecat.Model.ResponseMyCat;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.azhar.couplecat.R;
import com.azhar.couplecat.Rest.CombineApi;
import com.azhar.couplecat.Rest.CoupleCatInterface;
import com.azhar.couplecat.Utils.SessionManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddScheduleActivity extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {
    @BindView(R.id.actvNamaBetina)
    AutoCompleteTextView actvBetina;
    @BindView(R.id.actvNamaJantan)
    AutoCompleteTextView actvJantan;
    @BindView(R.id.etLokasi)
    EditText etLokasi;
    @BindView(R.id.tvTangal)
    TextView tvTanggal;
    SessionManager sessionManager;
    HashMap<String, String> map;
    CoupleCatInterface coupleCatInterface;

    DatePickerDialog datePickerDialog;
    //    TimePickerDialog timePickerDialog ;
    int Year, Month, Day, Hour, Minute;
    String jenis,idpenerima,hari,kucing,date;
    Calendar calendar;
    List<MyCat> kucingArrayList = new ArrayList<>();
    String bagi_hari [];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);
        ButterKnife.bind(this);
        jenis = getIntent().getStringExtra("jk");
        idpenerima = getIntent().getStringExtra("pengguna_id");
        hari = getIntent().getStringExtra("hari");
        bagi_hari = hari.split(",");
        kucing = getIntent().getStringExtra("kucing");
        sessionManager = new SessionManager(AddScheduleActivity.this);
        map = sessionManager.getDetailsLoggin();
        coupleCatInterface = CombineApi.getApiService();
        loadActvCat();
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR) ;
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        Hour = calendar.get(Calendar.HOUR_OF_DAY);
        Minute = calendar.get(Calendar.MINUTE);
    }

    private void loadActvCat() {

        if (jenis.equals("betina")){
            actvBetina.setText(kucing);
            actvBetina.setFocusable(false);
        }
        else{
            actvJantan.setText(kucing);
            actvJantan.setFocusable(false);

        }

        Call<ResponseMyCat> getMyCat = coupleCatInterface.myListCat(map.get(sessionManager.KEY_PENGGUNA_ID));
        getMyCat.enqueue(new Callback<ResponseMyCat>() {
            @Override
            public void onResponse(Call<ResponseMyCat> call, Response<ResponseMyCat> response) {
                kucingArrayList = response.body().getData();
//
//                for(Object o : kucingArrayList){
//                    Log.d("rebi  ck", "onResponse: "+response.body().getMyCat().get(a).getNama());
//                    a++;
//                }
                CACKucingAdapter adapter = new CACKucingAdapter(AddScheduleActivity.this,
                        R.layout.activity_add_schedule,
                        kucingArrayList);
                if (jenis.equals("betina")){
                actvJantan.setAdapter(adapter);
                }
                else{
                    actvBetina.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<ResponseMyCat> call, Throwable t) {

            }
        });
        
    }

    @OnClick(R.id.ivCalendar)
    protected void ivCalendar(View view) {
        datePickerDialog = DatePickerDialog.newInstance(AddScheduleActivity.this, Year, Month, Day);
        datePickerDialog.setThemeDark(false);
        datePickerDialog.showYearPickerFirst(false);
        datePickerDialog.setTitle("Pilih Jadwal");
        Calendar min_date_c = Calendar.getInstance();
        datePickerDialog.setMinDate(min_date_c);
        Calendar max_date_c = Calendar.getInstance();
        max_date_c.set(Calendar.YEAR, Year+5);
        datePickerDialog.setMaxDate(max_date_c);
        HashMap<Integer,Boolean> availableday = new HashMap<>();
        availableday.put(1,false);//minggu
        availableday.put(2,false);//senin
        availableday.put(3,false);//selasa
        availableday.put(4,false);//rabu
        availableday.put(5,false);//kamis
        availableday.put(6,false);//jumat
        availableday.put(7,false);//sabtu
        for(int i = 0;i<bagi_hari.length;i++){
            switch (bagi_hari[i].toLowerCase()){
                case "minggu": availableday.put(1,true);break;
                case "senin": availableday.put(2,true);break;
                case "selasa": availableday.put(3,true);break;
                case "rabu": availableday.put(4,true);break;
                case "kamis": availableday.put(5,true);break;
                case "jumat": availableday.put(6,true);break;
                case "sabtu": availableday.put(7,true);break;

            }
        }
        for (Calendar loopdate = min_date_c; min_date_c.before(max_date_c); min_date_c.add(Calendar.DATE, 1), loopdate = min_date_c) {
            int dayOfWeek = loopdate.get(Calendar.DAY_OF_WEEK);
            for (int i = 0 ;i<availableday.size();i++){
                if (dayOfWeek == (i+1) && availableday.get(i+1) == false) {
                    Calendar[] disabledDays =  new Calendar[1];
                    disabledDays[0] = loopdate;
                    datePickerDialog.setDisabledDays(disabledDays);
                }                
            }
            
        }
        datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialogInterface) {

                Toast.makeText(AddScheduleActivity.this, "Datepicker Canceled", Toast.LENGTH_SHORT).show();
            }
        });

        datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
    }



    @OnClick(R.id.btnJadwal)
    protected void btnJadwal(View view) {
        String namakucing = null;
        if (jenis.equals("betina")){
            namakucing = actvJantan.getText().toString();
        }
        else{
            namakucing = actvBetina.getText().toString();
        }
        Log.d("kumbang", "btnJadwal: "+namakucing);
        Call<ResponseJadwal> responseJadwalCall = coupleCatInterface.addJadwal(
                map.get(sessionManager.KEY_PENGGUNA_ID),
                idpenerima,
                getIntent().getStringExtra("kucing_id"),
                namakucing,
                date,
                etLokasi.getText().toString()
                );
        responseJadwalCall.enqueue(new Callback<ResponseJadwal>() {
            @Override
            public void onResponse(Call<ResponseJadwal> call, Response<ResponseJadwal> response) {
                if (response.body().getStatus() == 200){
                    Intent i = new Intent(AddScheduleActivity.this,MyScheduleActivity.class);
                    startActivity(i);
                    finish();
                }
                else{
                    Toast.makeText(AddScheduleActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseJadwal> call, Throwable t) {

            }
        });
    }


    @Override
    public void onDateSet(DatePickerDialog view, int yearnow, int monthOfYear, int dayOfMonth) {
        date = yearnow+"-"+(monthOfYear+1)+"-"+dayOfMonth;

        String bulan = null;
        switch (String.valueOf(monthOfYear+1)){
            case "1" : bulan = "Januari";break;
            case "2" : bulan = "Februari";break;
            case "3" : bulan = "Maret";break;
            case "4" : bulan = "April";break;
            case "5": bulan = "Mei";break;
            case "6": bulan = "Juni";break;
            case "7": bulan = "Juli";break;
            case "8": bulan = "Agustus";break;
            case "9": bulan = "September";break;
            case "10" : bulan = "Oktober";break;
            case "11" : bulan = "November";break;
            case "12" : bulan = "Desember";break;
        }
        Log.d("kambing", "onDateSet: "+(Month+1));
        Toast.makeText(AddScheduleActivity.this, date, Toast.LENGTH_LONG).show();
        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
        Date datee = new Date (yearnow, monthOfYear, dayOfMonth-1);
        String dayOfWeek = simpledateformat.format(datee);
        String hari = null;
        switch (dayOfWeek.toLowerCase()){
            case "sunday": hari = "Minggu";break;
            case "monday": hari = "Senin";break;
            case "tuesday": hari = "Selasa";break;
            case "wednesday": hari = "Rabu";break;
            case "thursday": hari = "Kamis";break;
            case "friday": hari = "Jum'at";break;
            case "saturday": hari = "Sabtu";break;
        }

        tvTanggal.setText(hari+", "+dayOfMonth+" "+bulan+" "+yearnow);

    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
