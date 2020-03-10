
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.util.Console;
import static java.lang.Thread.sleep;

public class Play {
    private static Console console;
    private static Orange orange;
    private static Game game;
    private static GoalCallback goalCallback = new GoalCallback() {
        @Override
        public void goal(GpioPinDigitalStateChangeEvent event) {
            if (event.getState() == PinState.LOW)
                orange.goalwhistle();
            if (event.getPin().getPin() == Orange.goal1pin) {
                game.goalteam1();
            } else if (event.getPin().getPin() == Orange.goal2pin) {
                game.goalteam2();
            } else {
                console.println("Unknown PIN");
            }
            console.println(game.getTeam1() + " " + game.getTeam1score() + ":" + game.getTeam2score() + " " + game.getTeam2());
        }
    };


    public static void main(String[] args) {
        console = new Console();
        console.println("waiting for events... ");
        game = new Game("A", "B");
        orange = new Orange(goalCallback);

        try {
            console.waitForExit();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//    while (true) {
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
    }


}
