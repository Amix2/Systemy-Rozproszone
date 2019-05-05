package sr.grpc.gen;

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
    comments = "Source: calculator.proto")
public final class StreamTesterGrpc {

  private StreamTesterGrpc() {}

  public static final String SERVICE_NAME = "calculator.StreamTester";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<sr.grpc.gen.Task,
      sr.grpc.gen.Number> METHOD_GENERATE_PRIME_NUMBERS =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING,
          generateFullMethodName(
              "calculator.StreamTester", "GeneratePrimeNumbers"),
          io.grpc.protobuf.ProtoUtils.marshaller(sr.grpc.gen.Task.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(sr.grpc.gen.Number.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<sr.grpc.gen.Number,
      sr.grpc.gen.Report> METHOD_COUNT_PRIME_NUMBERS =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING,
          generateFullMethodName(
              "calculator.StreamTester", "CountPrimeNumbers"),
          io.grpc.protobuf.ProtoUtils.marshaller(sr.grpc.gen.Number.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(sr.grpc.gen.Report.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static StreamTesterStub newStub(io.grpc.Channel channel) {
    return new StreamTesterStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static StreamTesterBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new StreamTesterBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static StreamTesterFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new StreamTesterFutureStub(channel);
  }

  /**
   */
  public static abstract class StreamTesterImplBase implements io.grpc.BindableService {

    /**
     */
    public void generatePrimeNumbers(sr.grpc.gen.Task request,
        io.grpc.stub.StreamObserver<sr.grpc.gen.Number> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GENERATE_PRIME_NUMBERS, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<sr.grpc.gen.Number> countPrimeNumbers(
        io.grpc.stub.StreamObserver<sr.grpc.gen.Report> responseObserver) {
      return asyncUnimplementedStreamingCall(METHOD_COUNT_PRIME_NUMBERS, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_GENERATE_PRIME_NUMBERS,
            asyncServerStreamingCall(
              new MethodHandlers<
                sr.grpc.gen.Task,
                sr.grpc.gen.Number>(
                  this, METHODID_GENERATE_PRIME_NUMBERS)))
          .addMethod(
            METHOD_COUNT_PRIME_NUMBERS,
            asyncClientStreamingCall(
              new MethodHandlers<
                sr.grpc.gen.Number,
                sr.grpc.gen.Report>(
                  this, METHODID_COUNT_PRIME_NUMBERS)))
          .build();
    }
  }

  /**
   */
  public static final class StreamTesterStub extends io.grpc.stub.AbstractStub<StreamTesterStub> {
    private StreamTesterStub(io.grpc.Channel channel) {
      super(channel);
    }

    private StreamTesterStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StreamTesterStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new StreamTesterStub(channel, callOptions);
    }

    /**
     */
    public void generatePrimeNumbers(sr.grpc.gen.Task request,
        io.grpc.stub.StreamObserver<sr.grpc.gen.Number> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(METHOD_GENERATE_PRIME_NUMBERS, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<sr.grpc.gen.Number> countPrimeNumbers(
        io.grpc.stub.StreamObserver<sr.grpc.gen.Report> responseObserver) {
      return asyncClientStreamingCall(
          getChannel().newCall(METHOD_COUNT_PRIME_NUMBERS, getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class StreamTesterBlockingStub extends io.grpc.stub.AbstractStub<StreamTesterBlockingStub> {
    private StreamTesterBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private StreamTesterBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StreamTesterBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new StreamTesterBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<sr.grpc.gen.Number> generatePrimeNumbers(
        sr.grpc.gen.Task request) {
      return blockingServerStreamingCall(
          getChannel(), METHOD_GENERATE_PRIME_NUMBERS, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class StreamTesterFutureStub extends io.grpc.stub.AbstractStub<StreamTesterFutureStub> {
    private StreamTesterFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private StreamTesterFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StreamTesterFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new StreamTesterFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_GENERATE_PRIME_NUMBERS = 0;
  private static final int METHODID_COUNT_PRIME_NUMBERS = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final StreamTesterImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(StreamTesterImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GENERATE_PRIME_NUMBERS:
          serviceImpl.generatePrimeNumbers((sr.grpc.gen.Task) request,
              (io.grpc.stub.StreamObserver<sr.grpc.gen.Number>) responseObserver);
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
        case METHODID_COUNT_PRIME_NUMBERS:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.countPrimeNumbers(
              (io.grpc.stub.StreamObserver<sr.grpc.gen.Report>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static final class StreamTesterDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return sr.grpc.gen.CalculatorProto.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (StreamTesterGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new StreamTesterDescriptorSupplier())
              .addMethod(METHOD_GENERATE_PRIME_NUMBERS)
              .addMethod(METHOD_COUNT_PRIME_NUMBERS)
              .build();
        }
      }
    }
    return result;
  }
}
