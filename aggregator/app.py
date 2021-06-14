import json
import urllib
from urllib.error import URLError
import urllib.request
from flask import Flask, Response, jsonify, make_response, request

app = Flask(__name__)

BOOK_URL = "http://localhost:7766/api/books"
headers = {'Content-Type': 'application/json'}


@app.route('/')
def hello_world():
    return 'Hello World!'


@app.route('/api/book-collection', methods=['GET'])
def get_book_collection():
    try:
        req = urllib.request.Request(BOOK_URL)
        response = urllib.request.urlopen(req)
        data = response.read().decode("utf-8")
        print(data)
        return Response(response=data, headers=headers)
    except URLError:
        return Response(response={"msg": "backend server unavailable"}, headers=headers)


@app.route('/api/book-collection/<id>', methods=['GET'])
def get_book_byId(id):
    try:
        req = urllib.request.Request(BOOK_URL + '/' + id)
        response = urllib.request.urlopen(req)
        data = response.read().decode("utf-8")
        print(data)
        return Response(response=data, headers=headers)
    except URLError:
        return make_response({"msg": "id unavailable"})


@app.route('/api/book-collection', methods=['POST'])
def insert_book():
    try:
        data = dict(request.json)
        req = urllib.request.Request(BOOK_URL, data=json.dumps(data).encode(), headers=headers)
        response = urllib.request.urlopen(req)
        return Response(status=response.status)
    except URLError:
        return make_response({"msg": "book already exists"})



if __name__ == '__main__':
    app.run()

