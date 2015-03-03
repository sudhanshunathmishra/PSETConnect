package com.example.sudhanshu.psetconnect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sudhanshu on 3/1/15.
 */
public class MatchFinder {

    private List <String> findAMatchForSizeTwo (List<RequestForGroup> listOfRequests) throws MatchNotFoundException{

    List<String> matchFormed = new ArrayList<String>();


            for (RequestForGroup request:listOfRequests){
                if(request.getSizeOfGroup() == GroupSizes.TWO && !matchFormed.contains(request.getUserId()) && matchFormed.size()<2){
                    matchFormed.add(request.getUserId());

                }
            }
        if (matchFormed.size() == 2){
            for (String everyUserIdFound: matchFormed){
            listOfRequests.remove(everyUserIdFound);

        }
            return matchFormed;
        }
        else {
            throw new MatchNotFoundException();
        }
    }

    private List <String> findAMatchForSizeFive (List<RequestForGroup> listOfRequests) throws MatchNotFoundException{

        List<String> matchFormed = new ArrayList<String>();


        for (RequestForGroup request:listOfRequests){
            if(request.getSizeOfGroup() == GroupSizes.FIVE && !matchFormed.contains(request.getUserId()) && matchFormed.size()<5){
                matchFormed.add(request.getUserId());

            }
        }
        if (matchFormed.size() == 5){
            for (String everyUserIdFound: matchFormed){
                listOfRequests.remove(everyUserIdFound);

            }
            return matchFormed;
        }
        else {
            throw new MatchNotFoundException();
        }
    }

}
