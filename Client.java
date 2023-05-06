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
    }

    return result;
  }

  public static void optionsMenu() {
    System.out.print("\n-- Calculadora distribuída --\n\n");
    System.out.print("Menu de operações disponíveis\n");
    System.out.print("-----------------------------\n");
    System.out.print("Soma (+)\n");
    System.out.print("Subtração (-)\n");
    System.out.print("Multiplicação (*)\n");
    System.out.print("Divisão (/)\n");
    System.out.println("-----------------------------\n");
  }

  public static Boolean checkContinue(
    String resultCondition,
    Boolean condition
  ) {
    System.out.print("\nDeseja continuar? (sim/nao): ");
    resultCondition = scanner.nextLine();

    String formattedString = resultCondition.toLowerCase().trim();

    System.out.println();

    if (formattedString.equals("sim")) condition = true;
    if (formattedString.equals("nao")) condition = false;

    return condition;
  }

  public static void main(String[] args) throws IOException {
    try (
      Socket socket = new Socket("192.168.0.104", 9999);
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

      optionsMenu();

      while (condition) {
        System.out.print("Escolha o operador: ");
        String resultOperator = operator.readLine();

        String nameOperation = operatorName(resultOperator);

        System.out.print("\nDigite o primeiro número: ");
        String num1 = number.readLine();
        System.out.print("Digite o segundo número: ");
        String num2 = number.readLine();

        // Envia os dados para o servidor;
        outData.println(condition); //Verificação para o server;
        outData.println(resultOperator); //Operador;
        outData.println(num1); //Numero 1;
        outData.println(num2); //Numero 2;

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
