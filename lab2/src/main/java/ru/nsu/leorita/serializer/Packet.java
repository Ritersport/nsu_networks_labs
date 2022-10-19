// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: serializer.proto

package ru.nsu.leorita.serializer;


/**
 * Protobuf type {@code Packet}
 */
public final class Packet extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:Packet)
    PacketOrBuilder {
private static final long serialVersionUID = 0L;
  // Use Packet.newBuilder() to construct.
  private Packet(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private Packet() {
    type_ = 0;
    filename_ = "";
    payload_ = com.google.protobuf.ByteString.EMPTY;
    checksum_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new Packet();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return ru.nsu.leorita.serializer.SerializerProto.internal_static_Packet_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return ru.nsu.leorita.serializer.SerializerProto.internal_static_Packet_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            ru.nsu.leorita.serializer.Packet.class, ru.nsu.leorita.serializer.Packet.Builder.class);
  }

  private int bitField0_;
  public static final int TYPE_FIELD_NUMBER = 1;
  private int type_;
  /**
   * <code>required .PacketType type = 1;</code>
   * @return Whether the type field is set.
   */
  @java.lang.Override public boolean hasType() {
    return ((bitField0_ & 0x00000001) != 0);
  }
  /**
   * <code>required .PacketType type = 1;</code>
   * @return The type.
   */
  @java.lang.Override public ru.nsu.leorita.serializer.PacketType getType() {
    @SuppressWarnings("deprecation")
    ru.nsu.leorita.serializer.PacketType result = ru.nsu.leorita.serializer.PacketType.valueOf(type_);
    return result == null ? ru.nsu.leorita.serializer.PacketType.INIT : result;
  }

  public static final int NAMELENGTH_FIELD_NUMBER = 2;
  private int nameLength_;
  /**
   * <code>optional int32 nameLength = 2;</code>
   * @return Whether the nameLength field is set.
   */
  @java.lang.Override
  public boolean hasNameLength() {
    return ((bitField0_ & 0x00000002) != 0);
  }
  /**
   * <code>optional int32 nameLength = 2;</code>
   * @return The nameLength.
   */
  @java.lang.Override
  public int getNameLength() {
    return nameLength_;
  }

  public static final int FILENAME_FIELD_NUMBER = 3;
  private volatile java.lang.Object filename_;
  /**
   * <code>optional string filename = 3;</code>
   * @return Whether the filename field is set.
   */
  @java.lang.Override
  public boolean hasFilename() {
    return ((bitField0_ & 0x00000004) != 0);
  }
  /**
   * <code>optional string filename = 3;</code>
   * @return The filename.
   */
  @java.lang.Override
  public java.lang.String getFilename() {
    java.lang.Object ref = filename_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      if (bs.isValidUtf8()) {
        filename_ = s;
      }
      return s;
    }
  }
  /**
   * <code>optional string filename = 3;</code>
   * @return The bytes for filename.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getFilenameBytes() {
    java.lang.Object ref = filename_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      filename_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int PAYLOAD_FIELD_NUMBER = 4;
  private com.google.protobuf.ByteString payload_;
  /**
   * <code>optional bytes payload = 4;</code>
   * @return Whether the payload field is set.
   */
  @java.lang.Override
  public boolean hasPayload() {
    return ((bitField0_ & 0x00000008) != 0);
  }
  /**
   * <code>optional bytes payload = 4;</code>
   * @return The payload.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getPayload() {
    return payload_;
  }

  public static final int CHECKSUM_FIELD_NUMBER = 5;
  private volatile java.lang.Object checksum_;
  /**
   * <code>optional string checksum = 5;</code>
   * @return Whether the checksum field is set.
   */
  @java.lang.Override
  public boolean hasChecksum() {
    return ((bitField0_ & 0x00000010) != 0);
  }
  /**
   * <code>optional string checksum = 5;</code>
   * @return The checksum.
   */
  @java.lang.Override
  public java.lang.String getChecksum() {
    java.lang.Object ref = checksum_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      if (bs.isValidUtf8()) {
        checksum_ = s;
      }
      return s;
    }
  }
  /**
   * <code>optional string checksum = 5;</code>
   * @return The bytes for checksum.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getChecksumBytes() {
    java.lang.Object ref = checksum_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      checksum_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int FILESIZE_FIELD_NUMBER = 6;
  private long filesize_;
  /**
   * <code>optional int64 filesize = 6;</code>
   * @return Whether the filesize field is set.
   */
  @java.lang.Override
  public boolean hasFilesize() {
    return ((bitField0_ & 0x00000020) != 0);
  }
  /**
   * <code>optional int64 filesize = 6;</code>
   * @return The filesize.
   */
  @java.lang.Override
  public long getFilesize() {
    return filesize_;
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    if (!hasType()) {
      memoizedIsInitialized = 0;
      return false;
    }
    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (((bitField0_ & 0x00000001) != 0)) {
      output.writeEnum(1, type_);
    }
    if (((bitField0_ & 0x00000002) != 0)) {
      output.writeInt32(2, nameLength_);
    }
    if (((bitField0_ & 0x00000004) != 0)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 3, filename_);
    }
    if (((bitField0_ & 0x00000008) != 0)) {
      output.writeBytes(4, payload_);
    }
    if (((bitField0_ & 0x00000010) != 0)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 5, checksum_);
    }
    if (((bitField0_ & 0x00000020) != 0)) {
      output.writeInt64(6, filesize_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (((bitField0_ & 0x00000001) != 0)) {
      size += com.google.protobuf.CodedOutputStream
        .computeEnumSize(1, type_);
    }
    if (((bitField0_ & 0x00000002) != 0)) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(2, nameLength_);
    }
    if (((bitField0_ & 0x00000004) != 0)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, filename_);
    }
    if (((bitField0_ & 0x00000008) != 0)) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(4, payload_);
    }
    if (((bitField0_ & 0x00000010) != 0)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(5, checksum_);
    }
    if (((bitField0_ & 0x00000020) != 0)) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt64Size(6, filesize_);
    }
    size += getUnknownFields().getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof ru.nsu.leorita.serializer.Packet)) {
      return super.equals(obj);
    }
    ru.nsu.leorita.serializer.Packet other = (ru.nsu.leorita.serializer.Packet) obj;

    if (hasType() != other.hasType()) return false;
    if (hasType()) {
      if (type_ != other.type_) return false;
    }
    if (hasNameLength() != other.hasNameLength()) return false;
    if (hasNameLength()) {
      if (getNameLength()
          != other.getNameLength()) return false;
    }
    if (hasFilename() != other.hasFilename()) return false;
    if (hasFilename()) {
      if (!getFilename()
          .equals(other.getFilename())) return false;
    }
    if (hasPayload() != other.hasPayload()) return false;
    if (hasPayload()) {
      if (!getPayload()
          .equals(other.getPayload())) return false;
    }
    if (hasChecksum() != other.hasChecksum()) return false;
    if (hasChecksum()) {
      if (!getChecksum()
          .equals(other.getChecksum())) return false;
    }
    if (hasFilesize() != other.hasFilesize()) return false;
    if (hasFilesize()) {
      if (getFilesize()
          != other.getFilesize()) return false;
    }
    if (!getUnknownFields().equals(other.getUnknownFields())) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    if (hasType()) {
      hash = (37 * hash) + TYPE_FIELD_NUMBER;
      hash = (53 * hash) + type_;
    }
    if (hasNameLength()) {
      hash = (37 * hash) + NAMELENGTH_FIELD_NUMBER;
      hash = (53 * hash) + getNameLength();
    }
    if (hasFilename()) {
      hash = (37 * hash) + FILENAME_FIELD_NUMBER;
      hash = (53 * hash) + getFilename().hashCode();
    }
    if (hasPayload()) {
      hash = (37 * hash) + PAYLOAD_FIELD_NUMBER;
      hash = (53 * hash) + getPayload().hashCode();
    }
    if (hasChecksum()) {
      hash = (37 * hash) + CHECKSUM_FIELD_NUMBER;
      hash = (53 * hash) + getChecksum().hashCode();
    }
    if (hasFilesize()) {
      hash = (37 * hash) + FILESIZE_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getFilesize());
    }
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static ru.nsu.leorita.serializer.Packet parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static ru.nsu.leorita.serializer.Packet parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static ru.nsu.leorita.serializer.Packet parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static ru.nsu.leorita.serializer.Packet parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static ru.nsu.leorita.serializer.Packet parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static ru.nsu.leorita.serializer.Packet parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static ru.nsu.leorita.serializer.Packet parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static ru.nsu.leorita.serializer.Packet parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static ru.nsu.leorita.serializer.Packet parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static ru.nsu.leorita.serializer.Packet parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static ru.nsu.leorita.serializer.Packet parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static ru.nsu.leorita.serializer.Packet parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(ru.nsu.leorita.serializer.Packet prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
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
   * Protobuf type {@code Packet}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:Packet)
      ru.nsu.leorita.serializer.PacketOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return ru.nsu.leorita.serializer.SerializerProto.internal_static_Packet_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return ru.nsu.leorita.serializer.SerializerProto.internal_static_Packet_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              ru.nsu.leorita.serializer.Packet.class, ru.nsu.leorita.serializer.Packet.Builder.class);
    }

    // Construct using ru.nsu.leorita.serializer.Packet.newBuilder()
    private Builder() {

    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);

    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      type_ = 0;
      bitField0_ = (bitField0_ & ~0x00000001);
      nameLength_ = 0;
      bitField0_ = (bitField0_ & ~0x00000002);
      filename_ = "";
      bitField0_ = (bitField0_ & ~0x00000004);
      payload_ = com.google.protobuf.ByteString.EMPTY;
      bitField0_ = (bitField0_ & ~0x00000008);
      checksum_ = "";
      bitField0_ = (bitField0_ & ~0x00000010);
      filesize_ = 0L;
      bitField0_ = (bitField0_ & ~0x00000020);
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return ru.nsu.leorita.serializer.SerializerProto.internal_static_Packet_descriptor;
    }

    @java.lang.Override
    public ru.nsu.leorita.serializer.Packet getDefaultInstanceForType() {
      return ru.nsu.leorita.serializer.Packet.getDefaultInstance();
    }

    @java.lang.Override
    public ru.nsu.leorita.serializer.Packet build() {
      ru.nsu.leorita.serializer.Packet result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public ru.nsu.leorita.serializer.Packet buildPartial() {
      ru.nsu.leorita.serializer.Packet result = new ru.nsu.leorita.serializer.Packet(this);
      int from_bitField0_ = bitField0_;
      int to_bitField0_ = 0;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        to_bitField0_ |= 0x00000001;
      }
      result.type_ = type_;
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.nameLength_ = nameLength_;
        to_bitField0_ |= 0x00000002;
      }
      if (((from_bitField0_ & 0x00000004) != 0)) {
        to_bitField0_ |= 0x00000004;
      }
      result.filename_ = filename_;
      if (((from_bitField0_ & 0x00000008) != 0)) {
        to_bitField0_ |= 0x00000008;
      }
      result.payload_ = payload_;
      if (((from_bitField0_ & 0x00000010) != 0)) {
        to_bitField0_ |= 0x00000010;
      }
      result.checksum_ = checksum_;
      if (((from_bitField0_ & 0x00000020) != 0)) {
        result.filesize_ = filesize_;
        to_bitField0_ |= 0x00000020;
      }
      result.bitField0_ = to_bitField0_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof ru.nsu.leorita.serializer.Packet) {
        return mergeFrom((ru.nsu.leorita.serializer.Packet)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(ru.nsu.leorita.serializer.Packet other) {
      if (other == ru.nsu.leorita.serializer.Packet.getDefaultInstance()) return this;
      if (other.hasType()) {
        setType(other.getType());
      }
      if (other.hasNameLength()) {
        setNameLength(other.getNameLength());
      }
      if (other.hasFilename()) {
        bitField0_ |= 0x00000004;
        filename_ = other.filename_;
        onChanged();
      }
      if (other.hasPayload()) {
        setPayload(other.getPayload());
      }
      if (other.hasChecksum()) {
        bitField0_ |= 0x00000010;
        checksum_ = other.checksum_;
        onChanged();
      }
      if (other.hasFilesize()) {
        setFilesize(other.getFilesize());
      }
      this.mergeUnknownFields(other.getUnknownFields());
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      if (!hasType()) {
        return false;
      }
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 8: {
              int tmpRaw = input.readEnum();
              ru.nsu.leorita.serializer.PacketType tmpValue =
                  ru.nsu.leorita.serializer.PacketType.forNumber(tmpRaw);
              if (tmpValue == null) {
                mergeUnknownVarintField(1, tmpRaw);
              } else {
                type_ = tmpRaw;
                bitField0_ |= 0x00000001;
              }
              break;
            } // case 8
            case 16: {
              nameLength_ = input.readInt32();
              bitField0_ |= 0x00000002;
              break;
            } // case 16
            case 26: {
              filename_ = input.readBytes();
              bitField0_ |= 0x00000004;
              break;
            } // case 26
            case 34: {
              payload_ = input.readBytes();
              bitField0_ |= 0x00000008;
              break;
            } // case 34
            case 42: {
              checksum_ = input.readBytes();
              bitField0_ |= 0x00000010;
              break;
            } // case 42
            case 48: {
              filesize_ = input.readInt64();
              bitField0_ |= 0x00000020;
              break;
            } // case 48
            default: {
              if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                done = true; // was an endgroup tag
              }
              break;
            } // default:
          } // switch (tag)
        } // while (!done)
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.unwrapIOException();
      } finally {
        onChanged();
      } // finally
      return this;
    }
    private int bitField0_;

    private int type_ = 0;
    /**
     * <code>required .PacketType type = 1;</code>
     * @return Whether the type field is set.
     */
    @java.lang.Override public boolean hasType() {
      return ((bitField0_ & 0x00000001) != 0);
    }
    /**
     * <code>required .PacketType type = 1;</code>
     * @return The type.
     */
    @java.lang.Override
    public ru.nsu.leorita.serializer.PacketType getType() {
      @SuppressWarnings("deprecation")
      ru.nsu.leorita.serializer.PacketType result = ru.nsu.leorita.serializer.PacketType.valueOf(type_);
      return result == null ? ru.nsu.leorita.serializer.PacketType.INIT : result;
    }
    /**
     * <code>required .PacketType type = 1;</code>
     * @param value The type to set.
     * @return This builder for chaining.
     */
    public Builder setType(ru.nsu.leorita.serializer.PacketType value) {
      if (value == null) {
        throw new NullPointerException();
      }
      bitField0_ |= 0x00000001;
      type_ = value.getNumber();
      onChanged();
      return this;
    }
    /**
     * <code>required .PacketType type = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearType() {
      bitField0_ = (bitField0_ & ~0x00000001);
      type_ = 0;
      onChanged();
      return this;
    }

    private int nameLength_ ;
    /**
     * <code>optional int32 nameLength = 2;</code>
     * @return Whether the nameLength field is set.
     */
    @java.lang.Override
    public boolean hasNameLength() {
      return ((bitField0_ & 0x00000002) != 0);
    }
    /**
     * <code>optional int32 nameLength = 2;</code>
     * @return The nameLength.
     */
    @java.lang.Override
    public int getNameLength() {
      return nameLength_;
    }
    /**
     * <code>optional int32 nameLength = 2;</code>
     * @param value The nameLength to set.
     * @return This builder for chaining.
     */
    public Builder setNameLength(int value) {
      bitField0_ |= 0x00000002;
      nameLength_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>optional int32 nameLength = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearNameLength() {
      bitField0_ = (bitField0_ & ~0x00000002);
      nameLength_ = 0;
      onChanged();
      return this;
    }

    private java.lang.Object filename_ = "";
    /**
     * <code>optional string filename = 3;</code>
     * @return Whether the filename field is set.
     */
    public boolean hasFilename() {
      return ((bitField0_ & 0x00000004) != 0);
    }
    /**
     * <code>optional string filename = 3;</code>
     * @return The filename.
     */
    public java.lang.String getFilename() {
      java.lang.Object ref = filename_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          filename_ = s;
        }
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>optional string filename = 3;</code>
     * @return The bytes for filename.
     */
    public com.google.protobuf.ByteString
        getFilenameBytes() {
      java.lang.Object ref = filename_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        filename_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>optional string filename = 3;</code>
     * @param value The filename to set.
     * @return This builder for chaining.
     */
    public Builder setFilename(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000004;
      filename_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>optional string filename = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearFilename() {
      bitField0_ = (bitField0_ & ~0x00000004);
      filename_ = getDefaultInstance().getFilename();
      onChanged();
      return this;
    }
    /**
     * <code>optional string filename = 3;</code>
     * @param value The bytes for filename to set.
     * @return This builder for chaining.
     */
    public Builder setFilenameBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000004;
      filename_ = value;
      onChanged();
      return this;
    }

    private com.google.protobuf.ByteString payload_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <code>optional bytes payload = 4;</code>
     * @return Whether the payload field is set.
     */
    @java.lang.Override
    public boolean hasPayload() {
      return ((bitField0_ & 0x00000008) != 0);
    }
    /**
     * <code>optional bytes payload = 4;</code>
     * @return The payload.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getPayload() {
      return payload_;
    }
    /**
     * <code>optional bytes payload = 4;</code>
     * @param value The payload to set.
     * @return This builder for chaining.
     */
    public Builder setPayload(com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000008;
      payload_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>optional bytes payload = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearPayload() {
      bitField0_ = (bitField0_ & ~0x00000008);
      payload_ = getDefaultInstance().getPayload();
      onChanged();
      return this;
    }

    private java.lang.Object checksum_ = "";
    /**
     * <code>optional string checksum = 5;</code>
     * @return Whether the checksum field is set.
     */
    public boolean hasChecksum() {
      return ((bitField0_ & 0x00000010) != 0);
    }
    /**
     * <code>optional string checksum = 5;</code>
     * @return The checksum.
     */
    public java.lang.String getChecksum() {
      java.lang.Object ref = checksum_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          checksum_ = s;
        }
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>optional string checksum = 5;</code>
     * @return The bytes for checksum.
     */
    public com.google.protobuf.ByteString
        getChecksumBytes() {
      java.lang.Object ref = checksum_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        checksum_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>optional string checksum = 5;</code>
     * @param value The checksum to set.
     * @return This builder for chaining.
     */
    public Builder setChecksum(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000010;
      checksum_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>optional string checksum = 5;</code>
     * @return This builder for chaining.
     */
    public Builder clearChecksum() {
      bitField0_ = (bitField0_ & ~0x00000010);
      checksum_ = getDefaultInstance().getChecksum();
      onChanged();
      return this;
    }
    /**
     * <code>optional string checksum = 5;</code>
     * @param value The bytes for checksum to set.
     * @return This builder for chaining.
     */
    public Builder setChecksumBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000010;
      checksum_ = value;
      onChanged();
      return this;
    }

    private long filesize_ ;
    /**
     * <code>optional int64 filesize = 6;</code>
     * @return Whether the filesize field is set.
     */
    @java.lang.Override
    public boolean hasFilesize() {
      return ((bitField0_ & 0x00000020) != 0);
    }
    /**
     * <code>optional int64 filesize = 6;</code>
     * @return The filesize.
     */
    @java.lang.Override
    public long getFilesize() {
      return filesize_;
    }
    /**
     * <code>optional int64 filesize = 6;</code>
     * @param value The filesize to set.
     * @return This builder for chaining.
     */
    public Builder setFilesize(long value) {
      bitField0_ |= 0x00000020;
      filesize_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>optional int64 filesize = 6;</code>
     * @return This builder for chaining.
     */
    public Builder clearFilesize() {
      bitField0_ = (bitField0_ & ~0x00000020);
      filesize_ = 0L;
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:Packet)
  }

  // @@protoc_insertion_point(class_scope:Packet)
  private static final ru.nsu.leorita.serializer.Packet DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new ru.nsu.leorita.serializer.Packet();
  }

  public static ru.nsu.leorita.serializer.Packet getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  @java.lang.Deprecated public static final com.google.protobuf.Parser<Packet>
      PARSER = new com.google.protobuf.AbstractParser<Packet>() {
    @java.lang.Override
    public Packet parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      Builder builder = newBuilder();
      try {
        builder.mergeFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(builder.buildPartial());
      } catch (com.google.protobuf.UninitializedMessageException e) {
        throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(e)
            .setUnfinishedMessage(builder.buildPartial());
      }
      return builder.buildPartial();
    }
  };

  public static com.google.protobuf.Parser<Packet> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<Packet> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public ru.nsu.leorita.serializer.Packet getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
