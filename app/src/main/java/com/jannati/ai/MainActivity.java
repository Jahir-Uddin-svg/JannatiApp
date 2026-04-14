package com.jannati.ai;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // এখানে আমরা লেআউটের আইডি মিলিয়ে নিচ্ছি
        View btn = findViewById(R.id.btnSettings);
        if (btn == null) {
            btn = findViewById(R.id.cardStatus);
        }

        if (btn != null) {
            btn.setOnClickListener(v -> {
                startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
            });
        }
    }
}
