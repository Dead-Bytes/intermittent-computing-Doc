import java.io.*;
import java.util.*;

public class LLVMInstructionAnalyzer {

    public static void main(String[] args) {
        String inputFilePath = "findreturn.ll"; // Input .ll file
        String outputFilePath = "instructions.csv"; // Output .csv file

        // To be updated
        Map<String, Integer> instructionCycles = new HashMap<>();
        instructionCycles.put("load", 3);
        instructionCycles.put("store", 3);
        instructionCycles.put("add", 1);
        instructionCycles.put("sub", 1);
        instructionCycles.put("mul", 2);
        instructionCycles.put("div", 4);
        instructionCycles.put("icmp", 1);
        instructionCycles.put("fcmp", 1);
        instructionCycles.put("call", 5);
        instructionCycles.put("ret", 1);

        Map<String, Integer> instructionCounts = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath))) {
            String line;

            String instructionRegex = "\\b(load|store|add|sub|mul|div|icmp|fcmp|call|ret)\\b";
            
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.matches(".*" + instructionRegex + ".*")) {
                    for (String instruction : instructionCycles.keySet()) {
                        if (line.contains(instruction)) {
                            instructionCounts.put(instruction, instructionCounts.getOrDefault(instruction, 0) + 1);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the input file: " + e.getMessage());
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath))) {
            bw.write("Instruction_Name,Instruction_Count,Cycles_Per_Instruction\n");

            for (Map.Entry<String, Integer> entry : instructionCounts.entrySet()) {
                String instruction = entry.getKey();
                int count = entry.getValue();
                int cycles = instructionCycles.getOrDefault(instruction, 0);
                bw.write(instruction + "," + count + "," + cycles + "\n");
                bw.flush();
            }

            System.out.println("CSV file generated successfully: " + outputFilePath);
        } catch (IOException e) {
            System.err.println("Error writing the CSV file: " + e.getMessage());
        }
    }
}