# Java Util Concurrent

## Category
- thread safety
    + mutex: lock, synchronized, Collections.synchronizedList, Vector
    + no mutex: Atomic Object, Adder
    + concurrent collection: CurrentHashMap, CopyOnWriteArrayList
    + non synchronized: final, ThreadLocal
- thread management
    + Thread Pool: Executor, ThreadPool
    + Return result: Callable, Future, FutureTask
- thread coordination
    + CountDownLatch, Semaphore
    + AQS
***    
    
## Thread Pool
#### Overview
- reuse the thread
- mange number of thread in pool
- thread per task
    + overhead of creation of thread
    + unlimited tasks to create too much thread
 - thread pool
    + no overhead of creation and destroy thread
    + manage CPU and memory
    + central management of pool
- scenario
    + server
    + more than 5 threads
- only scale up when work queue is full
- if workQueue does not has limit, then will not reach maximum thread size  
#### Parameters
- corePoolSize
    + after thread pool initialization, there is no thread
    + wait for coming tasks to create thread to reach core pool size
- maxPoolSize
    + max thread in pool
    + create additional threads after workQueue is full
- keepAliveTime: thread idle over keepAliveTime when thread size is greater than core pool size
- workQueue
    + store task
    + SynchronousQueue: no capacity
    + LinkedBlockingQueue: unbounded queue
    + ArrayBlockingQueue: bounded queue
    + recommended create thread pool manually
- threadFactory: default is Executors.defaultThreadFactory(), define the thread name, prority, daemon
- RejectedExecutionHandler

![](images/Jietu20200505-233518.png)