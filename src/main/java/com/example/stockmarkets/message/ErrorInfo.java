package com.example.stockmarkets.message;

public class ErrorInfo {
    private String errCode;
    private String errDesc;

    public ErrorInfo(String errCode, String errDesc) {
        this.errCode = errCode;
        this.errDesc = errDesc;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrCode() {
        return this.errCode;
    }

    public void setErrDesc(String errDesc) {
        this.errDesc = errDesc;
    }

    public String getErrDesc() {
        return this.errDesc;
    }
}
