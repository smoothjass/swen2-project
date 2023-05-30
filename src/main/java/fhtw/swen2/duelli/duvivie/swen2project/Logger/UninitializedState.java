package fhtw.swen2.duelli.duvivie.swen2project.Logger;

public class UninitializedState extends LoggerStateBase {

    @Override
    public void debug(String message) {
        this.printUninitializedWarning();
        return;
    }

    @Override
    public void fatal(String message) {
        this.printUninitializedWarning();
        return;
    }

    @Override
    public void error(String message) {
        this.printUninitializedWarning();
        return;
    }

    @Override
    public void warn(String message) {
        this.printUninitializedWarning();
        return;
    }

    private void printUninitializedWarning() {
        System.out.println("Operation was called in state uninitialized.");
    }
}
