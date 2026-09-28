# Design Rationale: Workout Tracker System

## 1. Problem Domain
The system tracks and records various athletic workouts (e.g., Cardio sessions with GPS/heart-rate, Boxing/Strength training with sets and reps) and synchronizes session logs to different storage and synchronization backends (Cloud storage, offline SQLite database, or third-party Bluetooth hardware).

## 2. Why Bridge Alone is Not Enough
Bridge successfully decouples the workout abstraction hierarchy (`WorkoutTracker`, `CardioTracker`, `StrengthTracker`) from the synchronization implementor hierarchy (`WorkoutSyncImplementor`). However, real-world sports hardware like the `LegacyPolarBandService` does not adhere to our uniform `WorkoutSyncImplementor` contract. Bridge alone cannot integrate this closed-source, incompatible library without polluting the abstraction or breaking clean interfaces.

## 3. Why Adapter Alone is Not Enough
Adapter allows wrapping `LegacyPolarBandService` into an expected interface. However, using Adapter without Bridge would create combinatorial subclass explosion if workout types and storage channels expanded together (e.g., `CardioCloudSync`, `CardioLocalSync`, `CardioPolarSync`, `StrengthCloudSync`, etc.). Bridge isolates these two orthogonal axes of change, satisfying the Open/Closed Principle.

## 4. Genuine Incompatibility of the Adaptee
The wrapped class (`LegacyPolarBandService`) is genuinely incompatible in multiple dimensions:
- **Method Signature & Types:** Instead of accepting high-level `WorkoutData` and `athleteId` strings, it accepts low-level raw byte arrays (`byte[]`), a MAC address string, and a primitive timeout integer.
- **Failure Handling:** It does not throw exceptions. Instead, it signals failures via integer return codes (`400` for device not found, `504` for timeout, `0` for success).
  The `PolarBandAdapter` encapsulates this logic, serializes data into bytes, and translates all non-zero status codes into domain-consistent `SyncException` instances without leaking Polar-specific details.

## 5. Required Complexity Module: Dynamic Implementor Selection
We chose **Dynamic Implementor Selection**. The client application does not hard-code the concrete implementation. Instead, `DynamicSyncFactory` evaluates runtime parameters (e.g., active network status and presence of a Bluetooth MAC address) to dynamically resolve whether to instantiate `CloudSyncImplementor`, `LocalDatabaseSyncImplementor`, or `PolarBandAdapter`.

## 6. Architectural Limitation
The current design maps rich object-oriented domain models (`WorkoutData`) into flattened byte representations for the legacy Polar hardware. As a result, detailed metrics that cannot be represented in a simple delimited byte string may experience data fidelity loss when synchronized exclusively via the legacy adapter.