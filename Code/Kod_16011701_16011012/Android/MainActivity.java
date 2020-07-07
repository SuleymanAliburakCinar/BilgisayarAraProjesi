package app.com.slo.flaskserver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView value;
    private Spinner location,state,uzaklık;
    private EditText odaSayısı,banyoSayısı,garajSayısı,mxm;

    JSONObject jObj = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        value = (TextView) findViewById(R.id.textView4);

        location = (Spinner) findViewById(R.id.Location);
        state = (Spinner) findViewById(R.id.State);
        uzaklık = (Spinner) findViewById(R.id.Uzaklık);

        odaSayısı = (EditText) findViewById(R.id.OdaSayısı);
        banyoSayısı = (EditText) findViewById(R.id.BanyoSayısı);
        garajSayısı = (EditText) findViewById(R.id.GarajSayısı);
        mxm = (EditText) findViewById(R.id.mxm);

        createSpinner();

    }

    public boolean controlValue(){
        if(location.isSelected() || location.getSelectedItem().toString().compareTo("Şehir Seçiniz") == 0){
            return false;
        }
        if(uzaklık.isSelected() || uzaklık.getSelectedItem().toString().compareTo("Uzaklık Seçiniz") == 0){
            return false;
        }
        if(state.isSelected() || state.getSelectedItem().toString().compareTo("Durum Seçiniz") == 0){
            return false;
        }
        if(odaSayısı.getText() == null || banyoSayısı.getText() == null || garajSayısı.getText() == null || mxm.getText() == null){
            return false;
        }
        return true;
    }

    public void createSpinner(){
        ArrayList<String> arrayList1 = new ArrayList<>();
        ArrayList<String> arrayList2 = new ArrayList<>();
        ArrayList<String> arrayList3 = new ArrayList<>();

        arrayList1.add("Durum Seçiniz");
        arrayList1.add("Unit");
        arrayList1.add("Townhouse");
        arrayList1.add("House");
        arrayList2.add("Şehir Seçiniz");
        arrayList3.add("Uzaklık Seçiniz");
        arrayList3.add("0");
        arrayList3.add("0 < x < 5");
        arrayList3.add("4 < x < 25");
        arrayList3.add("x > 25");

        String jStr = "{\"locations\":[\"banyule city council\",\"bayside city council\",\"boroondara city council\",\"brimbank city council\",\"cardinia shire council\",\"casey city council\",\"darebin city council\",\"frankston city council\",\"glen eira city council\",\"greater dandenong city council\",\"hobsons bay city council\",\"hume city council\",\"kingston city council\",\"knox city council\",\"macedon ranges shire council\",\"manningham city council\",\"maribyrnong city council\",\"maroondah city council\",\"melbourne city council\",\"melton city council\",\"mitchell shire council\",\"monash city council\",\"moonee valley city council\",\"moorabool shire council\",\"moreland city council\",\"nillumbik shire council\",\"port phillip city council\",\"stonnington city council\",\"whitehorse city council\",\"whittlesea city council\",\"wyndham city council\",\"yarra city council\",\"yarra ranges shire council\"]}";
        JSONObject js = null;
        JSONArray jArr = null;
        try {
            js = new JSONObject(jStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jArr = js.getJSONArray("locations");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int length = jArr.length();
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                try{
                    arrayList2.add(jArr.getString(i));
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList1);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(arrayAdapter);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList2);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        location.setAdapter(arrayAdapter2);
        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList3);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        uzaklık.setAdapter(arrayAdapter3);
    }

    public String getDistance(){
        if(!uzaklık.isSelected()){
            System.out.println(uzaklık.getSelectedItem().toString());
            if(uzaklık.getSelectedItem().toString().compareTo("0") == 0){
                return "0";
            }
            else if(uzaklık.getSelectedItem().toString().compareTo("0 < x < 5") == 0){
                return "1";
            }
            else if(uzaklık.getSelectedItem().toString().compareTo("4 < x < 25") == 0){
                return "2";
            }
            else if(uzaklık.getSelectedItem().toString().compareTo("x > 25") == 0){
                return "3";
            }
            else{
                return null;
            }
        }
        else{
            return null;
        }
    }

    public String getType(){
        if(!state.isSelected()){
            if(state.getSelectedItem().toString().compareTo("House") == 0){
                return "h";
            }
            else if(state.getSelectedItem().toString().compareTo("Townhouse") == 0){
                return "t";
            }
            else if(state.getSelectedItem().toString().compareTo("Unit") == 0){
                return "u";
            }
            else{
                return null;
            }
        }
        else{
            return null;
        }
    }

    public void makeJSON(String location, String type, String distance, String rooms, String bathroom, String car, String total){
        try{
            jObj.put("Locations" , location);
            jObj.put("Rooms",rooms);
            jObj.put("Distance",distance);
            jObj.put("Bathroom",bathroom);
            jObj.put("Car",car);
            jObj.put("Total",total);
            jObj.put("Type",type);
        }
        catch(Exception e){
            System.out.println("Error: " + e );
        }
        Log.i("",jObj.toString());
    }

    public void hesapla(View view){
        String distance = getDistance();
        String type = getType();
        if(!controlValue()){
            DisplayMessage("Miss Value");
            return;
        }
        makeJSON(location.getSelectedItem().toString(),type,distance,odaSayısı.getText().toString(),banyoSayısı.getText().toString(),garajSayısı.getText().toString(),mxm.getText().toString());
        new MakeNetworkCall().execute("http://10.0.2.2:5000/predict_home_price","Post");
    }


    InputStream ByPostMethod (String ServerURL){
        InputStream DataInputStream = null;
        try{
            URL url = new URL(ServerURL);

            HttpURLConnection connection  = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestProperty("charset", "utf-8");
            connection.setUseCaches(false);
            DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
            dos.writeBytes(jObj.toString());
            dos.flush();
            dos.close();

            int response = connection.getResponseCode();
            if(response == HttpURLConnection.HTTP_OK){
                DataInputStream = connection.getInputStream();
            }
        }
        catch(Exception e){
            Log.e("Error caught", "Error in get data", e);
        }
        return DataInputStream;
    }

    String ConvertStreamToString(InputStream stream){
        InputStreamReader isr = new InputStreamReader(stream);
        BufferedReader reader = new BufferedReader(isr);
        StringBuilder response = new StringBuilder();

        String line = null;
        try{
            while ((line = reader.readLine()) != null){
                response.append(line);
            }
        }
        catch (IOException e){
            Log.e("Error caught","Error in ConvertStreamToString",e);
        }
        catch(Exception e){
            Log.e("Error caught","Error in ConvertStreamToString",e);
        }
        finally {
            try{
                stream.close();
            }
            catch (IOException e){
                Log.e("Error caught","Error in ConvertStreamToString",e);
            }
            catch (Exception e){
                Log.e("Error caught","Error in ConvertStreamToString",e);
            }
        }
        return response.toString();
    }

    public void DisplayMessage(String s){
        value.setText(s);
        System.out.println(s);
    }

    private class MakeNetworkCall extends AsyncTask <String, Void, String>{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            System.out.println("Please Wait ...");
        }

        @Override
        protected String doInBackground(String... arg) {
            InputStream is = null;
            String URL = "http://10.0.2.2:5000/predict_home_price";
            Log.d("Error Caught","Url" + URL);
            String res = "";

            is = ByPostMethod(URL);
            if (is != null){
                res = ConvertStreamToString(is);
            }
            else{
                res = "Something went wrong";
            }
            return res;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            DisplayMessage(s);
            Log.d("Error Caught","Url" + s);
        }
    }
}
