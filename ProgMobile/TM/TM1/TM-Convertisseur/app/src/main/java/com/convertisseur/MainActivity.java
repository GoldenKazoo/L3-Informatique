package com.convertisseur;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button etol = findViewById(R.id.EtoL);
        Button ltoe = findViewById(R.id.LtoE);
        TextInputLayout input = findViewById(R.id.Input);
        String[] arraySpinner = new String[] {
                "€ → £", "£ → €", "€ → ¥", "¥ → €"
        };
        Spinner s = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        String selectItem = s.getSelectedItem().toString();

        etol.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String nb1 = input.getEditText().getText().toString();
                float nb2 = Float.valueOf(nb1);
                nb2 = nb2 / 1.55f;
                Toast.makeText(getApplicationContext(),String.valueOf(nb2), Toast.LENGTH_SHORT)
                        .show();
            }
        });
        ltoe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String nb1 = input.getEditText().getText().toString();
                float nb2 = Float.valueOf(nb1);
                nb2 = nb2 * 1.55f;
                Toast.makeText(getApplicationContext(),String.valueOf(nb2), Toast.LENGTH_SHORT)
                        .show();
            }
        });


    }
}