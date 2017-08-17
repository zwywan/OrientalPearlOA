package com.geely.app.geelyapprove.common.utils;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Oliver on 2016/9/28.
 */

public class DescripUtil {
    private static String[] CH = {"", "", "拾", "佰", "仟"};

    private static String[] CHS_NUMBER = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};

    private static String[] CHS = {"万", "亿", "兆"};

    private static DecimalFormat df = new DecimalFormat("#########################0.0#");

    /**
     * 传入数字金额双精度型值，返回数字金额对应的中文大字与读法
     *
     * @param money 金额
     * @return 金额中文大写
     */
    public static String transFormMoney(double money) {
        return transFormMoney(df.format(money));
    }

    /**
     * 传入数字金额浮点型值，返回数字金额对应的中文大字与读法
     *
     * @param money 金额
     * @return 金额中文大写
     */
    public static String transFormMoney(float money) {
        return transFormMoney(df.format(money));
    }

    /**
     * 传入数字金额字符串，返回数字金额对应的中文大字与读法
     *
     * @param money 金额字符串
     * @return 金额中文大写
     */
    public static String transFormMoney(String money) {
        String result = "";

        try {
            BigDecimal big = new BigDecimal(money);
            String[] t = null;
            try {
                t = big.toString().replace(".", ";").split(";");
            } catch (Exception e) {
                // 金额如果没有小数位时,也要加上小数位
                t = (big.toString() + ".0").replace(".", ";").split(";");
            }

            String[] intString = splitMoney(t[0]);
            String tmp_down = t[1];

            for (int i = 0; i < intString.length; i++) {
                result = result + count(intString[i]);

                if (i == intString.length - 2 || i == intString.length - 3)
                    continue;

                if (i != intString.length - 1) {
                    result = result + CHS[intString.length - 2 - i];
                }
            }

            if (Integer.parseInt(tmp_down) == 0) {
                result = result + (intString[0].equals("0") ? "零元" : "元整");
            } else {
                result = result + (intString[0].equals("0") ? "" : tmp_down.startsWith("0") ? "元零" : "元")
                        + getFloat(tmp_down);
            }

        } catch (Exception e) {
            return "你輸入的不是數字符串";
        }

        return result;
    }

    /**
     * 对整数部分字符串进行每四位分割放置分割符
     *
     * @param money 整数部分字符串
     * @return 放置分割符后的字符串
     */
    public static String[] splitMoney(String money) {
        StringBuffer tmp_int = new StringBuffer();
        tmp_int.append(money);

        // 先對整數位進行分割，每四位爲一組。
        int i = tmp_int.length();

        do {
            try {
                // 進行try..catch是因爲當位數不能滿足每四位放分割符時，就退出循環
                i = i - 4;

                if (i == 0)
                    break;

                tmp_int.insert(i, ';');

            } catch (Exception e) {
                break;
            }
        } while (true);

        return tmp_int.toString().split(";");
    }

    /**
     * 转换整数部分
     *
     * @param money 整数部分金额
     * @return 整数部分大写
     */
    public static String count(String money) {
        String tmp = "";
        Integer money_int = 0;
        char[] money_char;

        // 如果數字開始是零時
        if (money.startsWith("0")) {
            money_int = Integer.parseInt(money);

            if (money_int == 0)
                return tmp;
            else
                tmp = "零";

            money_char = money_int.toString().toCharArray();
        } else {
            money_char = money.toCharArray();
        }

        for (int i = 0; i < money_char.length; i++) {
            if (money_char[i] != '0') {
                // 如果當前位不爲“0”，才可以進行數字和位數轉換
                tmp = tmp + CHS_NUMBER[Integer.parseInt(money_char[i] + "")] + CH[money_char.length - i];
            } else {
                // 要想該位轉換爲零，要滿足三個條件
                // 1.上一位沒有轉換成零，2.該位不是最後一位，3.該位的下一位不能爲零
                if (!tmp.endsWith("零") && i != money_char.length - 1 && money_char[i + 1] != '0') {
                    tmp = tmp + CHS_NUMBER[Integer.parseInt(money_char[i] + "")];
                }
            }
        }

        return tmp;
    }

    /**
     * 转换小数部分
     *
     * @param fl 小数
     * @return 小数大写
     */
    private static String getFloat(String fl) {
        String f = "";
        char[] ch = fl.toCharArray();

        switch (ch.length) {
            case 1: {
                f = f + CHS_NUMBER[Integer.parseInt(ch[0] + "")] + "角整";
                break;
            }

            case 2: {
                if (ch[0] != '0')
                    f = f + CHS_NUMBER[Integer.parseInt(ch[0] + "")] + "角"
                            + CHS_NUMBER[Integer.parseInt(ch[1] + "")] + "分";
                else
                    f = f + CHS_NUMBER[Integer.parseInt(ch[1] + "")] + "分";

                break;
            }
        }
        return f;
    }

    public static String formatDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
    }
}
