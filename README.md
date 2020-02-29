# thread0

## synchronized
1. synchronized 是互斥锁
    - 对象被加锁之后，只有当synchronized代码块的内容执行完成后，才会释放锁
2. synchronized 锁定的是一个对象 锁的信息是再堆里面
    - synchronized 锁代码块说法是错误的
3. 可以形象的理解成厕所的锁
4. 一个 `synchronized` 代码块 想当于一个原子操作
    - 原子是不可分的


- 下面这两种写法是等价的
```
public void m() {
    synchronized (this) {
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }
}
```

```
public synchronized void m() {
    count--;
    System.out.println(Thread.currentThread().getName() + " count = " + count);
}
```

## java中主内存与工作内存之间的交互操作
关于主内存与工作内存之间具体的交互协议，即一个变量如何从主内存拷贝到工作内存、如何从工作内存同步到主内存的细节

Java内存模型定义了一下8中操作来完成、虚拟机实现的时候必须保证下下面提及的每一种操作都是原子的

- `load` 作用于`主内存的变量` ,把一个变量表示为一条线程独占的状态
- `unlock`  作用于`主内存的变量`,它把一个处于锁定状态的变量释放出来，释放后变量才可以被其他线程锁定
- `read` 作用于`主内存的变量`,它把一个变量的值 从 主内存中传输到线程的工作内存中，以便以后的load动作使用
- `load` 作用于`工作内存中的变量`, 它把 `read`操作从主内存中的得到的变量 放到工作内存的变量副本中
- `use` 作用于`工作内存中的变量`, 它把工作内存中的一个变量的值传递给执行引擎， 每当虚拟机遇到一个需要使用到变量的值的字节码指令时将会执行这个操作
- `assing` 作用于`工作内从中的变量`,它把一个从执行引擎接收到的值赋给工作内存的变量，每当虚拟机遇到一个给变量赋值的字节码指令时执行这个操作
- `store` 作用于`工作内存中的变量`,它把工作内存中一个变量的值传送到主内存，以便随后的`write`操作使用
- `write` 作用于`主内存的变量`,作用于主内存的变量，它把store操作从工作内存中得到的变量的值放入主内存的变量中

```
自我总结

一个闭环
lock(主) ---> read(主->工)  ---> load(工)  ---> use(工)   ---> assign(工)  ---> store(工->主)  ---> write(主) ---> unlock（主）

lock 和 unlock 对应 首尾
read 和 write 对应 

load -> use -> assign -> store

```

## 生产者消费者模型
1. synchronized wait notify notifyAll 一起使用
2. ReentrantLock 的 Promise 



