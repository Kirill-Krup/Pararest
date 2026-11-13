package com.actisys.grpc.payment;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.68.1)",
    comments = "Source: payment.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class PaymentStatsGrpc {

  private PaymentStatsGrpc() {}

  public static final java.lang.String SERVICE_NAME = "com.actisys.grpc.payment.PaymentStats";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.actisys.grpc.payment.RevenueStatsRequest,
      com.actisys.grpc.payment.RevenueStatsResponse> getGetRevenueStatsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetRevenueStats",
      requestType = com.actisys.grpc.payment.RevenueStatsRequest.class,
      responseType = com.actisys.grpc.payment.RevenueStatsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.actisys.grpc.payment.RevenueStatsRequest,
      com.actisys.grpc.payment.RevenueStatsResponse> getGetRevenueStatsMethod() {
    io.grpc.MethodDescriptor<com.actisys.grpc.payment.RevenueStatsRequest, com.actisys.grpc.payment.RevenueStatsResponse> getGetRevenueStatsMethod;
    if ((getGetRevenueStatsMethod = PaymentStatsGrpc.getGetRevenueStatsMethod) == null) {
      synchronized (PaymentStatsGrpc.class) {
        if ((getGetRevenueStatsMethod = PaymentStatsGrpc.getGetRevenueStatsMethod) == null) {
          PaymentStatsGrpc.getGetRevenueStatsMethod = getGetRevenueStatsMethod =
              io.grpc.MethodDescriptor.<com.actisys.grpc.payment.RevenueStatsRequest, com.actisys.grpc.payment.RevenueStatsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetRevenueStats"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.actisys.grpc.payment.RevenueStatsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.actisys.grpc.payment.RevenueStatsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PaymentStatsMethodDescriptorSupplier("GetRevenueStats"))
              .build();
        }
      }
    }
    return getGetRevenueStatsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PaymentStatsStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PaymentStatsStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PaymentStatsStub>() {
        @java.lang.Override
        public PaymentStatsStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PaymentStatsStub(channel, callOptions);
        }
      };
    return PaymentStatsStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PaymentStatsBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PaymentStatsBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PaymentStatsBlockingStub>() {
        @java.lang.Override
        public PaymentStatsBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PaymentStatsBlockingStub(channel, callOptions);
        }
      };
    return PaymentStatsBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PaymentStatsFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PaymentStatsFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PaymentStatsFutureStub>() {
        @java.lang.Override
        public PaymentStatsFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PaymentStatsFutureStub(channel, callOptions);
        }
      };
    return PaymentStatsFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void getRevenueStats(com.actisys.grpc.payment.RevenueStatsRequest request,
        io.grpc.stub.StreamObserver<com.actisys.grpc.payment.RevenueStatsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetRevenueStatsMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service PaymentStats.
   */
  public static abstract class PaymentStatsImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return PaymentStatsGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service PaymentStats.
   */
  public static final class PaymentStatsStub
      extends io.grpc.stub.AbstractAsyncStub<PaymentStatsStub> {
    private PaymentStatsStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PaymentStatsStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PaymentStatsStub(channel, callOptions);
    }

    /**
     */
    public void getRevenueStats(com.actisys.grpc.payment.RevenueStatsRequest request,
        io.grpc.stub.StreamObserver<com.actisys.grpc.payment.RevenueStatsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetRevenueStatsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service PaymentStats.
   */
  public static final class PaymentStatsBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<PaymentStatsBlockingStub> {
    private PaymentStatsBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PaymentStatsBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PaymentStatsBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.actisys.grpc.payment.RevenueStatsResponse getRevenueStats(com.actisys.grpc.payment.RevenueStatsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetRevenueStatsMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service PaymentStats.
   */
  public static final class PaymentStatsFutureStub
      extends io.grpc.stub.AbstractFutureStub<PaymentStatsFutureStub> {
    private PaymentStatsFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PaymentStatsFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PaymentStatsFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.actisys.grpc.payment.RevenueStatsResponse> getRevenueStats(
        com.actisys.grpc.payment.RevenueStatsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetRevenueStatsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_REVENUE_STATS = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_REVENUE_STATS:
          serviceImpl.getRevenueStats((com.actisys.grpc.payment.RevenueStatsRequest) request,
              (io.grpc.stub.StreamObserver<com.actisys.grpc.payment.RevenueStatsResponse>) responseObserver);
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

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getGetRevenueStatsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.actisys.grpc.payment.RevenueStatsRequest,
              com.actisys.grpc.payment.RevenueStatsResponse>(
                service, METHODID_GET_REVENUE_STATS)))
        .build();
  }

  private static abstract class PaymentStatsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PaymentStatsBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.actisys.grpc.payment.PaymentStatsProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PaymentStats");
    }
  }

  private static final class PaymentStatsFileDescriptorSupplier
      extends PaymentStatsBaseDescriptorSupplier {
    PaymentStatsFileDescriptorSupplier() {}
  }

  private static final class PaymentStatsMethodDescriptorSupplier
      extends PaymentStatsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    PaymentStatsMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (PaymentStatsGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PaymentStatsFileDescriptorSupplier())
              .addMethod(getGetRevenueStatsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
