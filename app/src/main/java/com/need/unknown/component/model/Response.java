package com.need.unknown.component.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {
    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("responseText")
    private String responseText;

    @SerializedName("logMessage")
    private String logMessage;
    private String rawResponse;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;

    public boolean isIs_registered() {
        return is_registered;
    }

    public void setIs_registered(boolean is_registered) {
        this.is_registered = is_registered;
    }

    private boolean is_registered;
    private List<DataBean> data;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    int errorCode;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getIs_new() {
        return is_new;
    }

    public void setIs_new(String is_new) {
        this.is_new = is_new;
    }

    private String value;
    private String is_new;

    public Response(String toLog, String string, boolean b, int errorCode) {
        this.errorCode = errorCode;
        this.logMessage = toLog;
        this.message = string;
        this.error = b;
    }

    public Response(String toLog, String string, boolean b) {
        this.errorCode = 0;
        this.logMessage = toLog;
        this.message = string;
        this.error = b;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public Response setRawResponse(String rawResponse) {
        this.rawResponse = rawResponse;
        return this;
    }

    public String getRawResponse() {
        return rawResponse;
    }

    public static class DataBean {
        /**
         * id : 24
         * image_url : https://bisatopup.sgp1.digitaloceanspaces.com/user_data/banner/5d2c593d32c18.jpeg
         * company_id : 1
         * url : https://bisatopup.sgp1.digitaloceanspaces.com/user_data/banner/5d2c593d32c18.jpeg
         * title : BisaQurban Bersama Bisatopup
         * body : <p>Mari tunaikan Qurban anda bersama Bisatopup</p>
         * decription : null
         * order_by : 1
         * is_active : 1
         * version : 2
         * menu : 1
         */

        private int id;
        private String image_url;
        private int company_id;
        private String url;
        private String title;
        private String body;
        private String decription;
        private int order_by;
        private int is_active;
        private int version;
        private int menu;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public int getCompany_id() {
            return company_id;
        }

        public void setCompany_id(int company_id) {
            this.company_id = company_id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getDecription() {
            return decription;
        }

        public void setDecription(String decription) {
            this.decription = decription;
        }

        public int getOrder_by() {
            return order_by;
        }

        public void setOrder_by(int order_by) {
            this.order_by = order_by;
        }

        public boolean getIs_active() {
            return is_active == 1;
        }

        public void setIs_active(int is_active) {
            this.is_active = is_active;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public int getMenu() {
            return menu;
        }

        public void setMenu(int menu) {
            this.menu = menu;
        }
    }
}
