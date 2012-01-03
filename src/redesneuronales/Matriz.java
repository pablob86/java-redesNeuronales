/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package redesneuronales;
import java.text.DecimalFormat;
/**
 *Clase Matriz
 *Clase que define objetos matriz para realizar operaciones de matrices
 * de forma rápida y sencilla
 * @author Pablo Borbón
 */
public class Matriz {
//Campos
int fil=0;
int col=0;
double mat[][];


//Constructures sobrecargados

/**
 *Contructor Matriz
 * @param m Arreglo bidimensional de double
 */
public Matriz(double m[][]){
this.mat=m;
this.fil=m.length;
this.col=m[0].length;
}

/**
 *Constructor Matriz
 * @param m Arreglo unidimensional de double
 */
public Matriz(double m[]){
this.mat=new double[1][m.length];
for (int i=0;i<m.length;i++){
this.mat[0][i]=m[i];
this.fil=1;
this.col=m.length;
}
}

/****************************************************************************
 * MÉTODOS
 ****************************************************************************
 */
/**
 * Obtener número de columnas
 * @param m Matriz
 * @return int NumColumnas
 */

public static int getCol(Matriz m){
return m.col;
}

/**
 * Obtener número de columnas
 * @return int NumColumnas
 */
public int getCol(){
return this.col;
}

/**
 * Obtener número de columnas
 * @return String NumColumnas
 */
public String getColString(){
return String.valueOf(this.col);
}

/**
 * Obtener número de filas
 * @param m Matriz
 * @return int NumFilas
 */
public static int getFil(Matriz m){
return m.fil;
}

/**
 * Obtener número de filas
 * @return int NumFilas
 */
public int getFil(){
return this.fil;
}

/**
 * Obtener número de filas
 * @return String NumFilas
 */
public String getFilString(){
return String.valueOf(this.fil);
}

/**
 * Convertir matriz en array
 * @return Double array[][]
 */
public double[][] toArray(){
return this.mat;
}

/**
 * Convertir matriz en array
 * @param m Matriz
 * @return Double array[][]
 */
public static double[][] toArray(Matriz m){
return m.mat;
}


/**
 *Colocar valor en posicion[f][c]
 * @param f índice de fila
 * @param c índice de columna
 * @param value Valor que se coloca en el arreglo de la matriz
 */
public void setFC(int f,int c, double value){
this.mat[f][c]=value;
}


/**
 *Obtener valor en posición[f][c]
 * @param f índice fila
 * @param c índice columna
 * @return double valor
 */
public double getFC(int f,int c){
return this.mat[f][c];
}


/**
 *Convertir la matriz en un String
 * @param m Matriz a convertir
 * @return String con las filas y las columnas de la matriz
 * debidamente ordenadas.
 */
public static String toStringM(Matriz m){
DecimalFormat df = new DecimalFormat("0.00");
StringBuffer mat1= new StringBuffer();
 for (int j=0;j<m.getFil();j++){

     for (int i=0;i<m.getCol();i++){
            mat1.append(" ");
            mat1.append(df.format(m.toArray()[j][i]));
            }
        mat1.append("\n");
 }
String salida = mat1.toString();
return salida;
}

/**
 *Convertir Matriz a String
 @return String con las filas y las columnas de la matriz
 * debidamente ordenadas.
 */
public String toStringM(){
DecimalFormat df = new DecimalFormat("0.00");
StringBuffer mat1= new StringBuffer();
 for (int j=0;j<this.getFil();j++){

     for (int i=0;i<this.getCol();i++){
            mat1.append(" ");
            mat1.append(df.format((this.toArray()[j][i])));
            }
        mat1.append("\n");
 }
String salida = mat1.toString();
return salida;
}


/**
 *Transponer matriz
 * @param m matriz a transponer
 * @return Matriz m'
 */
public static Matriz transponer(Matriz m){
double retorno[][];
retorno=new double[m.getCol()][m.getFil()];

for (int i=0; i<m.getFil();i++){
        for (int j=0; j<m.getCol();j++){

            retorno[j][i]=m.toArray()[i][j];

        }
}
Matriz ret= new Matriz(retorno);
return ret;
}

/**
 *Sumar matrices
 * @param mA sumandoA
 * @param mB sumandoB
 * @return Matriz (ma+mB)
 */
public static Matriz sumar(Matriz mA, Matriz mB){
double retorno[][];
retorno=new double[mB.getFil()][mB.getCol()];
Matriz ret= new Matriz(retorno);
for (int i=0; i<mA.getFil();i++){
        for (int j=0; j<mA.getCol();j++){

           ret.setFC(i, j, (mA.getFC(i, j)+mB.getFC(i, j)));

        }
}

return ret;
}

/**
 *Restar matrices
 * @param mA Minuendo
 * @param mB Sustraendo
 * @return Matriz diferencia= mA-mB
 */
public static Matriz restar(Matriz mA, Matriz mB){
double retorno[][];
retorno=new double[mB.getFil()][mB.getCol()];
Matriz ret= new Matriz(retorno);
for (int i=0; i<mA.getFil();i++){
        for (int j=0; j<mA.getCol();j++){

           ret.setFC(i, j, (mA.getFC(i, j)-mB.getFC(i, j)));

        }
}

return ret;
}

/**
 *Multiplicar matrices
 * @param mA factor A
 * @param mB factor B
 * @return mAxmB
 * La matriz de retorno tiene dimensions [fA]x[cB]
 */
public static Matriz multiplicar(Matriz mA, Matriz mB){
double retorno[][];
double tmp=0;
retorno=new double[mA.getFil()][mB.getCol()];
Matriz ret= new Matriz(retorno);

for (int i=0; i<mA.getFil();i++){
        
    for (int j=0; j<mB.getCol();j++){
                tmp=0;
                  for (int k=0; k<mA.getCol();k++){

                   tmp+=mA.getFC(i,k)*mB.getFC(k, j);

                }
                ret.setFC(i, j, tmp);
        }
}
return ret;
}

/**
 *Calcular la respueta más probable
 * @param resp Matriz con las respuestas de la simulación [1]x[10]
 * @param nom Arreglo de Strings con los nombres de los patrones
 * @return String con el patrón más probable del arreglo
 */
public  static String masProbable(Matriz resp,String nom[]){
int index=0;
double tmp=resp.toArray()[0][0];
    for (int i=1;i<resp.toArray()[0].length;i++){

            if(resp.toArray()[0][i]>tmp){
                tmp=resp.toArray()[0][i];
                index=i;
            }
    }
return nom[index];
}

/**
 *Obtener columna
 * @param index índice de la columna a obtener
 * @return Matriz Columna con todos los valores de la columna indicada
 */
public  Matriz getColumna(int index){
double tmp[][]= new double [this.getFil()][1];

    for (int i=0;i<this.getFil();i++){
    tmp[i][0]=this.toArray()[i][index];
    }
Matriz retorno = new Matriz(tmp);
return retorno;

}


/**
 *Multiplicar Matriz por escalar
 * @param m Matriz a multiplicar
 * @param esc factor escalar
 * @return Matriz escalada
 */
public static Matriz multEscalar(Matriz m, double esc){

 double tmp [][]= new double [m.getFil()][m.getCol()];
    for (int i=0;i<m.getFil();i++){

             for (int j=0;j<m.getCol();j++){
             tmp[i][j]=m.toArray()[i][j]*esc;
            }

    }

Matriz ret = new Matriz (tmp);
return ret;
}

/**
 *Multiplicar elementos entre matrices
 * @param m1 Matriz 1
 * @param m2 Matriz 2
 * @return Devuelve una matriz con las mismas dimensiones
 * con los productos de los elementos de cada matriz
 */
public static Matriz multElementos(Matriz m1, Matriz m2){

 double tmp [][]= new double [m1.getFil()][m2.getCol()];
    for (int i=0;i<m1.getFil();i++){

             for (int j=0;j<m1.getCol();j++){
             tmp[i][j]=m1.toArray()[i][j]*m2.toArray()[i][j];
            }

    }

Matriz ret = new Matriz (tmp);
return ret;
}


}

