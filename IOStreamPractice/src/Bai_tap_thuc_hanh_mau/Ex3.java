package Bai_tap_thuc_hanh_mau;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Ex3 {
    public static  int count(String path, char ch) {
        int c = 0;
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(path);
            int data;
            while ((data = fis.read()) != -1) {
                if (data == ch) {
                    c++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return c;
    }

    public static void main(String[] args) {
        System.out.print(count("D:\\Java_Project\\IOStreamPractice\\src\\Bai_tap_thuc_hanh_mau\\Ex3.java", 'i'));
    }
}
