package fr.matthieu.architecture.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.TreeMap;

public class MemoryManager {

    List<Integer> registers;
    TreeMap<String, Integer> memorySlots;
    TreeMap<String, Integer> labels;
    List<Integer> values;
    int nextSlot;

    Stack<Integer> stack;

    int counter;

    public MemoryManager() {
        this.memorySlots = new TreeMap<>();
        this.labels = new TreeMap<>();
        this.values = new ArrayList<>();
        this.stack = new Stack<>();
        this.counter = 1;
        this.nextSlot = 0;

        this.registers = new ArrayList<>();
        for (int i = 0; i < 5; i++) this.registers.add(0);
    }

    public void createVariable(String name, int value) {
        this.memorySlots.put(name, this.nextSlot);
        this.values.add(value);
        this.nextSlot++;
    }

    public void updateRegister(int reg, int value) {
        this.registers.set(reg,value);
    }

    public void changeVariableValue(String name, int value) {
        this.values.set(this.memorySlots.get(name),value);
    }

    public int getVariableLocation(String name) {
        return this.memorySlots.get(name);
    }

    public int getSlotValue(int slot) {
        return this.values.get(slot);
    }

    public int getVariableValue(String var) {
        return this.values.get(this.memorySlots.get(var));
    }

    public void push(int value) {
        this.stack.push(value);
    }

    public int pop() {
        return this.stack.pop();
    }

    public int getLabelAdress(String name) {
        return this.labels.get(name);
    }

    public void addLabel(String name, int line) {
        this.labels.put(name, line);
    }

    public void incReg(int reg) {
        this.registers.set(reg, this.registers.get(reg) + 1);
    }

    public void decReg(int reg) {
        this.registers.set(reg, this.registers.get(reg) - 1);
    }

    public List<Integer> getRegisters() {
        return registers;
    }

    public void setRegisters(List<Integer> registers) {
        this.registers = registers;
    }

    public TreeMap<String, Integer> getMemorySlots() {
        return memorySlots;
    }

    public void setMemorySlots(TreeMap<String, Integer> memorySlots) {
        this.memorySlots = memorySlots;
    }

    public List<Integer> getValues() {
        return values;
    }

    public void setValues(List<Integer> values) {
        this.values = values;
    }

    public int getNextSlot() {
        return nextSlot;
    }

    public void setNextSlot(int nextSlot) {
        this.nextSlot = nextSlot;
    }

    public Stack<Integer> getStack() {
        return stack;
    }

    public void setStack(Stack<Integer> stack) {
        this.stack = stack;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
