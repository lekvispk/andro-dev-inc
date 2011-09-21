package pe.org.cnl.movil.directorio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

public class DirectorioNotarialActivity extends Activity implements CompoundButton.OnCheckedChangeListener {
	
	private EditText txtNombre = null;
	private Button btnHola = null;
	private Spinner cbDistrito = null;
	private CheckBox chkSisev = null;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       
        try {
              	
        	txtNombre = (EditText)findViewById(R.id.txtNotario);
        	cbDistrito = (Spinner)findViewById(R.id.cbDistrito);
    		btnHola = (Button)findViewById(R.id.btnBuscar);
    		chkSisev = (CheckBox)findViewById(R.id.chkSisev);
    		
    		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource( this, R.array.distrito_label, android.R.layout.simple_spinner_item );
    		adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
    		cbDistrito.setAdapter(adapter);
    		chkSisev.setOnCheckedChangeListener(this);
    		 
            btnHola.setOnClickListener(new View.OnClickListener() {
    			public void onClick(View v) {
    				Intent intent = new Intent(DirectorioNotarialActivity.this, FrmListado.class);
    		        Bundle bundle = new Bundle();
    		        String selectedVal = getResources().getStringArray(R.array.distrito_id)[cbDistrito.getSelectedItemPosition()];
    		        bundle.putString("nombre", txtNombre.getText().toString() );
                    bundle.putString("distrito", selectedVal  + "" );
                    bundle.putString("sisev", (chkSisev.isChecked() ? 1 : 0) + "" );
                    Log.d("CNL", chkSisev.isChecked() +  " CHECK = "+(chkSisev.isChecked() ? 1 : 0) );
                    intent.putExtras(bundle);
                    startActivity(intent);
    			}
    		});
            
		} catch (Exception e) {
			e.printStackTrace();
			//txtNombre.setText("Error : " + e.getMessage());
		}        
		
    }
    
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
    	if(isChecked){
    		Log.d("CNL","CHEKADO");
    	}else{
    		Log.d("CNL","NOOO CHEKADO");
    	}
    }
    
    
}