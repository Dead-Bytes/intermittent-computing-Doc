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

## Example Code References
- **Memory.java:** Provides the base class for memory simulation.
- **FlashSegment.java:** Handles non-volatile flash memory operations.
- **MSP430Core.java:** Simulates the MSP430 microcontroller's core, including memory and register operations.

## Saving MSPSim State
Currently, MSPSim does not support saving the entire simulator state (registers, memory, peripherals) directly to non-volatile memory. This functionality can be extended by:

### Proposed Future Work:
1. **State Serialization:**
   - Create a mechanism to serialize the current state of memory, registers, and peripheral states to a file or database.
   - Use Java serialization or JSON to save the state.

2. **Integration with `cputest.c`:**
   - Modify the main program to save the final state of the simulator before exiting.
   - Example pseudocode:
     ```c
     void save_state_to_file(const char* filename) {
         FILE* file = fopen(filename, "w");
         if (file) {
             fwrite(memory_state, sizeof(memory_state), 1, file);
             fwrite(register_state, sizeof(register_state), 1, file);
             fclose(file);
         } else {
             printf("Failed to save state\n");
         }
     }

     int main(void) {
         msp430_setup();
         test_all_functions();
         save_state_to_file("mspsim_state.nvm");
         return 0;
     }
     ```

3. **NVM Emulation Enhancements:**
   - Extend `ExternalFlash.java` or `FileStorage.java` to support loading and saving entire simulator states.

## Implementation Steps for NVM Integration
1. **Modify Core Classes:**
   - Extend `MSP430Core.java` to include methods for exporting and importing simulator states.
   - Example:
     ```java
     public void saveState(String filepath) {
         // Serialize memory and register states
     }

     public void loadState(String filepath) {
         // Deserialize state back into the simulator
     }
     ```

2. **User Interface Support:**
   - Add CLI commands (e.g., `save state` and `load state`) for saving and loading states during simulation.

3. **Testing:**
   - Validate the implementation by running existing test cases (`cputest.c`, `timertest.c`) with state-saving and restoring capabilities.

## Conclusion
The MSPSim memory system is versatile and extensible, supporting both volatile and non-volatile memory types. Adding support for saving and restoring simulator states in non-volatile memory will enhance its usability and allow for more complex simulations. This functionality can be achieved by extending the existing core classes and integrating state management features into the simulator.

---

For further details, refer to:
- `Memory.java` and `MSP430Core.java` files for memory management.
- `cputest.c` and `timertest.c` for example test cases.
