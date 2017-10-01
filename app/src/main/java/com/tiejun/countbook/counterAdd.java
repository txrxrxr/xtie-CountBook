package com.tiejun.countbook;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This is another activity that handles the click after New Event button being pressed.
 * It asks the user to provide certain info. about the counter and save it into the file.
 */

public class counterAdd extends MainActivity implements View.OnClickListener{

    private Date date;
    private String name;
    private String current;
    private String comment;
    private String dateString;
    private Counter count;

    private EditText nameEdit;
    private TextView dateText;
    private EditText commentText;
    private EditText currentText;
    private Button cancelButton;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_count);

        nameEdit = (EditText) findViewById(R.id.nameEdit);

        dateText = (TextView) findViewById(R.id.dateText);
        date = new Date();
        dateString = new SimpleDateFormat("yyyy-MM-dd").format(date);
        dateText.setText(dateString);

        commentText = (EditText) findViewById(R.id.commentText);

        currentText = (EditText) findViewById(R.id.currentText);

        saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(this);

        cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == saveButton) {
            try {

                name = nameEdit.getText().toString();
                current = currentText.getText().toString();
                comment = commentText.getText().toString();
                count = new Counter(name, date, current, comment);
                counters.add(count);
                addCounter();
                finish();

            }catch (InvalidInputException e) {
                Log.d("empty", name+"  "+current);
                Toast.makeText(this, "Please specify a valid name and current value to continue on.", Toast.LENGTH_LONG).show();

            }catch (Exception e) {
                Toast.makeText(this, "Please specify a valid current value to continue on.", Toast.LENGTH_LONG).show();
            }
        }
        else if (view == cancelButton)
            finish();
    }
}
