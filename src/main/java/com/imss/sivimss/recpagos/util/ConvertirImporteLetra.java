package com.imss.sivimss.recpagos.util;

public class ConvertirImporteLetra {
	
	public static String importeEnTexto(int iImporte ) {
		String strImporte;
		int iUnidad;
		int iDMillar=0;
		
		String pesos = " PESOS 00/100 M.N.";
		
		// Obtiene unidad
		if(iImporte == 1) {
			return "UN PESO 00/100 M.N.";
		}
		
		if(iImporte < 10 ) {
			iUnidad = iImporte;
			iImporte = 0;
		}else {
			iUnidad = iImporte%10;
			iImporte = iImporte/10;
		}		
		
		strImporte = unidadEnTexto(iUnidad);
		
		if( iImporte == 0 ) {
			return strImporte.trim() + pesos;
		}
		
		// Obtiene decena
		int iDecena = iImporte%10;
		iImporte = iImporte/10;
		if (iUnidad==0 && iDecena>0) {
			strImporte = decenaEnTexto(iDecena);
		} else if (iDecena==1) {
			strImporte = decenas(10+iUnidad);
		} else if (iDecena > 1) {
			strImporte = decenaEnTexto(iDecena) + " Y " + strImporte;
		}
		
		if( iImporte == 0 ) {
			return strImporte.trim() + pesos;
		}
		
		// Obtiene centena
		int iCentena = iImporte%10;
		iImporte = iImporte/10;
		
		if(iCentena > 0) {
		
			if ((iCentena!=1) && (iCentena!=5) && (iCentena!=9) && (iCentena!=0)) {
				strImporte = unidadEnTexto(iCentena) + "CIENTOS" + strImporte;
			} else if ( (iCentena==5) || (iCentena==9) ) {
				strImporte = centenaEnTexto(iCentena) + " " + strImporte;
			}else if( (iCentena == 1) && (iDecena==0) && (iUnidad == 0) ) {
				strImporte = "CIEN" + strImporte;
			}else {
				strImporte = centenaEnTexto(iCentena) + " " + strImporte;
			}
			
		}
		
		if( iImporte == 0 ) {
			return strImporte.trim() + pesos;
		}
		
		// Obtiene millar
		int iMillar = iImporte%10;
		if (!(iImporte > 10 && iImporte < 20) && (iMillar !=0) ) {
			if (iMillar == 1) {
				strImporte = "MIL " + strImporte; 
			} else {
				strImporte = unidadEnTexto(iMillar) + " MIL " + strImporte;
			}
		}
		iImporte = iImporte/10;
		
		if( iImporte == 0 ) {
			return strImporte.trim() + pesos;
		}
		
		// Obtiene diez millar
		if (iImporte > 0) {
			iDMillar = iImporte%10;
			iImporte = iImporte/10;
			if (iMillar==0 && iDMillar>0) {
				strImporte = decenaEnTexto(iDMillar) + " MIL " + strImporte;
			} else if (iDMillar==1) {
				strImporte = decenas(10+iMillar) + " MIL " + strImporte;
			} else if (iDMillar > 1) {
				strImporte = decenaEnTexto(iDMillar) + " Y " + strImporte;
			}
		}
		
		if( iImporte == 0 ) {
			return strImporte.trim() + pesos;
		}
		
		if( (iDMillar ==0) && iMillar==0) {
			strImporte = " MIL" + strImporte;
		}
		
		int iCMillar = iImporte%10;
		iImporte = iImporte/10;
		if ((iCMillar!=1) && (iCMillar!=5) && (iCMillar!=9) && (iCMillar!=0)) {
			strImporte = unidadEnTexto(iCMillar) + "CIENTOS " + strImporte;
		} else if ( (iCMillar==5) || (iCMillar==9) ) {
			strImporte = centenaEnTexto(iCMillar) + " " + strImporte;
		}else if( (iCMillar == 1) && (iDMillar==0) && (iMillar == 0) ) {
			strImporte = "CIEN" + strImporte;
		}else {
			strImporte = centenaEnTexto(iCMillar) + " " + strImporte;
		}
		
		return strImporte.trim() + pesos;
	}
	
	private static String unidadEnTexto(int iNumero){
		 switch(iNumero){
			case 1:
				return "UNO";
			case 2:
				return "DOS";
			case 3:
				return "TRES";
			case 4:
				return "CUATRO";
			case 5:
				return "CINCO";
			case 6:
				return "SEIS";
			case 7:
				return "SIETE";
			case 8:
				return "OCHO";
			case 9:
				return "NUEVE";
			default:
				return "";
		 }
	}
	
	private static String decenaEnTexto(int iDecena){
		  switch (iDecena){
			case 1:
				return "DIEZ";
			case 2:
				return "VEINTE";
			case 3:
				return "TREINTA";
			case 4:
				return "CUARENTA";
			case 5:
				return "CINCUENTA";
			case 6:
				return "SESENTA";
			case 7:
				return "SETENTA";
			case 8:
				return "OCHENTA";
			case 9:
				return "NOVENTA";		
			default:
				return "";
		  }
	}

	private static String decenas(int iDecena) {
		  switch (iDecena){
			case 11:
				return "ONCE";
			case 12:
				return "DOCE";
			case 13:
				return "TRECE";
			case 14:
				return "CATORCE";
			case 15:
				return "QUINCE";
			case 16:
				return "DIECISEIS";
			case 17:
				return "DIECISIETE";
			case 18:
				return "DIECIOCHO";
			case 19:
				return "DIECINUEVE";		
			default:
				return "";
		  }
	}
	
	private static String centenaEnTexto(int iCentena){
		  switch (iCentena){
			case 1:
				return "CIENTO";
			case 2:
				return "DOSCIENTOS";
			case 3:
				return "TRESCIENTOS";
			case 4:
				return "CUATROCIENTOS";
			case 5:
				return "QUINIENTOS";
			case 6:
				return "SEISCIENTOS";
			case 7:
				return "SETECIENTOS";
			case 8:
				return "OCHOCIENTOS";
			case 9:
				return "NOVECIENTOS";				
			default:
				return "";
		  }
	}
	
}
