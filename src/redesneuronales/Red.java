/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package redesneuronales;
import java.util.Random;
import java.lang.Math.*;

/**
 * ´
 *Clase Red:Define las matrices de pesos, los métodos de entrenamiento, de propagación
 * y en general todos los aspectos de campos y funcionalidad de la red neuronal
 * propuesta como trabajo e implementada en Java
 * @author Pablo Borbón
 */
public class Red {
    
    Matriz wI;//matriz de pesos de entrada-oculta 18x35
    Matriz wO;//matriz de pesos de oculta salida 10x18
    Matriz trainMP;//matriz 10 patrones de entrada 35x10
    Matriz trainMT;//matriz de 10 patrones de salida 10x10
    Random generadorAl = new Random();//PAra inicializar los pesos
    int epocas=0;



    //CONSTRUCTORES


    /**
     *Constructor con matrices de patrones
     * @param mP1 Matriz de patrones de entrada [35x10]
     * @param mT1 Matriz de objetivos [10x10]
     */
    public Red(Matriz mP1, Matriz mT1){

this.trainMP=mP1;
this.trainMT=mT1;
}

/**
 *Constructor vacío
 */
public Red(){

}





// METODOS

/**
 *Establecer pesos
 * Coloca las matrices de la capa oculta y la capa de salida en
 * el objeto Red.
 * @param pesosI pesos de la capa oculta
 * @param pesosO pesos de la capa de salida
 */
public void setPesos(Matriz pesosI, Matriz pesosO){
this.wI=pesosI;
this.wO=pesosO;
}

/**
 *Inicializar red
 * Inicializa los pesos de la red neuronal pasada como parámetro
 * con un número aleatorio dentro de una distribución de probabilidad
 * Gaussiana con valores entre [-0.5,0.5]
 * @param redNeu Red Neuronal Objetivo
 */
public static void init(Red redNeu){
double tempI[][]=new double [18][35];
double tempO[][]=new double [10][18];
    for (int i=0;i<18;i++){
        for (int j=0;j<35;j++){
        tempI[i][j]=redNeu.generadorAl.nextGaussian()*0.5;
        }
    }

for (int i=0;i<10;i++){
        for (int j=0;j<18;j++){
        tempO[i][j]=redNeu.generadorAl.nextGaussian()*0.5;
        }
    }

redNeu.wI= new Matriz(tempI);
redNeu.wO= new Matriz(tempO);
}

/**
 * Obtener Wi
 * Devuelve la matriz de pesos de la capa oculta
 * @return Matriz Wi [18x35]
 */
public Matriz getWi(){
return this.wI;
}

/**
 *Obtener Wo
 * Devuelve la matriz de pesos de la capa de salida
 * @return Matriz Wo[10x18]
 */
public Matriz getWo(){
return this.wO;
}

/**
 *Obtener patrones de entrada
 * Devuelve la matriz con los patrones de entrada de entrenamiento
 * @return Matriz patronesEntrada[35x10]
 */
public Matriz getTrainMP(){
return this.trainMP;
}

/**
 *Obtener salidas objetivo
 * devuelve la matriz con los objetivos de entrenamiento
 * @return Matriz con los vectores de salida
 * @ Matriz salidas [10x10]
 */
public Matriz getTrainMT(){
return this.trainMT;
}


/**
 *fNetLog
 * Evalúa la  función sigmoidal 1/(1+e^-n) de un valor dado
 * @param value valor a evaluar
 * @return
 * @return1/(1+e^-value)
 */
public static double fNetLog(double value){
double num=1;
double den=0;
double f;
double exp=(-1)*value;
den=(1 + Math.pow(Math.E, (exp)) );
f=num/den;
return f;
}

/**
 *f prima de net
 * Evalúa la primera derivada de la función sigmoidal 1/(1+e^-n)
 * @param value valor a evaluar
 * @return (1/(1+e^-n))*(1-1/(1+e^-n))
 */
public static double fPrimaNetLog(double value){
double f;

f=Red.fNetLog(value)*(1-Red.fNetLog(value));
return f;
}



/**
 *F (net) a matriz
 * Evalúa la función sigmoidal a toda una matriz
 * @param mat matriz a evaluar
 * @return Matriz con sus elementos evaluados
 */
public static Matriz fNetMatrizLog(Matriz mat){
  double retorno[][];
  retorno=new double[mat.getFil()][mat.getCol()];
  Matriz res = new Matriz(retorno);

  for (int i=0;i<mat.getFil();i++){

        for (int j=0;j<mat.getCol();j++){

        res.setFC(i, j, Red.fNetLog(mat.getFC(i, j)));

        }

 }
return res;

}

/**
 *Evaluar f'(net) a matriz
 * Evalúa la derivada de la función sigmoidal a toda una matriz
 * @param mat matriz a evaluar
 * @return Matriz con sus elementos evaluados
 */
public static Matriz fNetPrimaMatrizLog(Matriz mat){
  double retorno[][];
  retorno=new double[mat.getFil()][mat.getCol()];
  Matriz res = new Matriz(retorno);

  for (int i=0;i<mat.getFil();i++){

        for (int j=0;j<mat.getCol();j++){

        res.setFC(i, j, Red.fPrimaNetLog(mat.getFC(i, j)));

        }

 }
return res;

}


/**
 *Simular Red Neuronal
 * Simula el comportamiento de la red neuronal.
 * Multiplica matrices, evalua las funciones de propagación y en general
 * propaga la red hacia adelante.
 * @param redNeu Red Neuronal modelo
 * @param Ventrada Vector de entrada a evaluar[35x1]
 * @return Matriz con un vector de respuestas de la red [10x1]
 */
public static Matriz simNet(Red redNeu, Matriz Ventrada){
//Ventrada 35x1
Matriz netI;
Matriz fNetI;

Matriz netO;
Matriz fNetO;


//MULTIPLICACION [NET]=[WI][XI]
netI= Matriz.multiplicar(redNeu.getWi(), Ventrada);
fNetI = Red.fNetMatrizLog(netI);

//MULTIPLICACION [NET]=[WO][FNETO]
netO= Matriz.multiplicar(redNeu.getWo(), fNetI);
fNetO = Red.fNetMatrizLog(netO);

return fNetO;
//salida de 10x1
}

/**
 *Establecer épocas
 * Establece el número de épocas de entrenamiento han transcurrido
 * en un proceso de entrenamiento tras la condición de salida
 * @param num contador de épocas
 */
public void setEpocas(int num){
this.epocas=num;
}

/**
 *Obtener el número de épocas
 * Devuelve el número de épocas que tuvo una red para alcanzar la condición
 * de salida.
 * @return int numEpocas
 */
public int getEpocas(){
return this.epocas;
}

/**
 *Obtener Error cuadrático
 * @param errores Matriz con los errores (yd-o)[10x1]
 * @return Double con la sumatoria de los errores al cuadrado multiplicado
 * por 1/2
 */
public static double getErrorCuadratico(Matriz errores){
double tmp=0;
        for (int i=0;i<errores.getFil();i++){
            tmp+=Math.pow(errores.getFC(i, 0), 2);
        }
tmp=tmp*0.5;
return tmp;
}

/**
 *Entrenar red neuronal
 * Entrena la red neuronal con el siguiente algoritmo:
 * <br>1.Se inicializa a red (valores aleatorios de wi y wo)
 * <br>2.huboError=true;
 *<br>
 * <br>Ciclo1.Mientras (epocas < iteracionesMáximas)&& (huboError==true))
 * <br>Ciclo2 for j=0;j<10;j++
 *<br>      3.Se presenta el patrón j y se propaga la red hacia adelante
 *<br>     4 Se calcula el error
 *<br>      Cond1 .If error>error:
 *<br>              -huboError
 *<br>              -Se propaga la red hacia atrás
 *<br>              -Se actualizan los pesos
 *<br>             
 *<br>
 *<br>           fin del ciclo2
 *<br>            epocas++;
 * <br>         fin del cliclo1
 *<br>El algoritmo se termina cuando todos los patrones tengan un valor de
 *error medio cuadrático menor que el establecido como parámetro o cuando
 *se supera el número máximo de iteraciones
 *
 * @param redNeu Red neuronal modelo
 * @param alpha factor de aprendizaje
 * @param error error objetivo por patrón
 * @param iteraciones numero máximo de iteraciones del algoritmo
 * @return String cadena con los resultados del entrenamiento
 */
public static String trainNetLog(Red redNeu,double alpha,double error, int iteraciones){

Red.init(redNeu);
    //Ventrada 35x1
int contEpocas=0;
int j=0;
Matriz netI;
Matriz fNetI;
Matriz fNetPrimaI;
Matriz wI;
Matriz eI;
Matriz dI;

Matriz netO;
Matriz fNetO;
Matriz fNetPrimaO;
Matriz wO;
Matriz eO;
Matriz dO;

double errG[]=new double[10];
double errGvalue=0;
double errP;
boolean huboError=true;

redNeu.setEpocas(0);

while((contEpocas<iteraciones)&&huboError==true){

huboError=false;
    for (j=0;j<10;j++){
//********************PASO HACIA ADELANTE*************************************
        //PROPAGAR LA CAPA OCULTA
        //[18x1] = [18x35] * [35x1] Net=WIxXI
        netI=Matriz.multiplicar(redNeu.getWi(), redNeu.getTrainMP().getColumna(j));
        //[18x1] = f([18x1])
        fNetI=Red.fNetMatrizLog(netI);

        //PROPAGAR LA SALIDA
        //[10x1] = [10x18] * [18x1]
        netO=Matriz.multiplicar(redNeu.getWo(),fNetI);
        //[10x1] = f([10x1])
        fNetO= Red.fNetMatrizLog(netO);

        //CALCULAR LOS ERRORES
        //[10x1] = [10x1] - [10x1] (yd-o)
        eO=Matriz.restar(redNeu.getTrainMT().getColumna(j), fNetO);

        //calcular el error cuadrático
        errP=Red.getErrorCuadratico(eO);
        errG[j]=errP;

//**************CONDICION DE ERROR : PASO HACIA ATRÁS**************************
        if(errP>error){

          huboError=true;

               //SE CALCULAN LAS DERIVADAS
               //[18x1] = f'([18x1])
                fNetPrimaI=Red.fNetPrimaMatrizLog(netI);

              //[10x1] = f'([10x1])
                fNetPrimaO=Red.fNetPrimaMatrizLog(fNetO);


            //CALCULAR dO
            //[10x1]=e[10x1]xe[10x1]  = (yd-o)*f'(netO)
            dO=Matriz.multElementos(eO, fNetPrimaO);


            //Calcular dI .. error propagado
            //[18x10]=[10x18]'T
            Matriz woT=Matriz.transponer(redNeu.getWo());
            //[18x1]=[18x10]x[10x1]
            Matriz tmp=Matriz.multiplicar(woT,dO);
            //[18x1]=e[18x1]xe[18x1]
            dI=Matriz.multElementos(tmp,fNetPrimaI);

            //ACTUALIZAR LOS PESOS
            //[10x18]=[10x1][18x1]'T
            Matriz deltaWO=Matriz.multEscalar(Matriz.multiplicar(dO,Matriz.transponer(fNetI)),alpha);
            wO=Matriz.sumar(redNeu.getWo(), deltaWO);

            //[18x35]=[18x1][35x1]'T
            Matriz deltaWI=Matriz.multEscalar(Matriz.multiplicar(dI,Matriz.transponer(redNeu.getTrainMP().getColumna(j))),alpha);
            wI=Matriz.sumar(redNeu.getWi(), deltaWI);


            //Se actualizan los pesos
            redNeu.setPesos(wI, wO);
           
            //Vuelve al primer patrón
      
        }
    }

 contEpocas++;//Una época más (iteración)
}
redNeu.setEpocas(contEpocas);


String ep=String.valueOf(redNeu.getEpocas());

if (huboError==false){

for (int i=0;i<10;i++)errGvalue+=errG[i];
 String errorG=String.valueOf(errGvalue);   
    return "¡Red entrenada con éxito!\n"+"Épocas: "+ep+"\nValor de error alcanzado\nError Global: "+errorG+"\n";

}else{
return "¡Red entrenada con éxito!\n"+"Épocas: "+ep+"\nValor de error NO alcanzado\n";
}



}


}


