package Bai_tap_thuc_hanh_mau;

import java.io.File;

public class Ex2 {

    public static int countFile(String path) {
        File f = new File(path);
        int c = 0;
        String[] fileNames = f.list();
        File f1 = null;
        for (int i = 0; i< fileNames.length; i++) {
            f1 = new File(path + "\\" + fileNames[i]);
            if (f1.isDirectory()) c += countFile(f1.getAbsolutePath());
            else c++;
        }
        return c;
    }

    public static void main(String[] args) {
        System.out.println(countFile("D:\\Java_Project\\IOStreamPractice\\src"));
    }
}
