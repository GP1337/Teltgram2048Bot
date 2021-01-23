import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class KeyboardFactory {

    public List<KeyboardRow> getKeyboard(String message){

        ArrayList<KeyboardRow> keyboard= new ArrayList();

       if (message.equals("/start")){

            KeyboardRow keyboardRow1 = new KeyboardRow();
            keyboardRow1.add("⬆");
            keyboard.add(keyboardRow1);

            KeyboardRow keyboardRow2 = new KeyboardRow();
            keyboardRow2.add("⬅");
            keyboardRow2.add("➡");

            keyboard.add(keyboardRow2);

            KeyboardRow keyboardRow3 = new KeyboardRow();
            keyboardRow3.add("⬇");
            keyboard.add(keyboardRow3);

        }

        return keyboard;

    }

}
