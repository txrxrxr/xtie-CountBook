package com.tiejun.countbook;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * This is the main activity of the xtie-countbook app. One button at bottom enables user to add new events.
 * One context menu on each list item with options to add or delete an existing counter.
 */

public class MainActivity extends Activity {

    protected static final String FILENAME = "file.sav";
    public ArrayList<Counter> counters = new ArrayList<>();
    public ArrayAdapter<Counter> adapter;
    private ListView counterList;
    private Button addButton;
    private TextView numRecords;
    public int itemIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFromFile();

        numRecords = (TextView) findViewById(R.id.numRecords);
        numRecords.setText(counters.size()+" counter(s): ");

        addButton = (Button) findViewById(R.id.addButton);
        counterList = (ListView) findViewById(R.id.counterList);

        adapter = new ArrayAdapter<>(this, R.layout.counter_1, counters);
        counterList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        addButton.setOnClickListener( new View.OnClickListener (){

            public void onClick(View v) {
                Intent addIntent = new Intent(MainActivity.this, counterAdd.class);
                startActivity(addIntent);
            }
        });

        registerForContextMenu(counterList);
        counterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                itemIndex = position;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("More Options");
        menu.add(0, v.getId(), 0, "Edit");
        menu.add(0, v.getId(), 0, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int p = info.position;

        if (item.getTitle().equals("Edit")) {
            Toast.makeText(this, "EDIT", Toast.LENGTH_LONG).show();
            Intent i = new Intent(MainActivity.this, counterEdit.class);
            i.putExtra("pos", p);
            startActivity(i);
        }

        else if (item.getTitle().equals("Delete")) {
            Toast.makeText(this, "DELETED", Toast.LENGTH_SHORT).show();
            counters.remove(itemIndex);
            numRecords.setText(counters.size()+" counter(s): ");
        }

        else {
            return false;
        }

        adapter = new ArrayAdapter<>(this, R.layout.counter_1, counters);
        counterList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        addCounter();
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile();
        adapter = new ArrayAdapter<>(this, R.layout.counter_1, counters);
        counterList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        numRecords.setText(counters.size()+" counter(s): ");
    }

    public void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Counter>>() {
            }.getType();
            counters = gson.fromJson(in, listType);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            counters = new ArrayList<Counter>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
    }

    public void addCounter() {

        /*save to the file */

        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(counters, writer);
            writer.flush();
            fos.close();
        } catch (Exception e) {
            //e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
