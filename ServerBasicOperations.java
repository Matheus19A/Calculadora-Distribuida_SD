import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

//Executar as 4 operaçoes básicas -> Soma, subtração, divisão e multiplicação.

public class ServerBasicOperations {

  public static void main(String[] args) {
    try (
      ServerSocket serverSocket = new ServerSocket(9999); // ServerSocket é responsável por aguardar conexões dos
      // clientes na porta definida anteriormente.
      Socket clientSocket = serverSocket.accept(); // Se conecta com o cliente;
      PrintWriter outData = new PrintWriter(
        clientSocket.getOutputStream(),
        true
      ); // Envia dados para o cliente;
      BufferedReader inData = new BufferedReader(
        new InputStreamReader(clientSocket.getInputStream())
      ); // Recebe os dados do cliente;
    ) {
      int operationResult;

      System.out.println("\nConexão estabelecida com o cliente");

      Boolean condition = Boolean.parseBoolean(inData.readLine()); //Recebe o condition do cliente;

      while (condition) {
        String resultOperator = inData.readLine(); //Operador;
        String num1 = inData.readLine(); //Número 1;
        String num2 = inData.readLine(); //Número 2;

        switch (resultOperator) {
          case "+":
            operationResult = Integer.parseInt(num1) + Integer.parseInt(num2);
            outData.println(operationResult);
            break;
          case "-":
            operationResult = Integer.parseInt(num1) - Integer.parseInt(num2);
            outData.println(operationResult);
            break;
          case "/":
            operationResult = Integer.parseInt(num1) / Integer.parseInt(num2);
            outData.println(operationResult);
            break;
          case "*":
            operationResult = Integer.parseInt(num1) * Integer.parseInt(num2);
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
    } catch (IOException e) {
      System.err.println(
        "Não foi possível se conectar com o cliente devido ao erro: " + e
      );
    }
  }
}
