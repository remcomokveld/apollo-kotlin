package com.apollographql.apollo3.api

import com.apollographql.apollo3.api.internal.ResponseAdapter
import com.apollographql.apollo3.api.internal.json.JsonWriter

/**
 * Represents a GraphQL operation (mutation or query).
 */
interface Operation<D : Operation.Data> {
  /**
   * Returns the raw GraphQL operation String.
   */
  fun queryDocument(): String

  /**
   * Returns GraphQL operation name
   */
  fun name(): String

  /**
   * Returns a unique identifier for this operation.
   */
  fun operationId(): String

  /**
   * Returns an Adapter that maps the server response data to/from generated model class [D].
   *
   * This is the low-level API generated by the compiler. Use [parse] and [toJson] extension functions for a higher level API
   */
  fun adapter(responseAdapterCache: ResponseAdapterCache): ResponseAdapter<D>

  /**
   * Serializes the variables of this operation to a json to send over HTTP or to a Map that we can later use to resolve variables
   */
  fun serializeVariables(writer: JsonWriter, responseAdapterCache: ResponseAdapterCache)

  /**
   * A helper class to hold variables
   */
  class Variables(val valueMap: Map<String, Any?>)

  /**
   * The tree of fields used for normalizing/reading from the cache
   */
  fun responseFields(): List<ResponseField.FieldSet>

  /**
   * Marker interface for generated models built from data returned by the server in response to this operation.
   */
  interface Data
}
