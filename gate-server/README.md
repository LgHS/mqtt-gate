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
