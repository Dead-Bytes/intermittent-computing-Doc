
# MSPSim Detailed Documentation

## Overview

MSPSim is an advanced Java-based instruction-level simulator for the MSP430 microcontroller series. It supports various sensor networking platforms, providing tools for debugging, profiling, and simulation. MSPSim is particularly useful for testing firmware in a controlled environment before deploying it to hardware.

---

## Features

- Instruction-level emulation of the MSP430 microcontroller family.
- Support for IHEX and ELF firmware formats.
- Built-in tools for debugging:
  - Stack monitoring.
  - Breakpoint configuration.
  - Profiling capabilities.
- Simulation of peripheral devices like timers, USARTs, and GPIO.
- Command-line interface (CLI) for user interaction and automation.
- GDB remote debugging support.
- Emulation of external hardware such as CC2420 and TR1001.

---

## File and Directory Breakdown

### **Root Directory**
- **README.md**: Quick start guide and overview of MSPSim.
- **CHANGE_LOG.txt**: Change history of the project.
- **license.txt**: License under which the project is distributed (BSD license).

### **Key Directories**

1. **`tests/`**: Contains test programs and configurations to validate the emulator's functionalities.
   - **msp430setup.c**: Initializes MSP430 peripherals (e.g., RS232, CPU).
   - **cputest.c**: Contains test cases for arithmetic, bitwise operations, and floating-point operations.
   - **timertest.c**: Verifies the accuracy and behavior of timer modules.
   - **Makefile**: Compilation rules for building and testing the programs.

2. **`lib/`**: Java libraries used for the simulation.
   - **json-simple-1.1.1.jar**: Lightweight library for JSON processing.
   - **jfreechart-1.0.11.jar**: For generating graphical charts in simulations.
   - **jcommon-1.0.14.jar**: Dependency for `jfreechart`.

3. **`se/sics/mspsim/`**: The core implementation of the simulator.
   - **`core/`**: Defines MSP430 CPU components, memory, and peripherals.
     - Example: `MSP430.java` (core CPU logic), `Timer.java` (timer subsystem).
   - **`platform/`**: Models different hardware platforms.
     - Example: `WismoteNode.java` (WiSmote platform simulation), `SkyNode.java`.
   - **`ui/`**: Graphical and interactive interfaces for simulation.
     - Example: `ChartPanel.java` (renders simulation charts), `DebugUI.java`.

4. **`firmware/`**: Pre-built firmware examples for supported platforms.
   - Example: `blink.wismote` (LED blink demo for WiSmote platform).

5. **`scripts/`**: Scripts for automating tasks.
   - Example: `autorun.sc` (runs predefined test scenarios).

---

## Workflow of MSPSim

### 1. **Building MSPSim**
- Prerequisite: Ensure Java (11+) and Apache Ant are installed.
- Steps:
  1. Navigate to the MSPSim root directory.
  2. Run the following command to compile the simulator:
     ```bash
     ant
     ```
  3. This will generate the required JAR files and make MSPSim ready for use.

### 2. **Running the Simulator**
- Example: Run the ESB node emulator with:
  ```bash
  ant runesb
  ```
- Example: Run the Sky node emulator with:
  ```bash
  ant runsky
  ```

### 3. **Testing Firmware**
- Supported firmware formats: `.ihex`, `.firmware` (ELF).
- To test, specify the firmware path:
  ```bash
  java -jar mspsim.jar -node platform.NodeName firmware_file
  ```

### 4. **Running Test Programs**
- Compile test programs using the provided Makefile.
- Example: Compiling and running `cputest`:
  ```bash
  cd tests
  make cputest.firmware
  java -jar mspsim.jar -node sky cputest.firmware
  ```
- The results (test pass/fail) are printed on the console.

---

## Explanation of Test Programs

1. **`msp430setup.c`**
   - Sets up the MSP430 peripherals, including:
     - **RS232**: Initializes serial communication with configurable baud rates.
     - **DCO**: Calibrates the digitally controlled oscillator (DCO) for clock speed.
     - **GPIO**: Configures input/output ports.

   Example Code for RS232 Initialization:
   ```c
   void rs232_init(void) {
       UCTL1 = CHAR;                         /* 8-bit character */
       UTCTL1 = SSEL1;                       /* UCLK = MCLK */
       rs232_set_speed(RS232_57600);         /* Set speed to 57600 bps */
       ME2 |= (UTXE1 | URXE1);               /* Enable USART TXD/RXD */
       IE2 |= URXIE1;                        /* Enable RX interrupt */
   }
   ```

2. **`cputest.c`**
   - Includes test cases for:
     - Arithmetic operations: Addition, subtraction, bitwise AND/OR.
     - Floating-point operations.
     - String manipulation.

   Example Test Case:
   ```c
   static void testSimple() {
       int a = 1, b = 2;
       assertTrue((a << b) == 4);
       assertFalse(a > b);
   }
   ```

3. **`timertest.c`**
   - Verifies the behavior of the Timer_A module.
   - Ensures correct timer interrupts and timekeeping.

   Example Timer Initialization:
   ```c
   void testTimers() {
       TACTL = TASSEL0 | TACLR | ID_3;   /* ACLK, divide by 8 */
       TACCTL1 = CCIE;                   /* Enable CCR1 interrupt */
       TACCR1 = INTERVAL;                /* Set interval */
       TACTL |= MC1;                     /* Start Timer_A in continuous mode */
   }
   ```

---

## Using MSPSim CLI

- Breakpoints:
  ```bash
  break <address>
  ```
- Profiling:
  ```bash
  profile start
  ```
- Monitoring:
  ```bash
  monitor <component>
  ```

---

## Advanced Usage

### Adding New Platforms
- Extend the `platform` package in `se/sics/mspsim`.
- Implement the required hardware behavior (e.g., GPIO, timers).

### Debugging with GDB
- Start MSPSim with GDB support:
  ```bash
  java -jar mspsim.jar -gdb
  ```
- Connect GDB to the simulator.

---

## Example Usage Scenarios

1. **Simulating LED Blink (WiSmote)**
   ```bash
   java -jar mspsim.jar -node wismote firmware/blink.wismote
   ```

2. **Running a Test Program**
   ```bash
   cd tests
   make timertest.firmware
   java -jar mspsim.jar -node sky timertest.firmware
   ```

---

## Troubleshooting

1. **Java Version Issues**
   - Ensure Java 11 or newer is installed.

2. **Compilation Errors**
   - Verify `msp430-gcc` is installed for compiling test programs.

3. **Incorrect Timer Behavior**
   - Check `timertest.c` for calibration constants.

---

## License

This software is distributed under the BSD license. Refer to `license.txt` for details.

---

## Contact

For further assistance, visit the official repository or reach out to the maintainers via the provided README.

