package UDPViettuts;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer {
    private static final int PIECES_OF_FILE_SIZE = 1024 * 32;
    private DatagramSocket serverSocket;
    private int port = 6677;

    private void openServer() {
        try {
            serverSocket = new DatagramSocket(port);
            System.out.println("Server is opened onn port " + port);
            listening();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void receiveFile() {
        byte[] receiveData = new byte[PIECES_OF_FILE_SIZE];
        DatagramPacket receivePacket;

        try {
            //getFileInfo
            receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            InetAddress inetAddress = receivePacket.getAddress();
            ByteArrayInputStream bais = new ByteArrayInputStream(receivePacket.getData());
            ObjectInputStream ois = new ObjectInputStream(bais);
            FileInfo fileInfo = (FileInfo) ois.readObject();
            //Show file info
            if (fileInfo != null) {
                System.out.println(fileInfo);
            }
            // get file content
            System.out.println("Receiving file...");
            File fileReceive = new File(fileInfo.getDestinationDirectory() + fileInfo.getFileName());
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileReceive));
            // write pieces of file
            for (int i = 0; i < (fileInfo.getPiecesOfFile() - 1); i++) {
                receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                bos.write(receiveData, 0 , PIECES_OF_FILE_SIZE);
//                may be
//                bos.write(receiveData, 0 , receivePacket.getLength()); if n == fileInfo
            }
            //write last bytes of file
            receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            bos.write(receiveData, 0 , fileInfo.getLastbyteLength());
            bos.flush();
            System.out.println("Done!");

            //close stream
            bos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void listening() {
        while (true) {
            receiveFile();
        }
    }

    public static void main(String[] args) {
        UDPServer udpServer = new UDPServer();
        udpServer.openServer();
    }
}
