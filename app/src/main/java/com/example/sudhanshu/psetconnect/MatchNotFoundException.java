package com.example.sudhanshu.psetconnect;

/**
 * Created by sudhanshu on 3/2/15.
 */
public class MatchNotFoundException extends Exception {


        public MatchNotFoundException() {}



        //Constructor that accepts a message
        public MatchNotFoundException(String message){

            super(message);
        }

}
