/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wms.alice.archtypes;

import wms.alice.archtypes.AliceProtoClient;
import wms.alice.archtypes.AliceProtoClientInterface;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.httpclient.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

/**
 *
 * @author MStemen
 */
public class AliceProtoClient42 extends AliceProtoClient implements AliceProtoClientInterface {

    private DefaultHttpClient client = null;
    private InputStream aliceStream = null;

    public AliceProtoClient42() {
        // construct and init
        Object rawClient = getAliceClientType().getClient();
        if (DefaultHttpClient.class.isInstance(rawClient)) {
            client = DefaultHttpClient.class.cast(rawClient);
            setParams();
        }
    }

    @Override
    public void setupAliceProtoClient(AliceClientType aliceClientInstanceType) {
        myClientType = aliceClientInstanceType;
        aliceClientZygote = aliceClientInstanceType;
        httpSpunClient = aliceClientZygote.getClient();
    }

    @Override
    public AliceProtoClientInterface getInterface() {
        return this;
    }

    private void setParams() {
        client.getParams().setParameter("http.useragent", myClientType.friendlyName());
    }

    public void doPost(String urlString) {
        alicePost = new HttpPost(urlString);
        // aliceResponse = this.execute(alicePost)xcute( alicePost );
    }

    public void doGet(String urlString) {
        HttpGet request = new HttpGet(urlString);
        Object rawClient = null;
        try {
            rawClient = this.getAliceClientType().getClientClass().newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(AliceProtoClient31.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(AliceProtoClient31.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (DefaultHttpClient.class.isInstance(rawClient)) {
            try {
                aliceResponse = client.execute(request);
                System.out.println("Response: " + aliceResponse.toString());
            } catch (IOException ex) {
                Logger.getLogger(AliceProtoClient42.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String getReponseAsString() {
        return aliceResponse.toString();
    }

    public String getContentAsString() {
        StringWriter content = new StringWriter();
        BufferedReader br;
        try {
            aliceStream = aliceResponse.getEntity().getContent();
            br = new BufferedReader(new InputStreamReader(aliceStream));
            String lineRead;
            while ((lineRead = br.readLine()) != null) {
                content.append(lineRead);
            }

        } catch (IOException | IllegalStateException ex) {
            Logger.getLogger(AliceProtoClient42.class.getName()).log(Level.SEVERE, null, ex);
        }

        return content.toString();
    }

    public String getStatusAsString() {
        StringBuilder sb = new StringBuilder();

        if (this.aliceResponse != null) {
            sb.append(this.aliceResponse.getStatusLine().toString());
            System.out.println("Status is: " + sb);
        }
        return sb.toString();
    }

    public Iterator<String> getIdentIds() {
        return this.myClientId.stringIterator();
    }

    public Iterator<String> getIdentTypes() {
        return this.myClientType.stringIterator();
    }

    @Override
    public void setSpunClient(AliceClientType type, Object rawClient) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void spinSetTypedClient() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void release() {
        if (aliceStream != null) {
            try {
                aliceStream.close();
            } catch (IOException ex) {
                Logger.getLogger(AliceProtoClient42.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public AliceProtoClientInterface spinAlice(AliceClientType type) {
        AliceProtoClientInterface retObj = null;
        if (type.equals(this.myClientType)) {
            retObj = this;
        }
        return retObj;
    }
}
