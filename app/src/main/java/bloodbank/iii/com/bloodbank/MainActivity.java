package bloodbank.iii.com.bloodbank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by rajiv on 2/21/15.
 */
public class MainActivity extends ActionBarActivity{

    private ProgressDialog mProgress;
    private static  String url="http://shareblood.hol.es/send.php";

    private static final String tag_name="name";
    private static final String tag_age="age";
    private static final String tag_area="area";
    private static final String tag_sex="sex";
    private static final String tag_mobile="mobile";

    public int textv[]={R.id.info_text,R.id.info_textdet,R.id.info_text1,R.id.info_textdet1,
                R.id.info_text2,R.id.info_textdet2,R.id.info_text3,R.id.info_textdet3,
                R.id.info_text4,R.id.info_textdet4,R.id.info_text5,R.id.info_textdet5,
                R.id.info_text6,R.id.info_textdet6,R.id.info_text7,R.id.info_textdet7,
                R.id.info_text8,R.id.info_textdet8,R.id.info_text9,R.id.info_textdet9};
    public int cardtv[]={R.id.card_view_events,R.id.card_view_events1,R.id.card_view_events2,
            R.id.card_view_events3,R.id.card_view_events4,R.id.card_view_events5,R.id.card_view_events6,
            R.id.card_view_events7,R.id.card_view_events8,R.id.card_view_events9};
    public TextView tv[];
    private TextView tv1;
    public CardView cv[];
    private RelativeLayout rl;
    int i=0;

    //public HashMap<String,String> info;
    public ArrayList<HashMap<String,String>> infolist=null;

    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.search_results);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#f3c614")));

        infolist = new ArrayList<HashMap<String, String>>();

        tv=new TextView[20];
        cv=new CardView[10];

        rl=(RelativeLayout)findViewById(R.id.relativelayout);

        for(i=0;i<10;i++) {
            cv[i]=(CardView)findViewById(cardtv[i]);
        }

        for(i=0;i<20;i++)
        {
            tv[i]=(TextView)findViewById(textv[i]);
            //tv[i].setOnClickListener(this);
        }

        new GetInfo().execute();
    }

    private class GetInfo extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            mProgress = new ProgressDialog(MainActivity.this);
            mProgress.setMessage("Please wait...");
            mProgress.setCancelable(false);
            mProgress.show();
            rl.setVisibility(View.INVISIBLE);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();


            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    Log.d("asd","asd");
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray results = jsonObj.getJSONArray("results");

                    for (int i = 0; i < results.length(); i++) {
                        JSONObject c = results.getJSONObject(i);

                        String name = c.getString(tag_name);
                        String area = c.getString(tag_area);
                        String age = c.getString(tag_age);
                        String mobile = c.getString(tag_mobile);
                        String sex = c.getString(tag_sex);

                        HashMap<String,String> info = new HashMap<String, String>();

                        info.put(tag_name, name);
                        info.put(tag_area, area);
                        info.put(tag_age, age);
                        info.put(tag_mobile, mobile);
                        info.put(tag_sex, sex);
                        infolist.add(info);
                        Log.d("QWERTY",infolist.get(i).get(tag_name));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if(mProgress.isShowing()) {
                mProgress.dismiss();
            }
            rl.setVisibility(View.VISIBLE);

            String s1,s2,s3,s4,s5;
            int j=0;

            for(i=0;i<20;i+=2) {
                tv[i].setText(infolist.get(j).get(tag_name));
                s1 = infolist.get(j).get(tag_sex);
                s2 = infolist.get(j).get(tag_age);
                s3 = infolist.get(j).get(tag_area);
                s4 = infolist.get(j).get(tag_mobile);
                s5 = "\n" + s1 + "\n" + s2 + " to " + s3 + "\n" + s4;
                Log.d("asd",s5);
                tv[i+1].setText(s5);
                j++;
            }

        }
    }

}

