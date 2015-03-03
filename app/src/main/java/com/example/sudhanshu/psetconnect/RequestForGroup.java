package com.example.sudhanshu.psetconnect;

/**
 * Created by sudhanshu on 3/1/15.
 */
public class RequestForGroup {

    private String classNumber;
    private GroupSizes sizeOfGroup;
    private String userId;

    public RequestForGroup(String classNumber, GroupSizes sizeOfGroup, String userId){
        this.classNumber = classNumber;
        this.sizeOfGroup = sizeOfGroup;
        this.userId = userId;
    }

    public String getClassNumber () {

        return this.classNumber;
    }
    public GroupSizes getSizeOfGroup () {

        return this.sizeOfGroup;
    }
    public String getUserId () {

        return this.userId;
    }


}
