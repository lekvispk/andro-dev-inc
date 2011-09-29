package pe.org.cnl.movil.directorio;

import java.util.List;

import android.os.Bundle;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class FrmMapa extends MapActivity{

	private static final String TAG ="MAPA";
	private MapView mapa = null;
	private MapController controlMapa = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frmmapa);
                
        mapa = (MapView)findViewById(R.id.mapacnl);
        Log.d(TAG,"carga mapa");
        
        controlMapa = mapa.getController();
        mapa.setBuiltInZoomControls(true);
        
        Bundle bundle = getIntent().getExtras();
        
        int latitude = (int) (Double.parseDouble(bundle.getString("latitud")) * 1E6);
        int longitude = (int) (Double.parseDouble(bundle.getString("longitud")) * 1E6);
        Integer zoom = Integer.parseInt(  bundle.getString("zoom") );
        
        //GeoPoint loc =  new GeoPoint(lat.intValue(), lon.intValue()); 
        GeoPoint loc =  new GeoPoint( latitude , longitude );
        controlMapa.setCenter(loc);
        controlMapa.setZoom(zoom);
     
        //Añadimos la capa de marcadores
        List<Overlay> capas = mapa.getOverlays();
        OverlayMapa om = new OverlayMapa();
        om.setLatitud( Double.parseDouble(bundle.getString("latitud")) * 1E6 );
        om.setLongitud(  Double.parseDouble(bundle.getString("longitud"))* 1E6 );
        om.setDireccion(bundle.getString("direccion"));
        capas.add(om);
        mapa.postInvalidate();
        
        controlMapa.animateTo(loc);

    }
	
	@Override
	protected boolean isRouteDisplayed() {
		 return true;
	}
	
}
