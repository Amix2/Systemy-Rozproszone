//
// Copyright (c) ZeroC, Inc. All rights reserved.
//
//
// Ice version 3.7.2
//
// <auto-generated>
//
// Generated from file `bank.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package Banks;

public class ErrorNewUser extends com.zeroc.Ice.UserException
{
    public ErrorNewUser()
    {
    }

    public ErrorNewUser(Throwable cause)
    {
        super(cause);
    }

    public String ice_id()
    {
        return "::Banks::ErrorNewUser";
    }

    /** @hidden */
    @Override
    protected void _writeImpl(com.zeroc.Ice.OutputStream ostr_)
    {
        ostr_.startSlice("::Banks::ErrorNewUser", -1, true);
        ostr_.endSlice();
    }

    /** @hidden */
    @Override
    protected void _readImpl(com.zeroc.Ice.InputStream istr_)
    {
        istr_.startSlice();
        istr_.endSlice();
    }

    /** @hidden */
    public static final long serialVersionUID = -703724409L;
}