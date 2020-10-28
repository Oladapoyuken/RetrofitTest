package com.example.retrofittest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button send_btn, get_btn, next_btn;
    private EditText name, roll_no, roll_in;
    private TextView showData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        send_btn = findViewById(R.id.send_data);
        get_btn = findViewById(R.id.get_data);
        next_btn = findViewById(R.id.next_btn);
        name = findViewById(R.id.name);
        roll_no = findViewById(R.id.roll_no);
        roll_in = findViewById(R.id.getRoll);
        showData = findViewById(R.id.show);

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, uploadImage.class);
                startActivity(intent);
                finish();
            }
        });


        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });

        get_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
    }

    private void getData() {
        String getRoll  =  roll_in.getText().toString().trim();
        if(getRoll.isEmpty()){
            roll_in.setError("roll required");
            roll_in.requestFocus();
            return;
        }

        Call<GetDataModel> call = RetrofitClient.getInstance().getAPI().getData(Integer.parseInt(getRoll));
        call.enqueue(new Callback<GetDataModel>() {
            @Override
            public void onResponse(Call<GetDataModel> call, Response<GetDataModel> response) {
                GetDataModel getDataModel = response.body();
                if(getDataModel != null){
                    showData.setText(getDataModel.getId() + "\n" + getDataModel.getName());
                }
            }

            @Override
            public void onFailure(Call<GetDataModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

    private void sendData() {
        String getName  =  name.getText().toString().trim();
        String getRollNo  =  roll_no.getText().toString().trim();

        if(getName.isEmpty()){
            name.setError("name required");
            name.requestFocus();
            return;
        }
        if(getRollNo.isEmpty()){
            roll_no.setError("name required");
            roll_no.requestFocus();
            return;
        }

        Call<SaveDataModel> call = RetrofitClient.getInstance().getAPI().
                sendData(getName, Integer.parseInt(getRollNo));
        call.enqueue(new Callback<SaveDataModel>() {
            @Override
            public void onResponse(Call<SaveDataModel> call, Response<SaveDataModel> response) {
                SaveDataModel obj = response.body();
                if(obj.isStatus()){

                }
                else{

                }
                Toast.makeText(MainActivity.this, obj.getRemarks(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<SaveDataModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Network Failure", Toast.LENGTH_LONG).show();
            }
        });
    }
}