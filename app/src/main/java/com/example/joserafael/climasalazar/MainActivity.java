package com.example.joserafael.climasalazar;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {


    private static String urldia = "http://api.openweathermap.org/data/2.5/weather?q=Barranquilla,co&units=metric";
    private static String urlDias = "http://api.openweathermap.org/data/2.5/forecast/daily?id=3689147&units=metric";
    private ProgressDialog pDialog;
    private int validador=0;
    ArrayList<DataEntry> listaClima;
    JSONArray clima = null;
    JSONObject clima2;
    private TextView salida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        salida = (TextView) findViewById(R.id.textView);
        salida.setText("");
        listaClima= new ArrayList<>();


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isNetworkAvaible = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isNetworkAvaible = true;
            Toast.makeText(this, "Network is available ", Toast.LENGTH_LONG) .show();

        } else {
            Toast.makeText(this, "Network not available ", Toast.LENGTH_LONG) .show();
        }
        return isNetworkAvaible;
    }
    public void checkInternet(View view) {
        isNetworkAvailable();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void hola()
    {
        if(validador==0) {
            salida.setText("Main : \r\n" + listaClima.get(0).getMain() + "\r\nDescription :\r\n" + listaClima.get(0).getDescription() + "\r\nTemp :\r\n" + listaClima.get(0).getTemp() + "\r\nHumidity :\r\n" + listaClima.get(0).gethumidity());
         }
        else {
           String va="";
            if (validador==1)
            {
               for(int i=0;i<listaClima.size();i++)
               {
                   va=va+"Temp Day "+i+ " :\r\n" + listaClima.get(i).getday() +"\r\nMain : \r\n" + listaClima.get(i).getMain() + "\r\nDescription :\r\n" + listaClima.get(i).getDescription() + "\r\nTemp min :\r\n" + listaClima.get(i).getTemp() + "\r\nTemp Max :\r\n" + listaClima.get(i).gethumidity()+"\r\n";

               }
                salida.setText(va);
            }
        }
    }

    public void requestData(View view) {
        listaClima.clear();
        validador=0;
        new GetData().execute();


    }
    public void requestData2(View view) {
        listaClima.clear();
        validador=1;
        new GetData().execute();


    }
    /**
     * Async task class to get json by making HTTP call
     */
    private class GetData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            // Making a request to url and getting response

            if(validador==0) {
                String jsonStr = sh.makeServiceCall(urldia, ServiceHandler.GET);
                DataEntry dataEntry = new DataEntry();


                //Log.d("Response: ", "> " + jsonStr);
                if (jsonStr != null) {

                    try {

                        JSONObject jsonObj = new JSONObject(jsonStr);

                        clima = jsonObj.getJSONArray("weather");
                        //  Log.d("Response length: ", "> " + clima.length());


                        //JSONObject c = clima.getJSONObject(i).getJSONObject("weather1");

                        //Log.d("entro9 ",);


                        dataEntry.setMain(clima.getJSONObject(0).getString("main"));
                        dataEntry.setdescription(clima.getJSONObject(0).getString("description"));
                        listaClima.add(dataEntry);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    Log.e("ServiceHandler", "Couldn't get any data from the url");
                }

                if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);


                        clima2 = jsonObj.getJSONObject("main");
                        //  Log.d("Response length: ", "> " + clima.length());
                        //Log.d("entro ","1 "+jsonObj.getString("main"));


                        dataEntry.setTemp(clima2.getString("temp"));

                        //JSONObject name = c.getJSONObject("name");

                        dataEntry.sethumidity(clima2.getString("humidity"));
                        //dataEntry.setLastName(name.getString("last"));

                        // JSONObject imageObject = c.getJSONObject("picture");

                        // dataEntry.setPicture(imageObject.getString("large"));

                        listaClima.add(dataEntry);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    Log.e("ServiceHandler", "Couldn't get any data from the url");
                }
            }
            else
            {
                if(validador==1)
                {
                   String jsonStr2 = sh.makeServiceCall(urlDias, ServiceHandler.GET);
                    if (jsonStr2 != null) {

                        try {

                            JSONObject jsonObj = new JSONObject(jsonStr2);

                            clima = jsonObj.getJSONArray("list");

                            Log.d("entro ","tam "+clima.length());
                            for (int i = 0; i < clima.length(); i++) {
                            DataEntry dataEntry = new DataEntry();

                            JSONArray c = clima.getJSONObject(i).getJSONArray("weather");
                            JSONObject d = clima.getJSONObject(i).getJSONObject("temp");
                          //  Log.d("entro ","1 "+c.getJSONObject(0).getString("description")+" "+clima.length()+" "+c.length());


                            dataEntry.setMain(c.getJSONObject(0).getString("main"));
                            dataEntry.setdescription(c.getJSONObject(0).getString("description"));
                            dataEntry.setTemp(d.getString("min"));
                            dataEntry.sethumidity(d.getString("max"));
                            dataEntry.setday(d.getString("day"));
                            listaClima.add(dataEntry);

                           }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        Log.e("ServiceHandler", "Couldn't get any data from the url");
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            //  CustomAdapter adapter = new CustomAdapter(MainActivity.this, listaClima);
            //  listView.setAdapter(adapter);//toca mirar algo asi aca
           hola();
        }
    }
    }
