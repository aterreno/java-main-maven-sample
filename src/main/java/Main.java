import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet("http://json.org/");
        System.out.println(httpget.getURI());        
        httpget.addHeader("Content-Type", "application/json");
        httpget.addHeader("Accept", "application/json");
        HttpResponse response = httpclient.execute(httpget);
        System.out.println(response.getStatusLine());
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            InputStream stream = entity.getContent();
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                System.out.println(reader.readLine());
            } catch (RuntimeException ex) {
                httpget.abort();
                throw ex;
            } finally {
                stream.close();
            }
            httpclient.getConnectionManager().shutdown();
        }
    }
}