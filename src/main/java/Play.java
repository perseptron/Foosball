import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.util.Console;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Play {
    private static Console console;
    private static Orange orange;
    private static Game game;
    private static MQTT mqtt;
    private static GoalCallback goalCallback = new GoalCallback() {
        @Override
        public void goal(GpioPinDigitalStateChangeEvent event) {
            orange.goalwhistle();
            if (event.getPin().getPin() == Orange.goal1pin) {
                if (game.goalteam1() == 0) orange.endgamewhistle();
            } else if (event.getPin().getPin() == Orange.goal2pin) {
                if (game.goalteam2() == 0) orange.endgamewhistle();
            } else {
                console.println("Unknown PIN");
            }
            String score = game.getTeam1() + " " + game.getTeam1score() + ":" + game.getTeam2score() + " " + game.getTeam2();
            console.println(score);
            mqtt.publish("score", score);

        }
    };

    private static MQTTCallback mqttCallback = new MQTTCallback() {
        @Override
        public void receive(String topic, MqttMessage message) {
            switch (topic) {
                case "score":
                default:
                    console.println(topic + " " + message);
            }
        }
    };

    public static void main(String[] args) {
        console = new Console();
        console.println("waiting for events... ");
        game = new Game("A", "B");
        orange = new Orange(goalCallback);
        mqtt = new MQTT(mqttCallback);
        mqtt.subscribe("score", mqttCallback);
        mqtt.subscribe("score2", mqttCallback);



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
