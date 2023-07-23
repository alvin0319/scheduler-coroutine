# scheduler-coroutine

Coroutine for Nukkit ported from [heartbeat-coroutines](https://github.com/monun/heartbeat-coroutines/tree/master)

--- 

* Features
    * Coroutine which dispatch()ed from Nukkit's main heartbeat
    * CoroutineScope in PluginBase lifecycle
    * Flexible delay process

---
Here's some example which process this code without asynchronous library:

1. Broadcast cooldown message every 1 second for 3 seconds
2. Damage all entities every 1 second for every 5 seconds
3. Broadcast `Surprise~` message and shutdown the server

#### Thread

```
Runnable {
    repeat(3) { 
        Server.getInstance().scheduler.scheduleTask(this) {
            broadcast(3 - it)
            Thread.sleep(1000L)
        }
    }
    repeat(5) {
        Server.getInstance().scheduler.scheduleTask(this) {
            damageAll()
        }
        Thread.sleep(1000L)
    }
    Server.getInstance().scheduler.scheduleTask(this) {
        broadcast("surprise~")
    }
}.let {
    Thread(it).start()
}
```

#### Callback

```kotlin
// somewhat method which process these code in async
async({
    repeat(3) {
        Server.getInstance().scheduler.scheduleTask(this) {
            broadcast(3 - it)
            Thread.sleep(1000L)
        }
    }
}) {
    async({
        repeat(5) {
            Server.getInstance().scheduler.scheduleTask(this) {
                damageAll()
            }
            Thread.sleep(1000L)
        }
    }) {
        async {
            Server.getInstance().scheduler.scheduleTask(this) {
                broadcast("surprise~")
            }
        }
    }
}
```

As routines become more complex, asynchrony issues and complexity reduce flexibility and increase exponentially in
maintenance difficulty.

By using coroutines, you can drastically reduce the complexity of chaining code in scheduler.

The example below is coroutine code that runs synchronously inside a scheduler.

#### Coroutine

```kotlin
CoroutineScope(Dispatchers.Scheduler).launch {
    repeat(3) {
        broadcast(3 - it)
        delay(1000L)
    }
    repeat(5) {
        damageAll()
        delay(1000L)
    }
    broadcast("surprise~")
}
```

It's simple, isn't it?

Please refer to [this document](https://kotlinlang.org/docs/coroutines-overview.html) to learn more about coroutine.

# Download & Installation

```kotlin
repositories {
    maven {
        name = "minjae-repo-snapshot"
        url = uri("https://repo.minjae.dev/snapshots")
    }
}

dependencies {
    implementation("dev.minjae.pnx.coroutine:scheduler-coroutine:1.0-SNAPSHOT")
}
```