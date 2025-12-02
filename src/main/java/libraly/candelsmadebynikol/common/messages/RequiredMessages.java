package libraly.candelsmadebynikol.common.messages;

public class RequiredMessages {

    //User registration required messages
    public static final String USERNAME_REQUIRED = "Username required";
    public static final String REQUIRED_USERNAME_LENGTH = "Username must be between 3 and 20 characters";
    public static final String PASSWORD_REQUIRED = "Password required";
    public static final String PASSWORD_LENGTH = "Password must be at least 5 characters long";
    public static final String CONFIRM_PASSWORD_REQUIRED = "Confirm Password required";
    public static final String EMAIL_REQUIRED = "Email required";
    public static final String VALID_EMAIL = "Please provide a valid email address";

    // Adding candle required messages
    public static final String CANDLE_NAME_REQUIRED = "Candle name required";
    public static final String DESCRIPTION_REQUIRED = "Description is required";
    public static final String PRICE_REQUIRED = "Price is required";
    public static final String PRICE_POSITIVE_REQUIRED = "Price must be greater than 0";
    public static final String IMAGE_REQUIRED = "Image is required";
    public static final String SKU_REQUIRED = "SKU is required for inventory tracking";
    public static final String CATEGORY_REQUIRED = "You must select a category";



    public RequiredMessages() {
    }
}
