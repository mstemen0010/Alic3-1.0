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
import java.net.URL;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 *
 * @author MStemen
 */
public class AliceProtoClient31 extends AliceProtoClient implements AliceProtoClientInterface {

     GetMethod aliceGetMethod;
    //  HttpResponse aliceResponse = null;

   
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

    @Override
    public void doPost(String urlString) {
//        alicePost  = new HttpPost( urlString );
        // aliceResponse = this.execute(alicePost)xcute( alicePost );
    }

    @Override
    public void doGet(String urlString) {
        URL urlToGet = null;


        aliceGetMethod = new GetMethod(urlString);
        Object rawClient = getAliceClientType().getClient();
        if (HttpClient.class.isInstance(rawClient)) {
            HttpClient client = HttpClient.class.cast(rawClient);
            int statusCode;
            try {
                statusCode = client.executeMethod(aliceGetMethod);
                if (statusCode == HttpStatus.SC_OK) {
                    InputStream repIS = aliceGetMethod.getResponseBodyAsStream();
                    aliceClientStatus = aliceGetMethod.getStatusText();
                    InputStream conIS = aliceGetMethod.getResponseBodyAsStream();
                    StringWriter content = new StringWriter();
                    BufferedReader br;
                    br = new BufferedReader(new InputStreamReader(repIS));
                    String lineRead;
                    while ((lineRead = br.readLine()) != null) {
                        content.append(lineRead);
                    }
                    aliceClientContent = content.toString();
                }
            } catch (IOException ex) {
                Logger.getLogger(AliceProtoClient31.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @Override
    public String getReponseAsString() {
        return aliceClientResponse;
    }

    public String getContentAsString() {
        return aliceClientContent;
    }

    public String getStatusAsString() {
        return aliceClientStatus;
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
    
    
    @Override
    public void release()
    {
        if( this.aliceGetMethod != null )
        {
            this.aliceGetMethod.releaseConnection();
        }
    }

    @Override
    public AliceProtoClientInterface spinAlice(AliceClientType type) {
        AliceProtoClientInterface retObj = null;
        if( type.equals(this.myClientType))
        {
            retObj = this;
        }
        return retObj;
    }
}