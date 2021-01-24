# README


## Install Kafka
```
brew install kafka
```



## Start kafka

### Zookeeper
Kafka needs zookeeper

```sh
cd kafka_2.13-2.7.0
zookeeper-server-start config/zookeeper.properties
```

### Kafka

```sh
cd kafka_2.13-2.7.0
kafka-server-start config/server.properties
```

