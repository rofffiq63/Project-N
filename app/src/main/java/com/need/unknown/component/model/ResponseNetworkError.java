package com.need.unknown.component.model;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.HttpException;

import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;

public class ResponseNetworkError extends Throwable {
    public static final String HTTP_ERROR_MESSAGE = "HTTP Error";
    public static final String DEFAULT_ERROR_MESSAGE = "Something went wrong. Please try again";
    public static final String UNAUTHORIZED = "Unauthorized";
    public static final String NULL_ERROR_MESSAGE = "NULL Response";
    public static final String NETWORK_ERROR_MESSAGE = "Masalah pada jaringan. mohon periksa kembali jaringan anda";
    public static final String ERROR_MESSAGE_HEADER = "Error-Message";
    private static final String TAG = "ERROR_MESSAGE";
    private final Throwable error;
    private Response responseJson;

    public ResponseNetworkError(Throwable e) {
        super(e);
        this.error = e;
    }

    public String getMessage() {
        return error.getMessage();
    }

    public boolean isAuthFailure() {
        return error instanceof HttpException && ((HttpException) error).code() == HTTP_UNAUTHORIZED;
    }

    public boolean isResponseNull() {
        return error instanceof HttpException && ((HttpException) error).response() == null;
    }

    public String getAppErrorMessage() {
        if (getResponse() != null)
            return getResponse().getMessage();

        return DEFAULT_ERROR_MESSAGE;
    }

    public Response getResponse() {
        if (this.error instanceof IOException)
            return new Response(error.getMessage(), NETWORK_ERROR_MESSAGE, true);
        if (!(this.error instanceof HttpException))
            return new Response(error.getMessage(), error.getMessage(), true);
//        if (((HttpException) error).code() == HTTP_UNAUTHORIZED)
//            return new Response(error.getMessage(), UNAUTHORIZED, true);

        if (responseJson != null) return responseJson;

        retrofit2.Response<?> response = ((HttpException) this.error).response();

        if (response != null) {
            responseJson = parseResponse(response);
            if (responseJson != null) {
                return responseJson;
            }

            Map<String, List<String>> headers = response.headers().toMultimap();
            if (headers.containsKey(ERROR_MESSAGE_HEADER))
                return new Response(headers.get(ERROR_MESSAGE_HEADER).get(0), DEFAULT_ERROR_MESSAGE, true);
        }

        return new Response(NULL_ERROR_MESSAGE, DEFAULT_ERROR_MESSAGE, true);
    }

    private Response parseResponse(final retrofit2.Response<?> error) {
        String jsonString = "error_response";
        try {
            jsonString = error.errorBody().string();
            Response response = new Gson().fromJson(jsonString, Response.class);
            response.setLogMessage(jsonString);
            response.setErrorCode(((HttpException) this.error).code());
            response.setRawResponse(jsonString);
            return response;
        } catch (Exception e) {
            return new Response(jsonString, DEFAULT_ERROR_MESSAGE, true)
                    .setRawResponse(jsonString);
        }
    }

    public Throwable getError() {
        return error;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResponseNetworkError that = (ResponseNetworkError) o;

        return Objects.equals(error, that.error);

    }

    @Override
    public int hashCode() {
        return error != null ? error.hashCode() : 0;
    }
}
