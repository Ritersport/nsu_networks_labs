// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: serializer.proto

package ru.nsu.leorita.serializer;

/**
 * Protobuf enum {@code PacketType}
 */
public enum PacketType
    implements com.google.protobuf.ProtocolMessageEnum {
  /**
   * <code>INIT = 0;</code>
   */
  INIT(0),
  /**
   * <code>ACK = 1;</code>
   */
  ACK(1),
  /**
   * <code>DATA = 2;</code>
   */
  DATA(2),
  /**
   * <code>END = 3;</code>
   */
  END(3),
  /**
   * <code>SUCCESS = 4;</code>
   */
  SUCCESS(4),
  /**
   * <code>FAIL = 5;</code>
   */
  FAIL(5),
  ;

  /**
   * <code>INIT = 0;</code>
   */
  public static final int INIT_VALUE = 0;
  /**
   * <code>ACK = 1;</code>
   */
  public static final int ACK_VALUE = 1;
  /**
   * <code>DATA = 2;</code>
   */
  public static final int DATA_VALUE = 2;
  /**
   * <code>END = 3;</code>
   */
  public static final int END_VALUE = 3;
  /**
   * <code>SUCCESS = 4;</code>
   */
  public static final int SUCCESS_VALUE = 4;
  /**
   * <code>FAIL = 5;</code>
   */
  public static final int FAIL_VALUE = 5;


  public final int getNumber() {
    return value;
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   * @deprecated Use {@link #forNumber(int)} instead.
   */
  @java.lang.Deprecated
  public static PacketType valueOf(int value) {
    return forNumber(value);
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   */
  public static PacketType forNumber(int value) {
    switch (value) {
      case 0: return INIT;
      case 1: return ACK;
      case 2: return DATA;
      case 3: return END;
      case 4: return SUCCESS;
      case 5: return FAIL;
      default: return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<PacketType>
      internalGetValueMap() {
    return internalValueMap;
  }
  private static final com.google.protobuf.Internal.EnumLiteMap<
      PacketType> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<PacketType>() {
          public PacketType findValueByNumber(int number) {
            return PacketType.forNumber(number);
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
    return ru.nsu.leorita.serializer.SerializerProto.getDescriptor().getEnumTypes().get(0);
  }

  private static final PacketType[] VALUES = values();

  public static PacketType valueOf(
      com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
    if (desc.getType() != getDescriptor()) {
      throw new java.lang.IllegalArgumentException(
        "EnumValueDescriptor is not for this type.");
    }
    return VALUES[desc.getIndex()];
  }

  private final int value;

  private PacketType(int value) {
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:PacketType)
}

