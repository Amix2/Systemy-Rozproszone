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

public enum Currency implements java.io.Serializable, com.google.protobuf.ProtocolMessageEnum
{
	 /**
	   * <code>EUR = 0;</code>
	   */
	  EUR(0),
	  /**
	   * <code>USD = 1;</code>
	   */
	  USD(1),
	  /**
	   * <code>CHF = 2;</code>
	   */
	  CHF(2),
	  /**
	   * <code>GBP = 3;</code>
	   */
	  GBP(3),
	  /**
	   * <code>JPY = 4;</code>
	   */
	  JPY(4),
	  /**
	   * <code>PLN = 5;</code>
	   */
	  PLN(5),
	  UNRECOGNIZED(-1),
	  ;

	  /**
	   * <code>EUR = 0;</code>
	   */
	  public static final int EUR_VALUE = 0;
	  /**
	   * <code>USD = 1;</code>
	   */
	  public static final int USD_VALUE = 1;
	  /**
	   * <code>CHF = 2;</code>
	   */
	  public static final int CHF_VALUE = 2;
	  /**
	   * <code>GBP = 3;</code>
	   */
	  public static final int GBP_VALUE = 3;
	  /**
	   * <code>JPY = 4;</code>
	   */
	  public static final int JPY_VALUE = 4;
	  /**
	   * <code>PLN = 5;</code>
	   */
	  public static final int PLN_VALUE = 5;


	  public final int getNumber() {
	    if (this == UNRECOGNIZED) {
	      throw new java.lang.IllegalArgumentException(
	          "Can't get the number of an unknown enum value.");
	    }
	    return _value;
	  }

	  /**
	   * @deprecated Use {@link #forNumber(int)} instead.
	   */
	  @java.lang.Deprecated
	  public static Currency valueOf(int value) {
	    return forNumber(value);
	  }

	  public static Currency forNumber(int value) {
	    switch (value) {
	      case 0: return EUR;
	      case 1: return USD;
	      case 2: return CHF;
	      case 3: return GBP;
	      case 4: return JPY;
	      case 5: return PLN;
	      default: return null;
	    }
	  }

	  public static com.google.protobuf.Internal.EnumLiteMap<Currency>
	      internalGetValueMap() {
	    return internalValueMap;
	  }
	  private static final com.google.protobuf.Internal.EnumLiteMap<
	      Currency> internalValueMap =
	        new com.google.protobuf.Internal.EnumLiteMap<Currency>() {
	          public Currency findValueByNumber(int number) {
	            return Currency.forNumber(number);
	          }
	        };

	  public final com.google.protobuf.Descriptors.EnumValueDescriptor
	      getValueDescriptor() {
	    return getDescriptor().getValues().get(ordinal());
	  }
	  public final com.google.protobuf.Descriptors.EnumDescriptor
	      getDescriptorForType() {
	    return getDescriptor();
	  }
	  public static final com.google.protobuf.Descriptors.EnumDescriptor
	      getDescriptor() {
	    return currencyExchangeGen.CurrencyExchangeProto.getDescriptor().getEnumTypes().get(0);
	  }

	  private static final Currency[] VALUES = values();

	  public static Currency valueOf(
	      com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
	    if (desc.getType() != getDescriptor()) {
	      throw new java.lang.IllegalArgumentException(
	        "EnumValueDescriptor is not for this type.");
	    }
	    if (desc.getIndex() == -1) {
	      return UNRECOGNIZED;
	    }
	    return VALUES[desc.getIndex()];
	  }


    public int value()
    {
        return _value;
    }

    private Currency(int v)
    {
        _value = v;
    }

    public void ice_write(com.zeroc.Ice.OutputStream ostr)
    {
        ostr.writeEnum(_value, 5);
    }

    public static void ice_write(com.zeroc.Ice.OutputStream ostr, Currency v)
    {
        if(v == null)
        {
            ostr.writeEnum(Banks.Currency.EUR.value(), 5);
        }
        else
        {
            ostr.writeEnum(v.value(), 5);
        }
    }

    public static Currency ice_read(com.zeroc.Ice.InputStream istr)
    {
        int v = istr.readEnum(5);
        return validate(v);
    }

    public static void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<Currency> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    public static void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, Currency v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.Size))
        {
            ice_write(ostr, v);
        }
    }

    public static java.util.Optional<Currency> ice_read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.Size))
        {
            return java.util.Optional.of(ice_read(istr));
        }
        else
        {
            return java.util.Optional.empty();
        }
    }

    private static Currency validate(int v)
    {
        final Currency e = valueOf(v);
        if(e == null)
        {
            throw new com.zeroc.Ice.MarshalException("enumerator value " + v + " is out of range");
        }
        return e;
    }

    private final int _value;
}
