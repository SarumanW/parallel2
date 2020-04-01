package clientserver;

import java.io.*;
import java.net.Socket;

import static clientserver.Settings.*;
import static matrixmultiply.MultiplicationUtils.generateRandomMatrix;

public class Client {
    public static void main(String[] args) {


        try (Socket socket = new Socket("localhost", 3345);
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());) {

            System.out.println("Client connected to socket.");
            System.out.println();

            while (!socket.isOutputShutdown()) {

                if (br.ready()) {

                    String clientCommand = br.readLine();

                    if (clientCommand.equals(SERVER_SIDE_MULTIPLY_COMMAND)) {
                        long startTime = System.nanoTime();

                        oos.writeObject(clientCommand);
                        oos.flush();

                        getResultAndTest(startTime, ois);
                    }

                    if (clientCommand.equals(CLIENT_SIDE_MULTIPLY_COMMAND)) {
                        long startTime = System.nanoTime();

                        oos.writeObject(clientCommand);
                        oos.flush();

                        int[][] first = generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);
                        int[][] second = generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);

                        oos.writeObject(first);
                        oos.flush();

                        oos.writeObject(second);
                        oos.flush();

                        getResultAndTest(startTime, ois);
                    }
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void getResultAndTest(long startTime, ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        System.out.println("reading...");
        int[][] in = (int[][]) objectInputStream.readObject();
        //MultiplicationUtils.printMatrix(in);

        long endTime = System.nanoTime();
        System.out.println(in.length);

        long duration = (endTime - startTime) / 1000000;
        System.out.println(duration);
    }
}
