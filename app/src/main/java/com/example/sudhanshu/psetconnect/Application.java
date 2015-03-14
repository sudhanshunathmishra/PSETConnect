package com.example.sudhanshu.psetconnect;

/**
 * Created by sudhanshu on 3/4/15.
 */

    import com.parse.Parse;
    import com.parse.ParseObject;
    import com.parse.ParseUser;

    public class Application extends android.app.Application {

        public void onCreate() {
            Parse.enableLocalDatastore(this);

            Parse.initialize(this, "LUlLqUhNCfFo1MdBYJ6qHkIQKUGrsdMarZrqaT8l", "4EBHWdRqwmpelaBHHC053aNlWCPLHq1jHnAsIFN7");

        }

    }

