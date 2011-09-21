package pe.org.cnl.movil.directorio;

import pe.org.cnl.movil.persistence.NotasDbAdapter;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class FrmListado extends ListActivity  {

	 private Cursor mNotesCursor;
	 private NotasDbAdapter mDbHelper;
	 
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mDbHelper = new NotasDbAdapter(this);
        mDbHelper.open();
        Bundle bundle = getIntent().getExtras();
        Log.d("CNL","Llamada a la carga");
        cargaCursor(bundle);

        ListView lv = getListView();
        lv.setTextFilterEnabled(true);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener  () {
	          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		            
	        	  Cursor c = mNotesCursor;
	        	  c.moveToPosition(position);
	        	  Log.d( "CNL", c.getString(c.getColumnIndexOrThrow("no_nota")) );
	        	  
	        	  	Log.i("CNL", "Seleccionado!!");
	  				Intent intent = new Intent(FrmListado.this, FrmDetalle.class);
	  		        Bundle bundle = new Bundle();
	                bundle.putString("notaria", c.getString(c.getColumnIndexOrThrow("no_nota")) );
	                bundle.putString("notario", c.getString(c.getColumnIndexOrThrow("de_comp")) );
	                bundle.putString("direccion", c.getString(c.getColumnIndexOrThrow("de_dire")) );
	                
	                String d = c.getString(c.getColumnIndexOrThrow("co_ubig"));
	                String dis = "";
	                int pos = 0 ;
	                for(String codDis : getResources().getStringArray(R.array.distrito_id)){
	                	if( d.substring(4, d.length()).equals(codDis)){	                		
	                		dis = getResources().getStringArray(R.array.distrito_label)[pos];
	                		break;
	                	}
	                	pos++;
	                }
	                
	                bundle.putString("distrito", dis );
	                bundle.putString("telefono", c.getString(c.getColumnIndexOrThrow("nu_tele_nota")) );
	                bundle.putString("horario", c.getString(c.getColumnIndexOrThrow("de_hora_nota")) );
	                bundle.putString("email", c.getString(c.getColumnIndexOrThrow("de_mail_nota")) );
	                bundle.putString("web", c.getString(c.getColumnIndexOrThrow("de_url_nota")) );
	                bundle.putString("latitud", c.getString(c.getColumnIndexOrThrow("latitud")) );
	                bundle.putString("longitud", c.getString(c.getColumnIndexOrThrow("longitud")) );
	                
	                intent.putExtras(bundle);
	                startActivity(intent);
                  
	          }
        });
        
    }
	
	
	public void cargaSimple(){
		 setListAdapter(new ArrayAdapter<String>(this, R.layout.frmlistado, mDbHelper.listAllNotarios() ));		 
	}
	
	public void cargaCursor(Bundle bundle){
		Log.d("CNL"," LISTAADO CARGARNOTAS  chek="+bundle.getString("sisev") );
		mNotesCursor = mDbHelper.fetchNotario(0, bundle.getString("nombre"), bundle.getString("distrito"),Integer.parseInt( bundle.getString("sisev") ));
        startManagingCursor(mNotesCursor);        
        String[] from = new String[]{"no_nota"};
        int[] to = new int[]{R.id.list};    
        SimpleCursorAdapter notes = new SimpleCursorAdapter(this, R.layout.frmlistado, mNotesCursor, from, to);
        setListAdapter(notes);       
	}
}
