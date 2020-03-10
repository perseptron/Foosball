import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;

public interface GoalCallback {
     void goal(GpioPinDigitalStateChangeEvent event);
}
