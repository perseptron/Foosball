import org.eclipse.paho.client.mqttv3.MqttMessage;

public interface MQTTCallback {
    void receive(String topic, MqttMessage message);
}
