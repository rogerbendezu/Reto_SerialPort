package serial;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class SerialWriter {

    static SerialPort serialPort;

    public static void main(String[] args) throws SerialPortException {
        serialPort = new SerialPort("COM17");
        try {
            serialPort.openPort();
            serialPort.setParams(19200, 8, 1, 0);
            int mask = SerialPort.MASK_RXCHAR;
            serialPort.setEventsMask(mask);
            serialPort.addEventListener(new SerialPortReader());

            serialPort.writeString(" HOLA MUNDO");
        } catch (SerialPortException ex) {
            System.out.println(ex);
        }
    }

    static class SerialPortReader implements SerialPortEventListener {

        public void serialEvent(SerialPortEvent event) {
            if (event.isRXCHAR()) {
                if (event.getEventValue() == 10) {
                    try {
                        String data = serialPort.readString();
                        System.out.println(data);
                    } catch (SerialPortException ex) {
                        System.out.println(ex);
                    }
                }
            }
        }
    }
}