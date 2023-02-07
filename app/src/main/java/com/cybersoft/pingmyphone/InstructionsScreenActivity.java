package com.cybersoft.pingmyphone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class InstructionsScreenActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "Start App";
    public static final String Name = "_code_real_new";
    Button startApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions_screen);
        startApp = (Button) findViewById(R.id.btn_start_app);
        startApp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_app: {

                Log.d(TAG, "Start App");
                SaveCodeClass savecode = new SaveCodeClass();
                savecode.setCode(Name, "pingme", getApplicationContext());
                Toast.makeText(getApplicationContext(), "Your Default Code is 'pingme'", Toast.LENGTH_LONG).show();
                Intent mainIntent = new Intent(InstructionsScreenActivity.this,MainActivity.class);
                InstructionsScreenActivity.this.startActivity(mainIntent);
                InstructionsScreenActivity.this.finish();
            }
            break;
        }
    }
}