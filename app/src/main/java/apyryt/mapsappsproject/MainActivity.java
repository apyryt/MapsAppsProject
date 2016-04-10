package apyryt.mapsappsproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button addActivity;
    private EditText titleText, descriptionText;

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

    }
}
