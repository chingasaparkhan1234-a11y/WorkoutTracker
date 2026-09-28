package workout.implementors;

import workout.models.SyncException;
import workout.models.WorkoutData;
import java.nio.charset.StandardCharsets;

public class PolarBandAdapter implements WorkoutSyncImplementor {
    private final LegacyPolarBandService legacySdk;
    private final String deviceMac;

    public PolarBandAdapter(LegacyPolarBandService legacySdk, String deviceMac) {
        this.legacySdk = legacySdk;
        this.deviceMac = deviceMac;
    }

    @Override
    public void sendWorkoutData(String athleteId, WorkoutData data) throws SyncException {
        if (athleteId == null || athleteId.isBlank()) {
            throw new SyncException("Спортшының ID-і бос болмауы керек!");
        }

        // 1. Деректерді бөгде SDK қабылдайтын byte[] түріне айналдыру
        String payload = athleteId + ":" + data.toPayload();
        byte[] rawBuffer = payload.getBytes(StandardCharsets.UTF_8);

        // 2. Басқа әдіс шақыру
        int statusCode = legacySdk.transmitRawBuffer(rawBuffer, deviceMac, 10);

        // 3. Failure Translation: сандық қате кодын ортақ SyncException-ге аудару
        if (statusCode != 0) {
            throw new SyncException("Polar құрылғысымен байланыс орнату қатесі. Қате коды: " + statusCode);
        }
    }
}