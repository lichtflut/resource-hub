/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.lichtflut.rb.core.eh;

/**
 * <p>
 *  Exception thrown when login does not succeed.
 * </p>
 *
 * <p>
 * 	Created Apr 29, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class LoginException extends RBAuthException {

    /**
     * @param errCode
     * @param msg
     * @param cause
     */
    public LoginException(int errCode, String msg, Throwable cause) {
        super(errCode, msg, cause);
    }

    /**
     * @param errCode
     * @param msg
     */
    public LoginException(int errCode, String msg) {
        super(errCode, msg);
    }

}
