package com.anomalycon.murdermysterycontest;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.anomalycon.clues.Key;

import com.anomalycon.clues.ClueInterface;

import javax.inject.Inject;

public class NewClueActivity extends Activity {
    @Inject
    ClueInterface cif;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Dagger things
        ContestApplication cApp = (ContestApplication) getApplication();
        cApp.getObjectGraph().inject(this);

        setContentView(R.layout.activity_new_clue);

        EditText editText = (EditText) findViewById(R.id.cluePassword);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    if(!checkIfEmptyPassword()) {
                        saveClue();
                        handled = true;
                    }
                }
                return handled;
            }
        });

    }

    public boolean checkIfEmptyPassword() {
        EditText editText = (EditText) findViewById(R.id.cluePassword);
        String password = editText.getText().toString();
        if(password.equals("")) {
            editText.setError("Oops! Cannot be empty!");
            return true;
        }
        return false;
    }

    public void saveClue() {
        EditText editText = (EditText) findViewById(R.id.cluePassword);
        String password = editText.getText().toString();
        Key key = new Key(password);
        cif.saveClue(key);
        Toast.makeText(getApplicationContext(), "Saving clue..", Toast.LENGTH_LONG).show();
    }


}
