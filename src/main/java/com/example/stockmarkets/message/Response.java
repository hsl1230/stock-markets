package com.example.stockmarkets.message;

import com.example.stockmarkets.document.DowJonesIndex;

import java.util.ArrayList;
import java.util.List;

public class Response {
    private List<Message> messages = null;
    private List<DowJonesIndex> dowJonesIndices = new ArrayList<>();

    public Response() {
        this.messages = new ArrayList<>();
    }

    public Response(String message, String url, List<DowJonesIndex> dowJonesIndices) {
        this();
        this.addMessage(new Message(url, message, "succeed"));
        this.dowJonesIndices = dowJonesIndices;
    }

    public Response(String message, String url, ErrorInfo error) {
        this();
        this.addMessage(new Message(url, message + "\n" + error.getErrDesc(), error.getErrCode()));
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<Message> getMessages() {
        return this.messages;
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    /**
     * @return List<DowJonesIndex> return the dowJonesIndices
     */
    public List<DowJonesIndex> getDowJonesIndices() {
        return dowJonesIndices;
    }

    /**
     * @param dowJonesIndices the dowJonesIndices to set
     */
    public void setDowJonesIndices(List<DowJonesIndex> dowJonesIndices) {
        this.dowJonesIndices = dowJonesIndices;
    }

}