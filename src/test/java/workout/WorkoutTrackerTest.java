package workout;

import org.junit.jupiter.api.Test;
import workout.abstractions.CardioTracker;
import workout.abstractions.StrengthTracker;
import workout.abstractions.WorkoutTracker;
import workout.implementors.LegacyPolarBandService;
import workout.implementors.PolarBandAdapter;
import workout.implementors.WorkoutSyncImplementor;
import workout.models.SyncException;
import workout.models.WorkoutData;

import static org.junit.jupiter.api.Assertions.*;

public class WorkoutTrackerTest {

    // 1-ТЕСТ: CardioTracker өз орындаушысына деректерді дұрыс жібере ме? (Делегация)
    @Test
    void testCardioTrackerDelegation() throws SyncException {
        // Stub Implementor: орындаушының шақырылғанын тіркеу үшін
        class MockSyncImplementor implements WorkoutSyncImplementor {
            boolean wasCalled = false;
            String sentAthleteId;

            @Override
            public void sendWorkoutData(String athleteId, WorkoutData data) {
                this.wasCalled = true;
                this.sentAthleteId = athleteId;
            }
        }

        MockSyncImplementor mockSync = new MockSyncImplementor();
        WorkoutTracker cardio = new CardioTracker(mockSync, 140, 5.0);

        cardio.recordSession("ATHLETE-01");

        assertTrue(mockSync.wasCalled, "CardioTracker Implementor әдісін шақыруы керек еді!");
        assertEquals("ATHLETE-01", mockSync.sentAthleteId);
    }

    // 2-ТЕСТ: StrengthTracker өз орындаушысына деректерді дұрыс жібере ме? (Делегация)
    @Test
    void testStrengthTrackerDelegation() throws SyncException {
        class MockSyncImplementor implements WorkoutSyncImplementor {
            boolean wasCalled = false;
            WorkoutData sentData;

            @Override
            public void sendWorkoutData(String athleteId, WorkoutData data) {
                this.wasCalled = true;
                this.sentData = data;
            }
        }

        MockSyncImplementor mockSync = new MockSyncImplementor();
        WorkoutTracker strength = new StrengthTracker(mockSync, 10, 30);

        strength.recordSession("ATHLETE-02");

        assertTrue(mockSync.wasCalled, "StrengthTracker Implementor әдісін шақыруы керек еді!");
        assertNotNull(mockSync.sentData);
    }

    // 3-ТЕСТ: Адаптер Polar қатесін жүйенің SyncException-іне аудара ма? (Failure Translation)
    @Test
    void testPolarAdapterFailureTranslation() {
        // Бөгде кластың сәтсіздікке ұшыраған жағдайы (Stub)
        LegacyPolarBandService failingSdk = new LegacyPolarBandService() {
            @Override
            public int transmitRawBuffer(byte[] buffer, String deviceMac, int timeoutSeconds) {
                return 400; // Құрылғы табылмады қатесі
            }
        };

        PolarBandAdapter adapter = new PolarBandAdapter(failingSdk, "INVALID-MAC");

        // Бөгде қате коды SyncException түрінде сыртқа лақтырылуы шарт
        SyncException thrown = assertThrows(SyncException.class, () -> {
            adapter.sendWorkoutData("ATHLETE-03", new WorkoutData("CARDIO", 30, 200));
        });

        assertTrue(thrown.getMessage().contains("400"), "Қате хабарламасында 400 коды болуы керек!");
    }
}