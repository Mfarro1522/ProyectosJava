package com.mycompany.calculadoraswing;

import Igu.VentanaCalculadora;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class CalculadoraSwing {

        public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        
       //en linux para que el preview coincida con la ejecucion ; en el foco darle a aceptar todos los throws
       UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            
        VentanaCalculadora ventana = new VentanaCalculadora();
        ventana.setVisible(true);
    }
}
