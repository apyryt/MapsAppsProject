package apyryt.mapsappsproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button addActivity;
    private EditText titleText, descriptionText;

    public static String eventDescr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //button
        addActivity = (Button) findViewById(R.id.addEventButton);
        addActivity.setOnClickListener(this);

        //textboxes to enter info for app
        titleText = (EditText) findViewById(R.id.titleEditText);
        descriptionText = (EditText) findViewById(R.id.descriptionEditText);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addEventButton:

                //Maps intent
                Intent maps = new Intent(this, MapsActivity.class);

                //gets the info of the title and description to be created in the marker
                String msg = titleText.getText().toString() + ";" + descriptionText.getText().toString();
                maps.putExtra(eventDescr, msg);

                //add a new marker with the information of the new event
                startActivity(maps);
                break;
        }
    }
}
