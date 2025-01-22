import java.io.*;
import java.util.*;

public class LLVMInstructionCycleAnalyzerUpdated {

    public static void main(String[] args) {
        String inputFilePath = "findreturn.ll";
        String outputFilePath = "instruction_analysis.csv";

        // To be updated....
        Map<String, Integer> instructionCycles = new HashMap<>();
        instructionCycles.put("load", 3);    // Example: 3 cycles for load
        instructionCycles.put("store", 3);
        instructionCycles.put("add", 1);
        instructionCycles.put("sub", 1);
        instructionCycles.put("mul", 2);
        instructionCycles.put("div", 4);
        instructionCycles.put("icmp", 1);
        instructionCycles.put("fcmp", 1);
        instructionCycles.put("call", 5);
        instructionCycles.put("ret", 1);


        // To be updated based on the config
        final int FETCH_DECODE_CYCLES = 1;  // Per instruction
        final int MEMORY_LATENCY = 10;     // Avg for cache misses (in cycles)
        final int BRANCH_PENALTY = 5;      // For mispredictions (in cycles)

        Map<String, Integer> instructionCounts = new HashMap<>();
        int totalInstructions = 0; // Count of all instructions
        int loadStoreCount = 0;    // Count of memory accesses
        int branchCount = 0;       // Count of branches (e.g., call, ret)

        try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath))) {
            String line;

            String instructionRegex = "\\b(load|store|add|sub|mul|div|icmp|fcmp|call|ret)\\b";

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.matches(".*" + instructionRegex + ".*")) {
                    for (String instruction : instructionCycles.keySet()) {
                        if (line.contains(instruction)) {
                            instructionCounts.put(instruction, instructionCounts.getOrDefault(instruction, 0) + 1);
                            totalInstructions++;
                            if (instruction.equals("load") || instruction.equals("store")) {
                                loadStoreCount++;
                            }
                            if (instruction.equals("call") || instruction.equals("ret")) {
                                branchCount++;
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the input file: " + e.getMessage());
            return;
        }

        int totalCycles = 0;

        for (Map.Entry<String, Integer> entry : instructionCounts.entrySet()) {
            String instruction = entry.getKey();
            int count = entry.getValue();
            int cyclesPerInstruction = instructionCycles.getOrDefault(instruction, 0);
            totalCycles += count * cyclesPerInstruction;
        }

        int fetchDecodeCycles = totalInstructions * FETCH_DECODE_CYCLES;
        int memoryOverhead = loadStoreCount * MEMORY_LATENCY;
        int branchOverhead = branchCount * BRANCH_PENALTY;

        totalCycles += fetchDecodeCycles + memoryOverhead + branchOverhead;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath))) {
            bw.write("Instruction_Name,Instruction_Count,Cycles_Per_Instruction\\n");

            for (Map.Entry<String, Integer> entry : instructionCounts.entrySet()) {
                String instruction = entry.getKey();
                int count = entry.getValue();
                int cycles = instructionCycles.getOrDefault(instruction, 0);
                bw.write(instruction + "," + count + "," + cycles + "\\n");
            }

            bw.write("\\nTotal Instructions," + totalInstructions + "\\n");
            bw.write("Total Cycles," + totalCycles + "\\n");

            System.out.println("CSV file generated successfully: " + outputFilePath);
        } catch (IOException e) {
            System.err.println("Error writing the CSV file: " + e.getMessage());
        }
    }
}
