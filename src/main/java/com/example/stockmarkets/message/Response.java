package com.example.stockmarkets.message;

import com.example.stockmarkets.document.DowJonesIndex;

import java.util.ArrayList;
import java.util.List;

public class Response {
    private List<Message> messages = null;
    private List<FileInfo> fileInfos = null;
    private String message = null;
    private List<DowJonesIndex> dowJonesIndices = new ArrayList<DowJonesIndex>();
    private String url;

    private ErrorInfo error = null;
    private String errStatus = "";

    public Response() {
        this.messages = new ArrayList<Message>();
    }

    public Response(List<FileInfo> fileInfos){
        this();
        this.fileInfos = fileInfos;
    }

    public Response(String errStatus, ErrorInfo err) {
        this();
        this.errStatus = errStatus;
        this.error = err;
    }

    public Response(String message, String url, List<DowJonesIndex> dowJonesIndices) {
        this();
        this.message = message;
        this.url = url;
        this.dowJonesIndices = dowJonesIndices;
    }

    public Response(String message, String url, ErrorInfo error) {
        this();
        this.message = message;
        this.url = url;
        this.error = error;
    }

    public void addFileInfo(FileInfo file) {
        this.fileInfos.add(file);
    }

    public List<FileInfo> getFileInfos(){
        return this.fileInfos;
    }

    public void setMessages(List<Message> messages) {
        this.messages =  messages;
    }

    public List<Message> getMessages() {
        return this.messages;
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    public void setErrStatus(String status) {
        this.errStatus = status;
    }

    public String getErrStatus() {
        return this.errStatus;
    }

    public void setError(ErrorInfo error) {
        this.error = error;
    }

    public ErrorInfo getError() {
        return this.error;
    }
}