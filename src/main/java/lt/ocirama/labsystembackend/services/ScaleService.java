package lt.ocirama.labsystembackend.services;

import com.fazecast.jSerialComm.SerialPort;

import java.io.InputStream;


public class ScaleService {

    public SerialPort serialPort;

    public SerialPort SvarstykliuJungtis(
    ) {
        SerialPort[] ports = SerialPort.getCommPorts();
        int i = 1;

            /*for (SerialPort port : ports)
                System.out.println(i++ + ": " + port.getSystemPortName());*/

        int chosenPort = 1;
        SerialPort serialPort = ports[chosenPort - 1];

            /*if (serialPort.openPort()) {
                System.out.println("Port opened successfully.");
            }else {
                System.out.println("Unable to open the port.");
            }*/
        serialPort.setComPortParameters(9600, 8, 1, SerialPort.NO_PARITY);
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 0, 0);
        serialPort.openPort();
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        return serialPort;
    }

    public Double Pasverti(SerialPort serialPort) {
        InputStream in = serialPort.getInputStream();

        String sg = "";
        try {
            while (true) {
                char c;
                do {
                    c = (char) in.read();
                    if (c == '\n')
                        break;
                    sg += c;
                } while (true);
                String string = "5)";
                if (sg.contains(string)) {
                    //System.out.println("Wrong value.");
                    ScaleService s = new ScaleService();
                    return s.Pasverti(serialPort);
                }
                sg = sg.replaceAll("\\s+", "");
                sg = sg.replaceAll("[^\\d.]", "");
                if ((sg.length() != 7) && (sg.length() != 8)) {
                    System.out.println("Value out of bounds. Repeating.");
                    ScaleService s = new ScaleService();
                    return s.Pasverti(serialPort);
                }
                System.out.println(sg);
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Double.parseDouble(sg);

    }

    public void ClosePort(SerialPort serialPort) {
        serialPort.closePort();
    }

}
