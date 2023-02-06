package Bai_tap_tu_giai;

import java.io.File;

public class Ex1 {

    public static int size(String path) {
        File f = new File(path);
        int result = 0;
        if (f.isFile()) return (int) f.length();
        String[] fileNames = f.list();
        for (int i = 0; i < fileNames.length; i++) {
            result += size(path + "\\" + fileNames[i]);
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(size("D:\\Java_Project\\IOStreamPractice\\src\\Bai_tap_thuc_hanh_mau"));
    }

}
