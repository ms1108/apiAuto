package utils;

import java.util.Random;

public class RandomUtil {

    public static String getStringRandom() {
        return getStringRandom(10);
    }

    //随机相关
    public static String getStringRandom(Integer length) {
        String str = "zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNMx1234567890";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; ++i) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static String getIntRandom() {
        Random random = new Random();
        return String.valueOf(random.nextInt(2000000000) - 1000000000);
    }

    /**
     * 获取x到y内的随机一位数（包含x,y）
     */
    public static int getIntRandom(int x, int y) {
        if (x <= 0 || y <= 0 || x > y) {
            return 0;
        }
        return (int) (Math.random() * (y - x + 1)) + x;
    }

    public static String getPhoneRandom() {
        String[] PhoneFirst = {"186", "187", "179", "178", "138", "139", "158", "159"};
        String PhoneEnd = String.valueOf(((int) ((Math.random() * 9) * 10000000)));
        String RandomFirst = PhoneFirst[(int) (Math.random() * 8)];
        return RandomFirst + PhoneEnd;
    }

    public static String getChineseRandom() {
        return getChineseRandom(5);
    }

    public static String getChineseRandom(int len) {
        String chinese = "";
        StringBuilder sb = new StringBuilder(chinese);
        for (int i = 0; i < len; i++) {
            sb.append(new String(new char[]{(char) (new Random().nextInt(20902) + 19968)}));
        }
        return sb.toString();
    }


}
