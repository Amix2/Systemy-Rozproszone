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

public class NewAccountDetails implements java.lang.Cloneable,
                                          java.io.Serializable
{
    public AccountType accountType;

    public String key;

    public NewAccountDetails()
    {
        this.accountType = AccountType.STANDARD;
        this.key = "";
    }

    public NewAccountDetails(AccountType accountType, String key)
    {
        this.accountType = accountType;
        this.key = key;
    }

    public boolean equals(java.lang.Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        NewAccountDetails r = null;
        if(rhs instanceof NewAccountDetails)
        {
            r = (NewAccountDetails)rhs;
        }

        if(r != null)
        {
            if(this.accountType != r.accountType)
            {
                if(this.accountType == null || r.accountType == null || !this.accountType.equals(r.accountType))
                {
                    return false;
                }
            }
            if(this.key != r.key)
            {
                if(this.key == null || r.key == null || !this.key.equals(r.key))
                {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    public int hashCode()
    {
        int h_ = 5381;
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, "::Banks::NewAccountDetails");
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, accountType);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, key);
        return h_;
    }

    public NewAccountDetails clone()
    {
        NewAccountDetails c = null;
        try
        {
            c = (NewAccountDetails)super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            assert false; // impossible
        }
        return c;
    }

    public void ice_writeMembers(com.zeroc.Ice.OutputStream ostr)
    {
        AccountType.ice_write(ostr, this.accountType);
        ostr.writeString(this.key);
    }

    public void ice_readMembers(com.zeroc.Ice.InputStream istr)
    {
        this.accountType = AccountType.ice_read(istr);
        this.key = istr.readString();
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, NewAccountDetails v)
    {
        if(v == null)
        {
            _nullMarshalValue.ice_writeMembers(ostr);
        }
        else
        {
            v.ice_writeMembers(ostr);
        }
    }

    static public NewAccountDetails ice_read(com.zeroc.Ice.InputStream istr)
    {
        NewAccountDetails v = new NewAccountDetails();
        v.ice_readMembers(istr);
        return v;
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<NewAccountDetails> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, NewAccountDetails v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            int pos = ostr.startSize();
            ice_write(ostr, v);
            ostr.endSize(pos);
        }
    }

    static public java.util.Optional<NewAccountDetails> ice_read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            istr.skip(4);
            return java.util.Optional.of(NewAccountDetails.ice_read(istr));
        }
        else
        {
            return java.util.Optional.empty();
        }
    }

    private static final NewAccountDetails _nullMarshalValue = new NewAccountDetails();

    /** @hidden */
    public static final long serialVersionUID = 540257103L;
}
