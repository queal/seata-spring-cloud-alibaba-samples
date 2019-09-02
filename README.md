# seata-spring-cloud-alibaba-samples
spring-cloud-alibaba seata samples

## Quick Start
technology stack : 
- jdk 1.8
- spring-cloud Greenwich.SR2
- spring-cloud-alibaba 2.1.0.RELEASE  
- nacos 
- feign
- sentinel
- seata 0.8.0
- mysql

https://github.com/alibaba/spring-cloud-alibaba

### Install Nacos
[download nacos](https://github.com/alibaba/nacos/releases)
```bash
unzip nacos-server-xxx.zip
cd nacos/bin 
sh startup.sh -m standalone
```

what is nacos? something like zk, config server, service discovery

### Install Seata TM
[download seata](https://github.com/seata/seata/releases)
```bash
unzip seata-server-xxx.zip
```
before run, there has some config to do(because i use spring-cloud-alibaba)

i use nacos for seata discovery-server

#### Edit nacos-config.txt
```
cd conf
vi nacos-config.txt

service.default.grouplist=[seata-server ip]
store.db.driver-class-name=com.mysql.jdbc.Driver

store.db.url=jdbc:mysql://[seata-db]
store.db.user=[user]
store.db.password=[pwd]
```

#### Upload Config to Nacos
start nacos, pull all seata-nacos-config to nacos
```
sh nacos-config.sh [nacos-ip]
```

#### Change registry.conf
type = 'nacos' to register itself to nacos as service
```
registry {
  # file 、nacos 、eureka、redis、zk、consul、etcd3、sofa
  type = "nacos"

  nacos {
    serverAddr = "[nacos-ip]"
    namespace = "public"
    cluster = "default"
  }
 
}

config {
  # file、nacos 、apollo、zk、consul、etcd3
  type = "nacos"

  nacos {
    serverAddr = "[nacos-ip]"
    namespace = "public"
    cluster = "default"
  }
  
}
```


#### seata-init-sql
```
CREATE TABLE `undo_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) NOT NULL,
  `context` varchar(128) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=203 DEFAULT CHARSET=utf8;

CREATE TABLE `lock_table` (
  `row_key` varchar(128) NOT NULL,
  `xid` varchar(96) DEFAULT NULL,
  `transaction_id` mediumtext,
  `branch_id` mediumtext,
  `resource_id` varchar(256) DEFAULT NULL,
  `table_name` varchar(32) DEFAULT NULL,
  `pk` varchar(32) DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`row_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `global_table` (
  `xid` varchar(128) NOT NULL,
  `transaction_id` bigint(20) DEFAULT NULL,
  `status` tinyint(4) NOT NULL,
  `application_id` varchar(32) DEFAULT NULL,
  `transaction_service_group` varchar(32) DEFAULT NULL,
  `transaction_name` varchar(64) DEFAULT NULL,
  `timeout` int(11) DEFAULT NULL,
  `begin_time` bigint(20) DEFAULT NULL,
  `application_data` varchar(2000) DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`xid`),
  KEY `idx_gmt_modified_status` (`gmt_modified`,`status`),
  KEY `idx_transaction_id` (`transaction_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `branch_table` (
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(128) NOT NULL,
  `transaction_id` bigint(20) DEFAULT NULL,
  `resource_group_id` varchar(32) DEFAULT NULL,
  `resource_id` varchar(256) DEFAULT NULL,
  `lock_key` varchar(128) DEFAULT NULL,
  `branch_type` varchar(8) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `client_id` varchar(64) DEFAULT NULL,
  `application_data` varchar(2000) DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`branch_id`),
  KEY `idx_xid` (`xid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

 
#### Start Server 
``` 
sh seata-server.sh -p 8091 -h 192.168.x.x -m db
```
-p port

-h ip

-m [db,file]
(this is my understand, not confirmed!!!)
- db, seata use db for HA cluster
- file,  seata use file-system for HA, it can run itself

 

### Clone git

create table for test:
```mysql
CREATE TABLE `seata_goods` (
  `id` varchar(32) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

config db, nacos info in application-demo.yml

then start SeataNode1Application, SeataNode2Application

browser: http://localhost:18081/addGoods1

whether node1.GoodsServiceImpl or node2.GoodsServiceImpl throw Exception

table seata_goods has not record`


### Some Notice
if you can't connect seata-server

check config
```
spring.alibaba.seata.tx-service-group: my_test_tx_group
```


### pom.xml
spring-cloud-starter-alibaba-seata
```
<dependency>
    <groupId>io.seata</groupId>
    <artifactId>seata-all</artifactId>
</dependency>
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-seata</artifactId>
</dependency>
```

nacos, openfeign, sentinel
```
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
</dependency>
```

if use sentinel, please notice feign throws exception, this exception should define at yourself. here just a example.


### Me
my english is poor, so, chinglish ^_^

i just learn this, and share this, my understanding may be wrong. please refer to the official statement. 