/*
 * Copyright (c) 2017-2019 AxonIQ B.V. and/or licensed to AxonIQ B.V.
 * under one or more contributor license agreements.
 *
 *  Licensed under the AxonIQ Open Source License Agreement v1.0;
 *  you may not use this file except in compliance with the license.
 *
 */

package io.axoniq.axonserver.applicationevents;

import io.axoniq.axonserver.grpc.command.CommandSubscription;
import io.axoniq.axonserver.grpc.query.QuerySubscription;
import io.axoniq.axonserver.message.ClientIdentification;
import io.axoniq.axonserver.message.command.CommandHandler;
import io.axoniq.axonserver.message.command.DirectCommandHandler;
import io.axoniq.axonserver.message.query.DirectQueryHandler;
import io.axoniq.axonserver.message.query.QueryHandler;

/**
 * Set of events that are raised when an application registers a CommandHandler or QueryHandler. Events are propagated to the other AxonServer nodes.
 * @author Marc Gathier
 */
public class SubscriptionEvents {
    public abstract static class SubscriptionBaseEvent {
        private final String context;
        private final boolean isProxied;

        SubscriptionBaseEvent(String context, boolean isProxied) {
            this.context = context;
            this.isProxied = isProxied;
        }

        public String getContext() {
            return context;
        }

        public boolean isProxied() {
            return isProxied;
        }
    }

    public static class UnsubscribeCommand extends SubscriptionBaseEvent {

        private final CommandSubscription request;

        public UnsubscribeCommand(String context, CommandSubscription request, boolean isProxied) {
            super(context, isProxied);
            this.request = request;
        }

        public CommandSubscription getRequest() {
            return request;
        }

        public ClientIdentification clientIdentification() {
            return new ClientIdentification(getContext(), request.getClientId());
        }

    }

    public static class UnsubscribeQuery extends SubscriptionBaseEvent {
        private final QuerySubscription unsubscribe;

        public UnsubscribeQuery(String context, QuerySubscription unsubscribe, boolean isProxied) {
            super(context, isProxied);
            this.unsubscribe = unsubscribe;
        }

        public QuerySubscription getUnsubscribe() {
            return unsubscribe;
        }
        public ClientIdentification clientIdentification() {
            return new ClientIdentification(getContext(), unsubscribe.getClientId());
        }

    }

    public static class SubscribeQuery extends SubscriptionBaseEvent {

        private final QuerySubscription subscription;
        private final QueryHandler queryHandler;

        public SubscribeQuery(String context, QuerySubscription subscription, QueryHandler queryHandler) {
            super(context, !(queryHandler instanceof DirectQueryHandler));
            this.subscription = subscription;
            this.queryHandler = queryHandler;
        }

        public QuerySubscription getSubscription() {
            return subscription;
        }

        public QueryHandler getQueryHandler() {
            return queryHandler;
        }

        public ClientIdentification clientIdentification() {
            return new ClientIdentification(getContext(), subscription.getClientId());
        }
    }

    public static class SubscribeCommand extends SubscriptionBaseEvent {

        private final CommandSubscription request;
        private final CommandHandler handler;

        public SubscribeCommand(String context, CommandSubscription request, CommandHandler handler) {
            super(context, !(handler instanceof DirectCommandHandler));
            this.request = request;
            this.handler = handler;
        }

        public CommandSubscription getRequest() {
            return request;
        }

        public CommandHandler getHandler() {
            return handler;
        }

        public ClientIdentification clientIdentification() {
            return new ClientIdentification( getContext(), request.getClientId());
        }

    }
}
