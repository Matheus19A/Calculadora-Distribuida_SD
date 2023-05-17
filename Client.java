import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.Socket;
import java.text.Normalizer;
import java.util.Scanner;

public class Client {

  static Scanner scanner = new Scanner(System.in);
  static String host = "192.168.0.104"; //Altere para o seu endereço;
  static String port = "1223";

  public static String operatorName(String operator) {
    String result = "";

    switch (operator) {
      case "+":
        result = "soma";
        break;
      case "-":
        result = "Subtração";
        break;
      case "*":
        result = "Multiplicação";
        break;
      case "/":
        result = "Divisão";
        break;
      case "%":
        result = "Porcentagem";
        break;
      case "raiz":
        result = "Raiz quadrada";
        break;
      case "^":
        result = "Potenciação";
        break;
    }

    return result;
  }

  public static void optionsMenu() {
    System.out.print("\n-- Calculadora distribuida --\n\n");
    System.out.print("Menu de operacoes disponiveis\n");
    System.out.print("-----------------------------\n");

    if (port == "1224") {
      System.out.print("Soma (+)\n");
      System.out.print("Subtracao (-)\n");
      System.out.print("Multiplicacao (*)\n");
      System.out.print("Divisao (/)\n");
    } else if (port == "1223") {
      System.out.print("Porcentagem (%)\n");
      System.out.print("Raiz quadrada (raiz)\n");
      System.out.print("Potenciacao (^)\n");
    }
    System.out.println("-----------------------------\n");
  }

  public static Boolean checkContinue(
    String resultCondition,
    Boolean condition
  ) {
    System.out.print("\nDeseja continuar? (sim/nao): ");
    resultCondition = scanner.nextLine();

    String formattedString = resultCondition.toLowerCase().trim();

    if (formattedString.equals("sim")) condition = true;
    if (formattedString.equals("nao")) condition = false;

    return condition;
  }

  public static void main(String[] args) throws IOException {
    try (
      Socket socket = new Socket(host, 1223);
      PrintWriter outData = new PrintWriter(socket.getOutputStream(), true); // PrintWriter é usado para enviar dados para
      // o servidor;
      BufferedReader inData = new BufferedReader(
        new InputStreamReader(socket.getInputStream())
      ); // BufferedReader é usado para receber dados do servidor;
      BufferedReader number = new BufferedReader(
        new InputStreamReader(System.in)
      );
      BufferedReader operator = new BufferedReader(
        new InputStreamReader(System.in)
      );
    ) {
      Boolean condition = true;
      String resultCondition = "";
      int cont = 0;

      if (cont == 0) optionsMenu();

      while (condition) {
        if (cont != 0) optionsMenu();
        cont++;

        System.out.print("Escolha o operador: ");
        String resultOperator = operator.readLine();

        String nameOperation = operatorName(resultOperator);

        if (resultOperator.equals("%")) {
          System.out.print("\nDigite o número: ");
          String num1 = number.readLine();
          System.out.print("Digite a taxa: ");
          String taxa = number.readLine();

          // Envia os dados para o servidor;
          outData.println(condition);
          outData.println(resultOperator);
          outData.println(num1);
          outData.println(taxa);
        } else if (resultOperator.equals("raiz")) {
          System.out.print("\nDigite um número: ");
          String num1 = number.readLine();

          outData.println(condition);
          outData.println(resultOperator);
          outData.println(num1);
        } else if (resultOperator.equals("^")) {
          System.out.print("\nDigite a base: ");
          String num1 = number.readLine();
          System.out.print("Digite o expoente: ");
          String num2 = number.readLine();

          outData.println(condition);
          outData.println(resultOperator);
          outData.println(num1);
          outData.println(num2);
        } else {
          System.out.print("\nDigite o primeiro número: ");
          String num1 = number.readLine();
          System.out.print("Digite o segundo número: ");
          String num2 = number.readLine();

          outData.println(condition); //Verificação para o server;
          outData.println(resultOperator); //Operador;
          outData.println(num1); //Numero 1;
          outData.println(num2); //Numero 2;
        }

        // Recebe o resultado enviado pelo servidor;
        String resultOperation = inData.readLine();
        System.out.println(
          "\nResultado da operação de " + nameOperation + ": " + resultOperation
        );

        condition = checkContinue(resultCondition, condition);
      }

      System.out.println("Programa finalizado!");
    } catch (IOException e) {
      System.err.println(
        "Não foi possível se conectar com o servidor devido ao erro:" + e
      );
    }
  }
}
