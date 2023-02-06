package UDPViettuts;

import java.io.Serializable;

public class FileInfo implements Serializable {

    private String destinationDirectory;
    private String sourceDirectory;
    private String fileName;
    private long fileSize;
    private int piecesOfFile;
    private int lastbyteLength;
    private String status;

    //Constructors

    // Getter and setter


    public String getDestinationDirectory() {
        return destinationDirectory;
    }

    public void setDestinationDirectory(String destinationDirectory) {
        this.destinationDirectory = destinationDirectory;
    }

    public String getSourceDirectory() {
        return sourceDirectory;
    }

    public void setSourceDirectory(String sourceDirectory) {
        this.sourceDirectory = sourceDirectory;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public int getPiecesOfFile() {
        return piecesOfFile;
    }

    public void setPiecesOfFile(int piecesOfFile) {
        this.piecesOfFile = piecesOfFile;
    }

    public int getLastbyteLength() {
        return lastbyteLength;
    }

    public void setLastbyteLength(int lastbyteLength) {
        this.lastbyteLength = lastbyteLength;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "destinationDirectory='" + destinationDirectory + '\'' +
                ", sourceDirectory='" + sourceDirectory + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileSize=" + fileSize +
                ", piecesOfFile=" + piecesOfFile +
                ", lastbyteLength=" + lastbyteLength +
                ", status='" + status + '\'' +
                '}';
    }
}
