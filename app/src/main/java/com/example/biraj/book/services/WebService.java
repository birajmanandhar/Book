package com.example.biraj.book.services;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WebService {

    public static final String URL = "http://10.0.2.2/Book";
    public static final String REGISTER_URL = URL +"/register.php";
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static OkHttpClient client = new OkHttpClient();

    public static String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    /*private class LongOperation extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String data = params[0];
            String result = null;
            try {
                result = new WebService().post(URL, data);
            } catch (IOException e) {
                return e.getMessage();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("biraj", "biraj " + result);
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    String temp = "";*/

}

    /* Old Deprecated method to make the web service job done.!
    Before sdk 22.
    public static String postInformation(String data) {
        try {
            JSONObject j = new JSONObject();
            j.put("engineer", "me");
            j.put("date", "today");
            j.put("fuel", "full");
            j.put("car", "mine");
            j.put("distance", "miles");

            // String url = "http://127.0.0.1/Webservice/test.php";
            String url = "http://10.0.2.2/Webservice/test.php";

            Map<String, String> kvPairs = new HashMap<String, String>();
            kvPairs.put("vehicle", j.toString());

            HttpResponse re = doPost(url, kvPairs);
            temp = EntityUtils.toString(re.getEntity());
            Log.d("Sending complete: ", "biraj Reply message " + temp);
            if (temp.compareTo("SUCCESS") == 0) {
                Toast.makeText(this, "Sending complete!", Toast.LENGTH_LONG).show();
            }
            return "";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public static HttpResponse doPost(String url, Map<String, String> kvPairs) throws ClientProtocolException,
            IOException {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);

        if (kvPairs != null && kvPairs.isEmpty() == false) {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(kvPairs.size());
            String k, v;
            Iterator<String> itKeys = kvPairs.keySet().iterator();

            while (itKeys.hasNext()) {
                k = itKeys.next();
                v = kvPairs.get(k);
                nameValuePairs.add(new BasicNameValuePair(k, v));
            }

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        }

        HttpResponse response;
        response = httpclient.execute(httppost);
        return response;
    }*/
