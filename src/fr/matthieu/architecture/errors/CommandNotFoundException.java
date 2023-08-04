package fr.matthieu.architecture.errors;

public class CommandNotFoundException extends Exception{
    public CommandNotFoundException(String message) {
        super(message);
    }
}
