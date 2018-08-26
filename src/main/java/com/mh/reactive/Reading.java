package com.mh.reactive;

import akka.util.ByteString;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

class Reading {

    private final int id;

    private final double value;

    private Reading(int id, double value) {
        this.id = id;
        this.value = value;
    }

    double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("Reading(%d, %f)", id, value);
    }

    static Reading create(Collection<ByteString> fields) {
        List<String> fieldList = fields.stream().map(ByteString::utf8String).collect(toList());
        int id = Integer.parseInt(fieldList.get(0));
        double value = Double.parseDouble(fieldList.get(1));
        return new Reading(id, value);
    }
}
