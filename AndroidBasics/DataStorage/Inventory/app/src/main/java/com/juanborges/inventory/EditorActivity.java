package com.juanborges.inventory;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EditorActivity extends AppCompatActivity {

    static final String LOG_TAG = EditorActivity.class.getSimpleName();
    static final int PICK_IMAGE = 1;

    ImageView image;

    MaterialButton minusButton;
    TextView quantityText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        image = findViewById(R.id.image);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        minusButton = findViewById(R.id.minus_button);
        quantityText = findViewById(R.id.quantity_text);
        MaterialButton plusButton = findViewById(R.id.plus_button);

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(quantityText.getText().toString());

                if (quantity < 1)
                    return;

                --quantity;

                quantityText.setText(String.valueOf(quantity));
            }
        });

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(quantityText.getText().toString());
                ++quantity;


                quantityText.setText(String.valueOf(quantity));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_IMAGE && data != null) {
            try {
                String path = data.getData().toString();
                Log.i(LOG_TAG, "Picture path: "+ path);
                image.setImageURI(data.getData());
            } catch (NullPointerException e) {
                Log.e(LOG_TAG, "Something went wrong while trying to get image path", e);
            }
        }
    }
}
