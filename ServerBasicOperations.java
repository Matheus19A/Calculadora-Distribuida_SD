import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerBasicOperations {
  public static float sum(String num1, String num2) {
    return Float.parseFloat(num1) + Float.parseFloat(num2);
  }

  public static float subtraction(String num1, String num2) {
    return Float.parseFloat(num1) - Float.parseFloat(num2);
  }

  public static float division(String num1, String num2) {
    return Float.parseFloat(num1) / Float.parseFloat(num2);
  }

  public static float multiplication(String num1, String num2) {
    return Float.parseFloat(num1) * Float.parseFloat(num2);
  }

  public static void main(String[] args) { 

    try {
      ServerSocket serverSocket = new ServerSocket(1224); //responsável por aguardar a conexão do cliente
      System.out.println("Servidor 1 iniciado.");

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
        float operationResult = 0;

        String resultOperator = inData.readLine(); //Operator;
        String num1 = inData.readLine(); //Number 1;
        String num2 = inData.readLine(); //Number 2;

        switch (resultOperator) {
          case "+":
            operationResult = sum(num1, num2);
            outData.println(operationResult);
            break;
          case "-":
            operationResult = subtraction(num1, num2);
            outData.println(operationResult);
            break;
          case "/":
            operationResult = division(num2, num2);
            outData.println(operationResult);
            break;
          case "*":
            operationResult = multiplication(num1, num1);
            outData.println(operationResult);
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
