// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: serializer.proto

package ru.nsu.leorita.serializer;

public interface PacketOrBuilder extends
    // @@protoc_insertion_point(interface_extends:Packet)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>required .PacketType type = 1;</code>
   * @return Whether the type field is set.
   */
  boolean hasType();
  /**
   * <code>required .PacketType type = 1;</code>
   * @return The type.
   */
  ru.nsu.leorita.serializer.PacketType getType();

  /**
   * <code>optional int32 nameLength = 2;</code>
   * @return Whether the nameLength field is set.
   */
  boolean hasNameLength();
  /**
   * <code>optional int32 nameLength = 2;</code>
   * @return The nameLength.
   */
  int getNameLength();

  /**
   * <code>optional string filename = 3;</code>
   * @return Whether the filename field is set.
   */
  boolean hasFilename();
  /**
   * <code>optional string filename = 3;</code>
   * @return The filename.
   */
  java.lang.String getFilename();
  /**
   * <code>optional string filename = 3;</code>
   * @return The bytes for filename.
   */
  com.google.protobuf.ByteString
      getFilenameBytes();

  /**
   * <code>optional bytes payload = 4;</code>
   * @return Whether the payload field is set.
   */
  boolean hasPayload();
  /**
   * <code>optional bytes payload = 4;</code>
   * @return The payload.
   */
  com.google.protobuf.ByteString getPayload();

  /**
   * <code>optional string checksum = 5;</code>
   * @return Whether the checksum field is set.
   */
  boolean hasChecksum();
  /**
   * <code>optional string checksum = 5;</code>
   * @return The checksum.
   */
  java.lang.String getChecksum();
  /**
   * <code>optional string checksum = 5;</code>
   * @return The bytes for checksum.
   */
  com.google.protobuf.ByteString
      getChecksumBytes();

  /**
   * <code>optional int64 filesize = 6;</code>
   * @return Whether the filesize field is set.
   */
  boolean hasFilesize();
  /**
   * <code>optional int64 filesize = 6;</code>
   * @return The filesize.
   */
  long getFilesize();
}
