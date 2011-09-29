package pe.org.cnl.movil.persistence;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NotasDbAdapter {
 
    private static String DATABASE_NAME="notarios";
    private static int 	DATABASE_VERSION =8;
    private static String  BD_TABLA = "notarios";
    public static final String KEY_ROWID = "_id";
    private static String DATABASE_CREATE = "CREATE TABLE " + BD_TABLA +" ( " + KEY_ROWID +" INTEGER primary key autoincrement,  de_comp TEXT, no_nota TEXT, de_dire TEXT, de_hora_nota TEXT, nu_tele_nota TEXT, de_mail_nota TEXT, de_url_nota TEXT , co_ubig TEXT , sisev INTEGER , latitud TEXT , longitud TEXT)";
    
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    
    private final Context mCtx;

	private static class DatabaseHelper extends SQLiteOpenHelper {
 
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
 
        @Override
        public void onCreate(SQLiteDatabase db){
        	 db.execSQL(DATABASE_CREATE);

       	  db.execSQL(" insert into notarios (" + KEY_ROWID +",de_comp, no_nota, de_dire, de_hora_nota, nu_tele_nota,de_mail_nota, de_url_nota,co_ubig, sisev, latitud , longitud ) values('143','DOCTORA MIRYAN ROSALVA ACEVEDO MENDOZA','ACEVEDO MENDOZA MIRYAN ROSALVA','AV. JAVIER PRADO OESTE N°850 MAGDALENA DEL MAR','De Lunes a Viernes de 8:30 a 6:30 pm. Sábado de 9:00 a 1:00 pm','460-6887/460-5005','miryanacevedo@notariaacevedomendoza.com','www.notariaacevedo.com','150117', 1,-12.093343,-77.059908) ; ");//,
       	 
        }
 
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        	Log.w("CNL", "Upgrading database from version " + oldVersion + " to "  + newVersion + ", which will destroy all old data");
        	db.execSQL("DROP TABLE IF EXISTS notarios");
            onCreate(db);
        }
    }
 
    /**
     * Constructor - pasa el contexto para poder abrir o crear la DB
     */
    public NotasDbAdapter(Context ctx) {
    	this.mCtx = ctx; 
    }
 
    /**
     * Abre la base de datos de notas. Si no puede abrirla, la crea. Si no se puede
     * lanza una excepcion
     */
    public NotasDbAdapter open() throws SQLException { 
    	mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }
 
    public void close() {  
    	mDbHelper.close(); 
    }
 
   
    /**
     * Devuelve un Cursor apuntando a la lista de todas las notas
     */
    public Cursor fetchAllNotarios() { 
    	String[] columns = {KEY_ROWID ,"de_comp","no_nota","de_dire","de_hora_nota","nu_tele_nota","de_mail_nota","de_url_nota","co_ubig","latitud","longitud"};
        Cursor c = mDb.query(BD_TABLA, columns, null, null, null, null, " no_nota asc " );
        if (c != null) {  
           Log.d("CNL", "entregando el cursor");
           return c;
        }
        Log.d("CNL", "CURSOR NULL");
        return null;
    }
 
    public List<String> listAllNotarios() { 
    	String[] columns = {KEY_ROWID ,"de_comp","no_nota","de_dire","de_hora_nota","nu_tele_nota","de_mail_nota","de_url_nota","co_ubig","latitud","longitud"};
    	List<String> lista = new ArrayList<String>();
        Cursor c = mDb.query(BD_TABLA, columns, null, null, null, null, " no_nota asc " );
        if (c != null) {         
           //int columnaCodigoIndice = c.getColumnIndexOrThrow(KEY_ROWID);
           int columnaNotariaIndice = c.getColumnIndexOrThrow("no_nota");
           
           int i = 1;           
           while (c.moveToNext()){
              //int valorColumnaCodigo = c.getInt(columnaCodigoIndice);
              String valorColumnaNotaria = c.getString(columnaNotariaIndice);
              lista.add(  valorColumnaNotaria );
              i++;
           }
           
        }
        Log.d("CNL", "entregando lista con " + lista.size() + " elementos" );
        return lista;
    }
    
    /**
     * Devuelve un Cursor apuntando a la nota con el rowId dado
     */
    public Cursor fetchNotario(long rowId, String notario,String distrito,Integer sisev) throws SQLException { 
    	String[] columns = {KEY_ROWID ,"de_comp","no_nota","de_dire","de_hora_nota","nu_tele_nota","de_mail_nota","de_url_nota","sisev","co_ubig","latitud","longitud"};
    StringBuilder where = new StringBuilder();
    boolean wre = false;
    if (notario!= null && !notario.equals("")) {
    	where.append(" no_nota like '" +notario + "%' ");
    	wre = true;
    }
    if( distrito!= null && !distrito.equals("00") ){
    	if(wre){ where.append(" and ") ; }
    	where.append( "  co_ubig like '%" +distrito + "' " );
    	wre = true;
    }    
    Log.d("CNL","CHECK = "+sisev);
    if(  sisev > 0  ){
    	if(wre){ where.append(" and ") ; }
    	where.append( "  sisev = 1 " );
    }  
    Log.d( "CNL", where.toString());
    Cursor c = mDb.query(BD_TABLA, columns, where.toString() , null, null, null, " no_nota asc " );
    if (c != null) {  
       Log.d("CNL", "entregando el cursor");
       return c;
    }
    Log.d("CNL", "CURSOR NULL");
    return null; }
//PAINO (-12.102506,-77.024648)    
}