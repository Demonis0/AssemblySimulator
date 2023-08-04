package fr.matthieu.architecture.utils;

import fr.matthieu.architecture.errors.CommandNotFoundException;
import fr.matthieu.architecture.errors.invalidcommands.*;

public class Dispatcher {

    public Dispatcher() {

    }

    public void dispatch(boolean single) throws CommandNotFoundException, InvalidLDAException, InvalidSTRException, InvalidPUSHException, InvalidPOPException, InvalidANDException, InvalidORException, InvalidNOTException, InvalidADDException, InvalidSUBException, InvalidDIVException, InvalidMULException, InvalidMODException, InvalidINCException, InvalidDECException, InvalidComparisonException, InvalidJMPException {
        if (Constants.p.getCODE().size() < Constants.mm.getCounter()) return;

        String line = Constants.p.getCODE().get(Constants.mm.getCounter()-1);
        String[] args = line.split(" ");

        switch (args[0]) {
            case "LDA":

                if (args.length != 3) throw new InvalidLDAException("Too many arguments, should be 'LDA <reg> <reg/var/cst>' (Found: " + line + ")");

                if (!isReg(args[1])) throw new InvalidLDAException("Invalid register, should be reg1, reg2, reg3 or reg4 (Found: " + args[1] + ")");


                int var;
                if (isCst(args[2])) {
                    var = Integer.valueOf(args[2]);
                } else {
                    if (isReg(args[2])) {
                        var = Constants.mm.getRegisters().get(Registers.valueOf(args[2]).ordinal());
                    } else {
                        if (isVar(args[2])) {
                            var = Constants.mm.getVariableValue(args[2]);
                        } else {
                            throw new InvalidLDAException("Invalid argument, should be a register, a variable or a constant (Found: " + args[2] + ")");
                        }
                    }
                }

                Constants.mm.updateRegister(Registers.valueOf(args[1]).ordinal(), var);
                break;
            case "STR":
                if (args.length != 3) throw new InvalidSTRException("Too many arguments, should be 'STR <var> <reg/cst>' (Found: " + line + ")");

                if (!isVar(args[1])) throw new InvalidSTRException("Invalid variable, should be 'STR <var> <reg/cst>' (" + line + ")");

                int var2;
                if (isReg(args[2])) {
                    var2 = Constants.mm.getRegisters().get(Registers.valueOf(args[2]).ordinal());
                } else if (isCst(args[2])) {
                    var2 = Integer.parseInt(args[2]);
                } else {
                    throw new InvalidSTRException("The second argument must be either a register or a constant ("+ line + ")");
                }

                Constants.mm.changeVariableValue(args[1], var2);
                break;
            case "PUSH":
                if (args.length != 2) throw new InvalidPUSHException("Too many arguments, should be 'PUSH <reg/var/cst>' (Found: " + line + ")");

                int var3;
                if (isReg(args[1])) {
                    var3 = Constants.mm.getRegisters().get(Registers.valueOf(args[1]).ordinal());
                } else if (isVar(args[1])) {
                    var3 = Constants.mm.getVariableValue(args[1]);
                } else if (isCst(args[1])) {
                    var3 = Integer.parseInt(args[1]);
                } else {
                    throw new InvalidPUSHException("Invalid arguments, should be 'PUSH <reg/var/cst>' (Found: " + line + ")");
                }

                Constants.mm.push(var3);
                break;
            case "POP":
                if (args.length != 2) throw new InvalidPOPException("Too many arguments, should be 'POP <reg>' (Found: " + line + ")");

                if (!isReg(args[1])) throw new InvalidPOPException("Invalid argument, should be 'POP <reg>' (Found: " + line + ")");

                Constants.mm.updateRegister(Registers.valueOf(args[1]).ordinal(), Constants.mm.pop());
                break;
            case "AND":
                if (args.length != 3) throw new InvalidANDException("Too many arguments, should be 'AND <reg> <reg/var/cst>' (Found: " + line + ")");

                if (!isReg(args[1])) throw new InvalidANDException("Invalid argument, should be 'AND <reg> <reg/var/cst>' (Found: " + line + ")");

                int var4;
                if (isReg(args[2])) {
                    var4 = Constants.mm.getRegisters().get(Registers.valueOf(args[2]).ordinal());
                } else if (isVar(args[2])) {
                    var4 = Constants.mm.getVariableValue(args[2]);
                } else if (isCst(args[2])) {
                    var4 = Integer.parseInt(args[2]);
                } else {
                    throw new InvalidANDException("Invalid arguments, should be 'AND <reg> <reg/var/cst>' (Found: " + line + ")");
                }

                Constants.mm.updateRegister(Registers.valueOf(args[1]).ordinal(), Constants.mm.getRegisters().get(Registers.valueOf(args[1]).ordinal()) & var4);
                break;
            case "OR":
                if (args.length != 3) throw new InvalidORException("Too many arguments, should be 'OR <reg> <reg/var/cst>' (Found: " + line + ")");

                if (!isReg(args[1])) throw new InvalidORException("Invalid argument, should be 'OR <reg> <reg/var/cst>' (Found: " + line + ")");

                int var5;
                if (isReg(args[2])) {
                    var5 = Constants.mm.getRegisters().get(Registers.valueOf(args[2]).ordinal());
                } else if (isVar(args[2])) {
                    var5 = Constants.mm.getVariableValue(args[2]);
                } else if (isCst(args[2])) {
                    var5 = Integer.parseInt(args[2]);
                } else {
                    throw new InvalidORException("Invalid arguments, should be 'OR <reg> <reg/var/cst>' (Found: " + line + ")");
                }

                Constants.mm.updateRegister(Registers.valueOf(args[1]).ordinal(), Constants.mm.getRegisters().get(Registers.valueOf(args[1]).ordinal()) | var5);
                break;
            case "NOT":
                if (args.length != 2) throw new InvalidNOTException("Too many arguments, should be 'NOT <reg>' (Found: " + line + ")");

                if (!isReg(args[1])) throw new InvalidNOTException("Invalid argument, should be 'NOT <reg>' (Found: " + line + ")");

                Constants.mm.updateRegister(Registers.valueOf(args[1]).ordinal(), ~Constants.mm.getRegisters().get(Registers.valueOf(args[1]).ordinal()));
                break;
            case "ADD":
                if (args.length != 3) throw new InvalidADDException("Too many arguments, should be 'ADD <reg> <reg/var/cst>' (Found: " + line + ")");

                if (!isReg(args[1])) throw new InvalidADDException("Invalid argument, should be 'ADD <reg> <reg/var/cst>' (Found: " + line + ")");

                int var6;
                if (isReg(args[2])) {
                    var6 = Constants.mm.getRegisters().get(Registers.valueOf(args[2]).ordinal());
                } else if (isVar(args[2])) {
                    var6 = Constants.mm.getVariableValue(args[2]);
                } else if (isCst(args[2])) {
                    var6 = Integer.parseInt(args[2]);
                } else {
                    throw new InvalidADDException("Invalid arguments, should be 'ADD <reg> <reg/var/cst>' (Found: " + line + ")");
                }

                Constants.mm.updateRegister(Registers.valueOf(args[1]).ordinal(), Constants.mm.getRegisters().get(Registers.valueOf(args[1]).ordinal()) + var6);
                break;
            case "SUB":
                if (args.length != 3) throw new InvalidSUBException("Too many arguments, should be 'SUB <reg> <reg/var/cst>' (Found: " + line + ")");

                if (!isReg(args[1])) throw new InvalidSUBException("Invalid argument, should be 'SUB <reg> <reg/var/cst>' (Found: " + line + ")");

                int var7;
                if (isReg(args[2])) {
                    var7 = Constants.mm.getRegisters().get(Registers.valueOf(args[2]).ordinal());
                } else if (isVar(args[2])) {
                    var7 = Constants.mm.getVariableValue(args[2]);
                } else if (isCst(args[2])) {
                    var7 = Integer.parseInt(args[2]);
                } else {
                    throw new InvalidSUBException("Invalid arguments, should be 'SUB <reg> <reg/var/cst>' (Found: " + line + ")");
                }

                Constants.mm.updateRegister(Registers.valueOf(args[1]).ordinal(), var7 - Constants.mm.getRegisters().get(Registers.valueOf(args[1]).ordinal()));
                break;
            case "DIV":
                if (args.length != 3) throw new InvalidDIVException("Too many arguments, should be 'DIV <reg> <reg/var/cst>' (Found: " + line + ")");

                if (!isReg(args[1])) throw new InvalidDIVException("Invalid argument, should be 'DIV <reg> <reg/var/cst>' (Found: " + line + ")");

                int var8;
                if (isReg(args[2])) {
                    var8 = Constants.mm.getRegisters().get(Registers.valueOf(args[2]).ordinal());
                } else if (isVar(args[2])) {
                    var8 = Constants.mm.getVariableValue(args[2]);
                } else if (isCst(args[2])) {
                    var8 = Integer.parseInt(args[2]);
                } else {
                    throw new InvalidDIVException("Invalid arguments, should be 'DIV <reg> <reg/var/cst>' (Found: " + line + ")");
                }

                Constants.mm.updateRegister(Registers.valueOf(args[1]).ordinal(), var8 / Constants.mm.getRegisters().get(Registers.valueOf(args[1]).ordinal()));
                break;
            case "MUL":
                if (args.length != 3) throw new InvalidMULException("Too many arguments, should be 'MUL <reg> <reg/var/cst>' (Found: " + line + ")");

                if (!isReg(args[1])) throw new InvalidMULException("Invalid argument, should be 'MUL <reg> <reg/var/cst>' (Found: " + line + ")");

                int var9;
                if (isReg(args[2])) {
                    var9 = Constants.mm.getRegisters().get(Registers.valueOf(args[2]).ordinal());
                } else if (isVar(args[2])) {
                    var9 = Constants.mm.getVariableValue(args[2]);
                } else if (isCst(args[2])) {
                    var9 = Integer.parseInt(args[2]);
                } else {
                    throw new InvalidMULException("Invalid arguments, should be 'MUL <reg> <reg/var/cst>' (Found: " + line + ")");
                }

                Constants.mm.updateRegister(Registers.valueOf(args[1]).ordinal(), var9 * Constants.mm.getRegisters().get(Registers.valueOf(args[1]).ordinal()));
                break;
            case "MOD":
                if (args.length != 3) throw new InvalidMODException("Too many arguments, should be 'MOD <reg> <reg/var/cst>' (Found: " + line + ")");

                if (!isReg(args[1])) throw new InvalidMODException("Invalid argument, should be 'MOD <reg> <reg/var/cst>' (Found: " + line + ")");

                int var10;
                if (isReg(args[2])) {
                    var10 = Constants.mm.getRegisters().get(Registers.valueOf(args[2]).ordinal());
                } else if (isVar(args[2])) {
                    var10 = Constants.mm.getVariableValue(args[2]);
                } else if (isCst(args[2])) {
                    var10 = Integer.parseInt(args[2]);
                } else {
                    throw new InvalidMODException("Invalid arguments, should be 'MOD <reg> <reg/var/cst>' (Found: " + line + ")");
                }

                Constants.mm.updateRegister(Registers.valueOf(args[1]).ordinal(), var10 % Constants.mm.getRegisters().get(Registers.valueOf(args[1]).ordinal()));
                break;
            case "INC":
                if (args.length != 2) throw new InvalidINCException("Too many arguments, should be 'INC <reg>' (Found: " + line + ")");

                if (!isReg(args[1])) throw new InvalidINCException("Invalid argument, should be 'INC <reg>' (Found: " + line + ")");

                Constants.mm.incReg(Registers.valueOf(args[1]).ordinal());
                break;
            case "DEC":
                if (args.length != 2) throw new InvalidDECException("Too many arguments, should be 'DEC <reg>' (Found: " + line + ")");

                if (!isReg(args[1])) throw new InvalidDECException("Invalid argument, should be 'DEC <reg>' (Found: " + line + ")");

                Constants.mm.decReg(Registers.valueOf(args[1]).ordinal());
                break;
            case "BEQ":
                if (args.length != 4) throw new InvalidComparisonException("Too many arguments, should be 'BEQ <reg/var/cst> <reg/var/cst> <LABEL>' (Found: " + line + ")");

                if (!Constants.mm.labels.keySet().contains(args[3])) throw new InvalidComparisonException("Label does not exist (" + line + ")");

                int var11;
                if (isReg(args[1])) {
                    var11 = Constants.mm.getRegisters().get(Registers.valueOf(args[1]).ordinal());
                } else if (isVar(args[1])) {
                    var11 = Constants.mm.getVariableValue(args[1]);
                } else if (isCst(args[1])) {
                    var11 = Integer.parseInt(args[1]);
                } else {
                    throw new InvalidComparisonException("Invalid arguments, should be 'BEQ <reg/var/cst> <reg/var/cst> <LABEL>' (Found: " + line + ")");
                }

                int var12;
                if (isReg(args[2])) {
                    var12 = Constants.mm.getRegisters().get(Registers.valueOf(args[2]).ordinal());
                } else if (isVar(args[2])) {
                    var12 = Constants.mm.getVariableValue(args[2]);
                } else if (isCst(args[2])) {
                    var12 = Integer.parseInt(args[2]);
                } else {
                    throw new InvalidComparisonException("Invalid arguments, should be 'BEQ <reg/var/cst> <reg/var/cst> <LABEL>' (Found: " + line + ")");
                }

                if (var11 == var12) {
                    Constants.mm.setCounter(Constants.mm.getLabelAdress(args[3]));
                }
                break;
            case "BNE":
                if (args.length != 4) throw new InvalidComparisonException("Too many arguments, should be 'BNE <reg/var/cst> <reg/var/cst> <LABEL>' (Found: " + line + ")");

                if (!Constants.mm.labels.keySet().contains(args[3])) throw new InvalidComparisonException("Label does not exist (" + line + ")");

                int var13;
                if (isReg(args[1])) {
                    var13 = Constants.mm.getRegisters().get(Registers.valueOf(args[1]).ordinal());
                } else if (isVar(args[1])) {
                    var13 = Constants.mm.getVariableValue(args[1]);
                } else if (isCst(args[1])) {
                    var13 = Integer.parseInt(args[1]);
                } else {
                    throw new InvalidComparisonException("Invalid arguments, should be 'BNE <reg/var/cst> <reg/var/cst> <LABEL>' (Found: " + line + ")");
                }

                int var14;
                if (isReg(args[2])) {
                    var14 = Constants.mm.getRegisters().get(Registers.valueOf(args[2]).ordinal());
                } else if (isVar(args[2])) {
                    var14 = Constants.mm.getVariableValue(args[2]);
                } else if (isCst(args[2])) {
                    var14 = Integer.parseInt(args[2]);
                } else {
                    throw new InvalidComparisonException("Invalid arguments, should be 'BNE <reg/var/cst> <reg/var/cst> <LABEL>' (Found: " + line + ")");
                }

                if (var13 != var14) {
                    Constants.mm.setCounter(Constants.mm.getLabelAdress(args[3]));
                }
                break;
            case "BBG":
                if (args.length != 4) throw new InvalidComparisonException("Too many arguments, should be 'BBG <reg/var/cst> <reg/var/cst> <LABEL>' (Found: " + line + ")");

                if (!Constants.mm.labels.keySet().contains(args[3])) throw new InvalidComparisonException("Label does not exist (" + line + ")");

                int var15;
                if (isReg(args[1])) {
                    var15 = Constants.mm.getRegisters().get(Registers.valueOf(args[1]).ordinal());
                } else if (isVar(args[1])) {
                    var15 = Constants.mm.getVariableValue(args[1]);
                } else if (isCst(args[1])) {
                    var15 = Integer.parseInt(args[1]);
                } else {
                    throw new InvalidComparisonException("Invalid arguments, should be 'BBG <reg/var/cst> <reg/var/cst> <LABEL>' (Found: " + line + ")");
                }

                int var16;
                if (isReg(args[2])) {
                    var16 = Constants.mm.getRegisters().get(Registers.valueOf(args[2]).ordinal());
                } else if (isVar(args[2])) {
                    var16 = Constants.mm.getVariableValue(args[2]);
                } else if (isCst(args[2])) {
                    var16 = Integer.parseInt(args[2]);
                } else {
                    throw new InvalidComparisonException("Invalid arguments, should be 'BBG <reg/var/cst> <reg/var/cst> <LABEL>' (Found: " + line + ")");
                }
                System.out.println(var15);
                System.out.println(var16);
                System.out.println(var15 > var16);
                if (var15 > var16) {
                    Constants.mm.setCounter(Constants.mm.getLabelAdress(args[3]));
                }
                break;
            case "BSM":
                if (args.length != 4) throw new InvalidComparisonException("Too many arguments, should be 'BSM <reg/var/cst> <reg/var/cst> <LABEL>' (Found: " + line + ")");

                if (!Constants.mm.labels.keySet().contains(args[3])) throw new InvalidComparisonException("Label does not exist (" + line + ")");

                int var17;
                if (isReg(args[1])) {
                    var17 = Constants.mm.getRegisters().get(Registers.valueOf(args[1]).ordinal());
                } else if (isVar(args[1])) {
                    var17 = Constants.mm.getVariableValue(args[1]);
                } else if (isCst(args[1])) {
                    var17 = Integer.parseInt(args[1]);
                } else {
                    throw new InvalidComparisonException("Invalid arguments, should be 'BSM <reg/var/cst> <reg/var/cst> <LABEL>' (Found: " + line + ")");
                }

                int var18;
                if (isReg(args[2])) {
                    var18 = Constants.mm.getRegisters().get(Registers.valueOf(args[2]).ordinal());
                } else if (isVar(args[2])) {
                    var18 = Constants.mm.getVariableValue(args[2]);
                } else if (isCst(args[2])) {
                    var18 = Integer.parseInt(args[2]);
                } else {
                    throw new InvalidComparisonException("Invalid arguments, should be 'BSM <reg/var/cst> <reg/var/cst> <LABEL>' (Found: " + line + ")");
                }

                if (var17 < var18) {
                    Constants.mm.setCounter(Constants.mm.getLabelAdress(args[3]));
                }
                break;
            case "JMP":
                if (args.length != 2) throw new InvalidJMPException("Too many arguments, should be 'JMP <LABEL>' (Found: " + line + ")");

                if (!Constants.mm.labels.keySet().contains(args[1])) throw new InvalidJMPException("Label does not exist (" + line + ")");

                Constants.mm.setCounter(Constants.mm.getLabelAdress(args[1]));
                break;
            case "HLT":
                Constants.mm.setCounter(Constants.p.CODE.size()+1);
                break;
            default:
                throw new CommandNotFoundException("Command not found (" + args[0] + ")");
        }
        Constants.mm.setCounter(Constants.mm.getCounter()+1);
        if (!single)    dispatch(false);
    }

    public boolean isReg(String reg) {
        try {
            Registers re = Registers.valueOf(reg);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    public boolean isVar(String name) {
        return Constants.mm.getMemorySlots().keySet().contains(name);
    }

    public boolean isCst(String cst) {
        try {
            int var = Integer.valueOf(cst);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
