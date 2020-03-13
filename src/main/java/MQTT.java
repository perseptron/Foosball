import org.eclipse.paho.client.mqttv3.*;

import java.util.ResourceBundle;

public class MQTT {
    private static final String CLIENTID = "FOOSBAL01";
    private static final String RESFILE = "priv";
    private static String serveruri = "tcp://172.16.1.1:1883";
    private IMqttClient client;
    private MQTTCallback callback;

    public MQTT(MQTTCallback callback) {
        ResourceBundle reader = ResourceBundle.getBundle(RESFILE);
        String serveruri = reader.getString("SERVERURI");
        this.callback = callback;
        try {
            client = new MqttClient(serveruri, CLIENTID);
        } catch (MqttException e) {
            e.printStackTrace();
        }
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setConnectionTimeout(10);
        try {
            if (client != null) {
                client.connect(mqttConnectOptions);
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }


    }

    public void subscribe(String topic, MQTTCallback callback) {
        try {
            client.subscribe(topic, new IMqttMessageListener() {
                        @Override
                        public void messageArrived(String topic, MqttMessage message) {
                            callback.receive(topic, message);
                        }
                    }
            );
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


    public void publish(String topic, String data) {
        MqttMessage message = new MqttMessage(data.getBytes());
        message.setQos(0);
        message.setId(23232);
        message.setRetained(true);
        try {
            client.publish(topic, message);
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }
}
