/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package io.opentelemetry.javaagent.instrumentation.opentelemetryapi.metrics.bridge;

import application.io.opentelemetry.api.common.Attributes;
import application.io.opentelemetry.api.metrics.BoundLongUpDownCounter;
import application.io.opentelemetry.api.metrics.LongUpDownCounter;
import application.io.opentelemetry.context.Context;
import io.opentelemetry.javaagent.instrumentation.opentelemetryapi.context.AgentContextStorage;
import io.opentelemetry.javaagent.instrumentation.opentelemetryapi.trace.Bridging;

class ApplicationLongUpDownCounter implements LongUpDownCounter {

  private final io.opentelemetry.api.metrics.LongUpDownCounter agentCounter;

  ApplicationLongUpDownCounter(io.opentelemetry.api.metrics.LongUpDownCounter agentCounter) {
    this.agentCounter = agentCounter;
  }

  @Override
  public void add(long value) {
    agentCounter.add(value);
  }

  @Override
  public void add(long value, Attributes applicationAttributes) {
    agentCounter.add(value, Bridging.toAgent(applicationAttributes));
  }

  @Override
  public void add(long value, Attributes applicationAttributes, Context applicationContext) {
    agentCounter.add(
        value,
        Bridging.toAgent(applicationAttributes),
        AgentContextStorage.getAgentContext(applicationContext));
  }

  @Override
  public BoundLongUpDownCounter bind(Attributes applicationAttributes) {
    return new ApplicationBoundLongUpDownCounter(
        agentCounter.bind(Bridging.toAgent(applicationAttributes)));
  }
}
