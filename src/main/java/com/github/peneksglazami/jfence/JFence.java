/*
 * Copyright 2016 Andrey Grigorov
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.github.peneksglazami.jfence;

import java.lang.instrument.Instrumentation;

/**
 * Java agent that instruments classes and makes all declared fields volatile.
 * So, some memory consistency errors can be fixed. But may be not right :-)
 * <p>
 * This agent was created just for fun as example of bytecode instrumentation
 * using <a href="http://jboss-javassist.github.io/javassist/">Javassist library</a>.
 *
 * @author Andrey Grigorov
 * @see <a href="https://docs.oracle.com/javase/tutorial/essential/concurrency/memconsist.html">Memory consistency errors</a>.
 */
public class JFence {

    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new JFenceClassTransformer());
    }
}
