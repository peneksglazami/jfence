# jfence

Java agent that instruments all class and makes all declared fields volatile.
So, some <a href="https://docs.oracle.com/javase/tutorial/essential/concurrency/memconsist.html">memory consistency errors</a> can be fixed. But may be not right :-)
This agent was created just for fun as example of bytecode instrumentation using <a href="http://jboss-javassist.github.io/javassist/">Javassist library</a>.
 
