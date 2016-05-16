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

/**
 * Example of wrong using shared variables between threads, reordering and
 * memory consistency error.
 * Field "stop" must be volatile.
 *
 * @author Andrey Grigorov
 */
public class UnfencedAcquireReleaseClass {

    private boolean stop;
    private int value;
    private int res;

    private void a() {
        value = 1;
        stop = true;
    }

    private void b() {
        while (!stop);
        res = value;
    }

    public static int getOne() {
        final UnfencedAcquireReleaseClass that = new UnfencedAcquireReleaseClass();

        Thread threadA = new Thread(new Runnable() {
            public void run() {
                that.a();
            }
        });
        Thread threadB = new Thread(new Runnable() {
            public void run() {
                that.b();
            }
        });
        threadA.start();
        threadB.start();

        try {
            threadB.join();
        } catch (InterruptedException e) {
        }

        return that.res;
    }
}