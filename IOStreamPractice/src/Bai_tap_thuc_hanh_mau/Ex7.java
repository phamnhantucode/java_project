package Bai_tap_thuc_hanh_mau;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Ex7 {
    public static void writeFile(String path) {
        DataOutputStream bf = null;
        String msg = "Programming with IO Strdddeam in java";
        try {
            bf = new DataOutputStream(new FileOutputStream(path));
            bf.writeChars(msg);
            bf.flush();        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if ( bf != null)
                try {
                    bf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    public static void main(String[] args) {
        writeFile("test.txt");
    }
}
