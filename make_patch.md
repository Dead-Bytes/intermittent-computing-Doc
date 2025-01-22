# Memory Management in MSPSim

## Overview
The MSPSim simulator emulates the MSP430 microcontroller and provides features such as instruction-level simulation, register monitoring, and platform emulation. A key aspect of the simulator is its memory management system, which is crucial for accurately simulating the behavior of the MSP430 platform.

## Types of Memory in MSPSim
1. **RAM (Random Access Memory):**
   - Simulated as volatile memory, primarily used for stack and variable storage during program execution.
   - Implemented in the `Memory.java` and `RAMSegment.java` files within the `core` package.

2. **Flash Memory:**
   - Simulated as non-volatile memory, used for program storage.
   - Handled in files like `FlashSegment.java` and `Flash.java` in the `core` package.

3. **Registers:**
   - Simulates special-purpose memory locations that control and monitor various subsystems.
   - Mapped using `StatusRegister.java`, `SFR.java`, and `MSP430Core.java`.

4. **External Memory:**
   - Includes EEPROM or external flash memory modules like M25P16 and AT45DB.
   - Simulated through files such as `ExternalFlash.java` and `FileStorage.java`.

## Memory Mapping
1. **Register Mapping:**
   - Registers are mapped within the core simulation files (`MSP430.java`, `MSP430Core.java`) to their respective address locations.
   - Functions like `write()` and `read()` allow interaction with memory addresses.

2. **RAM/Flash Mapping:**
   - Segments are defined for different memory regions, and they are accessed using helper classes like `RAMSegment.java` and `FlashSegment.java`.

3. **Non-Volatile Memory (NVM):**
   - Flash and external memory modules simulate non-volatile behavior, retaining data even after power loss.
   - These are implemented with read/write operations that simulate delay and constraints of real hardware.

## Saving and Loading Simulator State
MSPSim does not natively support saving or loading the entire simulator state, but this functionality can be implemented using the following methods. The `MSP430.java` class is a suitable location for implementing these features.

### Proposed Methods

#### 1. **`saveState(String filepath)`**
   - **Purpose:** Serialize and save the simulator state (registers, memory, peripherals) to a file.
   - **Steps to Implement:**
     1. **Memory Serialization:**
        - Use Java's serialization mechanism to convert the current memory and register states into a byte stream.
        - Include all volatile and non-volatile memory regions (RAM, Flash, and Registers).
     2. **Peripheral State:**
        - Include any peripheral states managed by `MSP430.java` or other core classes.
     3. **Save to File:**
        - Write the serialized byte stream to the specified file path using `FileOutputStream` and `ObjectOutputStream`.

   - **Implementation Example:**
     ```java
     public void saveState(String filepath) {
         try (FileOutputStream fileOut = new FileOutputStream(filepath);
              ObjectOutputStream out = new ObjectOutputStream(fileOut)) {

             // Save memory state
             out.writeObject(this.memory);

             // Save registers
             out.writeObject(this.registers);

             // Save peripheral state
             out.writeObject(this.peripherals);

             System.out.println("State saved to " + filepath);
         } catch (IOException e) {
             System.err.println("Error saving state: " + e.getMessage());
         }
     }
     ```

#### 2. **`loadState(String filepath)`**
   - **Purpose:** Load a previously saved simulator state from a file.
   - **Steps to Implement:**
     1. **Read Serialized Data:**
        - Use Java's `FileInputStream` and `ObjectInputStream` to read the serialized state from the file.
     2. **Restore State:**
        - Deserialize the memory, registers, and peripherals, and restore them to the simulator.
     3. **Validation:**
        - Verify that the loaded state matches the expected configuration (e.g., memory size, register count).

   - **Implementation Example:**
     ```java
     public void loadState(String filepath) {
         try (FileInputStream fileIn = new FileInputStream(filepath);
              ObjectInputStream in = new ObjectInputStream(fileIn)) {

             // Load memory state
             this.memory = (Memory) in.readObject();

             // Load registers
             this.registers = (Register[]) in.readObject();

             // Load peripheral state
             this.peripherals = (Peripheral[]) in.readObject();

             System.out.println("State loaded from " + filepath);
         } catch (IOException | ClassNotFoundException e) {
             System.err.println("Error loading state: " + e.getMessage());
         }
     }
     ```

### Dependencies
1. **Java Serialization:** Ensure that all classes (e.g., `Memory`, `Register`, `Peripheral`) implement the `Serializable` interface.
2. **File Handling:** Java's `FileOutputStream` and `FileInputStream` APIs are used for reading and writing state data.
3. **Object Streams:** Use `ObjectOutputStream` and `ObjectInputStream` for serialization and deserialization.

### Caveats and Warnings
- **Backward Compatibility:**
  - Ensure that changes to the `Memory`, `Register`, or `Peripheral` classes do not break existing serialized states.
  - Use `serialVersionUID` to maintain compatibility.

- **Performance Impact:**
  - Serialization may add overhead, especially for large memory configurations.

- **Data Integrity:**
  - Validate the file format and contents before loading to prevent corrupted or incompatible state files from crashing the simulator.

- **Platform-Specific Behavior:**
  - Verify the implementation across different platforms (e.g., Windows, Linux, MacOS).

### Testing the Save and Load Functions
1. **Unit Tests:**
   - Create JUnit tests for the `saveState` and `loadState` functions.
   - Validate the integrity of the saved and loaded states by comparing them with the original simulator state.

   - **Example Test Code:**
     ```java
     public static void main(String[] args) {
         MSP430 simulator = new MSP430();

         // Set up initial state
         simulator.initialize();
         simulator.memory.write(0x100, (byte) 0xAA);
         simulator.registers[0].setValue(0x1234);

         // Save state
         simulator.saveState("simulator_state.bin");

         // Modify state
         simulator.memory.write(0x100, (byte) 0x00);
         simulator.registers[0].setValue(0x0000);

         // Load state
         simulator.loadState("simulator_state.bin");

         // Verify state
         assert simulator.memory.read(0x100) == (byte) 0xAA;
         assert simulator.registers[0].getValue() == 0x1234;

         System.out.println("Save and load test passed.");
     }
     ```

2. **Integration Tests:**
   - Save the state at the end of `cputest.c` execution and reload it in another instance.
   - Compare the state variables (memory, registers, peripherals) to ensure correctness.

   - **Integration Test Steps:**
     - Run `cputest.c` and save the state using `saveState`.
     - Restart the simulator and load the saved state using `loadState`.
     - Execute a small program to verify the simulator's state matches expectations.

## Conclusion
Adding `saveState` and `loadState` functions to the MSPSim simulator enhances its capabilities by allowing persistent state management. These functions enable advanced simulation workflows, such as pausing and resuming simulations or sharing simulator states between users or tools. Proper implementation and testing will ensure reliability and usability.
