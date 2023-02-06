package Bai_tap_thuc_hanh_mau;


import java.io.*;
import java.nio.Buffer;

public class Ex4_2 {
    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader("D:\\Java_Project\\IOStreamPractice\\src\\Bai_tap_thuc_hanh_mau\\Ex4.java");
        BufferedReader br = new BufferedReader(fr);
        String s;
        while ((s = br.readLine()) != null) {
            System.out.println(s);

        }
        fr.close();
    }
}
