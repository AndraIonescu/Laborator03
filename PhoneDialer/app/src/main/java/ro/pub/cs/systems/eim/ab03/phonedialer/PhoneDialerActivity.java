package ro.pub.cs.systems.eim.ab03.phonedialer;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PhoneDialerActivity extends AppCompatActivity {

    private EditText editText = null;
    private NumberButtonListener numberButtonListener = new NumberButtonListener();
    private DeleteButtonListener deleteButtonListener = new DeleteButtonListener();
    private EndButtonListener endButtonListener = new EndButtonListener();
    private CallButtonListener callButtonListener = new CallButtonListener();

    final static public int[] ids = {
            R.id.button0,
            R.id.button1,
            R.id.button2,
            R.id.button3,
            R.id.button4,
            R.id.button5,
            R.id.button6,
            R.id.button7,
            R.id.button8,
            R.id.button9,
            R.id.button_hashtag,
            R.id.button_star
    };

    private class NumberButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            editText.setText(editText.getText().toString() + ((Button) view).getText().toString());
        }
    }

    private class DeleteButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String text = editText.getText().toString();

            if (text.length() > 0) {
                editText.setText(text.substring(0, text.length() - 1));
            }
        }
    }

    private class CallButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            String phoneNumber = editText.getText().toString();
            if (ContextCompat.checkSelfPermission(PhoneDialerActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        PhoneDialerActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE}, 1);
            } else {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + editText.getText().toString()));
                startActivity(intent);
            }
        }
    }

    private class EndButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            editText.setText("");
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_phone_dialer);

        editText = (EditText) findViewById(R.id.editText);

        Button button;
        for (int i = 0; i < ids.length; i++) {
            button = (Button)findViewById(ids[i]);
            button.setOnClickListener(numberButtonListener);
        }

        button = (Button)findViewById(R.id.button_del);
        button.setOnClickListener(deleteButtonListener);

        button = (Button)findViewById(R.id.button_call);
        button.setOnClickListener(callButtonListener);

        button = (Button)findViewById(R.id.button_end);
        button.setOnClickListener(endButtonListener);
    }
}
