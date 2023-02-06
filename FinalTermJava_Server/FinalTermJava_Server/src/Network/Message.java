package Network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Message {
    private byte command;
    private ByteArrayInputStream is;
    public DataInputStream dis;
    private ByteArrayOutputStream os;
    public DataOutputStream dos;

    public Message(int command) {
        this((byte)command);
    }

    public Message(byte command) {
        this.command = command;
        this.os = new ByteArrayOutputStream();
        this.dos = new DataOutputStream(this.os);
    }

    public Message(byte command, byte[] data) {
        this.command = command;
        this.is = new ByteArrayInputStream(data);
        this.dis = new DataInputStream(this.is);
    }

    public byte getCommand() {
        return this.command;
    }

    public void setCommand(int cmd) {
        this.setCommand((byte)cmd);
    }

    public void setCommand(byte command) {
        this.command = command;
    }

    public byte[] getData() {
        return this.os.toByteArray();
    }

    public DataInputStream reader() {
        return this.dis;
    }

    public DataOutputStream writer() {
        return this.dos;
    }

    public void cleanup() {
        try {
            if (this.dis != null) {
                this.dis.close();
            }
            if (this.dos != null) {
                this.dos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
