package com.mh.reactive;

import akka.NotUsed;
import akka.http.javadsl.model.ws.Message;
import akka.http.javadsl.model.ws.TextMessage;
import akka.http.javadsl.server.HttpApp;
import akka.http.javadsl.server.Route;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;

class Server extends HttpApp {

    private final Source<Double, NotUsed> readings;

    Server(Source<Double, NotUsed> readings) {
        this.readings = readings;
    }

    @Override
    protected Route routes() {
        return route(
                path("data", () -> {
                            Source<Message, NotUsed> messages = readings.map(String::valueOf).map(TextMessage::create);
                            return handleWebSocketMessages(Flow.fromSinkAndSourceCoupled(Sink.ignore(), messages));
                        }
                ),
                get(() ->
                        pathSingleSlash(() ->
                                getFromResource("index.html")
                        )
                )
        );
    }
}