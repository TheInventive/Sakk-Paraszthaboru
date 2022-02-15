package hu.desktop.gui;

import javafx.scene.control.Button;

import java.util.List;

public class Helper {
    public static boolean isEven(final int number){
        return number % 2 == 0;
    }

    public static boolean isOdd(final int number){
        return number % 2 != 0;
    }

    public static int getButtonCoordinate(Button button){
        return Integer.parseInt(button.getId().substring(4));
    }

    public static <T> T lastElement(List<T> list){
        return list.get(list.size() - 1);
    }
}
