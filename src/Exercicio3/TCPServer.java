package Exercicio3;

/**
 * TCPServer: Servidor para conexao TCP com Threads Descricao: Recebe uma
 * conexao, cria uma thread, recebe uma mensagem e finaliza a conexao
 */
import java.net.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TCPServer {

    private ArrayList<Socket> clientes;

    public TCPServer() {
        this.clientes = new ArrayList<Socket> ();
    }

    public void iniciarServidor() throws IOException {
        int serverPort = 6666; // porta do servidor

        ServerSocket listenSocket = new ServerSocket(serverPort);

        while (true) {
            System.out.println("Servidor aguardando conexao ...");    
            Socket clientSocket = listenSocket.accept();
            System.out.println("Cliente conectado ... Criando thread ...");
            clientes.add(clientSocket);
            ClientThread c = new ClientThread(clientSocket,clientes);
            c.start();
        } //
    }

    public static void main(String args[]) throws IOException {
        TCPServer t = new TCPServer();
        t.iniciarServidor();
    }

} //class

/**
 * Classe ClientThread: Thread responsavel pela comunicacao Descricao: Rebebe um
 * socket, cria os objetos de leitura e escrita, aguarda msgs clientes e
 * responde com a msg + :OK
 */
class ClientThread extends Thread {

    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;
    ArrayList<Socket> clientes;

    public ClientThread(Socket clientSocket,ArrayList<Socket> c) {
        try {
            this.clientSocket = clientSocket;
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
            clientes = c;
        } catch (IOException ioe) {
            System.out.println("Connection:" + ioe.getMessage());
        } //catch
    } //construtor

    /* metodo executado ao iniciar a thread - start() */
    @Override
    public void run() {
        try {
            String buffer = "";
            while (true) {
               
                buffer = in.readUTF();
                 
                for(int i = 0; i <clientes.size(); i++){
                   if(buffer == "time" ){
                       SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                       Date d = new Date();
                      
                       System.out.println(dateFormat.format(d));
                       
                       
                   }
                   else if(buffer == "date"){
                   
                   }
                   else if(buffer == "file"){
                   
                   }
                    DataOutputStream out = new DataOutputStream(clientes.get(i).getOutputStream());
                   out.writeUTF(buffer);
                
                }

                
                
                
            }
        } catch (EOFException eofe) {
            System.out.println("EOF: " + eofe.getMessage());
        } catch (IOException ioe) {
            System.out.println("IOE: " + ioe.getMessage());
        } finally {
            try {
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException ioe) {
                System.err.println("IOE: " + ioe);
            }
        }
        System.out.println("Thread comunicação cliente finalizada.");
    } //run
} //class
