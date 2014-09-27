package com.humblecoder.pyp.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by User on 23-Sep-14.
 */
@ParseClassName("User")
public class _User extends ParseObject{

    public _User() {}

    public static String getParseClassName() {
        return "User";
    }
}
