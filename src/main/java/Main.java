import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
  public static void main(String[] args) {
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.out.println("Logs from your program will appear here!");

    // Uncomment this block to pass the first stage
    //
     try {
       ServerSocket serverSocket = new ServerSocket(4221);
    //
    //   // Since the tester restarts your program quite often, setting SO_REUSEADDR
    //   // ensures that we don't run into 'Address already in use' errors
       serverSocket.setReuseAddress(true);
    //
       Socket clientSocket = serverSocket.accept(); // Wait for connection from client.
      BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

      String requestLine = reader.readLine();
      String[] parts = requestLine.split(" ");
      String method = parts[0];
      String path;

      String[] paths =parts[1].split("/");
     if (paths.length > 1){

       path = paths[1];
      } 
      else{
         path = "";
      }

      if(path.equals("echo")){
        String response = "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\nContent-Length: " + paths[2].length() + "\r\n\r\n" + paths[2];
        clientSocket.getOutputStream().write(
        response.getBytes()
      );
      } else if(path.equals("")){
            clientSocket.getOutputStream().write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
        
      } else{
        clientSocket.getOutputStream().write(
          "HTTP/1.1 404 Not Found\r\n\r\n".getBytes()
        );
      
      }

       System.out.println("accepted new connection");
     } catch (IOException e) {
       System.out.println("IOException: " + e.getMessage());
     }
  }
}
