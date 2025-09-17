package clases;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;




public class Calculadora {
    
    private BigDecimal numero1;
    private BigDecimal numero2;
    private String operador;
    private BigDecimal resultado;

    public Calculadora() {
    }

    public List<String> tokenisar(String expr) {
        List<String> tokens = new ArrayList<>();
        expr = expr.replaceAll("\\s+", ""); 
        
        Scanner scanner = new Scanner(expr);
        scanner.useDelimiter(""); 
        
        StringBuilder currentToken = new StringBuilder();
        
        while (scanner.hasNext()) {
            String ch = scanner.next();
            
            if (ch.matches("[+\\-*/()]")) {

                if (currentToken.length() > 0) {
                    tokens.add(currentToken.toString());
                    currentToken.setLength(0);
                }
                tokens.add(ch);
            } else if (ch.matches("[0-9.]")) {
                currentToken.append(ch);
            } else {
                scanner.close();
                throw new IllegalArgumentException("Carácter inválido: '" + ch + "'");
            }
        }
        
        if (currentToken.length() > 0) {
            tokens.add(currentToken.toString());
        }
        
        scanner.close();
        validarTokens(tokens);
        return tokens;
    }
    
    private void validarTokens(List<String> tokens) {
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Expresión vacía");
        }
        
        // Validar paréntesis balanceados
        int balance = 0;
        for (String token : tokens) {
            if (token.equals("(")) balance++;
            else if (token.equals(")")) balance--;
            if (balance < 0) {
                throw new IllegalArgumentException("Paréntesis desbalanceados");
            }
        }
        if (balance != 0) {
            throw new IllegalArgumentException("Paréntesis desbalanceados");
        }
        
        // Validar que no termine en operador
        String ultimo = tokens.get(tokens.size() - 1);
        if ("+-*/".contains(ultimo)) {
            throw new IllegalArgumentException("No puede terminar con operador");
        }
        
        // Validar números bien formados
        for (String token : tokens) {
            if (token.matches("-?\\d*\\.\\d*")) {
                if (token.equals(".") || token.equals("-.") || token.endsWith(".")) {
                    throw new IllegalArgumentException("Número mal formado: " + token);
                }
            }
        }
    }
    
    

    
    public BigDecimal sumar (){
     return numero1.add(numero2);
    }
    
    public BigDecimal restar (){
     return numero1.subtract(numero2);
    }
    
    public BigDecimal multiplicar (){
     return numero1.multiply(numero2);
    }
    
    public BigDecimal porcentaje (){
     return numero2.multiply(numero1.divide(BigDecimal.valueOf(100.0)));
    }
    
    public BigDecimal dividir (){
        if(numero2.compareTo(BigDecimal.ZERO)==0){
            throw new IllegalArgumentException("Papi como vas a dividir entre 0");
        }
        return numero1.divide(numero2 , RoundingMode.HALF_UP);  
    }
    

    public BigDecimal getResultado() {
        return resultado;
    }
    
    public BigDecimal realizarOperacion(String operador) {
        switch (operador) {
            case "+":
                resultado = sumar();
                break;
            case "-":
                resultado = restar();
                break;
            case "*":
                resultado = multiplicar();
                break;
            case "/":
                resultado = dividir();
                break;
            case "%":
                resultado = porcentaje();
                break;
            default:
                throw new IllegalArgumentException("Operador no válido: " + operador);
        }
        return resultado;
    }
    
    public BigDecimal calcular(BigDecimal num1, String op, BigDecimal num2) {
        this.numero1 = num1;
        this.numero2 = num2;
        this.operador = op;
        return realizarOperacion(op);
    }
    
    public BigDecimal evaluarExpresion(String expresion) {
        List<String> tokens = tokenisar(expresion);
        return evaluarTokensUnaVez(tokens);
    }
    
    private BigDecimal evaluarTokensUnaVez(List<String> tokens) {
 
        while (tokens.contains("(")) {
            int inicio = -1, fin = -1;
            
            for (int i = 0; i < tokens.size(); i++) {
                if (tokens.get(i).equals("(")) {
                    inicio = i;
                } else if (tokens.get(i).equals(")")) {
                    fin = i;
                    break;
                }
            }

            List<String> subTokens = new ArrayList<>(tokens.subList(inicio + 1, fin));
            BigDecimal resultadoParentesis = evaluarSinParentesis(subTokens);
            
            for (int i = fin; i >= inicio; i--) {
                tokens.remove(i);
            }
            tokens.add(inicio, resultadoParentesis.toString());
        }
        
        return evaluarSinParentesis(tokens);
    }
    
    private BigDecimal evaluarSinParentesis(List<String> tokens) {

        
        int i = 1;
        while (i < tokens.size()) {
            String operador = tokens.get(i);
            if (operador.equals("*") || operador.equals("/") || operador.equals("%")) {
                BigDecimal num1 = new BigDecimal(tokens.get(i - 1));
                BigDecimal num2 = new BigDecimal(tokens.get(i + 1));
                
                BigDecimal resultado = ejecutarOperacionSwitch(num1, operador, num2);
                
                tokens.set(i - 1, resultado.toString());
                tokens.remove(i);    
                tokens.remove(i);     

            } else {
                i += 2;
            }
        }
        
        
        i = 1;
        while (i < tokens.size()) {
            String operador = tokens.get(i);
            if (operador.equals("+") || operador.equals("-")) {
                BigDecimal num1 = new BigDecimal(tokens.get(i - 1));
                BigDecimal num2 = new BigDecimal(tokens.get(i + 1));
                
                BigDecimal resultado = ejecutarOperacionSwitch(num1, operador, num2);
                
                tokens.set(i - 1, resultado.toString());
                tokens.remove(i);     
                tokens.remove(i);     

            } else {
                i += 2;
            }
        }
        
        return new BigDecimal(tokens.get(0));
    }
    
    private BigDecimal ejecutarOperacionSwitch(BigDecimal num1, String operador, BigDecimal num2) {
        switch (operador) {
            case "+":
                return num1.add(num2);
            case "-":
                return num1.subtract(num2);
            case "*":
                return num1.multiply(num2);
            case "/":
                if (num2.compareTo(BigDecimal.ZERO) == 0) {
                    throw new IllegalArgumentException("Papi como vas a dividir entre 0");
                }
                return num1.divide(num2, RoundingMode.HALF_UP);
            case "%":
                return num2.multiply(num1.divide(BigDecimal.valueOf(100.0)));
            default:
                throw new IllegalArgumentException("Operador no válido: " + operador);
        }
    }

}
