# Gate Server

This is the piece of software that will authorise users to enter spaces based on different rules.

## Rules

At the moment, only one rule is implemented, card ids are specified in the configuration as

```properties
card.<id>=<name>
```

### Planned rules

* room based (with ldap groups)
* protected token on card (backup card)
* single use token on card (rewritten when the user badges)

## Installation

```bash
# This script assumes you are running as root.

APPLICATION_DIRECTORY=/opt/gate-server
JAR_FILE=/tmp/gate-server.jar
SERVICE_FILE=/tmp/gate-server.service

mkdir -p "$APPLICATION_DIRECTORY/data"
cd "$APPLICATION_DIRECTORY"

useradd --home-dir "$APPLICATION_DIRECTORY/data" \
        --system \
        --shell /sbin/nologin \
        gate-server

install -T -o gate-server -g gate-server -m 0444 "$JAR_FILE" .
install -T -o root -g root -m 0444 "$SERVICE_FILE" .

touch gate-server.conf
touch gate-server.properties

chown gate-server: gate-server.properties
chmod 0440 gate-server.properties

ln -s $(basename "$JAR_FILE") gate-server.jar
ln -s $(basename "$SERVICE_FILE") /etc/systemd/system/gate-server.service

systemctl daemon-reload
systemctl enable gate-server
systemctl start gate-server
```

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
