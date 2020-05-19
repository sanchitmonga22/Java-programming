package common;

/**
 * author: Sanchit Monga
 * author: Helen Healy
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * This class helps you to open the input and output streams and you can send data from server to client and vice versa
 */
public class Duplexer implements AutoCloseable{
    private Socket socket;
    private Scanner in;
    private PrintWriter out;
    public Duplexer(Socket socket) throws IOException{
        this.socket=socket;
        in=new Scanner(socket.getInputStream());// opening the socket for reading the data
        out=new PrintWriter(socket.getOutputStream());// opening the socket in the writing mode
    }

    /**
     * @param message: The message that has to be sent
     */
    public void send(String message){
        out.println(message);// sending the message
        out.flush();
    }
    /**
     * @return: The message that was read
     */
    public String read(){
        return in.nextLine();// reading the input
    }
    /**
     * @return: Whether or not there is a message being received
     */
    public boolean nextLine(){
        return in.hasNextLine();
    }
    @Override
    public void close() throws Exception {
        socket.close();
    }
}
//hasnextline

