package Bai_tap_thuc_hanh_mau;

import java.io.FileInputStream;
import java.io.IOException;

public class Ex4 {
    public static void readFile(String path) {
        FileInputStream in = null;
        try {
            in = new FileInputStream(path);
            int data;
            while ((data = in.read()) != -1)
                System.out.print((char) data);
        } catch (Exception e) {

        }finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        readFile("D:\\Java_Project\\IOStreamPractice\\src\\Bai_tap_thuc_hanh_mau\\Ex4.java");
    }
}
