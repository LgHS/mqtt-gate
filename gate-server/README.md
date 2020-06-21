# Gate Server

This is the piece of software that will authorise users to enter spaces based on different rules.


## Setup

```sql
create user lghs_gate_server_root with password 'lghs_gate_server_root_password'; -- change it really
create database lghs_gate_server owner lghs_gate_server_root;

\c lghs_gate_server
revoke all on schema public from public;

create user lghs_gate_server_app;
grant connect on database lghs_gate_server to lghs_gate_server_app;

grant all on schema public to lghs_gate_server_root;
grant usage on schema public to lghs_gate_server_app;

create extension "uuid-ossp";
create extension pgcrypto;
create extension btree_gist;
```

## Rules

At the moment, only one rule is implemented, card ids are specified in the configuration as

```properties
card.<id>=<name>
```

### Planned rules

* room based (with ldap groups)
* protected token on card (backup card)
* single use token on card (rewritten when the user badges)

## Configuration

A property file is loaded on boot with the following required parameters

```properties
gate-server.mqtt.url=ssl://<host>:<port>
gate-server.mqtt.client-id=<client-id>
gate-server.mqtt.password=<password>
# the request-topic should contain a single `+` that matches the gate id
gate-server.mqtt.request-topic=<topic on which the doors request access>
# the response-topic should contain a single `{gate}` placeholder that will be replaced by the gate id
gate-server.mqtt.response-topic=<topic on which the server responds>
```


## Updater Protocol

Status:
  * ok
  * timeout
  * busy
  * cooldown (the updater waits a
   certain time after an action to prevent misuse)
  * wrong card
  
Actions

* reset: stop trying to read or write a card
* locate: makes the updater beep
* write: write some data to a card
  * request
    * uid
    * data
    * address
  * response
    * status (ok, timeout, wrong card)
* read: read data from a card
  * request
    * uid
    * address
  * response
    * status (ok, timeout, wrong card)
    * data 
* register: read any card and write a token to it
  * request
    * address
    * data
  * response
    * status (ok, timeout)
    * uid
* compare-and-swap: read data from card and
  * address
  * old_data
  * new_data
