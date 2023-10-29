package com.example.hacknc2023;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;


public class AddActivity extends DialogFragment {
    private MainActivity activity;
    public AddActivity(MainActivity activity){
        this.activity = activity;
    }

    HandleDatabase myDb = HandleDatabase.getInstance();
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Activity");

        final View layout = getLayoutInflater().inflate(R.layout.add_activity_user_form, null);
        builder.setView(layout);

        Spinner timeSpinner = layout.findViewById(R.id.timeSpinner);
        timeSpinner.setPrompt("Select School");
        ArrayAdapter<CharSequence> timeAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.time, android.R.layout.simple_spinner_item);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(timeAdapter);

        Spinner schoolSpinner = layout.findViewById(R.id.schoolSpinner);
        schoolSpinner.setPrompt("Select School");
        ArrayAdapter<CharSequence> schoolAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.schools, android.R.layout.simple_spinner_item);
        schoolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        schoolSpinner.setAdapter(schoolAdapter);


        final Spinner mealSportsSpinner = layout.findViewById(R.id.mealSportsSpinner);
        ArrayAdapter<CharSequence> mealSportsAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.meals_sports, android.R.layout.simple_spinner_item);
        mealSportsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mealSportsSpinner.setAdapter(mealSportsAdapter);

        mealSportsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedItem = mealSportsSpinner.getSelectedItem().toString();
                if (selectedItem.equals("Meal")) {

                    layout.findViewById(R.id.cuisineSpinner).setVisibility(View.VISIBLE);
                    layout.findViewById(R.id.sportSpinner).setVisibility(View.GONE);
                    layout.findViewById(R.id.cuisinePrompt).setVisibility(View.VISIBLE);
                    layout.findViewById(R.id.sportsPrompt).setVisibility(View.GONE);

                } else if (selectedItem.equals("Sports")) {

                    layout.findViewById(R.id.cuisineSpinner).setVisibility(View.GONE);
                    layout.findViewById(R.id.sportSpinner).setVisibility(View.VISIBLE);
                    layout.findViewById(R.id.cuisinePrompt).setVisibility(View.GONE);
                    layout.findViewById(R.id.sportsPrompt).setVisibility(View.VISIBLE);
                } else {

                    layout.findViewById(R.id.cuisineSpinner).setVisibility(View.GONE);
                    layout.findViewById(R.id.sportSpinner).setVisibility(View.GONE);
                    layout.findViewById(R.id.cuisinePrompt).setVisibility(View.GONE);
                    layout.findViewById(R.id.sportsPrompt).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle when nothing is selected
            }
        });

        Spinner cuisineSpinner = layout.findViewById(R.id.cuisineSpinner);
        ArrayAdapter<CharSequence> cuisineAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.cuisines, android.R.layout.simple_spinner_item);
        cuisineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cuisineSpinner.setAdapter(cuisineAdapter);

        Spinner sportSpinner = layout.findViewById(R.id.sportSpinner);
        ArrayAdapter<CharSequence> sportAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.sports, android.R.layout.simple_spinner_item);
        sportAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sportSpinner.setAdapter(sportAdapter);

        Spinner genderSpinner = layout.findViewById(R.id.genderSpinner);
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.genders, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);

        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Handle the form submission here
                String selectedItem = mealSportsSpinner.getSelectedItem().toString();
                if (selectedItem.equals("Meal")) {

                    myDb.findOrCreateActivities(MainActivity.currUserId, cuisineSpinner.getSelectedItem().toString() + " cuisine", activity);
                } else if (selectedItem.equals("Sports")){
                    myDb.findOrCreateActivities(MainActivity.currUserId, sportSpinner.getSelectedItem().toString(), activity);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        return builder.create();
    }
}
