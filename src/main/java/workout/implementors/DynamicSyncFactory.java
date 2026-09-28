package workout.implementors;

public class DynamicSyncFactory {
    // Кіріс деректеріне байланысты орындаушыны динамикалық түрде таңдау
    public static WorkoutSyncImplementor createImplementor(String networkStatus, String polarDeviceMac) {
        // Егер Bluetooth құрылғысының MAC-мекенжайы берілсе -> Adapter таңдалады
        if (polarDeviceMac != null && !polarDeviceMac.isBlank()) {
            return new PolarBandAdapter(new LegacyPolarBandService(), polarDeviceMac);
        }

        // Егер желі бар болса -> Бұлтқа жіберу
        if ("ONLINE".equalsIgnoreCase(networkStatus)) {
            return new CloudSyncImplementor();
        }

        // Басқа жағдайда -> Телефонның жергілікті базасына жазу
        return new LocalDatabaseSyncImplementor();
    }
}