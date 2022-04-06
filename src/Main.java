import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

import java.io.*;

public class Main {
    private final static String ACCESS_TOKEN = "";

    public static void main(String[] args) {
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial")
                .build();
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
        //так как время исполнения превышает 5сек,
        //то программа запускается в 2 чередующихся потока
        try {
            MyThread thread1 = new MyThread(client);
            MyThread thread2 = new MyThread(client);
            Thread.sleep(1000);
            thread1.start();
            Thread.sleep(5000);
            thread2.start();

            System.out.println("Кофе-машина" + System.lineSeparator() +
                    "Итак, сколько вы готовы потратить сегодня? Введите сумму:");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                int moneyAmount = Integer.parseInt(reader.readLine());
                int[] drinkPrices = {150, 80, 50, 20};
                String[] drinkNames = {"капучино", "эспрессо", "молоко", "вода"};
                boolean endOfPurchase = false;
                int availableGoodsCount = 0;
                while (!endOfPurchase) {
                    System.out.println("Отслеживание доступных позиций...");
                    for (int i = 0; i < drinkNames.length; i = i + 1) {
                        if (moneyAmount >= drinkPrices[i]) {
                            availableGoodsCount++;
                            System.out.println(availableGoodsCount + ". " + drinkNames[i] + ": " + drinkPrices[i] + "руб.");
                        }
                    }
                    if (availableGoodsCount == 0) {
                        System.out.println("К сожалению, вам не хватает даже на воду ): Хотите добавить немного? Y/N");
                    } else {
                        System.out.println("Что вы выбрали?");
                        endOfPurchase = true;
                    }
                    if (reader.readLine().equals("Y")) {
                        System.out.println("Введите сумму, которую готовы добавить:");
                        moneyAmount += Integer.parseInt(reader.readLine());
                    } else endOfPurchase = true;
                }
                if (availableGoodsCount > 0)
                    System.out.println("Спасибо за покупку!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}