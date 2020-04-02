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
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
    private String fileName;
    private ByteArrayOutputStream temp = new ByteArrayOutputStream();
    private Robot robot = new Robot();
    private Rectangle area = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

    MyThread(DbxClientV2 client) throws AWTException {
        this.client = client;
    }

    @Override
    public void run() {
        while (true) {
            try (InputStream in = new ByteArrayInputStream(temp.toByteArray())) {
                BufferedImage screenshot = robot.createScreenCapture(area);
                ImageIO.write(screenshot, "png", temp);
                fileName = "/" + formatter.format(new Date()) + ".png";
                client.files().uploadBuilder(fileName).uploadAndFinish(in);
                sleep(5000);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
