package com.minortechnologies.workr_backend.networkhandler;

public class NetworkResponseConstants {

    public static final String ERROR_KEY = "error";

    // Authentication Codes
    public static final int OPERATION_SUCCESS = 1;
    public static final int TOKEN_AUTH_FAIL = 0;
    public static final String TOKEN_AUTH_FAIL_STRING = "error 0: user authentication failed";

    // General HTTP Request stuff
    public static final int PAYLOAD_MALFORMED = 2;
    public static final String PAYLOAD_MALFORMED_STRING = "error 2: payload malformed";

    // Account
    public static final int LOGIN_TAKEN = 3;
    public static final int INVALID_LOGIN = 4;
    public static final int INVALID_EMAIL = 5;
    public static final int INVALID_PASSWORD = 6;

    // Map related issues
    public static final int KEY_NOT_EXIST = 7;
    public static final int DATA_MALFORMED = 8;
    public static final String DATA_MALFORMED_STRING = "error 8: map data malformed";
    public static final int INCORRECT_KEYS = 9;
    public static final String INCORRECT_KEYS_STRING = "error 9: provided keys does not correspond to request type";

    //
}
