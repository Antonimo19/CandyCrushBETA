//Antonio Sanjuan de la Mano y Mario Villacorta Garcia
//CandyCrush modificado

import java.util.Scanner;

public class CandyCrushModificado {
	
    public static void main(String[] args) {
    	
        Scanner in = new Scanner(System.in);
        int seleccion;
        
        do{
        	System.out.println("Elija el tipo de tablero: \n 1. Facil \n 2. Intermedio" +
        			"\n 3. Dificil \n 0. Salir");
        
        	seleccion = in.nextInt();
        }while(seleccion<0 ||seleccion>4);
        
        int f1,c1,f2,c2;
        int movimientos = 0;
    	char[][]tablaanterior = new char[10][4];
    	
    	if(seleccion != 0){
    		Tablero tablero = new Tablero(seleccion);
    		actualizarTablero(tablero);
    		tablero.puntuacion = 0;
    		
    		while(movimientos<5){
    			if(Tablero.tableroJugable(tablero.tabla)==false){
    				//Si se llega a un tablero sin jugadas disponibles
    				System.out.println("El tablero no era jugable. Tu nuevo tablero:");
    				if(tablero.dificultad == 4){
    					tablero = new Tablero(1,tablero.puntuacion);
    					
    				}else{
    					tablero = new Tablero(tablero.dificultad,tablero.puntuacion);
    					
    				}			
    			}
    			
    			ImprimirTablero(tablero);
    			
    			tablaanterior = copiaTabla(tablero.tabla, tablaanterior);
    			System.out.println("Introduzca las coordenadas de dos celdas contiguas");
    			/*Entrada de las coordenadas de la jugada
    			 * Validacion realizada en metodo Jugada*/			
    				System.out.print("Fila de la primera casilla");
    				f1 = 10-in.nextInt();
            
    				System.out.print("Columna de la primera casilla");
    				c1 = in.nextInt()-1;
            
    				System.out.print("Fila de la segunda casilla");
    				f2 = 10-in.nextInt();
            
    				System.out.print("Columna de la segunda casilla");
    				c2 = in.nextInt()-1;
    				System.out.println("");
    				
    				if(f1 == 10 && f2 == 10 && c1 == -1 && c2 == -1){
    	    	   		System.out.println("Has abandonado el juego");
    	    	   		movimientos = 1000;
    	    	   		
    	    	   	}
    			/*Realizacion de la jugada*/
    			if(movimientos<10){
    				Jugada(tablero,f1,c1,f2,c2);
    				if(tablasIguales(tablero.tabla, tablaanterior) == false){
    					//En el caso de que la jugada haya tenido efecto sobre el tablero
    					movimientos++;
    				}
    				System.out.println("\n\nMovimientos restantes: "+(5-movimientos)+"\t Puntuacion: "+tablero.puntuacion+"\n");
    			}
    			
    		}	
    		ImprimeResultados(tablero);
    
    	} 
        System.out.println("Fin del juego \n");
        in.close();
        
     }
    
    public static boolean tablasIguales(char[][]A, char[][]B){
 	   int filA = A.length;
 	   int colA = A[0].length;
 	   int filB = B.length;
 	   int colB = B[0].length;
 	
 	   if(filA != filB || colA!=colB)return false;
 	   for(int i=0; i<filA; i++){
 		   for(int j=0; j<colB; j++){
 			   if(A[i][j] != B[i][j]){
 				   return false;
 			   }
 		   }
 	   }
 	   return true;
    }
    public static void Jugada(Tablero t, int f1, int c1, int f2, int c2){
    	   //Realiza la jugada
    	   	char temp;
    	   	char[][]tablaanterior = new char[10][4];
    	   	if((f1 == f2 ^ c1 == c2) && (((int)Math.abs(f1-f2) == 1) ^((int)Math.abs(c1-c2) == 1)) && ((f1>=0 && f1<10 && f2>=0 && f2<10 && c1>=0 && c1<4 && c2>=0 && c2<4))){
    	   		//Movimiento de bolas
    	   		temp = t.tabla[f1][c1];
    	   		t.tabla[f1][c1] = t.tabla[f2][c2];
    	   		t.tabla[f2][c2] = temp;
    	   		tablaanterior = copiaTabla(t.tabla, tablaanterior);
    	   		actualizarTablero(t);
    	   		if(tablasIguales(tablaanterior,t.tabla)){
    	   			//Si la jugada no tiene efecto
    	   			System.out.println("Jugada sin cambios");
    	  	   		temp = t.tabla[f1][c1];
    	   	   		t.tabla[f1][c1] = t.tabla[f2][c2];
    	   	   		t.tabla[f2][c2] = temp;
    	   		}
    	   		
    	   	}else{
    	   		System.out.println("Jugada no valida");
    	   		
    	   	}
    }
  
    public static void actualizarTablero(Tablero t){
       	char[][]tablaanterior = new char[10][4];
       	do{
               tablaanterior = copiaTabla(t.tabla, tablaanterior);
    			eliminarBolas(t);
    			bajarBolas(t);
    		} while(tablasIguales(t.tabla,tablaanterior) == false);  	
       }
   public static void eliminarBolas(Tablero t){

   /*Elimina las bolas contiguas iguales del tablero, aumenta la puntuacion en lo que las corresponda y las sustituye
    *por el caracter 'r'*/
   	for(int j = 0; j<4; j++){
   		for(int i = 0; i<8; i++){
   			if(t.tabla[i][j] == t.tabla[i+1][j] && t.tabla[i][j]==t.tabla[i+2][j]){
   				if(i<=5&&(t.tabla[i][j]==t.tabla[i+3][j])){
   					if(i<=4&&(t.tabla[i][j]==t.tabla[i+4][j])){
   						if(i<=3&&(t.tabla[i][j] == t.tabla[i+5][j])){
   							if(i<=2&&(t.tabla[i][j]==t.tabla[i+6][j])){	//7 bolas
   								t.puntuacion += 50;
   								t.tabla[i+6][j] = t.tabla[i+5][j] = t.tabla[i+4][j]= t.tabla[i+3][j] = t.tabla[i+2][j] = t.tabla[i+1][j] = t.tabla[i][j] = 'r';
   							}
   							else{	//6 bolas
   								t.puntuacion += 40;
   								t.tabla[i+5][j] = t.tabla[i+4][j]= t.tabla[i+3][j] = t.tabla[i+2][j] = t.tabla[i+1][j] = t.tabla[i][j] = 'r';

   							}
   						}
   						else{	//5 bolas
   							t.puntuacion+= 30;
								t.tabla[i+4][j]= t.tabla[i+3][j] = t.tabla[i+2][j] = t.tabla[i+1][j] = t.tabla[i][j] = 'r';

   						}
   					}
   					else{	//4 bolas
   						t.puntuacion += 20;
							t.tabla[i+3][j] = t.tabla[i+2][j] = t.tabla[i+1][j] = t.tabla[i][j] = 'r';

   					}
   				}
   				
   				else{	//3 bolas	
   					t.puntuacion += 10;
						t.tabla[i+2][j] = t.tabla[i+1][j] = t.tabla[i][j] = 'r';

   				}
   			}
   		}
   	}
   }

   public static void bajarBolas(Tablero t){
	   int posFinal=8, i = 8, j= 8;
	   
	   do{
		   for(i = 9; i>=0;i--){
			   for(j =3; j>=0; j--){
				   if(t.tabla[i][j] == 'r'){
					   for(int x = i; x>=0;x--){		//Cuentas numero de r en columna j
						   if(t.tabla[x][j] == 'r'){
							   posFinal = x;
						   }
					   }
					   if(posFinal-1>=0){
						   t.tabla[i][j] = t.tabla[posFinal-1][j];
						   t.tabla[posFinal-1][j] = 'r';
					   }
				   }
			   
			   }
	   	   }
	   }while(posFinal != 0 && i == 0 && j == 0);
	   for(int f = 0; f<10;f++){
		   for(int c = 0; c<4;c++){
			   if(t.tabla[f][c] == 'r'){
				   t.tabla[f][c] = Tablero.caracterAleatorio(t.dificultad);
			   }
		   }
		   
	   }
   
   
   }
		

   
  
   public static char[][] copiaTabla(char[][]A, char[][]B){
   //Copia la tabla A en la tabla B
   //PRE: Las tablas tienen las mismas dimensiones
	   int filas = A.length;
	   int columnas = A[0].length;
	   for(int i=0; i<filas; i++){
		   for(int j=0;j<columnas; j++){
			   B[i][j] = A[i][j];
		   }
	   }
	   return B;
   }
 
   public static void ImprimeResultados(Tablero t){
	   System.out.println("Puntuacion: " + t.puntuacion);
   }
   public static void ImprimirTablero(Tablero e){ 
       for(int i = 0; i<=10;i++){
           for(int j = 0; j<=4;j++){
               if(i == 0 && j == 0){
                   System.out.print("   ");
               }
               else if(i == 0 && j>0) {
                   System.out.print(j+" ");    //Imprime leyenda columnas
               }
               else if(j == 0 && i>0){
            	   if(i == 1){
                       System.out.print((11-i)+" "); //Imprime leyenda fila 10
            	   }
            	   else{
                   System.out.print((11-i)+"  "); //Imprime leyenda filas
            	   }
               }
               else{
                   System.out.print(e.tabla[i-1][j-1]+" "); //Imprime elemento
               }
               if(j == 4){
                   System.out.println("");
               }
           }
       }
   }
}

class Tablero{
    char[][]tabla = new char[10][4];
    int puntuacion;
    int dificultad;
    
    public static final char caracter[] = {'@','#','%','&','='};
    
    //Parametros del tablero
    public Tablero(int n){
        this.dificultad = n;
        this.tabla=rellenarAletorio(dificultad);
        this.puntuacion = 0;
    }
    public Tablero(int dif, int punt){
    	this.dificultad = dif;
        this.tabla=rellenarAletorio(dificultad);
        this.puntuacion = punt;
    }
    
    public static char[][] rellenarAletorio(int n){
    	
        char[][]tablasalida = new char[10][4];
        
        
        	
            boolean rellenoCorrecto = true;
            
            while(rellenoCorrecto){
                for(int i=0;i<=9;i++){
                    for(int j=0;j<=3;j++){
                        tablasalida[i][j] = caracterAleatorio(n);
                    }
                }
                rellenoCorrecto = !(tableroJugable(tablasalida));
            }
            
            return tablasalida; 
        
    }
    
    public static char caracterAleatorio(int n){
    	if(n==4){
    		n=1;
    		
    		//Devuelve un caracter aleatorio dependiendo del modo de dificultad del juego n
    	}
        int aleatorio = (int)(Math.random()*(n+2));
        return caracter[aleatorio];
    
    }
    

    public static boolean tableroJugable(char[][]entrada){
    	
    	for(int j = 0; j<4; j++){	//Jugadas todas las columnas caramelos verticales
    		for(int i = 0; i<6;i++){
    			if((entrada[i][j] == entrada[i+1][j] && entrada[i][j] == entrada[i+3][j]) || (entrada[i][j] == entrada[i+2][j] && entrada[i][j] == entrada[i+3][j])){
    				return true;
    			}
    		}
    	}
    	for(int i = 0; i<7; i++){ //Jugadas columna 0
    		if((entrada[i][0] == entrada[i+1][0] && entrada[i][0] == entrada[i+2][1])||(entrada[i][0] == entrada[i+2][0] && entrada[i][0] == entrada[i+1][1])||(entrada[i][1] == entrada[i+1][0] && entrada[i+1][0] == entrada[i+2][0])){
    			return true;
    		}
    	}
    	for(int i = 0; i<8; i++){ //Jugadas columna 8
    		if((entrada[i][3] == entrada[i+1][3] && entrada[i][3] == entrada[i+2][2])||(entrada[i][3] == entrada[i+2][3] && entrada[i][3] == entrada[i+1][2])||(entrada[i][2] == entrada[i+1][3] && entrada[i+1][3] == entrada[i+2][2])){
    			return true;
    		}
    	}
    	for(int j=1; j<4;j++){	//Jugadas todas las columnas caramelos en l en seis posibilidades
    		for(int i=0; i<8; i++){
    			if((entrada[i][j] == entrada[i+1][j] && (entrada[i][j] == entrada[i+2][j+1]||entrada[i][j]== entrada[i+2][j-1]))||((entrada[i][j] == entrada[i+1][j+1]&&entrada[i][j]==entrada[i+2][j+1])||entrada[i][j]==entrada[i-1][j-1]&&entrada[i][j]==entrada[i-2][j-1])||(entrada[i][j]==entrada[i+2][j]&&(entrada[i][j]==entrada[i+1][j+1]||entrada[i][j]==entrada[i+1][j-1]))){
    				return true;
    			}
    		}
    	}
    	return false;
    	
    }
}

