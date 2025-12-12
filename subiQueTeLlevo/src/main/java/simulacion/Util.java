package simulacion;

import java.util.Random;

/**
 *Clase utilizada para generar numeros random donde sea necesario
 **/
public class Util {

    public static final Random r = new Random();

    public static void espera(int milisegundos)
    {
	try
	{
	    Thread.sleep(milisegundos);
	} catch (InterruptedException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

    public static void espera()
    {
	Util.espera(1000+rand(2000,7000));
    }
 
    public static int rand(int limite)
    {
        return r.nextInt(limite);
    }
    
    public static int rand(int limiteInf,int limiteSup)
    {
        if(limiteSup>1)
         return limiteInf + r.nextInt(limiteSup - limiteInf);
        else
         return 1;
    }
}
