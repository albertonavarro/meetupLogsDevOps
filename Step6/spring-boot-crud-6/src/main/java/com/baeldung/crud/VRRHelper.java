package com.baeldung.crud;

import net.logstash.logback.argument.StructuredArgument;

import static net.logstash.logback.argument.StructuredArguments.kv;

public class VRRHelper {

    public static StructuredArgument LOW = kv("priority", "low");

    public static StructuredArgument MID = kv("priority", "medium");

    public static StructuredArgument HIGH = kv("priority", "high");

}
