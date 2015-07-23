package com.augylabs.volleyloader;

/**
 * Created by adarshpandey on 7/23/15.
 */
public class Response<Result> {
    private Exception mException;

    private Result mResult;

    static <D> Response<D> ok(D data){

        Response<D> response = new Response<D>();
        response.mResult = data;

        return  response;
    }

    static <D> Response<D> error(Exception ex){

        Response<D> response = new Response<D>();
        response.mException = ex;

        return  response;
    }

    public boolean hasError() {

        return mException != null;
    }

    public Exception getException() {

        return mException;
    }

    public Result getResult() {

        return mResult;
    }
}
