# MSPSim Command Documentation

## DebugCommands

### `break`
- **Description**: Add a breakpoint to a given address or symbol.
- **Usage**: `break <address or symbol>`

### `watch`
- **Description**: Add a write/read watch to a given address or symbol.
- **Usage**: `watch <address or symbol> [length] [char | hex | break]`

### `watchreg`
- **Description**: Add a write watch to a given register.
- **Usage**: `watchreg <register> [int]`

### `clear`
- **Description**: Clear a breakpoint or watch from a given address or symbol.
- **Usage**: `clear <address or symbol>`

### `start`
- **Description**: Start the CPU.
- **Usage**: `start`

### `throw`
- **Description**: Throw an Emulation Exception.
- **Usage**: `throw [message]`

### `step`
- **Description**: Single step the CPU.
- **Usage**: `step [number of instructions]`

### `stepmicro`
- **Description**: Single step the CPU for a specified number of microseconds.
- **Usage**: `stepmicro <micro skip> <micro step>`

### `stack`
- **Description**: Show stack info.
- **Usage**: `stack`

### `print`
- **Description**: Print value of an address or symbol.
- **Usage**: `print <address or symbol>`

### `printreg`
- **Description**: Print value of a register.
- **Usage**: `printreg [register]`

### `reset`
- **Description**: Reset the CPU.
- **Usage**: `reset`

### `time`
- **Description**: Print the elapsed time and cycles.
- **Usage**: `time`

### `mem`
- **Description**: Dump memory.
- **Usage**: `mem <start address> <num_entries> [type] [hex|char|dis]`

### `mset`
- **Description**: Set memory.
- **Usage**: `mset <address> [type] <value> [value ...]`

### `xmem`
- **Description**: Dump flash memory.
- **Usage**: `xmem <start address> <num_entries> [type]`

### `xmset`
- **Description**: Set flash memory.
- **Usage**: `xmset <address> <value> [type]`

### `trace`
- **Description**: Store a trace of execution positions.
- **Usage**: `trace [trace size | show]`

### `events`
- **Description**: Print event queues.
- **Usage**: `events`

### `gdbstubs`
- **Description**: Open a GDB stubs server for GDB remote debugging.
- **Usage**: `gdbstubs <port>`

### `log`
- **Description**: Log a loggable object.
- **Usage**: `log [loggable...]`

### `logevents`
- **Description**: Log events.
- **Usage**: `logevents [chips...]`

### `profile`
- **Description**: Show profile information.
- **Usage**: `profile [-clear] [-sort column] [-showcallers] [regexp]`

### `stacktrace`
- **Description**: Show stack trace.
- **Usage**: `stacktrace`

### `stackprof`
- **Description**: Start stack profiler.
- **Usage**: `stackprof`

### `irqprofile`
- **Description**: Show interrupt profile.
- **Usage**: `irqprofile`

### `logcalls`
- **Description**: Log function calls.
- **Usage**: `logcalls`

### `profiler`
- **Description**: Configure profiler.
- **Usage**: `profiler <command> <arguments>`

### `readmap`
- **Description**: Read map.
- **Usage**: `readmap`

### `tagprof`
- **Description**: Profile between two events.
- **Usage**: `tagprof`

### `printtags`
- **Description**: Print tags profile.
- **Usage**: `printtags`

## DivCommand

### `save`
- **Description**: Save CPU state to file.
- **Usage**: `save <filename>`

### `load`
- **Description**: Load CPU state from file.
- **Usage**: `load <filename>`

## MiscCommands

### `speed`
- **Description**: Set the speed factor for the CPU.
- **Usage**: `speed [factor]`

### `echo`
- **Description**: Echo arguments.
- **Usage**: `echo`

### `source`
- **Description**: Run script.
- **Usage**: `source [-v] <filename>`

### `repeat`
- **Description**: Repeat the specified command line.
- **Usage**: `repeat [-t delay] [-c count] <command line>`

### `sysinfo`
- **Description**: Show info about the MSPSim system.
- **Usage**: `sysinfo [-registry] [-config]`

### `quit`
- **Description**: Exit MSPSim.
- **Usage**: `quit`

### `exit`
- **Description**: Exit MSPSim.
- **Usage**: `exit`

### `set`
- **Description**: Set a config parameter.
- **Usage**: `set <parameter> <value>`

### `exec`
- **Description**: Execute a command.
- **Usage**: `exec <command>`

### `trig`
- **Description**: Trigger command when getting input.
- **Usage**: `trig <command>`

### `install`
- **Description**: Install and start a plugin.
- **Usage**: `install <ClassName> [Name]`

### `service`
- **Description**: Handle service plugins.
- **Usage**: `service [-f] [class name|service name] [start|stop]`

### `rflistener`
- **Description**: RF listener.
- **Usage**: `rflistener <input|output> <rf-chip>`

## ProfilerCommands

### `profile`
- **Description**: Show profile information.
- **Usage**: `profile [-clear] [-sort column] [-showcallers] [regexp]`

### `stacktrace`
- **Description**: Show stack trace.
- **Usage**: `stacktrace`

### `stackprof`
- **Description**: Start stack profiler.
- **Usage**: `stackprof`

### `irqprofile`
- **Description**: Show interrupt profile.
- **Usage**: `irqprofile`

### `logcalls`
- **Description**: Log function calls.
- **Usage**: `logcalls`

### `profiler`
- **Description**: Configure profiler.
- **Usage**: `profiler <command> <arguments>`

### `readmap`
- **Description**: Read map.
- **Usage**: `readmap`

### `tagprof`
- **Description**: Profile between two events.
- **Usage**: `tagprof`

### `printtags`
- **Description**: Print tags profile.
- **Usage**: `printtags`

## FileCommands

### `>`
- **Description**: Redirect output to file.
- **Usage**: `> <filename>`

### `>>`
- **Description**: Append output to file.
- **Usage**: `>> <filename>`

### `tee`
- **Description**: Redirect to file and standard out.
- **Usage**: `tee <filename>`

### `fclose`
- **Description**: Close the specified file.
- **Usage**: `fclose <filename>`

### `files`
- **Description**: List open files.
- **Usage**: `files`

## NetCommands

### `ipstack`
- **Description**: Setup 802.15.4/IP stack.
- **Usage**: `ipstack`

### `tspstart`
- **Description**: Start a TSP tunnel.
- **Usage**: `tspstart <server> <user> <password>`

## WindowCommands

### `window`
- **Description**: Redirect input to a window.
- **Usage**: `window [-close|-clear|-list] [windowname]`

### `wclear`
- **Description**: Reset stored window positions.
- **Usage**: `wclear`

## StatCommands

### `stat`
- **Description**: Show statistics.
- **Usage**: `stat [unit]`

### `statreset`
- **Description**: Reset statistics.
- **Usage**: `statreset`

### `statstart`
- **Description**: Start statistics collection.
- **Usage**: `statstart`

### `statstop`
- **Description**: Stop statistics collection.
- **Usage**: `statstop`