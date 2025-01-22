# Generating Firmware from C Program for MSPSim

## Overview
This guide explains the steps required to compile and generate a firmware file (in formats such as `.firmware` or `.ihex`) from a C program for use with MSPSim.

## Prerequisites
1. **MSP430 Toolchain:**
   - Install the MSP430-GCC toolchain, which includes:
     - `msp430-gcc`: Compiler
     - `msp430-ld`: Linker
     - `msp430-objcopy`: For format conversion
   - On Linux:
     ```bash
     sudo apt install gcc-msp430 gdb-msp430 binutils-msp430
     ```

2. **Makefile:**
   - Ensure your project includes a `Makefile` that defines the necessary build rules.
   - The provided `Makefile` in MSPSim's `/tests` directory is an example.

3. **C Program:**
   - Have your C program ready, such as `cputest.c` or `timertest.c`.

## Step-by-Step Instructions

### 1. Define Your C Program
- Write your C program. Example (`example.c`):

```c
#include <msp430.h>

int main(void) {
    WDTCTL = WDTPW | WDTHOLD;  // Stop watchdog timer
    P1DIR |= 0x01;             // Set P1.0 to output direction

    while (1) {
        P1OUT ^= 0x01;         // Toggle P1.0
        __delay_cycles(100000);
    }

    return 0;
}
```

### 2. Modify the Makefile
Ensure the `Makefile` has the correct definitions for your project:

#### Example `Makefile`:
```makefile
.SUFFIXES:

MCU=msp430f1611

CC       = msp430-gcc
LD       = msp430-ld
OBJCOPY  = msp430-objcopy
CFLAGS   = -mmcu=$(MCU) -Wall -g -O2

SOURCES := example.c
OBJECTS := $(SOURCES:.c=.o)

all: example.ihex

%.firmware: %.o
	$(CC) $(CFLAGS) -o $@ $^

%.ihex: %.firmware
	$(OBJCOPY) -O ihex $^ $@

%.o: %.c
	$(CC) $(CFLAGS) -c $< -o $@

clean:
	rm -f *.o *.ihex *.firmware
```

### 3. Compile the Program
Run the following commands in the terminal within the project directory:

1. **Compile and Link:**
   ```bash
   make
   ```
   This will generate `example.firmware` and `example.ihex` files.

2. **Clean Build Files (Optional):**
   ```bash
   make clean
   ```

### 4. Verify the Firmware File
- The generated `.ihex` or `.firmware` file can now be used in MSPSim.
- Example output file:
  - `example.ihex`: Intel HEX format.
  - `example.firmware`: Executable binary for the MSP430 emulator.

### 5. Load the Firmware in MSPSim
Use the following steps to load the firmware into MSPSim:

1. **Run MSPSim:**
   ```bash
   ant runsky
   ```
   or
   ```bash
   java -jar mspsim.jar
   ```

2. **Load Firmware:**
   From the MSPSim CLI, load your firmware:
   ```
   load example.ihex
   ```

### Troubleshooting
- **Missing Tools:** Ensure `msp430-gcc`, `msp430-ld`, and `msp430-objcopy` are installed and available in the system PATH.
- **Compilation Errors:** Verify the `Makefile` includes the correct MCU model and flags.
- **Incorrect Firmware Behavior:** Debug your C program using simulation tools like GDB or MSPSim's built-in debugging features.

### Testing the Generated Firmware
You can test the generated firmware by running it in MSPSim and observing the expected behavior, such as toggling LEDs.

---

## Example Test Case
To ensure everything works correctly:

1. Write a simple LED toggle program (e.g., `example.c`).
2. Compile using the provided `Makefile`.
3. Load and run the firmware in MSPSim.

If the simulation shows the LED toggling as expected, the process is successful.

---

## Conclusion
By following the steps outlined above, you can easily generate firmware files for MSPSim from C programs. Ensure that the toolchain is correctly installed and the `Makefile` is properly configured for your project.
