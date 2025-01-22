
# MSPSim Documentation: Focusing on `se/sics`

## Overview

The `se/sics` directory contains the core components and structure of the MSPSim simulator. This package is organized into several sub-packages that handle various aspects of the MSP430 emulation, including hardware components, user interfaces, platform-specific configurations, debugging, and utilities.

---

## Directory Structure of `se/sics`

```
se/sics/mspsim
├── chip                # Hardware components (e.g., memory, sensors, radios).
├── cli                 # Command-line interface for debugging and configuration.
├── core                # Core CPU and peripheral emulation logic.
├── debug               # Debugging support for symbol tables, breakpoints, etc.
├── emulink             # Emulation-specific networking link layer.
├── extutil             # Extended utilities, including syntax highlighting and charting.
├── platform            # Platform-specific implementations (e.g., WiSmote, Sky).
├── profiler            # Tools for profiling code execution.
├── ui                  # Graphical and user interface components.
├── util                # General utilities for file handling, configuration, etc.
└── json                # Lightweight JSON library for data representation.
```

---

## Detailed Package Breakdown

### **1. `chip/`**
Handles emulation of hardware chips and peripherals connected to the MSP430.
- **Key Classes**:
  - `Memory.java`: Defines how memory is emulated in the MSP430.
  - `CC2420.java`: Simulates the CC2420 radio transceiver.
  - `TMP112.java`: Models a temperature sensor.
  - `Button.java`: Emulates a button press.
  - `SHT11.java`: Simulates the SHT11 temperature and humidity sensor.

- **Example**:
  ```java
  public class Memory {
      private byte[] memory;
      public Memory(int size) {
          memory = new byte[size];
      }
      public byte read(int address) {
          return memory[address];
      }
      public void write(int address, byte value) {
          memory[address] = value;
      }
  }
  ```

### **2. `cli/`**
Provides a command-line interface (CLI) for interacting with the simulator.
- **Key Classes**:
  - `Command.java`: Abstract class defining a CLI command.
  - `CommandParser.java`: Parses user input and executes commands.
  - `NetCommands.java`: Handles networking-related commands.
  - `DebugCommands.java`: Implements debugging commands (e.g., breakpoints).

- **Example**: Adding a breakpoint via CLI.
  ```bash
  > break 0x1234
  ```

### **3. `core/`**
Implements the MSP430 CPU and its peripherals.
- **Key Classes**:
  - `MSP430.java`: The central CPU emulation logic.
  - `Timer.java`: Emulates the Timer_A and Timer_B subsystems.
  - `USART.java`: Handles serial communication emulation.
  - `IOPort.java`: Models GPIO ports.

- **Example**: Emulating Timer_A in `Timer.java`.
  ```java
  public class Timer {
      private int counter;
      public void tick() {
          counter++;
          if (counter == overflowLimit) {
              generateInterrupt();
          }
      }
  }
  ```

### **4. `debug/`**
Facilitates debugging with symbol tables, breakpoints, and stack analysis.
- **Key Classes**:
  - `StabDebug.java`: Handles debugging symbols for stack frames.
  - `BreakpointException.java`: Raised when a breakpoint is hit.

- **Example**: Adding a debug breakpoint.
  ```java
  public class Breakpoint {
      private int address;
      public Breakpoint(int address) {
          this.address = address;
      }
      public boolean isHit(int currentAddress) {
          return currentAddress == address;
      }
  }
  ```

### **5. `emulink/`**
Implements emulation-specific networking.
- **Key Classes**:
  - `EmuLink.java`: Provides a simulated link for network communication between nodes.

### **6. `extutil/`**
Provides extended utilities like syntax highlighting and chart generation.
- **Key Classes**:
  - `HighlightSourceViewer.java`: Highlights source code for debugging.
  - `LineChart.java`: Plots line charts for simulation data.
  - `TextScanner.java`: Implements text-based syntax scanning.

### **7. `platform/`**
Contains platform-specific implementations and configurations.
- **Key Platforms**:
  - **`wismote/`**: WiSmote platform emulation.
    - `WismoteNode.java`: Simulates the WiSmote hardware.
    - `WismoteGui.java`: Provides a GUI for WiSmote simulation.
  - **`sky/`**: Sky platform emulation.
    - `SkyNode.java`: Simulates the Sky hardware.
    - `SkyGui.java`: GUI for Sky simulation.
  - **`z1/`**: Zolertia Z1 platform emulation.

- **Example**: Simulating the WiSmote node.
  ```java
  public class WismoteNode extends GenericNode {
      public WismoteNode() {
          // Initialize WiSmote-specific hardware components
      }
  }
  ```

### **8. `profiler/`**
Provides tools for profiling code execution and resource usage.
- **Key Classes**:
  - `SimpleProfiler.java`: Tracks execution time and function calls.
  - `CallEntry.java`: Represents a function call in the profiling stack.

### **9. `ui/`**
Handles graphical and interactive user interfaces.
- **Key Classes**:
  - `ChartPanel.java`: Renders simulation data as charts.
  - `DebugUI.java`: Provides a debugging user interface.
  - `WindowManager.java`: Manages windows for multiple simulations.

- **Example**: Displaying a line chart.
  ```java
  public class LineChart {
      public void addDataPoint(int x, int y) {
          // Add data to the chart
      }
  }
  ```

### **10. `util/`**
Utility classes for file handling, configurations, and logging.
- **Key Classes**:
  - `Utils.java`: General utility functions (e.g., byte conversions).
  - `ConfigManager.java`: Manages simulator configurations.

### **11. `json/`**
Lightweight JSON library for data serialization.
- **Key Classes**:
  - `JSONObject.java`: Represents a JSON object.
  - `JSONArray.java`: Represents a JSON array.

---

## Workflow Using `se/sics`

### **1. Running a Simulation**
1. Load the desired platform:
   ```bash
   java -jar mspsim.jar -node wismote
   ```
2. Load firmware for the selected node:
   ```bash
   load firmware/blink.wismote
   ```

### **2. Debugging a Simulation**
1. Set a breakpoint:
   ```bash
   break 0x1234
   ```
2. Start the simulation and monitor the console for debug information.

### **3. Profiling Code**
1. Enable profiling using the CLI:
   ```bash
   profile start
   ```
2. View profiling results:
   ```bash
   profile stop
   ```

### **4. Visualizing Data**
Use the `ui` package to render charts or graphical representations of simulation data.

---

## Example: Adding a New Platform

1. Create a new package under `platform/` (e.g., `platform/newplatform`).
2. Extend `GenericNode` to define hardware behavior:
   ```java
   public class NewPlatformNode extends GenericNode {
       public NewPlatformNode() {
           // Define hardware initialization here
       }
   }
   ```
3. Add a GUI if needed by extending `AbstractNodeGUI`.

---

## Conclusion

The `se/sics` directory is the backbone of MSPSim, offering modular and extensible components to emulate MSP430 hardware and peripherals. By understanding its structure and functionality, developers can customize the simulator to suit their specific requirements, test firmware, and profile applications effectively.

---

## Contact

For further assistance or customization guidance, consult the maintainers or refer to the official repository.
