# Gate Py

## Setup

```bash
virtualenv .
source ./bin/activate
pip install -r requirements

protoc --python_out=. --proto_path=.. ../gate.proto

cp uids.py.dist uids.py

python gate.py
```
