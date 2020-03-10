import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class Orange {
    public static Pin goal1pin = RaspiPin.GPIO_05;
    public static Pin goal2pin = RaspiPin.GPIO_07;
    private Pin whistlepin = RaspiPin.GPIO_30;
    private GpioController gpioController;
    private GpioPinDigitalOutput whistle;
    private GoalCallback callback;
    private int goalcoolingtime = 1000;
    private long lastgpioevent;

    public Orange(GoalCallback callback){
//        try {
//            PlatformManager.setPlatform(Platform.ORANGEPI);
//        } catch (PlatformAlreadyAssignedException e) {
//            e.printStackTrace();
//        }
        this.callback = callback;
        gpioController = GpioFactory.getInstance();
        GpioPinDigitalInput team1 = gpioController.provisionDigitalInputPin(goal1pin);
        GpioPinDigitalInput team2 = gpioController.provisionDigitalInputPin(goal2pin);
        whistle = gpioController.provisionDigitalOutputPin(whistlepin);
        team1.addListener(goallistener);
        team2.addListener(goallistener);

    }
    private GpioPinListenerDigital goallistener = new GpioPinListenerDigital() {
        @Override
        public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
            if (System.currentTimeMillis() - lastgpioevent >= goalcoolingtime){
                lastgpioevent = System.currentTimeMillis();
                callback.goal(event);
            }

        }
    };

    public void goalwhistle(){
        whistle.blink(500, 500);
    }

    public void endgamewhistle(){
        whistle.blink(500, 3000);
    }

    public void shut(){
        gpioController.shutdown();
    }

}
