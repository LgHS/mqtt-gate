# Gate Py

## Setup

Generating the proto file:
```bash
protoc --python_out=. --proto_path=.. ../gate.proto
```


Installing the required stuff
```bash
python3 -m virtualenv -p python3 .
source ./bin/activate
pip install -r requirements

cp uids.py.dist uids.py

python gate.py
```


## Deployement on temp network

```bash
scp *.py root@192.168.42.78:/root/OrangePiZeroMFRC522/MFRC522-python/ \
    && ssh root@192.168.42.78 systemctl restart gate
```
