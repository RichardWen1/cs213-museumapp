package com.example.museumapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MuseumInfoActivity extends AppCompatActivity {
    public static double salesTax = 0.08875;

    ImageButton imageButton;
    TextView textView;

    Spinner[] spinners = new Spinner[3];
    TextView priceTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_museum_info);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int museumId = getIntent().getIntExtra("MUSEUM_ID", -1);
        if (museumId == -1) finish();

        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setImageResource(getResources().getIdentifier(getStringById("image", museumId), "drawable", getPackageName()));
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getStringById("site", museumId))));
            }
        });

        textView = (TextView) findViewById(R.id.textView);
        String[] priceStrings = getStringById("prices", museumId).split(",");
        int[] prices = new int[3];
        for (int i = 0; i < prices.length; i++) {
            prices[i] = Integer.parseInt(priceStrings[i]);
        }
        textView.setText(getString(R.string.price_info) + "\nAdults: $" + prices[0] + "\nSeniors: $" + prices[1] + "\nStudents: $" + prices[2]);


        spinners[0] = (Spinner) findViewById(R.id.spinner1);
        spinners[1] = (Spinner) findViewById(R.id.spinner2);
        spinners[2] = (Spinner) findViewById(R.id.spinner3);
        priceTotal = (TextView) findViewById(R.id.price);

        String[] options = {"0", "1", "2", "3", "4", "5"};
        for (Spinner spinner : spinners) {
            spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, options));
            spinner.setSelection(0);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    int total = 0;
                    for (int j = 0; j < 3; j++) {
                        total += prices[j] * Integer.parseInt(spinners[j].getSelectedItem().toString());
                    }
                    double tax = total * salesTax;
                    priceTotal.setText("Price: $" + total + ".00\nTax: $" + String.format("%.2f", tax) + "\nTotal: $" + String.format("%.2f", total + tax));
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        }

        Toast t = Toast.makeText(this, R.string.ticket_limit, Toast.LENGTH_LONG);
        t.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
        t.show();
    }

    private String getStringById(String tag, int id) {
        return getString(getResources().getIdentifier(tag + id, "string", getPackageName()));
    }
}
