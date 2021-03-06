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
    + SynchronousQueue: no capacity: caused OOM is workQueue is too large, e.g. FixedThreadPool, SingleThreadPool
    + LinkedBlockingQueue: unbounded queue
    + ArrayBlockingQueue: bounded queue
    + recommended create thread pool manually
- threadFactory: default is Executors.defaultThreadFactory(), define the thread name, priority, daemon
- RejectedExecutionHandler
![](images/Jietu20200505-233518.png)
#### Types
- FixedThreadPool: LinkedBlockingQueue does not have limit bound, may cause OOM, 0s keepAlive Time
- SingleThreadPool: LinkedBlockingQueue does not have limit bound, may cause OOM, 0s keepAlive Time
- CachedThreadPool: SynchronizedQueue only contains 1 task, idle thread destroy unlimited thread may cause OOM, 60s keepAlive Time
- ScheduleThreadPool: schedule one time or periodic task, 60s keepAlive Time, DelayWorkingQueue
- WorkStealingPool: task has child task
- Create Thread Pool based on scenarios
![](images/Jietu20200506-222612.png)
#### Core size/Max size
- CPU bounded: calculation, hash, core size is twice of number of CPU cores
- IO bounded: multiple times of cores, most time CPU is waiting for IO
- thread number = core * (1 + waiting time/working time)
#### Shutdown
- shutdown: shutdown after executing task and waiting task done
- shutdownNow: interrupt executing task, 
- isShutdown: indicate if shutdown executed
- isTerminate: thread pool destroy
- awaitTermination: test if task will be done after period
#### Rejection Policy
- case
    + after shutdown
    + exceeded queue capacity and reach max pool
- policy
    + AbortPolicy: throw exception after submit task
    + DiscardPolicy: throw task without notification
    + DiscardOldestPolicy: throw the oldest task
    + CallerRunsPolicy: the caller execute task
#### Hook
- beforeExecute: hook before execution the task
```java
public class PauseableThreadPool extends ThreadPoolExecutor {
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        lock.lock();
        try {
            while (isPause) {
                unpause.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
```
#### Status
- running
- shutdown: will not accept new task
- stop: no new task, no handle for pending task
- tidying: all task done, going to execute terminate() 
- terminated: terminate() done
#### Implementation
- thread pool management
- working thread
- task queue: must be thread safe
- task interface
![](images/Jietu20200506-232240.png)
- Executors: util tools to create thread pool
- Executor: execute() to execute task
- ExecutorService: shutdown()
- Thread pick up task from task queue, get the Runnable to execute run()
- execute()
    + if number of thread less than core pool size, creat thread
    + if less than core pool size, put task into queue, create thread if there is no worker thread
    + reject the task
***

## Thread
#### Scenario
- each thread exclusive owe tools utils
    + initalValue to init utils
- each thread contains global variable to avoid parameter passing, different method in same request'
    + set in first time
    + get in subsequent
#### SimpleDateFormat
- refer to cache calendar object which is not thread safe
#### Benefit
- Thread safe
- no lock, more efficient
- save memory space
- avoid pass parameter for every layers
#### Implementation
- method: initialValue, set, get, remove
- thread has threadLocalMap
- threadLocalMap contains different threadLocal
- get(): passing the threadLocal object to find object
- set(): save object to threadLocalMap which key is threadLocal
- key is WeakReference<ThreadLocal>, value is strong Reference
    + if thread is never stop, key may GC but not value, JVM gc value if key is null
    + invoke remove() to avoid memory leak
- if key collision, find the next empty slot
![](images/Jietu20200507-142259.png)
***

## Lock
#### Overview
- lock access to shared resource
- lock and synchronized but lock does not replace synchronized
- why not synchronized
    + bad performance: no timeout, no interrupted
    + no shared resource lock
    + no idea if get the lock
- why lock
    + lock(): manually unlock, exception will not release lock
    + tryLock(): return immediate, true if success, false is can't acquire lock
    + tryLock(long time, TimeUnit unit): try acquire lock in certain period
    + lockInterruptibly(): thread interrupted during waiting for lock or having lock
- happens-before: lock and unlock is visible to all thread
![](images/可见性2.png)
#### Category
- Pessimistic lock, Optimistic lock
- shared lock, exclusive lock
- fair lock, unfair lock
- ReentrantLock, non ReentrantLock
- Interpretable lock, non Interpretable lock
- self spin lock, non self spin lock
#### Pessimistic vs Optimistic
- Pessimistic
    + exclusive lock, blocking thread and notify thread
    + thread maybe blocked forever
    + case: locking period is long, avoid self spin, high concurrent
- Optimistic
    + does not lock, check before update
    + CAS, e.g. git
    + case: read is much more than write
#### ReentrantLock
- ReentrantLock: same lock may get lock if this lock owed 
#### Fair lock vs unFair lock
- fair: FIFO but may cause the starvation
- unfair
    + allow the jumping queue in specific time
    + java default is unfair to improve performance
    + decrease performance
#### Shared lock vs Exclusive lock
- shared lock
    + ReentrantReadLock, increase performance for read
    + readonly, no update
- exclusive lock: ReentrantWriteLock
    + write lock is exclusive, only one thread owe lock
    + only one write lock
- one thread write or multiple threads read
- one lock which is lock by different way
- fair mode: disallow thread the jumping queue which may cause starvation
- unfair mode: 
    + allow write thread jump queue
    + allow read thread jump queue only when wait queue's header is not write thread
- allow lock downgrade but not upgrade
#### Self Spin vs Block
- Self Spin
    - may wait for short period to get lock
    - cost thread context swap maybe higher than self spin
    - thread does not give up CPU time slot
    - if lock is hold only, self spin will consume the CPU resource
    + do while  + CAS
***

## Atomic
#### Atomic operation
- thread safe
- lock in smaller scope, in variable level
- higher performance except the high competition
#### Category
![](images/6类原子类纵览.png)
#### AtomicIntegr
- get()
- getAndSet(int newValue)
- getAndIncrement()/getAndDecrement()
- compareAndSet(int expect, int update)
#### AtomicIntegerFiledUpdater
- only apply atomic operation in certain scenario
- does not support static
#### Adder, Accumulator 
- better performance than atomicLong
- segment lock
- atomicLong vs longAdder
    + atomicLong: local cache flush to shared cache
    + longAdder: different thread hold different count
        + base variable: directly add if less competition
        + cell[]: each thread add into cell[i]
        + sum = base + each cell[i]
- Accumulator: execution sequence is not sensitive for parallel computing