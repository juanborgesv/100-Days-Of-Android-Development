package com.juanborges.coffeeorder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    Button decreaseButton;
    Button increaseButton;
    TextView quantityTextView;
    TextView totalTextView;

    int quantity;
    float coffeeValue;
    float total;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        decreaseButton = findViewById(R.id.decrease_button);
        increaseButton = findViewById(R.id.increase_button);
        quantityTextView = findViewById(R.id.qvalue_tv);
        totalTextView = findViewById(R.id.total_tv);

        quantity = Integer.parseInt(quantityTextView.getText().toString());
        coffeeValue = 2.75f;
        total = quantity*coffeeValue;
    }

    public void decreaseValue(View view)
    {
        if (quantity > 0)
        {
            quantityTextView.setText("" + --quantity);
            calculatePrice();
        }
    }

    public void increaseValue(View view)
    {
        quantityTextView.setText(""+ ++quantity);
        calculatePrice();
    }

    void calculatePrice()
    {
        total = quantity*coffeeValue;
        totalTextView.setText("Total: $"+ String.format("%.2f", total));
    }

    public void displayToast(View view)
    {
        if (quantity > 0)
        {
            Toast.makeText(MainActivity.this, "Your order has been registered.",
                    Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(MainActivity.this,"Error: Set one coffee or more to order.",
                    Toast.LENGTH_LONG).show();
        }
    }
}
