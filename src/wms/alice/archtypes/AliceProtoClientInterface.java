/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wms.alice.archtypes;

import static wms.alice.archtypes.AliceProtoClientInterface.AliceClientId.iterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.httpclient.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author MStemen
 */
public interface AliceProtoClientInterface {

    enum AliceClientType {

        Undefined("Undefined", null, null ),
        HttpClient31("HttpClient 3.1", HttpClient.class, AliceProtoClient31.class ),
        HttpClient42("HttpClient 4.2", DefaultHttpClient.class, AliceProtoClient42.class );
        String friendlyName;
        Class clientClass;
        Object currentClientObject;
        AliceProtoClient currentClient;
        Class adaptorClass;

        AliceClientType(String newFriendlyName, Class clientClass, Class adaptorClass ) {
            this.friendlyName = newFriendlyName;
            this.clientClass = clientClass;
            this.adaptorClass = adaptorClass;
            if (this.clientClass != null) {
                spinClient();
            }
        }
        
        

        public String friendlyName() {
            return this.friendlyName;
        }

        public static Iterator<AliceProtoClientInterface.AliceClientType> iterator() {
            return Arrays.asList(AliceProtoClientInterface.AliceClientType.values()).iterator();
        }

        public void setClassForType(AliceProtoClientInterface.AliceClientType type, Class clientclass) {
            this.clientClass = clientclass;

        }

        public AliceProtoClientInterface spinClient() {
            try {
                // use refecttion
                this.currentClientObject = this.clientClass.newInstance();
                this.currentClient = (AliceProtoClient) this.adaptorClass.newInstance();
            } catch (InstantiationException ex) {
                Logger.getLogger(AliceProtoClientInterface.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(AliceProtoClientInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return this.currentClient;
            
        }

        public Object getClient() {
            return this.currentClient;
        }

        public Iterator<String> stringIterator() {
            ArrayList<String> asStrings = new ArrayList<>();
            Iterator<AliceProtoClientInterface.AliceClientType> it = iterator();
            while (it.hasNext()) {
                asStrings.add(it.next().friendlyName);
            }
            return asStrings.iterator();
        }
        
        public static AliceProtoClientInterface.AliceClientType fromStringName( String idStringName )
        {
            AliceProtoClientInterface.AliceClientType retId = AliceProtoClientInterface.AliceClientType.Undefined;
            
            Iterator<AliceProtoClientInterface.AliceClientType> it = iterator();
            while( it.hasNext() )
            {
                AliceProtoClientInterface.AliceClientType testId = it.next();
                if( testId != null && testId.friendlyName.equals(  idStringName ))
                {   retId = testId;
                    
                    break;
                }
            }            
            return retId;            
        }  
        
        public Class getProtoClass()
        {
            return this.adaptorClass;
        }
        
        public Class getClientClass()
        {
            return this.clientClass;
        }
    }

    enum AliceClientId {

        Undefined( "Undefined" ),
        Alice1("Alice 1.0"),
        MSI6("MSI 6.0");
        String friendlyName;

        AliceClientId(String newFriendlyName) {
            this.friendlyName = newFriendlyName;
        }

        public String friendlyName() {
            return this.friendlyName;
        }

        static public Iterator<AliceProtoClientInterface.AliceClientId> iterator() {
            return Arrays.asList(AliceProtoClientInterface.AliceClientId.values()).iterator();
        }

        public Iterator<String> stringIterator() {
            ArrayList<String> asStrings = new ArrayList<>();
            Iterator<AliceProtoClientInterface.AliceClientId> it = iterator();
            while (it.hasNext()) {
                asStrings.add(it.next().friendlyName);
            }
            return asStrings.iterator();
        }
        
        public static AliceProtoClientInterface.AliceClientId fromStringName( String idStringName )
        {
            AliceProtoClientInterface.AliceClientId retId = AliceProtoClientInterface.AliceClientId.Undefined;
            
            Iterator<AliceProtoClientInterface.AliceClientId> it = iterator();
            while( it.hasNext() )
            {
                AliceProtoClientInterface.AliceClientId testId = it.next();
                if( testId != null && testId.friendlyName.equals(  idStringName ))
                {   retId = testId;
                    
                    break;
                }
            }            
            return retId;            
        }
    }
    
    

    public AliceProtoClientInterface getInterface();
    
    public Iterator<String> getIdentIds();
    
    public Iterator<String> getIdentTypes();
    
     public AliceProtoClientInterface.AliceClientId getAliceClientId();
     
    public AliceProtoClientInterface.AliceClientType getAliceClientType();

    public void setSpunClient(AliceProtoClientInterface.AliceClientType type, Object rawClient);
    
    public AliceProtoClientInterface spinAlice( AliceProtoClientInterface.AliceClientType type);
         
    public void setupAliceProtoClient(AliceProtoClientInterface.AliceClientType aliceClientInstanceType);

    public void spinSetTypedClient();

    public String getReponseAsString();

    public String getContentAsString();

    public String getStatusAsString();
    
    public String getClientType();
    
    public String getClientId();

    public void doPost(String urlString);

    public void doGet(String urlString);    
    
    public void setClientId( AliceProtoClientInterface.AliceClientId newId );
    
    public void setClientType( AliceProtoClientInterface.AliceClientType newType );
    
    public void release();
}
