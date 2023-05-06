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
      ServerSocket serverSocket = new ServerSocket(9999); //ServerSocket é responsável por aguardar conexões dos clientes na porta definida anteriormente.
      Socket clientSocket = serverSocket.accept(); //Se conecta com o cliente;
      PrintWriter outData = new PrintWriter(
        clientSocket.getOutputStream(),
        true
      ); //Envia dados para o cliente;
      BufferedReader inData = new BufferedReader(
        new InputStreamReader(clientSocket.getInputStream())
      ); //Recebe os dados do cliente;
    ) {
      System.out.println("Conexão estabelecida com o cliente");

      int resultOperation;

      //Recebendo os numeros e o operador enviados pelo cliente;
      String operation = inData.readLine();
      String num1 = inData.readLine();
      String num2 = inData.readLine();

      System.out.print(num1);
      System.out.print(num2);

      Boolean result = Boolean.parseBoolean(inData.readLine());

      while (result) {
        switch (operation) {
          case "+":
            resultOperation = Integer.parseInt(num1) + Integer.parseInt(num2);
            outData.println(resultOperation);
            break;
          case "-":
            break;
          case "/":
            break;
          case "*":
            break;
          default:
            System.out.println("Nenhuma resposta encontrada");
        }

        if (result == true) {
          operation = inData.readLine();
          num1 = inData.readLine();
          num2 = inData.readLine();
        }
      }

      inData.close();
      outData.close();
      clientSocket.close();
    } catch (IOException e) {
      System.err.println("Não foi possível se conectar com o cliente.");
      System.err.println("Erro: " + e);
    }
  }
}
