package igu;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Juego extends JFrame {
    
    int anchoVentana = 600;
    int altoVentana = 650;
    
    JFrame ventana = new JFrame("3 En RAYA");
    JLabel labelTexto =  new JLabel();
    JPanel panelTexto =  new JPanel();
    JPanel panelCasillas =  new JPanel();

    JButton [][] casillas =  new JButton[3][3];
    String jugador_1 = "O";
    String jugador_2 = "X";
    String JugadorEnJuego = jugador_1;
    boolean gameOver = false;
    int turnos = 0;



    public Juego() {
        
        ventana.setSize(anchoVentana, altoVentana);
        ventana.setLocationRelativeTo(null);
        ventana.setResizable(false);//para que no pueda redimencionar
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLayout(new BorderLayout());//en un contenedor para poder ubicar elementos de forma predeterminada
        
        //barra de estado del juego
        labelTexto.setBackground(new Color(255,243,232));
        labelTexto.setForeground(Color.black);
        labelTexto.setFont(new Font("Arial", Font.BOLD ,50 ));
        labelTexto.setHorizontalAlignment(JLabel.CENTER);
        labelTexto.setText("3 EN RAYA");
        labelTexto.setOpaque(true);
        
        //PANEL QUE CONTIENE A EL LABEL DE TEXTO 
        panelTexto.setLayout(new  BorderLayout());
        panelTexto.add(labelTexto);//dentro de panel esta el label
        ventana.add(panelTexto,BorderLayout.NORTH);//dentro de frame es e panel ; north es arriba
        
        //panel de las casillas
        panelCasillas.setLayout(new GridLayout(3,3)); // este es layaout prederminado de 3 x 3 
        panelCasillas.setBackground(Color.WHITE);
        ventana.add(panelCasillas);
        //contruimos las casillas 
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j <3; j++) {
                //inicializamos cada casilla
                JButton temp =  new JButton();
                casillas[i][j] = temp;
                panelCasillas.add(temp);
                
                temp.setBackground(new Color(255,243,232));
                if(JugadorEnJuego.equals(jugador_1)){
                    temp.setForeground(Color.red);
                }else {
                     temp.setForeground(Color.blue);
                }
                temp.setFont(new Font("Arial", Font.BOLD ,120 ));
                temp.setFocusable(false);
                
                ventana.setVisible(true);
                //eventos
                temp.addActionListener((ActionEvent e) -> {
                    if (gameOver) return;
                    JButton temp1 = (JButton) e.getSource();
                    if (temp1.getText() == "") {
                        temp1.setText(JugadorEnJuego);
                        turnos++;
                        ganador();
                        if (!gameOver) {
                            JugadorEnJuego = JugadorEnJuego.equals(jugador_1)? jugador_2 : jugador_1;
                            labelTexto.setText("Turno de "+JugadorEnJuego);
                        }
                    }
                });
 
                
            }
        }

        
    }
    
    
    public void ganador(){
        //filas
        for (int i = 0; i < 3; i++) {
            if("".equals(casillas[i][0].getText()))continue;
            
           if (casillas[i][0].getText().equals(casillas[i][1].getText())
                    && casillas[i][1].getText().equals(casillas[i][2].getText())) {
                for (int j = 0; j < 3; j++) {
                    setGanador(casillas[i][j]);
                }
                gameOver = true;
                return;
            }
            
        }
        
        //columnas
        for (int i = 0; i < 3; i++) {
            if("".equals(casillas[0][i].getText()))continue;
            
            if(casillas[0][i].getText().equals(casillas[1][i].getText()) &&
                    casillas[1][i].getText().equals(casillas[2][i].getText())){
                for (int j = 0; j < 3; j++) {
                    setGanador(casillas[j][i]);
                }
                gameOver = true;
                return;
            }
            
        }

        //diagonal izq - derecha
        if(casillas[0][0].getText().equals(casillas[1][1].getText()) &&
                casillas[1][1].getText().equals(casillas[2][2].getText())&&
           !"".equals(casillas[0][0].getText())){
           
            for (int j = 0; j < 3; j++) {
                setGanador(casillas[j][j]);
            }
            gameOver =  true;
            return;
            }
        //diagonal derecha izquierda
        if(casillas[0][2].getText().equals(casillas[1][1].getText()) &&
           casillas[1][1].getText().equals(casillas[2][0].getText())&&
           !"".equals(casillas[0][2].getText())){
            
            setGanador(casillas[0][2]);
            setGanador(casillas[1][1]);
            setGanador(casillas[2][0]);
                 gameOver =  true;
                 return;
            }
        
        if (turnos == 9) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    setEmpate(casillas[i][j]);
                }
            }
            gameOver = true;
        }
            

        }
        
    
    void setGanador(JButton temp) {
        temp.setForeground(Color.black);
        temp.setBackground(Color.green);
        labelTexto.setText(JugadorEnJuego + " es fino!!!");
    }

    void setEmpate(JButton temp) {
        temp.setForeground(Color.black);
        temp.setBackground(Color.orange);
        labelTexto.setText("Empate");
    }
}
   
