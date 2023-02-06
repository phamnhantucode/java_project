import java.io.*;
import java.net.*;

import static java.util.regex.Pattern.compile;

class TCPServer
{
    String text;
    ServerSocket server;
    BufferedReader stream_in;
    PrintWriter stream_out;


    public TCPServer()
    {
        try{
            server = new ServerSocket(7000);

            System.out.println("Server is starting...");

            Socket socket = server.accept();

            stream_in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            DataOutputStream stream_out = new DataOutputStream(socket.getOutputStream());
            stream_out = new PrintWriter(socket.getOutputStream(), true);
            while(true)
            {
                text = stream_in.readLine();
                System.out.println("Received: " + text);


//                stream_out.writeBytes(text);

                stream_out.println(compiler(text));
            }
        }
        catch(Exception e) {e.printStackTrace();}
    }

    public static boolean isNumeric(String str) {
        return str.matches("\\d+");
    }

    public int compiler(String text) {
        int answer = 0;
        try {

            int location = 0;

            String num = "";
            while (isNumeric(String.valueOf(text.charAt(location)))) {
                num+= text.charAt(location);
                location++;
            }
            while (location < text.length()) {
                answer += Integer.parseInt(num);
                int tmp = location;
                location++;
                num = "";
                while (  location < text.length() && isNumeric(String.valueOf(text.charAt(location)))) {
                    num+= text.charAt(location);
                    location++;
                }
                if (text.charAt(tmp) == '+') {
                    answer += Integer.parseInt(num);
                }
                if (text.charAt(tmp) == '-') {
                    answer -= Integer.parseInt(num);
                }
                if (text.charAt(tmp) == '*') {
                    answer *= Integer.parseInt(num);
                }
                if (text.charAt(tmp) == '/') {
                    answer /= Integer.parseInt(num);
                }
            }

        } catch (Exception e) {
            stream_out.println("Error");
        }
        return answer;
    }

    public static void main(String argv[])
    {
        new TCPServer();
    }
}
