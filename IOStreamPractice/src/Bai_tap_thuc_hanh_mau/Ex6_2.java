package Bai_tap_thuc_hanh_mau;

import java.io.FileWriter;
import java.io.IOException;

public class Ex6_2 {
    public static void main(String[] args) {
        String source = "Programming with IO Stream in Java";
        try {
            FileWriter f = new FileWriter("./test.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
