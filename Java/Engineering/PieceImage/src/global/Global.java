package global;

public class Global {

    private static String state = "no_start";
    public static String STATE_NO_START = "no_start";
    public static String STATE_LOAD_EXCEL = "load_excel";
    public static String STATE_PIECE_IMAGE = "piece_image";
    public static String STATE_ERROR = "error";
    public static String STATE_SUCCESS = "success";

    public static String getState() {
        synchronized (state) {
            return state;
        }
    }

    public static void setState(String cusState) {
        synchronized (state) {
            state = cusState;
        }
    }
    
    public static String SAVE_URL = "";
}
