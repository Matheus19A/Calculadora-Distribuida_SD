import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerComplexOperations {

  public static Double power(Double base, Double exponent) {
    double result = 1;

    for (int i = 0; i < exponent; i++) {
      result *= base;
    }

    return result;
  }

  public static Double percentage(Double number, Double rate) {
    return (number * rate) / 100.0;
  }

  public static void main(String[] args) {
    try {
      ServerSocket serverSocket = new ServerSocket(1223); //responsável por aguardar a conexão do cliente
      System.out.println("Servidor 2 iniciado.");

      Socket clientSocket = serverSocket.accept(); // Se conecta com o cliente;

      PrintWriter outData = new PrintWriter(
        clientSocket.getOutputStream(),
        true
      ); // Envia dados para o cliente;
      BufferedReader inData = new BufferedReader(
        new InputStreamReader(clientSocket.getInputStream())
      ); // Recebe os dados do cliente;

      Boolean condition = Boolean.parseBoolean(inData.readLine()); //Recebe o condition do cliente;

      while (condition) {
        double result = 0, number = 0, rate = 0, source = 0, base =
          0, exponent = 0;

        String resultOperator = inData.readLine();

        if (resultOperator.equals("%")) {
          number = Double.parseDouble(inData.readLine());
          rate = Double.parseDouble(inData.readLine());
        } else if (resultOperator.equals("raiz")) {
          source = Double.parseDouble(inData.readLine());
        } else if (resultOperator.equals("^")) {
          base = Double.parseDouble(inData.readLine());
          exponent = Double.parseDouble(inData.readLine());
        }

        switch (resultOperator) {
          case "%":
            result = percentage(number, rate);
            outData.println(result);
            break;
          case "raiz":
            result = Math.sqrt(source);
            outData.println(result);
            break;
          case "^":
            result = power(base, exponent);
            outData.println(result);
            break;
          default:
            System.out.println("Nenhuma resposta encontrada");
        }

        condition = Boolean.parseBoolean(inData.readLine());
      }

      inData.close();
      outData.close();
      clientSocket.close();
      serverSocket.close();
    } catch (IOException e) {
      System.err.println(
        "Não foi possível se conectar com o cliente devido ao erro: " + e
      );
    }
  }
}
