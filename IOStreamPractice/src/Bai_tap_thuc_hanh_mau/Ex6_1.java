package Bai_tap_thuc_hanh_mau;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Ex6_1 {
    public static void writer(String content, String path) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(path);

            for (int i = 0; i < content.length(); i++) {
                out.write((int) content.charAt(i));
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        writer("Programming with IO Stream in Java", "src/test.txt");
    }
}
