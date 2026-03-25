import socket

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    s.bind(("127.0.0.1", 8000))
    s.listen()
    while True:
        conn, addr = s.accept()
        with conn:
            print(f"connexion de {addr}")
            html = "<html><body><h1>Hello World !</h1></body></html>"
            data = str.encode("GET / HTTP/1.1\r\nContent-Type: text/html\r\n<html><body><h1>Hello World !</h1></body></html>")
            print(f"recu : {data}")
            conn.send(data)
            print("deconnexion")
