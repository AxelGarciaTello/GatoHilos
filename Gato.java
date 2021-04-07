
import GUI.TableroFrame;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import javax.swing.JOptionPane;


public class Gato {

    
    public static void main(String[] args) {
        int i;
        int jugadas = Integer.parseInt(
                JOptionPane.showInputDialog(
                        "Ingrese el número de jugadas a realizar"
                )
        );
        for(i=0;i<jugadas;i++){
            iniciarJuego(i+1);
        }
    }
    
    private static void iniciarJuego(int numero){
        try{
            boolean iniciaCirculo;
            boolean iniciaTache;
            
            PipedOutputStream outcirtac = new PipedOutputStream();
            PipedInputStream  incirtac  = new PipedInputStream(outcirtac);
            
            PipedOutputStream outtaccir = new PipedOutputStream();
            PipedInputStream  intaccir  = new PipedInputStream(outtaccir);
            
            int aleatorio = (int) Math.floor(Math.random()*(100+1));
            int turno = aleatorio%2;
            if(turno==0){
                iniciaCirculo=true;
                iniciaTache=false;
                JOptionPane.showMessageDialog(null, "Inicia Circulo");
            }
            else{
                iniciaCirculo=false;
                iniciaTache=true;
                JOptionPane.showMessageDialog(null, "Inicia Tache");
            }
            
            TableroFrame circulo = new TableroFrame(
                    intaccir, outcirtac, 1, numero, iniciaCirculo
            );
            
            TableroFrame tache = new TableroFrame(
                    incirtac, outtaccir, -1, numero, iniciaTache
            );
            
            Thread hiloCirculo = new Thread(circulo);
            Thread hiloTache = new Thread(tache);
            
            hiloCirculo.start();
            hiloTache.start();
        } catch(IOException io){
            io.printStackTrace();
        }
    }
    
}
