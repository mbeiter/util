/*
 * #%L
 * This file is part of a universal JDBC Connection factory.
 * %%
 * Copyright (C) 2014 Michael Beiter <michael@beiter.org>
 * %%
 * All rights reserved.
 * .
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the copyright holder nor the names of the
 *       contributors may be used to endorse or promote products derived
 *       from this software without specific prior written permission.
 * .
 * .
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package org.beiter.michael.db;

/**
 * This exception is thrown by Factories when they encounter an error that needs to be handled by the caller.
 */
public class FactoryException
        extends Exception {

    /**
     * Serialization
     */
    private static final long serialVersionUID = 20141021L;

    /**
     * @see Exception#Exception()
     */
    public FactoryException() {
        super();
    }

    /**
     * @param message @see Exception#Exception(String, Throwable)
     * @param cause   @see Exception#Exception(String, Throwable)
     * @see Exception#Exception(String, Throwable)
     */
    public FactoryException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message @see Exception#Exception(String)
     * @see Exception#Exception(String)
     */
    public FactoryException(final String message) {
        super(message);
    }

    /**
     * @param cause @see Exception#Exception(Throwable)
     * @see Exception#Exception(Throwable)
     */
    public FactoryException(final Throwable cause) {
        super(cause);
    }
}
