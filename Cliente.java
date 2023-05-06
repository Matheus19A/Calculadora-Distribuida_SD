import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

  public static void main(String[] args) throws IOException {
    try (
      Socket socket = new Socket("192.168.0.104", 9999);
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true); //PrintWriter é usado para enviar dados para o servidor;
      BufferedReader in = new BufferedReader(
        new InputStreamReader(socket.getInputStream())
      ); //BufferedReader é usado para receber dados do servidor;
      BufferedReader numero = new BufferedReader(
        new InputStreamReader(System.in)
      );
      BufferedReader operator = new BufferedReader(
        new InputStreamReader(System.in)
      );
      Scanner scanner = new Scanner(System.in);
    ) {
      System.out.print("Calculadora distribuída\n\n");
      System.out.print("-----------------------------\n");
      System.out.print("| Soma (+) |\n");
      System.out.print("| Subtração (-) |\n");
      System.out.print("| Multiplicação (*) |\n");
      System.out.print("| Divisão (/) |\n");
      System.out.println("-----------------------------\n");

      Boolean result = true;
      String teste;

      while (result) {
        System.out.print("Digite o operador: ");
        String ope = operator.readLine();

        System.out.println();

        System.out.print("Digite o primeiro número: ");
        String num1 = numero.readLine();
        System.out.print("Digite o segundo número: ");
        String num2 = numero.readLine();

        //Envia os números para o servidor;
        out.println(ope);
        out.println(num1);
        out.println(num2);

        out.println(result); //Mandando verificação para o server;

        System.out.println();

        //Retorna o resultado enviado pelo servidor;
        String resultOperations = in.readLine();
        System.out.println("Resultado da operação: " + resultOperations);
        System.out.println();

        System.out.print("Deseja continuar? (s/n): ");
        teste = scanner.nextLine();

        System.out.println();

        if (teste.equals("s")) {
          result = true;
        } else if (teste.equals("n")) {
          result = false;
        }
      }

      System.out.println();
      System.out.println("Programa finalizado!");
    } catch (IOException e) {
      System.err.println("Não foi possível se conectar com o servidor");
      System.err.println("Erro: " + e);
    }
  }
}
