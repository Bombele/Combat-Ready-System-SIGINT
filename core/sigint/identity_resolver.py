import zmq
import redis

class IdentityResolver:
    def __init__(self):
        self.r = redis.Redis(host='localhost', port=6379, db=0)
        self.context = zmq.Context()
        self.socket = self.context.socket(zmq.SUB)
        self.socket.connect("tcp://10.10.0.5:5555") # IP de la sonde SIGINT
        self.socket.setsockopt_string(zmq.SUBSCRIBE, "")

    def listen_and_map(self):
        print("[SIGINT] Mapping IP/IMSI opérationnel...")
        while True:
            msg = self.socket.recv_json()
            # On lie l'IP locale à l'IMSI capturé sur l'antenne relais
            self.r.set(msg['ip'], msg['imsi'], ex=3600) 

    def get_identity(self, ip):
        return self.r.get(ip)
