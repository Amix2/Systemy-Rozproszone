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

public class DualCurrency implements java.lang.Cloneable,
                                     java.io.Serializable
{
    public Currency globalCurrency;

    public int globalCurrencyValue;

    public int localCurrencyValue;

    public DualCurrency()
    {
        this.globalCurrency = Currency.EUR;
    }

    public DualCurrency(Currency globalCurrency, int globalCurrencyValue, int localCurrencyValue)
    {
        this.globalCurrency = globalCurrency;
        this.globalCurrencyValue = globalCurrencyValue;
        this.localCurrencyValue = localCurrencyValue;
    }

    public boolean equals(java.lang.Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        DualCurrency r = null;
        if(rhs instanceof DualCurrency)
        {
            r = (DualCurrency)rhs;
        }

        if(r != null)
        {
            if(this.globalCurrency != r.globalCurrency)
            {
                if(this.globalCurrency == null || r.globalCurrency == null || !this.globalCurrency.equals(r.globalCurrency))
                {
                    return false;
                }
            }
            if(this.globalCurrencyValue != r.globalCurrencyValue)
            {
                return false;
            }
            if(this.localCurrencyValue != r.localCurrencyValue)
            {
                return false;
            }

            return true;
        }

        return false;
    }

    public int hashCode()
    {
        int h_ = 5381;
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, "::Banks::DualCurrency");
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, globalCurrency);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, globalCurrencyValue);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, localCurrencyValue);
        return h_;
    }

    public DualCurrency clone()
    {
        DualCurrency c = null;
        try
        {
            c = (DualCurrency)super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            assert false; // impossible
        }
        return c;
    }

    public void ice_writeMembers(com.zeroc.Ice.OutputStream ostr)
    {
        Currency.ice_write(ostr, this.globalCurrency);
        ostr.writeInt(this.globalCurrencyValue);
        ostr.writeInt(this.localCurrencyValue);
    }

    public void ice_readMembers(com.zeroc.Ice.InputStream istr)
    {
        this.globalCurrency = Currency.ice_read(istr);
        this.globalCurrencyValue = istr.readInt();
        this.localCurrencyValue = istr.readInt();
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, DualCurrency v)
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

    static public DualCurrency ice_read(com.zeroc.Ice.InputStream istr)
    {
        DualCurrency v = new DualCurrency();
        v.ice_readMembers(istr);
        return v;
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<DualCurrency> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, DualCurrency v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            int pos = ostr.startSize();
            ice_write(ostr, v);
            ostr.endSize(pos);
        }
    }

    static public java.util.Optional<DualCurrency> ice_read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            istr.skip(4);
            return java.util.Optional.of(DualCurrency.ice_read(istr));
        }
        else
        {
            return java.util.Optional.empty();
        }
    }

    private static final DualCurrency _nullMarshalValue = new DualCurrency();

    /** @hidden */
    public static final long serialVersionUID = -810000312L;
}
