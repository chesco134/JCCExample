package com.example.brenda.jccexample.activities;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.brenda.jccexample.R;

import java.util.List;

/**
 * Created by jcapiz on 7/10/17.
 */

public class MyVoiceActivity extends AppCompatActivity {

    private TextView mainTextView;

    private static final int SPEECH_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_voice_activity);
        mainTextView = (TextView) findViewById(R.id.my_voice_activity_main_text);
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putString("main_text",mainTextView.getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        mainTextView.setText(savedInstanceState.getString("main_text"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.my_voice_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.my_voice_activity_menu_get_text){
            displaySpeechRecognizer();
            return true;
        }
        return false;
    }

    // Create an intent that can start the Speech Recognizer activity
    private void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
// Start the activity, the intent will be populated with the speech text
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    // This callback is invoked when the Speech Recognizer returns.
// This is where you process the intent and extract the speech text from the intent.
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            // Do something with spokenText
            mainTextView.setText(spokenText);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
