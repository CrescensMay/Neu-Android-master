package com.neuandroid.chuck;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.squareup.seismic.ShakeDetector;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements ShakeDetector.Listener {

    private TextView tvChuck;
    private ProgressBar pbLoading;
    private EditText edtFirstName;
    private EditText edtLastName;
    private Button jButton;
    private colorWheel jcolorWheel = new colorWheel();
    private View jView;
    private TextView jViewCount;
    private int count = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        ShakeDetector sd = new ShakeDetector(this);
        sd.start(sensorManager);

        tvChuck = (TextView) findViewById(R.id.tv_chuck);
        tvChuck.setText(getText(R.string.click_to_load));
        jButton = (Button) findViewById(R.id.trigger);
        jView = findViewById(R.id.framelayout);
        jViewCount = (TextView) findViewById(R.id.counter);

        jButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int jColor;
                jColor = jcolorWheel.getColor();
                jView.setBackgroundColor(jColor);
                animate();
                counting();
                loadChuckQuotes();


            }
        });
        pbLoading = (ProgressBar) findViewById(R.id.pb_loading);
        edtFirstName = (EditText) findViewById(R.id.edt_first_name);
        edtLastName = (EditText) findViewById(R.id.edt_last_name);

        Toast.makeText(MainActivity.this, "Welcome to the Joke's Show!",Toast.LENGTH_LONG).show();
    }
    private void counting(){


            String counting = " Joke : " + count++ + " ";
            jViewCount.setText(counting);
    }
    private void loadChuckQuotes() {
        String firstName = edtFirstName.getText().toString();
        String lastName = edtLastName.getText().toString();
        URL url = NetworkUtils.buildUrl(firstName, lastName);

        new ChuckQuoteTask().execute(url);

    }

    @Override
    public void hearShake() {
        loadChuckQuotes();
    }

    public void animate(){

        Animator scaleText = AnimatorInflater.loadAnimator(this,R.animator.scale);
        scaleText.setTarget(tvChuck);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(scaleText);
        set.start();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

        return true;
    }
    /**
     * This class extends AsyncTask to execute the query out of the main thread.
     * We should always not run a time consuming task on main thread.
     */
    private class ChuckQuoteTask extends AsyncTask<URL, Object, String> {

        @Override
        protected void onPreExecute() {
            // Here we prepare what we need to be done on the UI
            // For example, show a progress bar for a download task.
            // This method is executed on main thread.
            pbLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... params) {
            String result = null;

            try {
                URL url = params[0];
                result = NetworkUtils.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            // This is method is executed after the task is over.
            // Since we use this task to get a String.
            // We should have a function to let us render what we got after the task
            // And here the function is onPostExecute()


            // This function is executed on main thread as well.
            pbLoading.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(result)) {

                try {
                    String joke = extractJokeFromJson(result);
                    tvChuck.setText(joke);
                } catch (JSONException e) {
                    e.printStackTrace();
                    tvChuck.setText(getString(R.string.result_empty));
                }
            }
        }

        private String extractJokeFromJson(String json) throws JSONException {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject value = jsonObject.optJSONObject("value");
            String joke = value.optString("joke");
            return joke;
        }
    }

}
