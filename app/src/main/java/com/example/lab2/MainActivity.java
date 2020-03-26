package com.example.lab2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {
    private String Tag= "MainActivity";
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allow();
        setContentView(R.layout.activity_main);
        final String[] products = new String[] {"Carti de povesti","Carti SF","Carti istorice"};
        save_items(products);
        final String[] quantities = new String[] {"45","56","100"};
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, R.layout.center_list, products);
        ListView listView = findViewById(R.id.ProductList);
        textView = findViewById(R.id.ShopTitle);
        textView.setText("BookShop");
        textView.setTextColor(Color.BLUE);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(getApplicationContext(), ProductActivity.class);
                intent.putExtra("index",position );
                intent.putExtra("quantity",quantities[position] );
                startActivity(intent);
            }
        });
        set_preference();
        Button buton=findViewById(R.id.buton);
        buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), SensorActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.search:
                search();
                return true;
            case R.id.settings:
                Intent intent= new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.home:
                return true;
            case R.id.account:
                return true;
            case R.id.shopping_cart:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void set_preference()
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean checked = sharedPreferences.getBoolean("pinktext", false);
        if (checked)
            textView.setTextColor(Color.parseColor("#FC46AA"));
        else
            textView.setTextColor(Color.parseColor("#000000"));
    }


    private void search(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Search...");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT );
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String m_Text = input.getText().toString();
                if (m_Text.isEmpty())
                {
                    dialog.cancel();
                    AlertDialog.Builder buildererr = new AlertDialog.Builder(MainActivity.this);

                    buildererr.setMessage("Input should not be empty!")
                            .setTitle("Error");
                    buildererr.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            search();
                        }
                    });
                    AlertDialog error = buildererr.create();
                    error.show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void allow()
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }


    public void save_items(String[] items)
    {
        File file= new File(getApplicationContext().getFilesDir(), "data.txt");
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        writer.print("");
        writer.close();
        try {
            FileWriter fileWriter= new FileWriter(file,true);
            for( String item:items)
            {
                fileWriter.append(item).append("\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(Tag,"Activity started" );
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(Tag,"Activity stopped" );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(Tag,"Activity destroyed" );
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(Tag,"Activity paused" );
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(Tag,"Activity resumed" );
        set_preference();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(Tag,"Activity restarted" );
        set_preference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        String text= (String) textView.getText();
        outState.putString("title", text);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState) {
        String title= savedInstanceState.getString("title");
        textView.setText(title);
        super.onRestoreInstanceState(savedInstanceState);
    }


}
