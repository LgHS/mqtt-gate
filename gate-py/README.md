# Gate Py

## Setup

The gate's code requires python 3.

Generating the proto file:
```bash
protoc --python_out=. --proto_path=.. ../gate.proto
```


Installing the required stuff
```bash
python -m virtualenv -p python3 .
source ./bin/activate
pip install -r requirements

cp uids.py.dist uids.py
# don't forget to update it

python gate.py
```


## Deployement through gingerbread (LGHS specific)

```bash
# Open the tunnel once
ssh -fNL 8022:192.168.43.102:22 gingerbread


scp -P8022 *.py root@localhost:/root/OrangePiZeroMFRC522/MFRC522-python/ \
    && ssh -p8022 root@localhost systemctl restart gate
```
