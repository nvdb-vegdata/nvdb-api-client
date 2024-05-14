/*
 * Copyright (c) 2015-2017, Statens vegvesen
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package no.vegvesen.nvdbapi.client.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.Priority;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.client.ClientResponseContext;
import jakarta.ws.rs.client.ClientResponseFilter;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.WriterInterceptor;
import jakarta.ws.rs.ext.WriterInterceptorContext;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Jersey client filter that logs all requests and responses
 *
 * @author Tore Eide Andersen (Kantega AS)
 */
@Priority(Integer.MIN_VALUE)
public class LoggingFilter implements ClientRequestFilter, ClientResponseFilter, WriterInterceptor {
    private final Logger logger;
    private static final String NOTIFICATION_PREFIX = "* ";
    private static final String REQUEST_PREFIX = "> ";
    private static final String RESPONSE_PREFIX = "< ";
    private static final String ENTITY_LOGGER_PROPERTY = LoggingFilter.class.getName() + ".entityLogger";

    private final AtomicLong _id = new AtomicLong(0);
    private final boolean printEntity;
    private final int maxEntitySize;

    /**
     * Create a logging filter logging the request and response to a default JDK
     * logger, named as the fully qualified class name of this class. Entity
     * logging is turned off by default.
     */
    public LoggingFilter() {
        this(null, false);
    }

    /**
     * Create a logging filter with custom settings of entity
     * logging.
     * @param logger custom {@code {@link Logger}} to use
     * @param printEntity if true, entity will be logged as well up to the default maxEntitySize, which is 10KB
     */
    public LoggingFilter(Logger logger, boolean printEntity) {
        this.logger = Optional.ofNullable(logger).orElse(LoggerFactory.getLogger(getClass()));
        this.printEntity = printEntity;
        this.maxEntitySize = 10 * 1024;
    }

    /**
     * Creates a logging filter with entity logging turned on, but potentially limiting the size
     * of entity to be buffered and logged.
     *
     * @param logger custom {@code {@link Logger}} to use

     * @param maxEntitySize maximum number of entity bytes to be logged (and buffered) - if the entity is larger,
     *                      logging filter will print (and buffer in memory) only the specified number of bytes
     *                      and print "...more..." string at the end.
     */
    public LoggingFilter(Logger logger, int maxEntitySize) {
        this.logger = Optional.ofNullable(logger).orElse(LoggerFactory.getLogger(getClass()));
        this.printEntity = true;
        this.maxEntitySize = maxEntitySize;
    }

    private void log(StringBuilder b) {
        logger.debug(b.toString());
    }

    private StringBuilder prefixId(StringBuilder b, long id) {
        b.append(id).append(" ");
        return b;
    }

    private void printRequestLine(StringBuilder b, long id, String method, URI uri) {
        prefixId(b, id).append(NOTIFICATION_PREFIX).append("LoggingFilter - Request received on thread ").append(Thread.currentThread().getName()).append("\n");
        prefixId(b, id).append(REQUEST_PREFIX).append(method).append(" ").
                append(uri.toASCIIString()).append("\n");
    }

    private void printResponseLine(StringBuilder b, long id, int status) {
        prefixId(b, id).append(NOTIFICATION_PREFIX).
                append("LoggingFilter - Response received on thread ").append(Thread.currentThread().getName()).append("\n");
        prefixId(b, id).append(RESPONSE_PREFIX).
                append(status).
                append("\n");
    }

    private void printPrefixedHeaders(StringBuilder b, long id, final String prefix, MultivaluedMap<String, String> headers) {
        for (Map.Entry<String, List<String>> e : headers.entrySet()) {
            List<?> val = e.getValue();
            String header = e.getKey();

            if (val.size() == 1) {
                prefixId(b, id).append(prefix).append(header).append(": ").append(val.get(0)).append("\n");
            } else {
                StringBuilder sb = new StringBuilder();
                boolean add = false;
                for (Object s : val) {
                    if (add) {
                        sb.append(',');
                    }
                    add = true;
                    sb.append(s);
                }
                prefixId(b, id).append(prefix).append(header).append(": ").append(sb.toString()).append("\n");
            }
        }
    }

    private InputStream logInboundEntity(StringBuilder b, InputStream stream) throws IOException {
        if (!stream.markSupported()) {
            stream = new BufferedInputStream(stream);
        }
        stream.mark(maxEntitySize + 1);
        byte[] entity = new byte[maxEntitySize + 1];
        int entitySize = stream.read(entity);
        b.append(new String(entity, 0, Math.min(entitySize, maxEntitySize)));
        if (entitySize > maxEntitySize) {
            b.append("...more...");
        }
        b.append('\n');
        stream.reset();
        return stream;
    }

    @Override
    public void filter(ClientRequestContext context) throws IOException {
        if (!logger.isDebugEnabled()) {
            return;
        }
        long id = this._id.incrementAndGet();
        StringBuilder b = new StringBuilder();

        printRequestLine(b, id, context.getMethod(), context.getUri());
        printPrefixedHeaders(b, id, REQUEST_PREFIX, context.getStringHeaders());

        if (printEntity && context.hasEntity()) {
            OutputStream stream = new LoggingStream(b, context.getEntityStream());
            context.setEntityStream(stream);
            context.setProperty(ENTITY_LOGGER_PROPERTY, stream);
            // not calling log(b) here - it will be called by the interceptor
        } else {
            log(b);
        }
    }

    @Override
    public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {
        if (!logger.isDebugEnabled()) {
            return;
        }
        long id = this._id.incrementAndGet();
        StringBuilder b = new StringBuilder();

        printResponseLine(b, id, responseContext.getStatus());
        printPrefixedHeaders(b, id, RESPONSE_PREFIX, responseContext.getHeaders());

        if (printEntity && responseContext.hasEntity()) {
            responseContext.setEntityStream(logInboundEntity(b, responseContext.getEntityStream()));
        }

        log(b);
    }

    @Override
    public void aroundWriteTo(WriterInterceptorContext writerInterceptorContext) throws IOException, WebApplicationException {
        LoggingStream stream = (LoggingStream) writerInterceptorContext.getProperty(ENTITY_LOGGER_PROPERTY);
        writerInterceptorContext.proceed();
        if (logger.isTraceEnabled() && stream != null) {
            log(stream.getStringBuilder());
        }
    }

    private class LoggingStream extends OutputStream {
        private final StringBuilder b;
        private final OutputStream inner;
        private final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        LoggingStream(StringBuilder b, OutputStream inner) {
            this.b = b;
            this.inner = inner;
        }

        StringBuilder getStringBuilder() {
            // write entity to the builder
            byte[] entity = baos.toByteArray();

            b.append(new String(entity, 0, Math.min(entity.length, maxEntitySize)));
            if (entity.length > maxEntitySize) {
                b.append("...more...");
            }
            b.append('\n');

            return b;
        }

        @Override
        public void write(int i) throws IOException {
            if (baos.size() <= maxEntitySize) {
                baos.write(i);
            }
            inner.write(i);
        }
    }
}
