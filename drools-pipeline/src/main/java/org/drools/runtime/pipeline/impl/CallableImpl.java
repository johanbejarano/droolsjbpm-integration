/*
 * Copyright 2010 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.runtime.pipeline.impl;

import org.drools.runtime.pipeline.Callable;
import org.drools.runtime.pipeline.PipelineContext;

public class CallableImpl<E> extends BaseEmitter
    implements
    Callable {
    public static final String key = "__CallableReturnValue__";

    public CallableImpl() {
        super();
    }

    public Object call(Object object,
                       PipelineContext context) {
        emit( object,
              context );
        return context.getProperties().remove( key );
    }

    public void receive(Object object,
                       PipelineContext context) {
        context.getProperties().put( key,
                                     object );

    }

}
