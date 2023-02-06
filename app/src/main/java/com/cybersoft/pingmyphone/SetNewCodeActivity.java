package com.cybersoft.pingmyphone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SetNewCodeActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String MyPREFERENCES = "_codesec" ;
    public static final String Name = "_code_real_new";
    private static final String TAG = "Save New Code";
    Button setCodeBtn;
    EditText txtNewCode;
    //AdView adView;
    //SharedPreferences sharedpreferences;
    String _newCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_code_screen);
        //MobileAds.initialize(getApplicationContext(),"ca-app-pub-2201114375597651~3299786343");
        //Sample Ad  //  MobileAds.initialize(getApplicationContext(),"ca-app-pub-3940256099942544~3347511713");

        setCodeBtn = (Button) findViewById(R.id.btn_setNewCode);
        txtNewCode = (EditText) findViewById(R.id.input_newCode);
        //adView = (AdView) findViewById(R.id.adView);
        setCodeBtn.setOnClickListener(this);

        //AdRequest adRequest = new AdRequest.Builder().build();
        // adView.loadAd(adRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_setNewCode:{

                Log.d(TAG, "New Code");

                if (!validate()) {
                    onFail();
                    return;
                }

                setCodeBtn.setEnabled(false);
                onSuccess();
            }
            break;
        }
    }

    private void onSuccess() {
        SaveCodeClass savecode = new SaveCodeClass();
        String newcode = txtNewCode.getText().toString();
        /*sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Name, newcode);
        editor.commit();*/
        savecode.setCode(Name,newcode,getApplicationContext());
        Toast.makeText(getApplicationContext(),"Your New Code is Saved Successfully!",Toast.LENGTH_LONG).show();
    }

    public void onFail(){
        Toast.makeText(getBaseContext(), "New Code Not Set!", Toast.LENGTH_LONG).show();
        setCodeBtn.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;
        String newcode = txtNewCode.getText().toString();

        if (newcode.isEmpty() || newcode.length() < 0) {
            txtNewCode.setError("Please Enter Valid Code");
            valid = false;
        } else {
            txtNewCode.setError(null);
        }

        return valid;
    }
}