
package Control;

import GUI.TableroFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JOptionPane;


public class HacerJugadaActionListener implements ActionListener {
    private int coordenada;
    private JButton boton;
    private DataOutputStream salida;
    private String miFigura;
    private TableroFrame tablero;
    
    public HacerJugadaActionListener(int x, int y, JButton boton,
            DataOutputStream salida, String miFigura, TableroFrame tablero){
        coordenada=(y*10)+x;
        this.boton=boton;
        this.salida=salida;
        this.miFigura=miFigura;
        this.tablero=tablero;
    }
    

    @Override
    public void actionPerformed(ActionEvent ae) {
        try{
            if(tablero.getTurno()){
                if(boton.getText().equals("")){
                    boton.setText(miFigura);
                    salida.writeInt(coordenada);
                    salida.flush();
                    if(tablero.verificarGanador()==1){
                        JOptionPane.showMessageDialog(
                                null, "¡Felicidades "+miFigura+", ganaste!"
                        );
                        salida.writeInt(100);
                        salida.flush();
                    }
                    else if(tablero.verificarGanador()==2){
                        JOptionPane.showMessageDialog(
                                null, "Parece que tenemos un empate"
                        );
                        salida.writeInt(100);
                        salida.flush();
                    }
                    tablero.setTurno(false);
                }
                else{
                    JOptionPane.showMessageDialog(
                            null, "Este lugar ya esta ocupado"
                    );
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "No es tu turno");
            }
        } catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
}
