package com.example.darthvader.chatapp;

import android.media.MediaMetadata;

public class Message {
    String text;
    MemberData data;
    boolean ifBelongstoUser;

    public Message(String text, MemberData data, boolean ifBelongstoUser) {
        this.text = text;
        this.data = data;
        this.ifBelongstoUser = ifBelongstoUser;
    }

    public String getText() {
        return text;
    }

    public MemberData getData() {
        return data;
    }

    public boolean isIfBelongstoUser() {
        return ifBelongstoUser;
    }
}
