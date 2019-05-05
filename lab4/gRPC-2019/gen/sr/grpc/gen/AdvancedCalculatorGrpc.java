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
public final class AdvancedCalculatorGrpc {

  private AdvancedCalculatorGrpc() {}

  public static final String SERVICE_NAME = "calculator.AdvancedCalculator";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<sr.grpc.gen.ComplexArithmeticOpArguments,
      sr.grpc.gen.ComplexArithmeticOpResult> METHOD_COMPLEX_OPERATION =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "calculator.AdvancedCalculator", "ComplexOperation"),
          io.grpc.protobuf.ProtoUtils.marshaller(sr.grpc.gen.ComplexArithmeticOpArguments.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(sr.grpc.gen.ComplexArithmeticOpResult.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AdvancedCalculatorStub newStub(io.grpc.Channel channel) {
    return new AdvancedCalculatorStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AdvancedCalculatorBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new AdvancedCalculatorBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static AdvancedCalculatorFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new AdvancedCalculatorFutureStub(channel);
  }

  /**
   */
  public static abstract class AdvancedCalculatorImplBase implements io.grpc.BindableService {

    /**
     */
    public void complexOperation(sr.grpc.gen.ComplexArithmeticOpArguments request,
        io.grpc.stub.StreamObserver<sr.grpc.gen.ComplexArithmeticOpResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_COMPLEX_OPERATION, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_COMPLEX_OPERATION,
            asyncUnaryCall(
              new MethodHandlers<
                sr.grpc.gen.ComplexArithmeticOpArguments,
                sr.grpc.gen.ComplexArithmeticOpResult>(
                  this, METHODID_COMPLEX_OPERATION)))
          .build();
    }
  }

  /**
   */
  public static final class AdvancedCalculatorStub extends io.grpc.stub.AbstractStub<AdvancedCalculatorStub> {
    private AdvancedCalculatorStub(io.grpc.Channel channel) {
      super(channel);
    }

    private AdvancedCalculatorStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AdvancedCalculatorStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new AdvancedCalculatorStub(channel, callOptions);
    }

    /**
     */
    public void complexOperation(sr.grpc.gen.ComplexArithmeticOpArguments request,
        io.grpc.stub.StreamObserver<sr.grpc.gen.ComplexArithmeticOpResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_COMPLEX_OPERATION, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class AdvancedCalculatorBlockingStub extends io.grpc.stub.AbstractStub<AdvancedCalculatorBlockingStub> {
    private AdvancedCalculatorBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private AdvancedCalculatorBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AdvancedCalculatorBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new AdvancedCalculatorBlockingStub(channel, callOptions);
    }

    /**
     */
    public sr.grpc.gen.ComplexArithmeticOpResult complexOperation(sr.grpc.gen.ComplexArithmeticOpArguments request) {
      return blockingUnaryCall(
          getChannel(), METHOD_COMPLEX_OPERATION, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class AdvancedCalculatorFutureStub extends io.grpc.stub.AbstractStub<AdvancedCalculatorFutureStub> {
    private AdvancedCalculatorFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private AdvancedCalculatorFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AdvancedCalculatorFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new AdvancedCalculatorFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<sr.grpc.gen.ComplexArithmeticOpResult> complexOperation(
        sr.grpc.gen.ComplexArithmeticOpArguments request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_COMPLEX_OPERATION, getCallOptions()), request);
    }
  }

  private static final int METHODID_COMPLEX_OPERATION = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AdvancedCalculatorImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(AdvancedCalculatorImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_COMPLEX_OPERATION:
          serviceImpl.complexOperation((sr.grpc.gen.ComplexArithmeticOpArguments) request,
              (io.grpc.stub.StreamObserver<sr.grpc.gen.ComplexArithmeticOpResult>) responseObserver);
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

  private static final class AdvancedCalculatorDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return sr.grpc.gen.CalculatorProto.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (AdvancedCalculatorGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AdvancedCalculatorDescriptorSupplier())
              .addMethod(METHOD_COMPLEX_OPERATION)
              .build();
        }
      }
    }
    return result;
  }
}
