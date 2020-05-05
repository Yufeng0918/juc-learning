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
- reuse the thread
- mange number of thread in pool
