
# MSP430 Simulator (MSPSim) Documentation

## Overview
MSPSim is a Java-based instruction-level emulator designed to emulate the MSP430 microcontroller family. It includes simulation for several sensor networking platforms, allowing developers to test and debug their firmware in a simulated environment.

## Features
- Emulation of MSP430 microprocessor instruction set.
- Support for loading IHEX and ELF firmware files.
- Tools for profiling, setting breakpoints, and monitoring stacks.
- CLI for debugging and automation.
- Built-in support for GDB remote debugging.

## System Requirements
- **Java**: Version 11 or later.
- **Build Tool**: Apache Ant.

## Building MSPSim
1. Clone the repository.
2. Navigate to the `mspsim` directory.
3. Run the following command to build the simulator:
   ```bash
   ant
   ```

## Directory Structure

```
mspsim
├── tests                  # Test programs for validation.
├── license.txt            # Licensing information.
├── images                 # Images for supported platforms.
├── lib                    # Java libraries required for MSPSim.
├── se/sics/mspsim         # Core source code for the emulator.
├── README.md              # Basic usage and features.
├── scripts                # Example scripts for automation.
├── firmware               # Example firmware for supported platforms.
├── CHANGE_LOG.txt         # Change log for version tracking.
└── build.xml              # Ant build script.
```

## Example Platforms Supported
- **ESB Node Emulator**: Run using `ant runesb`.
- **Sky Node Emulator**: Run using `ant runsky`.

## Emulator Capabilities
### Hardware Emulated
- CPU (Instruction-level simulation)
- Timer A/B subsystem
- USARTs (Serial Communication)
- Digital I/O
- Multiplication unit
- Basic A/D subsystem
- Watchdog

### Limitations
- Flash writes and certain memory restrictions are not enforced.
- DMA is not implemented.
- Timer emulation is incomplete.

## Key Files
### Test Programs
- **msp430setup.c**: Contains initialization for MSP430 RS232 and CPU.
- **cputest.c**: Unit tests for arithmetic, strings, timers, etc.
- **timertest.c**: Specific tests for Timer A operations.

### Headers
- **msp430setup.h**: Header file defining RS232 communication and utility functions.

### Build Configuration
- **Makefile**: Contains build rules and dependencies.

## Example Code
### RS232 Initialization (`msp430setup.c`)
The following code initializes the RS232 module for communication:
```c
void rs232_init(void) {
  UCTL1 = CHAR;                         /* 8-bit character */
  UTCTL1 = SSEL1;                       /* UCLK = MCLK */
  rs232_set_speed(RS232_57600);         /* Default speed */
  input_handler = NULL;
  ME2 |= (UTXE1 | URXE1);               /* Enable TXD/RXD */
  IE2 |= URXIE1;                        /* Enable RX interrupt */
}
```

### Timer Example (`timertest.c`)
This test ensures accurate timer operations using `Timer_A`:
```c
void testTimers() {
  dint();                              /* Disable interrupts */
  TACTL = TASSEL0 | TACLR | ID_3;      /* Select ACLK, divide by 8 */
  TACCTL1 = CCIE;                      /* Enable CCR1 interrupt */
  TACCR1 = INTERVAL;                   /* Set interval */
  TACTL |= MC1;                        /* Start Timer_A in continuous mode */
  eint();                              /* Enable interrupts */
}
```

### Unit Test Macros (`cputest.c`)
MSPSim uses macros for test validations:
```c
#define TEST(...) if(__VA_ARGS__) {   printf("OK: " #__VA_ARGS__ " passed at %s:%d
", __FILE__,__LINE__); } else {   printf("FAIL: " #__VA_ARGS__ " failed at %s:%d
", __FILE__,__LINE__); }
```

## License
This software is distributed under the BSD license. For full details, see `license.txt`.

## Contact
For more information or support, please refer to the `README.md` file or contact the maintainers.
