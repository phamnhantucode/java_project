package Bai_tap_thuc_hanh_mau;

import java.io.File;

public class Ex1 {
    public Ex1 (String path){
        File f = new File(path);
        String[] filenames = f.list();

        for (int i=0; i<filenames.length; i++) {
            System.out.println(filenames[i]);
        }
    }

    public static void main(String[] args) {
        new Ex1("D://");
    }
}
