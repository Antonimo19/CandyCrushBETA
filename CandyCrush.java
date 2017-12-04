
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
            
            movimientos++;
        }
        
        System.out.println("Fin del juego \n");
        
        if(movimientos != 0){
            ImprimeResultados(tablero);
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
        this.tabla=rellenarAleatorio(dificultad);
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
    
 public static void ImprimirTablero(Tablero e){ //Hay que cambiar leyenda
        for(int i = 0; i<=9;i++){
            for(int j = 0; j<=9;j++){
                if(i == 0 && j == 0){
                    System.out.print("\t");
                }
                else if(i == 0 && j>0) {
                    System.out.print(j-1+"\t");    //Imprime leyenda columnas
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
    	
        char anterior2 = entrada[0][0], anterior1 = entrada[1][0];
        
        for(int i=0;i<=8;i++){
            for(int j=2;j<=7;j++){
                if((i==0)&&(anterior2==anterior1)&&(entrada[j+1][i+1] == anterior1)){  //Primera columna
                	
                    return true;
                } 
            }
        }
    }
}
