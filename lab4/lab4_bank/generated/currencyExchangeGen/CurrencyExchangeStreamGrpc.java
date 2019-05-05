package currencyExchangeGen;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.2.0)",
    comments = "Source: bank.proto")
public final class CurrencyExchangeStreamGrpc {

  private CurrencyExchangeStreamGrpc() {}

  public static final String SERVICE_NAME = "currencyExchange.CurrencyExchangeStream";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<currencyExchangeGen.CurrencyList,
      currencyExchangeGen.CurrencyMap> METHOD_ORDER_CURRENCY_EXCHANGE =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING,
          generateFullMethodName(
              "currencyExchange.CurrencyExchangeStream", "orderCurrencyExchange"),
          io.grpc.protobuf.ProtoUtils.marshaller(currencyExchangeGen.CurrencyList.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(currencyExchangeGen.CurrencyMap.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CurrencyExchangeStreamStub newStub(io.grpc.Channel channel) {
    return new CurrencyExchangeStreamStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CurrencyExchangeStreamBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new CurrencyExchangeStreamBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static CurrencyExchangeStreamFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new CurrencyExchangeStreamFutureStub(channel);
  }

  /**
   */
  public static abstract class CurrencyExchangeStreamImplBase implements io.grpc.BindableService {

    /**
     */
    public void orderCurrencyExchange(currencyExchangeGen.CurrencyList request,
        io.grpc.stub.StreamObserver<currencyExchangeGen.CurrencyMap> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_ORDER_CURRENCY_EXCHANGE, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_ORDER_CURRENCY_EXCHANGE,
            asyncServerStreamingCall(
              new MethodHandlers<
                currencyExchangeGen.CurrencyList,
                currencyExchangeGen.CurrencyMap>(
                  this, METHODID_ORDER_CURRENCY_EXCHANGE)))
          .build();
    }
  }

  /**
   */
  public static final class CurrencyExchangeStreamStub extends io.grpc.stub.AbstractStub<CurrencyExchangeStreamStub> {
    private CurrencyExchangeStreamStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CurrencyExchangeStreamStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CurrencyExchangeStreamStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CurrencyExchangeStreamStub(channel, callOptions);
    }

    /**
     */
    public void orderCurrencyExchange(currencyExchangeGen.CurrencyList request,
        io.grpc.stub.StreamObserver<currencyExchangeGen.CurrencyMap> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(METHOD_ORDER_CURRENCY_EXCHANGE, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class CurrencyExchangeStreamBlockingStub extends io.grpc.stub.AbstractStub<CurrencyExchangeStreamBlockingStub> {
    private CurrencyExchangeStreamBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CurrencyExchangeStreamBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CurrencyExchangeStreamBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CurrencyExchangeStreamBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<currencyExchangeGen.CurrencyMap> orderCurrencyExchange(
        currencyExchangeGen.CurrencyList request) {
      return blockingServerStreamingCall(
          getChannel(), METHOD_ORDER_CURRENCY_EXCHANGE, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class CurrencyExchangeStreamFutureStub extends io.grpc.stub.AbstractStub<CurrencyExchangeStreamFutureStub> {
    private CurrencyExchangeStreamFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CurrencyExchangeStreamFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CurrencyExchangeStreamFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CurrencyExchangeStreamFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_ORDER_CURRENCY_EXCHANGE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final CurrencyExchangeStreamImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(CurrencyExchangeStreamImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ORDER_CURRENCY_EXCHANGE:
          serviceImpl.orderCurrencyExchange((currencyExchangeGen.CurrencyList) request,
              (io.grpc.stub.StreamObserver<currencyExchangeGen.CurrencyMap>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static final class CurrencyExchangeStreamDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return currencyExchangeGen.CurrencyExchangeProto.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (CurrencyExchangeStreamGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CurrencyExchangeStreamDescriptorSupplier())
              .addMethod(METHOD_ORDER_CURRENCY_EXCHANGE)
              .build();
        }
      }
    }
    return result;
  }
}
