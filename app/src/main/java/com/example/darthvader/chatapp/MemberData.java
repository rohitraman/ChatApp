package com.example.darthvader.chatapp;

public class MemberData {
    String memberName,memberColor;

    public MemberData(String memberName, String memberColor) {
        this.memberName = memberName;
        this.memberColor = memberColor;
    }

    public String getMemberName() {
        return memberName;
    }


    public String getMemberColor() {
        return memberColor;
    }

    public MemberData(){

    }
    @Override
    public String toString() {
        return "MemberData{" +
                "name='" + memberName + '\'' +
                ", color='" + memberColor + '\'' +
                '}';
    }
}
