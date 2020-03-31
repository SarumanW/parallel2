package clientserver;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {


        try (Socket socket = new Socket("localhost", 3345);
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());) {

            System.out.println("Client connected to socket.");
            System.out.println();
            System.out.println("Client writing channel = oos & reading channel = ois initialized.");

            while (!socket.isOutputShutdown()) {

                if (br.ready()) {

                    System.out.println("Client start writing in channel...");
                    String clientCommand = br.readLine();

                    oos.writeObject(clientCommand);
                    oos.flush();
                    System.out.println("Client sent message " + clientCommand + " to server.");

                    if (clientCommand.equalsIgnoreCase("quit")) {
                        System.out.println("Client kill connections");
                        System.out.println("reading...");
                        String in = (String) ois.readObject();
                        System.out.println(in);
                        break;
                    }

                    System.out.println("Client sent message & start waiting for data from server...");

                    System.out.println("reading...");
                    int[][] in = (int[][]) ois.readObject();
                    //MultiplicationUtils.printMatrix(in);

                    System.out.println(in.length);

                }
            }

            System.out.println("Closing connections & channels on clentSide - DONE.");

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
