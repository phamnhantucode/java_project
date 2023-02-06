package Bai_tap_thuc_hanh_mau;

import java.io.*;

public class Ex9 {

    public static void copy(String pathin, String pathout) throws IOException {
        FileReader reader = null;
        BufferedReader bfr = null;
        FileWriter writer = null;
        BufferedWriter bfw = null;
        try {
            reader = new FileReader(pathin);
            bfr = new BufferedReader(reader);
            writer = new FileWriter(pathout);
            bfw = new BufferedWriter(writer);
            String s, sa = "";
            while ((s = bfr.readLine()) != null) {
                sa = sa + s + "\n";
            }
            bfw.write(sa);
            bfw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (reader != null) {
                reader.close();
            }
            if (writer != null) {
                reader.close();
            }
            if (bfr != null) {
                reader.close();
            }
            if (bfw != null) {
                reader.close();
            }
        }
    }
    public static void main(String[] args) throws IOException {
        copy("in.txt", "out.txt");
    }
}
