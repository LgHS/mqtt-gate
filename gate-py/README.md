# Gate Py

## Setup

The gate's code requires python 3.

Generating the proto file:
```bash
apt install protobuf-compiler
protoc --python_out=. --proto_path=.. ../gate.proto
```


Installing the required stuff
```bash
apt install python3-venv
python3 -m venv [--prompt gate-py] .venv
source ./bin/activate
pip install -r requirements

cp config.py.dist config.py
# don't forget to update it

python gate.py
```


## Configuration of the client

* Check the content of the `config.py` file
* Add the user to the spi group to allow it to access the card reader (`sudo usermod -G spi -a pi`)
* Add the user to the gpio group to allow it to access the gpio (`sudo usermod -G gpio -a pi`)


## Deployment through gingerbread (LGHS specific)

```bash
# Open the tunnel once
ssh -fNL 8022:gate-internal.lghs.space:22 gingerbread


scp -P8022 *.py root@localhost:/root/OrangePiZeroMFRC522/MFRC522-python/ \
    && ssh -p8022 root@localhost systemctl restart gate
```




## Wiegand

The [`wiegand.py`](wiegand.py) file allows reading data from a wiegand device, but it only allows reading half the id of the card.
It could be used inside the space to identify members on restricted equipment, but colisions make it a last resort tool.

That program requires pigpiod which can be installed using the following commands

```bash
sudo apt install python3

python3 -m venv -p python3 .venv
source .venv/bin/activate
pip install pigpio

mkdir libs
cd libs
git clone https://github.com/joan2937/pigpio.git
cd pigpio
make
sudo LD_LIBRARY_PATH=. ./pigpiod # should be started as a service if the thing becomes permanent
```
