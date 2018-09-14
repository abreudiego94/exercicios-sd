package Exercicio3;

/**
 * TCPClient: Cliente para conexao TCP
 * Descricao: Envia uma informacao ao servidor e recebe confirmações ECHO
 * Ao enviar "PARAR", a conexão é finalizada.
 */

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class TCPClient {
   Socket clientSocket;
   DataInputStream in;
   DataOutputStream out;
   
   public void conectar(Integer porta,String url) throws UnknownHostException, IOException{
       InetAddress serverAddr = InetAddress.getByName(url);
       clientSocket = new Socket(serverAddr, porta);  
       in  = new DataInputStream( clientSocket.getInputStream());
       out = new DataOutputStream( clientSocket.getOutputStream());
   }
   public void enviarMensagem(String mensagem) throws IOException{
       out.writeUTF(mensagem);
   }
   
}

class ouvirMensagens extends Thread{
    TCPClient cliente;
    
}
//class
