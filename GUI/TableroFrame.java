
package GUI;

import Control.HacerJugadaActionListener;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.swing.JButton;
import javax.swing.JFrame;


public class TableroFrame extends JFrame implements Runnable{
    private JButton[][] tablero;
            
    private boolean turno;
    private DataInputStream entrada;
    private DataOutputStream salida;
    private String miFigura,
                   enemigo;
    
    public TableroFrame(InputStream entrada, OutputStream salida, int cirTac, 
            int numero, boolean turno){
        String titulo = "";
        this.entrada = new DataInputStream(entrada);
        this.salida = new DataOutputStream(salida);
        if(cirTac == 1){
            titulo = "Circulo ";
            miFigura = "O";
            enemigo = "X";
        }
        else{
            titulo = "Tache ";
            enemigo = "O";
            miFigura = "X";
        }
        this.setTitle(titulo+numero);
        this.turno=turno;
        this.initComponents();
        this.setVisible(true);
    }
    
    private void initComponents(){
        int i,
            j;
        this.setSize(450,450);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        this.setLayout(new GridLayout(3,3));
        
        tablero = new JButton[3][3];
        for(j=0; j<3; j++){
            for(i=0; i<3; i++){
                tablero[j][i] = new JButton();
                //tablero[j][i].setSize(140, 140);
                tablero[j][i].addActionListener(
                        new HacerJugadaActionListener(
                                i, j, tablero[j][i], salida, miFigura, this
                        )
                );
                tablero[j][i].setFont(new Font("Ubuntu", 0, 30));
                this.add(tablero[j][i]);
            }
        }
    }
    
    private void destruir(){
        int i,
            j;
        for(i=0; i<3; i++){
            for(j=0; j<3; j++){
                tablero[j][i]=null;
            }
        }
        if(entrada!=null){
            entrada=null;
        }
        if(salida!=null){
            salida=null;
        }
        if(miFigura!=null){
            miFigura=null;
        }
        if(enemigo!=null){
            enemigo=null;
        }
        System.gc();
    }

    @Override
    public void run() {
        boolean bandera = true;
        int respEnt = 0,
            x = 0,
            y = 0;
        while(bandera){
            try{
                respEnt = entrada.readInt();
                switch (respEnt) {
                    case 100:
                        salida.writeInt(101);
                        salida.flush();
                        bandera=false;
                        this.destruir();
                        this.dispose();
                        break;
                    case 101:
                        bandera=false;
                        this.destruir();
                        this.dispose();
                        break;
                    default:
                        x = respEnt%10;
                        y = ((respEnt%100)-x)/10;
                        tablero[y][x].setText(enemigo);
                        turno=true;
                        break;
                }
            } catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }
    
    public boolean getTurno(){
        return turno;
    }
    
    public void setTurno(boolean turno){
        this.turno=turno;
    }
    
    public int verificarGanador(){
        String[][] tablero = new String[3][3];
        int i,
            j;
        for(j=0; j<3; j++){
            for(i=0; i<3; i++){
                tablero[j][i]=this.tablero[j][i].getText();
            }
        }
        if( tablero[0][0].equals(tablero[0][1]) && 
            tablero[0][0].equals(tablero[0][2]) && 
           !tablero[0][0].equals("")){
            return 1;
        }
        if( tablero[1][0].equals(tablero[1][1]) && 
            tablero[1][0].equals(tablero[1][2]) && 
           !tablero[1][0].equals("")){
            return 1;
        }
        if( tablero[2][0].equals(tablero[2][1]) && 
            tablero[2][0].equals(tablero[2][2]) && 
           !tablero[2][0].equals("")){
            return 1;
        }
        if( tablero[0][0].equals(tablero[1][0]) && 
            tablero[0][0].equals(tablero[2][0]) && 
           !tablero[0][0].equals("")){
            return 1;
        }
        if( tablero[0][1].equals(tablero[1][1]) && 
            tablero[0][1].equals(tablero[2][1]) && 
           !tablero[0][1].equals("")){
            return 1;
        }
        if( tablero[0][2].equals(tablero[1][2]) && 
            tablero[0][2].equals(tablero[2][2]) && 
           !tablero[0][2].equals("")){
            return 1;
        }
        if( tablero[0][0].equals(tablero[1][1]) && 
            tablero[0][0].equals(tablero[2][2]) && 
           !tablero[0][0].equals("")){
            return 1;
        }
        if( tablero[0][2].equals(tablero[1][1]) && 
            tablero[0][2].equals(tablero[2][0]) && 
           !tablero[0][2].equals("")){
            return 1;
        }
        for(i=0; i<3; i++){
            for(j=0; j<3; j++){
                if(tablero[i][j].equals("")){
                    return 0;
                }
            }
        }
        return 2;
    }

}
