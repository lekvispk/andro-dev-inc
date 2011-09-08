package pe.org.cnl.movil.hello;



import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Utiles {

    public static final String RUTA_TEMPORAL = "/temp";
    
    public static String FORMATO_FECHA_LARGE="dd/MM/yyyy hh:mm:ss";
    public static String FORMATO_FECHA_CORTA="dd/MM/yyyy";
    public static String FORMATO_FECHA_CORTA_MYSQL="dd-MM-yyyy";
    
    
    /**
     * retorna una cadena vacia en caso de ser null
     */
    public static String nullToBlank(Object texto){
    	try {
    		if(texto==null){
                return "";
	        }
    		if(texto.toString().trim().equals("null")){
    			return "";
    		}
	        return texto.toString().trim();
		} catch (Exception e) {
			return "";
		}
            
    }
    
    public static Integer nullToZero(Object texto){
        try{
            if(texto == null)
                return Integer.valueOf(0);
        }catch(Exception e){
            return Integer.valueOf(0);
        }
        return Integer.valueOf(Integer.parseInt(texto.toString()));
    }
    
    public static Long nullToZeroL(Object texto){
        try{
            if(texto == null)
                return Long.valueOf(0);
            return Long.valueOf(texto.toString());
        }catch(Exception e){
            return Long.valueOf(0);
        }
    }
    
    /**
     * entrega un objetod el tipo GregorianCalendar con la fecha indicada
     * @param fecha texto a convertir en fecha
     * @param formato usar Utils.FORMATO_FECHA_CORTA o Utils.FORMATO_FECHA_LARGE
     * @return objeto gregoriancalendar con la fecha en el formato indicado
     * @throws Exception
     */
    public static GregorianCalendar stringToCalendar(String fecha,String formato) throws Exception{
            fecha=nullToBlank(fecha);
            GregorianCalendar gc = new GregorianCalendar();
            SimpleDateFormat df = new SimpleDateFormat(formato);
            gc.setTime(df.parse(fecha));
            return gc;
    }
    
    public static String CalendarToString(Calendar fecha,String formato) throws Exception{
    	if(nullToBlank(fecha).equals("")){
    		return "";
    	}
        SimpleDateFormat df = new SimpleDateFormat(formato);
        return df.format(fecha.getTime());
    }
    
    public static String DateToString(Date fecha,String formato) throws Exception{
    	if(nullToBlank(fecha).equals("")){
    		return "";
    	}
        SimpleDateFormat df = new SimpleDateFormat(formato);
        return df.format(fecha);
}
    
    public static String ValidaDate(String fecha,String formato) throws Exception{
        fecha=nullToBlank(fecha);
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat df = new SimpleDateFormat(formato);
        gc.setTime(df.parse(fecha));
        return df.format(gc.getTime());
    }
    
    public static Date stringToDate(String fecha,String formato){
            fecha=nullToBlank(fecha);
            GregorianCalendar gc = new GregorianCalendar();
            SimpleDateFormat df = new SimpleDateFormat(formato);
            try {
				gc.setTime(df.parse(fecha));
				return gc.getTime();
			} catch (ParseException e) {
				e.printStackTrace();
			}
            return null;
    }
    
    /**
     * Crea un archivo con extension jpg en la carpeta temporal del proyecto web para poder mostrarla en 
     * la pagina web
     * @param imagenBuffer InputStream que contiene la imagen
     * @param extra texto para incluir en el nombre (D: dedo; H:huella)
     * @param path ruta en donde se grabar� la imagen (ruta de la carpeta que contiene los html - webcontent / publichtml)
     * @return nombre de la imagen con ruta relativa para pintarla en el html
     * @throws Exception
     */
    public static String guardaImagenEnDisco(InputStream imagenBuffer,String extra,String path)throws Exception{
        File fichero =null;
        String nombre = "";
        try{
            Calendar fe = new GregorianCalendar();
            nombre =extra +fe.getTimeInMillis()+".jpg";            
            fichero = new File(path+"\\temp\\"+nombre);
            
            BufferedInputStream in = new BufferedInputStream(imagenBuffer);
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(fichero));
            
            byte[] bytes = new byte[8096];
            int len = 0;
            while ( (len = in.read( bytes )) > 0 ){
                out.write( bytes, 0, len );
            }            
            out.flush();
            out.close();
            in.close();            
            System.out.println("archivo grabado en : "+fichero.getAbsolutePath());
            
        }catch(Exception e){
            System.out.println("Error al escribir en disco " + e.getMessage());
        }
    return "temp/"+nombre;
    }
      
    /**
     * Copia un archivo en el sigiente especificado.
     * El metodo coge la ruta, asigna un nombre y escrive el archivo retornando 
     * el nomrbe y la ruta del archivo nuevo
     * @param in InputStream origen
     * @param ruta ruta destino
     * @param extension extension que llevara el archivo
     * @return nombre del nuevo archivo
     * @throws Exception
     */
    public static String copy(InputStream in, String ruta,String extension) throws Exception {
 	   GregorianCalendar now = new GregorianCalendar();
 	   String tiempo = ""+now.getTimeInMillis();
 	   String nombre = ruta +  File.separator + "up_"+tiempo+"."+extension;
       OutputStream out = new FileOutputStream(nombre);        
       byte[] buf = new byte[1024];
       int len;
       while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
       }
       in.close();
       out.close();
       return "up_"+tiempo+"."+extension;
    }
    
  
    public static String descripcionSexo(String sexo)throws Exception{
        if(nullToBlank(sexo).equals("F")){return "Femenino";}
        if(nullToBlank(sexo).equals("M")){return "Masculino";}
        return "";
    }
    
    
    /**
     * recibe de la base de datos un decimal de la forma <b>00000.00</b> o <b>00000</b> (sin decimales)<br/>
     * y lo formatea de la forma <b>00000.00</b>
     * @param numero
     * @return
     */
    public static String formatoDecimalPunto(String numero){
    	if(!nullToBlank(numero).equals("")){
    		numero=numero.trim();
    		String s = "#########.##";
    		DecimalFormatSymbols dformater_rules = new DecimalFormatSymbols ();
            dformater_rules.setDecimalSeparator('.');//con este simbolo separara a los decimales
            DecimalFormat decimalFormat = new DecimalFormat(s,dformater_rules);
            decimalFormat.setMaximumFractionDigits(2);
            decimalFormat.setMinimumFractionDigits(2);
            System.out.println(" 177 --> "+ numero);
            Double num  = Double.parseDouble(numero);
            return decimalFormat.format(num); 
    	}
    	return "";
    }

	public static String hashMd5(String palabra){
		
		MessageDigest md = null;
		StringBuffer h = null;
		try {
			md = MessageDigest.getInstance("MD5");
			byte[] b = md.digest(palabra.getBytes());
			int size = b.length;
			h = new StringBuffer(size);
			for (int i = 0; i < size; i++) {
				int u = b[i] & 255;
				if (u < 16) {
					h.append("0" + Integer.toHexString(u));
				} else {
					h.append(Integer.toHexString(u));
				}
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return h.toString();
	}

	/**
	 * Valida que la lista contenga un objeto no nulo en la posicion indicada
	 * @param lista lista en la cual uscar un objeto no nulo
	 * @param i posicion en la lista
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean validaNotNullenLista(List lista, int i) {
		try {
			if(lista.get(i)!=null){
				return true;
			}
			return false;
		} catch (IndexOutOfBoundsException e) {
			return false;
		} catch(Exception ez){
			return false;
		}
	}

	public static Calendar dateToCalendar(Date fecha,String formato) throws Exception{
    	if(nullToBlank(fecha).equals("")){
    		return null;
    	}
        SimpleDateFormat df = new SimpleDateFormat(formato);
        String text = df.format(fecha);
        return stringToCalendar(text,formato);
}
	
	public static int diasParaFecha(Date fechafin) {
		try {
			Calendar hoy = new GregorianCalendar();
			Calendar fin = dateToCalendar(fechafin,"dd/MM/yyyy");
			long diffMillis = fin.getTimeInMillis()-hoy.getTimeInMillis();
			Long diffDays = diffMillis/(24*60*60*1000);
			return diffDays.intValue();	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static String completaDigitos(Integer maximo, String original, String digito) {
		if(original.length() < maximo)
			return completaDigitos(maximo, digito+original, digito);
		return original;
	}
	
	public static String numberToText(BigDecimal numero , String moneda) {
		System.out.println(" BORRAR : "+numero.toString());
		return convertNumberToLetter(numero.toString(), moneda);
	}
	
	private static final String[] UNIDADES = { "", "UN ", "DOS ", "TRES ",
		"CUATRO ", "CINCO ", "SEIS ", "SIETE ", "OCHO ", "NUEVE ", "DIEZ ",
		"ONCE ", "DOCE ", "TRECE ", "CATORCE ", "QUINCE ", "DIECISEIS",
		"DIECISIETE", "DIECIOCHO", "DIECINUEVE", "VEINTE" };

	private static final String[] DECENAS = { "VENTI", "TREINTA ", "CUARENTA ",
		"CINCUENTA ", "SESENTA ", "SETENTA ", "OCHENTA ", "NOVENTA ",
		"CIEN " };

	private static final String[] CENTENAS = { "CIENTO ", "DOSCIENTOS ",
			"TRESCIENTOS ", "CUATROCIENTOS ", "QUINIENTOS ", "SEISCIENTOS ",
			"SETECIENTOS ", "OCHOCIENTOS ", "NOVECIENTOS " };
	
	/**
	 * Convierte a letras un numero de la forma $123,456.32 (StoreMath)
	 * <p>
	 * Creation date 5/06/2006 - 10:20:52 AM
	 * </p>
	 * @param number Numero en representacion texto
	 * @return Numero en letras
	 * @since 1.0
	 */
	public static String convertNumberToLetter(String number,String moneda) {
		return convertNumberToLetter(Double.parseDouble(number), moneda);
	}

	/**
	 * Convierte un numero en representacion numerica a uno en representacion de
	 * texto. El numero es valido si esta entre 0 y 999'999.999
	 * <p>
	 * Creation date 3/05/2006 - 05:37:47 PM
	 * 
	 * @param number
	 *            Numero a convertir
	 * @return Numero convertido a texto
	 * @throws NumberFormatException
	 *             Si el numero esta fuera del rango
	 * @since 1.0
	 */
	public static String convertNumberToLetter(double number,String moneda)
			throws NumberFormatException {
		String converted = new String();

	// Validamos que sea un numero legal
	double doubleNumber = number;
	
	if (doubleNumber > 999999999)
		throw new NumberFormatException(
				"El numero es mayor de 999'999.999, "
						+ "no es posible convertirlo");

	String splitNumber[] = String.valueOf(doubleNumber).replace('.', '#')
			.split("#");

	// Descompone el trio de millones - �SGT!
	int millon = Integer.parseInt(String.valueOf(getDigitAt(splitNumber[0],
			8))
			+ String.valueOf(getDigitAt(splitNumber[0], 7))
			+ String.valueOf(getDigitAt(splitNumber[0], 6)));
	if (millon == 1)
		converted = "UN MILLON ";
	if (millon > 1)
		converted = convertNumber(String.valueOf(millon)) + "MILLONES ";

	// Descompone el trio de miles - �SGT!
	int miles = Integer.parseInt(String.valueOf(getDigitAt(splitNumber[0],
			5))
			+ String.valueOf(getDigitAt(splitNumber[0], 4))
			+ String.valueOf(getDigitAt(splitNumber[0], 3)));
	if (miles == 1)
		converted += "MIL ";
	if (miles > 1)
		converted += convertNumber(String.valueOf(miles)) + "MIL ";

	// Descompone el ultimo trio de unidades - �SGT!
	int cientos = Integer.parseInt(String.valueOf(getDigitAt(
			splitNumber[0], 2))
			+ String.valueOf(getDigitAt(splitNumber[0], 1))
			+ String.valueOf(getDigitAt(splitNumber[0], 0)));
	if (cientos == 1)
		converted += "UN";

	if (millon + miles + cientos == 0)
		converted += "CERO";
	if (cientos > 1)
		converted += convertNumber(String.valueOf(cientos));

//	converted += moneda; 

	// Descompone los centavos - Camilo
	int centavos = Integer.parseInt(String.valueOf(getDigitAt(
			splitNumber[1], 2))
			+ String.valueOf(getDigitAt(splitNumber[1], 1))
			+ String.valueOf(getDigitAt(splitNumber[1], 0)));
	if (centavos < 1)
                converted += " CON 00";//converted += " CON UN CENTAVO";
        if (centavos == 1)
		converted += " CON 01";//converted += " CON UN CENTAVO";
	if (centavos > 1 && centavos < 10)
		converted += " CON 0" + centavos;//converted += " CON 0" + convertNumber(String.valueOf(centavos));
	if (centavos > 1)
		converted += " CON " + centavos;
	converted += "/100 ";//+ " CENTAVOS";
	//TODO poner aqui la moneda
	converted += moneda.toUpperCase();
	return converted;
}

/**
 * Convierte los trios de numeros que componen las unidades, las decenas y
 * las centenas del numero.
 * <p>
 * Creation date 3/05/2006 - 05:33:40 PM
 * 
 * @param number
 *            Numero a convetir en digitos
 * @return Numero convertido en letras
 * @since 1.0
 */
private static String convertNumber(String number) {
	if (number.length() > 3)
		throw new NumberFormatException(
				"La longitud maxima debe ser 3 digitos");

	String output = new String();
	if (getDigitAt(number, 2) != 0)
		output = CENTENAS[getDigitAt(number, 2) - 1];

	int k = Integer.parseInt(String.valueOf(getDigitAt(number, 1))
			+ String.valueOf(getDigitAt(number, 0)));

	if (k <= 20)
		output += UNIDADES[k];
	else {
		if (k > 30 && getDigitAt(number, 0) != 0)
			output += DECENAS[getDigitAt(number, 1) - 2] + "Y "
					+ UNIDADES[getDigitAt(number, 0)];
		else
			output += DECENAS[getDigitAt(number, 1) - 2]
					+ UNIDADES[getDigitAt(number, 0)];
	}

	// Caso especial con el 100
	if (getDigitAt(number, 2) == 1 && k == 0)
		output = "CIEN";

	return output;
}

/**
 * Retorna el digito numerico en la posicion indicada de derecha a izquierda
 * <p>
 * Creation date 3/05/2006 - 05:26:03 PM
 * 
 * @param origin
 *            Cadena en la cual se busca el digito
 * @param position
 *            Posicion de derecha a izquierda a retornar
 * @return Digito ubicado en la posicion indicada
 * @since 1.0
 */
private static int getDigitAt(String origin, int position) {
	if (origin.length() > position && position >= 0)
		return origin.charAt(origin.length() - position - 1) - 48;
	return 0;
}

	
}
