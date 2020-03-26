package com.example.lab2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ProductActivity extends AppCompatActivity {
    private TextView name;
    String Tag="Product activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        name= findViewById(R.id.product);
        TextView quantity= findViewById(R.id.quantity);
        Intent intent = getIntent();
        int position= intent.getIntExtra("index", -1);
        name.setText(getItem(position));
        String quantity_text="Nr. carti in stoc: "+intent.getStringExtra("quantity");
        quantity.setText(quantity_text);
        set_preference();
    }

    public String getItem(int position)
    {
        File file= new File(getApplicationContext().getFilesDir(), "data.txt");
        try {
            BufferedReader bufferedReader= new BufferedReader(new FileReader(file));
            String line = "";
            for ( int i=0;i<=position;i++)
                line= bufferedReader.readLine();
            return line;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return "";
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
            name.setTextColor(Color.parseColor("#FC46AA"));
        else
            name.setTextColor(Color.parseColor("#000000"));
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
                    AlertDialog.Builder buildererr = new AlertDialog.Builder(ProductActivity.this);

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
}
