package chain.utilities;

/**
 * Created by JOHNNY on 4/13/18.
 */
public class NCExceptions extends Exception {

    protected int exceptionID;
    protected String exceptionMessage;

    public NCExceptions(int ID, String message) {
        this.exceptionID = ID;
        this.exceptionMessage = message;
    }

    protected void printErrorMessage() {
        this.printStackTrace();
        System.out.println(String.format("Exception Type: %d\nException Message: %s", exceptionID, exceptionMessage));
    }

    public static class NCHashException extends NCExceptions {
        public NCHashException() {
            super(1, "can't calculate block hash value");
        }
    }
}
