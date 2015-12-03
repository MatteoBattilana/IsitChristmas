package isitchristmas.battilanamatteo.com.isitchristmas;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import isitchristmas.battilanamatteo.com.isitchristmas.R;

public class MainActivity extends Activity {
    TextView txt_yes, txt_req;
    RelativeLayout mRelativeLayout;
    Typeface roboto_Thin, roboto_LightItalic;
    Animation anim;
    int tap_count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.content_main);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.view);
        txt_yes = (TextView) findViewById(R.id.txt_yes);
        txt_req = (TextView) findViewById(R.id.txt_req);
        roboto_Thin = Typeface.createFromAsset(getResources().getAssets(), "Roboto-Thin.ttf");
        roboto_LightItalic = Typeface.createFromAsset(getResources().getAssets(), "Roboto-LightItalic.ttf");
        txt_yes.setTypeface(roboto_Thin);
        txt_req.setTypeface(roboto_LightItalic);
        anim = AnimationUtils.loadAnimation(this, R.anim.fadein);
        new TaskControl(txt_yes).execute();
    }

    public class TaskControl extends AsyncTask<Void, Void, Boolean> {
        /*
        * 0 not executed
        * 1 is today
        * -1 not today*/
        TextView anw;

        public TaskControl(TextView anw) {
            this.anw = anw;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void[] values) {
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            Calendar c = Calendar.getInstance();
            //ESEGUIRE OPERAZIONI IN BACKGROUND
            if (GetTime.isNetworkAvailable(MainActivity.this)) {
                Date todayInternet = GetTime.getNTPDate();
                c.set(Calendar.DAY_OF_MONTH, todayInternet.getDay() - 1);
                c.set(Calendar.MONTH, todayInternet.getMonth());
            }
            // Log.e("Entrato", "Entrato " + c.get(Calendar.DAY_OF_MONTH) + " " + c.get(Calendar.MONTH));
            return (c.get(Calendar.MONTH) == 11 && c.get(Calendar.DAY_OF_MONTH) == 25);
        }

        @Override
        protected void onPostExecute(final Boolean check) {
            if (check)
                anw.setText(getResources().getString(R.string.yes));
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    txt_yes.startAnimation(anim);
                }
            }, 100);

            mRelativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tap_count > 1) {
                        //Start Activity
                        Intent myIntent = new Intent(MainActivity.this, InfoActivity.class);
                        startActivity(myIntent);
                        tap_count = 0;
                    } else
                        tap_count++;
                }
            });
        }

        @Override
        protected void onCancelled() {
        }
    }
}
