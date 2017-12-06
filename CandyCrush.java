
import java.util.Scanner;

public class CandyCrush {
   
    public static void main(String[] args) {
    	
        Scanner in = new Scanner(System.in);
        
        System.out.println("Elija el tipo de tablero: \n 1. Facil \n 2. Intermedio" +
                "\n 3. Dificil \n 4. Tablero fijo \n 0. Salir");
        
        int seleccion = in.nextInt(); //Hay que validar
        int f1,c1,f2,c2;
        int movimientos = 0;
        
        Tablero tablero = new Tablero(seleccion);
        actualizarTablero(tablero);
        tablero.puntuacion = 0;
        char[][]tablaanterior = tablero.tabla;
        
        while(movimientos<10 && seleccion != 0){
        	
            System.out.println("Introduzca las coordenadas de dos celdas contiguas");
            
            //Falta validar
            System.out.print("Fila de la primera casilla");
            f1 = in.nextInt();
            
            System.out.print("Columna de la primera casilla");
            c1 = in.nextInt();
            
            System.out.print("Fila de la segunda casilla");
            f2 = in.nextInt();
            
            System.out.print("Columna de la segunda casilla");
            c2 = in.nextInt();
            
            Jugada(tablero,f1,c1,f2,c2);
            if(tablaanterior !=tablero.tabla){
            	movimientos++;
            }
            tablaanterior = tablero.tabla;            
        }
        
        System.out.println("Fin del juego \n");
        
        if(movimientos != 0){
            ImprimeResultados(tablero);
        }
     }
    public static boolean tableroJugable(char[][]entrada){ //Duda Maria Luisa
    	for(int j = 0; j<9; j++){	//Jugadas todas las columnas caramelos verticales
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
    	for(int i = 0; i<7; i++){ //Jugadas columna 8
    		if((entrada[i][8] == entrada[i+1][8] && entrada[i][8] == entrada[i+2][7])||(entrada[i][8] == entrada[i+2][8] && entrada[i][8] == entrada[i+1][7])||(entrada[i][7] == entrada[i+1][8] && entrada[i+1][8] == entrada[i+2][8])){
    			return true;
    		}
    	}
    	for(int j=1; j<8;j++){	//Jugadas todas las columnas caramelos en l en seis posibilidades
    		for(int i=0; i<7; i++){
    			if((entrada[i][j] == entrada[i+1][j] && (entrada[i][j] == entrada[i+2][j+1]||entrada[i][j]== entrada[i+2][j-1]))||((entrada[i][j] == entrada[i+1][j+1]&&entrada[i][j]==entrada[i+2][j+1])||entrada[i][j]==entrada[i-1][j-1]&&entrada[i][j]==entrada[i-2][j-1])||(entrada[i][j]==entrada[i+2][j]&&(entrada[i][j]==entrada[i+1][j+1]||entrada[i][j]==entrada[i+1][j-1]))){
    				return true;
    			}
    		}
    	}
    	return false;
    	
    }
    public static void eliminarBolas(Tablero t){

    /*Elimina las bolas contiguas iguales del tablero, aumenta la puntuacion en lo que las corresponda y las sustituye
     *por el caracter 'r'*/
    	for(int j = 0; j<9; j++){
    		for(int i = 0; i<7; i++){
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
    public static void actualizarTablero(Tablero t){
    	char[][]tablaanterior;
    	do{
			tablaanterior = t.tabla;
			eliminarBolas(t);
			bajarBolas(t);
		} while(t.tabla !=tablaanterior);
    }
    public static void bajarBolas(Tablero t){
    	char temp;
    	for(int j = 0; j<9;j++){
    		for(int i = 0; i<9; i++){
    			if(i)
    		}
    				
    	}
    }
    public static void Jugada(Tablero t, int f1, int c1, int f2, int c2){
    //Realiza la jugada
    	char temp;
    	char[][]tablaanterior;
    	if(f1 == f2 ^ c1 == c2){
    		tablaanterior = t.tabla;
    		temp = t.tabla[f1][c1];
    		t.tabla[f1][c1] = t.tabla[f2][c2];
    		t.tabla[f2][c2] = temp;
    		actualizarTablero(t);
    		if(tablaanterior == t.tabla){
    			System.out.println("Jugada sin cambios");
    		}   		
    	}
    	else{
    		System.out.println("Jugada no valida");
    	}
   }
}

class Tablero{
    char[][]tabla = new char[9][9];
    int puntuacion;
    int dificultad;
    
    public static final char caracter[] = {'@','#','%','&','='};
    
    //Parametros del tablero
    public Tablero(int n){
        this.dificultad = n;
        this.tabla=rellenarAletorio(dificultad);
        this.puntuacion = 0;
    }
    
    public static char[][] rellenarAletorio(int n){
    	
        char[][]tablasalida = new char[9][9];
        
        if(n == 4){ //Relleno tabla fija
            /*Relleno primer caracter*/
            for(int i = 1; i<=8;i++) tablasalida[0][i] = caracter[0]; //Fila 0, car 1
            tablasalida[1][0]=tablasalida[1][4]=tablasalida[1][8]=caracter[0]; //Fila 1, car 1
            tablasalida[2][5] = caracter[0]; //Fila 2, car 1 
            for(int i=0; i<=8;i++)tablasalida[3][i]=caracter[0]; //Fila 3, car 1
            tablasalida[4][4] = tablasalida[4][7] = caracter[0]; //Fila 4, car 1
            tablasalida[5][8] = caracter[0];
            tablasalida[6][0] = tablasalida[6][1] = tablasalida[6][3] =
            tablasalida[6][5]=tablasalida[6][6]=tablasalida[6][7]=tablasalida[6][8]=caracter[0];//Fila 6, car 1
            tablasalida[7][2] = tablasalida[7][7] = caracter[0]; //Fila 7, car 1
            tablasalida[8][2] = tablasalida[8][8] = caracter[0]; // Fila 8, car 1
            /*Relleno segundo caracter*/
            tablasalida[0][0] = caracter[1]; //Fila 0, car 2
            tablasalida[1][1] = tablasalida[1][2] = tablasalida[1][3] = tablasalida[1][6]=
            tablasalida[1][7] = caracter[1]; //Fila 1, car 2
            tablasalida[2][1] = caracter[1]; //Fila 2, car 2
            tablasalida[4][0] = tablasalida[4][1]=tablasalida[4][2] = tablasalida[4][3] =
            tablasalida[4][5] = tablasalida[4][6] = caracter[1]; //Fila 4, car 2
            tablasalida[6][2] = caracter[1]; //Fila 6, car 2
            tablasalida[7][0] = tablasalida[7][1] = tablasalida[7][2] = tablasalida[7][3]=
            tablasalida[7][4] = tablasalida[7][5] = tablasalida[7][6] =
            tablasalida[7][8] = caracter[1]; //Fila 7, car 1
            tablasalida[8][1] = caracter[1]; //Fila 8, car 1
            /*Relleno tercer caracter*/
            
            for(int i=0;i<=8;i++){
                for(int j=0; j<=8;j++){
                    if(tablasalida[i][j] == '\u0000'){
                        tablasalida[i][j] = caracter[2];
                    }
                }
            }
            
        return tablasalida;       
        
        }else{
        	
            boolean rellenoCorrecto = true;
            
            while(rellenoCorrecto){
                for(int i=0;i<=8;i++){
                    for(int j=0;j<=8;j++){
                        tablasalida[i][j] = caracterAleatorio(n);
                    }
                }
                rellenoCorrecto = !(tableroJugable(tablasalida));
            }
            
            return tablasalida; 
        }
    }
    
    public static char caracterAleatorio(int n){
        //Devuelve un caracter aleatorio dependiendo del modo de dificultad del juego n
    	
        int aleatorio = (int)Math.random()*(n+2);
        
        switch(aleatorio){
        case 0:
            return caracter[0];
        case 1:
            return caracter[1];
        case 2:
            return caracter[2];
        case 3:
            return caracter[3];
        case 4:
            return caracter[4]; 
        }
        
    return '\u0000';
    
    }
    
    public static void ImprimirTablero(Tablero e){ 
        for(int i = 0; i<=9;i++){
            for(int j = 0; j<=9;j++){
                if(i == 0 && j == 0){
                    System.out.print("\t");
                }
                else if(i == 0 && j>0) {
                    System.out.print(j+"\t");    //Imprime leyenda columnas
                }
                else if(j == 0 && i>0){
                    System.out.print(i-1+"\t"); //Imprime leyenda filas
                }
                else{
                    System.out.print(e.tabla[i-1][j-1]+"\t"); //Imprime elemento
                }
                if(j == 9){
                    System.out.println("");
                }
            }
        }
    }
    public static boolean tableroJugable(char[][]entrada){
    	for(int j = 0; j<9; j++){	//Jugadas todas las columnas caramelos verticales
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
    	for(int i = 0; i<7; i++){ //Jugadas columna 8
    		if((entrada[i][8] == entrada[i+1][8] && entrada[i][8] == entrada[i+2][7])||(entrada[i][8] == entrada[i+2][8] && entrada[i][8] == entrada[i+1][7])||(entrada[i][7] == entrada[i+1][8] && entrada[i+1][8] == entrada[i+2][8])){
    			return true;
    		}
    	}
    	for(int j=1; j<8;j++){	//Jugadas todas las columnas caramelos en l en seis posibilidades
    		for(int i=0; i<7; i++){
    			if((entrada[i][j] == entrada[i+1][j] && (entrada[i][j] == entrada[i+2][j+1]||entrada[i][j]== entrada[i+2][j-1]))||((entrada[i][j] == entrada[i+1][j+1]&&entrada[i][j]==entrada[i+2][j+1])||entrada[i][j]==entrada[i-1][j-1]&&entrada[i][j]==entrada[i-2][j-1])||(entrada[i][j]==entrada[i+2][j]&&(entrada[i][j]==entrada[i+1][j+1]||entrada[i][j]==entrada[i+1][j-1]))){
    				return true;
    			}
    		}
    	}
    	return false;
    	
    }
}
