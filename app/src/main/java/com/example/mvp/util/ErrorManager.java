package com.example.mvp.util;

public final class ErrorManager {

    private ErrorManager() {

    }

    public static String handleError(NetworkError error) {
        String errorMessage;
        switch (error) {
            case BAD_REQUEST:
                errorMessage = "Bad request";
                break;
            case UNAUTHORIZED:
                errorMessage = "Unauthorized Access";
                break;
            case FOBIDDEN:
                errorMessage = "Access is forbidden";
                break;
            case NOT_FOUND:
                errorMessage = "Resource not found";
                break;
            case SERVER:
                errorMessage = "Internal Server Error";
                break;
            case NO_DATA:
                errorMessage = "No users found";
                break;
            default:
                errorMessage = "An unexpected error occurred";
        }
        return errorMessage;
    }

}
