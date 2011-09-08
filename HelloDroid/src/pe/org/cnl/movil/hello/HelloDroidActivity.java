package pe.org.cnl.movil.hello;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class HelloDroidActivity extends Activity {

	//
	private static final String NAMESPACE = "http://servicio.onp.plataforma.cnl.org.pe/";
	//private static String URL = "http://10.0.2.2:8080/plataforma/ServiciosColegio?wsdl";
	private static String URL = "http://190.216.169.23/plataforma/ServiciosColegio?wsdl";
	private static final String METHOD_NAME = "buscarAlertas";
	private static final String SOAP_ACTION =  "http://servicio.onp.plataforma.cnl.org.pe/buscarAlertas";
	private static final int TIMEOUT = 50000;
	private EditText txtNom,txtNotUsr, txtApPaterno ,txtApMaterno,txtDNI, txtRazSoc,txtRuc;
	private TextView lblResult;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    // This method is called at button click because we assigned the name to the
	// "On Click property" of the button
	public void buscarAlertaHandler(View view) {
		switch (view.getId()) {
		case R.id.btnEnviar:
				this.txtNom = (EditText) findViewById(R.id.txtNombre);
				this.txtNotUsr = (EditText) findViewById(R.id.txtNotUsr);
				this.txtApPaterno = (EditText) findViewById(R.id.txtPaterno);
				this.txtApMaterno = (EditText) findViewById(R.id.txtMaterno);
				this.txtDNI = (EditText) findViewById(R.id.txtDNI);
				this.txtRazSoc = (EditText) findViewById(R.id.txtRazSoc);
				this.txtRuc = (EditText) findViewById(R.id.txtRUC);
		    	this.lblResult = (TextView) findViewById(R.id.lblRes);
		    	
		        try {	
		        		lblResult.setText( "Cargando Cliente WS" );
		        		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);              
			            //enviando objetos simples
			            //request.addProperty("", txtNom.getText().toString());
			            SoapObject alerta = new SoapObject(NAMESPACE, "alertasRequest");
			            alerta.addProperty("apeMaterno", txtApMaterno.getText().toString());
			            alerta.addProperty("apePaterno", txtApPaterno.getText().toString());
			            alerta.addProperty("dni", txtDNI.getText().toString());
			            alerta.addProperty("nombre", txtNom.getText().toString());
			            alerta.addProperty("notarioCodigo", txtNotUsr.getText().toString());
			            alerta.addProperty("razonsocial", txtRazSoc.getText().toString());
			            alerta.addProperty("ruc", txtRuc.getText().toString());

			            PropertyInfo documentIdsPropertyInfo = new PropertyInfo();
			            documentIdsPropertyInfo.setName("alertasRequest");
			            documentIdsPropertyInfo.setValue(alerta);
			            documentIdsPropertyInfo.setType(alerta.getClass());
			            
			            request.addProperty( documentIdsPropertyInfo );

			            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); 
			            envelope.setOutputSoapObject(request);
			            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,TIMEOUT);
			            Log.d("TEST", URL );
			            Log.d("TEST", "httpTransportSE.call " + SOAP_ACTION );
		            	androidHttpTransport.call(SOAP_ACTION, envelope);
		            	Log.d("TEST", " Antes de la llamada");
		            	SoapObject so = (SoapObject)envelope.getResponse();
		            	StringBuilder mensaje = new StringBuilder(); 
		            	mensaje.append("RESPUESTA: [Cod = ")
		            	.append(Utiles.nullToBlank(so.getProperty("codigo")) )
		            	.append("]; [Msg = ")
		            	.append(Utiles.nullToBlank(so.getProperty("mensaje")))
		            	.append("]; [ Alertas = ") 
		            	.append((so.getPropertyCount()-2) + "]");
		            	for( int i = 2  ; i < so.getPropertyCount() ;i++ ){
		            		SoapObject prop = (SoapObject)so.getProperty(i);
		            		Log.d( "TEST",prop.toString());
		            		/*Log.d( "TEST","claseAlerta="+Utiles.nullToBlank(prop.getProperty("claseAlerta")));
		            		Log.d( "TEST","codigoAlerta="+Utiles.nullToBlank(prop.getProperty("codigoAlerta")));
		            		Log.d( "TEST","detalleAlerta="+Utiles.nullToBlank(prop.getProperty("detalleAlerta")));
		            		Log.d( "TEST","entidad="+Utiles.nullToBlank(prop.getProperty("entidad")));
		            		Log.d( "TEST","fechaAlerta="+Utiles.nullToBlank(prop.getProperty("fechaAlerta")));*/
		            		mensaje.append( "\n"+Utiles.nullToBlank(prop.getProperty("detalleAlerta")) );
		            	}
	            		Log.d( "TEST", mensaje.toString()  );
		            	lblResult.setText( mensaje  );
				} catch (Exception e) {
					e.printStackTrace();
					 lblResult.setText( e.getMessage());
				}
				
			break;
		}
	}
	
}