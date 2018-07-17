package com.juanborges.coffeeorder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    float[] coffeeValue = {3.45f, 3.45f, 4.05f, 1.95f, 4.25f};
    int[] iCoffeeQuantity = {0, 0, 0, 0, 0};
    TextView[] coffeeQuantity = new TextView[5];
    String[] coffeeName = {"Latte", "Cappuccino", "Flat White", "Espresso", "Caramel Macchiato"};

    EditText firstName;
    EditText lastName;

    TextView orderSummary;

    CheckBox whipped;
    CheckBox caramel;
    CheckBox chocolate;
    CheckBox nutella;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView myTextView = findViewById(R.id.latte_quantity_text);
        coffeeQuantity[0] = myTextView;
        myTextView = findViewById(R.id.cappuccino_quantity_text);
        coffeeQuantity[1] = myTextView;
        myTextView = findViewById(R.id.flatwhite_quantity_text);
        coffeeQuantity[2] = myTextView;
        myTextView = findViewById(R.id.espresso_quantity_text);
        coffeeQuantity[3] = myTextView;
        myTextView = findViewById(R.id.caramel_quantity_text);
        coffeeQuantity[4] = myTextView;

        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);

        orderSummary = findViewById(R.id.summary_text);

        whipped = findViewById(R.id.whipped_cb);
        caramel = findViewById(R.id.caramel_cb);
        chocolate = findViewById(R.id.chocolate_cb);
        nutella = findViewById(R.id.nutella_cb);
    }

    public void decreaseQuantity(View view)
    {
        int coffeeTag = Integer.parseInt(view.getTag().toString());
        if (iCoffeeQuantity[coffeeTag] > 0)
        {
            --iCoffeeQuantity[coffeeTag];
            coffeeQuantity[coffeeTag].setText("" + iCoffeeQuantity[coffeeTag]);
        }
    }

    public void increaseQuantity(View view)
    {
        int coffeeTag = Integer.parseInt(view.getTag().toString());
        ++iCoffeeQuantity[coffeeTag];
        coffeeQuantity[coffeeTag].setText("" + iCoffeeQuantity[coffeeTag]);
    }

    public void orderSummary(View view)
    {
        String summary = "" + firstName.getText().toString() + " " + lastName.getText().toString() +
                "\n\n";
        float price;
        float total = 0;
        for (int x = 0; x < 5; x++)
        {
            if (iCoffeeQuantity[x] > 0)
            {
                price = iCoffeeQuantity[x] * coffeeValue[x];
                summary += iCoffeeQuantity[x];
                summary += "  " + coffeeName[x];
                summary += "\t\t $" + price;
                summary += "\n\n";

                total += price;
            }
        }

        if (whipped.isChecked())
        {
            summary += "Topping: Whipped Cream \t\t $1.00\n";
            total += 1.00f;
        }
        if (caramel.isChecked())
        {
            summary += "Topping: Caramel Syrup \t\t $1.00\n";
            total += 1.00f;
        }
        if (chocolate.isChecked())
        {
            summary += "Topping: Chocolate \t\t $1.00\n";
            total += 1.00f;
        }
        if (nutella.isChecked())
        {
            summary += "Topping: Nutella \t\t $1.00\n";
            total += 1.00f;
        }

        summary += "Total: $" + total + "\n\n";
        orderSummary.setText("" + summary);
    }
}
