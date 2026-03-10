import socket

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    s.connect(("104.18.26.120", 80))
    while True:
        s.sendall(b"GET / HTTP/1.1\\nHost: www.example.com\\n\\n")
        data = s.recv(1024)
        print(f"recu: {data}")
        if  not data:
            break
    print(f"deco")