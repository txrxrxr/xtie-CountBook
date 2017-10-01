package com.tiejun.countbook;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *  This is the activity after edit option has been selected on the context menu.
 * User can change previous entered info and save it by clicking the save button or discard the changes by clicking cancel.
 * Reset to initial button enables user to reset the current value of the counter to its intial value.
 */

public class counterEdit extends MainActivity implements View.OnClickListener{

    private Date date;
    private String name;
    private String current;
    private String comment;
    private String dateString;
    private int position;
    private int current_1;

    private EditText nameEdit;
    private TextView dateText;
    private EditText commentText;
    private EditText currentText;
    private Button cancelButton;
    private Button saveButton;
    private Button incButton;
    private Button decButton;
    private Button resetButton;
    private EditText initialText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_count);

        Intent i = getIntent();
        position = i.getIntExtra("pos", 0);

        nameEdit = (EditText) findViewById(R.id.nameEdit);

        dateText = (TextView) findViewById(R.id.dateText);
        date = new Date();
        dateString = new SimpleDateFormat("yyyy-MM-dd").format(date);
        dateText.setText(dateString);

        current_1 = counters.get(position).getCurrent();

        initialText = (EditText) findViewById(R.id.initialText);

        commentText = (EditText) findViewById(R.id.commentText);

        currentText = (EditText) findViewById(R.id.currentText);

        nameEdit.setText(counters.get(position).getName());

        currentText.setText(counters.get(position).getCurrent()+"");
        commentText.setText(counters.get(position).getComment());
        initialText.setText(counters.get(position).resetValue()+"");

        initialText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (current_1 == 0) {
                    currentText.setText(initialText.getText());
                }
            }
        });

        saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(this);

        cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(this);

        incButton = (Button) findViewById(R.id.incButton);
        incButton.setOnClickListener(this);

        decButton = (Button) findViewById(R.id.decButton);
        decButton.setOnClickListener(this);

        resetButton = (Button) findViewById(R.id.resetButton);
        resetButton.setOnClickListener(this);
    }


    public void onClick(View view) {
        int c;

        if (view == saveButton) {
            try {

                name = nameEdit.getText().toString();
                current = currentText.getText().toString();
                comment = commentText.getText().toString();

                counters.get(position).setCurrent(Integer.parseInt(current));
                counters.get(position).setComment(comment);
                counters.get(position).setName(name);
                counters.get(position).setInitial(Integer.parseInt(initialText.getText().toString()));

                if (Integer.parseInt(current) != current_1) {       // current value is changed
                    counters.get(position).setDate(date);
                    Log.d("date", date.toString());
                }

                addCounter();

                Toast.makeText(this, "Change Saved", Toast.LENGTH_SHORT).show();

                finish();

            }catch (InvalidInputException e) {

                Toast.makeText(this, "Please specify a valid name and values to continue on.", Toast.LENGTH_LONG).show();

            }catch (Exception e) {
                Toast.makeText(this, "Errors", Toast.LENGTH_LONG).show();
            }
        }

        else if (view == cancelButton)
            finish();
        else if (view == incButton) {
            try {
                c = Integer.parseInt(currentText.getText().toString());
                if (c < 0) {
                    counters.get(position).setCurrent(c);
                }
                c++;
                counters.get(position).setCurrent(c);
                currentText.setText(c + "");
            } catch (Exception e) {
                Toast.makeText(this, "Invalid value", Toast.LENGTH_SHORT).show();
            }
        }
        else if (view == decButton) {
            try {
                c = Integer.parseInt(currentText.getText().toString());
                c--;
                counters.get(position).setCurrent(c);
                currentText.setText(c + "");
            } catch (InvalidInputException e) {
                currentText.setText(0+"");
                Toast.makeText(this, "Minimum value is 0.", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Invalid value", Toast.LENGTH_SHORT).show();
            }

        }
        else if (view == resetButton) {

            int v = counters.get(position).resetValue() ;       // get initial value

            if (v != counters.get(position).getCurrent()) {
                try {
                    counters.get(position).setCurrent(v);
                    currentText.setText(initialText.getText().toString());
                    counters.get(position).setDate(date);

                } catch (InvalidInputException e) {
                    //Toast.makeText(this, "Invalid value entered", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }
    }

}
