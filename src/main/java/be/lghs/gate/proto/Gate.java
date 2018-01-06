// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: gate.proto

package be.lghs.gate.proto;

public final class Gate {
  private Gate() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface GateOpenRequestOrBuilder extends
      // @@protoc_insertion_point(interface_extends:be.lghs.gate.proto.GateOpenRequest)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>uint64 cardId = 1;</code>
     */
    long getCardId();

    /**
     * <code>uint64 tokenLow = 2;</code>
     */
    long getTokenLow();

    /**
     * <code>uint64 tokenHigh = 3;</code>
     */
    long getTokenHigh();

    /**
     * <code>bytes pin = 4;</code>
     */
    com.google.protobuf.ByteString getPin();
  }
  /**
   * Protobuf type {@code be.lghs.gate.proto.GateOpenRequest}
   */
  public  static final class GateOpenRequest extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:be.lghs.gate.proto.GateOpenRequest)
      GateOpenRequestOrBuilder {
    // Use GateOpenRequest.newBuilder() to construct.
    private GateOpenRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private GateOpenRequest() {
      cardId_ = 0L;
      tokenLow_ = 0L;
      tokenHigh_ = 0L;
      pin_ = com.google.protobuf.ByteString.EMPTY;
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
    }
    private GateOpenRequest(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      int mutable_bitField0_ = 0;
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!input.skipField(tag)) {
                done = true;
              }
              break;
            }
            case 8: {

              cardId_ = input.readUInt64();
              break;
            }
            case 16: {

              tokenLow_ = input.readUInt64();
              break;
            }
            case 24: {

              tokenHigh_ = input.readUInt64();
              break;
            }
            case 34: {

              pin_ = input.readBytes();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return be.lghs.gate.proto.Gate.internal_static_be_lghs_gate_proto_GateOpenRequest_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return be.lghs.gate.proto.Gate.internal_static_be_lghs_gate_proto_GateOpenRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              be.lghs.gate.proto.Gate.GateOpenRequest.class, be.lghs.gate.proto.Gate.GateOpenRequest.Builder.class);
    }

    public static final int CARDID_FIELD_NUMBER = 1;
    private long cardId_;
    /**
     * <code>uint64 cardId = 1;</code>
     */
    public long getCardId() {
      return cardId_;
    }

    public static final int TOKENLOW_FIELD_NUMBER = 2;
    private long tokenLow_;
    /**
     * <code>uint64 tokenLow = 2;</code>
     */
    public long getTokenLow() {
      return tokenLow_;
    }

    public static final int TOKENHIGH_FIELD_NUMBER = 3;
    private long tokenHigh_;
    /**
     * <code>uint64 tokenHigh = 3;</code>
     */
    public long getTokenHigh() {
      return tokenHigh_;
    }

    public static final int PIN_FIELD_NUMBER = 4;
    private com.google.protobuf.ByteString pin_;
    /**
     * <code>bytes pin = 4;</code>
     */
    public com.google.protobuf.ByteString getPin() {
      return pin_;
    }

    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (cardId_ != 0L) {
        output.writeUInt64(1, cardId_);
      }
      if (tokenLow_ != 0L) {
        output.writeUInt64(2, tokenLow_);
      }
      if (tokenHigh_ != 0L) {
        output.writeUInt64(3, tokenHigh_);
      }
      if (!pin_.isEmpty()) {
        output.writeBytes(4, pin_);
      }
    }

    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (cardId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt64Size(1, cardId_);
      }
      if (tokenLow_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt64Size(2, tokenLow_);
      }
      if (tokenHigh_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt64Size(3, tokenHigh_);
      }
      if (!pin_.isEmpty()) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(4, pin_);
      }
      memoizedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof be.lghs.gate.proto.Gate.GateOpenRequest)) {
        return super.equals(obj);
      }
      be.lghs.gate.proto.Gate.GateOpenRequest other = (be.lghs.gate.proto.Gate.GateOpenRequest) obj;

      boolean result = true;
      result = result && (getCardId()
          == other.getCardId());
      result = result && (getTokenLow()
          == other.getTokenLow());
      result = result && (getTokenHigh()
          == other.getTokenHigh());
      result = result && getPin()
          .equals(other.getPin());
      return result;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + CARDID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getCardId());
      hash = (37 * hash) + TOKENLOW_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getTokenLow());
      hash = (37 * hash) + TOKENHIGH_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getTokenHigh());
      hash = (37 * hash) + PIN_FIELD_NUMBER;
      hash = (53 * hash) + getPin().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static be.lghs.gate.proto.Gate.GateOpenRequest parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static be.lghs.gate.proto.Gate.GateOpenRequest parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static be.lghs.gate.proto.Gate.GateOpenRequest parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static be.lghs.gate.proto.Gate.GateOpenRequest parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static be.lghs.gate.proto.Gate.GateOpenRequest parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static be.lghs.gate.proto.Gate.GateOpenRequest parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static be.lghs.gate.proto.Gate.GateOpenRequest parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static be.lghs.gate.proto.Gate.GateOpenRequest parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static be.lghs.gate.proto.Gate.GateOpenRequest parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static be.lghs.gate.proto.Gate.GateOpenRequest parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(be.lghs.gate.proto.Gate.GateOpenRequest prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code be.lghs.gate.proto.GateOpenRequest}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:be.lghs.gate.proto.GateOpenRequest)
        be.lghs.gate.proto.Gate.GateOpenRequestOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return be.lghs.gate.proto.Gate.internal_static_be_lghs_gate_proto_GateOpenRequest_descriptor;
      }

      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return be.lghs.gate.proto.Gate.internal_static_be_lghs_gate_proto_GateOpenRequest_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                be.lghs.gate.proto.Gate.GateOpenRequest.class, be.lghs.gate.proto.Gate.GateOpenRequest.Builder.class);
      }

      // Construct using be.lghs.gate.proto.Gate.GateOpenRequest.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      public Builder clear() {
        super.clear();
        cardId_ = 0L;

        tokenLow_ = 0L;

        tokenHigh_ = 0L;

        pin_ = com.google.protobuf.ByteString.EMPTY;

        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return be.lghs.gate.proto.Gate.internal_static_be_lghs_gate_proto_GateOpenRequest_descriptor;
      }

      public be.lghs.gate.proto.Gate.GateOpenRequest getDefaultInstanceForType() {
        return be.lghs.gate.proto.Gate.GateOpenRequest.getDefaultInstance();
      }

      public be.lghs.gate.proto.Gate.GateOpenRequest build() {
        be.lghs.gate.proto.Gate.GateOpenRequest result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public be.lghs.gate.proto.Gate.GateOpenRequest buildPartial() {
        be.lghs.gate.proto.Gate.GateOpenRequest result = new be.lghs.gate.proto.Gate.GateOpenRequest(this);
        result.cardId_ = cardId_;
        result.tokenLow_ = tokenLow_;
        result.tokenHigh_ = tokenHigh_;
        result.pin_ = pin_;
        onBuilt();
        return result;
      }

      public Builder clone() {
        return (Builder) super.clone();
      }
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return (Builder) super.setField(field, value);
      }
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return (Builder) super.clearField(field);
      }
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return (Builder) super.clearOneof(oneof);
      }
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, Object value) {
        return (Builder) super.setRepeatedField(field, index, value);
      }
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return (Builder) super.addRepeatedField(field, value);
      }
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof be.lghs.gate.proto.Gate.GateOpenRequest) {
          return mergeFrom((be.lghs.gate.proto.Gate.GateOpenRequest)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(be.lghs.gate.proto.Gate.GateOpenRequest other) {
        if (other == be.lghs.gate.proto.Gate.GateOpenRequest.getDefaultInstance()) return this;
        if (other.getCardId() != 0L) {
          setCardId(other.getCardId());
        }
        if (other.getTokenLow() != 0L) {
          setTokenLow(other.getTokenLow());
        }
        if (other.getTokenHigh() != 0L) {
          setTokenHigh(other.getTokenHigh());
        }
        if (other.getPin() != com.google.protobuf.ByteString.EMPTY) {
          setPin(other.getPin());
        }
        onChanged();
        return this;
      }

      public final boolean isInitialized() {
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        be.lghs.gate.proto.Gate.GateOpenRequest parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (be.lghs.gate.proto.Gate.GateOpenRequest) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private long cardId_ ;
      /**
       * <code>uint64 cardId = 1;</code>
       */
      public long getCardId() {
        return cardId_;
      }
      /**
       * <code>uint64 cardId = 1;</code>
       */
      public Builder setCardId(long value) {
        
        cardId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint64 cardId = 1;</code>
       */
      public Builder clearCardId() {
        
        cardId_ = 0L;
        onChanged();
        return this;
      }

      private long tokenLow_ ;
      /**
       * <code>uint64 tokenLow = 2;</code>
       */
      public long getTokenLow() {
        return tokenLow_;
      }
      /**
       * <code>uint64 tokenLow = 2;</code>
       */
      public Builder setTokenLow(long value) {
        
        tokenLow_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint64 tokenLow = 2;</code>
       */
      public Builder clearTokenLow() {
        
        tokenLow_ = 0L;
        onChanged();
        return this;
      }

      private long tokenHigh_ ;
      /**
       * <code>uint64 tokenHigh = 3;</code>
       */
      public long getTokenHigh() {
        return tokenHigh_;
      }
      /**
       * <code>uint64 tokenHigh = 3;</code>
       */
      public Builder setTokenHigh(long value) {
        
        tokenHigh_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint64 tokenHigh = 3;</code>
       */
      public Builder clearTokenHigh() {
        
        tokenHigh_ = 0L;
        onChanged();
        return this;
      }

      private com.google.protobuf.ByteString pin_ = com.google.protobuf.ByteString.EMPTY;
      /**
       * <code>bytes pin = 4;</code>
       */
      public com.google.protobuf.ByteString getPin() {
        return pin_;
      }
      /**
       * <code>bytes pin = 4;</code>
       */
      public Builder setPin(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        pin_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>bytes pin = 4;</code>
       */
      public Builder clearPin() {
        
        pin_ = getDefaultInstance().getPin();
        onChanged();
        return this;
      }
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return this;
      }

      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return this;
      }


      // @@protoc_insertion_point(builder_scope:be.lghs.gate.proto.GateOpenRequest)
    }

    // @@protoc_insertion_point(class_scope:be.lghs.gate.proto.GateOpenRequest)
    private static final be.lghs.gate.proto.Gate.GateOpenRequest DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new be.lghs.gate.proto.Gate.GateOpenRequest();
    }

    public static be.lghs.gate.proto.Gate.GateOpenRequest getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<GateOpenRequest>
        PARSER = new com.google.protobuf.AbstractParser<GateOpenRequest>() {
      public GateOpenRequest parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
          return new GateOpenRequest(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<GateOpenRequest> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<GateOpenRequest> getParserForType() {
      return PARSER;
    }

    public be.lghs.gate.proto.Gate.GateOpenRequest getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  public interface GateOpenResponseOrBuilder extends
      // @@protoc_insertion_point(interface_extends:be.lghs.gate.proto.GateOpenResponse)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>uint64 cardId = 1;</code>
     */
    long getCardId();

    /**
     * <code>bool ok = 2;</code>
     */
    boolean getOk();
  }
  /**
   * Protobuf type {@code be.lghs.gate.proto.GateOpenResponse}
   */
  public  static final class GateOpenResponse extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:be.lghs.gate.proto.GateOpenResponse)
      GateOpenResponseOrBuilder {
    // Use GateOpenResponse.newBuilder() to construct.
    private GateOpenResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private GateOpenResponse() {
      cardId_ = 0L;
      ok_ = false;
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
    }
    private GateOpenResponse(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      int mutable_bitField0_ = 0;
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!input.skipField(tag)) {
                done = true;
              }
              break;
            }
            case 8: {

              cardId_ = input.readUInt64();
              break;
            }
            case 16: {

              ok_ = input.readBool();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return be.lghs.gate.proto.Gate.internal_static_be_lghs_gate_proto_GateOpenResponse_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return be.lghs.gate.proto.Gate.internal_static_be_lghs_gate_proto_GateOpenResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              be.lghs.gate.proto.Gate.GateOpenResponse.class, be.lghs.gate.proto.Gate.GateOpenResponse.Builder.class);
    }

    public static final int CARDID_FIELD_NUMBER = 1;
    private long cardId_;
    /**
     * <code>uint64 cardId = 1;</code>
     */
    public long getCardId() {
      return cardId_;
    }

    public static final int OK_FIELD_NUMBER = 2;
    private boolean ok_;
    /**
     * <code>bool ok = 2;</code>
     */
    public boolean getOk() {
      return ok_;
    }

    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (cardId_ != 0L) {
        output.writeUInt64(1, cardId_);
      }
      if (ok_ != false) {
        output.writeBool(2, ok_);
      }
    }

    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (cardId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt64Size(1, cardId_);
      }
      if (ok_ != false) {
        size += com.google.protobuf.CodedOutputStream
          .computeBoolSize(2, ok_);
      }
      memoizedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof be.lghs.gate.proto.Gate.GateOpenResponse)) {
        return super.equals(obj);
      }
      be.lghs.gate.proto.Gate.GateOpenResponse other = (be.lghs.gate.proto.Gate.GateOpenResponse) obj;

      boolean result = true;
      result = result && (getCardId()
          == other.getCardId());
      result = result && (getOk()
          == other.getOk());
      return result;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + CARDID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getCardId());
      hash = (37 * hash) + OK_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
          getOk());
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static be.lghs.gate.proto.Gate.GateOpenResponse parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static be.lghs.gate.proto.Gate.GateOpenResponse parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static be.lghs.gate.proto.Gate.GateOpenResponse parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static be.lghs.gate.proto.Gate.GateOpenResponse parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static be.lghs.gate.proto.Gate.GateOpenResponse parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static be.lghs.gate.proto.Gate.GateOpenResponse parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static be.lghs.gate.proto.Gate.GateOpenResponse parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static be.lghs.gate.proto.Gate.GateOpenResponse parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static be.lghs.gate.proto.Gate.GateOpenResponse parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static be.lghs.gate.proto.Gate.GateOpenResponse parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(be.lghs.gate.proto.Gate.GateOpenResponse prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code be.lghs.gate.proto.GateOpenResponse}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:be.lghs.gate.proto.GateOpenResponse)
        be.lghs.gate.proto.Gate.GateOpenResponseOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return be.lghs.gate.proto.Gate.internal_static_be_lghs_gate_proto_GateOpenResponse_descriptor;
      }

      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return be.lghs.gate.proto.Gate.internal_static_be_lghs_gate_proto_GateOpenResponse_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                be.lghs.gate.proto.Gate.GateOpenResponse.class, be.lghs.gate.proto.Gate.GateOpenResponse.Builder.class);
      }

      // Construct using be.lghs.gate.proto.Gate.GateOpenResponse.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      public Builder clear() {
        super.clear();
        cardId_ = 0L;

        ok_ = false;

        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return be.lghs.gate.proto.Gate.internal_static_be_lghs_gate_proto_GateOpenResponse_descriptor;
      }

      public be.lghs.gate.proto.Gate.GateOpenResponse getDefaultInstanceForType() {
        return be.lghs.gate.proto.Gate.GateOpenResponse.getDefaultInstance();
      }

      public be.lghs.gate.proto.Gate.GateOpenResponse build() {
        be.lghs.gate.proto.Gate.GateOpenResponse result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public be.lghs.gate.proto.Gate.GateOpenResponse buildPartial() {
        be.lghs.gate.proto.Gate.GateOpenResponse result = new be.lghs.gate.proto.Gate.GateOpenResponse(this);
        result.cardId_ = cardId_;
        result.ok_ = ok_;
        onBuilt();
        return result;
      }

      public Builder clone() {
        return (Builder) super.clone();
      }
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return (Builder) super.setField(field, value);
      }
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return (Builder) super.clearField(field);
      }
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return (Builder) super.clearOneof(oneof);
      }
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, Object value) {
        return (Builder) super.setRepeatedField(field, index, value);
      }
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return (Builder) super.addRepeatedField(field, value);
      }
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof be.lghs.gate.proto.Gate.GateOpenResponse) {
          return mergeFrom((be.lghs.gate.proto.Gate.GateOpenResponse)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(be.lghs.gate.proto.Gate.GateOpenResponse other) {
        if (other == be.lghs.gate.proto.Gate.GateOpenResponse.getDefaultInstance()) return this;
        if (other.getCardId() != 0L) {
          setCardId(other.getCardId());
        }
        if (other.getOk() != false) {
          setOk(other.getOk());
        }
        onChanged();
        return this;
      }

      public final boolean isInitialized() {
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        be.lghs.gate.proto.Gate.GateOpenResponse parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (be.lghs.gate.proto.Gate.GateOpenResponse) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private long cardId_ ;
      /**
       * <code>uint64 cardId = 1;</code>
       */
      public long getCardId() {
        return cardId_;
      }
      /**
       * <code>uint64 cardId = 1;</code>
       */
      public Builder setCardId(long value) {
        
        cardId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>uint64 cardId = 1;</code>
       */
      public Builder clearCardId() {
        
        cardId_ = 0L;
        onChanged();
        return this;
      }

      private boolean ok_ ;
      /**
       * <code>bool ok = 2;</code>
       */
      public boolean getOk() {
        return ok_;
      }
      /**
       * <code>bool ok = 2;</code>
       */
      public Builder setOk(boolean value) {
        
        ok_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>bool ok = 2;</code>
       */
      public Builder clearOk() {
        
        ok_ = false;
        onChanged();
        return this;
      }
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return this;
      }

      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return this;
      }


      // @@protoc_insertion_point(builder_scope:be.lghs.gate.proto.GateOpenResponse)
    }

    // @@protoc_insertion_point(class_scope:be.lghs.gate.proto.GateOpenResponse)
    private static final be.lghs.gate.proto.Gate.GateOpenResponse DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new be.lghs.gate.proto.Gate.GateOpenResponse();
    }

    public static be.lghs.gate.proto.Gate.GateOpenResponse getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<GateOpenResponse>
        PARSER = new com.google.protobuf.AbstractParser<GateOpenResponse>() {
      public GateOpenResponse parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
          return new GateOpenResponse(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<GateOpenResponse> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<GateOpenResponse> getParserForType() {
      return PARSER;
    }

    public be.lghs.gate.proto.Gate.GateOpenResponse getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_be_lghs_gate_proto_GateOpenRequest_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_be_lghs_gate_proto_GateOpenRequest_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_be_lghs_gate_proto_GateOpenResponse_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_be_lghs_gate_proto_GateOpenResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\ngate.proto\022\022be.lghs.gate.proto\"S\n\017Gate" +
      "OpenRequest\022\016\n\006cardId\030\001 \001(\004\022\020\n\010tokenLow\030" +
      "\002 \001(\004\022\021\n\ttokenHigh\030\003 \001(\004\022\013\n\003pin\030\004 \001(\014\".\n" +
      "\020GateOpenResponse\022\016\n\006cardId\030\001 \001(\004\022\n\n\002ok\030" +
      "\002 \001(\010b\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_be_lghs_gate_proto_GateOpenRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_be_lghs_gate_proto_GateOpenRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_be_lghs_gate_proto_GateOpenRequest_descriptor,
        new java.lang.String[] { "CardId", "TokenLow", "TokenHigh", "Pin", });
    internal_static_be_lghs_gate_proto_GateOpenResponse_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_be_lghs_gate_proto_GateOpenResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_be_lghs_gate_proto_GateOpenResponse_descriptor,
        new java.lang.String[] { "CardId", "Ok", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
