/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wms.alice.archtypes;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
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
 *
 * A 'ProtoClient' is a "typed wrapper". This allows the wrapped client to be
 * changed at "will" The Reflection device that handled this is contained as an
 * in enum; is this classes interface.
 */
public abstract class AliceProtoClient implements AliceProtoClientInterface {
    // extends DefaultHttpClient 

    final static protected ClientConnectionManager cm;
    final static protected HttpParams params;
    protected Object httpSpunClient = null;
    protected HttpPost alicePost = null;
    protected HttpResponse aliceResponse = null;
    protected AliceProtoClientInterface.AliceClientId myClientId = AliceProtoClientInterface.AliceClientId.Undefined;
    protected AliceProtoClientInterface.AliceClientType myClientType = AliceProtoClientInterface.AliceClientType.Undefined;
    ;
    static protected AliceClientType aliceClientZygote = AliceClientType.Undefined;
    public String aliceClientResponse = "";
    public String aliceClientContent = "";
    public String aliceClientStatus = "";
    static AliceProtoClient theOnlyInstance = null;
    static AliceProtoClientInterface aliceProtoClientInstanceInterface;
    static Class aliceProtoClientClass = null;
    static Object rawAdaptorClassObjeact = null;

    static {

        String userAgentString = "MSIE 6.0";
        params = new BasicHttpParams();
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        cm = new ThreadSafeClientConnManager(params, schemeRegistry);
        HttpProtocolParams.setUserAgent(params, userAgentString);
    }

//    public AliceProtoClient( ClientConnectionManager cm, HttpParams params )
//    {
//        super(cm, params);
//        setParams();
//    }
//    public AliceProtoClient() {
//        // construct and init
//        super(cm, params);
//        setParams();
//
//    }
//
//    private void setParams() {
//        this.getParams().setParameter("http.useragent", myClientType.friendlyName() );
//    }
//     abstract public void doPost( String urlString );
//    {
//        alicePost  = new HttpPost( urlString );
//        // aliceResponse = this.execute(alicePost)xcute( alicePost );
//    }
//   abstract public void doGet( String urlString );
//    {
//        HttpGet request = new HttpGet(urlString);
//        try {
//            aliceResponse = this.execute(request);
//            System.out.println("Response: " +  aliceResponse.toString() );
//        } catch (IOException ex) {
//            Logger.getLogger(AliceProtoClient.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    @Override
    public AliceProtoClientInterface getInterface() {
        return this;
    }

    public static AliceProtoClientInterface getInstance(Class adaptorClass, AliceClientType aliceClientInstanceType) {

        aliceClientZygote = aliceClientInstanceType;

        if (adaptorClass != aliceProtoClientClass) {
            aliceProtoClientClass = adaptorClass;
            try {
;                rawAdaptorClassObjeact = adaptorClass.newInstance(); //this creates an instance of the subclass;
                if (rawAdaptorClassObjeact != null) {

                    AliceProtoClient tClient = AliceProtoClient.class.cast(rawAdaptorClassObjeact);
                    aliceProtoClientInstanceInterface = tClient.getInterface();
                }
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(AliceProtoClient.class.getName()).log(Level.SEVERE, null, ex);
            }

            aliceProtoClientInstanceInterface.setupAliceProtoClient(aliceClientInstanceType);
            return aliceProtoClientInstanceInterface;
        }
        try {
            if (adaptorClass != null) {
                aliceProtoClientClass = adaptorClass;
                rawAdaptorClassObjeact = adaptorClass.newInstance();//this creates an instance of the subclass
                if (rawAdaptorClassObjeact != null) {

                    AliceProtoClient tClient = AliceProtoClient.class.cast(rawAdaptorClassObjeact);
                    aliceProtoClientInstanceInterface = tClient.getInterface();
                }
            }
        } catch (InstantiationException | IllegalAccessException ie) {
            Logger.getLogger(AliceProtoClient.class.getName()).log(Level.SEVERE, null, ie);
        }
        aliceProtoClientInstanceInterface.setupAliceProtoClient(aliceClientInstanceType);

        return aliceProtoClientInstanceInterface;
    }

//    @Override
//    public void setupAliceProtoClient(AliceClientType aliceClientInstanceType) {
//        myClientType = aliceClientInstanceType;
//        aliceClientZygote = aliceClientInstanceType;
//        httpSpunClient = aliceClientZygote.getClient();
//
//    }
//    @Override
//    public String getReponseAsString()
//    {
//        if( aliceResponse != null )
//        {
//            aliceClientResponse = aliceResponse.toString();
//        }
//        return aliceClientResponse;
//    }
//    
//    @Override
//    public String getContentAsString()
//    {
//        StringWriter content = new StringWriter();
//        BufferedReader br;
//        try {            
//            br = new BufferedReader( new InputStreamReader( aliceResponse.getEntity().getContent() ) );
//            String lineRead;
//            while( (lineRead = br.readLine()) != null )
//            {
//                content.append(lineRead);
//            }
//            
//        } catch (IOException | IllegalStateException ex) {
//            Logger.getLogger(AliceProtoClient.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        this.aliceClientContent = content.toString();
//        return aliceClientContent;
//    }
//    
//    
//    
//    public String getStatusAsString()
//    {
//        StringBuilder sb = new StringBuilder();
//        
//        if( this.aliceResponse != null )
//        {
//            sb.append( this.aliceResponse.getStatusLine().toString());
//            System.out.println("Status is: " + sb );
//        }
//        return sb.toString();
//    }
    @Override
    public Iterator<String> getIdentIds() {
        return this.myClientId.stringIterator();
    }

    @Override
    public Iterator<String> getIdentTypes() {
        return this.myClientType.stringIterator();
    }

    protected void protExecute(AliceProtoClientInterface.AliceClientType clientType, Method methodToExecute) {
        Class classToCastTo = null;

        switch (clientType) {
            case HttpClient31:

                break;

            case HttpClient42:

                break;

        }
        //super.e
    }

    @Override
    public AliceClientType getAliceClientType() {
        return aliceClientZygote;
    }

    @Override
    public AliceClientId getAliceClientId() {
        return this.myClientId;
    }
    
    @Override
    public String getClientType() {
        return this.myClientType.name();
    }

    @Override
    public String getClientId() {
        return this.myClientId.name();
    }
    
    @Override
    public void setClientId( AliceClientId newId )
    {
        this.myClientId = newId;
        System.out.println("Client ID is now: " + newId.friendlyName );
    }
    
    @Override
    public void setClientType( AliceClientType newType )
    {
        this.myClientType = newType;
        System.out.println("Client ID is now: " + newType.friendlyName );
    }
}
///// OLD CODE BELOW THIS LINE 
///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package alice.archtypes.apachecommonhttpclient;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.StringWriter;
//import java.util.Iterator;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.conn.ClientConnectionManager;
//import org.apache.http.conn.scheme.PlainSocketFactory;
//import org.apache.http.conn.scheme.Scheme;
//import org.apache.http.conn.scheme.SchemeRegistry;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
//import org.apache.http.params.BasicHttpParams;
//import org.apache.http.params.HttpParams;
//import org.apache.http.params.HttpProtocolParams;
//
///**
// *
// * @author MStemen
// */
//public class AliceProtoClient extends DefaultHttpClient implements AliceProtoClientInterface {
//    final static private ClientConnectionManager cm;
//    final static private HttpParams params;
//    
//    private HttpPost alicePost =  null;
//    private HttpResponse aliceResponse = null;
//    AliceClientId myClientId = AliceClientId.Alice1;
//    AliceClientType myClientType = AliceClientType.HttpClient42;
//    static 
//    {
//        
//        String userAgentString = "MSIE 6.0";
//        params = new BasicHttpParams();
//        SchemeRegistry schemeRegistry = new SchemeRegistry();
//        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
//        cm = new ThreadSafeClientConnManager(params, schemeRegistry);
//        HttpProtocolParams.setUserAgent(params, userAgentString) ;       
//    }
//    public AliceProtoClient() {
//        // construct and init
//        super(cm, params);
//        setParams();
//
//    }
//
//    protected void setParams() {
//        this.getParams().setParameter("http.useragent", myClientType.friendlyName() );
//    }
//    
//    public void doPost( String urlString )
//    {
//        alicePost  = new HttpPost( urlString );
//        // aliceResponse = this.execute(alicePost)xcute( alicePost );
//    }
//   
//    public void doGet( String urlString )
//    {
//        HttpGet request = new HttpGet(urlString);
//        try {
//            aliceResponse = this.execute(request);
//            System.out.println("Response: " +  aliceResponse.toString() );
//        } catch (IOException ex) {
//            Logger.getLogger(AliceProtoClient.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    
//    public String getReponseAsString()
//    {
//        return aliceResponse.toString();
//    }
//    
//    public String getContentAsString()
//    {
//        StringWriter content = new StringWriter();
//        BufferedReader br;
//        try {            
//            br = new BufferedReader( new InputStreamReader( aliceResponse.getEntity().getContent() ) );
//            String lineRead;
//            while( (lineRead = br.readLine()) != null )
//            {
//                content.append(lineRead);
//            }
//            
//        } catch (IOException | IllegalStateException ex) {
//            Logger.getLogger(AliceProtoClient.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        return content.toString();
//    }
//    
//    public String getStatusAsString()
//    {
//        StringBuilder sb = new StringBuilder();
//        
//        if( this.aliceResponse != null )
//        {
//            sb.append( this.aliceResponse.getStatusLine().toString());
//            System.out.println("Status is: " + sb );
//        }
//        return sb.toString();
//    }
//    
//    public Iterator<String> getIdentIds()
//    {
//        return this.myClientId.stringIterator();        
//    }
//    
//     public Iterator<String> getIdentTypes()
//    {
//        return this.myClientType.stringIterator();       
//    }
//}