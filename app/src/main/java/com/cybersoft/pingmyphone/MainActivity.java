package com.cybersoft.pingmyphone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "Unmute Mobile Tag";
    public static final String Name = "_code_real_new";
    Button sendBtn;
    Button newCodeBtn;
    Button viewCodeBtn;
    EditText txtphoneNo;
    EditText txtCode;
    ImageView iv_info;
    String phoneNo;
    String msgCode;
    //AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        SecretCodeReciever secretCodeReciever = new SecretCodeReciever();
        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(secretCodeReciever, mIntentFilter);
/*
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this, "You have already granted this permission!",
                    Toast.LENGTH_SHORT).show();
        } else {
            requestStoragePermission("Manifest.permission.READ_SMS");
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this, "You have already granted this permission!",
                    Toast.LENGTH_SHORT).show();
        } else {
            requestStoragePermission("Manifest.permission.SEND_SMS");
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this, "You have already granted this permission!",
                    Toast.LENGTH_SHORT).show();
        } else {
            requestStoragePermission("Manifest.permission.RECEIVE_SMS");
        }   */


        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, 10 );
        }
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 11 );
        }
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, 12 );
        }

        //MobileAds.initialize(getApplicationContext(),"ca-app-pub-2201114375597651~3299786343");
        //Sample Ad      //MobileAds.initialize(getApplicationContext(),"ca-app-pub-3940256099942544~3347511713");

        sendBtn = (Button) findViewById(R.id.btn_unmute);
        newCodeBtn = (Button) findViewById(R.id.btn_new_code);
        viewCodeBtn = (Button) findViewById(R.id.btn_view_code);
        txtphoneNo = (EditText) findViewById(R.id.input_phoneNo);
        txtCode = (EditText) findViewById(R.id.input_code);
        iv_info = (ImageView) findViewById(R.id.info_iv);
        //  adView = (AdView) findViewById(R.id.adView);
        iv_info.setOnClickListener(this);
        sendBtn.setOnClickListener(this);
        newCodeBtn.setOnClickListener(this);
        viewCodeBtn.setOnClickListener(this);
        txtphoneNo.setRawInputType(InputType.TYPE_CLASS_NUMBER);

        //AdRequest adRequest = new AdRequest.Builder().build();
        //adView.loadAd(adRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_unmute:{

                Log.d(TAG, "Send Code");

                if (!validate()) {
                    onFail();
                    return;
                }

                sendBtn.setEnabled(false);
                onSuccess();
            }
            break;
            case R.id.btn_new_code:{

                Log.d(TAG, "Create New Code");

                Intent i = new Intent(getApplicationContext(), SetNewCodeActivity.class);
                startActivity(i);
            }
            break;
            case R.id.btn_view_code:{

                Log.d(TAG, "View My Code");
                SaveCodeClass newcode = new SaveCodeClass();
                String current_code = newcode.getCode(Name,getApplicationContext());
                Toast.makeText(getApplicationContext(), "Your Code is '"+current_code+"'",
                        Toast.LENGTH_LONG).show();
            }
            break;
            case R.id.info_iv:{
                Log.d(TAG, "View Instructions");
                Intent i = new Intent(getApplicationContext(), InstructionDisplayActivity.class);
                startActivity(i);
            }
            break;
        }
    }

    public void onSuccess(){
        sendBtn.setEnabled(true);
        String phoneNo = txtphoneNo.getText().toString();
        String msgCode = txtCode.getText().toString();

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msgCode, null, null);
            Toast.makeText(getApplicationContext(), "Code Sent Through SMS!",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "Code Sending faild, please try again!",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        finish();
    }

    public void onFail(){
        Toast.makeText(getBaseContext(), "Code Sending failed!", Toast.LENGTH_LONG).show();
        sendBtn.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;
        String phoneNo = txtphoneNo.getText().toString();
        String msgCode = txtCode.getText().toString();

        if (phoneNo.isEmpty() || phoneNo.length() < 0) {
            txtphoneNo.setError("Please Enter Valid Phone No");
            valid = false;
        } else {
            txtphoneNo.setError(null);
        }
        if (msgCode.isEmpty() || msgCode.length() < 0) {
            txtCode.setError("Please Enter Valid Code");
            valid = false;
        } else {
            txtCode.setError(null);
        }

        return valid;
    }

/*
    private void requestStoragePermission(final String str_permission) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,str_permission)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of Read SMS Code")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[] {str_permission}, 10);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {str_permission}, 10);
        }
    }  */

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == 11) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == 12) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}