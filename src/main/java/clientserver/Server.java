package clientserver;

import matrixmultiply.MultiplicationAlgorithm;
import matrixmultiply.fox.forkjoin.FoxForkJoinAlgorithm;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static clientserver.Settings.MATRIX_SIZE;
import static clientserver.Settings.SERVER_SIDE_MULTIPLY_COMMAND;
import static matrixmultiply.MultiplicationUtils.generateRandomMatrix;

public class Server {
    public static void main(String[] args) {


        try (ServerSocket server = new ServerSocket(3345)) {

            Socket client = server.accept();

            System.out.print("Connection accepted.");


            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            System.out.println("DataOutputStream  created");

            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            System.out.println("DataInputStream created");

            while (!client.isClosed()) {

                System.out.println("Server reading from channel");


                String entry = (String) in.readObject();

                System.out.println("READ from client message - " + entry);

                System.out.println("Server try writing to channel");

                if (entry.equalsIgnoreCase("quit")) {
                    System.out.println("Client initialize connections suicide ...");
                    out.writeUTF("Server reply - " + entry + " - OK");
                    out.flush();
                    break;
                }

                if (entry.equals(SERVER_SIDE_MULTIPLY_COMMAND)) {
                    System.out.println("server-side matrix multiply starts");

                    int[][] result = new int[MATRIX_SIZE][MATRIX_SIZE];
                    int[][] first = generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);
                    int[][] second = generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);

                    MultiplicationAlgorithm foxForkJoinAlgorithm = new FoxForkJoinAlgorithm();
                    foxForkJoinAlgorithm.multiplyMatrix(first, second, result);

                    System.out.println("server-side matrix multiply is finished");

                    out.writeObject(result);

                    System.out.println("Server Wrote message to client.");
                }

//                out.writeObject("Server reply - " + entry + " - OK");
//                System.out.println("Server Wrote message to client.");

                out.flush();

            }


            System.out.println("Client disconnected");
            System.out.println("Closing connections & channels.");


            in.close();
            out.close();

            client.close();

            System.out.println("Closing connections & channels - DONE.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
