package workout.implementors;

public class LegacyPolarBandService {
    // Exception лақтырмайды, только сандық код қайтарады
    // WorkoutData емес, тікелей byte[] қабылдайды
    public int transmitRawBuffer(byte[] buffer, String deviceMac, int timeoutSeconds) {
        if (deviceMac == null || deviceMac.isBlank()) {
            return 400; // Қате коды: Құрылғы табылмады
        }
        if (timeoutSeconds <= 0) {
            return 504; // Код ошибки: Таймаут
        }
        System.out.println("[Polar SDK] Bluetooth арқылы " + buffer.length + " байт жіберілді -> " + deviceMac);
        return 0; // 0 = Сәтті орындалды
    }
}