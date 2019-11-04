package com.example.paylocityemployees;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private RadioGroup radioGroup;
    private EditText children;

    private Intent intent;
    private Employee oldEmployee;
    private Employee newEmployee;
    private String name;
    private boolean spouse;
    private int num_child;
    private int pos;

    private static final String TAG = "AddActivity";
    // public to access the extra in the OnActivityResult
    // so that we can
    // Retrieve the data by the intent name
    public static final String extraName = "DATA HOLDER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // get a reference to each component
        // to fill things in or to save the data
        firstName = findViewById(R.id.editText_firstName);
        lastName = findViewById(R.id.editText_lastName);
        radioGroup = findViewById(R.id.radioGroup);
        children = findViewById(R.id.editText_children);

        intent = getIntent();
        // only check if we have an Employee to edit the object
        // if we do not have this extra, we are creating a new employee object
        if (intent.hasExtra("Employee")) {
            // set the oldEmployee to preview the old data
            oldEmployee = (Employee) intent.getSerializableExtra("Employee");
            name = oldEmployee.getName();
            spouse = oldEmployee.isSpouse();
            num_child = oldEmployee.getChildren();

            String arr[] = name.split(" ", 2);

            // fill in the first name
            firstName.setText(arr[0]);
            // fill in the the last name
            lastName.setText(arr[1]);
            // fill in which radio button was clicked
            if (spouse == true) {
                radioGroup.check(R.id.radioButton2);
            } else {
                radioGroup.check(R.id.radioButton);
            }
            // fill in the number of children
            children.setText(Integer.toString(num_child));
            // reference the position to remove it
            // and add it to the right place
            pos = intent.getIntExtra("Position", 0);
        }
    }

    public void startPreview(View view) {
        // start the new activity

        Intent preview = new Intent(this, PreviewActivity.class);
        // if we pass an employee from main, pass it on to preview activity
        // else we use the data from this activity
        // otherwise you will pass the old employee to the preview even if you save or not
        // so saving won't make a difference
        // now if you save, you can preview the new employee
        if (intent.hasExtra("Employee")) {
            preview.putExtra("Employee", oldEmployee);
        }
        else {
            preview.putExtra("Employee", newEmployee);
        }

        startActivity(preview);
    }

    public void saveData(View view) {
        // save the data
        Toast.makeText(this, "Saved the data!", Toast.LENGTH_SHORT).show();

        String name = firstName.getText().toString() + " " + lastName.getText().toString();

        int radioId = radioGroup.getCheckedRadioButtonId();
        RadioButton selected = findViewById(radioId);
        String has_spouse = selected.getText().toString();

        boolean spouse;
        if(has_spouse.equals("Yes")) {
            spouse = true;
        }
        else {
            spouse = false;
        }
        int num_chil = Integer.parseInt(children.getText().toString());

        // set the newEmployee to preview the new data
        newEmployee = new Employee(name, spouse, num_chil);

        Intent intent = new Intent();
        intent.putExtra(extraName, newEmployee);
        intent.putExtra("Position", pos);
        setResult(Activity.RESULT_OK, intent);
        Log.d(TAG, "onOptionsItemSelected: " + "DATA IS SET");

    }
}
