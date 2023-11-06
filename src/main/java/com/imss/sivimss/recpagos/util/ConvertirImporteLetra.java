package com.imss.sivimss.recpagos.util;

import java.util.regex.Pattern;

public class ConvertirImporteLetra {

	 private ConvertirImporteLetra() {
		    throw new IllegalStateException("ConvertirImporteLetra class");
		  }
	private static final String[] UNIDADES = { "", "un ", "dos ", "tres ", "cuatro ", "cinco ", "seis ", "siete ", "ocho ", "nueve " };
	private static final String[] DECENAS_UNIDADES = { "diez", "once", "doce ", "trece ", "catorce ", "quince "};
	private static final String[] DECENAS = { "", "dieci", "veinti", "treinta ", "cuarenta ", "cincuenta ", "sesenta ", "setenta ", "ochenta ", "noventa " };
	private static final String[] CENTENAS = { "", "ciento ", "doscientos ", "trescientos ", "cuatrocientos ", "quinientos ", "seiscientos ", "setecientos ", "ochocientos ", "novecientos " };
	

	private static final String PESOS = " PESOS 00/100 M.N.";
	private static final String PESO = " PESO 00/100 M.N.";
	
	
	public static String importeEnTexto(int iImporte ) {
		String s = String.valueOf(iImporte);
		StringBuilder resultado = new StringBuilder();
        //si el numero utiliza (.) en lugar de (,) -> se reemplaza
        s = s.replace(".", ",");
        //si el numero no tiene parte decimal, se le agrega ,00
        if(s.indexOf(',')==-1){
            s = s + ",00";
        }
        //se valida formato de entrada -> 0,00 y 999 999 999,00
        if (Pattern.matches("\\d{1,9},\\d{1,2}", s)) {
            
            String[] num = s.split(",");    
            s = num[0];        
        }

        long parteEntera = Long.parseLong(s); 
        int unidades      = (int)(parteEntera % 1000);
        int miles         = (int)((parteEntera / 1000) % 1000);
        int millones      = (int)((parteEntera / 1000000) % 1000);
        int milMillones      = (int)((parteEntera / 1000000000) % 1000);
        
        resultado.append((parteEntera == 0)? "cero ":"");
        resultado.append((milMillones > 0) ? convertirATexto(milMillones).toString() + "mil ":"");
        resultado.append((millones > 0) ? convertirATexto(millones).toString():"");        
        resultado.append((milMillones == 0 && millones == 1)? "millón " : "");
        resultado.append((milMillones > 0 && millones > 0)? "millones ":"");
        resultado.append((miles > 0) ?convertirATexto(miles).toString() + "mil ":"");
        resultado.append((unidades > 0) ? convertirATexto(unidades).toString():"");
        if ( iImporte == 1)
        	return resultado.toString().toUpperCase() + PESO;
        else
        	return resultado.toString().toUpperCase() + PESOS;
	}

    private static StringBuilder convertirATexto(int n) {
        StringBuilder result = new StringBuilder();
        int centenas = n / 100;
        int decenas  = (n % 100) / 10;
        int unidades = (n % 10);
        
        result.append((n == 100) ? "cien ":CENTENAS[centenas]);
        
        if(decenas == 1 && unidades < 6) {
                result.append(DECENAS_UNIDADES[unidades]);
        }
        result.append((decenas == 1 && unidades > 5) ? DECENAS[decenas] : "");
        if (decenas == 2 && unidades == 0) {
        		result.append("veinte "); 
        }else if (decenas == 2 && unidades > 0) 
        		result.append( DECENAS[decenas]);        	
        

        result.append( (decenas > 2 && unidades > 0) ? "y " :"");
        result.append((decenas == 1 && unidades <= 5) ? "" : UNIDADES[unidades]);
        
        return result;
    }
	
}
