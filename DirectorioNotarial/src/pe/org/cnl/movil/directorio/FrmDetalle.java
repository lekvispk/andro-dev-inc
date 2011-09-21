package pe.org.cnl.movil.directorio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class FrmDetalle extends Activity{
	
	private static final String TAG = "FrmDetalle";
	private ImageButton btnMapa = null;
	String lon =""; 
    String lat = "";
    String dir = "";
    
	@Override
    public void onCreate(Bundle savedInstanceState) {		
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.frmdetalle);
        Log.d(TAG, "inicia el frmDetalle");
        btnMapa = (ImageButton)findViewById(R.id.btnMapa);
        TextView txtMensaje = (TextView)findViewById(R.id.lblDetalle);
        TextView lbldireccion = (TextView)findViewById(R.id.lblDireccion);
        TextView lblhorario = (TextView)findViewById(R.id.lblHorario);
        TextView lblfono = (TextView)findViewById(R.id.lblTelefono);
        TextView lblmail = (TextView)findViewById(R.id.lblEmail);
        TextView lblweb = (TextView)findViewById(R.id.lblWeb);
        
        Bundle bundle = getIntent().getExtras();
       
        StringBuilder texto = new StringBuilder();
        texto.append("NOTARÍA  " + bundle.getString("notaria")+"\n");
        texto.append("" + bundle.getString("notario")+"\n");
        txtMensaje.setText(texto.toString());
        lbldireccion.setText(bundle.getString("direccion") + " - " + bundle.getString("distrito"));
        lblhorario.setText(bundle.getString("horario"));
        lblfono.setText(bundle.getString("telefono"));
        lblmail.setText(bundle.getString("email"));
        lblweb.setText(bundle.getString("web"));
        lon = bundle.getString("longitud") ; 
        lat = bundle.getString("latitud");
        dir = bundle.getString("direccion");
        
        Log.d(TAG, "colcada la indormacion");
        btnMapa.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(FrmDetalle.this, FrmMapa.class);
		        Bundle bundle = new Bundle();
		        bundle.putString("longitud", lon );//-77024074//-77024344
		        bundle.putString("latitud", lat );//-12102601//-12101967
		        bundle.putString("direccion", dir);
		        bundle.putString("zoom", "17");
                Log.d("CNL", "pasandole parametros al mapa" );
                intent.putExtras(bundle);
                startActivity(intent);
                //http://maps.google.com/maps?q=-12.102601,-77.024074&hl=es&ll=-12.102283,-77.023816&spn=0.002009,0.002411&sll=-12.102606,-77.024117&sspn=0.004018,0.004823&vpsrc=6&gl=pe&t=m&z=19
			}
		});
        
    }
	
}

