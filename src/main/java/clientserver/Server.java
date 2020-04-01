package clientserver;

import matrixmultiply.MultiplicationAlgorithm;
import matrixmultiply.fox.forkjoin.FoxForkJoinAlgorithm;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static clientserver.Settings.*;
import static matrixmultiply.MultiplicationUtils.generateRandomMatrix;

public class Server {
    public static void main(String[] args) {


        try (ServerSocket server = new ServerSocket(3345)) {

            Socket client = server.accept();

            System.out.print("Connection accepted.");

            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(client.getInputStream());

            while (!client.isClosed()) {

                System.out.println("Server reading from channel");

                String entry = (String) in.readObject();

                if (entry.equals(SERVER_SIDE_MULTIPLY_COMMAND)) {

                    System.out.println("Server-side matrix multiply starts");

                    int[][] result = new int[MATRIX_SIZE][MATRIX_SIZE];
                    int[][] first = generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);
                    int[][] second = generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);

                    MultiplicationAlgorithm foxForkJoinAlgorithm = new FoxForkJoinAlgorithm();
                    foxForkJoinAlgorithm.multiplyMatrix(first, second, result);

                    System.out.println("Server-side matrix multiply is finished");

                    out.writeObject(result);
                }

                if (entry.equals(CLIENT_SIDE_MULTIPLY_COMMAND)) {
                    System.out.println("Client-side matrix multiply starts");

                    int[][] result = new int[MATRIX_SIZE][MATRIX_SIZE];

                    int[][] first = (int[][]) in.readObject();
                    int[][] second = (int[][]) in.readObject();

                    MultiplicationAlgorithm foxForkJoinAlgorithm = new FoxForkJoinAlgorithm();
                    foxForkJoinAlgorithm.multiplyMatrix(first, second, result);

                    System.out.println("Client-side matrix multiply is finished");

                    out.writeObject(result);
                }

                out.flush();
            }
            in.close();
            out.close();

            client.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
