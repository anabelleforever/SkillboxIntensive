import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private final static String ACCESS_TOKEN = "Me9g8WZRaK8AAAAAAABTSjyTkm0TSv3priwHGxLkZ7f5XUHK3wv4S5u2JchxWso7";

    public static void main(String[] args) {

        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);

        MyThread thread = null;
        try {
            thread = new MyThread(client);
        } catch (AWTException e) {
            e.printStackTrace();
        }
        thread.start();

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
    }

}
