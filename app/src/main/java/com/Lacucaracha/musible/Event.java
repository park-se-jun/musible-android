package com.lacucaracha.musible;

public class Event<T> {
    private T mContent;

    private boolean hasBeenHandled = false;

    public Event(T content){
        if(content == null){
            throw new IllegalArgumentException(("null value in Event are not allowed"));
        }
        mContent=content;
    }
    public T getContentIfnotHandled(){
        if(hasBeenHandled){
            return null;
        }else{
            hasBeenHandled = true;
            return mContent;
        }

    }
    public boolean hasBeenHandled(){  return hasBeenHandled;  }
}
